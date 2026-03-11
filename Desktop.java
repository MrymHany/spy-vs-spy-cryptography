package CryptoProject2;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class EncryptionChallengeGameGUI {
    private JFrame frame1, frame2;
    private JTextField messageField1, keyField1, encryptedField1;
    private JTextField messageField2, keyField2, encryptedField2;
    private JTextField decryptionKeyField1, decryptionKeyField2;
    private JComboBox<String> algoComboBox1, algoComboBox2;
    private JLabel scoreLabel, timerLabel, hintLabel1, hintLabel2,mLabel;
    private JButton encryptButton1, sendButton1, decryptButton1;
    private JButton encryptButton2, sendButton2, decryptButton2;
    private String encryptedMessage, encryptionKey, selectedAlgorithm;
    private int player1Score = 0, player2Score = 0;
    private int currentTurn = 1;
    private static final int TIME_LIMIT_SECONDS = 60;
    private Timer timer;

    public EncryptionChallengeGameGUI() {
        initializePlayerFrames();
        initializeScoreAndTimer();
    }

    private void initializePlayerFrames() {
        // Player 1 Frame
        frame1 = createPlayerFrame("Player 1", true);
        // Player 2 Frame
        frame2 = createPlayerFrame("Player 2", false);
    }

    private JFrame createPlayerFrame(String playerName, boolean isPlayer1) {
        JFrame frame = new JFrame(playerName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBackground(new Color(255, 228, 181));

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel(playerName, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        JLabel hintLabel = new JLabel("Hint: ", SwingConstants.LEFT);
        if (isPlayer1) {
            hintLabel1 = hintLabel;
        } else {
            hintLabel2 = hintLabel;
        }

        // Use JPasswordField for sensitive input fields
        JPasswordField messageField = new JPasswordField(20);  
        JPasswordField keyField = new JPasswordField(20);
        JTextField encryptedField = new JTextField(20);
        encryptedField.setEditable(false);
        JTextField decryptionKeyField = new JTextField(20);

        JComboBox<String> algoComboBox = new JComboBox<>(new String[]{"AES", "DES"});

        JButton encryptButton = new JButton("Encrypt");
        JButton sendButton = new JButton("Send");
        JButton decryptButton = new JButton("Decrypt");
        JButton resetButton = new JButton("Reset");
        sendButton.setEnabled(false);
        decryptButton.setEnabled(false);

        encryptButton.setBackground(new Color(153, 204, 255));
        sendButton.setBackground(new Color(152, 200, 152));
        decryptButton.setBackground(new Color(153, 204, 255));
        resetButton.setBackground(new Color(255, 153, 160));
        encryptButton.setForeground(Color.black);
        sendButton.setForeground(Color.black);
        decryptButton.setForeground(Color.black);
        resetButton.setForeground(Color.BLACK);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(hintLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Enter Message:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(messageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Enter Key:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(keyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Algorithm:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(algoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Encrypted Message:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(encryptedField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(new JLabel("Decryption Key:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        panel.add(decryptionKeyField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        panel.add(encryptButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        panel.add(sendButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 9;
        panel.add(decryptButton, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 10;
        panel.add(resetButton, gbc);      

        frame.add(panel);

        if (isPlayer1) {
            messageField1 = messageField;
            keyField1 = keyField;
            encryptedField1 = encryptedField;
            decryptionKeyField1 = decryptionKeyField;
            algoComboBox1 = algoComboBox;
            encryptButton1 = encryptButton;
            sendButton1 = sendButton;
            decryptButton1 = decryptButton;

            encryptButton1.addActionListener(e -> encryptMessage(messageField1, keyField1, algoComboBox1, encryptedField1, sendButton1));
            sendButton1.addActionListener(e -> sendMessage());
            decryptButton1.addActionListener(e -> decryptMessage(decryptionKeyField1));
            resetButton.addActionListener(e -> resetFields(messageField1, keyField1, encryptedField1, decryptionKeyField1, encryptButton1, sendButton1, decryptButton1)); // Reset functionality
        } else {
            messageField2 = messageField;
            keyField2 = keyField;
            encryptedField2 = encryptedField;
            decryptionKeyField2 = decryptionKeyField;
            algoComboBox2 = algoComboBox;
            encryptButton2 = encryptButton;
            sendButton2 = sendButton;
            decryptButton2 = decryptButton;

            encryptButton2.addActionListener(e -> encryptMessage(messageField2, keyField2, algoComboBox2, encryptedField2, sendButton2));
            sendButton2.addActionListener(e -> sendMessage());
            decryptButton2.addActionListener(e -> decryptMessage(decryptionKeyField2));
            resetButton.addActionListener(e -> resetFields(messageField2, keyField2, encryptedField2, decryptionKeyField2, encryptButton2, sendButton2, decryptButton2)); // Reset functionality
        }

        frame.setVisible(true);
        return frame;
    }
    

    private void initializeScoreAndTimer() {
        JFrame scoreFrame = new JFrame("Score and Timer");
        scoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scoreFrame.setSize(600, 100);
     scoreFrame.getContentPane().setBackground(new Color(255, 228, 181));
        

        scoreLabel = new JLabel("Player 1: 0 | Player 2: 0", SwingConstants.CENTER);
        timerLabel = new JLabel("Time Left: " + TIME_LIMIT_SECONDS + "s", SwingConstants.CENTER);
        mLabel = new JLabel("Welcome to the Encryption Challenge!  First player to score 5 points wins.",SwingConstants.CENTER);

        scoreFrame.add(mLabel, BorderLayout.NORTH);
        scoreFrame.add(scoreLabel, BorderLayout.CENTER);
        scoreFrame.add(timerLabel, BorderLayout.SOUTH);

        scoreFrame.setVisible(true);
    }

    private void encryptMessage(JTextField messageField, JTextField keyField, JComboBox<String> algoComboBox, JTextField encryptedField, JButton sendButton) {
        String message = messageField.getText();
        encryptionKey = keyField.getText();
        selectedAlgorithm = (String) algoComboBox.getSelectedItem();

        if (message.isEmpty() || encryptionKey.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields!");
            return;
        }

        try {
            encryptedMessage = EncryptionChallenge.encrypt(message, encryptionKey, selectedAlgorithm);
            encryptedField.setText(encryptedMessage);
            sendButton.setEnabled(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Encryption Error: " + ex.getMessage());
        }
    }

    private void sendMessage() {
        String hint = "Hint: " + encryptionKey.substring(0, Math.min(4, encryptionKey.length())) + " (" + selectedAlgorithm + ")";

        if (currentTurn == 1) {
            hintLabel2.setText(hint);
            encryptedField2.setText(encryptedMessage);
            decryptButton2.setEnabled(true);
            sendButton1.setEnabled(false);
            encryptButton1.setEnabled(false);
        } else {
            hintLabel1.setText(hint);
            encryptedField1.setText(encryptedMessage);
            decryptButton1.setEnabled(true);
            sendButton2.setEnabled(false);
            encryptButton2.setEnabled(false);
        }

        JOptionPane.showMessageDialog(null, "Message Sent! Player " + (currentTurn == 1 ? 2 : 1) + "'s Turn.");
        startTimer();
    }

    private void decryptMessage(JTextField decryptionKeyField) {
        stopTimer();  // Stop the timer as soon as the decrypt button is clicked

        String guessedKey = decryptionKeyField.getText();
        if (guessedKey.equals(encryptionKey)) {
            try {
                String decryptedMessage = EncryptionChallenge.decrypt(encryptedMessage, guessedKey, selectedAlgorithm);
                JOptionPane.showMessageDialog(null, "Decryption Successful: " + decryptedMessage);
                if (currentTurn == 1) {
                    player2Score++;
                } else {
                    player1Score++;
                }
                updateScore();
                resetTurn();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Decryption Error: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect Key!");
            resetTurn();
        }
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int timeLeft = TIME_LIMIT_SECONDS;

            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> timerLabel.setText("Time Left: " + timeLeft + "s"));
                timeLeft--;
                if (timeLeft < 0) {
                    timer.cancel();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "Time's Up! Next Turn.");
                        resetTurn();
                    });
                }
            }
        }, 0, 1000);
    }
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;  // Reset the timer to null to allow for a new timer to be started later
        }
    }
    private void updateScore() {
        scoreLabel.setText("Player 1: " + player1Score + " | Player 2: " + player2Score);
        if (player1Score >= 5) {
            JOptionPane.showMessageDialog(null, "Player 1 Wins!");
            resetGame();
        } else if (player2Score >= 5) {
            JOptionPane.showMessageDialog(null, "Player 2 Wins!");
            resetGame();
        }
    }

    private void resetGame() {
        player1Score = 0;
        player2Score = 0;
        updateScore();
        resetTurn();
    }
    private void resetFields(JTextField messageField, JTextField keyField, JTextField encryptedField, JTextField decryptionKeyField, JButton encryptButton, JButton sendButton, JButton decryptButton) {
        messageField.setText("");
        keyField.setText("");
        encryptedField.setText("");
        decryptionKeyField.setText("");
        encryptButton.setEnabled(true);
        sendButton.setEnabled(false);
        decryptButton.setEnabled(false);
    }
    private void resetTurn() {
        currentTurn = (currentTurn == 1) ? 2 : 1;

        messageField1.setText("");
        keyField1.setText("");
        encryptedField1.setText("");
        decryptionKeyField1.setText("");
        messageField2.setText("");
        keyField2.setText("");
        encryptedField2.setText("");
        decryptionKeyField2.setText("");

        encryptButton1.setEnabled(true);
        encryptButton2.setEnabled(true);
        sendButton1.setEnabled(false);
        sendButton2.setEnabled(false);
        decryptButton1.setEnabled(false);
        decryptButton2.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EncryptionChallengeGameGUI::new);
    }
}