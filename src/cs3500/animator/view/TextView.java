package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.ReadOnlyAnimationModel;
import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.shapes.Shape;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * The TextView shows a textual description of an animation. It creates a textual representation
 * which has each shape listed by its name and each motion of that shape listed by the shape name.
 */
public class TextView extends AbstractTextualView {

  /**
   * The model with which to create TextView.
   */
  private ReadOnlyAnimationModel model;

  /**
   * The output of the textual view; Appendable used to abstract output.
   */
  private Appendable appendable;

  /**
   * Construct a TextView based on a model and an appendable to output to.
   * @param model the model to base the text view on
   * @param ap the appendable to output the text view on to
   * @throws IllegalArgumentException if the inputted values are null
   */
  public TextView(ReadOnlyAnimationModel model, Appendable ap) {
    if (model == null || ap == null) {
      throw new IllegalArgumentException("the model or appendable is null");
    }
    this.model = model;
    this.appendable = ap;
  }

  /**
   * Appends the text for the TextView onto the appendable.
   */
  @Override
  public void makeVisible() {
    try {
      Map<String, Shape> shapes = model.getShapes();
      String canvas = "canvas " + model.getWindowX() + " " + model.getWindowY() + " "
          + model.getWindowWidth() + " " + model.getWindowHeight() + "\n";
      this.appendable.append(canvas);
      for (Map.Entry<String, Shape> pair : shapes.entrySet()) {
        this.appendable.append("shape ").append(pair.getKey()).append(" ")
            .append(pair.getValue().getType()).append("\n");
        for (Motion m : pair.getValue().getMotions()) {
          this.appendShapeState(pair.getKey(), m.getStartTime(), m.getFinishTime(),
              m.getStartInfo(), m.getFinishInfo());
        }
      }
      //it is necessary to close the appendable is it is a FileWriter, else the file does
      //not write to disk properly. Therefore instanceof is used to check whether the
      //appendable is a FileWriter, then close it if it is.
      if (appendable instanceof FileWriter) {
        ((FileWriter)appendable).close();
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("appending error");
    }
  }

  private void appendShapeState(String name, int startTime, int finishTime,
      ShapeInfo startInfo, ShapeInfo finishInfo) throws IOException {
    this.appendable.append("motion ").append(name).append(" ");
    this.appendable.append(Integer.toString(startTime)).append(" ")
        .append(startInfo.getPosition().toString()).append(" ")
        .append(startInfo.getSize().toString()).append(" ")
        .append(startInfo.getColor().toString()).append(" ");
    this.appendable.append(Integer.toString(finishTime)).append(" ")
        .append(finishInfo.getPosition().toString()).append(" ")
        .append(finishInfo.getSize().toString()).append(" ")
        .append(finishInfo.getColor().toString()).append("\n");
  }

  @Override
  public ViewType getViewType() {
    return ViewType.Text;
  }

  @Override
  public boolean sameModel(AnimationModel m) {
    if (m == null) {
      throw new IllegalArgumentException("the given model is null");
    }
    return m == this.model;
  }

}
