import java.util.*;

enum ApplicationState {
    APPLIED,
    VIEWED,
    SHORTLISTED,
    HIRED,
    REJECTED,
    WITHDRAWN
}

class Event {
    private final String eventId;
    private final String applicantId;
    private final String jobId;
    private final ApplicationState state;
    private final long timestamp;

    public Event(String eventId, String applicantId, String jobId, ApplicationState state, long timestamp) {
        this.eventId = eventId;
        this.applicantId = applicantId;
        this.jobId = jobId;
        this.state = state;
        this.timestamp = timestamp;
    }

    public String getEventId() {
        return eventId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public String getJobId() {
        return jobId;
    }

    public ApplicationState getState() {
        return state;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("Event{id='%s', applicant='%s', job='%s', state=%s, time=%d}",
                eventId, applicantId, jobId, state, timestamp);
    }
}

class JobApplicationEventStream {

    // Transition Rules definition using Sets
    private static final Map<ApplicationState, Set<ApplicationState>> VALID_TRANSITIONS = new EnumMap<>(ApplicationState.class);
    private static final Set<ApplicationState> TERMINAL_STATES = EnumSet.of(
            ApplicationState.HIRED,
            ApplicationState.REJECTED,
            ApplicationState.WITHDRAWN
    );

    static {
        // 1. APPLIED can only transition to VIEWED or WITHDRAWN
        VALID_TRANSITIONS.put(ApplicationState.APPLIED, EnumSet.of(ApplicationState.VIEWED, ApplicationState.WITHDRAWN));

        // 2. VIEWED can only transition to SHORTLISTED, REJECTED, or WITHDRAWN
        VALID_TRANSITIONS.put(ApplicationState.VIEWED, EnumSet.of(ApplicationState.SHORTLISTED, ApplicationState.REJECTED, ApplicationState.WITHDRAWN));

        // 3. SHORTLISTED can only transition to HIRED, REJECTED, or WITHDRAWN
        VALID_TRANSITIONS.put(ApplicationState.SHORTLISTED, EnumSet.of(ApplicationState.HIRED, ApplicationState.REJECTED, ApplicationState.WITHDRAWN));

        // Terminal states cannot transition further
        VALID_TRANSITIONS.put(ApplicationState.HIRED, Collections.emptySet());
        VALID_TRANSITIONS.put(ApplicationState.REJECTED, Collections.emptySet());
        VALID_TRANSITIONS.put(ApplicationState.WITHDRAWN, Collections.emptySet());
    }

    // 1. Deduplication set for event IDs
    private final Set<String> processedEventIds = new HashSet<>();

    // 2. Event Store: applicantId -> jobId -> list of Events sorted by timestamp
    private final Map<String, Map<String, List<Event>>> applicantJobEvents = new HashMap<>();

    /**
     * Process an incoming event:
     * - Deduplicates by eventId
     * - Inserts event into the chronological position in O(N) using binary search insertion
     */
    public boolean processEvent(Event event) {
        if (event == null || event.getEventId() == null) {
            return false;
        }

        // Deduplication check
        if (!processedEventIds.add(event.getEventId())) {
            // Already processed this eventId
            return false;
        }

        applicantJobEvents
                .computeIfAbsent(event.getApplicantId(), k -> new HashMap<>())
                .computeIfAbsent(event.getJobId(), k -> new ArrayList<>());

        List<Event> timeline = applicantJobEvents.get(event.getApplicantId()).get(event.getJobId());

        // Insert into chronological position in O(N) using binary search
        int idx = Collections.binarySearch(timeline, event, Comparator.comparingLong(Event::getTimestamp));
        if (idx < 0) {
            idx = -(idx + 1);
        }
        timeline.add(idx, event);

        return true;
    }

    /**
     * Returns the full history of candidate's job actions (both valid and invalid)
     * sorted chronologically by timestamp.
     */
    public List<Event> getHistory(String applicantId, String jobId) {
        if (!applicantJobEvents.containsKey(applicantId) ||
            !applicantJobEvents.get(applicantId).containsKey(jobId)) {
            return List.of();
        }
        return List.copyOf(applicantJobEvents.get(applicantId).get(jobId));
    }

