package cs3500.animator.model.motions;

import cs3500.animator.model.motions.info.ShapeInfo;

/**
 * Represents a motion, which is a change of a single shape's properties over a certain
 * period of time. A motion has information about the shape's properties at a starting time, and
 * information about the shape's properties at an ending time.
 */
public interface Motion {

  /**
   * Returns a string representation of a motion containing the start
   * and end location, size, and color.
   *
   * @param name the name of the shape
   * @return the motion represented as a list of integers
   * @throws IllegalArgumentException if the given name is null
   */
  String motionAsText(String name);

  /**
   * Gets the ending time of this specific motion.
   * @return tick that this motion ends on
   */
  int getFinishTime();

  /**
   * Gets the shape information at the end of this motion.
   * @return the shape information this motion ends at
   */
  ShapeInfo getFinishInfo();

  /**
   * Gets the shape information at the start of this motion.
   * @return the shape information this motion starts at
   */
  ShapeInfo getStartInfo();

  /**
   * Gets the start time of this motion.
   * @return the integer start time of this motion in ticks
   */
  int getStartTime();

  /**
   * Tells whether the start time and information for this motion is the same as the given.
   * @param time the start time of this motion
   * @param info the shape information for this motion
   * @return true if the start time and information are the same
   */
  boolean sameStart(int time, ShapeInfo info);


}
