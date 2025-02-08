package com.mohanvamsi06.FileEncryptor.ui;

import com.mohanvamsi06.FileEncryptor.encryptor.impl.EncryptorImpl;
import java.awt.*;
import java.io.File;
import javax.swing.*;

public class EncryptScreen extends JPanel {
    private JTextField fileTextField;
    private JPasswordField passwordField, confirmPasswordField;
    private JCheckBox showPasswordCheckBox;

    public EncryptScreen(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 16);

        // File selection components
        JButton selectFileButton = new JButton("Select File");
        selectFileButton.setFont(buttonFont);
        selectFileButton.setPreferredSize(new Dimension(160, 40));

        fileTextField = new JTextField("No file selected", 20);
        fileTextField.setEditable(false);
        fileTextField.setFont(textFieldFont);

        // Password fields
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordField = new JPasswordField(20);
        passwordField.setFont(textFieldFont);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(labelFont);
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setFont(textFieldFont);

        // Show Password Toggle
        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setFont(labelFont);
        showPasswordCheckBox.addActionListener(e -> togglePasswordVisibility());

        // Algorithm selection
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

        JPanel algoPanel = new JPanel();
        algoPanel.add(desButton);
        algoPanel.add(aesButton);
        algoPanel.add(blowfishButton);

        // Keep original file checkbox
        JCheckBox keepOriginalCheckBox = new JCheckBox("Keep Original File", true);
        keepOriginalCheckBox.setFont(labelFont);

        // Encrypt and Back buttons
        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setFont(buttonFont);
        encryptButton.setPreferredSize(new Dimension(160, 40));

        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.setPreferredSize(new Dimension(160, 40));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        // File selection logic
        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                fileTextField.setText(selectedFile.getAbsolutePath());
                System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            }
        });

        // Encrypt button logic
        encryptButton.addActionListener(e -> {
            String filePath = fileTextField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (filePath.equals("No file selected")) {
                JOptionPane.showMessageDialog(this, "Please select a file to encrypt!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a password!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String algorithm = desButton.isSelected() ? "des" : aesButton.isSelected() ? "aes" : "blowfish";
            boolean keepOriginal = keepOriginalCheckBox.isSelected();

            EncryptorImpl encryptor = new EncryptorImpl();
            int result = encryptor.EncryptFile(filePath, password, algorithm, keepOriginal);

            if (result == 0) {
                JOptionPane.showMessageDialog(this, "Encryption successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                keepOriginalCheckBox.setSelected(true);
            } else {
                JOptionPane.showMessageDialog(this, "Encryption failed! Error code: " + result, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Adding components to the panel using GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(selectFileButton, gbc);

        gbc.gridx = 1;
        add(fileTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(showPasswordCheckBox, gbc); // Show password toggle checkbox

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(algorithmLabel, gbc);

        gbc.gridx = 1;
        add(algoPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        add(keepOriginalCheckBox, gbc);

        // Buttons panel
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(encryptButton);
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);
    }
    // Toggles Password Visibility
    private void togglePasswordVisibility() {
        if (showPasswordCheckBox.isSelected()) {
            passwordField.setEchoChar((char) 0); // Show password
            confirmPasswordField.setEchoChar((char) 0);
        } else {
            passwordField.setEchoChar('*'); // Hide password
            confirmPasswordField.setEchoChar('*');
        }
    }

    private void clearFields() {
        fileTextField.setText("No file selected");
        passwordField.setText("");
        confirmPasswordField.setText("");
        showPasswordCheckBox.setSelected(false);
        passwordField.setEchoChar('*');
        confirmPasswordField.setEchoChar('*');
    }
}
