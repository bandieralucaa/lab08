package it.unibo.mvc;

import java.awt.LayoutManager;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.io.IOException;


/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUI {

    private final JFrame frame = new JFrame("First GUI Java");
    private SimpleGUI(final Controller c) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel panel = new JPanel();
        final JTextArea textArea = new JTextArea();
        final LayoutManager layout = new BorderLayout();
        panel.setLayout(layout);
        final JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                   try {
                    c.saveFile(textArea.getText());
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(frame, "Error saving file");
                }
            }
        });

        panel.add(textArea, BorderLayout.CENTER);
        panel.add(save, BorderLayout.SOUTH);
        frame.setContentPane(panel);

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screenSize.getWidth();
        final int height = (int) screenSize.getHeight();
        frame.setSize(width / 2, height / 2);
        frame.setLocationByPlatform(true);

    }

    private void display() {
        frame.setVisible(true);
    }

    /**
     * Main method.
     * @param c
     */
    public static void main(final String... c) {
        final SimpleGUI gui = new SimpleGUI(new Controller());
        gui.display();
    }




}

