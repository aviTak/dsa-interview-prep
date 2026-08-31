import java.util.*;
import java.util.Objects;

// Event class to hold event data
class Event {
    String eventId;
    String applicantId;
    String jobId;
    String eventType;
    long timestamp;
    
    Event(String eventId, String applicantId, String jobId, String eventType, long timestamp) {
        this.eventId = eventId;
        this.applicantId = applicantId;
        this.jobId = jobId;
        this.eventType = eventType;
        this.timestamp = timestamp;
    }
}

// Pair record for applicantId-jobId key
record ApplicantJobPair(String applicantId, String jobId) {}

public class ApplicationEventPipeline {
    
    // ===== STEP 1: Data Structures & Initialization =====
    
    // Valid state transitions mapping
    private static final Map<String, Set<String>> VALID_TRANSITIONS = new HashMap<>();
    
    // Storage structures
    private Map<ApplicantJobPair, List<Event>> events;  // Key: ApplicantJobPair, Value: List of events
    private Set<String> processedEventIds;              // Track processed events for deduplication
    private Map<String, Set<String>> jobApplicants;     // Key: jobId, Value: Set of unique applicantIds
    
    // Static block to initialize valid transitions
    static {
        VALID_TRANSITIONS.put("APPLIED", new HashSet<>(Arrays.asList("VIEWED", "WITHDRAWN")));
        VALID_TRANSITIONS.put("VIEWED", new HashSet<>(Arrays.asList("SHORTLISTED", "REJECTED", "WITHDRAWN")));
        VALID_TRANSITIONS.put("SHORTLISTED", new HashSet<>(Arrays.asList("HIRED", "REJECTED", "WITHDRAWN")));
        VALID_TRANSITIONS.put("REJECTED", new HashSet<>());    // Terminal state
        VALID_TRANSITIONS.put("HIRED", new HashSet<>());       // Terminal state
        VALID_TRANSITIONS.put("WITHDRAWN", new HashSet<>());   // Terminal state
    }
    
    // Constructor
    public ApplicationEventPipeline() {
        this.events = new HashMap<>();
        this.processedEventIds = new HashSet<>();
        this.jobApplicants = new HashMap<>();
    }
    
    // ===== Required Methods =====
    
    // Helper method: Replay transitions and return valid event history
    private List<String> replayTransitions(String applicantId, String jobId) {
        ApplicantJobPair pair = new ApplicantJobPair(applicantId, jobId);
        
        // If no events exist, return empty list
        if (!events.containsKey(pair)) {
            return new ArrayList<>();
        }
        
        // Events are already sorted by timestamp on insertion
        List<Event> eventList = events.get(pair);
        
        // Replay through state machine
        List<String> validHistory = new ArrayList<>();
        String currentState = null;
        
        for (Event event : eventList) {
            // Check if this is a valid transition
            boolean isValidTransition = false;
            
            if (currentState == null) {
                // First event must be APPLIED
                isValidTransition = "APPLIED".equals(event.eventType);
            } else {
                // Check if transition is valid from current state
                Set<String> validNextStates = VALID_TRANSITIONS.get(currentState);
                isValidTransition = validNextStates != null && validNextStates.contains(event.eventType);
            }
            
            // If valid transition, update state and add to history
            if (isValidTransition) {
                currentState = event.eventType;
                validHistory.add(event.eventType);
            }
            // If invalid transition, skip it (don't add to history)
        }
        
        return validHistory;
    }
    
    public void processEvent(String eventId, String applicantId, String jobId, String eventType, long timestamp) {
        // Handle null/empty inputs
        if (eventId == null || eventId.isEmpty() || 
            applicantId == null || applicantId.isEmpty() || 
            jobId == null || jobId.isEmpty() || 
            eventType == null || eventType.isEmpty()) {
            return;
        }
        
        // Check if eventId already processed (deduplication)
        if (processedEventIds.contains(eventId)) {
            return;
        }
        
        // Create event and store it
        Event event = new Event(eventId, applicantId, jobId, eventType, timestamp);
        ApplicantJobPair pair = new ApplicantJobPair(applicantId, jobId);
        
        // Add to events map, maintaining sorted order by timestamp
        events.putIfAbsent(pair, new ArrayList<>());
        List<Event> eventList = events.get(pair);
        
        // Insert in sorted order by timestamp, with eventId as tie-breaker
        int insertPos = 0;
        for (int i = 0; i < eventList.size(); i++) {
            Event current = eventList.get(i);
            if (current.timestamp > timestamp || 
                (current.timestamp == timestamp && current.eventId.compareTo(eventId) > 0)) {
                insertPos = i;
                break;
            }
            insertPos = i + 1;
        }
        eventList.add(insertPos, event);
        
        // Mark eventId as processed
        processedEventIds.add(eventId);
        
        // Update jobApplicants tracking
        jobApplicants.putIfAbsent(jobId, new HashSet<>());
        jobApplicants.get(jobId).add(applicantId);
    }
    
    public String getStatus(String applicantId, String jobId) {
        // Handle null/empty inputs
        if (applicantId == null || applicantId.isEmpty() || jobId == null || jobId.isEmpty()) {
            return null;
        }
        
        // Get valid event history
        List<String> validHistory = replayTransitions(applicantId, jobId);
        
        // Return the last (current) state, or null if no history
        if (validHistory.isEmpty()) {
            return null;
        }
        
        return validHistory.get(validHistory.size() - 1);
    }
    
