package com.neusoft.sdd.util.file;
import java.util.*;

public class ExcuteCMD {

    public static void main(String[] args) throws Exception {
        String cmd = "cmd /c dir";
        System.out.println("Executing: " + cmd);
        Process proc = Runtime.getRuntime().exec(cmd); // Executing the Command.
        
        ProcessorReader procReader = new ProcessorReader(proc);
        procReader.start(); // Start the thread for reading process' output
        procReader.join(); // Wait for the thread finish.
        
        System.out.println("Process exit with code: " + proc.exitValue());
    }
}

class ProcessorReader extends Thread {
    Process proc;

    public ProcessorReader(Process proc) {
        this.proc = proc;
    }

    public void run() {
        Scanner scStdOut = new Scanner(proc.getInputStream(), "GBK"); // Prepare Scanner for standard's output.
        Scanner scErrOut = new Scanner(proc.getInputStream(), "GBK"); // Prepare Scanner for error's output.
        while (true) {
            if (scStdOut.hasNextLine()) {
                System.out.println(scStdOut.nextLine()); // Standard Output Information
            } else if (scErrOut.hasNextLine()) {
                System.err.println(scErrOut.nextLine()); // Error Information
            } else {
                try {
                    Thread.sleep(20); // Nothing to do, sleep a while...
                    proc.exitValue(); // ThrowIllegalThreadStateException, if the subprocess represented by this Process object has not yet terminated.
                    break;
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (IllegalThreadStateException ex) {
                    // Process still alive
                }
            }
        }
    }
}
