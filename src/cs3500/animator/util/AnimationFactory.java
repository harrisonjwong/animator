package cs3500.animator.util;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.EditView;
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

  private AnimationModel model;
  private int speed;
  private AnimationView view;

  /**
   * Constructs an AnimationFactory by taking a list of command arguments and producing a view
   * model and model based on them.
   * -view (text, visual, svg, edit), which tells which view should be displayed (required);
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
  public AnimationFactory(String[] args) throws FileNotFoundException {
    String fileName = lookForRequired("-in", args);
    String viewType = lookForRequired("-view", args);
    Appendable outLocation = lookForOutLocation(args);
    this.speed = lookForSpeed(args);
    this.model = AnimationReader.parseFile(new FileReader(fileName),
        new AnimationModelImpl.Builder());
    switch (viewType) {
      case "text":
        this.view = new TextView(model, outLocation);
        break;
      case "svg":
        this.view = new SVGView(model, speed, outLocation);
        break;
      case "visual":
        this.view = new VisualView(model, speed);
        break;
      case "edit":
        this.view = new EditView(model, speed);
        break;
      default:
        throw new IllegalArgumentException("invalid view type");
    }
  }

  /**
   * Gets the speed of this animation.
   * @return
   */
  public int getSpeed() {
    return this.speed;
  }

  /**
   * Gets the model of this animation
   * @return the {@link AnimationModel} of this animation
   */
  public AnimationModel getModel() {
    return this.model;
  }

  /**
   * Gets the view of this animation.
   * @return the {@link AnimationView} of this animation
   */
  public AnimationView getView() {
    return this.view;
  }

  private String lookForRequired(String thing, String[] args) {
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

  private Appendable lookForOutLocation(String[] args) {
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

  private int lookForSpeed(String[] args) {
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
