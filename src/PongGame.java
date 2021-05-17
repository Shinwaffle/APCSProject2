import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class PongGame extends JPanel implements KeyListener,
        ActionListener, Runnable {
    private boolean start;

    static boolean right = false;
    static boolean left = false;

    int ballx = 160;
    int bally = 218;

    int batx = 160;
    int baty = 245;

    int brickx = 70;
    int bricky = 50;

    Rectangle Ball = new Rectangle(ballx, bally, 5, 5);
    Rectangle Bat = new Rectangle(batx, baty, 40, 5);

    int lives = 3;
    int amount = 12; //change this value to update amount of bricks
    ArrayList<Rectangle> Brick = new ArrayList<>();
    ArrayList<ArrayList<Float>> color = new ArrayList<>();

    public PongGame() {
        start = false;
        addKeyListener(this);
        setFocusable(true);
        Thread t = new Thread(this);
        t.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        PongGame game = new PongGame();
        JButton button = new JButton("restart");
        frame.setSize(350, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(game);
        frame.add(button, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        button.addActionListener(game);

    }


    @Override
    public void paint(Graphics g) {
        Font f = new Font("Arial", Font.BOLD, 10);
        g.setFont(f);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 350, 450);

        g.setColor(Color.GRAY);
        g.fillRect(0, 251, 450, 200);

        g.setColor(Color.red);
        g.drawRect(0, 0, 343, 250);

        g.setColor(Color.blue);
        g.fillOval(Ball.x, Ball.y, Ball.width, Ball.height);

        g.fill3DRect(Bat.x, Bat.y, Bat.width, Bat.height, true);

        for (int i = 0; i < Brick.size(); i++) {
            if (Brick.get(i) != null) {
                ArrayList<Float> colorIndex = color.get(i);
                g.setColor(new Color(colorIndex.get(0), colorIndex.get(1), colorIndex.get(2)));
                g.fill3DRect(Brick.get(i).x, Brick.get(i).y, Brick.get(i).width,
                        Brick(i).height, true);
            }
        }

        g.setColor(Color.white);
        /*
        I think there's some performance hits by concatenating the string so much but whatever
         */
        g.drawString("Bricks remaining: " + (Brick.length-count), 40, 300);
        g.drawString("Score: " + count, 40, 350);
        g.drawString("Paddles remaining: " + lives, 40, 325);

        if (ballFallDown || bricksOver) {
            f = new Font("Arial", Font.BOLD, 20);
            g.setFont(f);
            g.drawString(status, 70, 120);
            ballFallDown = true;
            bricksOver = true;
        }

    }

    public void paintBrick() {
        int index = 5;
        for (int i = 0; i < Brick.length; i++) {
            ArrayList<Float> colorIndex = new ArrayList<>();
            colorIndex.add((float) Math.random());
            colorIndex.add((float) Math.random());
            colorIndex.add((float) Math.random());
            color.add(colorIndex);
                
            Brick.add(new Rectangle(brickx, bricky, 30, 10));
            if (i == index) {
                brickx = 39;
                bricky += 12;
                index = index + 6;
            }

            brickx += 31;
        }
    }

    int movex = -1;
    int movey = -1;
    boolean ballFallDown = false;
    boolean bricksOver = false;
    int count = 0;

    String status;

    @Override
    public void run() {

        paintBrick();

        while (!ballFallDown && !bricksOver) {
            if (start) {

                if (count == Brick.length) {
                    status = "YOU WON THE GAME";
                    bricksOver = true;
                    repaint();
                }
                repaint();
                Ball.x += movex;
                Ball.y += movey;
                for (int i = 0; i < Brick.length; i++) {
                    if (Brick[i] != null && Brick[i].intersects(Ball)) {
                            Brick[i] = null;
                            movey = -movey;
                            count++;

                    }
                } //250
                if (Ball.y >= 250) {
                    if (lives != 0) {
                        lives--;
                        ballx = 160;
                        bally = 150;
                        movey = -movey;
                        movex = -movex;
                    } else {
                        status = "YOU LOST THE GAME";
                        ballFallDown = true;
                        repaint();
                    }
                }
                if (left) {

                    Bat.x -= 5;
                    right = false;
                }
                if (right) {
                    Bat.x += 5;
                    left = false;
                }
                if (Bat.x <= 4) {
                    Bat.x = 4;
                } else if (Bat.x >= 298) {
                    Bat.x = 298;
                }
                if (Ball.intersects(Bat)) {
                    movey = -movey;
                }

                if (Ball.x <= 0 || Ball.x + Ball.height >= 343) {
                    movex = -movex;
                }
                if (Ball.y <= 0) {
                    movey = -movey;
                }

            }
            try {
                Thread.sleep(8);
            } catch (Exception ex) {
            }

        }

    }

    public void restart() {

        requestFocus(true);
        ballx = 160;
        bally = 218;

        batx = 160;
        baty = 245;

        brickx = 70;
        bricky = 50;

        Ball = new Rectangle(ballx, bally, 5, 5);
        Bat = new Rectangle(batx, baty, 40, 5);
        Brick = new Rectangle[amount];

        movex = -1;
        movey = -1;
        ballFallDown = false;
        bricksOver = false;
        count = 0;
        status = null;

        paintBrick();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            left = true;
        }

        if (keyCode == KeyEvent.VK_RIGHT) {
            right = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            left = false;
        }

        if (keyCode == KeyEvent.VK_RIGHT) {
            right = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equals("restart")) {
            start = true;
            this.restart();
        }
    }
}


