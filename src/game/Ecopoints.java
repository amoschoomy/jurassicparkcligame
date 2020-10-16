package game;

/**
 * Class for Player's ecopoint
 *
 * @author Amos
 */
public class Ecopoints {
  private int points = 0;

  public void gain(int point) {
    if (point < 0) throw new IllegalArgumentException("Point less than zero!");
    points += point;
  }

  /**
   * spend Player's ecopoint
   *
   * @param point . int value
   * @throws IllegalArgumentException if point less than zero
   */
  public void spend(int point) {
    if (point < 0) throw new IllegalArgumentException("Point less than zero!");
    points -= point;
  }

  /**
   * get Player ecopoints
   *
   * @return int value of points
   */
  public int getPoints() {
    return points;
  }

  @Override
  public String toString() {
    return "Ecopoints{" + "points=" + points + '}';
  }
}
