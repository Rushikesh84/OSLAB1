import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

class Producer implements Runnable {

    private Queue<Integer> buffer;
    private int capacity;
    private Semaphore empty;
    private Semaphore full;
    private Semaphore mutex;

    public Producer(Queue<Integer> buffer, int capacity, Semaphore empty, Semaphore full, Semaphore mutex) {
        this.buffer = buffer;
        this.capacity = capacity;
        this.empty = empty;
        this.full = full;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        int item = 1;
        try {
            while (true) {

                empty.acquire();   // Wait if buffer is full
                mutex.acquire();   // Enter critical section

                buffer.add(item);
                System.out.println("Producer produced: " + item);
                item++;

                mutex.release();   // Exit critical section
                full.release();    // Signal that buffer has new item

                Thread.sleep(500); // Simulate time taken to produce
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {

    private Queue<Integer> buffer;
    private Semaphore empty;
    private Semaphore full;
    private Semaphore mutex;

    public Consumer(Queue<Integer> buffer, Semaphore empty, Semaphore full, Semaphore mutex) {
        this.buffer = buffer;
        this.empty = empty;
        this.full = full;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        try {
            while (true) {

                full.acquire();    // Wait if buffer is empty
                mutex.acquire();   // Enter critical section

                int item = buffer.remove();
                System.out.println("Consumer consumed: " + item);

                mutex.release();   // Exit critical section
                empty.release();   // Signal that space is available

                Thread.sleep(700); // Simulate time taken to consume
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ProducerConsumerProblem {

    public static void main(String[] args) {

        Queue<Integer> buffer = new LinkedList<>();
        int capacity = 5;

        // Semaphores
        Semaphore empty = new Semaphore(capacity); // initially all slots empty
        Semaphore full = new Semaphore(0);         // initially nothing to consume
        Semaphore mutex = new Semaphore(1);        // binary mutex

        Producer producer = new Producer(buffer, capacity, empty, full, mutex);
        Consumer consumer = new Consumer(buffer, empty, full, mutex);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }
}

