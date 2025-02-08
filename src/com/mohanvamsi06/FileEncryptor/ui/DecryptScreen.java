package com.mohanvamsi06.FileEncryptor.ui;

import com.mohanvamsi06.FileEncryptor.encryptor.Decryptor;
import java.awt.*;
import java.io.File;
import javax.swing.*;

public class DecryptScreen extends JPanel {
    private JTextField fileTextField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox, keepOriginalCheckBox;

    public DecryptScreen(CardLayout cardLayout, JPanel mainPanel) {
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

        // Password input
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordField = new JPasswordField(20);
        passwordField.setFont(textFieldFont);

        // Show Password Toggle
        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setFont(labelFont);
        showPasswordCheckBox.addActionListener(e -> togglePasswordVisibility());

        // Keep original file checkbox
        keepOriginalCheckBox = new JCheckBox("Keep Original File", true);
        keepOriginalCheckBox.setFont(labelFont);

        // Decrypt and Back buttons
        JButton decryptButton = new JButton("Decrypt");
        decryptButton.setFont(buttonFont);
        decryptButton.setPreferredSize(new Dimension(160, 40));

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

        // Decrypt button logic
        decryptButton.addActionListener(e -> {
            String filePath = fileTextField.getText();
            String password = new String(passwordField.getPassword());

            if (filePath.equals("No file selected")) {
                JOptionPane.showMessageDialog(this, "Please select a file to decrypt!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a password!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean keepOriginal = keepOriginalCheckBox.isSelected();

            Decryptor decryptor = new Decryptor();
            int result = decryptor.DecryptFile(filePath, password, keepOriginal);

            if (result == 0) {
                JOptionPane.showMessageDialog(this, "Decryption successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                String errorMessage = Decryptor.getErrorMessage(result);
                JOptionPane.showMessageDialog(this, "Decryption failed! " + errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
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

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(showPasswordCheckBox, gbc); // Show password toggle checkbox

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(keepOriginalCheckBox, gbc);

        // Buttons panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(decryptButton);
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);
    }

    // Toggles Password Visibilty
    private void togglePasswordVisibility() {
        if (showPasswordCheckBox.isSelected()) {
            passwordField.setEchoChar((char) 0); // Show password
        } else {
            passwordField.setEchoChar('*'); // Hide password
        }
    }

    private void clearFields() {
        fileTextField.setText("No file selected");
        passwordField.setText("");
        showPasswordCheckBox.setSelected(false);
        passwordField.setEchoChar('*'); 
        keepOriginalCheckBox.setSelected(true);
    }
}
