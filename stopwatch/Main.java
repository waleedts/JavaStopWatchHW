package edu.najah.java.stopwatch;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        StopWatchUI stopwatch=new StopWatchUI();
        stopwatch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stopwatch.setResizable(false);
        stopwatch.setVisible(true);
    }
}
