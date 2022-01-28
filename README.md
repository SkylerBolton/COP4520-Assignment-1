# COP4520-Assignment-1
RUNNING: 
  javac coolSieve.java
  java coolSieve
This will print output to primes.txt.


ALGORITHM: This file adapts the Sieve of Eratosthenes to use multithreading. It calls 8 threads, each thread having a different range of numbers to test. Do to the nature of the sieve, I gave the lower-numbered threads smaller ranges, since they have more significantly work to perform. 

EFFICIENCY: The algorithm is slightly faster when multithreaded compared to when not: concurrently, the algorithm takes ~1800-1900 ms. Multithreaded, it takes ~1600-1700 ms. 

CORRECTNESS: The number of primes is slightly incorrect. 5761360 is what my program outputs, but it should be 5761479 (119 more). As a result of the missing primes, the sum is also slightly off. The final ten primes printed are correct, as are the primes that I did find.
