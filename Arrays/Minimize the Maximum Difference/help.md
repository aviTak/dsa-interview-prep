| 1 | 7 | 10 | 12 | 15 | 22 |

K = 6

Sorted array hai

Ideally, last mein -K karo and first mein +K karo and kaam bann jana chahiye.
Lekin kuch cases mein aisa nahi kaam karega. When:-
1. Koi element se plus kiya and voh last element (largest) se bhi bda ho gya kyu ki last element ko toh minus hi karna hai. Minus uss element ko kar nahi sakte varna itna chota ho jayega ki max difference hi bda diya.
2. Similarly, koi element se minus kiya and voh first element se bhi chota ho gya.

Toh apne ko yeh dono case check karne.

* Plus aisa bhi ho sakta ki dono hi case true ho jaye.
Agar aisa hua toh voh dono adjacent elements hi hoyeinge kyu ki sorted array hai.

| +K | +K | +K | -K | -K | -K |

1. Toh last plus K kiya hua element hi sabse bda ho sakta after adding K.
2. Similarly, first minus K kiya hua element hi sabse chota ho sakta. Kyu ki aage ke elements toh usse bde hi hain.

Toh apne ko bas adjacent difference nikalna rarest case mein.

1. Toh pehla element lo uss mein +K karo and baaki ke sab -K.
2. Fir dheere-dheere karke aage ke elements mein +K karte jao and baaki ke remaining mein -K.
3. Difference nikal-nikalke dekhte raho ki konsa sabse chota aaya hai.
