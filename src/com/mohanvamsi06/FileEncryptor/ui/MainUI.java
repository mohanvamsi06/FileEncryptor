package com.mohanvamsi06.FileEncryptor.ui;

import com.mohanvamsi06.FileEncryptor.encryptor.Decryptor;
import com.mohanvamsi06.FileEncryptor.encryptor.Encryptor;
import java.awt.*;
import java.io.File;
import javax.swing.*;

public class MainUI {
    public static void LaunchUI() {
        JFrame frame = new JFrame("File Encryptor");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 500));

        JPanel panel = new JPanel(new GridBagLayout());
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

        // ** File Selection Listener **
        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                fileTextField.setText(selectedFile.getAbsolutePath());
                System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            }
        });

        // ** Encrypt Button Listener **
        encryptButton.addActionListener(e -> {
            String filePath = fileTextField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (filePath.equals("No file selected")) {
                JOptionPane.showMessageDialog(frame, "Please select a file to encrypt!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a password!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String algorithm = desButton.isSelected() ? "des" : aesButton.isSelected() ? "aes" : "blowfish";
            boolean keepOriginal = keepOriginalCheckBox.isSelected();

            Encryptor encryptor = new Encryptor();
            int result = encryptor.EncryptFile(filePath, password, algorithm, keepOriginal);

            if (result == 0) {
                JOptionPane.showMessageDialog(frame, "Encryption successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Encryption failed! Error code: " + result, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ** Decrypt Button Listener **
        decryptButton.addActionListener(e -> {
            String filePath = fileTextField.getText();
            String password = new String(passwordField.getPassword());

            if (filePath.equals("No file selected")) {
                JOptionPane.showMessageDialog(frame, "Please select a file to decrypt!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a password!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean keepOriginal = keepOriginalCheckBox.isSelected();

            Decryptor decryptor = new Decryptor();
            int result = decryptor.DecryptFile(filePath, password, keepOriginal);

            if (result == 0) {
                JOptionPane.showMessageDialog(frame, "Decryption successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Decryption failed! Error code: " + result, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}