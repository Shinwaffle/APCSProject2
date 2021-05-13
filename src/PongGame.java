import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongGame extends JPanel implements KeyListener,
        ActionListener, Runnable {
    private boolean start;
    // movement keys..
    static boolean right = false;
    static boolean left = false;
    // variables declaration for ball...
    int ballx = 160;
    int bally = 218;
    // variables declaration for ball.
    // =======================================
    // variables declaration for bat..........
    int batx = 160;
    int baty = 245;
    // variables declaration for bat...........
    // ========================================
    // variables declaration for brick.........
    int brickx = 70;
    int bricky = 50;
    // variables declaration for brick...............................
    // ===============================================================
    // declaring ball, paddle,bricks
    Rectangle Ball = new Rectangle(ballx, bally, 5, 5);
    Rectangle Bat = new Rectangle(batx, baty, 40, 5);

    Rectangle[] Brick = new Rectangle[12];
    Thread t;

    public PongGame() {
        start = false;
        addKeyListener(this);
        setFocusable(true);
        t = new Thread(this);
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

    // declaring ball, paddle,bricks
    @Override
    public void paint(Graphics g) {
        //draw play area
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 350, 450);
        //draw bottom
        g.setColor(Color.GRAY);
        g.fillRect(0, 251, 450, 200);
        //draw outline
        g.setColor(Color.red);
        g.drawRect(0, 0, 343, 250);
        //draw ball (Task 1)
        //draw paddle (Task 2)
        //paint brick (Task 3)

        if (ballFallDown || bricksOver) {
            Font f = new Font("Arial", Font.BOLD, 20);
            g.setFont(f);
            g.drawString(status, 70, 120);
            ballFallDown = false;
            bricksOver = false;
        }

    }

    // /...Game Loop...................

    // /////////////////// When ball strikes borders......... it
    // reverses......==>
    int movex = -1;
    int movey = -1;
    boolean ballFallDown = false;
    boolean bricksOver = false;
    int count = 0;
    //use this string to report status
    String status;

    //run() method ONLY run at the beginning of program
    @Override
    public void run() {

        // //////////// =====Creating bricks for the game===>.....
        paintBrick();
        // ===========BRICKS created for the game new ready to use===

        // ====================================================
        // == ball reverses when touches the brick=======

        while (ballFallDown && bricksOver ) {
        // routine to create collision  (Task 4)

            if (start) {
                // /////////// =================================

                if (count == Brick.length) {// check if ball hits all bricks
                    bricksOver = true;
                    status = "YOU WON THE GAME";
                    repaint();
                }
                // /////////// =================================
                repaint();
                Ball.x += movex;
                Ball.y += movey;

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
                // /===== Ball reverses when strikes the bat
                if (Ball.intersects(Bat)) {
                    movey = -movey;
                    // if(Ball.y + Ball.width >=Bat.y)
                }
                // //=====================================
                // ....ball reverses when touches left and right boundary
                if (Ball.x <= 0 || Ball.x + Ball.height >= 343) {
                    movex = -movex;
                }// if ends here
                if (Ball.y <= 0) {// ////////////////|| bally + Ball.height >= 250
                    movey = -movey;
                }// if ends here.....
            //Game over routine (Task 5)

            }
            try {
                Thread.sleep(16); //change refresh speed
            } catch (Exception ex) {
            }// try catch ends here

        }// while loop ends here

    }

    // loop ends here

    // ///////..... HANDLING KEY EVENTS................//
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

    //Method to paint your brick pattern (Task 6)
    public void paintBrick() {

    }

    public void restart() {

        requestFocus(true);
        // ..............
        // variables declaration for ball.................................
        ballx = 160;
        bally = 218;
        // variables declaration for ball.................................
        // ===============================================================
        // variables declaration for bat..................................
        batx = 160;
        baty = 245;
        // variables declaration for bat..................................
        // ===============================================================
        // variables declaration for brick...............................
        brickx = 70;
        bricky = 50;
        // variables declaration for brick...............................
        // ===============================================================
        // declaring ball, paddle,bricks
        Ball = new Rectangle(ballx, bally, 5, 5);
        Bat = new Rectangle(batx, baty, 40, 5);
        // Rectangle Brick;// = new Rectangle(brickx, bricky, 30, 10);
        Brick = new Rectangle[12];

        movex = -1;
        movey = -1;
        ballFallDown = false;
        bricksOver = false;
        count = 0;
        status = null;

        paintBrick();
        repaint();
    }
}


