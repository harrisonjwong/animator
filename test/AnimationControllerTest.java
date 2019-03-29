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
import cs3500.animator.view.ButtonListener;
import cs3500.animator.view.EditView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.UserPrompterView;
import cs3500.animator.view.UserPrompterViewImpl;
import cs3500.animator.view.VisualView;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

  @Test
  public void editView2() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info1));
    AnimationView view = new EditView(m, 20);
    AnimationController controller = new AnimationControllerImpl(view, m, 1);
    controller.start();
    assertEquals(ViewType.Edit, view.getViewType());
  }

  @Test
  public void constructorError() {
    try {
      new AnimationControllerImpl(null, null, 1);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("given view and/or model is null", e.getMessage());
    }
  }

  @Test
  public void constructorError2() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info1));
    AnimationView view = new TextView(m, new StringBuilder());
    try {
      new AnimationControllerImpl(view, m, -1);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("speed must be greater than 0", e.getMessage());
    }
  }

  @Test
  public void constructorError3() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info1));
    AnimationView view = new TextView(new AnimationModelImpl(), new StringBuilder());
    try {
      new AnimationControllerImpl(view, m, 1);
      fail("exception not thrown");
    } catch (IllegalStateException e) {
      assertEquals("the model in the view and the model in the controller "
          + "must be the same view", e.getMessage());
    }
  }

  @Test
  public void buttonListener() {
    ButtonListener b = new ButtonListener();
    AnimationModel m = new AnimationModelImpl();
    Map<String, Runnable> actionMap = new HashMap<>();
    assertEquals(false, m.getShapes().containsKey("R"));
    actionMap.put("Hi", () -> m.addShape(new Rectangle("R")));
    b.setButtonClickedActionMap(actionMap);
    b.actionPerformed(new ActionEvent(this, 0, "Hi"));
    assertEquals(true, m.getShapes().containsKey("R"));
  }

  @Test
  public void prompterConstructorError1() {
    try {
      new UserPrompterViewImpl(null, 20);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("model is null", e.getMessage());
    }
  }

  @Test
  public void prompterConstructorError2() {
    try {
      new UserPrompterViewImpl(new AnimationModelImpl(), -1);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the speed is less than 0", e.getMessage());
    }
  }

  @Test
  public void prompterSetModelFail() {
    try {
      new UserPrompterViewImpl(new AnimationModelImpl(), 1).setModel(null);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("given model is null", e.getMessage());
    }
  }

  @Test
  public void prompterSetModel() {
    UserPrompterView upv = new UserPrompterViewImpl(new AnimationModelImpl(), 1);
    try {
      upv.setModel(new AnimationModelImpl());
    } catch (Exception e) {
      fail("exception thrown");
    }
  }

  @Test
  public void editViewActionsTest() {
    ButtonListener b = new ButtonListener();
    AnimationModel m = new AnimationModelImpl();
    Map<String, Runnable> actionMap = new HashMap<>();
    assertEquals(false, m.getShapes().containsKey("R"));
    actionMap.put("Play Button", () -> m.addShape(new Rectangle("R")));
    b.setButtonClickedActionMap(actionMap);
    AnimationView view = new EditView(m, 20);
    view.addActionListener(b);
    b.actionPerformed(new ActionEvent(this, 0, "Play Button"));
    assertEquals(true, m.getShapes().containsKey("R"));
  }


}
