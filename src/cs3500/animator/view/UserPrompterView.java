package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;

/**
 * The user prompter view is a separate view delegated to by the controller to prompt the user
 * and create runnable that edit the model based on the prompts. It is separate from the
 * AnimationView because it only applies to the EditView, so in order to not add many methods
 * to the AnimationView interface that would only be used by one view type,
 * a separate view was created. This view runs entire on JOptionPane prompts, so it
 * can be distinct from the other views and does not depend on the EditView JPanel.
 */
public interface UserPrompterView {

  /**
   * Creates a runnable that adds a shape to the model based on the
   * user's input in the JOptionPane. The user is prompted
   * for a shape name, and a shape type. If the user's input is invalid,
   * then shows a JOptionPane error message, but leaves the model untouched.
   * @return a runnable that adds a shape to the model, or {@code null} if inputs are invalid
   */
  Runnable addShape();

  /**
   * Creates a runnable that removes a shape from the model based on the
   * user's input in the JOptionPane. Prompts for only a shape name.
   * If the user's input is invalid, then shows JOptionPane error message,
   * but leaves the model untouched.
   * @return a runnable that removes a shape from the model, or {@code null} if inputs are invalid
   */
  Runnable removeShape();

  /**
   * Creates a runnable that adds a keyframe to the model based on the user's input in the
   * JOptionPane. Prompts the user for a shape name and a time. If the user's input is invalid,
   * then shows a JOptionPane error message, but leaves the model untouched.
   * @return a runnable that adds a keyframe to the model, or {@code null} if inputs invalid
   */
  Runnable addKeyframe();

  /**
   * Creates a runnable that edits a keyframe on the model based on the user's input
   * in the JOptionPane. Prompts the user for a shape name and time, then if those are valid,
   * shows another prompt to edit that keyframe. If the user's input is invalid, then shows a
   * JOptionPane error message, but leaves the model untouched.
   * @return a runnable that edits the keyframe in the model, or {@code null} if inputs invalid
   */
  Runnable editKeyframe();

  /**
   * Creates a runnable that removes a keyframe on the model based on the user's input in the
   * JOptionPane. Prompts the user for a shape name and time. If those are invalid, then show
   * a JOptionPane error message but leave the model untouched.
   * @return a runnable that removes the keyframe in the model, or {@code null} if inputs invalid
   */
  Runnable removeKeyframe();

  /**
   * Saves the animation model to text or svg based on the user's choice in a JOptionPane.
   * Prompts the user for a file name, then text or svg. If inputs are invalid, show a
   * JOptionPane error and then do nothing.
   */
  void saveAnimation();

  /**
   * Loads an animation from a file selector. If the file selected is invalid, show a
   * JOptionPane error message, but do nothing else.
   * @return the AnimationModel created
   */
  AnimationModel loadAnimation();

  /**
   * Sets the model of this UserPrompterView to the given model.
   * @param model the AnimationModel that this UserPrompterView uses
   * @throws IllegalArgumentException if the given model is null
   */
  void setModel(AnimationModel model);

}
