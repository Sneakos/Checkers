
/**
 * Write a description of class ConnectFour here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ConnectFour extends JFrame
{
    static int WIDTH = 600, HEIGHT = 575;
    Dimension size = new Dimension(WIDTH, HEIGHT);

    public ConnectFour()
    {
        EventQueue.invokeLater(new Runnable() {
                public void run() {
                    setTitle("Connect Four");
                    pack();
                    getContentPane().setBackground(Color.WHITE);
                    setVisible(true);
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setSize(size);
                    setLocationRelativeTo(null);

                    add(new Picture());
                }
            });
    }

    
}
