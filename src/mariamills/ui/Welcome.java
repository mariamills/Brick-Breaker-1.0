package mariamills.ui;

import mariamills.gameplay.GamePlay;

import javax.swing.*;
import java.awt.*;

public class Welcome extends JFrame {
    private JFrame frame;

    public Welcome() {
        frame = new JFrame();

        // Set up the frame
        frame.setBounds(10, 10, 700, 600);
        frame.setTitle("Brick Breaker 1.0 - Maria Mills");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create the welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Brick Breaker!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("serif", Font.BOLD, 25));
        frame.add(welcomeLabel, BorderLayout.NORTH);

        // Add game instructions
        JTextArea instructions = new JTextArea(
                """
                        Instructions:
                        1. Press the left and right arrow keys to move the paddle.
                        2. Prevent the ball from falling below the paddle.
                        3. Break all the bricks to win!
                        """
        );
        instructions.setFont(new Font("serif", Font.PLAIN, 18));
        instructions.setWrapStyleWord(true);
        instructions.setLineWrap(true);
        instructions.setOpaque(false);
        instructions.setEditable(false);
        instructions.setFocusable(false);
        instructions.setMargin(new Insets(10, 50, 50, 50));
        frame.add(instructions, BorderLayout.CENTER);

        // Create the Play button
        JButton playButton = new JButton("Play");
        playButton.setPreferredSize(new Dimension(150, 50));
        playButton.addActionListener(e -> startGame());

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(150, 50));
        exitButton.addActionListener(e -> frame.dispose());

        // Center the button on the screen
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(playButton);
        buttonPanel.add(exitButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void startGame() {
        frame.dispose();  // Close the welcome screen
        JFrame gameFrame = new JFrame();
        GamePlay gamePlay = new GamePlay();
        gameFrame.setBounds(10, 10, 700, 600);
        gameFrame.setTitle("Brick Breaker 1.0 - Maria Mills");
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(gamePlay);
        gameFrame.setVisible(true);
    }
}

