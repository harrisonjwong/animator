package cs3500.animator.model.shapes;

/**
 * Represents an oval shape which can be animated. Contains a list of motions that determine its
 * movements. An oval's position is its center; height is up/down-radius, and width is side-radius.
 */
public class Oval extends AbstractShape {

  /**
   * Constructs a new Oval with the given name.
   * @param name the name of the shape
   * @throws IllegalArgumentException if the given name of the shape is null
   */
  public Oval(String name) {
    super(name);
  }

  @Override
  public String getType() {
    return "ellipse";
  }

  @Override
  public String getSVGType() {
    return "ellipse";
  }
}
