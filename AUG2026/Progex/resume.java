import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Step 1: Resume type
// Just wraps the field data for one snapshot. No id, no versionId —
// identity/versioning is handled entirely by the manager (resumeId -> list index).
record Resume(Map<String, String> fields) {}

// Step 2: manager skeleton + core state
// resumeId -> ordered list of snapshots. 1-indexed: index (i-1) holds versionId i.
class ResumeVersionManager {

    private final Map<String, ArrayList<Resume>> store = new HashMap<>();

    // Step 3: addVersion
    // Append-only: new snapshot goes at the end. size() after adding == its versionId.
    public int addVersion(String resumeId, Resume resume) {
        ArrayList<Resume> versions = store.computeIfAbsent(resumeId, k -> new ArrayList<>());
        versions.add(resume);
        return versions.size();
    }

    // Step 4: getVersion
    // Returns null on unknown resumeId or out-of-range versionId — never throws.
    public Resume getVersion(String resumeId, int versionId) {
        ArrayList<Resume> versions = store.get(resumeId);
        if (versions == null || versionId < 1 || versionId > versions.size()) {
            return null;
        }
        return versions.get(versionId - 1);
    }

    // Step 5: getLatest
    // Returns null for unknown resumeId (empty lists never occur: addVersion is the
    // only way a list gets created, and it always adds before returning).
    public Resume getLatest(String resumeId) {
        ArrayList<Resume> versions = store.get(resumeId);
        if (versions == null || versions.isEmpty()) {
            return null;
        }
        return versions.get(versions.size() - 1);
    }

    // Step 6: listVersions
    // Version ids are always the contiguous range [1, size]. Materialized eagerly.
    public List<Integer> listVersions(String resumeId) {
        ArrayList<Resume> versions = store.get(resumeId);
        if (versions == null || versions.isEmpty()) {
            return Collections.emptyList();
        }
        List<Integer> ids = new ArrayList<>(versions.size());
        for (int i = 1; i <= versions.size(); i++) {
            ids.add(i);
        }
        return ids;
    }
}

// Step 7: sample data + manual test walkthrough
class Main {
    public static void main(String[] args) {
        ResumeVersionManager manager = new ResumeVersionManager();

        // --- r1: add 3 versions ---
        int v1 = manager.addVersion("r1", new Resume(Map.of("name", "Alice", "role", "Intern")));
        int v2 = manager.addVersion("r1", new Resume(Map.of("name", "Alice", "role", "SWE1")));
        int v3 = manager.addVersion("r1", new Resume(Map.of("name", "Alice", "role", "SWE2")));
        System.out.println("r1 versionIds returned: " + v1 + ", " + v2 + ", " + v3); // expect 1, 2, 3

        // --- r2: add 1 version ---
        int r2v1 = manager.addVersion("r2", new Resume(Map.of("name", "Bob", "role", "Manager")));
        System.out.println("r2 versionId returned: " + r2v1); // expect 1

        // --- getVersion: valid lookups ---
        System.out.println("r1 v1 -> " + manager.getVersion("r1", 1)); // Intern
        System.out.println("r1 v3 -> " + manager.getVersion("r1", 3)); // SWE2

        // --- getVersion: invalid lookups ---
        System.out.println("r1 v99 (out of range) -> " + manager.getVersion("r1", 99)); // null
        System.out.println("r1 v0 (below range) -> " + manager.getVersion("r1", 0));     // null
        System.out.println("unknown resumeId -> " + manager.getVersion("nope", 1));      // null

        // --- getLatest ---
        System.out.println("r1 latest -> " + manager.getLatest("r1"));   // SWE2
        System.out.println("r2 latest -> " + manager.getLatest("r2"));   // Manager
        System.out.println("unknown latest -> " + manager.getLatest("nope")); // null

        // --- listVersions ---
        System.out.println("r1 versions -> " + manager.listVersions("r1"));       // [1, 2, 3]
        System.out.println("r2 versions -> " + manager.listVersions("r2"));       // [1]
        System.out.println("unknown versions -> " + manager.listVersions("nope")); // []

        // --- sanity: history is append-only, older versions unaffected by later adds ---
        System.out.println("r1 v1 still Intern -> " + manager.getVersion("r1", 1));
    }
}
