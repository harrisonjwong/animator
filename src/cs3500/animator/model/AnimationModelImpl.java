package cs3500.animator.model;

import cs3500.animator.model.motions.Motion;
import cs3500.animator.model.motions.MotionImpl;
import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.motions.info.ShapeInfoImpl;
import cs3500.animator.model.shapes.Oval;
import cs3500.animator.model.shapes.Rectangle;
import cs3500.animator.model.shapes.Shape;
import cs3500.animator.model.types.Color;
import cs3500.animator.model.types.Position2D;
import cs3500.animator.model.types.ShapeSize;
import cs3500.animator.util.AnimationBuilder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Represents the model for a simple animation. This animation model has a list of shapes,
 * which have a list of motions that describe its movements. It is a collection of animations on
 * a collection of shapes.
 */
public class AnimationModelImpl implements AnimationModel {

  /**
   * A map containing all the shapes in the animation. HashMap contains the String name as the key
   * and the Shape as the value.
   */
  private HashMap<String, Shape> shapes;

  private int windowX;
  private int windowY;
  private int windowWidth;
  private int windowHeight;

  private static int DEFAULT_WINDOW_LOC = 0;
  private static int DEFAULT_WINDOW_SIZE = 500;

  /**
   * Constructor that sets the list of shapes to the given list.
   * @param shapes ArrayList of shapes
   * @throws IllegalArgumentException if the given list of shapes is null
   */
  public AnimationModelImpl(HashMap<String, Shape> shapes) {
    if (shapes == null) {
      throw new IllegalArgumentException("the given list of shapes is null");
    }
    this.shapes = shapes;
    this.windowX = DEFAULT_WINDOW_LOC;
    this.windowY = DEFAULT_WINDOW_LOC;
    this.windowWidth = DEFAULT_WINDOW_SIZE;
    this.windowHeight = DEFAULT_WINDOW_SIZE;
  }

  /**
   * Constructor for the builder.
   */
  private AnimationModelImpl(HashMap<String, Shape> shapes, int x, int y, int width, int height) {
    this.shapes = shapes;
    this.windowX = x;
    this.windowY = y;
    this.windowWidth = width;
    this.windowHeight = height;
  }

  /**
   * Constructor that initializes the model with an empty list of shapes.
   */
  public AnimationModelImpl() {
    this(new LinkedHashMap<>());
  }

  @Override
  public void addShape(Shape s) {
    if (s == null) {
      throw new IllegalArgumentException("the given shape is null");
    }
    if (shapes.get(s.getName()) != null) {
      throw new IllegalArgumentException("the given shape name is not unique");
    }
    shapes.put(s.getName(), s);
  }

  @Override
  public void addAnimation(String s, Motion m) {
    if (s == null) {
      throw new IllegalArgumentException("given name is null");
    }
    if (m == null) {
      throw new IllegalArgumentException("given motion is null");
    }
    Shape shape = shapes.get(s);
    if (shape == null) {
      throw new IllegalArgumentException("the given name does not have a matching shape");
    }
    List<Motion> motions = shape.getMotions();
    if (motions.size() > 0 && motions.get(motions.size() - 1).getFinishTime() != m.getStartTime()) {
      throw new IllegalArgumentException("given motion doesn't start at end of previous motion");
    }
    if (motions.size() > 0
        && !motions.get(motions.size() - 1).getFinishInfo().equals(m.getStartInfo())) {
      throw new IllegalArgumentException("given motion teleports from previous motion");
    }
    shape.addMotion(m);
  }

  @Override
  public String animationAsText() {
    StringBuilder sb = new StringBuilder();
    sb.append("canvas ").append(this.windowX).append(" ").append(this.windowY).append(" ")
        .append(this.windowWidth).append(" ").append(this.windowHeight).append("\n");
    for (Shape s : shapes.values()) {
      sb.append(s.animationsAsText());
    }
    return sb.toString();
  }

  @Override
  public String addShape(String name, String type) {
    if (name == null || type == null) {
      throw new IllegalArgumentException("one or more of the inputs are null");
    }
    if (shapes.containsKey(name)) {
      return "the given shape name already exists";
    } else {
      switch (type) {
        case "Rectangle":
          this.addShape(new Rectangle(name));
          return "rectangle " + name + " successfully added";
        case "Ellipse":
          this.addShape(new Oval(name));
          return "ellipse " + name + " successfully added";
        default:
          return "the given shape type is invalid";
      }
    }
  }

