package com.mohanvamsi06.FileEncryptor.ui;

import java.awt.*;
import javax.swing.*;

public class MainUI {
    public static void LaunchUI() {
        JFrame frame = new JFrame("File Encryptor");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 500));

        // Create CardLayout
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Home Screen with Buttons
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        
        JButton encryptButton = new JButton("Encrypt");
        JButton decryptButton = new JButton("Decrypt");

        encryptButton.setPreferredSize(new Dimension(200, 50));
        decryptButton.setPreferredSize(new Dimension(200, 50));

        encryptButton.setFont(new Font("Arial", Font.BOLD, 18));
        decryptButton.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        homePanel.add(encryptButton, gbc);
        
        gbc.gridx = 1;
        homePanel.add(decryptButton, gbc);

        // Create Encryption and Decryption Screens
        JPanel encryptScreen = new EncryptScreen(cardLayout, mainPanel);
        JPanel decryptScreen = new DecryptScreen(cardLayout, mainPanel);

        // Add screens to the CardLayout
        mainPanel.add(homePanel, "Home");
        mainPanel.add(encryptScreen, "Encrypt");
        mainPanel.add(decryptScreen, "Decrypt");

        // Button Listeners to switch screens
        encryptButton.addActionListener(e -> cardLayout.show(mainPanel, "Encrypt"));
        decryptButton.addActionListener(e -> cardLayout.show(mainPanel, "Decrypt"));

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
