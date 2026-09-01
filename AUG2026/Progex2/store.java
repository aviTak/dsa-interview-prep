import java.util.*;

/**
 * SegmentTreeStore: An in-memory key-value store for dense/contiguous ID ranges (0 to capacity - 1)
 * backed by a Segment Tree for logarithmic range and boundary queries.
 */
public class store {

    public static class SegmentTreeStore {
        private final int capacity;
        private final String[] values;
        private final int[] tree; // tree[node] = count of active IDs in the range represented by node

        public SegmentTreeStore(int capacity) {
            if (capacity <= 0) {
                throw new IllegalArgumentException("Capacity must be positive");
            }
            this.capacity = capacity;
            this.values = new String[capacity];
            // Segment tree array size is 4 * capacity
            this.tree = new int[4 * capacity];
        }

        /**
         * Point update in Segment Tree: O(log N)
         * delta is +1 when adding an ID, or -1 when deleting an ID.
         */
        private void update(int node, int start, int end, int targetId, int delta) {
            if (start == end) {
                tree[node] += delta;
                return;
            }

            int mid = start + (end - start) / 2;
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;

            if (targetId <= mid) {
                update(leftChild, start, mid, targetId, delta);
            } else {
                update(rightChild, mid + 1, end, targetId, delta);
            }

            tree[node] = tree[leftChild] + tree[rightChild];
        }

        /**
         * Inserts or updates an ID with a value.
         * Time Complexity: O(log N) if new ID, O(1) if updating existing value.
         */
        public void put(int id, String value) {
            checkBounds(id);
            if (values[id] == null) {
                // New ID being inserted: activate in segment tree
                update(1, 0, capacity - 1, id, +1);
            }
            values[id] = value;
        }

        /**
         * Deletes an ID.
         * Time Complexity: O(log N)
         * Returns the deleted value, or null if ID was not present.
         */
        public String delete(int id) {
            if (id < 0 || id >= capacity || values[id] == null) {
                return null;
            }

            String oldVal = values[id];
            values[id] = null;
            // Deactivate in segment tree
            update(1, 0, capacity - 1, id, -1);
            return oldVal;
        }

        /**
         * Retrieves the value of an ID.
         * Time Complexity: O(1)
         */
        public String get(int id) {
            if (id < 0 || id >= capacity) {
                return null;
            }
            return values[id];
        }

        /**
         * Checks if an ID currently exists and is active.
         * Time Complexity: O(1)
         */
        public boolean contains(int id) {
            if (id < 0 || id >= capacity) {
                return false;
            }
            return values[id] != null;
        }

        /**
         * Returns total number of active IDs in the store.
         * Time Complexity: O(1)
         */
        public int size() {
            return tree[1]; // Root stores total active count
        }

        /**
         * Counts how many active IDs exist in the inclusive range [queryL, queryR].
         * Time Complexity: O(log N)
         */
        public int countRange(int queryL, int queryR) {
            queryL = Math.max(0, queryL);
            queryR = Math.min(capacity - 1, queryR);
            if (queryL > queryR || isEmpty()) {
                return 0;
            }
            return queryCount(1, 0, capacity - 1, queryL, queryR);
        }

        private int queryCount(int node, int start, int end, int queryL, int queryR) {
            // Case 1: Node range is completely outside query range
            if (start > queryR || end < queryL || tree[node] == 0) {
                return 0;
            }

            // Case 2: Node range is completely inside query range
            if (queryL <= start && end <= queryR) {
                return tree[node];
            }

            // Case 3: Partial overlap - recurse both children
            int mid = start + (end - start) / 2;
            int leftCount = queryCount(2 * node, start, mid, queryL, queryR);
            int rightCount = queryCount(2 * node + 1, mid + 1, end, queryL, queryR);
            return leftCount + rightCount;
        }

        /**
         * Retrieves all active (ID -> Value) pairs in the inclusive range [queryL, queryR].
         * Time Complexity: O(log N + K) where K is the number of active IDs in the range.
         * Subtrees with 0 active IDs are pruned in O(1).
         */
        public Map<Integer, String> getRange(int queryL, int queryR) {
            queryL = Math.max(0, queryL);
            queryR = Math.min(capacity - 1, queryR);
            Map<Integer, String> result = new LinkedHashMap<>();
            if (queryL > queryR || isEmpty()) {
                return result;
            }
            collectRange(1, 0, capacity - 1, queryL, queryR, result);
            return result;
        }

