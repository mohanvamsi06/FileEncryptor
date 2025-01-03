package com.mohanvamsi06.FileEncryptor.test;

import com.mohanvamsi06.FileEncryptor.encryptor.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;

class TestUI{
    public static void main(String[] args) {
        JFrame frame = new JFrame();    // creating a GUI frame
        frame.setSize(800, 500);    // setitng frame size
        frame.setLayout(null);      // using no layout managers
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // making the program exit on close

        JButton selectFileButton = new JButton("Select File");
        selectFileButton.setBounds(300, 200, 200, 40);  // Setting button position and size
        frame.add(selectFileButton);

        // Creating a label to display the selected file
        JLabel fileLabel = new JLabel("No file selected");
        fileLabel.setBounds(510, 250, 300, 30);  // Position and size
        frame.add(fileLabel);

        // Adding an action listener to the button
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null); // Opens file chooser dialog

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    fileLabel.setText("Selected: " + selectedFile.getName());
                    System.out.println("Selected File: " + selectedFile.getAbsolutePath());
                }
            }
        });

        frame.setVisible(true);     // making the frame visible
    }
}