package cs3500.animator.util;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.VisualView;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Creates an animation model based on given program arguments.
 */
public class AnimationFactory {

  /**
   * Takes a list of command arguments and produces a view based on them.
   * -view (text, visual, svg), which tells which view should be displayed (required);
   * -in (*file name*), which tells what file should be displayed on the screen (required);
   * -out (*file name*), which tells where the text should be outputted (used for
   *                     svg view and text view--default to System.out)
   * -speed (int), which tells the speed of the animation in ticks per second (used for
   *               visual view and svg view--defaults to 1 tps)
   * @param args the command arguments
   * @return the AnimationView based on the command arguments
   * @throws FileNotFoundException if the file in the -in argument is not found
   * @throws IllegalArgumentException if the arguments are invalid
   */
  public static AnimationView get(String[] args) throws FileNotFoundException {
    String fileName = lookForRequired("-in", args);
    String viewType = lookForRequired("-view", args);
    Appendable outLocation = lookForOutLocation(args);
    int speed = lookForSpeed(args);
    AnimationModel model = AnimationReader.parseFile(new FileReader(fileName),
        new AnimationModelImpl.Builder());
    switch (viewType) {
      case "text":
        return new TextView(model, outLocation);
      case "svg":
        return new SVGView(model, speed, outLocation);
      case "visual":
        return new VisualView(model, speed);
      default:
        throw new IllegalArgumentException("invalid view type");
    }
  }

  private static String lookForRequired(String thing, String[] args) {
    int index;
    try {
      index = Arrays.asList(args).indexOf(thing);
      if (index == -1) {
        throw new IllegalArgumentException("no argument found for " + thing);
      }
      return args[index + 1];
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("no valid argument found following " + thing);
    }
  }

  private static Appendable lookForOutLocation(String[] args) {
    int index;
    try {
      index = Arrays.asList(args).indexOf("-out");
      if (index == -1) {
        return System.out;
      }
      return new FileWriter(args[index + 1]);
    } catch (IndexOutOfBoundsException | IOException e) {
      return System.out;
    }
  }

  private static int lookForSpeed(String[] args) {
    int index;
    try {
      index = Arrays.asList(args).indexOf("-speed");
      if (index == -1) {
        return 1;
      }
      int speed = Integer.valueOf(args[index + 1]);
      if (speed > 0) {
        return speed;
      } else {
        return 1;
      }
    } catch (IndexOutOfBoundsException | NumberFormatException e) {
      return 1;
    }
  }



}
