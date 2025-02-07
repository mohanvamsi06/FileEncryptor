# FileEncryptor

FileEncryptor is a Java-based application that provides file encryption and decryption functionalities using various algorithms such as AES, DES, and Blowfish.

## Features

- **Encrypt Files**: Secure your files using AES, DES, or Blowfish encryption algorithms.
- **Decrypt Files**: Restore your encrypted files to their original state.
- **User-Friendly Interface**: A graphical user interface (GUI) built with Java Swing for easy interaction.
- **Size Limit**: Files up to the size of 2GB (approx 2.14) can be Encrypted.

## Prerequisites

- **Java Development Kit (JDK)**: Ensure that JDK 8 or higher is installed on your system.

## Installation From Source

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/mohanvamsi06/FileEncryptor.git
   ```

2. **Navigate to the Project Directory**:

   ```bash
   cd FileEncryptor
   ```

3. **Compile the Source Code**:

   ```bash
   javac -d bin src/com/mohanvamsi06/FileEncryptor/**/*.java
   ```

4. **Create a JAR File**:

   ```bash
   jar cfe FileEncryptor.jar com.mohanvamsi06.FileEncryptor.main.Main -C bin .
   ```

## Usage

- **Run the Application**:

  ```bash
  java -jar FileEncryptor.jar
  ```

- **Using the GUI**:
  - Click on "Select File" to choose the file you want to encrypt or decrypt.
  - Enter and confirm the password.
  - Select the desired encryption algorithm (DES, AES, or Blowfish).
  - Choose whether to keep the original file.
  - Click "Encrypt" to encrypt the file or "Decrypt" to decrypt an encrypted file.

## License

This project is licensed under the MIT License. See the [LICENSE](https://github.com/mohanvamsi06/FileEncryptor/blob/main/LICENSE) file for details.

## Acknowledgments

This project utilizes Java's `javax.crypto` package for encryption and decryption functionalities.

For more information, visit the [GitHub repository](https://github.com/mohanvamsi06/FileEncryptor). 
