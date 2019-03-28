package cs3500.animator.model;

import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.shapes.Shape;

/**
 * Represents the model for a simple animation. An shape or an animation for a given shape can be
 * added to the animation. An animation model represents a full animation, including all
 * the necessary shapes and motions to draw on the screen.
 */
public interface AnimationModel extends ReadOnlyAnimationModel {

  /**
   * Adds the given shape to the animation, with the starting attributes.
   * @param s the shape to be added, which contains its name, and is created by its type
   * @throws IllegalArgumentException if the given shape is null or the name is not unique
   */
  void addShape(Shape s);

  /**
   * Adds a new animation starting at the end of the previous animation.
   * @param s the name of the shape to animate
   * @param m the given motion to add to the shape
   * @throws IllegalArgumentException if the arguments are null, the shape doesn't exist
   *         or the motion does not match the previous motion
   */
  void addAnimation(String s, Motion m);

  /**
   * Returns the animation as a textual list of movements.
   * @return the animation formatted as text
   */
  String animationAsText();

  /**
   * Adds a shape based on a name and type of shape. Different use case from void addShape, as
   * this method does not crash the program if the shape name is invalid.
   * @param name the name of the shape to be added
   * @param type the type of the shape to be added (Rectangle or Ellipse)
   * @return a message to be displayed to the user, either confirming the shape deletion or
   *         an appropriate error message
   * @throws IllegalArgumentException if either of the inputs are null
   */
  String addShape(String name, String type);

  /**
   * Deletes a shape based on a name.
   * @param name the name of the shape to be deleted
   * @return a message to be displayed to the user, either confirming the shape deletion or
   *         an appropriate error message
   * @throws IllegalArgumentException if the given string is null
   */
  String deleteShape(String name);

  /**
   * Adds a keyframe for a given shape name at a given time. If the shape is not found,
   * then a message is displayed in the return, but no error is thrown.
   * @param name the given shape name to add a keyframe for
   * @param time the time to add the keyframe at
   * @return a message to be displayed to the user, either confirming the keyframe addition or
   *         an appropriate error message
   * @throws IllegalArgumentException if the time is less than 0 or the name is null
   */
  String addKeyframe(String name, int time);

  /**
   * Edits a keyframe for a given shape name at a given time.
   * @param name the name of the shape to edit the keyframe for
   * @param time the time to edit the keyframe at
   * @param info the shape information to be placed at that keyframe
   * @return a message to be displayed to the user, either confirming the keyframe edit or
   *         an appropriate error message
   * @throws IllegalArgumentException if the time is less than 0, the shape name is null, or
   *         the shape info is null
   */
  String editKeyframe(String name, int time, ShapeInfo info);

  /**
   * Deletes a keyframe for a given shape at a given time.
   * @param name the name of the shape to delete the keyframe for
   * @param time the time to delete the keyframe at
   * @return a message to be displayed to the user, either confirming the deletion or
   *         an appropriate error message
   * @throws IllegalArgumentException if the given time is less than 0 or the name is null
   */
  String deleteKeyframe(String name, int time);

  /**
   * Tells whether the given shape has a keyframe at this time.
   * @param name name of the shape
   * @param time integer representing the tick at which to check
   * @return true if there is a keyframe at the given time, false otherwise
   * @throws IllegalArgumentException if the name is null or the time is less than 0
   */
  boolean isKeyframe(String name, int time);

}
