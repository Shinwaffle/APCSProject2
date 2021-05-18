package level;

import java.awt.*;
import java.util.ArrayList;

public interface Level {

  /*
  here, implement your paintbrick method
  */
  ArrayList<Rectangle> paintBrick();

  /*
  return the speed of the game, aka the Thread.sleep(). this is the only way to change the ball speed
  */
  int getBallSpeed();

  /*
  return the bat itself to use in-game.
  */
  Rectangle getBat();

  /*
  return the bat speed to modify its speed
  */
  int getBatSpeed();
  /*
  Returns an arraylist of arraylists, each arraylist having an r, g, and b value.
   */
  ArrayList<ArrayList<Float>> getColors();
}
