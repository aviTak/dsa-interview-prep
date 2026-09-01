import java.util.*;

class DocumentMatchingIndex {

    // invertedIndex: word -> (docName -> frequencyInDoc)
    private final Map<String, Map<String, Integer>> invertedIndex = new HashMap<>();

    // docWordsMap: docName -> Set of unique words in that document (used to clean up on duplicate document names)
    private final Map<String, Set<String>> docWordsMap = new HashMap<>();

    // Part 1: Tokenize text into lowercase alphanumeric words using Character.isLetterOrDigit
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

    // Helper: Removes existing document postings if a document with the same name is added
    private void removeOldDocumentPostings(String name) {
        Set<String> oldWords = docWordsMap.remove(name);
        if (oldWords == null) {
            return;
        }

        for (String word : oldWords) {
            Map<String, Integer> postings = invertedIndex.get(word);
            if (postings != null) {
                postings.remove(name);
                if (postings.isEmpty()) {
                    invertedIndex.remove(word); // Clean up empty term entry
                }
            }
        }
    }

    // Part 2: Ingestion method - add/update document and populate inverted index
    public void addDocument(String name, String content) {
        if (name == null || name.trim().isEmpty() || content == null) {
            return;
        }

        // If document with the same name exists, remove old postings first
        removeOldDocumentPostings(name);

        // Tokenize content and count word frequencies
        List<String> tokens = tokenize(content);
        Map<String, Integer> wordFreq = new HashMap<>();
        for (String word : tokens) {
            wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
        }

        // Populate inverted index
        for (Map.Entry<String, Integer> entry : wordFreq.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();
            invertedIndex.computeIfAbsent(word, k -> new HashMap<>()).put(name, count);
        }

        // Track unique words for this document
        docWordsMap.put(name, wordFreq.keySet());
    }

    // Part 3: Search method with Top-K Min-Heap and multi-level tie-breaking
    public List<String> search(String query, int k) {
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

        // 3. Aggregate match scores for each document (count occurrences of query terms)
        Map<String, Integer> docScores = new HashMap<>();
        for (String word : uniqueQueryWords) {
            Map<String, Integer> postings = invertedIndex.get(word);
            if (postings != null) {
                for (Map.Entry<String, Integer> entry : postings.entrySet()) {
                    docScores.put(entry.getKey(), docScores.getOrDefault(entry.getKey(), 0) + entry.getValue());
                }
            }
        }

        // If no documents matched
        if (docScores.isEmpty()) {
            return Collections.emptyList();
        }

        // 4. Min-Heap of size k for Top-K selection
        // Eviction order (root = weakest item evicted when minHeap.size() > k):
        // 1. Lower score
        // 2. Alphabetically later name (case-insensitive)
        // 3. Exact case fallback (ASCII) if names differ only by letter case
        PriorityQueue<String> minHeap = new PriorityQueue<>((docA, docB) -> {
            int scoreA = docScores.get(docA);
            int scoreB = docScores.get(docB);
            if (scoreA != scoreB) {
                return Integer.compare(scoreA, scoreB);
            }

            // 1st Tie-Breaker: Case-insensitive alphabetical comparison
            int cmp = docB.compareToIgnoreCase(docA);
            if (cmp != 0) {
                return cmp;
            }

            // 2nd Tie-Breaker: Exact case comparison if names differ only by letter case
            return docB.compareTo(docA);
        });

        // 5. Push matched document names into Min-Heap
        for (String docName : docScores.keySet()) {
            minHeap.offer(docName);
            if (minHeap.size() > k) {
                minHeap.poll(); // evict weakest
            }
        }

        // 6. Extract results and reverse to get descending order (Rank 1 first)
        List<String> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll());
        }
        Collections.reverse(result);

        return result;
    }

    // Part 4: Main Method with Test Cases
    public static void main(String[] args) {
        DocumentMatchingIndex index = new DocumentMatchingIndex();

        // Sample Documents
        // docA: 'java' appears 2 times
        index.addDocument("docA.txt", "java java spring backend");
        // docB: 'java' appears 1 time
        index.addDocument("docB.txt", "java spring react");
        // docC: 'python' and 'django'
        index.addDocument("docC.txt", "python django framework");
        // alpha & beta: both have 'java' 2 times (same score as docA -> tie-breaker test)
        index.addDocument("beta.txt", "java java microservices");
        index.addDocument("alpha.txt", "java java cloud");
        // Same name modulo case test: 'file.txt' vs 'FILE.txt'
        index.addDocument("file.txt", "java java");
        index.addDocument("FILE.txt", "java java");

        System.out.println("=== Test 1: Search 'java' (Top 3) ===");
        System.out.println(index.search("java", 3));

        System.out.println("\n=== Test 2: Search with Duplicate Query Words 'java java java' (Top 3) ===");
        System.out.println(index.search("java java java", 3));

        System.out.println("\n=== Test 3: Same Name Modulo Case Tie-Breaker ('file.txt' vs 'FILE.txt') ===");
        System.out.println(index.search("java", 5));

        System.out.println("\n=== Test 4: Overwrite / Update Document 'docC.txt' ===");
        index.addDocument("docC.txt", "java java java ultimate");
        System.out.println("Search 'java' after updating docC (docC has score 3, should rank #1):");
        System.out.println(index.search("java", 3));

        System.out.println("\nSearch 'python' (docC was overwritten, should return empty []):");
        System.out.println(index.search("python", 3));

        System.out.println("\n=== Test 5: Edge Cases ===");
        System.out.println("k <= 0: " + index.search("java", 0));
        System.out.println("Non-matching query 'rust': " + index.search("rust", 5));
        System.out.println("Special characters query '!!! @@@': " + index.search("!!! @@@", 5));
        System.out.println("k > matches (k=10 for search 'spring'): " + index.search("spring", 10));
    }
}
