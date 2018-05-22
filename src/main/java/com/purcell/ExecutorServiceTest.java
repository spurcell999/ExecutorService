package com.purcell;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {

    private static Future taskTwo = null;
    private static Future taskThree = null;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable taskOne = new MyThread("TaskOne", 2, 100);
        executor.execute(taskOne);

        for (int i = 0; i < 2; i++) {

            if ((taskTwo == null) || (taskTwo.isDone()) || (taskTwo.isCancelled())) {
                taskTwo = executor.submit(new MyThread("TaskTwo", 4, 200));
            }

            if ((taskThree == null) || (taskThree.isDone()) || (taskThree.isCancelled())) {
                taskThree = executor.submit(new MyThread("TaskThree", 5, 100));
            }

            if (taskTwo.get() == null) {
                System.out.println(i + 1 + ") TaskTwo terminated successfully");
            } else {
                taskTwo.cancel(true);
            }
            if (taskThree.get() == null) {
                System.out.println(i + 1 + ") TaskThree terminated successfully");
            } else {
                taskThree.cancel(true);
            }
        }
        executor.shutdown();
        ;
        System.out.println("-------------------------");
        executor.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println("All tasks are finished.");
    }
}
