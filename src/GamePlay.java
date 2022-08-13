import javax.swing.*;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean playing = false;
    private int score = 0;
    private int totalBricks = 21;

    private Timer timer;
    private int delay = 0;

    private int playerPosX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballDirectionX = -2;
    private int ballDirectionY = -3;

    private MapGenerator map;

    private long previousTime;

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        playing = true;
        timer = new Timer(delay, this);
        timer.start();
        previousTime = System.currentTimeMillis();
    }

    public void paint(Graphics gpc) {
        //Background
        gpc.setColor(Color.WHITE);
        gpc.fillRect(1, 1, 692, 592);

        //Map
        map.draw((Graphics2D)gpc);

        //Borders
        gpc.setColor(Color.LIGHT_GRAY);
        gpc.fillRect(0, 0, 3, 592);
        gpc.fillRect(0, 0, 692, 3);
        gpc.fillRect(680, 0, 3, 592);

        //Paddle
        gpc.setColor(Color.BLACK);
        gpc.fillRect(playerPosX, 550, 100, 8);

        //Ball
        gpc.setColor(Color.BLUE);
        gpc.fillOval(ballPosX, ballPosY, 20, 20);

        //Score display
        gpc.setColor(Color.BLACK);
        gpc.setFont(new Font("serif", Font.BOLD, 25));
        //Update score
        gpc.drawString("Score: " + score, 580, 30);

        //Time in seconds display
        long currentTime = System.currentTimeMillis();
        double elapsedTime = (currentTime - previousTime) / 1000.0;
        gpc.setFont(new Font("serif", Font.BOLD, 20));
        gpc.drawString("Time in seconds: " + (int)elapsedTime, 20, 30);

        //Winner
        if(totalBricks <= 0) {
            gameOver();

            gpc.setColor(Color.GREEN);
            gpc.setFont(new Font("serif", Font.BOLD, 30));
            gpc.drawString("You Won! Score: " + score, 190, 300);

            gpc.setFont(new Font("serif", Font.BOLD, 20));
            gpc.drawString("Press Enter to Restart", 230, 350);
        }

        //Loser
        if(ballPosY > 570) {
            gameOver();

            gpc.setColor(Color.RED);
            gpc.setFont(new Font("serif", Font.BOLD, 30));
            gpc.drawString("Game Over, Score: " + score, 190, 300);

            gpc.setFont(new Font("serif", Font.BOLD, 20));
            gpc.drawString("Press Enter to Restart", 230, 350);
        }

        gpc.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(playing) {
            // Ball / Paddle interaction
            if(new Rectangle(ballPosX, ballPosY, 20, 30).intersects(new Rectangle(playerPosX, 560, 100, 8))) {
                ballDirectionY = -ballDirectionY;
            }

            for(int i = 0; i < map.map.length; i++) {
                for(int j = 0; j < map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        int brickPosX = j * map.brickWidth + map.distanceFromSide;
                        int brickPosY = i * map.brickHeight + map.distanceFromTop;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle brickRectangle = new Rectangle(brickPosX, brickPosY, brickWidth, brickHeight);
                        Rectangle ballRectangle = new Rectangle(ballPosX, ballPosY, 20, 20);

                        // Ball / Brick interaction
                        if(ballRectangle.intersects(brickRectangle)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score++;

                            if(ballPosX + 19 <= brickRectangle.x || ballPosX + 1 <= brickRectangle.x + brickRectangle.width) {
                                ballDirectionX = -ballDirectionX;
                            } else {
                                ballDirectionY = - ballDirectionY;
                            }
                        }
                    }
                }
            }
            ballPosX += ballDirectionX;
            ballPosY += ballDirectionY;

            if(ballPosX < 0) {
                ballDirectionX = -ballDirectionX;
            }

            if(ballPosY < 0) {
                ballDirectionY = -ballDirectionY;
            }

            if(ballPosX > 670) {
                ballDirectionX = -ballDirectionX;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            if(playerPosX >= 600) {
                playerPosX = 600;
            } else {
                moveRight();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            if(playerPosX < 1) {
                playerPosX = 0;
            } else {
                moveLeft();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!playing) {
                restart();
                repaint();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
    }

    private void moveRight() {
        if(playing) {
            playerPosX += 20;
        }
    }

    private void moveLeft() {
        if(playing) {
            playerPosX -= 20;
        }
    }

    private void restart() {
        playing = true;
        previousTime = System.currentTimeMillis();
        timer.start();
        ballPosX = 120;
        ballPosY = 350;
        ballDirectionX = -2;
        ballDirectionY = -3;
        score = 0;
        totalBricks = 21;
        map = new MapGenerator(3, 7);
    }

    private void gameOver(){
        playing = false;
        ballDirectionX = 0;
        ballDirectionY = 0;
        timer.stop();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

