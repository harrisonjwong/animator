package cs3500.animator.model.motions.info;

import cs3500.animator.model.types.Color;
import cs3500.animator.model.types.Position2D;
import cs3500.animator.model.types.ShapeSize;
import java.util.Objects;

/**
 * Represents the shape information for a single shape, which includes its position, size,
 * and color. Shape information is represented by 3 objects, one for position, one for size,
 * and one for color.
 */
public final class ShapeInfoImpl implements ShapeInfo {

  /**
   * Position of the shape.
   */
  private final Position2D position;

  /**
   * Size of the shape.
   */
  private final ShapeSize size;

  /**
   * Color of the shape.
   */
  private final Color color;

  /**
   * Constructs a ShapeInfoImpl with a given position, size, and color.
   * @param position the position of the shape
   * @param size the size of the shape
   * @param color the color of the shape
   */
  public ShapeInfoImpl(Position2D position, ShapeSize size, Color color) {
    if (position == null || size == null || color == null) {
      throw new IllegalArgumentException("invalid shape info containing null");
    }
    this.position = position;
    this.size = size;
    this.color = color;
  }

  @Override
  public Position2D getPosition() {
    return position;
  }

  @Override
  public ShapeSize getSize() {
    return size;
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof ShapeInfo) {
      ShapeInfo that = (ShapeInfo)other;
      return that.getPosition().equals(this.position)
          && that.getColor().equals(this.color)
          && that.getSize().equals(this.size);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.position, this.size, this.color);
  }
}
