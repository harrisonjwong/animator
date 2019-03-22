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
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class for AnimationModelImpl.
 */
public class AnimationModelImplTest {

  @Test
  public void addAnimation() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info1));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 300), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(20, 25, info1, info2));
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 0 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n"
        + "motion R 20 200 200 50 100 255 0 0 25 300 300 50 100 255 0 0\n", m.animationAsText());
  }

  @Test
  public void fullAnimation() {
    AnimationModel m = new AnimationModelImpl();
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
        + "motion C 80 440 370 120 60 0 255 0 100 440 370 120 60 0 255 0\n", m.animationAsText());
  }

  @Test
  public void partialAnimation() {
    AnimationModel m = new AnimationModelImpl();
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
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 1 200 200 50 100 255 0 0 10 200 200 50 100 255 0 0\n"
        + "motion R 10 200 200 50 100 255 0 0 50 300 300 50 100 255 0 0\n"
        + "motion R 50 300 300 50 100 255 0 0 51 300 300 50 100 255 0 0\n"
        + "shape C ellipse\n"
        + "motion C 6 440 70 120 60 0 0 255 20 440 70 120 60 0 0 255\n"
        + "motion C 20 440 70 120 60 0 0 255 50 440 250 120 60 0 0 255\n"
        + "motion C 50 440 250 120 60 0 0 255 70 440 370 120 60 0 170 85\n", m.animationAsText());
  }


  @Test
  public void partialAnimation2() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));

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
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "shape C ellipse\n"
        + "motion C 6 440 70 120 60 0 0 255 20 440 70 120 60 0 0 255\n"
        + "motion C 20 440 70 120 60 0 0 255 50 440 250 120 60 0 0 255\n"
        + "motion C 50 440 250 120 60 0 0 255 70 440 370 120 60 0 170 85\n", m.animationAsText());
  }

  @Test
  public void partialAnimation3() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
        new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(1, 10, info1, info1));

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
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 1 200 200 50 100 255 0 0 10 200 200 50 100 255 0 0\n"
        + "shape C ellipse\n"
        + "motion C 6 440 70 120 60 0 0 255 20 440 70 120 60 0 0 255\n"
        + "motion C 20 440 70 120 60 0 0 255 50 440 250 120 60 0 0 255\n"
        + "motion C 50 440 250 120 60 0 0 255 70 440 370 120 60 0 170 85\n", m.animationAsText());
  }


  @Test
  public void startAndNext1() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
        new ShapeSize(50, 100), new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 0 200 200 50 100 255 0 0 10 200 200 50 100 255 0 0\n", m.animationAsText());
  }

  @Test
  public void startAnimation() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n", m.animationAsText());
  }

  @Test
  public void startAnimationNoChanges() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
        new ShapeSize(100, 30),
        new Color(0, 255, 255));
    m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 0 300 300 100 30 0 255 255 10 300 300 100 30 0 255 255\n", m.animationAsText());
  }

  @Test
  public void startAnimationNoChangesOval() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Oval("O"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
        new ShapeSize(100, 30),
        new Color(0, 255, 255));
    m.addAnimation("O", new MotionImpl(0, 10, info1, info1));
    assertEquals("canvas 0 0 500 500\n" + "shape O ellipse\n"
        + "motion O 0 300 300 100 30 0 255 255 10 300 300 100 30 0 255 255\n", m.animationAsText());
  }

  @Test
  public void startAnimationChangePositionOnly() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
        new ShapeSize(100, 30),
        new Color(0, 255, 255));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200),
        new ShapeSize(100, 30),
        new Color(0, 255, 255));
    m.addAnimation("R", new MotionImpl(0, 15, info1, info2));
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 0 300 300 100 30 0 255 255 15 200 200 100 30 0 255 255\n", m.animationAsText());
  }

  @Test
  public void startAnimationChangeSizeOnly() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
        new ShapeSize(100, 30),
        new Color(0, 255, 255));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 300),
        new ShapeSize(200, 10),
        new Color(0, 255, 255));
    m.addAnimation("R", new MotionImpl(0, 15, info1, info2));
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 0 300 300 100 30 0 255 255 15 300 300 200 10 0 255 255\n", m.animationAsText());
  }

  @Test
  public void startAnimationChangeColorOnly() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
        new ShapeSize(100, 30),
        new Color(0, 255, 255));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 300),
        new ShapeSize(100, 30),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 15, info1, info2));
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 0 300 300 100 30 0 255 255 15 300 300 100 30 255 0 0\n", m.animationAsText());
  }

  @Test
  public void startAnimationChangePositionSize() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
        new ShapeSize(100, 30),
        new Color(0, 255, 255));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(100, 100),
        new ShapeSize(30, 30),
        new Color(0, 255, 255));
    m.addAnimation("R", new MotionImpl(0, 15, info1, info2));
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 0 300 300 100 30 0 255 255 15 100 100 30 30 0 255 255\n", m.animationAsText());
  }

  @Test
  public void startAnimationChangePositionColor() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
        new ShapeSize(100, 30),
        new Color(0, 255, 255));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(100, 100),
        new ShapeSize(100, 30),
        new Color(0, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 15, info1, info2));
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 0 300 300 100 30 0 255 255 15 100 100 100 30 0 0 0\n", m.animationAsText());
  }

  @Test
  public void startAnimationChangeSizeColor() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
        new ShapeSize(100, 30),
        new Color(0, 255, 255));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 300),
        new ShapeSize(100, 100), new Color(0, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 15, info1, info2));
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 0 300 300 100 30 0 255 255 15 300 300 100 100 0 0 0\n", m.animationAsText());
  }

  @Test
  public void startAnimationChangeAll3() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
        new ShapeSize(100, 30),
        new Color(0, 255, 255));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(100, 100),
        new ShapeSize(100, 100), new Color(0, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 15, info1, info2));

    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 0 300 300 100 30 0 255 255 15 100 100 100 100 0 0 0\n", m.animationAsText());
  }

  @Test
  public void noMotionsAnimationTest1() {
    AnimationModel example = new AnimationModelImpl();
    example.addShape(new Oval("yeet"));
    assertEquals("canvas 0 0 500 500\n" + "shape yeet ellipse\n", example.animationAsText());
  }

  @Test
  public void noMotionsAnimationTest2() {
    AnimationModel example = new AnimationModelImpl();
    example.addShape(new Oval("yeet"));
    example.addShape(new Rectangle("foo"));
    assertEquals("canvas 0 0 500 500\n"
        + "shape yeet ellipse\nshape foo rectangle\n", example.animationAsText());
  }

  @Test
  public void noShapesAnimationTest() {
    AnimationModel example = new AnimationModelImpl();
    assertEquals("canvas 0 0 500 500\n", example.animationAsText());
  }

  @Test
  public void nullShapesTest() {
    try {
      new AnimationModelImpl(null);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given list of shapes is null", e.getMessage());
    }
  }

  @Test
  public void nullAddShapesTest() {
    try {
      AnimationModel foo = new AnimationModelImpl();
      foo.addShape(new Oval("yeet"));
      foo.addShape(null);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given shape is null", e.getMessage());
    }
  }

  @Test
  public void notUniqueName() {
    try {
      AnimationModel foo = new AnimationModelImpl();
      foo.addShape(new Oval("yeet"));
      foo.addShape(new Oval("yeet"));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given shape name is not unique", e.getMessage());
    }
  }

  @Test
  public void nullAddAnimationTest1() {
    try {
      AnimationModel foo = new AnimationModelImpl();
      new ShapeInfoImpl(null, null, null);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("invalid shape info containing null", e.getMessage());
    }
  }

  @Test
  public void nullAddAnimationTest2() {
    try {
      AnimationModel foo = new AnimationModelImpl();
      foo.addShape(new Oval("yeet"));
      ShapeInfo info = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
          new Color(255, 0, 0));
      foo.addAnimation(null, new MotionImpl(0, 10, info, info));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("given name is null", e.getMessage());
    }
  }

  @Test
  public void shapeNotFoundTest() {
    try {
      AnimationModel foo = new AnimationModelImpl();
      foo.addShape(new Oval("yeet"));
      ShapeInfo info = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
          new Color(255, 0, 0));
      foo.addAnimation("bar", new MotionImpl(0, 10, info, info));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given name does not have a matching shape", e.getMessage());
    }

  }

  @Test
  public void invalidTimeStart() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));

      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
          new ShapeSize(100, 30),
          new Color(0, 255, 255));
      ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      m.addAnimation("R", new MotionImpl(-1, 1, info1, info2));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("time cannot be less than 0", e.getMessage());
    }
  }

  @Test
  public void invalidTimeEnd() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));

      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
          new ShapeSize(100, 30),
          new Color(0, 255, 255));
      ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      m.addAnimation("R", new MotionImpl(0, -1, info1, info2));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("time cannot be less than 0", e.getMessage());
    }
  }

  @Test
  public void invalidTimeLessThan() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));

      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
          new ShapeSize(100, 30),
          new Color(0, 255, 255));
      ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      m.addAnimation("R", new MotionImpl(5, 3, info1, info2));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("time is invalid, start time must be < finish time", e.getMessage());
    }
  }

  @Test
  public void invalidTimeLessThan2() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));

      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
          new ShapeSize(100, 30),
          new Color(0, 255, 255));
      ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      m.addAnimation("R", new MotionImpl(8, 1, info1, info2));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("time is invalid, start time must be < finish time", e.getMessage());
    }
  }

  @Test
  public void addAnimationInvalidName() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));
      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
          new ShapeSize(100, 30),
          new Color(0, 255, 255));
      ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      m.addAnimation(null, new MotionImpl(0, 5, info1, info2));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("given name is null", e.getMessage());
    }
  }

  @Test
  public void startAnimationNotFoundName() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));
      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(300, 300),
          new ShapeSize(100, 30),
          new Color(0, 255, 255));
      ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      m.addAnimation("S", new MotionImpl(0, 5, info1, info2));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given name does not have a matching shape", e.getMessage());
    }
  }



  @Test
  public void invalidColor() {
    try {
      new Color(-1, -1, -1);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("color values are invalid, must be between 0 and 255", e.getMessage());
    }
  }

  @Test
  public void invalidColor2() {
    try {
      new Color(-1, 15, 15);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("color values are invalid, must be between 0 and 255", e.getMessage());
    }
  }

  @Test
  public void invalidColor3() {
    try {
      new Color(-1, -10, 15);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("color values are invalid, must be between 0 and 255", e.getMessage());
    }
  }

  @Test
  public void invalidSize() {
    try {
      new ShapeSize(-1, -1);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the height or width are less than 0", e.getMessage());
    }
  }

  @Test
  public void invalidSize3() {
    try {
      new ShapeSize(0, -1);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the height or width are less than 0", e.getMessage());
    }
  }

  @Test
  public void invalidSize4() {
    try {
      new ShapeSize(5, -1);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the height or width are less than 0", e.getMessage());
    }
  }


  @Test
  public void invalidSize5() {
    try {
      new ShapeSize(-1, 5);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the height or width are less than 0", e.getMessage());
    }
  }

  @Test
  public void invalidSize6() {
    try {
      new ShapeSize(-100, 5);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the height or width are less than 0", e.getMessage());
    }
  }


  @Test
  public void invalidShapeNull() {
    try {
      new Rectangle(null);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given name is null", e.getMessage());
    }
  }

  @Test
  public void invalidShapeNull2() {
    try {
      new Rectangle("hi");
      new Oval(null);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given name is null", e.getMessage());
    }
  }

  @Test
  public void invalidShapeTime() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));
      ShapeInfo info = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      m.addAnimation("R", new MotionImpl(-5, 2, info, info));
    } catch (IllegalArgumentException e) {
      assertEquals("time cannot be less than 0", e.getMessage());
    }
  }

  @Test
  public void badMotionTime1() {
    try {
      new MotionImpl(-1, 1, new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0)),
          new ShapeInfoImpl(new Position2D(200, 200),
              new ShapeSize(50, 100), new Color(255, 0, 0)));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("time cannot be less than 0", e.getMessage());
    }
  }

  @Test
  public void badMotionTime2() {
    try {
      new MotionImpl(1, -1, new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0)),
          new ShapeInfoImpl(new Position2D(200, 200),
              new ShapeSize(50, 100), new Color(255, 0, 0)));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("time cannot be less than 0", e.getMessage());
    }

  }

  @Test
  public void badMotionTime3() {
    try {
      new MotionImpl(5, 3, new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0)),
          new ShapeInfoImpl(new Position2D(200, 200),
              new ShapeSize(50, 100), new Color(255, 0, 0)));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("time is invalid, start time must be < finish time", e.getMessage());
    }

  }

  @Test
  public void badMotionAttributes1() {
    try {
      new MotionImpl(5, 3, null,
          new ShapeInfoImpl(new Position2D(200, 200),
              new ShapeSize(50, 100), new Color(255, 0, 0)));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the starting or ending shape information is null", e.getMessage());
    }
  }

  @Test
  public void badMotionAttributes2() {
    try {
      new MotionImpl(5, 3,
          new ShapeInfoImpl(new Position2D(200, 200),
              new ShapeSize(50, 100), new Color(255, 0, 0)), null);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the starting or ending shape information is null", e.getMessage());
    }
  }

  @Test
  public void notAlignedTime() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));
      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
      m.addAnimation("R", new MotionImpl(11, 20, info1, info1));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("given motion doesn't start at end of previous motion", e.getMessage());
    }
  }

  @Test
  public void notAlignedAnimationPos() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));
      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 300),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
      m.addAnimation("R", new MotionImpl(10, 20, info2, info2));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("given motion teleports from previous motion", e.getMessage());
    }
  }


  @Test
  public void notAlignedAnimationSize() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));
      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(100, 100), new Color(255, 0, 0));
      m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
      m.addAnimation("R", new MotionImpl(10, 20, info2, info2));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("given motion teleports from previous motion", e.getMessage());
    }
  }

  @Test
  public void notAlignedAnimationColor() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));
      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      ShapeInfo info2 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 255, 0));
      m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
      m.addAnimation("R", new MotionImpl(10, 20, info2, info2));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("given motion teleports from previous motion", e.getMessage());
    }
  }

  @Test
  public void nullAnimation() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));
      m.addAnimation("R", null);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("given motion is null", e.getMessage());
    }
  }

  @Test
  public void shapeInfoStringNull() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));
      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
      m.shapeInfoAtTime(null, 0);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("given name is null", e.getMessage());
    }
  }

  @Test
  public void shapeInfoTimeNeg() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));
      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
      m.shapeInfoAtTime("R", -1);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("tick cannot be negative", e.getMessage());
    }
  }

  @Test
  public void shapeInfoTimeBadName() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));
      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
      m.shapeInfoAtTime("S", 3);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given name does not have a matching shape", e.getMessage());
    }
  }

  @Test
  public void shapeInfoTime0() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
        new ShapeSize(50, 100), new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
    assertEquals(info1, m.shapeInfoAtTime("R", 0));
  }

  @Test
  public void shapeInfoTime5() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
        new ShapeSize(50, 100), new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
    assertEquals(info1, m.shapeInfoAtTime("R", 5));
  }

  @Test
  public void shapeInfoTime10() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
        new ShapeSize(50, 100), new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
    assertEquals(info1, m.shapeInfoAtTime("R", 10));
  }

  @Test
  public void shapeInfoTimeOutofTime() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
        new ShapeSize(50, 100), new Color(255, 0, 0));
    ShapeInfo blank = new ShapeInfoImpl(new Position2D(0, 0),
        new ShapeSize(0, 0), new Color(0, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
    assertEquals(blank, m.shapeInfoAtTime("R", 15));
  }

  @Test
  public void shapeInfoTimeNoMotions() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));
      m.shapeInfoAtTime("R", 0);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("this shape has no info at the given time", e.getMessage());
    }

  }

  @Test
  public void addMotionThroughShape() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));
      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
      m.getShapes().get("R").addMotion(new MotionImpl(11, 20, info1, info1));
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("given motion does not match previous", e.getMessage());
    }
  }

  @Test
  public void addMotionThroughShapeNull() {
    try {
      AnimationModel m = new AnimationModelImpl();
      m.addShape(new Rectangle("R"));
      ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
          new ShapeSize(50, 100), new Color(255, 0, 0));
      m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
      m.getShapes().get("R").addMotion(null);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("given motion is null", e.getMessage());
    }
  }

  @Test
  public void getEndThroughShape() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200),
        new ShapeSize(50, 100), new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 10, info1, info1));
    assertEquals(10, m.getShapes().get("R").getEnd());
  }

  @Test
  public void getEndThroughShapeEmpty() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    assertEquals(0, m.getShapes().get("R").getEnd());
  }

  @Test
  public void builderTest() {
    AnimationModel model = new AnimationModelImpl.Builder()
        .declareShape("R", "rectangle").build();
    assertEquals("canvas 0 0 500 500\n"
        + "shape R rectangle\n", model.animationAsText());
  }

  @Test
  public void builderTest2() {
    AnimationModel model = new AnimationModelImpl.Builder()
        .declareShape("R", "rectangle")
        .addMotion("R", 0, 200, 200, 200, 200, 200, 200, 200,
        10, 200, 200, 200, 200, 200, 200, 200).build();
    assertEquals("canvas 0 0 500 500\n"
        + "shape R rectangle\n"
        + "motion R 0 200 200 200 200 200 200 200 10 200 200 200 200 200 200 200\n",
        model.animationAsText());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void builderTest3() {
    new AnimationModelImpl.Builder()
        .declareShape("R", "rectangle")
        .addMotion("R", 0, 200, 200, 200, 200, 200, 200, 200,
            10, 200, 200, 200, 200, 200, 200, 200)
        .addKeyframe("R", 10, 200, 200, 200, 200, 200, 200, 200).build();
  }


}
