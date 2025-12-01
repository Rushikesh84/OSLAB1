import java.io.*;

public class SystemCallDemo {

    public static void main(String[] args) {
        // 1) Process-related system calls using ProcessBuilder
        System.out.println("Demonstrating process-related calls: fork & wait");

        // try {
        //     // Simulate fork by creating a new process (runs 'java -version' as example)
        //     ProcessBuilder pb = new ProcessBuilder("java", "-version");
        //     Process childProcess = pb.start(); // fork

        //     System.out.println("Parent process waiting for child to complete...");
        //     int exitCode = childProcess.waitFor(); // wait
        //     System.out.println("Child process finished with exit code: " + exitCode + "\n");

        // } catch (IOException | InterruptedException e) {
        //     e.printStackTrace();
        // }

        // 2) File-related system calls
        System.out.println("Demonstrating file-related calls: open, write, read, close");

        String filename = "example.txt";
        String dataToWrite = "Hello, this is a test using Java file streams!\n";

        // Open & write
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(dataToWrite.getBytes()); // write
            System.out.println("Data written to file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Open & read
        try (FileInputStream fis = new FileInputStream(filename)) {
            byte[] buffer = new byte[100];
            int bytesRead = fis.read(buffer); // read
            String readData = new String(buffer, 0, bytesRead);
            System.out.println("Data read from file:\n" + readData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}