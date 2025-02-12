package com.mohanvamsi06.FileEncryptor.test;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

class TestUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("File Encryptor Test UI");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(800, 500));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 16);

        JButton selectFileButton = new JButton("Select File");
        selectFileButton.setFont(buttonFont);
        selectFileButton.setPreferredSize(new Dimension(160, 40));

        JTextField fileTextField = new JTextField("No file selected", 20);
        fileTextField.setEditable(false);
        fileTextField.setFont(textFieldFont);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(textFieldFont);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(labelFont);
        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setFont(textFieldFont);

        JLabel algorithmLabel = new JLabel("Select Algorithm:");
        algorithmLabel.setFont(labelFont);

        JRadioButton desButton = new JRadioButton("DES", true);
        desButton.setFont(labelFont);
        JRadioButton aesButton = new JRadioButton("AES");
        aesButton.setFont(labelFont);
        JRadioButton blowfishButton = new JRadioButton("Blowfish");
        blowfishButton.setFont(labelFont);

        ButtonGroup algorithmGroup = new ButtonGroup();
        algorithmGroup.add(desButton);
        algorithmGroup.add(aesButton);
        algorithmGroup.add(blowfishButton);

        JCheckBox keepOriginalCheckBox = new JCheckBox("Keep Original File", true);
        keepOriginalCheckBox.setFont(labelFont);

        // **Reduced Encrypt & Decrypt button size**
        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setFont(buttonFont);
        encryptButton.setPreferredSize(new Dimension(160, 40));

        JButton decryptButton = new JButton("Decrypt");
        decryptButton.setFont(buttonFont);
        decryptButton.setPreferredSize(new Dimension(160, 40));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(selectFileButton, gbc);

        gbc.gridx = 1;
        panel.add(fileTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        panel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(algorithmLabel, gbc);

        gbc.gridx = 1;
        JPanel algoPanel = new JPanel();
        algoPanel.add(desButton);
        algoPanel.add(aesButton);
        algoPanel.add(blowfishButton);
        panel.add(algoPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(keepOriginalCheckBox, gbc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.insets = new Insets(10, 20, 10, 20);

        btnGbc.gridx = 0;
        buttonPanel.add(encryptButton, btnGbc);

        btnGbc.gridx = 1;
        buttonPanel.add(decryptButton, btnGbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                fileTextField.setText(selectedFile.getAbsolutePath());
                System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            }
        });

        encryptButton.addActionListener(e -> System.out.println("Encryption Started"));
        decryptButton.addActionListener(e -> System.out.println("Decryption Started"));

        // **Resize dynamically while keeping buttons balanced**
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = frame.getWidth();
                int height = frame.getHeight();

                int newFontSize = Math.max(width / 50, 16);
                Font newFont = new Font("Arial", Font.BOLD, newFontSize);

                selectFileButton.setFont(newFont);
                encryptButton.setFont(newFont);
                decryptButton.setFont(newFont);
                fileTextField.setFont(new Font("Arial", Font.PLAIN, newFontSize));
                passwordField.setFont(new Font("Arial", Font.PLAIN, newFontSize));
                confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, newFontSize));
                passwordLabel.setFont(newFont);
                confirmPasswordLabel.setFont(newFont);
                algorithmLabel.setFont(newFont);
                desButton.setFont(newFont);
                aesButton.setFont(newFont);
                blowfishButton.setFont(newFont);
                keepOriginalCheckBox.setFont(newFont);

                // Scale buttons based on window size
                int buttonWidth = Math.max(width / 8, 160);
                int buttonHeight = Math.max(height / 15, 40);
                Dimension newButtonSize = new Dimension(buttonWidth, buttonHeight);

                selectFileButton.setPreferredSize(newButtonSize);
                encryptButton.setPreferredSize(newButtonSize);
                decryptButton.setPreferredSize(newButtonSize);

                panel.revalidate();
                panel.repaint();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
