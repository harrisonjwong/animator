package cs3500.animator.model;

import cs3500.animator.model.motions.Motion;
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

}
