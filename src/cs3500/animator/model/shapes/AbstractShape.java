package cs3500.animator.model.shapes;

import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.motions.MotionImpl;
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

  private int soloKeyframeTime;
  private ShapeInfo soloKeyframeInfo;

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
    this.soloKeyframeTime = -1;
    this.soloKeyframeInfo = null;
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
//    if (motions.isEmpty()) {
//      throw new IllegalArgumentException("this shape has no info at the given time");
//    } else {
      return new ShapeInfoImpl(new Position2D(0, 0),
          new ShapeSize(0, 0), new Color(0, 0, 0));
//    }
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

  @Override
  public String addKeyframe(int time) {
    if (time < 0) {
      throw new IllegalArgumentException("the time can't be less than 1");
    }
    if (motions.isEmpty()) {
      if (this.soloKeyframeTime == -1) { //there is a no time for a keyframe currently
        soloKeyframeTime = time;
        return "keyframe successfully added for shape " + name + " at time " + time;
      } else { //there is a time for a keyframe currently
        if (soloKeyframeInfo != null) { //there is info for that keyframe, then create a motion
          int firstTime = (soloKeyframeTime < time) ? soloKeyframeTime : time;
          int secondTime = (soloKeyframeTime < time) ? time : soloKeyframeTime;
          motions.add(new MotionImpl(firstTime, secondTime, soloKeyframeInfo, soloKeyframeInfo));
          //resets them back to zero for the case of all keyframes being removed again...
          soloKeyframeTime = -1;
          soloKeyframeInfo = null;
          return "keyframe successfully added for shape " + name + " at time " + time + "; "
              + "motion successfully added from time " + firstTime + " to " + secondTime;
        } else { //there is no info for the first keyframe
          return "error: must add keyframe information for shape " + name + " at time "
              + soloKeyframeTime + " first";
        }
      }
    } else {
      //gets the first and last motions in the animation
      Motion first = motions.get(0);
      Motion last = motions.get(motions.size() - 1);
      if (time < first.getStartTime()) { //if it's before first, add new motion to extend first
        motions.add(0, new MotionImpl(time, first.getStartTime(),
            first.getStartInfo(), first.getStartInfo()));
        return "keyframe successfully added for shape " + name + " at time " + time;
      } else if (time > last.getFinishTime()) { //after last, add new motion to extend last
        motions.add(motions.size(), new MotionImpl(last.getFinishTime(), time,
            last.getFinishInfo(), last.getFinishInfo()));
        return "keyframe successfully added for shape " + name + " at time " + time;
      } else { //it's somewhere in the middle of the motions
        for (int i = 0; i < motions.size(); i++) {
          //it's on some border
          if ((motions.get(i).getStartTime() == time) || (motions.get(i).getFinishTime() == time)) {
            return "keyframe for shape " + name + " already exists at " + time;
          } else if (motions.get(i).getStartTime() < time
              && motions.get(i).getFinishTime() > time) {
            Motion removing = motions.remove(i);
            ShapeInfo tweened = tween(time, removing.getStartTime(), removing.getFinishTime(),
                removing.getStartInfo(), removing.getFinishInfo());
            Motion toAdd1 = new MotionImpl(removing.getStartTime(), time,
                removing.getStartInfo(), tweened);
            Motion toAdd2 = new MotionImpl(time, removing.getFinishTime(),
                tweened, removing.getFinishInfo());
            motions.add(i, toAdd1);
            motions.add(i + 1, toAdd2);
            return "keyframe successfully added for shape " + name + " at time " + time;
          }
        }
        return "error: an unexpected error occurred, and the keyframe could not be added";
      }
    }
  }

  @Override
  public String editKeyframe(int time, ShapeInfo info) {
    //check inputs
    if (time < 0) {
      throw new IllegalArgumentException("the given time is less than 0");
    }
    if (info == null) {
      throw new IllegalArgumentException("the given shape info is null");
    }
    //if no motions: must set keyframe info for solo keyframe, otherwise error
    if (motions.isEmpty()) {
      if (soloKeyframeInfo == null && soloKeyframeTime == time) {
        soloKeyframeInfo = info;
        return "keyframe successfully edited for shape " + name + " at time " + time;
      } else if (soloKeyframeTime == -1) {
        return "error: must add a keyframe for shape " + name + " first";
      } else {
        return "error: must edit the keyframe for shape " + name + " at time "
            + soloKeyframeTime + " first";
      }
    } else { //there are motions
      Motion first = motions.get(0);
      Motion last = motions.get(motions.size() - 1);
      if (first.getStartTime() == time) { //edit the first motion's start
        motions.remove(0);
        Motion toAdd = new MotionImpl(time, first.getFinishTime(), info, first.getFinishInfo());
        motions.add(0, toAdd);
        return "keyframe successfully edited for shape " + name + " at time " + time;
      } else if (last.getFinishTime() == time) { //edit the last motion's finish
        motions.remove(motions.size() - 1);
        Motion toAdd = new MotionImpl(last.getStartTime(), time, last.getStartInfo(), info);
        motions.add(toAdd);
        return "keyframe successfully edited for shape " + name + " at time " + time;
      } else { //edit two motions
        //should only go to second to last motion, avoiding index oob error
        //TODO: Potential error with edge case sizes, think harder
        for (int i = 0; i < motions.size() - 1; i++) {
          Motion current = motions.get(i);
          Motion next = motions.get(i + 1);
          if (current.getFinishTime() == time && next.getStartTime() == time) {
            motions.remove(i);
            motions.remove(i);
            Motion toAdd1 = new MotionImpl(current.getStartTime(), time,
                current.getStartInfo(), info);
            Motion toAdd2 = new MotionImpl(time, next.getFinishTime(),
                info, next.getFinishInfo());
            motions.add(i, toAdd1);
            motions.add(i + 1, toAdd2);
            return "keyframe successfully edited for shape " + name + " at time " + time;
          }
        }
        return "error: no keyframe exists for shape " + name + " at time " + time;
      }
    }
  }

  @Override
  public String deleteKeyframe(int time) {
    if (motions.isEmpty()) {
      if (soloKeyframeTime == time) {
        soloKeyframeTime = -1;
        soloKeyframeInfo = null;
        return "keyframe successfully deleted for shape " + name + " at time " + time;
      } else {
        return "error: no keyframe exists for shape " + name + " at time " + time;
      }
    } else {
      Motion first = motions.get(0);
      Motion last = motions.get(motions.size() - 1);
      if (first.getStartTime() == time) { //delete the first motion
        motions.remove(0);
        return "keyframe successfully deleted for shape " + name + " at time " + time;
      } else if (last.getFinishTime() == time) { //delete the last motion
        motions.remove(motions.size() - 1);
        return "keyframe successfully deleted for shape " + name + " at time " + time;
      } else { //it's somewhere in the middle
        //TODO: Potential error with edge case sizes, think harder
        for (int i = 0; i < motions.size() - 1; i++) {
          Motion current = motions.get(i);
          Motion next = motions.get(i + 1);
          if (current.getFinishTime() == time && next.getStartTime() == time) {
            motions.remove(i);
            motions.remove(i);
            Motion toAdd1 = new MotionImpl(current.getStartTime(), next.getFinishTime(),
                current.getStartInfo(), next.getFinishInfo());
            motions.add(i, toAdd1);
            return "keyframe successfully deleted for shape " + name + " at time " + time;
          }
        }
        return "error: no keyframe exists for shape " + name + " at time " + time;
      }
    }
  }

  @Override
  public boolean isKeyframe(int time) {
    if (time < 0) {
      throw new IllegalArgumentException("time cannot be less than 0");
    }
    if (motions.isEmpty()) {
      return soloKeyframeTime == time;
    } else {
      for (int i = 0; i < motions.size(); i++) {
        Motion current = motions.get(i);
        if (current.getStartTime() == time || current.getFinishTime() == time) {
          return true;
        }
      }
      return false;
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
