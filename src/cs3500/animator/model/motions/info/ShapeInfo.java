package cs3500.animator.model.motions.info;

import cs3500.animator.model.types.Color;
import cs3500.animator.model.types.Position2D;
import cs3500.animator.model.types.ShapeSize;

/**
 * Represents the attributes of a single shape. Allows you to retrieve a single attribute of the
 * shape.
 */
public interface ShapeInfo {

  /**
   * Gets the position of this shape.
   * @return the 2D position of this shape
   */
  Position2D getPosition();

  /**
   * Gets the size of this shape.
   * @return the size of this shape
   */
  ShapeSize getSize();

  /**
   * Gets the RGB values of this shape.
   * @return the color of this shape
   */
  Color getColor();

}
