import java.util.Random;
import java.util.concurrent.Semaphore;

public class ReaderWriterDemo {
    // Semaphore protecting the shared resource (only one writer or many readers)
    private static final Semaphore resource = new Semaphore(1);
    // Semaphore to protect readCount updates (acts as a mutex)
    private static final Semaphore readCountMutex = new Semaphore(1);
    private static int readCount = 0;

    static class Reader implements Runnable {
        private final int id;
        private final Random rnd = new Random();

        Reader(int id) { this.id = id; }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) { // each reader reads 5 times
                    // Entry section for readers
                    readCountMutex.acquire();
                    readCount++;
                    if (readCount == 1) {
                        // first reader locks the resource to block writers
                        resource.acquire();
                    }
                    readCountMutex.release();

                    // Critical section: reading
                    System.out.printf("Reader-%d START reading (readers=%d)%n", id, readCount);
                    Thread.sleep(200 + rnd.nextInt(300));
                    System.out.printf("Reader-%d DONE reading (readers=%d)%n", id, readCount);

                    // Exit section for readers
                    readCountMutex.acquire();
                    readCount--;
                    if (readCount == 0) {
                        // last reader releases the resource to allow writers
                        resource.release();
                    }
                    readCountMutex.release();

                    // Do some non-critical work
                    Thread.sleep(200 + rnd.nextInt(400));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Writer implements Runnable {
        private final int id;
        private final Random rnd = new Random();

        Writer(int id) { this.id = id; }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) { // each writer writes 5 times
                    // Entry section for writers
                    resource.acquire(); // exclusive access
                    // Critical section: writing
                    System.out.printf("Writer-%d START writing%n", id);
                    Thread.sleep(300 + rnd.nextInt(400));
                    System.out.printf("Writer-%d DONE writing%n", id);
                    // Exit section for writers
                    resource.release();

                    // Do some non-critical work
                    Thread.sleep(300 + rnd.nextInt(500));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int numReaders = 5;
        int numWriters = 2;

        Thread[] readers = new Thread[numReaders];
        Thread[] writers = new Thread[numWriters];

        for (int i = 0; i < numReaders; i++) {
            readers[i] = new Thread(new Reader(i + 1), "Reader-" + (i + 1));
            readers[i].start();
        }
        for (int i = 0; i < numWriters; i++) {
            writers[i] = new Thread(new Writer(i + 1), "Writer-" + (i + 1));
            writers[i].start();
        }

        // Wait for all threads to finish
        for (Thread t : readers) t.join();
        for (Thread t : writers) t.join();

        System.out.println("All readers and writers have finished.");
    }
}