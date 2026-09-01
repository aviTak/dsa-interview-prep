package Progex2;

import java.util.*;

/**
 * Resume Versioning System
 * Step 1: Domain Models (ChangeType, FieldChange, ResumeDiff, ResumeVersion)
 * Step 2: Core Storage & Normalization / Defensive Utilities
 * Step 3: Core CRUD Operations (addVersion, getResume, getLatestResume, deleteVersion, getAllVersions)
 * Step 4: Diffing & Tokenization Engine (tokenizeSkills, areValuesEqual, diff)
 * Step 5: Point-in-time Queries (floorEntry) & Comprehensive Test Verification
 */
public class Resume {

    // ==========================================
    // STEP 1: DOMAIN MODELS
    // ==========================================

    // Enum representing the type of modification between versions
    public enum ChangeType {
        ADDED,
        REMOVED,
        MODIFIED
    }

    // Represents a single field change between two versions
    public static class FieldChange {
        private final ChangeType type;
        private final Object oldValue;
        private final Object newValue;

        public FieldChange(ChangeType type, Object oldValue, Object newValue) {
            this.type = type;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        public ChangeType getType() {
            return type;
        }

        public Object getOldValue() {
            return oldValue;
        }

        public Object getNewValue() {
            return newValue;
        }

        @Override
        public String toString() {
            switch (type) {
                case ADDED:
                    return "[ADDED] -> " + newValue;
                case REMOVED:
                    return "[REMOVED] (was: " + oldValue + ")";
                case MODIFIED:
                    return "[MODIFIED] " + oldValue + " -> " + newValue;
                default:
                    return super.toString();
            }
        }
    }

    // Represents the overall diff between two versions
    public static class ResumeDiff {
        private final String resumeId;
        private final int fromVersion;
        private final int toVersion;
        private final Map<String, FieldChange> changes;

        public ResumeDiff(String resumeId, int fromVersion, int toVersion, Map<String, FieldChange> changes) {
            this.resumeId = resumeId;
            this.fromVersion = fromVersion;
            this.toVersion = toVersion;
            this.changes = changes;
        }

        public String getResumeId() {
            return resumeId;
        }

        public int getFromVersion() {
            return fromVersion;
        }

        public int getToVersion() {
            return toVersion;
        }

        public Map<String, FieldChange> getChanges() {
            return Collections.unmodifiableMap(changes);
        }

        public boolean hasChanges() {
            return !changes.isEmpty();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Diff for '").append(resumeId)
              .append("' (v").append(fromVersion).append(" -> v").append(toVersion).append(") ===\n");
            if (changes.isEmpty()) {
                sb.append("  (No differences found)\n");
            } else {
                for (Map.Entry<String, FieldChange> entry : changes.entrySet()) {
                    sb.append("  - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }
            }
            return sb.toString();
        }
    }

    // Represents a single version snapshot of a resume
    public static class ResumeVersion {
        private final int version;
        private final Map<String, Object> data;
        private final long timestamp;

        public ResumeVersion(int version, Map<String, Object> data) {
            this.version = version;
            // Store an unmodifiable copy to ensure immutability
            this.data = Collections.unmodifiableMap(new HashMap<>(data));
            this.timestamp = System.currentTimeMillis();
        }

        public int getVersion() {
            return version;
        }

        public Map<String, Object> getData() {
            return data;
        }

        public long getTimestamp() {
            return timestamp;
        }

        @Override
        public String toString() {
            return "v" + version + " @ " + new Date(timestamp) + " => " + data;
        }
    }

    // ==========================================
    // STEP 2: CORE STORAGE & DEFENSIVE UTILITIES
    // ==========================================

    // Primary storage: resumeId -> TreeMap of (version -> ResumeVersion)
    private final Map<String, TreeMap<Integer, ResumeVersion>> storage;

    public Resume() {
        this.storage = new HashMap<>();
    }

    /**
     * Normalizes map keys by trimming whitespace and converting to lowercase
     * to avoid casing mismatches (e.g. "Skills" vs "skills").
     */
    private Map<String, Object> normalizeData(Map<String, Object> input) {
        if (input == null) {
            return new HashMap<>();
        }
        Map<String, Object> normalized = new HashMap<>();
        for (Map.Entry<String, Object> entry : input.entrySet()) {
            if (entry.getKey() != null) {
                String normalizedKey = entry.getKey().trim().toLowerCase();
                normalized.put(normalizedKey, entry.getValue());
            }
        }
        return normalized;
    }

    /**
     * Checks if a resume exists in the system with active versions.
     */
    public boolean exists(String resumeId) {
        return resumeId != null && storage.containsKey(resumeId) && !storage.get(resumeId).isEmpty();
    }

    // ==========================================
    // STEP 3: CORE CRUD OPERATIONS
    // ==========================================

    /**
     * Adds a new version of a resume.
     * Auto-increments version number (1 for new resume, max existing + 1 for updates).
     *
     * @param resumeId Unique identifier for the resume.
     * @param data     The map representing resume fields.
     * @return The newly assigned version number.
     */
    public int addVersion(String resumeId, Map<String, Object> data) {
        if (resumeId == null || resumeId.trim().isEmpty()) {
            throw new IllegalArgumentException("resumeId cannot be null or empty");
        }
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }

        Map<String, Object> normalized = normalizeData(data);
        TreeMap<Integer, ResumeVersion> history = storage.computeIfAbsent(resumeId, k -> new TreeMap<>());

        // Auto-generate version: if empty -> 1, otherwise lastKey() + 1
        int nextVersion = history.isEmpty() ? 1 : history.lastKey() + 1;
        ResumeVersion versionSnapshot = new ResumeVersion(nextVersion, normalized);
        history.put(nextVersion, versionSnapshot);

        return nextVersion;
    }

