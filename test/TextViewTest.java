import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.motions.MotionImpl;
import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.motions.info.ShapeInfoImpl;
import cs3500.animator.model.shapes.Oval;
import cs3500.animator.model.shapes.Rectangle;
import cs3500.animator.model.types.Color;
import cs3500.animator.model.types.Position2D;
import cs3500.animator.model.types.ShapeSize;
import cs3500.animator.util.BadAppendable;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.AnimationView.ViewType;
import cs3500.animator.view.ButtonListener;
import cs3500.animator.view.TextView;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class for AnimationModelImpl.
 */
public class TextViewTest {

  private AnimationModel m;
  private Appendable ap;
  private AnimationView view;

  @Before
  public void setUp() {
    this.m = new AnimationModelImpl();
    this.ap = new StringBuilder();
    this.view = new TextView(m, ap);
  }

  @Test
  public void simpleTextView() {
    m.addShape(new Rectangle("R"));
    view.makeVisible();
    assertEquals("canvas 0 0 500 500\nshape R rectangle\n", ap.toString());
  }

  @Test
  public void emptyTextView() {
    view.makeVisible();
    assertEquals("canvas 0 0 500 500\n", ap.toString());
  }

  @Test
  public void oneMotion() {
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info1));
    view.makeVisible();
    assertEquals("canvas 0 0 500 500\n"
        + "shape R rectangle\n"
        + "motion R 0 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n", ap.toString());
  }

  @Test
  public void refreshException() {
    try {
      view.refresh();
      fail("exception not thrown");
    } catch (UnsupportedOperationException e) {
      assertEquals("can't refresh textual views", e.getMessage());
    }
  }

  @Test
  public void twoMotions() {
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info1));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 300), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(20, 25, info1, info2));
    view.makeVisible();
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 0 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n"
        + "motion R 20 200 200 50 100 255 0 0 25 300 300 50 100 255 0 0\n", ap.toString());
  }

  @Test
  public void fullAnimation() {
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
        new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(1, 10, info1, info1));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 300),
        new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(10, 50, info1, info2));
    m.addAnimation("R", new MotionImpl(50, 51, info2, info2));
    ShapeInfo info3 = new ShapeInfoImpl(new Position2D(300, 300),
        new ShapeSize(25, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(51, 70, info2, info3));
    ShapeInfo info4 = new ShapeInfoImpl(new Position2D(200, 200),
        new ShapeSize(25, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(70, 100, info3, info4));

    m.addShape(new Oval("C"));
    ShapeInfo infox1 = new ShapeInfoImpl(new Position2D(440, 70),
        new ShapeSize(120, 60),
        new Color(0, 0, 255));
    m.addAnimation("C", new MotionImpl(6, 20, infox1, infox1));
    ShapeInfo infox2 = new ShapeInfoImpl(new Position2D(440, 250),
        new ShapeSize(120, 60),
        new Color(0, 0, 255));
    m.addAnimation("C", new MotionImpl(20, 50, infox1, infox2));
    ShapeInfo infox3 = new ShapeInfoImpl(new Position2D(440, 370),
        new ShapeSize(120, 60),
        new Color(0, 170, 85));
    m.addAnimation("C", new MotionImpl(50, 70, infox2, infox3));
    ShapeInfo infox4 = new ShapeInfoImpl(new Position2D(440, 370),
        new ShapeSize(120, 60),
        new Color(0, 255, 0));
    m.addAnimation("C", new MotionImpl(70, 80, infox3, infox4));
    m.addAnimation("C", new MotionImpl(80, 100, infox4, infox4));
    view.makeVisible();
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 1 200 200 50 100 255 0 0 10 200 200 50 100 255 0 0\n"
        + "motion R 10 200 200 50 100 255 0 0 50 300 300 50 100 255 0 0\n"
        + "motion R 50 300 300 50 100 255 0 0 51 300 300 50 100 255 0 0\n"
        + "motion R 51 300 300 50 100 255 0 0 70 300 300 25 100 255 0 0\n"
        + "motion R 70 300 300 25 100 255 0 0 100 200 200 25 100 255 0 0\n"
        + "shape C ellipse\n"
        + "motion C 6 440 70 120 60 0 0 255 20 440 70 120 60 0 0 255\n"
        + "motion C 20 440 70 120 60 0 0 255 50 440 250 120 60 0 0 255\n"
        + "motion C 50 440 250 120 60 0 0 255 70 440 370 120 60 0 170 85\n"
        + "motion C 70 440 370 120 60 0 170 85 80 440 370 120 60 0 255 0\n"
        + "motion C 80 440 370 120 60 0 255 0 100 440 370 120 60 0 255 0\n", ap.toString());
  }

  @Test
  public void nullModel() {
    try {
      AnimationView view = new TextView(null, new StringBuilder());
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the model or appendable is null", e.getMessage());
    }
  }

  @Test
  public void nullAppendable() {
    try {
      AnimationModel model = new AnimationModelImpl();
      AnimationView view = new TextView(model, null);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the model or appendable is null", e.getMessage());
    }
  }

  @Test
  public void nullBoth() {
    try {
      AnimationView view = new TextView(null, null);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the model or appendable is null", e.getMessage());
    }
  }

  @Test
  public void appendableError() {
    try {
      AnimationModel model = new AnimationModelImpl();
      AnimationView view = new TextView(model, new BadAppendable());
      view.makeVisible();
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("appending error", e.getMessage());
    }
  }

  @Test
  public void viewType() {
    assertEquals(ViewType.Text, view.getViewType());
  }

  @Test
  public void refreshError() {
    try {
      view.refresh();
      fail("exception not thrown");
    } catch (UnsupportedOperationException e) {
      assertEquals("can't refresh textual views", e.getMessage());
    }
  }

  @Test
  public void resetFocusError() {
    try {
      view.resetFocus();
      fail("exception not thrown");
    } catch (UnsupportedOperationException e) {
      assertEquals("can't reset focus on textual views", e.getMessage());
    }
  }

  @Test
  public void actionListenerError() {
    try {
      view.addActionListener(new ButtonListener());
      fail("exception not thrown");
    } catch (UnsupportedOperationException e) {
      assertEquals("can't add action listeners to textual views", e.getMessage());
    }
  }

  @Test
  public void getPanelError() {
    try {
      view.getPanel();
      fail("exception not thrown");
    } catch (UnsupportedOperationException e) {
      assertEquals("can't get panel from textual views", e.getMessage());
    }
  }

}
