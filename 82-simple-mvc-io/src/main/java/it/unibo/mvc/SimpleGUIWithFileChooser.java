package it.unibo.mvc;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.io.File;
import java.io.IOException;


/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    private final JFrame frame = new JFrame("Second GUI Java");

    private SimpleGUIWithFileChooser(final Controller c) {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      final JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout()); 
      final JTextArea textArea = new JTextArea();
      final JButton save = new JButton("Save");
      save.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
          try {
            c.saveFile(textArea.getText());
          } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error saving file");
          }
        }
      });

      panel.add(textArea, BorderLayout.CENTER);
      panel.add(save, BorderLayout.SOUTH);

      final JTextField filePath = new JTextField(c.getCurrentFilePath());
      filePath.setEditable(false);
      final JButton browse = new JButton("Browse..."); 
      browse.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
          final JFileChooser fc = new JFileChooser("Choose where to save the file");
          fc.setSelectedFile(c.getCurrentFile());
          final int returnVal = fc.showSaveDialog(frame);
          switch (returnVal) {
            case JFileChooser.APPROVE_OPTION:
              final File file = fc.getSelectedFile();
              c.setDestination(file);
              filePath.setText(file.getPath());
              break;
            case JFileChooser.CANCEL_OPTION:
              break;
            default:
              JOptionPane.showMessageDialog(frame, "Error choosing file");
          }
        }
      });

      final JPanel upPanel = new JPanel();
      upPanel.setLayout(new BorderLayout());
      upPanel.add(filePath, BorderLayout.CENTER);
      upPanel.add(browse, BorderLayout.LINE_END);
      panel.add(upPanel, BorderLayout.NORTH);

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
     * @param args
     */
    public static void main(final String... args) {
      final SimpleGUIWithFileChooser gui = new SimpleGUIWithFileChooser(new Controller());
      gui.display();
    }
}
