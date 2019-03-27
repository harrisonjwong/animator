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

  /**
   * Adds a keyframe to this shape at the given time.
   * @param time integer telling what time to add the keyframe
   * @return a message to be displayed to the user, either confirming addition of keyframe, or
   *         an appropriate error message
   * @throws IllegalArgumentException if the time is less than 0
   */
  String addKeyframe(int time);

  /**
   * Edits a keyframe for this shape at the given time.
   * @param time integer telling what time to edit the keyframe at
   * @param info shape information to be displayed at the time
   * @return a message to be displayed to the user, either confirming editing of keyframe, or
   *         an appropriate error message
   * @throws IllegalArgumentException if the time is less than 0 or the shape info is null
   */
  String editKeyframe(int time, ShapeInfo info);

  /**
   * Deletes a keyframe for this shape at the given time.
   * @param time integer telling what time to delete the keyframe at
   * @return a message to be displayed to the user, either confirming the deletion,
   *         or an appropriate error message
   * @throws IllegalArgumentException if the time is less than 0
   */
  String deleteKeyframe(int time);

  /**
   * Tells whether the given time is a keyframe in this shape.
   * @param time integer telling what time to check
   * @return true if it is a keyframe, false otherwise
   * @throws IllegalArgumentException if the time is less than 0
   */
  boolean isKeyframe(int time);
}