        private void collectRange(int node, int start, int end, int queryL, int queryR, Map<Integer, String> result) {
            // Pruning: if range disjoint or subtree has NO active elements, skip entire subtree
            if (start > queryR || end < queryL || tree[node] == 0) {
                return;
            }

            // Leaf node: found an active ID
            if (start == end) {
                if (values[start] != null) {
                    result.put(start, values[start]);
                }
                return;
            }

            int mid = start + (end - start) / 2;
            collectRange(2 * node, start, mid, queryL, queryR, result);
            collectRange(2 * node + 1, mid + 1, end, queryL, queryR, result);
        }

        /**
         * Finds the largest active ID that is <= targetId.
         * Time Complexity: O(log N)
         * Returns -1 if no such active ID exists.
         */
        public int floor(int targetId) {
            if (targetId < 0 || isEmpty()) {
                return -1;
            }
            targetId = Math.min(capacity - 1, targetId);
            return findFloor(1, 0, capacity - 1, targetId);
        }

        private int findFloor(int node, int start, int end, int targetId) {
            // If range is strictly above targetId or subtree has no active elements
            if (start > targetId || tree[node] == 0) {
                return -1;
            }

            // Leaf reached and it is active
            if (start == end) {
                return start;
            }

            int mid = start + (end - start) / 2;

            // Greedily check right child first if targetId is in or beyond right child's range
            if (targetId > mid) {
                int rightResult = findFloor(2 * node + 1, mid + 1, end, targetId);
                if (rightResult != -1) {
                    return rightResult;
                }
            }

            // Otherwise, or if not found in right child, check left child
            return findFloor(2 * node, start, mid, targetId);
        }

        /**
         * Finds the smallest active ID that is >= targetId.
         * Time Complexity: O(log N)
         * Returns -1 if no such active ID exists.
         */
        public int ceiling(int targetId) {
            if (targetId >= capacity || isEmpty()) {
                return -1;
            }
            targetId = Math.max(0, targetId);
            return findCeiling(1, 0, capacity - 1, targetId);
        }

        private int findCeiling(int node, int start, int end, int targetId) {
            // If range is strictly below targetId or subtree has no active elements
            if (end < targetId || tree[node] == 0) {
                return -1;
            }

            // Leaf reached and it is active
            if (start == end) {
                return start;
            }

            int mid = start + (end - start) / 2;

            // Greedily check left child first if targetId is in or before left child's range
            if (targetId <= mid) {
                int leftResult = findCeiling(2 * node, start, mid, targetId);
                if (leftResult != -1) {
                    return leftResult;
                }
            }

            // Otherwise, or if not found in left child, check right child
            return findCeiling(2 * node + 1, mid + 1, end, targetId);
        }

        /**
         * Returns the maximum active ID (the latest ID).
         * Time Complexity: O(log N)
         * Returns -1 if store is empty.
         */
        public int getLatestId() {
            if (isEmpty()) {
                return -1;
            }
            return floor(capacity - 1);
        }

        /**
         * Returns the minimum active ID (the earliest ID).
         * Time Complexity: O(log N)
         * Returns -1 if store is empty.
         */
        public int getFirstId() {
            if (isEmpty()) {
                return -1;
            }
            return ceiling(0);
        }

        /**
         * Returns the value associated with the floor ID of targetId.
         */
        public String getFloorValue(int targetId) {
            int id = floor(targetId);
            return id != -1 ? values[id] : null;
        }

        /**
         * Returns the value associated with the ceiling ID of targetId.
         */
        public String getCeilingValue(int targetId) {
            int id = ceiling(targetId);
            return id != -1 ? values[id] : null;
        }

        /**
         * Returns true if store contains no active IDs.
         */
        public boolean isEmpty() {
            return tree[1] == 0;
        }

