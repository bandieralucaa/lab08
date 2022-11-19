package it.unibo.mvc;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.util.List;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUI {

    private static final String PRINT = "print";
    private static final String HISTORY = "history";
    private final JFrame frame = new JFrame();
    private final Controller controller;

   /**
    * 
    * @param controller
    */
    public SimpleGUI(final Controller controller) {
        this.controller = controller;
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        final JTextField textField = new JTextField();
        textField.setBackground(Color.lightGray);
        panel.add(textField, BorderLayout.NORTH);
        final JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        panel.add(textArea, BorderLayout.CENTER);
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        panel.add(buttonPanel, BorderLayout.SOUTH);
        final JButton printButton = new JButton(PRINT);
        final JButton historyButton = new JButton(HISTORY);
        buttonPanel.add(printButton);
        buttonPanel.add(historyButton);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                SimpleGUI.this.controller.setNextStringPrint(textField.getText());
                SimpleGUI.this.controller.printCurrentString();
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final StringBuilder sb = new StringBuilder();
                final List<String> history = SimpleGUI.this.controller.getPrinteStringHistory();
                for (final String s : history) {
                    sb.append(s);
                    sb.append('\n');
                }
                if (!history.isEmpty()) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                textArea.setText(sb.toString());
            }
        });

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screenSize.getWidth();
        final int height = (int) screenSize.getHeight();
        frame.setSize(width / 2, height / 2);
        frame.setLocationByPlatform(true);
    }

    /**
     * 
     */
    public void display() {
        frame.setVisible(true);
    }

    /**
     * 
     * @param args
     */
    public static void main(final String[] args) {
        new SimpleGUI(new SimpleController()).display();
    } 
}
