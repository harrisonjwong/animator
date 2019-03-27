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
import cs3500.animator.view.SVGView;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class for SVGView.
 */
public class SVGViewTest {

  @Test
  public void badAppend() {
    try {
      AnimationModel model = new AnimationModelImpl();
      AnimationView view = new SVGView(model, 2, new BadAppendable());
      view.makeVisible();
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("error with appending", e.getMessage());
    }
  }

  @Test
  public void nullModel() {
    try {
      AnimationView view = new SVGView(null, 2, new StringBuilder());
      view.makeVisible();
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Appendable or model cannot be null", e.getMessage());
    }
  }

  @Test
  public void nullAppendable() {
    try {
      AnimationModel model = new AnimationModelImpl();
      AnimationView view = new SVGView(model, 2, null);
      view.makeVisible();
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Appendable or model cannot be null", e.getMessage());
    }
  }

  @Test
  public void badSpeed0() {
    try {
      AnimationModel model = new AnimationModelImpl();
      AnimationView view = new SVGView(model, 0, new StringBuilder());
      view.makeVisible();
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("speed must be greater than 0", e.getMessage());
    }
  }

  @Test
  public void badSpeedNegative() {
    try {
      AnimationModel model = new AnimationModelImpl();
      AnimationView view = new SVGView(model, -3, new StringBuilder());
      view.makeVisible();
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("speed must be greater than 0", e.getMessage());
    }
  }

  @Test
  public void refreshUnsupported() {
    try {
      AnimationView view = new SVGView(new AnimationModelImpl(), 3, new StringBuilder());
      view.refresh();
      fail("exception not thrown");
    } catch (UnsupportedOperationException e) {
      assertEquals("can't refresh textual views", e.getMessage());
    }
  }

