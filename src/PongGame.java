import level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PongGame extends JPanel implements KeyListener,
        ActionListener, Runnable {
    private boolean start;

    static boolean right = false;
    static boolean left = false;

    List<Level> levels = getLevels();

    Rectangle Ball = new Rectangle(160, 218, 5, 5);
    Rectangle Bat;

    int lives = 3;
    int level = 0;
    boolean newLevelLoad = false;
    HashMap<Integer, Integer> codes = new HashMap<>();

    ArrayList<Rectangle> Brick = new ArrayList<>();
    Thread t;

    public PongGame() {
        Brick.addAll(levels.get(0).paintBrick());
        int i = 0;
        for (Level d : levels) {
            codes.put(d.getCode(), i);
            i++;
        } // i could restructure this to be way better but whatever its 11 pm
        Bat = levels.get(0).getBat();
        for (Integer s : codes.keySet()) {
            System.out.println(s);
        }
        start = false;
        addKeyListener(this);
        setFocusable(true);
        t = new Thread(this);
        t.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        PongGame game = new PongGame();
        JButton restartButton = new JButton("restart");
        JButton levelButton = new JButton("level selection");
        frame.setSize(350, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(game);
        frame.add(restartButton, BorderLayout.SOUTH);
        frame.add(levelButton, BorderLayout.NORTH);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        restartButton.addActionListener(game);
        levelButton.addActionListener(game);

    }

    /**
     * Loads levels in the level directory reflectively. Ignores Level.java and anything that doesn't start with .java
     * Also will throw a fit if the object in level doesn't implement Level
     *
     * @return list of levels
     */
    public List<Level> getLevels() {
        File path = new File(".\\src\\level");
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Level> temporaryLevelList = new ArrayList<>();
        for (String file : Objects.requireNonNull(path.list())) {
            if (file.equals("Level.java")) {
                continue;
            }
            if (file.endsWith(".java")) {
                list.add("level." + file.substring(0, file.length() - 5));
            }
        }
        for (String levelName : list) {
            try {
                Object name = Class.forName(levelName).getConstructor().newInstance();
                if (name instanceof Level) {
                    temporaryLevelList.add((Level) name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return temporaryLevelList;
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
            Ball = new Rectangle(160, 218, 5, 5);
            Brick = levels.get(level).paintBrick();
            movex = -1;
            movey = -1;
        }
        ArrayList<ArrayList<Integer>> colors = levels.get(level).getColors();
        for (int i = 0; i < Brick.size(); i++) {
            if (Brick.get(i) != null) {
                ArrayList<Integer> colorIndex = colors.get(i);
                g.setColor(new Color(colorIndex.get(0), colorIndex.get(1), colorIndex.get(2)));
                g.fill3DRect(Brick.get(i).x, Brick.get(i).y, Brick.get(i).width,
                        Brick.get(i).height, true);
            }
        }

        g.setColor(Color.white);
        /*
        I think there's some performance hits by concatenating the string so much but whatever
         */
        g.drawString("Bricks remaining: " + (Brick.size() - levelBrickCount), 40, 275);
        g.drawString("Score: " + totalCount, 40, 300);
        g.drawString("Paddles remaining: " + lives, 40, 325);
        g.drawString("Code: "+levels.get(level).getCode(), 40, 350);

        if (ballFallDown) {
            f = new Font("Arial", Font.BOLD, 20);
            g.setFont(f);
            g.drawString(status, 70, 120);
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
                requestFocus(); //when level selection is pressed, this allows the game to keep running
                int movespeed = levels.get(level).getBatSpeed();
                if (levelBrickCount == Brick.size()) {
                    lives++;
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
                        if (totalCount % 15 == 0) {
                            lives++;
                        }
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
        //this sequence keeps the thread alive after restarting, even with no user input
        while (ballFallDown) {
            try {
                Thread.sleep(1);
            } catch (Exception ex) {
            }
        }
        restart(level);
        run();
    }

    public void restart(int level) {

        requestFocus(true);
        Ball = new Rectangle(160, 218, 5, 5);
        Bat = levels.get(level).getBat();
        Brick = new ArrayList<>();
        Brick.addAll(levels.get(level).paintBrick());
        movex = -1;
        movey = -1;
        ballFallDown = false;
        bricksOver = false;
        lives = 3;
        totalCount = 0;
        levelBrickCount = 0;
        status = null;

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
            this.restart(level);
        }
        if (str.equals("level selection")) {

            while (true) {
                try {
                    String stringCode = JOptionPane.showInputDialog("Enter the code of the level");
                    if (stringCode == null) {
                        break;
                    }
                    if (stringCode.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "Either enter a valid code or if you don't have one simply close the window",
                                "Invalid Code",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    int code = Integer.parseInt(stringCode);
                    if (codes.containsKey(code)) {
                        newLevelLoad = true;
                        start = true;
                        level = codes.get(code);
                        restart(codes.get(code));
                        break;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Either enter a valid code or if you don't have one simply close the window",
                            "Invalid Code",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}


