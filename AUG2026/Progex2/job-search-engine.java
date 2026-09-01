import java.util.*;

// Step 1: Job Document Record
record JobDoc(int id, String title, String description, long timePosted) {}

class JobSearchEngine {

    // Step 3: Storage & Inverted Index Data Structures
    // docStore: docId -> JobDoc
    private final Map<Integer, JobDoc> docStore = new HashMap<>();
    // invertedIndex: word -> (docId -> termScore)
    private final Map<String, Map<Integer, Integer>> invertedIndex = new HashMap<>();

    // Step 2: Tokenize text into lowercase alphanumeric words using Character.isLetterOrDigit
    private List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return tokens;
        }

        StringBuilder currentWord = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                currentWord.append(Character.toLowerCase(c));
            } else if (currentWord.length() > 0) {
                tokens.add(currentWord.toString());
                currentWord.setLength(0); // reset buffer
            }
        }

        // Add the last word if present
        if (currentWord.length() > 0) {
            tokens.add(currentWord.toString());
        }

        return tokens;
    }

    // Helper: Remove old postings from inverted index when a job is updated/re-added
    private void removeOldJobPostings(JobDoc oldDoc) {
        if (oldDoc == null) {
            return;
        }

        Set<String> oldWords = new HashSet<>(tokenize(oldDoc.title()));
        oldWords.addAll(tokenize(oldDoc.description()));

        for (String word : oldWords) {
            Map<Integer, Integer> postings = invertedIndex.get(word);
            if (postings != null) {
                postings.remove(oldDoc.id());
                if (postings.isEmpty()) {
                    invertedIndex.remove(word); // Clean up empty term entries
                }
            }
        }
    }

    // Step 3: Ingestion method - add/update job document and populate inverted index
    public void addJob(JobDoc doc) {
        if (doc == null) {
            return;
        }

        // Handle existing ID update (clean old postings to prevent stale matches)
        if (docStore.containsKey(doc.id())) {
            removeOldJobPostings(docStore.get(doc.id()));
        }

        // 1. Store the document
        docStore.put(doc.id(), doc);

        // 2. Count word frequencies in title
        Map<String, Integer> titleFreq = new HashMap<>();
        for (String word : tokenize(doc.title())) {
            titleFreq.put(word, titleFreq.getOrDefault(word, 0) + 1);
        }

        // 3. Count word frequencies in description
        Map<String, Integer> descFreq = new HashMap<>();
        for (String word : tokenize(doc.description())) {
            descFreq.put(word, descFreq.getOrDefault(word, 0) + 1);
        }

        // 4. Collect unique words across both fields
        Set<String> allWords = new HashSet<>(titleFreq.keySet());
        allWords.addAll(descFreq.keySet());

        // 5. Calculate weighted score per word: (titleCount * 2) + (descCount * 1)
        for (String word : allWords) {
            int score = (titleFreq.getOrDefault(word, 0) * 2) + (descFreq.getOrDefault(word, 0) * 1);
            invertedIndex.computeIfAbsent(word, k -> new HashMap<>()).put(doc.id(), score);
        }
    }

    // Step 4: Search method with Top-K Min-Heap and multi-tier tie breaking
    public List<JobDoc> search(String query, int k) {
        // 1. Handle edge cases
        if (k <= 0 || query == null || query.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. Tokenize and deduplicate query words
        List<String> tokens = tokenize(query);
        if (tokens.isEmpty()) {
            return Collections.emptyList();
        }
        Set<String> uniqueQueryWords = new HashSet<>(tokens);

        // 3. Aggregate match scores for each matching document
        Map<Integer, Integer> docScores = new HashMap<>();
        for (String word : uniqueQueryWords) {
            Map<Integer, Integer> postings = invertedIndex.get(word);
            if (postings != null) {
                for (Map.Entry<Integer, Integer> entry : postings.entrySet()) {
                    docScores.put(entry.getKey(), docScores.getOrDefault(entry.getKey(), 0) + entry.getValue());
                }
            }
        }

        // If no documents matched
        if (docScores.isEmpty()) {
            return Collections.emptyList();
        }

        // 4. Min-Heap of size k for Top-K selection
        // Eviction order (root = weakest item evicted when heap.size() > k):
        // 1. Lower score
        // 2. Older timestamp (smaller timePosted)
        // 3. Alphabetically later title (e.g., 'Beta' evicted before 'Alpha')
        // 4. Larger ID (deterministic tie-breaker)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((idA, idB) -> {
            int scoreA = docScores.get(idA);
            int scoreB = docScores.get(idB);
            if (scoreA != scoreB) {
                return Integer.compare(scoreA, scoreB);
            }

            JobDoc docA = docStore.get(idA);
            JobDoc docB = docStore.get(idB);
            long timeA = (docA != null) ? docA.timePosted() : 0L;
            long timeB = (docB != null) ? docB.timePosted() : 0L;
            if (timeA != timeB) {
                return Long.compare(timeA, timeB);
            }

            // Null-safe alphabetical title comparison
            String titleA = (docA != null && docA.title() != null) ? docA.title() : "";
            String titleB = (docB != null && docB.title() != null) ? docB.title() : "";
            int titleCmp = titleB.compareToIgnoreCase(titleA);
            if (titleCmp != 0) {
                return titleCmp;
            }

            // Final deterministic tie-breaker on ID (smaller ID prioritized)
            return Integer.compare(idB, idA);
        });

        // 5. Push matched docIds into Min-Heap
        for (int docId : docScores.keySet()) {
            minHeap.offer(docId);
            if (minHeap.size() > k) {
                minHeap.poll(); // evict weakest
            }
        }

        // 6. Extract results and reverse to get descending order (Rank 1 first)
        List<JobDoc> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            JobDoc doc = docStore.get(minHeap.poll());
            if (doc != null) {
                result.add(doc);
            }
        }
        Collections.reverse(result);

        return result;
    }

    // Step 5: Test Suite / Main Driver
    public static void main(String[] args) {
        JobSearchEngine engine = new JobSearchEngine();

        // Sample Jobs
        // Job 1: Title has "Java" (score +2), Description has "Spring" (score +1)
        JobDoc job1 = new JobDoc(1, "Senior Java Developer", "Build cloud services using Spring and AWS", 1700000000L);
        // Job 2: Description has "Java" twice (score +2)
        JobDoc job2 = new JobDoc(2, "Backend Engineer", "Deep experience with Java, microservices, and Java frameworks", 1700000500L);
        // Job 3: Title has "Java" (score +2), Description has "Java" (score +1) -> Total 3
        JobDoc job3 = new JobDoc(3, "Java Software Architect", "Expert in Java and distributed architecture", 1700000100L);
        // Job 4: Tie-breaker test (same score as Job 5, but more recent timestamp)
        JobDoc job4 = new JobDoc(4, "Frontend Developer", "React and TypeScript", 1700001000L);
        // Job 5: Tie-breaker test (same score, older timestamp)
        JobDoc job5 = new JobDoc(5, "Frontend Engineer", "React and CSS", 1700000000L);
        // Job 6 & 7: Alphabetical tie-breaker test (same score, same timestamp)
        JobDoc job6 = new JobDoc(6, "Beta Data Scientist", "Python and ML", 1700002000L);
        JobDoc job7 = new JobDoc(7, "Alpha Data Scientist", "Python and AI", 1700002000L);

        engine.addJob(job1);
        engine.addJob(job2);
        engine.addJob(job3);
        engine.addJob(job4);
        engine.addJob(job5);
        engine.addJob(job6);
        engine.addJob(job7);

        System.out.println("=== Test 1: Search 'Java' (Top 2) ===");
        // Job 3 score = 3 (title + desc), Job 1 score = 2 (title), Job 2 score = 2 (desc x2)
        // Job 2 has time 1700000500 > Job 1 time 1700000000, so Job 2 wins tie-breaker over Job 1.
        List<JobDoc> res1 = engine.search("Java", 2);
        for (JobDoc doc : res1) {
            System.out.println(" - " + doc);
        }

        System.out.println("\n=== Test 2: Search with Repeated Query Words 'Java Java Java' ===");
        // Query deduplication test: score should match single "Java" query
        List<JobDoc> res2 = engine.search("Java Java Java", 2);
        for (JobDoc doc : res2) {
            System.out.println(" - " + doc);
        }

        System.out.println("\n=== Test 3: Tie-breaker by Time Posted (Search 'React', Top 2) ===");
        // Job 4 (time=1700001000) should rank before Job 5 (time=1700000000)
        List<JobDoc> res3 = engine.search("React", 2);
        for (JobDoc doc : res3) {
            System.out.println(" - " + doc);
        }

        System.out.println("\n=== Test 4: Alphabetical Tie-breaker (Search 'Python', Top 2) ===");
        // Same score, same time -> 'Alpha' (Job 7) should come before 'Beta' (Job 6)
        List<JobDoc> res4 = engine.search("Python", 2);
        for (JobDoc doc : res4) {
            System.out.println(" - " + doc);
        }

        System.out.println("\n=== Test 5: Overwrite / Update Job with Same ID ===");
        // Update Job 1: change from Java to Kotlin
        JobDoc updatedJob1 = new JobDoc(1, "Senior Kotlin Developer", "Build Kotlin microservices", 1700003000L);
        engine.addJob(updatedJob1);
        System.out.println("Searching 'Java' after updating Job 1 to Kotlin (Job 1 should NOT appear):");
        List<JobDoc> resJavaAfterUpdate = engine.search("Java", 5);
        for (JobDoc doc : resJavaAfterUpdate) {
            System.out.println(" - " + doc);
        }
        System.out.println("Searching 'Kotlin' (Job 1 should appear):");
        List<JobDoc> resKotlin = engine.search("Kotlin", 5);
        for (JobDoc doc : resKotlin) {
            System.out.println(" - " + doc);
        }

        System.out.println("\n=== Test 6: Edge Cases ===");
        System.out.println("k <= 0: " + engine.search("Java", 0).size());
        System.out.println("Non-matching query: " + engine.search("Rust Golang", 5).size());
        System.out.println("Special characters query '!!! @@@': " + engine.search("!!! @@@", 5).size());
        System.out.println("k > matches (search 'React', k=10): " + engine.search("React", 10).size());
        System.out.println("Null title job document handled safely: " + (engine.search("", 1).isEmpty()));
    }
}
