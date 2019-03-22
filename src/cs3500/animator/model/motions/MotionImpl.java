package cs3500.animator.model.motions;

import cs3500.animator.model.motions.info.ShapeInfo;

/**
 * The implementation of a Motion. Contains information about the starting and ending times of a
 * motion and the shape's state at the start and end.
 */
public class MotionImpl implements Motion {

  /**
   * The time that this motion starts.
   */
  private int startTime;

  /**
   * The time that this motion ends.
   */
  private int finishTime;

  /**
   * The starting information for the shape.
   */
  private ShapeInfo start;

  /**
   * The ending information for this shape.
   */
  private ShapeInfo finish;

  /**
   * Constructs a MotionImpl with the given starting time, ending time, starting shape information
   * and ending shape information.
   *
   * @param startTime the starting time
   * @param finishTime the ending time
   * @param start the starting shape information
   * @param finish the ending shape information
   * @throws IllegalArgumentException if the time is less than 0 or the shape information is null,
   *         or if the start time is before the finish time.
   */
  public MotionImpl(int startTime, int finishTime, ShapeInfo start, ShapeInfo finish) {
    if (startTime < 0 || finishTime < 0) {
      throw new IllegalArgumentException("time cannot be less than 0");
    }
    if (start == null || finish == null) {
      throw new IllegalArgumentException("the starting or ending shape information is null");
    }
    if (startTime > finishTime) {
      throw new IllegalArgumentException("time is invalid, start time must be < finish time");
    }
    this.startTime = startTime;
    this.finishTime = finishTime;
    this.start = start;
    this.finish = finish;
  }

  @Override
  public String motionAsText(String name) {
    StringBuilder sb = new StringBuilder();
    this.appendWithSpace("motion", sb);
    this.appendWithSpace(name, sb);
    this.appendWithSpace(startTime + "", sb);
    this.appendWithSpace(start.getPosition().toString(), sb);
    this.appendWithSpace(start.getSize().toString(), sb);
    this.appendWithSpace(start.getColor().toString(), sb);
    this.appendWithSpace(finishTime + "", sb);
    this.appendWithSpace(finish.getPosition().toString(), sb);
    this.appendWithSpace(finish.getSize().toString(), sb);
    sb.append(finish.getColor().toString());
    return sb.toString();
  }

  /**
   * Adds the given string and a space to the given StringBuilder.
   * @param s the given string to add to the stringbuilder
   * @param sb the given stringbuilder to be added to
   * @throws IllegalArgumentException if the given string or stringbuilder is null
   */
  private void appendWithSpace(String s, StringBuilder sb) {
    if (s == null || sb == null) {
      throw new IllegalArgumentException("the string or stringbuilder is null");
    }
    sb.append(s);
    sb.append(" ");
  }

  @Override
  public int getFinishTime() {
    return this.finishTime;
  }

  @Override
  public ShapeInfo getFinishInfo() {
    return this.finish;
  }

  @Override
  public boolean sameStart(int time, ShapeInfo info) {
    return time == startTime && info.equals(start);
  }

  @Override
  public ShapeInfo getStartInfo() {
    return this.start;
  }

  @Override
  public int getStartTime() {
    return this.startTime;
  }
}
