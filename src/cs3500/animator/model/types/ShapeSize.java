package cs3500.animator.model.types;

import java.util.Objects;

/**
 * Represents the size of a shape. Contains height and width, which both have to be greater than 0.
 * A shape size is a value object; it can't be mutated.
 */
public final class ShapeSize {

  /**
   * The height of the shape.
   */
  private final int height;

  /**
   * The width of the shape.
   */
  private final int width;

  /**
   * Constructs a ShapeSize with the given height and weight.
   * @param height the given height
   * @param width the given width
   * @throws IllegalArgumentException if the height or width are less than equal 0
   */
  public ShapeSize(int width, int height) {
    if (height < 0 || width < 0) {
      throw new IllegalArgumentException("the height or width are less than 0");
    }
    this.height = height;
    this.width = width;
  }

  /**
   * Returns a textual representation of this ShapeSize, which contains the height and width.
   * @return a String representing this ShapeSize
   */
  @Override
  public String toString() {
    return this.width + " " + this.height;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof ShapeSize) {
      ShapeSize that = (ShapeSize)other;
      return that.height == this.height && that.width == this.width;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.height, this.width);
  }

  /**
   * Gets the width value for this Shape Size.
   * @return the integer width value
   */
  public int getW() {
    return width;
  }

  /**
   * Gets the height value for this Shape Size.
   * @return the integer height value
   */
  public int getH() {
    return height;
  }
}
