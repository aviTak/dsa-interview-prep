arr[] = { 10, 5, 4, 3, 48, 6, 2, 33, 53, 10 }
K = 2

Iss mein Kth smallest element nikalna hai

1. Heap sort karlo priority queue se.
    a. Priority queue is a binary tree that always peek the smallest/largest number (based on ki min PQ ha ya max PQ)
    b. Smallest nikalna hai toh max PQ lo.
2. Max Priority Queue mein elements add karo. Lekin 'K' hi.
3. Jaisi K + 1 elements ho jayein, 1 element nikal do. Largest vala nikal dega lekin K elements rheinge jo usse chote hoyeinge. And apne ko chote numbers se hi matlab hai.
4. Loop pura ho jaye toh peek kar dena. Unn K mein se sabse bda element de dega. Matlab unn chote numbers mein se sabse bda element.
