package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.ReadOnlyAnimationModel;
import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.shapes.Shape;
import cs3500.animator.model.types.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The SVGView produces the Scalable Vector Graphics code necessary to display the animation.
 * It contains each shape and the motions that that shapes does, formatted in xml format.
 */
public class SVGView implements AnimationView {

  /**
   * The model with which to create the svg output.
   */
  private ReadOnlyAnimationModel model;

  /**
   * The speed of the animation in ticks per second.
   */
  private int speed;

  /**
   * The output of the SVG view; appendable is used to abstract output so that it is possible
   * to output to a file and the console, as well as run tests.
   */
  private Appendable ap;

  /**
   * Constructs a SVGView from the given model, speed, and appendable.
   *
   * @param model A model for an animation.
   * @param speed The ticks per second of the animation.
   * @param ap The Appendable to contain the output.
   * @throws IllegalArgumentException if the model or appendable are invalid or the speed is
   *         less than 0
   */
  public SVGView(AnimationModel model, int speed, Appendable ap) {
    if (model == null || ap == null) {
      throw new IllegalArgumentException("Appendable or model cannot be null");
    }
    if (speed <= 0) {
      throw new IllegalArgumentException("speed must be greater than 0");
    }
    this.model = model;
    this.speed = speed;
    this.ap = ap;
  }