  @Test
  public void emptyAnimation() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 3, sb);
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void oneShape() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 3, sb);
    m.addShape(new Rectangle("R"));
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void rectShapeNoMovement() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 3, sb);
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info1));
    view.makeVisible();
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
  public void rectShapeChangeX() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 20, sb);
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info2));
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" fill=\""
        + "rgb(255,0,0)\" visibility=\"hidden\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"0ms\" attributeName=\""
        + "visibility\" from=\"visible\" to=\"visible\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"1000ms\" "
        + "attributeName=\"x\" from=\"200\" to=\"300\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" "
        + "attributeName=\"visibility\" from=\"hidden\" to=\"hidden\" fill=\"freeze\" />\n"
        + "</rect>\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void rectShapeChangeY() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 20, sb);
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 300), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info2));
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" "
        + "fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"0ms\" "
        + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"1000ms\" "
        + "attributeName=\"y\" from=\"200\" to=\"300\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" "
        + "attributeName=\"visibility\" from=\"hidden\" to=\"hidden\" fill=\"freeze\" />\n"
        + "</rect>\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void rectShapeChangeW() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 20, sb);
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(100, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info2));
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" "
        + "fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"0ms\" "
        + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"1000ms\" "
        + "attributeName=\"width\" from=\"50\" to=\"100\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" "
        + "attributeName=\"visibility\" from=\"hidden\" to=\"hidden\" fill=\"freeze\" />\n"
        + "</rect>\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void rectShapeChangeH() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 20, sb);
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 150),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info2));
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" "
        + "fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"0ms\" "
        + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"1000ms\" "
        + "attributeName=\"height\" from=\"100\" to=\"150\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" "
        + "attributeName=\"visibility\" from=\"hidden\" to=\"hidden\" fill=\"freeze\" />\n"
        + "</rect>\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void rectShapeChangeRGB() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 20, sb);
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 255, 255));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info2));
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" "
        + "fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"0ms\" "
        + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"1000ms\" "
        + "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,255,255)\" "
        + "fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" "
        + "attributeName=\"visibility\" from=\"hidden\" to=\"hidden\" fill=\"freeze\" />\n"
        + "</rect>\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void oneOval() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 3, sb);
    m.addShape(new Rectangle("R"));
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void ovalOnly() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 3, sb);
    m.addShape(new Oval("O"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("O", new MotionImpl(0, 20, info1, info1));
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<ellipse id=\"O\" cx=\"225\" cy=\"250\" rx=\"25\" ry=\"50\" "
        + "fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"0ms\" "
        + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"6660ms\" dur=\"6660ms\" "
        + "attributeName=\"visibility\" from=\"hidden\" to=\"hidden\" fill=\"freeze\" />\n"
        + "</ellipse>\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void ovalShapeChangeX() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 20, sb);
    m.addShape(new Oval("O"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("O", new MotionImpl(0, 20, info1, info2));
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<ellipse id=\"O\" cx=\"225\" cy=\"250\" rx=\"25\" ry=\"50\" "
        + "fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"0ms\" "
        + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"1000ms\" "
        + "attributeName=\"cx\" from=\"225\" to=\"325\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" "
        + "attributeName=\"visibility\" from=\"hidden\" to=\"hidden\" fill=\"freeze\" />\n"
        + "</ellipse>\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void ovalShapeChangeY() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 20, sb);
    m.addShape(new Oval("O"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 300), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("O", new MotionImpl(0, 20, info1, info2));
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<ellipse id=\"O\" cx=\"225\" cy=\"250\" rx=\"25\" ry=\"50\" "
        + "fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"0ms\" "
        + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"1000ms\" "
        + "attributeName=\"cy\" from=\"250\" to=\"350\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" "
        + "attributeName=\"visibility\" from=\"hidden\" to=\"hidden\" fill=\"freeze\" />\n"
        + "</ellipse>\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void ovalShapeChangeW() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 20, sb);
    m.addShape(new Oval("O"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(100, 100),
        new Color(255, 0, 0));
    m.addAnimation("O", new MotionImpl(0, 20, info1, info2));
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<ellipse id=\"O\" cx=\"225\" cy=\"250\" rx=\"25\" ry=\"50\" "
        + "fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"0ms\" "
        + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"1000ms\" "
        + "attributeName=\"cx\" from=\"225\" to=\"250\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"1000ms\" "
        + "attributeName=\"rx\" from=\"25\" to=\"50\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" "
        + "attributeName=\"visibility\" from=\"hidden\" to=\"hidden\" fill=\"freeze\" />\n"
        + "</ellipse>\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void ovalShapeChangeH() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 20, sb);
    m.addShape(new Oval("O"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 150),
        new Color(255, 0, 0));
    m.addAnimation("O", new MotionImpl(0, 20, info1, info2));
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<ellipse id=\"O\" cx=\"225\" cy=\"250\" rx=\"25\" ry=\"50\" "
        + "fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"0ms\" "
        + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"1000ms\" "
        + "attributeName=\"cy\" from=\"250\" to=\"275\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"1000ms\" "
        + "attributeName=\"ry\" from=\"50\" to=\"75\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" "
        + "attributeName=\"visibility\" from=\"hidden\" to=\"hidden\" fill=\"freeze\" />\n"
        + "</ellipse>\n"
        + "</svg>", sb.toString());
  }

  @Test
  public void oneOvalChangeRGB() {
    AnimationModel m = new AnimationModelImpl();
    StringBuilder sb = new StringBuilder();
    AnimationView view = new SVGView(m, 20, sb);
    m.addShape(new Oval("O"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 255, 255));
    m.addAnimation("O", new MotionImpl(0, 20, info1, info2));
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n"
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<ellipse id=\"O\" cx=\"225\" cy=\"250\" rx=\"25\" ry=\"50\" "
        + "fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"0ms\" "
        + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"0ms\" dur=\"1000ms\" "
        + "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,255,255)\" "
        + "fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1000ms\" "
        + "attributeName=\"visibility\" from=\"hidden\" to=\"hidden\" fill=\"freeze\" />\n"
        + "</ellipse>\n"
        + "</svg>", sb.toString());
  }


}
