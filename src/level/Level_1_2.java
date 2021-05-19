package level;

import java.awt.*;
import java.util.ArrayList;

public class Level_1_2 implements Level{

    ArrayList<ArrayList<Integer>> color = new ArrayList<>();

    @Override
    public ArrayList<Rectangle> paintBrick() {
        ArrayList<Rectangle> Brick = new ArrayList<>();
        int brickx = 70;
        int bricky = 50;
        int index = 5;

        for (int i = 0; i < 30; i++) {
            Brick.add(new Rectangle(brickx, bricky, 30, 10));
            if (i == index) {
                brickx = 39;
                bricky += 12;
                index = index + 6;
            }
            brickx += 31;

        }
        return Brick;
    }

    @Override
    public int getBallSpeed() {
        return 6;
    }

    @Override
    public Rectangle getBat() {
        return new Rectangle(160, 245, 35, 10);
    }

    @Override
    public int getBatSpeed() {
        return 3;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getColors() {
        if (color.size() != 30) {
            for (int i = 0; i < 30; i++) {
                if (i <= 11) {
                    ArrayList<Integer> colorIndex = new ArrayList<>();
                    colorIndex.add(209);
                    colorIndex.add(33);
                    colorIndex.add(115);
                    color.add(colorIndex);

                } else if (i <= 17) {
                    ArrayList<Integer> colorIndex = new ArrayList<>();
                    colorIndex.add(179);
                    colorIndex.add(104);
                    colorIndex.add(139);
                    color.add(colorIndex);

                } else {
                    ArrayList<Integer> colorIndex = new ArrayList<>();
                    colorIndex.add(96);
                    colorIndex.add(96);
                    colorIndex.add(209);
                    color.add(colorIndex);
                }
            }
        }
        return color;
    }

    @Override
    public int getCode() {
        return 2705;
    }
}
