package cs3500.animator.model.shapes;

import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.motions.info.ShapeInfo;
import java.util.ArrayList;

/**
 * Represents a shape, which is a part of an animation. A Shape has motions, which control the
 * movement of the shape in the animation.
 */
public interface Shape {

  /**
   * Creates and adds a new motion to this shape. A motion has a starting time, starting
   * shape information, ending time, and ending shape information.
   *
   * @throws IllegalArgumentException if the given motion does not match up with the end of the
   *         previous motion
   */
  void addMotion(Motion m);

  /**
   * Gets this shape's location as a textual list of movements.
   *
   * @return the list of movements for this shape
   */
  String animationsAsText();

  /**
   * Gets the name of this shape as text.
   *
   * @return the name of this shape
   */
  String getName();

  /**
   * Gets the type of this shape as text (like rectangle, oval).
   *
   * @return the type of this shape
   */
  String getType();

  /**
   * Gets the type of this shape as an SVG shape type.
   *
   * @return the SVG type of this shape.
   */
  String getSVGType();

  /**
   * Gets this shape's motions.
   *
   * @return the motions of this shape.
   */
  ArrayList<Motion> getMotions();

  /**
   * Gets the shape information at a certain time for this shape.
   * @param tick the tick at which to get shape information
   * @return the shape information at the given tick
   * @throws IllegalArgumentException if the shape does not have any motion information
   */
  ShapeInfo shapeInfoAtTime(int tick);

  /**
   * Gets the last tick of the last animation of this shape. Used to know when to end the animation
   * @return the last tick of the last animation of this shape
   */
  int getEnd();
}