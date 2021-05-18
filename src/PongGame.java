import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import level.Level;

public class PongGame extends JPanel implements KeyListener,
        ActionListener, Runnable {
    private boolean start;

    static boolean right = false;
    static boolean left = false;

    List<Level> levels = getLevels();

    // the below should be altered by the Level

    Rectangle Ball = new Rectangle(160, 218, 5, 5);
    Rectangle Bat;

    int lives = 3;
    int level = 0;
    ArrayList<Rectangle> Brick = new ArrayList<>();

    public PongGame() {
        for (Rectangle b : levels.get(0).paintBrick()) {
            Brick.add(b);
        }
        Bat = levels.get(0).getBat();
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

    public List<Level> getLevels() {
        File path = new File(".\\src\\level");
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Level> levels = new ArrayList<>();
        for (String file : Objects.requireNonNull(path.list())) {
            if (file.equals("Level.java")) {
                continue;
            }
            if (file.endsWith(".java")) {
                list.add("level." + file.substring(0, file.length()-5));
            }
        }
        for (String levelName : list) {
            try {
                Object name = Class.forName(levelName).getConstructor().newInstance();
                if (name instanceof Level) {
                    levels.add((Level) name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return levels;
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
        /*
        this check is before the actual brick painting as this check loads the level's bricks
         */
        if (bricksOver) {
            bricksOver = false;
            level++;
            levelBrickCount = 0;
            Brick = levels.get(level).paintBrick();
        }
        for (int i = 0; i < Brick.size(); i++) {
            if (Brick.get(i) != null) {
                ArrayList<Float> colorIndex = levels.get(level).getColors().get(i);
                g.setColor(new Color(colorIndex.get(0), colorIndex.get(1), colorIndex.get(2)));
                g.fill3DRect(Brick.get(i).x, Brick.get(i).y, Brick.get(i).width,
                        Brick.get(i).height, true);
            }
        }

        g.setColor(Color.white);
        /*
        I think there's some performance hits by concatenating the string so much but whatever
         */
        g.drawString("Bricks remaining: " + (Brick.size()-levelBrickCount), 40, 300);
        g.drawString("Score: " + totalCount, 40, 350);
        g.drawString("Paddles remaining: " + lives, 40, 325);

        if (ballFallDown) {
            f = new Font("Arial", Font.BOLD, 20);
            g.setFont(f);
            g.drawString(status, 70, 120);
            ballFallDown = true;
            bricksOver = true;
        }

    }

    int movex = -1;
    int movey = -1;
    boolean ballFallDown = false;
    boolean bricksOver = false;
    int totalCount = 0; //used to count total amount of bricks destroyed in the game
    int levelBrickCount = 0;

    String status;

    @Override
    public void run() {

        levels.get(0).paintBrick();

        while (!ballFallDown && !bricksOver) {
            if (start) {
                int movespeed = levels.get(level).getBatSpeed();

                if (levelBrickCount == Brick.size()) {
                    bricksOver = true;
                    repaint();
                }
                repaint();
                Ball.x += movex;
                Ball.y += movey;
                for (int i = 0; i < Brick.size(); i++) {
                    if (Brick.get(i) != null && Brick.get(i).intersects(Ball)) {
                            Brick.set(i, null);
                            movey = -movey;
                            levelBrickCount++;
                            totalCount++;

                    }
                }
                if (Ball.y >= 250) {
                    if (lives != 0) {
                        lives--;
                        Ball.x = 160;
                        Ball.y = 150;
                        movey = -movey;
                        movex = -movex;
                    } else {
                        status = "YOU LOST THE GAME";
                        ballFallDown = true;
                        repaint();
                    }
                }
                if (left) {

                    Bat.x -= movespeed;
                    right = false;
                }
                if (right) {
                    Bat.x += movespeed;
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
                Thread.sleep(levels.get(level).getBallSpeed());
            } catch (Exception ex) {
            }

        }

    }

    public void restart() {

        requestFocus(true);

        Ball = new Rectangle(160, 218, 5, 5);
        Bat = levels.get(0).getBat();
        Brick = new ArrayList<>();
        for (Rectangle b : levels.get(0).paintBrick()) {
            Brick.add(b);
        }
        movex = -1;
        movey = -1;
        ballFallDown = false;
        bricksOver = false;
        totalCount = 0;
        status = null;

        levels.get(0).paintBrick();
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


