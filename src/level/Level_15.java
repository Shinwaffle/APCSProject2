package level;

import java.awt.*;
import java.util.ArrayList;

public class Level_15 implements Level{

    ArrayList<ArrayList<Integer>> color = new ArrayList<>();

    @Override
    public ArrayList<Rectangle> paintBrick() {
        ArrayList<Rectangle> Brick = new ArrayList<>();
        int brickx = 55;
        int bricky = 50;
        int index = 17;

        for (int i = 0; i < 48; i++) {
            Brick.add(new Rectangle(brickx, bricky, 15, 10));
            if (i == index) {
                brickx = 55;
                bricky += 12;
                index = index + 12;
            }
            brickx += 17;

        }
        return Brick;
    }

    @Override
    public int getBallSpeed() {
        return 7;
    }

    @Override
    public Rectangle getBat() {
        return new Rectangle(160, 245, 20, 5);
    }

    @Override
    public int getBatSpeed() {
        return 4;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getColors() {
        for (int i = 0; i < 48; i++) {
        ArrayList<Integer> colorIndex = new ArrayList<>();
            colorIndex.add((int) (Math.random()*256));
            colorIndex.add((int) (Math.random()*256));
            colorIndex.add((int) (Math.random()*256));
            color.add(colorIndex);
        }
        return color;
    }

    @Override
    public int getCode() {
        return 8392;
    }
}
