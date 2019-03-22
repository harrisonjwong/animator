package cs3500.animator.view;

/**
 * Represents an animation view, which displays the data from a given model. An animation view
 * can be signaled to show on the screen or draw itself.
 */
public interface AnimationView {

  /**
   * Make the view visible. This is usually called after the view is constructed.
   * @throws IllegalArgumentException if there is a problem with creating the view
   */
  void makeVisible();

  /**
   * Signal the view to draw itself based on the current information it has.
   */
  void refresh();

}
