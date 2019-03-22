package cs3500.animator.model.shapes;

/**
 * Represents a rectangle shape which can be animated. Contains a list of motions describing its
 * motions. Its position is determined by its upper left corner.
 */
public class Rectangle extends AbstractShape {

  /**
   * Constructs a new Rectangle with the given name.
   *
   * @param name the name of the shape
   * @throws IllegalArgumentException if the given name of the shape is null
   */
  public Rectangle(String name) {
    super(name);
  }

  @Override
  public String getType() {
    return "rectangle";
  }

  @Override
  public String getSVGType() {
    return "rect";
  }
}
