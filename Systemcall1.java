public class Systemcall1 {
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && "child".equals(args[0])) {
            // Child: open, write, read, close
            long pid = java.lang.ProcessHandle.current().pid();
            String fileName = "syscall_demo.txt";
            String message = "Child PID " + pid + " wrote at " + java.time.Instant.now() + System.lineSeparator();

            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(fileName, true)) {
                fos.write(message.getBytes(java.nio.charset.StandardCharsets.UTF_8));
                fos.flush();
            }

            try (java.io.FileInputStream fis = new java.io.FileInputStream(fileName)) {
                byte[] buf = new byte[1024];
                int n = fis.read(buf);
                String content = n > 0 ? new String(buf, 0, n, java.nio.charset.StandardCharsets.UTF_8) : "";
                System.out.println("Child read file start:\n" + content);
            }

            // exit to return control to parent (demonstrates wait)
            System.exit(0);
        } else {
            // Parent: "fork" by launching another JVM instance of the same class, then wait()
            String javaBin = System.getProperty("java.home") + java.io.File.separator + "bin" + java.io.File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            java.util.List<String> cmd = java.util.Arrays.asList(javaBin, "-cp", classpath, Systemcall1.class.getName(), "child");
            java.lang.ProcessBuilder pb = new java.lang.ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            java.lang.Process child = pb.start();

            System.out.println("Parent started child (pid=" + child.pid() + "), waiting for it to finish...");

            // read child's stdout while it runs
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(child.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println("Child> " + line);
                }
            }

            int exitCode = child.waitFor(); // wait syscall equivalent
            System.out.println("Child exited with code " + exitCode);

            // Parent: open, read, close the file written by child
            String fileName = "syscall_demo.txt";
            try (java.io.FileInputStream fis = new java.io.FileInputStream(fileName)) {
                byte[] all = fis.readAllBytes();
                String content = new String(all, java.nio.charset.StandardCharsets.UTF_8);
                System.out.println("Parent read final file content:\n" + content);
            } catch (java.io.FileNotFoundException e) {
                System.out.println("File not found: " + fileName);
            }
        }
    }

}
