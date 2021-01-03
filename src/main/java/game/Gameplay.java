package game;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private int[] snakeXLength = new int[750];
    private int[] snakeYLength = new int[750];
    private int[] appleXpos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450,
        475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850};
    private int[] appleYpos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500,
        525, 550, 575, 600, 625};
    private int lengthOfSnake = 3;
    private int score = 0;
    private int moves = 0;

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private final BufferedImage titleImage;
    private final BufferedImage snakeImage;
    private final BufferedImage appleIcon;
    private final BufferedImage rightMouth;
    private final BufferedImage upMouth;
    private final BufferedImage downMouth;
    private final BufferedImage leftMouth;

    private Timer timer;
    private int delay = 100;
    private Random random = new Random();
    private int xpos = random.nextInt(34);
    private int ypos = random.nextInt(23);

    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);

        timer = new Timer(delay, this);
        timer.start();

        // read all images
        try {
            titleImage = ImageIO.read(Gameplay.class.getResourceAsStream("/images/TitluSnake.png"));
            snakeImage = ImageIO.read(Gameplay.class.getResourceAsStream("/images/SnakeImage.png"));
            appleIcon = ImageIO.read(Gameplay.class.getResourceAsStream("/images/Apple.png"));
            rightMouth = ImageIO.read(Gameplay.class.getResourceAsStream("/images/RightMouth.png"));
            upMouth = ImageIO.read(Gameplay.class.getResourceAsStream("/images/UpMouth.png"));
            downMouth = ImageIO.read(Gameplay.class.getResourceAsStream("/images/DownMouth.png"));
            leftMouth = ImageIO.read(Gameplay.class.getResourceAsStream("/images/LeftMouth.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void paint(Graphics g) {
        if (moves == 0) {
            snakeXLength[2] = 50;
            snakeXLength[1] = 75;
            snakeXLength[0] = 100;

            snakeYLength[2] = 100;
            snakeYLength[1] = 100;
            snakeYLength[0] = 100;
        }

        // draw title image border
        g.setColor(Color.white);
        g.drawRect(24, 10, 851, 55);

        // draw title image
        g.drawImage(titleImage, 25, 11, null);

        // draw border for gameplay
        g.setColor(Color.white);
        g.drawRect(24, 74, 851, 577);

        // draw background for the gameplay
        g.setColor(Color.black);
        g.fillRect(25, 75, 850, 575);

        // draw scores
        g.setColor(Color.white);
        g.setFont(new Font("Clibri", Font.PLAIN, 14));
        g.drawString("Scores:" + score, 780, 30);

        // draw length
        g.setColor(Color.white);
        g.setFont(new Font("Clibri", Font.PLAIN, 14));
        g.drawString("Length:" + lengthOfSnake, 780, 50);

        g.drawImage(rightMouth, snakeXLength[0], snakeYLength[0], null);

        for (int i = 0; i < lengthOfSnake; i++) {
            int x = snakeXLength[i];
            int y = snakeYLength[i];

            if (i == 0) {
                if (right) {
                    g.drawImage(rightMouth, x, y, null);
                } else if (left) {
                    g.drawImage(leftMouth, x, y, null);
                } else if (up) {
                    g.drawImage(upMouth, x, y, null);
                } else if (down) {
                    g.drawImage(downMouth, x, y, null);
                }
            } else {
                g.drawImage(snakeImage, x, y, null);
            }
        }

        if ((appleXpos[xpos] == snakeXLength[0] && appleYpos[ypos] == snakeYLength[0])) {
            score++;
            lengthOfSnake++;
            xpos = random.nextInt(34);
            ypos = random.nextInt(23);
        }
        g.drawImage(appleIcon, appleXpos[xpos], appleYpos[ypos], null);

        for (int i = 1; i < lengthOfSnake; i++) {
            if (snakeXLength[i] == snakeXLength[0] && snakeYLength[i] == snakeYLength[0]) {
                left = false;
                up = false;
                right = false;
                down = false;

                g.setColor(Color.white);
                g.setFont(new Font("arial", Font.BOLD, 50));
                g.drawString("Game Over", 300, 300);

                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString("Space to RESTART", 350, 340);
            }
        }
        g.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            moves = 0;
            score = 0;
            lengthOfSnake = 3;
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moves++;
            right = true;
            if (!left) {
                right = true;
            } else {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moves++;
            left = true;
            if (!right) {
                left = true;
            } else {
                right = true;
                left = false;
            }
            up = false;
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            moves++;
            up = true;
            if (!down) {
                up = true;
            } else {
                down = true;
                up = false;
            }
            left = false;
            right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moves++;
            down = true;
            if (!up) {
                down = true;
            } else {
                down = false;
                up = true;
            }
            left = false;
            right = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public boolean keyDown(Event evt, int key) {
        return super.keyDown(evt, key);
    }

    @Override
    public boolean keyUp(Event evt, int key) {
        return super.keyUp(evt, key);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (right) {
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakeYLength[i + 1] = snakeYLength[i];
                if (i == 0) {
                    snakeXLength[i] = snakeXLength[i] + 25;
                } else {
                    snakeXLength[i] = snakeXLength[i - 1];
                }
                if (snakeXLength[i] > 850) {
                    snakeXLength[i] = 25;
                }
            }
            repaint();
        }
        if (left) {
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakeYLength[i + 1] = snakeYLength[i];
                if (i == 0) {
                    snakeXLength[i] = snakeXLength[i] - 25;
                } else {
                    snakeXLength[i] = snakeXLength[i - 1];
                }
                if (snakeXLength[i] < 25) {
                    snakeXLength[i] = 850;
                }
            }
            repaint();
        }
        if (up) {
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakeXLength[i + 1] = snakeXLength[i];
                if (i == 0) {
                    snakeYLength[i] = snakeYLength[i] - 25;
                } else {
                    snakeYLength[i] = snakeYLength[i - 1];
                }
                if (snakeYLength[i] < 75) {
                    snakeYLength[i] = 625;
                }
            }
            repaint();
        }
        if (down) {
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakeXLength[i + 1] = snakeXLength[i];
                if (i == 0) {
                    snakeYLength[i] = snakeYLength[i] + 25;
                } else {
                    snakeYLength[i] = snakeYLength[i - 1];
                }
                if (snakeYLength[i] > 625) {
                    snakeYLength[i] = 75;
                }
            }
            repaint();
        }
    }

    private String getResourceFullPath(String relativePath) {
        return getClass().getResource(relativePath).getPath();
    }

}