  @Override
  public ShapeInfo shapeInfoAtTime(String s, int tick) {
    if (s == null) {
      throw new IllegalArgumentException("given name is null");
    }
    if (tick < 0) {
      throw new IllegalArgumentException("tick cannot be negative");
    }
    Shape shape = shapes.get(s);
    if (shape == null) {
      throw new IllegalArgumentException("the given name does not have a matching shape");
    }
    return shape.shapeInfoAtTime(tick);
  }

  @Override
  public HashMap<String, Shape> getShapes() {
    return shapes;
  }

  @Override
  public int getEndingTick() {
    int last = 0;
    for (Shape s : shapes.values()) {
      int temp = s.getEnd();
      if (temp > last) {
        last = temp;
      }
    }
    return last;
  }

  @Override
  public int getWindowX() {
    return this.windowX;
  }

  @Override
  public int getWindowY() {
    return this.windowY;
  }

  @Override
  public int getWindowWidth() {
    return this.windowWidth;
  }

  @Override
  public int getWindowHeight() {
    return this.windowHeight;
  }

  @Override
  public String deleteShape(String name) {
    if (name == null) {
      throw new IllegalArgumentException("the given name is null");
    }
    if (shapes.containsKey(name)) {
      shapes.remove(name);
      return "shape " + name + " deleted";
    } else {
      return "shape " + name + " cannot be found";
    }
  }

  @Override
  public String addKeyframe(String name, int time) {
    if (name == null) {
      throw new IllegalArgumentException("the given name is null");
    }
    if (time < 0) {
      throw new IllegalArgumentException("the given time cannot be less than 0");
    }
    Shape s = shapes.get(name);
    if (s == null) {
      return "shape " + name + " cannot be found";
    }
    return s.addKeyframe(time);
  }

  @Override
  public String editKeyframe(String name, int time, ShapeInfo info) {
    if (name == null) {
      throw new IllegalArgumentException("the given name is null");
    }
    if (time < 0) {
      throw new IllegalArgumentException("the given time cannot be less than 0");
    }
    if (info == null) {
      throw new IllegalArgumentException("the given info is null");
    }
    Shape s = shapes.get(name);
    if (s == null) {
      return "shape " + name + " cannot be found";
    }
    return s.editKeyframe(time, info);
  }

  @Override
  public String deleteKeyframe(String name, int time) {
    if (name == null) {
      throw new IllegalArgumentException("the given name is null");
    }
    if (time < 0) {
      throw new IllegalArgumentException("the given time cannot be less than 0");
    }
    Shape s = shapes.get(name);
    if (s == null) {
      return "shape " + name + " cannot be found";
    }
    return s.deleteKeyframe(time);
  }

  @Override
  public boolean isKeyframe(String name, int time) {
    if (name == null) {
      throw new IllegalArgumentException("the given name is null");
    }
    if (time < 0) {
      throw new IllegalArgumentException("the given time cannot be less than 0");
    }
    Shape s = shapes.get(name);
    if (s == null) {
      return false;
    }
    return s.isKeyframe(time);
  }


  /**
   * Embedded builder class that constructs an instance of an AnimationModel based on steps.
   */
  public static final class Builder implements AnimationBuilder<AnimationModel> {

    private HashMap<String, Shape> shapes = new LinkedHashMap<>();
    private int x = DEFAULT_WINDOW_LOC;
    private int y = DEFAULT_WINDOW_LOC;
    private int width = DEFAULT_WINDOW_SIZE;
    private int height = DEFAULT_WINDOW_SIZE;

    @Override
    public AnimationModel build() {
      return new AnimationModelImpl(shapes, x, y, width, height);
    }

    @Override
    public AnimationBuilder<AnimationModel> setBounds(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> declareShape(String name, String type) {
      switch (type) {
        case "rectangle":
          shapes.put(name, new Rectangle(name));
          break;
        case "ellipse":
          shapes.put(name, new Oval(name));
          break;
        default:
          throw new IllegalArgumentException("illegal shape type");
      }
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> addMotion(String name, int t1, int x1, int y1, int w1,
        int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2,
        int b2) {
      Shape s = shapes.get(name);
      if (name == null) {
        throw new IllegalArgumentException("shape doesn't exist");
      }
      s.addMotion(new MotionImpl(t1, t2, new ShapeInfoImpl(
          new Position2D(x1, y1), new ShapeSize(w1, h1), new Color(r1, g1, b1)),
          new ShapeInfoImpl(
              new Position2D(x2, y2), new ShapeSize(w2, h2), new Color(r2, g2, b2))));
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> addKeyframe(String name, int t, int x, int y, int w,
        int h, int r, int g, int b) {
      throw new UnsupportedOperationException("keyframes are not currently supported");
    }

  }

}
