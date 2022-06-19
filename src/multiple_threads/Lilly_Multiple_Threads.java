//Name: Trevor Lilly 
//Date: 3/23/2022
//Program: Lilly_Multiple_Threads
//Description: A program that compares the performance of addition done via multiple threads versus a single thread.

package multiple_threads;

import java.util.Random;

public class Lilly_Multiple_Threads {
	// this is the integer that we randomly add up with, generally it'll be filled up with whatever ran will generate with
	static int sum = 0;
	static int sum1 = 0;
	// This counts every time the increment method has been accessed to create something of a check
	// this check ensures that we've accessed the array1.length the correct amount of times
	// if there's a mismatch between array1.length and the count, this means the sleep thread is a bad value (causes things to close prematurely)
	// or we don't have the correct divisor to match the thread. 
	// yes, this is beyond ghetto
	static int count = 0;
	// this count is for the single-threaded counter part
	static int count1 = 0;
	// the random element
	static Random ran = new Random();
	// multi-threaded array that we're adding too
	static int[] array1;
	// single threaded array that we're adding to
	static int[] array2;
	
	public static void main(String[] args) {
		 
		 array1 = new int [200000000];
		 array2 = new int [200000000];
		
		 // t1-t4 will all calculate up to the same amount, 200 million in the same method
		 Thread t1 = new Thread (new Runnable() {
			public void run() {
					 increment();
				 }
		 });
		 
		 Thread t2 = new Thread (new Runnable() {
				public void run() {
					 increment();
				 }
		 });
		 
		 
		 Thread t3 = new Thread (new Runnable() {
				public void run() {
					 increment();
				 }
		 });
		 
		 
		 Thread t4 = new Thread (new Runnable() {
				public void run() {
					 increment();
				 }
		 });
		 
		 // the single thread will increment alone, all by itself 200 million times
		 Thread t5 = new Thread (new Runnable() {
			public void run() {
					 increment1();				 
				
			}
		});
		 
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		// starting the timer for the multi threaded process
		long start = System.nanoTime();

		 try {
			 // The moment I started adding nanoseconds to each of the join methods was when I saw my results actually scale.
			 // The sweet spot for performance I found was maybe like 200-300. If you just leave it at 1000 nanoseconds, it'll perform just fine
			 // This is just optimization for optimization sake, cause 200 nanoseconds gives me half of what i'd get at 4 threads w. 1000
			 // On second thought, I'm gonna leave this at 1000. It's the sanest default and it'll probably run better on different machines. 
			t1.join(1000);
			t2.join(1000);
			t3.join(1000);
			t4.join(1000);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //timer for multi threaded stops here 
		 long end = System.nanoTime() - start;
		
		 
		 t5.start();
		 // timer for single threaded starts here
		 long start1 = System.nanoTime();
		 
		 	try {
		 		// I tried playing with the values in join() method, but all I got was the thread closing early. As it stands, it's doing good on its own I guess
				t5.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 long end1= System.nanoTime() - start1;
		 System.out.println("multithreaded sum is: " + sum);
		 System.out.println("multi threaded sum has been accessed: " + count +" times" );
		 if (count == array1.length) {
			 System.out.println("This is the correct amount of accesses");
		 } else 
			 System.out.println("This is not the correct amount of accesses, change thread sleep values or divisor to match thread count.");
		 System.out.println(end + " is how long it took multi-threaded sum");
		 
		 System.out.println("\nsingle-threaded sum is: " + sum1);
		 System.out.println("single threaded sum has been accessed: " + count1 +" times" );
		 if (count1 == array2.length) {
			 System.out.println("This is the correct amount of accesses");
		 } else 
			 System.out.println("This is not the correct amount of accesses, change thread sleep values or divisor to match thread count.");
		 System.out.println(end1 + " is how long it took single thread sum");
	}
	
	
	// This method is for the multi-threads. 
	public static synchronized void increment() {
		// To keep it simple, the divisor of the array1.length changes on the amount of threads.
		// If we're using 2 threads, then we'll have the divisor be 2. If we're using 4 threads, the divisor should be 4.
		// If you really wanted to remove the divisor you could... and it'd probably outperform the single thread? Of course, YMMV.
		for (int i = 0; i < array1.length/4; i++) {
			count++;
			sum += ran.nextInt((10) + 1);
			array1[i] = sum;
		}
}
		
	// For the sake of fairness and accuracy, I just made the single thread method use synchronized too. 
	// When I took it off, I was getting 3 times worse performance than usual.
	// regardless this is the single threaded increment method
	public static synchronized void increment1() {
		for (int i = 0; i < array2.length; i++) {
		count1++;
		sum1 += ran.nextInt((10) + 1);
		array2[i] = sum1;
		}
	}
}