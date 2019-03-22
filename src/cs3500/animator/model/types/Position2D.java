package cs3500.animator.model.types;

import java.util.Objects;

/**
 * Represents the position of a shape on a 2D plane. A position is a value object that cannot be
 * mutated. A position contains an x and y position.
 */
public final class Position2D {

  /**
   * The x-position.
   */
  private final int x;

  /**
   * The y-position.
   */
  private final int y;

  /**
   * Constructs a Position2D with the given x and y values.
   * @param x the x-coordinate of the position
   * @param y the y-coordinate of the position
   */
  public Position2D(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns a textual representation of this 2D position.
   * @return a string containing the x and y values of this shape.
   */
  @Override
  public String toString() {
    return this.x + " " + this.y;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Position2D) {
      Position2D that = (Position2D)other;
      return that.x == this.x && that.y == this.y;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

  /**
   * Gets the x-value for this position.
   * @return the integer value for the x coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y-value for this position.
   * @return the integer value for the y-coordinate
   */
  public int getY() {
    return y;
  }


}
