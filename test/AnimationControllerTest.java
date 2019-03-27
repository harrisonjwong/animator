import cs3500.animator.controller.AnimationController;
import cs3500.animator.controller.AnimationControllerImpl;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.motions.MotionImpl;
import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.motions.info.ShapeInfoImpl;
import cs3500.animator.model.shapes.Rectangle;
import cs3500.animator.model.types.Color;
import cs3500.animator.model.types.Position2D;
import cs3500.animator.model.types.ShapeSize;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.AnimationView.ViewType;
import cs3500.animator.view.EditView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.VisualView;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the AnimationController.
 */
public class AnimationControllerTest {

  @Test
  public void textViewThruControllerTextOutput() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info1));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 300), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(20, 25, info1, info2));
    Appendable ap = new StringBuilder();
    AnimationView view = new TextView(m, ap);
    AnimationController controller = new AnimationControllerImpl(view, m, 1);
    controller.start();

    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 0 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n"
        + "motion R 20 200 200 50 100 255 0 0 25 300 300 50 100 255 0 0\n", ap.toString());
  }

  @Test
  public void svgViewThruControllerTextOutput() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info1));
    Appendable sb = new StringBuilder();
    AnimationView view = new SVGView(m, 3, sb);
    AnimationController controller = new AnimationControllerImpl(view, m, 3);
    controller.start();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" fill=\"rgb(255,0,0)\" "
        + "visibility=\"hidden\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"0ms\" "
        + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"6660ms\" dur=\"6660ms\" "
        + "attributeName=\"visibility\" from=\"hidden\" to=\"hidden\" fill=\"freeze\" />\n"
        + "</rect>\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void visualView() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info1));
    AnimationView view = new VisualView(m, 20);
    assertEquals(ViewType.Visual, view.getViewType());
  }

  @Test
  public void editView() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info1));
    AnimationView view = new EditView(m, 20);
    assertEquals(ViewType.Edit, view.getViewType());
  }

}
