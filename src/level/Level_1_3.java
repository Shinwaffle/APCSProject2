package level;

import java.awt.*;
import java.util.ArrayList;

public class Level_1_3 implements Level {

    ArrayList<ArrayList<Integer>> color = new ArrayList<>();

    @Override
    public ArrayList<Rectangle> paintBrick() {
        ArrayList<Rectangle> Brick = new ArrayList<>();
        int brickx = 70;
        int bricky = 50;
        int index = 5;

        for (int i = 0; i < 24; i++) {
            Brick.add(new Rectangle(brickx, bricky, 30, 10));
            if (i == index) {
                brickx = 39;
                bricky += 18;
                index = index + 6;
            }
            brickx += 31;

        }
        return Brick;
    }
    @Override
    public int getBallSpeed() {
        return 9;
    }

    @Override
    public Rectangle getBat() {
        return new Rectangle(150, 245, 30, 5);
    }

    @Override
    public int getBatSpeed() {
        return 3;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getColors() {
        if (color.size() != 24) {
            int count = 1;
            for (int i = 0; i < 24; i++) {
                ArrayList<Integer> colorIndex = new ArrayList<>();
                if (i <= 5) {
                    colorIndex.add(0+count);
                    colorIndex.add(0+count);
                    colorIndex.add(0);
                    count += 5;
                    color.add(colorIndex);
                } else if (i <= 11) {
                    colorIndex.add(0+count);
                    colorIndex.add(0+count);
                    colorIndex.add(0);
                    count += 5;
                    color.add(colorIndex);
                } else if (i <= 17) {
                    colorIndex.add(0+count);
                    colorIndex.add(0+count);
                    colorIndex.add(0);
                    count += 5;
                    color.add(colorIndex);
                } else {
                    colorIndex.add(0+count);
                    colorIndex.add(0+count);
                    colorIndex.add(0);
                    count += 5;
                    color.add(colorIndex);
                }
            }
        }
        return color;
    }

    @Override
    public int getCode() {
        return 9425;
    }
}
