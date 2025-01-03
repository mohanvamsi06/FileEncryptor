import java.awt.event.*;
import java.io.*;
import javax.swing.*;

class TestUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("File Encryptor Test UI"); // Setting a title
        frame.setSize(800, 500); // Setting frame size
        frame.setLayout(null); // Using no layout managers
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit application on close

        // Creating a button to open file chooser
        JButton selectFileButton = new JButton("Select File");
        selectFileButton.setBounds(150, 75, 150, 30); // Setting button position and size
        frame.add(selectFileButton);

        // Creating a text field to display the selected file path
        JTextField fileTextField = new JTextField("No file selected");
        fileTextField.setBounds(350, 75, 300, 30); // Position and size
        fileTextField.setEditable(false); // Make it non-editable (optional)
        frame.add(fileTextField);

        // Creating two fields for password and confirm password
        // Label for Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(150, 150, 100, 30);
        frame.add(passwordLabel);

        // Password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(350, 150, 300, 30);
        frame.add(passwordField);

        // Label for Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(150, 200, 150, 30);
        frame.add(confirmPasswordLabel);

        // Confirm Password field
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(350, 200, 300, 30);
        frame.add(confirmPasswordField);

        // Creating Radio Buttons for Encryption Algorithms
        JRadioButton desButton = new JRadioButton("DES");
        desButton.setBounds(350, 270, 100, 30);
        desButton.setSelected(true);
        JRadioButton aesButton = new JRadioButton("AES");
        aesButton.setBounds(450, 270, 100, 30);
        JRadioButton blowfishButton = new JRadioButton("Blowfish");
        blowfishButton.setBounds(550, 270, 100, 30);

        // Grouping Radio Buttons to allow only one selection
        ButtonGroup algorithmGroup = new ButtonGroup();
        algorithmGroup.add(desButton);
        algorithmGroup.add(aesButton);
        algorithmGroup.add(blowfishButton);

        // Adding radio buttons to the frame
        frame.add(desButton);
        frame.add(aesButton);
        frame.add(blowfishButton);

        // Checkbox to keep original file
        JCheckBox keepOriginalCheckBox = new JCheckBox("Keep Original File");
        keepOriginalCheckBox.setBounds(350, 320, 200, 30);
        keepOriginalCheckBox.setSelected(true); // Checked by default
        frame.add(keepOriginalCheckBox);

        // Encrypt Button
        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(250, 380, 120, 40);
        frame.add(encryptButton);

        // Decrypt Button
        JButton decryptButton = new JButton("Decrypt");
        decryptButton.setBounds(400, 380, 120, 40);
        frame.add(decryptButton);

        // Adding an action listener to the button
        selectFileButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null); // Opens file chooser dialog
            
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                fileTextField.setText(selectedFile.getAbsolutePath()); // Update text field
                System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            }
        });

        // Handling Encryption
        encryptButton.addActionListener((ActionEvent e) -> {
                System.out.println("Encrption Started");
        });

        // Handling Decryption
        decryptButton.addActionListener((ActionEvent e) -> {
                System.out.println("Decrption Started");
        });

        frame.setVisible(true); // Making the frame visible
    }
}