  @Override
  public void makeVisible() {
    try {
      appendSVGHeader();
      for (Map.Entry<String, Shape> pair : this.model.getShapes().entrySet()) {
        appendShapeHeader(pair);
        int perTick = 1000 / speed;
        List<Motion> motions = pair.getValue().getMotions();
        int timeToVisible;
        if (motions.isEmpty()) {
          continue;
        } else {
          timeToVisible = motions.get(0).getStartTime() * perTick;
        }

        appendShapeVisibility(timeToVisible, "visible");

        for (Motion m : motions) {
          int startTick = m.getStartTime();
          int startW = m.getStartInfo().getSize().getW();
          int startH = m.getStartInfo().getSize().getH();
          int startX = m.getStartInfo().getPosition().getX();
          int startY = m.getStartInfo().getPosition().getY();
          int startR = m.getStartInfo().getColor().getR();
          int startG = m.getStartInfo().getColor().getG();
          int startB = m.getStartInfo().getColor().getB();

          int endTick = m.getFinishTime();
          int endW = m.getFinishInfo().getSize().getW();
          int endH = m.getFinishInfo().getSize().getH();
          int endX = m.getFinishInfo().getPosition().getX();
          int endY = m.getFinishInfo().getPosition().getY();
          int endR = m.getFinishInfo().getColor().getR();
          int endG = m.getFinishInfo().getColor().getG();
          int endB = m.getFinishInfo().getColor().getB();

          int startTime = perTick * startTick;
          int endTime = perTick * endTick;
          int duration = endTime - startTime;

          if (pair.getValue().getSVGType().equals("rect")) {
            if (startX != endX) {
              this.appendSingleAttribute(Integer.toString(startTime), Integer.toString(duration),
                  "x", Integer.toString(startX), Integer.toString(endX));
            }
            if (startY != endY) {
              this.appendSingleAttribute(Integer.toString(startTime), Integer.toString(duration),
                  "y", Integer.toString(startY), Integer.toString(endY));
            }
            if (startW != endW) {
              this.appendSingleAttribute(Integer.toString(startTime), Integer.toString(duration),
                  "width", Integer.toString(startW), Integer.toString(endW));
            }
            if (startH != endH) {
              this.appendSingleAttribute(Integer.toString(startTime), Integer.toString(duration),
                  "height", Integer.toString(startH), Integer.toString(endH));
            }
            if (startR != endR || startG != endG || startB != endB) {
              String rgbStart = "rgb(" + startR + "," + startG + "," + startB + ")";
              String rgbEnd = "rgb(" + endR + "," + endG + "," + endB + ")";
              this.appendSingleAttribute(Integer.toString(startTime), Integer.toString(duration),
                  "fill", rgbStart, rgbEnd);
            }
          } else if (pair.getValue().getSVGType().equals("ellipse")) {
            int startWEllipse = startW / 2;
            int startHEllipse = startH / 2;
            int startXEllipse = startX + startWEllipse;
            int startYEllipse = startY + startHEllipse;
            int endWEllipse = endW / 2;
            int endHEllipse = endH / 2;
            int endXEllipse = endX + endWEllipse;
            int endYEllipse = endY + endHEllipse;
            if (startXEllipse != endXEllipse) {
              this.appendSingleAttribute(Integer.toString(startTime), Integer.toString(duration),
                  "cx", Integer.toString(startXEllipse), Integer.toString(endXEllipse));
            }
            if (startYEllipse != endYEllipse) {
              this.appendSingleAttribute(Integer.toString(startTime), Integer.toString(duration),
                  "cy", Integer.toString(startYEllipse), Integer.toString(endYEllipse));
            }
            if (startWEllipse != endWEllipse) {
              this.appendSingleAttribute(Integer.toString(startTime), Integer.toString(duration),
                  "rx", Integer.toString(startWEllipse), Integer.toString(endWEllipse));
            }
            if (startHEllipse != endHEllipse) {
              this.appendSingleAttribute(Integer.toString(startTime), Integer.toString(duration),
                  "ry", Integer.toString(startHEllipse), Integer.toString(endHEllipse));
            }
            if (startR != endR || startG != endG || startB != endB) {
              String rgbStart = "rgb(" + startR + "," + startG + "," + startB + ")";
              String rgbEnd = "rgb(" + endR + "," + endG + "," + endB + ")";
              this.appendSingleAttribute(Integer.toString(startTime), Integer.toString(duration),
                  "fill", rgbStart, rgbEnd);
            }
          }
        }
        //already checked if the list was empty, so this is safe
        int timeToHide = motions.get(motions.size() - 1)
            .getFinishTime() * perTick;
        this.appendShapeVisibility(timeToHide, "hidden");
        this.appendShapeTail(pair);

      }

      this.ap.append("</svg>");
      //must close the file if appendable is a FileWriter
      if (this.ap instanceof FileWriter) {
        ((FileWriter) this.ap).close();
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("error with appending");
    }

  }

  private String getShapeDescription(Shape shape) {
    StringBuilder sb = new StringBuilder();
    if (shape.getSVGType().equals("rect")) {
      if (!shape.getMotions().isEmpty()) {
        ShapeInfo first = shape.getMotions().get(0).getStartInfo();
        Color color = first.getColor();
        sb.append("x=\"").append(first.getPosition().getX()).append("\" y=\"")
            .append(first.getPosition().getY()).append("\" width=\"").append(first.getSize().getW())
            .append("\" height=\"").append(first.getSize().getH())
            .append("\" fill=\"rgb(").append(color.getR())
            .append(",").append(color.getG()).append(",").append(color.getB()).append(")\"")
            .append(" visibility=\"").append("hidden").append("\"");
      }
    }
    else if (shape.getSVGType().equals("ellipse")) {
      if (!shape.getMotions().isEmpty()) {
        ShapeInfo first = shape.getMotions().get(0).getStartInfo();
        int circleXRad = first.getSize().getW() / 2;
        int circleYRad = first.getSize().getH() / 2;
        int circleX = first.getPosition().getX() + circleXRad;
        int circleY = first.getPosition().getY() + circleYRad;

        sb.append("cx=\"").append(circleX)
            .append("\" cy=\"").append(circleY)
            .append("\" rx=\"").append(circleXRad).append("\" ry=\"").append(circleYRad)
            .append("\" fill=\"rgb(").append(first.getColor().getR()).append(",")
            .append(first.getColor().getG()).append(",").append(first.getColor().getB())
            .append(")\"").append(" visibility=\"").append("hidden").append("\"");
      }
    }
    return sb.toString();
  }

  private void appendSVGHeader() throws IOException {
    this.ap.append("<svg width=\"")
        .append(String.valueOf(this.model.getWindowX() + this.model.getWindowWidth()))
        .append("\" height=\"")
        .append(String.valueOf(this.model.getWindowY() + this.model.getWindowHeight()))
        .append("\" version=\"1.1\"\n").append("xmlns=\"http://www.w3.org/2000/svg\">\n");
  }

  private void appendShapeHeader(Map.Entry<String, Shape> pair) throws IOException {
    if (!pair.getValue().getMotions().isEmpty()) {
      this.ap.append("<").append(pair.getValue().getSVGType()).append(" id=\"")
          .append(pair.getKey()).append("\" ")
          .append(this.getShapeDescription(pair.getValue())).append(" >\n");
    }
  }

  private void appendShapeVisibility(int timeToVisible, String visibility) throws IOException {
    this.ap.append("    <animate ").append("attributeType=\"xml\" ")
        .append("begin=\"").append(Integer.toString(timeToVisible)).append("ms\" ")
        .append("dur=\"").append(Integer.toString(timeToVisible)).append("ms\" ")
        .append("attributeName=\"visibility\" ")
        .append("from=\"").append(visibility).append("\" ")
        .append("to=\"").append(visibility).append("\" ")
        .append("fill=\"freeze\"").append(" />\n");
  }

  private void appendSingleAttribute(String startTime, String duration, String att,
      String start, String end) throws IOException {
    this.ap.append("    <animate ").append("attributeType=\"xml\" ")
        .append("begin=\"").append(startTime).append("ms\" ")
        .append("dur=\"").append(duration).append("ms\" ")
        .append("attributeName=\"").append(att).append("\" ")
        .append("from=\"").append(start).append("\" ")
        .append("to=\"").append(end).append("\" ")
        .append("fill=\"freeze\"").append(" />\n");
  }

  private void appendShapeTail(Map.Entry<String, Shape> pair) throws IOException {
    this.ap.append("</").append(pair.getValue().getSVGType()).append(">").append("\n");
  }

  @Override
  public void refresh() {
    throw new UnsupportedOperationException("cannot refresh a svg view");
  }
}
