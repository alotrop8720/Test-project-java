package by.qulixsystem.practice.testTask;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TestKeyListener {



    static class NumberKeyListener extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
                System.out.println("Press");
        }
    }

    public static void main( String[] args ) {
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.addKeyListener(new NumberKeyListener());
        frame.requestFocusInWindow();
        frame.pack();
        frame.setVisible(true);


    }
}