    /**
     * Retrieves the resume data at an exact specific version.
     *
     * @param resumeId Unique identifier for the resume.
     * @param version  The specific version to retrieve.
     * @return An unmodifiable map containing the resume data.
     */
    public Map<String, Object> getResume(String resumeId, int version) {
        if (!exists(resumeId)) {
            throw new NoSuchElementException("Resume not found with ID: " + resumeId);
        }

        TreeMap<Integer, ResumeVersion> history = storage.get(resumeId);
        ResumeVersion resumeVersion = history.get(version);

        if (resumeVersion == null) {
            throw new NoSuchElementException("Version " + version + " not found for resume ID: " + resumeId);
        }

        return resumeVersion.getData();
    }

    /**
     * Retrieves the latest active version of a resume.
     *
     * @param resumeId Unique identifier for the resume.
     * @return An unmodifiable map containing the latest resume data.
     */
    public Map<String, Object> getLatestResume(String resumeId) {
        if (!exists(resumeId)) {
            throw new NoSuchElementException("Resume not found with ID: " + resumeId);
        }

        TreeMap<Integer, ResumeVersion> history = storage.get(resumeId);
        return history.lastEntry().getValue().getData();
    }

    /**
     * Retrieves the latest active version number for a resume.
     *
     * @param resumeId Unique identifier for the resume.
     * @return The latest active version number.
     */
    public int getLatestVersionNumber(String resumeId) {
        if (!exists(resumeId)) {
            throw new NoSuchElementException("Resume not found with ID: " + resumeId);
        }

        return storage.get(resumeId).lastKey();
    }

    /**
     * Retrieves the resume state "as-of" a given version (point-in-time query).
     * Uses floorEntry to find the latest active version <= targetVersion.
     *
     * @param resumeId      Unique identifier for the resume.
     * @param targetVersion The upper bound version number.
     * @return The resume data at the nearest active version <= targetVersion.
     */
    public Map<String, Object> getResumeAsOfVersion(String resumeId, int targetVersion) {
        if (!exists(resumeId)) {
            throw new NoSuchElementException("Resume not found with ID: " + resumeId);
        }

        TreeMap<Integer, ResumeVersion> history = storage.get(resumeId);
        Map.Entry<Integer, ResumeVersion> entry = history.floorEntry(targetVersion);

        if (entry == null) {
            throw new NoSuchElementException("No version found on or before version " + targetVersion + " for resume: " + resumeId);
        }

        return entry.getValue().getData();
    }

    /**
     * Deletes a specific version of a resume.
     * If all versions for a resume are deleted, removes the resume entry entirely.
     *
     * @param resumeId Unique identifier for the resume.
     * @param version  The version to delete.
     * @return True if the version was found and deleted, false otherwise.
     */
    public boolean deleteVersion(String resumeId, int version) {
        if (!exists(resumeId)) {
            return false;
        }

        TreeMap<Integer, ResumeVersion> history = storage.get(resumeId);
        ResumeVersion removed = history.remove(version);

        if (removed != null) {
            if (history.isEmpty()) {
                storage.remove(resumeId);
            }
            return true;
        }

        return false;
    }

