package edu.najah.java.stopwatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopWatchUI extends JFrame {
    private final JButton startPauseButton;
    private final JButton resetButton;
    private final JLabel timeLabel;
    private int minutes=0,seconds=0,tenths=0;
    private boolean started =false;
    StopWatchUI() {
        super("Stop Watch");
        //Styles the Time Label
        timeLabel = new JLabel(String.format("%02d:%02d.%d",minutes,seconds,tenths));
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setFont(new Font("serif",Font.BOLD,58));
        timeLabel.setForeground(Color.RED);
        //Style for the reset and Start/Pause buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        startPauseButton=new JButton("START");
        startPauseButton.setFont(new Font("serif",Font.BOLD,18));
        resetButton=new JButton("RESET");
        resetButton.setFont(new Font("serif",Font.BOLD,18));
        resetButton.setForeground(Color.RED);
        buttonsPanel.add(startPauseButton);
        buttonsPanel.add(resetButton);
        //Sets the handler for the buttons
        TimeHandler handler=new TimeHandler();
        startPauseButton.addActionListener(handler);
        resetButton.addActionListener(handler);
        //Add to the frame
        add(timeLabel,BorderLayout.NORTH);
        add(buttonsPanel,BorderLayout.SOUTH);
        counting(false);
        this.setSize(270, 145);
    }
    /**Starts/Pauses the counter and switches the Start/Pause button accordingly.
     * @param state {@code true} starts the counter and the button label becomes "PAUSE"
     *              {@code false} pauses the counter and the button label becomes "START"
     * */
    void counting(boolean state){
            if (!state) {
                startPauseButton.setText("START");
                startPauseButton.setForeground(Color.BLUE);
                started = false;
            } else {
                startPauseButton.setText("PAUSE");
                startPauseButton.setForeground(Color.ORANGE);
                started = true;
                Thread counter = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(started){
                            try {
                                tenths++;
                                if (tenths / 10 == 1) {
                                    seconds++;
                                    if (seconds / 60 == 1) {
                                        minutes++;
                                    }
                                }
                                tenths %= 10;
                                seconds %= 60;
                                timeLabel.setText(String.format("%02d:%02d.%d", minutes, seconds, tenths));
                                Thread.sleep(100);
                            } catch (InterruptedException ignored) {}
                        }
                    }
                });
                counter.start();
            }
    }

    private class TimeHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getSource()==startPauseButton) {
                counting(!started);
            }
            if(actionEvent.getSource()==resetButton){
                counting(false);
                tenths=seconds=minutes=0;
                timeLabel.setText(String.format("%02d:%02d.%d",minutes,seconds,tenths));
            }
        }
    }
}