        private void checkBounds(int id) {
            if (id < 0 || id >= capacity) {
                throw new IndexOutOfBoundsException("ID " + id + " is out of bounds [0, " + (capacity - 1) + "]");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("=== 1. Basic CRUD & Segment Tree State Test ===");
        System.out.println("==================================================");

        SegmentTreeStore store = new SegmentTreeStore(100);

        store.put(10, "User_10");
        store.put(25, "User_25");
        store.put(50, "User_50");
        store.put(75, "User_75");
        store.put(90, "User_90");

        System.out.println("Total active size: " + store.size() + " (Expected: 5)");
        System.out.println("Get ID 25: " + store.get(25) + " (Expected: User_25)");
        System.out.println("Get ID 30 (non-existent): " + store.get(30) + " (Expected: null)");
        System.out.println("Contains ID 50: " + store.contains(50) + " (Expected: true)");

        System.out.println("\n=== 2. Testing Deletion ===");
        String deleted = store.delete(50);
        System.out.println("Deleted ID 50 value: " + deleted + " (Expected: User_50)");
        System.out.println("Contains ID 50 after delete: " + store.contains(50) + " (Expected: false)");
        System.out.println("Total active size after delete: " + store.size() + " (Expected: 4)");

        System.out.println("\n=== 3. Testing Navigation Queries (Floor, Ceiling, Latest, First) ===");
        // Active IDs currently: 10, 25, 75, 90 (50 was deleted)
        System.out.println("First active ID: " + store.getFirstId() + " (Expected: 10)");
        System.out.println("Latest active ID: " + store.getLatestId() + " (Expected: 90)");

        System.out.println("Floor of 50 (50 is deleted): " + store.floor(50) + " (Expected: 25)");
        System.out.println("Ceiling of 50 (50 is deleted): " + store.ceiling(50) + " (Expected: 75)");

        System.out.println("Floor of 10 (exact match): " + store.floor(10) + " (Expected: 10)");
        System.out.println("Ceiling of 10 (exact match): " + store.ceiling(10) + " (Expected: 10)");

        System.out.println("Floor of 5 (below min): " + store.floor(5) + " (Expected: -1)");
        System.out.println("Ceiling of 95 (above max): " + store.ceiling(95) + " (Expected: -1)");

        System.out.println("\n=== 4. Testing Range Counting & Range Retrieval ===");
        // Range [20, 80] contains IDs 25 and 75
        System.out.println("Active count in range [20, 80]: " + store.countRange(20, 80) + " (Expected: 2)");
        System.out.println("Active entries in range [20, 80]: " + store.getRange(20, 80) + " (Expected: {25=User_25, 75=User_75})");

        // Range [0, 100] contains IDs 10, 25, 75, 90
        System.out.println("Active count in range [0, 100]: " + store.countRange(0, 100) + " (Expected: 4)");
        System.out.println("Active entries in range [0, 100]: " + store.getRange(0, 100) + " (Expected: {10=User_10, 25=User_25, 75=User_75, 90=User_90})");

        System.out.println("\n==================================================");
        System.out.println("=== 5. Scale Simulation: 1 Million (1,000,000) IDs ===");
        System.out.println("==================================================");

        int ONE_MILLION = 1_000_000;
        SegmentTreeStore millionStore = new SegmentTreeStore(ONE_MILLION);

        System.out.println("Inserting 1,000,000 records...");
        long startInsert = System.currentTimeMillis();
        for (int i = 0; i < ONE_MILLION; i++) {
            millionStore.put(i, "Val_" + i);
        }
        long endInsert = System.currentTimeMillis();
        System.out.printf("1,000,000 inserts completed in %d ms (Total size: %d)%n",
                (endInsert - startInsert), millionStore.size());

        System.out.println("\nDeleting every 2nd ID (500,000 deletions)...");
        long startDelete = System.currentTimeMillis();
        for (int i = 0; i < ONE_MILLION; i += 2) {
            millionStore.delete(i);
        }
        long endDelete = System.currentTimeMillis();
        System.out.printf("500,000 deletes completed in %d ms (Remaining size: %d)%n",
                (endDelete - startDelete), millionStore.size());

        System.out.println("\nTesting Range and Navigation on 1 Million Store:");
        System.out.println("countRange(100, 200): " + millionStore.countRange(100, 200) + " (Expected: 50 odd numbers)");
        System.out.println("floor(500) (even was deleted): " + millionStore.floor(500) + " (Expected: 499)");
        System.out.println("ceiling(500) (even was deleted): " + millionStore.ceiling(500) + " (Expected: 501)");
        System.out.println("getLatestId(): " + millionStore.getLatestId() + " (Expected: 999999)");
        System.out.println("getFirstId(): " + millionStore.getFirstId() + " (Expected: 1)");

        System.out.println("\nAll tests completed successfully!");
    }
}