    public List<String> getEventHistory(String applicantId, String jobId) {
        // Handle null/empty inputs
        if (applicantId == null || applicantId.isEmpty() || jobId == null || jobId.isEmpty()) {
            return new ArrayList<>();
        }
        
        ApplicantJobPair pair = new ApplicantJobPair(applicantId, jobId);
        
        // If no events exist, return empty list
        if (!events.containsKey(pair)) {
            return new ArrayList<>();
        }
        
        // Events are already sorted by timestamp on insertion
        List<Event> eventList = events.get(pair);
        
        // Return all event types in timestamp order
        List<String> history = new ArrayList<>();
        for (Event event : eventList) {
            history.add(event.eventType);
        }
        
        return history;
    }
    
    public Map<String, Integer> getFunnelCounts(String jobId) {
        // Handle null/empty inputs
        if (jobId == null || jobId.isEmpty()) {
            return new HashMap<>();
        }
        
        Map<String, Integer> funnelCounts = new HashMap<>();
        
        // Get all applicants for this job
        Set<String> applicantsForJob = jobApplicants.get(jobId);
        if (applicantsForJob == null || applicantsForJob.isEmpty()) {
            return funnelCounts;
        }
        
        // For each applicant, replay their events and count first occurrence of each event type
        for (String applicantId : applicantsForJob) {
            List<String> validHistory = replayTransitions(applicantId, jobId);
            
            // Track which event types we've counted for this applicant (only count first occurrence)
            Set<String> countedForApplicant = new HashSet<>();
            
            for (String eventType : validHistory) {
                if (!countedForApplicant.contains(eventType)) {
                    funnelCounts.put(eventType, funnelCounts.getOrDefault(eventType, 0) + 1);
                    countedForApplicant.add(eventType);
                }
            }
        }
        
        return funnelCounts;
    }
    
    public List<String> getTopJobsByApplicants(int n) {
        // Handle edge cases
        if (n <= 0 || jobApplicants == null || jobApplicants.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Use min heap to track top n jobs by applicant count, with jobId as tie-breaker
        PriorityQueue<String> minHeap = new PriorityQueue<>((a, b) -> {
            // Primary: compare applicant count
            int countA = jobApplicants.get(a).size();
            int countB = jobApplicants.get(b).size();
            int countCmp = Integer.compare(countA, countB);
            if (countCmp != 0) return countCmp;
            
            // Tie-breaker: use jobId lexicographically (reverse so earlier jobId survives)
            return b.compareTo(a);
        });
        
        // Process each job
        for (String jobId : jobApplicants.keySet()) {
            minHeap.offer(jobId);
            
            if (minHeap.size() > n) {
                minHeap.poll();
            }
        }
        
        // Extract from heap by draining (smallest to largest)
        List<String> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll());
        }
        Collections.reverse(result);
        
        return result;
    }
    
    public static void main(String[] args) {
        ApplicationEventPipeline pipeline = new ApplicationEventPipeline();
        
        // Test 1: Process events for applicant1 on job1
        pipeline.processEvent("ev1", "alice", "job1", "APPLIED", 100);
        pipeline.processEvent("ev2", "alice", "job1", "VIEWED", 200);
        pipeline.processEvent("ev3", "alice", "job1", "SHORTLISTED", 300);
        pipeline.processEvent("ev4", "alice", "job1", "HIRED", 400);
        
        // Test 2: Out-of-order events for applicant2 on job1
        pipeline.processEvent("ev5", "bob", "job1", "VIEWED", 150);
        pipeline.processEvent("ev6", "bob", "job1", "APPLIED", 50);   // Arrives late
        pipeline.processEvent("ev7", "bob", "job1", "REJECTED", 250);
        
        // Test 3: Another applicant and job
        pipeline.processEvent("ev8", "charlie", "job2", "APPLIED", 100);
        pipeline.processEvent("ev9", "charlie", "job2", "VIEWED", 200);
        pipeline.processEvent("ev10", "charlie", "job2", "WITHDRAWN", 300);
        
        // Test 4: Duplicate event (should be ignored)
        pipeline.processEvent("ev1", "alice", "job1", "VIEWED", 150);
        
        // Test getStatus
        System.out.println("Alice status on job1: " + pipeline.getStatus("alice", "job1"));
        System.out.println("Bob status on job1: " + pipeline.getStatus("bob", "job1"));
        System.out.println("Charlie status on job2: " + pipeline.getStatus("charlie", "job2"));
        
        // Test getEventHistory
        System.out.println("\nAlice event history on job1: " + pipeline.getEventHistory("alice", "job1"));
        System.out.println("Bob event history on job1: " + pipeline.getEventHistory("bob", "job1"));
        
        // Test getFunnelCounts
        System.out.println("\nFunnel counts for job1: " + pipeline.getFunnelCounts("job1"));
        System.out.println("Funnel counts for job2: " + pipeline.getFunnelCounts("job2"));
        
        // Test getTopJobsByApplicants
        System.out.println("\nTop 2 jobs by applicants: " + pipeline.getTopJobsByApplicants(2));
    }
}
