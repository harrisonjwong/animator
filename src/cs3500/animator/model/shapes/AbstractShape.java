package cs3500.animator.model.shapes;

import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.motions.info.ShapeInfoImpl;
import cs3500.animator.model.types.Color;
import cs3500.animator.model.types.Position2D;
import cs3500.animator.model.types.ShapeSize;
import java.util.ArrayList;

/**
 * Abstract class representing a shape, which contains common code between all shapes.
 * All shape has a list of motions, which are how it moves over time, creating the animation.
 */
abstract class AbstractShape implements Shape {

  /**
   * The name of the shape.
   */
  private String name;

  /**
   * The list of motions for the shape.
   * <p>INVARIANT: The list of shapes is in chronological order.</p>
   * <p>INVARIANT: The list of times are continuous: from the first start time to the last end time
   * there are no gaps or overlaps between motions.</p>
   */
  private ArrayList<Motion> motions;

  /**
   * Constructor for AbstractShape that takes a name and creates a new list of motions.
   * @param name the name of the shape
   * @throws IllegalArgumentException if the given name of the shape is null
   */
  protected AbstractShape(String name) {
    if (name == null) {
      throw new IllegalArgumentException("the given name is null");
    }
    this.name = name;
    this.motions = new ArrayList<>();
  }

  @Override
  public String animationsAsText() {
    StringBuilder sb = new StringBuilder();
    sb.append("shape ");
    sb.append(this.name);
    sb.append(" ");
    sb.append(this.getType());
    sb.append("\n");
    for (Motion m : motions) {
      sb.append(m.motionAsText(this.name));
      sb.append("\n");
    }
    return sb.toString();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void addMotion(Motion m) {
    if (m == null) {
      throw new IllegalArgumentException("given motion is null");
    }
    if (!motions.isEmpty()) {
      Motion last = motions.get(motions.size() - 1);
      int lastTime = last.getFinishTime();
      ShapeInfo lastInfo = last.getFinishInfo();
      if (m.sameStart(lastTime, lastInfo)) {
        motions.add(m);
      } else {
        throw new IllegalArgumentException("given motion does not match previous");
      }
    } else {
      motions.add(m);
    }
  }

  @Override
  public ShapeInfo shapeInfoAtTime(int tick) {
    for (Motion m : motions) {
      if (m.getFinishTime() >= tick && m.getStartTime() <= tick) {
        return tween(tick, m.getStartTime(), m.getFinishTime(),
            m.getStartInfo(), m.getFinishInfo());
      }
    }
    if (motions.isEmpty()) {
      throw new IllegalArgumentException("this shape has no info at the given time");
    } else {
      return new ShapeInfoImpl(new Position2D(0, 0),
          new ShapeSize(0, 0), new Color(0, 0, 0));
    }
  }

  @Override
  public ArrayList<Motion> getMotions() {
    return motions;
  }

  @Override
  public int getEnd() {
    if (motions.isEmpty()) {
      return 0;
    } else {
      return motions.get(motions.size() - 1).getFinishTime();
    }
  }

  private ShapeInfo tween(int currentTick, int startTick, int endTick,
      ShapeInfo start, ShapeInfo end) {
    //uses the formula 𝑓(𝑡)=𝑎(𝑡𝑏−𝑡/𝑡𝑏−𝑡𝑎)+𝑏(𝑡−𝑡𝑎/𝑡𝑏−𝑡𝑎)
    double firstParen = (double)(endTick - currentTick) / (double)(endTick - startTick);
    double secondParen = (double)(currentTick - startTick) / (double)(endTick - startTick);

    Position2D firstPos = start.getPosition();
    Position2D secondPos = end.getPosition();
    double newPosX = (firstPos.getX() * firstParen) + (secondPos.getX() * secondParen);
    double newPosY = (firstPos.getY() * firstParen) + (secondPos.getY() * secondParen);
    Position2D newPos = new Position2D((int)newPosX, (int)newPosY);

    ShapeSize firstSize = start.getSize();
    ShapeSize secondSize = end.getSize();
    double newSizeW = (firstSize.getW() * firstParen) + (secondSize.getW() * secondParen);
    double newSizeH = (firstSize.getH() * firstParen) + (secondSize.getH() * secondParen);
    ShapeSize newSize = new ShapeSize((int)newSizeW, (int)newSizeH);

    Color firstColor = start.getColor();
    Color secondColor = end.getColor();
    double newColorR = (firstColor.getR() * firstParen) + (secondColor.getR() * secondParen);
    double newColorG = (firstColor.getG() * firstParen) + (secondColor.getG() * secondParen);
    double newColorB = (firstColor.getB() * firstParen) + (secondColor.getB() * secondParen);
    Color newColor = new Color((int)newColorR, (int)newColorG, (int)newColorB);

    return new ShapeInfoImpl(newPos, newSize, newColor);

  }

}
