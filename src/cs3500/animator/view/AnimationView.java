package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import java.awt.event.ActionListener;

/**
 * Represents an animation view, which displays the data from a given model. An animation view
 * can be signaled to show on the screen or draw itself.
 */
public interface AnimationView {

  /**
   * Make the view visible. This is usually called after the view is constructed.
   *
   * @throws IllegalArgumentException if there is a problem with creating the view
   */
  void makeVisible();

  /**
   * Signal the view to draw itself based on the current information it has.
   * @throws UnsupportedOperationException for text and svg views, as the cannot be refreshed
   */
  void refresh();

  /**
   * Signals the view to set reset focus on the full application window.
   * @throws UnsupportedOperationException for text and svg views, as focus cannot be reset on text
   */
  void resetFocus();

  /**
   * Adds the action listener to buttons in the edit view.
   * @param listener the ActionListener to be added to the buttons
   * @throws UnsupportedOperationException for text, visual, and svg views, as they don't respond
   *         to input from the user
   * @throws IllegalArgumentException if the given actionlistener is null
   */
  void addActionListener(ActionListener listener);

  /**
   * Gets the {@link ViewType} of the given animation view. Unfortunately necessary so that
   * the controller can respond appropriately to the type of view it has
   * (ex: visual and edit views need timing, while the text and svg views don't).
   * @return the {@link ViewType} representing the view
   */
  ViewType getViewType();

  /**
   * Gets the {@link AnimationPanel} for the visual or edit view.
   * @return the {@link AnimationPanel} showing the animation
   * @throws UnsupportedOperationException for text, svg views, as they don't have a panel
   */
  AnimationPanel getPanel();

  /**
   * Tells if the given model is the exact same model as the given using intensional equality.
   * Used to check if the model in the view is the exact same as the one in the model.
   * @param m the given animation model
   * @return true if the given animation model is exactly the same as the one in this view.
   * @throws IllegalArgumentException if the given model is null
   */
  boolean sameModel(AnimationModel m);

  /**
   * Sets the tick label on the edit view to have the given tick and ticks per second.
   * @param tick the tick integer
   * @param tps the tps integer
   *
   */
  void setTickLabel(int tick, int tps);

  /**
   * Sets the model of this animation view to the given model
   * @param model the given {@link AnimationModel}
   * @throws IllegalArgumentException if the model is null
   */
  void setModel(AnimationModel model);


  /**
   * A ViewType is one of Text, SVG, Edit, Visual.
   */
  enum ViewType {
    Text, SVG, Edit, Visual
  }


}