    /**
     * Returns all active version numbers for a given resume in ascending sorted order.
     *
     * @param resumeId Unique identifier for the resume.
     * @return A sorted list of available version numbers.
     */
    public List<Integer> getAllVersions(String resumeId) {
        if (!exists(resumeId)) {
            return Collections.emptyList();
        }

        return new ArrayList<>(storage.get(resumeId).keySet());
    }

    // ==========================================
    // STEP 4: DIFFING & TOKENIZATION ENGINE
    // ==========================================

    /**
     * Tokenizes multi-value fields (e.g. skills) into a Set of strings.
     * Splits by standard delimiters (',', ';', '|', '\n') while preserving
     * tech keywords with special characters like "C++", "C#", "Node.js", ".NET".
     */
    public static Set<String> tokenizeSkills(Object value) {
        Set<String> tokens = new HashSet<>();
        if (value == null) {
            return tokens;
        }

        if (value instanceof Collection<?>) {
            for (Object item : (Collection<?>) value) {
                if (item != null) {
                    String s = item.toString().trim().toLowerCase();
                    if (!s.isEmpty()) {
                        tokens.add(s);
                    }
                }
            }
        } else if (value instanceof String) {
            String str = (String) value;
            for (String part : str.split("[,;|\\n]+")) {
                String s = part.trim().toLowerCase();
                if (!s.isEmpty()) {
                    tokens.add(s);
                }
            }
        } else {
            tokens.add(value.toString().trim().toLowerCase());
        }

        return tokens;
    }

    /**
     * Checks if two values for a given field are logically equivalent.
     * For skill-related fields, performs order-independent set comparison.
     * For general strings, performs case-insensitive trimmed comparison.
     */
    private boolean areValuesEqual(String key, Object val1, Object val2) {
        if (val1 == null && val2 == null) {
            return true;
        }
        if (val1 == null || val2 == null) {
            return false;
        }

        // Order-independent token set comparison for skills
        if (key.contains("skill")) {
            Set<String> tokens1 = tokenizeSkills(val1);
            Set<String> tokens2 = tokenizeSkills(val2);
            return tokens1.equals(tokens2);
        }

        // String comparison with trim
        if (val1 instanceof String && val2 instanceof String) {
            return ((String) val1).trim().equalsIgnoreCase(((String) val2).trim());
        }

        // Fallback to standard object equality
        return Objects.equals(val1, val2);
    }

    /**
     * Computes the diff between two versions of a resume.
     *
     * @param resumeId    Unique identifier for the resume.
     * @param fromVersion The baseline version number.
     * @param toVersion   The target version number to compare against.
     * @return A ResumeDiff containing all added, removed, and modified fields.
     */
    public ResumeDiff diff(String resumeId, int fromVersion, int toVersion) {
        Map<String, Object> fromMap = getResume(resumeId, fromVersion);
        Map<String, Object> toMap = getResume(resumeId, toVersion);

        Map<String, FieldChange> changes = new LinkedHashMap<>();

        // Collect union of all keys, sorted alphabetically
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(fromMap.keySet());
        allKeys.addAll(toMap.keySet());

        for (String key : allKeys) {
            boolean inFrom = fromMap.containsKey(key);
            boolean inTo = toMap.containsKey(key);

            if (!inFrom && inTo) {
                // Field was added in toVersion
                changes.put(key, new FieldChange(ChangeType.ADDED, null, toMap.get(key)));
            } else if (inFrom && !inTo) {
                // Field was removed in toVersion
                changes.put(key, new FieldChange(ChangeType.REMOVED, fromMap.get(key), null));
            } else {
                // Field exists in both -> check if values changed
                Object val1 = fromMap.get(key);
                Object val2 = toMap.get(key);

                if (!areValuesEqual(key, val1, val2)) {
                    changes.put(key, new FieldChange(ChangeType.MODIFIED, val1, val2));
                }
            }
        }

        return new ResumeDiff(resumeId, fromVersion, toVersion, changes);
    }

    // ==========================================
    // STEP 5: TEST DRIVER / VERIFICATION
    // ==========================================

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   RESUME VERSIONING SYSTEM - TEST VERIFICATION   ");
        System.out.println("==================================================\n");

        Resume system = new Resume();

        // ----------------------------------------------------
        // Test Case 1: Create Initial Resume (v1)
        // ----------------------------------------------------
        System.out.println(">>> 1. Creating Initial Resume (v1 for user_101)");
        Map<String, Object> v1 = new HashMap<>();
        v1.put("Name", "Alex Chen");
        v1.put("Title", "Software Engineer");
        v1.put("Skills", "Java, Spring Boot, MySQL");
        v1.put("Location", "San Francisco, CA");
        v1.put("Experience_Years", 2);

