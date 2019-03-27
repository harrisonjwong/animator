package cs3500.animator;

import cs3500.animator.controller.AnimationController;
import cs3500.animator.controller.AnimationControllerImpl;
import cs3500.animator.util.AnimationFactory;
import java.io.FileNotFoundException;

/**
 * Main class used to run the Excellence program. Takes command line arguments to produce an
 * animation.
 */
public final class Excellence {

  /**
   * Main method for the Excellence program.
   * @param args command arguments that tells what the animation should do, including
   *        -view (text, visual, svg, edit), which tells which view should be displayed (required);
   *        -in (*file name*), which tells what file should be displayed on the screen (required);
   *        -out (*file name*), which tells where the text should be outputted (used for
   *                            svg view and text view--default to System.out)
   *        -speed (int), which tells the speed of the animation in ticks per second (used for
   *                            visual view and svg view--defaults to 1 tps)
   * @throws IllegalArgumentException if the file name or command line arguments are invalid
   */
  public static void main(String[] args) {
    try {
      AnimationFactory factory = new AnimationFactory(args);
      AnimationController controller = new AnimationControllerImpl(
          factory.getView(), factory.getModel(), factory.getSpeed());
      controller.start();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("invalid file");
    }
  }
}
