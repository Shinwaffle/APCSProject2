
public interface Level {
 
  /*
  here, implement your paintbrick method
  */
  public Rectangle[] paintBrick() {
    
  }
  
  /*
  return the speed of the game, aka the Thread.sleep(). this is the only way to change the ball speed
  */
  public int getBallSpeed() {
    return 10;
  }
  /*
  return the bat itself to use in-game.
  */
  public Rectangle getBat() {
    return new Rectangle(160, 245, 40, 5);
  }
  /*
  return the bat speed to modify its speed
  */
  return int getBatSpeed() {
   return 5; 
  }
}