    /**
     * Computes the current valid state of an applicant for a job id.
     * Replays the sorted timeline:
     * - Must start with APPLIED
     * - Discards invalid jumps or events after terminal states
     */
    public ApplicationState getState(String applicantId, String jobId) {
        List<Event> history = getHistory(applicantId, jobId);
        if (history.isEmpty()) {
            return null;
        }

        ApplicationState currentState = null;

        for (Event event : history) {
            ApplicationState nextState = event.getState();

            if (currentState == null) {
                // Application must start with APPLIED
                if (nextState == ApplicationState.APPLIED) {
                    currentState = ApplicationState.APPLIED;
                }
                // Events before APPLIED are invalid and ignored
            } else if (TERMINAL_STATES.contains(currentState)) {
                // Terminal state reached: any further forwarded actions are ignored
                break;
            } else {
                // Check if transition is valid
                Set<ApplicationState> allowedTransitions = VALID_TRANSITIONS.get(currentState);
                if (allowedTransitions != null && allowedTransitions.contains(nextState)) {
                    currentState = nextState;
                }
                // Invalid transitions are skipped / ignored
            }
        }

        return currentState;
    }

    /**
     * Returns count of valid jobs per state for a given applicant.
     */
    public Map<ApplicationState, Integer> getApplicantJobCountsByState(String applicantId) {
        Map<ApplicationState, Integer> stateCounts = new EnumMap<>(ApplicationState.class);
        for (ApplicationState state : ApplicationState.values()) {
            stateCounts.put(state, 0);
        }

        if (!applicantJobEvents.containsKey(applicantId)) {
            return stateCounts;
        }

        Map<String, List<Event>> jobMap = applicantJobEvents.get(applicantId);
        for (String jobId : jobMap.keySet()) {
            ApplicationState validState = getState(applicantId, jobId);
            if (validState != null) {
                stateCounts.put(validState, stateCounts.get(validState) + 1);
            }
        }

        return stateCounts;
    }

    /**
     * Returns which job IDs are in each valid state for a given applicant.
     */
    public Map<ApplicationState, List<String>> getApplicantJobIdsByState(String applicantId) {
        Map<ApplicationState, List<String>> stateJobs = new EnumMap<>(ApplicationState.class);
        for (ApplicationState state : ApplicationState.values()) {
            stateJobs.put(state, new ArrayList<>());
        }

        if (!applicantJobEvents.containsKey(applicantId)) {
            return stateJobs;
        }

        Map<String, List<Event>> jobMap = applicantJobEvents.get(applicantId);
        for (String jobId : jobMap.keySet()) {
            ApplicationState validState = getState(applicantId, jobId);
            if (validState != null) {
                stateJobs.get(validState).add(jobId);
            }
        }

        return stateJobs;
    }

