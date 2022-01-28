// Skyler Bolton
// Assignment 1

import java.lang.Math;
import java.lang.Thread;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

class Global
{
    public static boolean sieve[] = new boolean[100000001];
    public static AtomicLong count = new AtomicLong(0);
}

// Structure of classes adapted from geeksforgeeks.org.
// Original code by Mehak Narang.
// https://www.geeksforgeeks.org/multithreading-in-java/
class Thready extends Thread
{
	// Constructor. n specificies the ceiling of this thread's range.
    // Start is what element this thread starts from.
	int n;
    int start;
	CountDownLatch cd;
    public Thready(int n, int start, CountDownLatch cd)
	{
        this.n = n;
        this.start = start;
		this.cd = cd;
    }

	// This uses the sieve of Eratosthenes. Code is adapted from geeksforgeeks.org.
	// Original author is Amit Khandelwal.
	// https://www.geeksforgeeks.org/sieve-of-eratosthenes/
	public void run()
	{
		int max = 100000000;

        // Each thread checks only within the range it is given.
        for (int p = this.start; (p * p <= max) && p <= this.n; p++)
        {
            if (Global.sieve[p] == true)
            {
                Global.count.getAndIncrement();
                for (int i = p * p; i <= max && i >= 0; i += p)
				{
                    Global.sieve[i] = false;
				}
            }
        }
		cd.countDown();
	}
}

class coolSieve
{
	public static void main(String[] args)
	{
        long sum = 0;
		int max = 100000000;
		int threads = 8;
		int portion = max / 8;
        int count = 0;
        CountDownLatch cd = new CountDownLatch(8);
        long startTime = System.currentTimeMillis();

        // Initialize the whole thing to true.
		for (int i = 0; i <= max; i++)
		{
            Global.sieve[i] = true;
		}

        // Spawn the threads and give them their portion of the array.
        // This is very ugly.
        Thready foopy = new Thready(100, 2, cd);
        foopy.start();

        foopy = new Thready(250, 100, cd);
        foopy.start();

        foopy = new Thready(500, 250, cd);
        foopy.start();

        foopy = new Thready(2500, 500, cd);
        foopy.start();

        foopy = new Thready(10000, 2500, cd);
        foopy.start();

        foopy = new Thready(1000000, 10000, cd);
        foopy.start();

        foopy = new Thready(10000000, 1000000, cd);
        foopy.start();

        foopy = new Thready(100000000, 10000000, cd);
        foopy.start();

        // Wait for threads to finish before doing anything with the array.
		try {cd.await();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}

		// Print primes using sieve[], and track the sum
        for (int i = 2; i < max; i++)
        {
            if (Global.sieve[i])
            {
                sum += i;
                count++;
            }
        }

        // Get last ten primes (yeah this is clunky)
        int j = 9;
        int[] lastTen = new int[10];
        for (int i = 100000000; j >= 0; i--)
        {
            if (Global.sieve[i])
            {
                lastTen[j] = i;
                j--;
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println(duration + "ms  " + count + " " + sum);
        for (int i : lastTen)
        {
            System.out.println(i);
        }
	}
}