package game;

public class Ecopoints {
  private int points = 0;

  public void gain(int point) {
    if (point < 0) throw new IllegalArgumentException("Point less than zero!");
    points += point;
  }

  public void spend(int point) {
    if (point < 0) throw new IllegalArgumentException("Point less than zero!");
    points -= point;
  }

  @Override
  public String toString() {
    return "Ecopoints{" + "points=" + points + '}';
  }
}