    /**
     * Returns top K jobs applied by valid applicants using a Min-Heap.
     * Only valid applications that initiated with APPLIED are counted.
     */
    public List<String> getTopKAppliedJobs(int k) {
        if (k <= 0) {
            return Collections.emptyList();
        }

        // Count valid applications per jobId
        Map<String, Integer> jobApplicationCount = new HashMap<>();

        for (Map.Entry<String, Map<String, List<Event>>> applicantEntry : applicantJobEvents.entrySet()) {
            String applicantId = applicantEntry.getKey();
            Map<String, List<Event>> jobs = applicantEntry.getValue();

            for (String jobId : jobs.keySet()) {
                // Check if this application has a valid state (meaning it started with APPLIED)
                if (getState(applicantId, jobId) != null) {
                    jobApplicationCount.put(jobId, jobApplicationCount.getOrDefault(jobId, 0) + 1);
                }
            }
        }

        if (jobApplicationCount.isEmpty()) {
            return Collections.emptyList();
        }

        // Min-Heap of size K: orders by count ascending; ties broken lexicographically descending
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(
                (a, b) -> {
                    if (!a.getValue().equals(b.getValue())) {
                        return Integer.compare(a.getValue(), b.getValue());
                    }
                    return b.getKey().compareTo(a.getKey());
                }
        );

        for (Map.Entry<String, Integer> entry : jobApplicationCount.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        // Extract from Min-Heap into sorted list (descending order)
        List<String> topKList = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            topKList.add(minHeap.poll().getKey());
        }
        Collections.reverse(topKList);

        return topKList;
    }
}

public class stream {
    public static void main(String[] args) {
        JobApplicationEventStream stream = new JobApplicationEventStream();

        System.out.println("=== 1. Testing Out-of-Order Events & Deduplication ===");
        // Events for Applicant A1 on Job J1 coming out of order:
        // t=10: SHORTLISTED
        // t=1:  APPLIED
        // t=5:  VIEWED
        // t=5:  VIEWED (duplicate event ID)
        // t=12: HIRED
        // t=15: WITHDRAWN (invalid because already HIRED terminal)

        stream.processEvent(new Event("e3", "A1", "J1", ApplicationState.SHORTLISTED, 10));
        stream.processEvent(new Event("e1", "A1", "J1", ApplicationState.APPLIED, 1));
        stream.processEvent(new Event("e2", "A1", "J1", ApplicationState.VIEWED, 5));
        boolean dupResult = stream.processEvent(new Event("e2", "A1", "J1", ApplicationState.VIEWED, 5)); // Duplicate
        stream.processEvent(new Event("e4", "A1", "J1", ApplicationState.HIRED, 12));
        stream.processEvent(new Event("e5", "A1", "J1", ApplicationState.WITHDRAWN, 15)); // After terminal

        System.out.println("Duplicate e2 accepted? " + dupResult + " (Expected: false)");

        System.out.println("\nFull Raw History for A1 on J1 (Sorted by timestamp):");
        for (Event e : stream.getHistory("A1", "J1")) {
            System.out.println("  " + e);
        }

        System.out.println("\nResolved Valid State for A1 on J1: " + stream.getState("A1", "J1") + " (Expected: HIRED)");

        System.out.println("\n=== 2. Testing Invalid Transitions ===");
        // Applicant A1 on Job J2: APPLIED -> HIRED (Direct jump, invalid)
        stream.processEvent(new Event("e6", "A1", "J2", ApplicationState.APPLIED, 1));
        stream.processEvent(new Event("e7", "A1", "J2", ApplicationState.HIRED, 3)); // Invalid transition
        System.out.println("Resolved Valid State for A1 on J2: " + stream.getState("A1", "J2") + " (Expected: APPLIED)");

        // Applicant A1 on Job J3: VIEWED without APPLIED
        stream.processEvent(new Event("e8", "A1", "J3", ApplicationState.VIEWED, 1));
        System.out.println("Resolved Valid State for A1 on J3: " + stream.getState("A1", "J3") + " (Expected: null)");

        // Applicant A2 on Job J1
        stream.processEvent(new Event("e9", "A2", "J1", ApplicationState.APPLIED, 2));
        stream.processEvent(new Event("e10", "A2", "J1", ApplicationState.VIEWED, 4));
        stream.processEvent(new Event("e11", "A2", "J1", ApplicationState.REJECTED, 6));

        // Applicant A3 on Job J1
        stream.processEvent(new Event("e12", "A3", "J1", ApplicationState.APPLIED, 1));

        // Applicant A3 on Job J2
        stream.processEvent(new Event("e13", "A3", "J2", ApplicationState.APPLIED, 1));
        stream.processEvent(new Event("e14", "A3", "J2", ApplicationState.WITHDRAWN, 2));

        System.out.println("\n=== 3. Testing Applicant Job Counts & Job IDs by State ===");
        System.out.println("A1 Job counts by state: " + stream.getApplicantJobCountsByState("A1"));
        System.out.println("A1 Job IDs by state: " + stream.getApplicantJobIdsByState("A1"));

        System.out.println("\n=== 4. Testing Top K Applied Jobs (Min-Heap) ===");
        // Valid applications count:
        // J1: A1 (valid), A2 (valid), A3 (valid) -> count = 3
        // J2: A1 (valid), A3 (valid)             -> count = 2
        // J3: none (A1 had no APPLIED)           -> count = 0
        List<String> topJobs = stream.getTopKAppliedJobs(2);
        System.out.println("Top 2 Applied Jobs: " + topJobs + " (Expected: [J1, J2])");
    }
}
