package cs3500.animator;

import cs3500.animator.util.AnimationFactory;
import cs3500.animator.view.AnimationView;
import java.io.FileNotFoundException;

/**
 * Main class used to run the Excellence program. Takes command line arguments to produce an
 * animation.
 */
public final class Excellence {

  /**
   * Main method for the Excellence program.
   * @param args command arguments that tells what the animation should do, including
   *        -view (text, visual, svg), which tells which view should be displayed (required);
   *        -in (*file name*), which tells what file should be displayed on the screen (required);
   *        -out (*file name*), which tells where the text should be outputted (used for
   *                            svg view and text view--default to System.out)
   *        -speed (int), which tells the speed of the animation in ticks per second (used for
   *                            visual view and svg view--defaults to 1 tps)
   * @throws IllegalArgumentException if the file name or command line arguments are invalid
   */
  public static void main(String[] args) {
    try {
      AnimationView view = AnimationFactory.get(args);
      view.makeVisible();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("invalid file");
    }
  }
}