        int v1Num = system.addVersion("user_101", v1);
        System.out.println("Created version: v" + v1Num);
        System.out.println("Snapshot: " + system.getResume("user_101", v1Num) + "\n");

        // ----------------------------------------------------
        // Test Case 2: Update Resume (v2)
        // ----------------------------------------------------
        System.out.println(">>> 2. Adding Update (v2 for user_101)");
        Map<String, Object> v2 = new HashMap<>();
        v2.put("name", "Alex Chen");
        v2.put("title", "Senior Software Engineer");
        // Same skills in different order + Docker added
        v2.put("skills", "MySQL, Java, Spring Boot, Docker");
        v2.put("location", "San Francisco, CA");
        v2.put("experience_years", 4);
        v2.put("linkedin", "linkedin.com/in/alexchen");

        int v2Num = system.addVersion("user_101", v2);
        System.out.println("Created version: v" + v2Num);
        System.out.println("Snapshot: " + system.getResume("user_101", v2Num) + "\n");

        // ----------------------------------------------------
        // Test Case 3: Update Resume (v3)
        // ----------------------------------------------------
        System.out.println(">>> 3. Adding Update (v3 for user_101)");
        Map<String, Object> v3 = new HashMap<>();
        v3.put("name", "Alex Chen");
        v3.put("title", "Staff Engineer / Tech Lead");
        v3.put("skills", "Java, Docker, Kubernetes, AWS, System Design");
        // Removed location, updated experience
        v3.put("experience_years", 7);
        v3.put("linkedin", "linkedin.com/in/alexchen");

        int v3Num = system.addVersion("user_101", v3);
        System.out.println("Created version: v" + v3Num);
        System.out.println("Active versions: " + system.getAllVersions("user_101"));
        System.out.println("Latest version number: " + system.getLatestVersionNumber("user_101") + "\n");

        // ----------------------------------------------------
        // Test Case 4: Diffing Between Versions
        // ----------------------------------------------------
        System.out.println(">>> 4. Testing Diffing Engine");
        ResumeDiff diffV1toV2 = system.diff("user_101", 1, 2);
        System.out.println(diffV1toV2);

        ResumeDiff diffV1toV3 = system.diff("user_101", 1, 3);
        System.out.println(diffV1toV3);

        ResumeDiff diffSameVersion = system.diff("user_101", 2, 2);
        System.out.println(diffSameVersion);

        // ----------------------------------------------------
        // Test Case 5: Deleting a Middle Version (v2)
        // ----------------------------------------------------
        System.out.println(">>> 5. Deleting Middle Version (v2)");
        boolean deletedV2 = system.deleteVersion("user_101", 2);
        System.out.println("v2 deleted: " + deletedV2);
        System.out.println("Remaining active versions: " + system.getAllVersions("user_101"));
        System.out.println("Latest active version is still: v" + system.getLatestVersionNumber("user_101") + "\n");

        // ----------------------------------------------------
        // Test Case 6: Point-in-time (floorEntry) Query
        // ----------------------------------------------------
        System.out.println(">>> 6. Point-in-Time Query (as-of v2 after v2 was deleted)");
        Map<String, Object> asOfV2 = system.getResumeAsOfVersion("user_101", 2);
        System.out.println("As-of v2 (falls back to v1): " + asOfV2 + "\n");

        // ----------------------------------------------------
        // Test Case 7: Adding Version After Deletion (Auto-Increment to v4)
        // ----------------------------------------------------
        System.out.println(">>> 7. Adding new version after deletion (should become v4)");
        Map<String, Object> v4 = new HashMap<>(v3);
        v4.put("title", "VP of Engineering");
        int v4Num = system.addVersion("user_101", v4);
        System.out.println("Created version: v" + v4Num);
        System.out.println("Active versions now: " + system.getAllVersions("user_101") + "\n");

        // ----------------------------------------------------
        // Test Case 8: Error Handling & Edge Cases
        // ----------------------------------------------------
        System.out.println(">>> 8. Error Handling Verification");
        try {
            system.getResume("user_101", 2); // was deleted
        } catch (NoSuchElementException e) {
            System.out.println("Expected Exception for deleted version: " + e.getMessage());
        }

        try {
            system.getResume("non_existent_user", 1);
        } catch (NoSuchElementException e) {
            System.out.println("Expected Exception for unknown user: " + e.getMessage());
        }

        System.out.println("\nAll Steps and Tests Completed Successfully!");
    }
}
