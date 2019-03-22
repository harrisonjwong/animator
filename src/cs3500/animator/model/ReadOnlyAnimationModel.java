package cs3500.animator.model;

import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.shapes.Shape;
import java.util.HashMap;

/**
 * This is the read only version of the animation model. It contains methods that allow access of
 * data inside the model. A model contains the information for one full animation, including one
 * or several shapes over a given time.
 */
public interface ReadOnlyAnimationModel {

  /**
   * Gets the shape information at the given tick.
   * @param s the name of the shape you are searching
   * @param tick the tick at which you are getting information for the shape
   * @throws IllegalArgumentException if the name is invalid, or the time is invalid
   */
  ShapeInfo shapeInfoAtTime(String s, int tick);

  /**
   * Get the map of shapes in this animation.
   * @return the map of the shapes in this animation
   */
  HashMap<String, Shape> getShapes();

  /**
   * Gets the last tick of the animation, when the animation ends.
   * @return the last tick of the animation
   * @throws IllegalArgumentException is
   */
  int getEndingTick();

  /**
   * Gets the x-location of the window.
   * @return the pixel int for the x location of the window
   */
  int getWindowX();

  /**
   * Gets the y-location of the window.
   * @return the pixel int for the y location of the window
   */
  int getWindowY();

  /**
   * Gets the width of the window.
   * @return the pixel int width of the window
   */
  int getWindowWidth();

  /**
   * Gets the height of the window.
   * @return the pixel int height of the window
   */
  int getWindowHeight();


}
