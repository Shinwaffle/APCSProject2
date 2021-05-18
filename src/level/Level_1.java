package level;

import java.awt.*;
import java.util.ArrayList;

public class Level_1 implements Level{

    ArrayList<ArrayList<Float>> color = new ArrayList<>();

    @Override
    public ArrayList<Rectangle> paintBrick() {
        ArrayList<Rectangle> Brick = new ArrayList<>();
        int brickx = 70;
        int bricky = 50;
        int index = 5;

        for (int i = 0; i < 12; i++) {
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
        return Brick;
    }

    @Override
    public int getBallSpeed() {
        return 6;
    }

    @Override
    public Rectangle getBat() {
        return new Rectangle(160, 245, 40, 5);
    }

    @Override
    public int getBatSpeed() {
        return 3;
    }

    @Override
    public ArrayList<ArrayList<Float>> getColors() {
        return color == null ? new ArrayList<>() : color;
    }
}
