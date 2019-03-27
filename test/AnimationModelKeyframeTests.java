import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.motions.MotionImpl;
import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.motions.info.ShapeInfoImpl;
import cs3500.animator.model.shapes.Rectangle;
import cs3500.animator.model.types.Color;
import cs3500.animator.model.types.Position2D;
import cs3500.animator.model.types.ShapeSize;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class for keyframe addition to model.
 */
public class AnimationModelKeyframeTests {

  private AnimationModel model;

  @Before
  public void setUp() {
    this.model = new AnimationModelImpl();
    this.model.addShape(new Rectangle("R"));
  }

  @Test
  public void addKeyframeNullName() {
    try {
      model.addKeyframe(null, 0);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given name is null", e.getMessage());
    }
  }

  @Test
  public void addKeyframeBadTime() {
    try {
      model.addKeyframe("R", -1);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given time cannot be less than 0", e.getMessage());
    }
  }

  @Test
  public void addKeyframeShapeNotFound() {
    String msg = model.addKeyframe("S", 0);
    assertEquals("shape S cannot be found", msg);
  }

  @Test
  public void addKeyframeNoMotions() {
    String firstMsg = model.addKeyframe("R", 0);
    assertEquals("keyframe successfully added for shape R at time 0", firstMsg);
    assertEquals("canvas 0 0 500 500\n"
        + "shape R rectangle\n", model.animationAsText());
  }

  @Test
  public void addKeyframeHaveTimeButNoInfo() {
    String firstMsg = model.addKeyframe("R", 0);
    assertEquals("keyframe successfully added for shape R at time 0", firstMsg);
    String secondMsg = model.addKeyframe("R", 10);
    assertEquals("error: must add keyframe information for shape R at time 0 first", secondMsg);
    assertEquals("canvas 0 0 500 500\n"
        + "shape R rectangle\n", model.animationAsText());
  }

  @Test
  public void addKeyframeBeforeFirstMotion() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    assertEquals("canvas 0 0 500 500\n"
        + "shape R rectangle\n"
        + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n",
        model.animationAsText());
    String msg1 = model.addKeyframe("R", 0);
    assertEquals("keyframe successfully added for shape R at time 0", msg1);
    assertEquals("canvas 0 0 500 500\n"
        + "shape R rectangle\n"
        + "motion R 0 200 200 50 100 255 0 0 10 200 200 50 100 255 0 0\n"
        + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n",
        model.animationAsText());
  }

  @Test
  public void addKeyframeAfterLastMotion() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n",
        model.animationAsText());
    String msg1 = model.addKeyframe("R", 30);
    assertEquals("keyframe successfully added for shape R at time 30", msg1);
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n"
            + "motion R 20 200 200 50 100 255 0 0 30 200 200 50 100 255 0 0\n",
        model.animationAsText());
  }

  @Test
  public void addKeyframeAlreadyExists() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    model.addKeyframe("R", 30);

    String msg1 = model.addKeyframe("R", 10);
    String msg2 = model.addKeyframe("R", 20);
    String msg3 = model.addKeyframe("R", 30);

    assertEquals("keyframe for shape R already exists at 10", msg1);
    assertEquals("keyframe for shape R already exists at 20", msg2);
    assertEquals("keyframe for shape R already exists at 30", msg3);
    assertEquals("canvas 0 0 500 500\n"
        + "shape R rectangle\n"
        + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n"
        + "motion R 20 200 200 50 100 255 0 0 30 200 200 50 100 255 0 0\n",
        model.animationAsText());
  }

  @Test
  public void addKeyframeTweened() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 300), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info2));
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 20 300 300 50 100 255 0 0\n",
        model.animationAsText());
    String msg = model.addKeyframe("R", 15);
    assertEquals("keyframe successfully added for shape R at time 15", msg);
    assertEquals("canvas 0 0 500 500\n"
        + "shape R rectangle\n"
        + "motion R 10 200 200 50 100 255 0 0 15 250 250 50 100 255 0 0\n"
        + "motion R 15 250 250 50 100 255 0 0 20 300 300 50 100 255 0 0\n",
        model.animationAsText());
  }

  @Test
  public void addKeyframeHaveTimeAndInfo() {
    String firstMsg = model.addKeyframe("R", 0);
    assertEquals("keyframe successfully added for shape R at time 0", firstMsg);
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    String secondMsg = model.editKeyframe("R", 0, info1);
    assertEquals("keyframe successfully edited for shape R at time 0", secondMsg);
    String thirdMsg = model.addKeyframe("R", 10);
    assertEquals("keyframe successfully added for shape R at time 10; "
        + "motion successfully added from time 0 to 10", thirdMsg);
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 0 200 200 50 100 255 0 0 10 200 200 50 100 255 0 0\n",
        model.animationAsText());
  }

  @Test
  public void editKeyframeNoMotionsNoFirstKeyframe() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    String firstMsg = model.editKeyframe("R", 0, info1);
    assertEquals("error: must add a keyframe for shape R first", firstMsg);
  }

  @Test
  public void editKeyframeNoMotionsWrongTime() {
    String firstMsg = model.addKeyframe("R", 5);
    assertEquals("keyframe successfully added for shape R at time 5", firstMsg);
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    String secondMsg = model.editKeyframe("R", 0, info1);
    assertEquals("error: must edit the keyframe for shape R at time 5 first", secondMsg);
  }

  @Test
  public void editKeyframeFirstMotion() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n",
        model.animationAsText());
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 300), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    String msg = model.editKeyframe("R", 10, info2);
    assertEquals("keyframe successfully edited for shape R at time 10", msg);
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 300 300 50 100 255 0 0 20 200 200 50 100 255 0 0\n",
        model.animationAsText());
  }

  @Test
  public void editKeyframeLastMotion() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n",
        model.animationAsText());
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 300), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    String msg = model.editKeyframe("R", 20, info2);
    assertEquals("keyframe successfully edited for shape R at time 20", msg);
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 20 300 300 50 100 255 0 0\n",
        model.animationAsText());
  }

  @Test
  public void editKeyframeMid() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    model.addAnimation("R", new MotionImpl(20, 30, info1, info1));
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n"
            + "motion R 20 200 200 50 100 255 0 0 30 200 200 50 100 255 0 0\n",
        model.animationAsText());
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 300), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    String msg = model.editKeyframe("R", 20, info2);
    assertEquals("keyframe successfully edited for shape R at time 20", msg);
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 20 300 300 50 100 255 0 0\n"
            + "motion R 20 300 300 50 100 255 0 0 30 200 200 50 100 255 0 0\n",
        model.animationAsText());
  }

  @Test
  public void editKeyframeNoKeyframe() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    model.addAnimation("R", new MotionImpl(20, 30, info1, info1));
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n"
            + "motion R 20 200 200 50 100 255 0 0 30 200 200 50 100 255 0 0\n",
        model.animationAsText());
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 300), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    String msg = model.editKeyframe("R", 22, info2);
    assertEquals("error: no keyframe exists for shape R at time 22", msg);
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n"
            + "motion R 20 200 200 50 100 255 0 0 30 200 200 50 100 255 0 0\n",
        model.animationAsText());
  }

  @Test
  public void editKeyframeNameNull() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    model.addAnimation("R", new MotionImpl(20, 30, info1, info1));
    try {
      model.editKeyframe(null, 20, info1);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given name is null", e.getMessage());
    }
  }

  @Test
  public void editKeyframeInfoNull() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    model.addAnimation("R", new MotionImpl(20, 30, info1, info1));
    try {
      model.editKeyframe("R", 20, null);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given info is null", e.getMessage());
    }
  }

  @Test
  public void editKeyframeInvalidTime() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    model.addAnimation("R", new MotionImpl(20, 30, info1, info1));
    try {
      model.editKeyframe("R", -1, info1);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given time cannot be less than 0", e.getMessage());
    }
  }

  @Test
  public void editKeyframeShapeNotFound() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    model.addAnimation("R", new MotionImpl(20, 30, info1, info1));
    String msg = model.editKeyframe("S", 10, info1);
    assertEquals("shape S cannot be found", msg);
  }

  @Test
  public void removeKeyframeNullName() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    model.addAnimation("R", new MotionImpl(20, 30, info1, info1));
    try {
      model.deleteKeyframe(null, 10);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given name is null", e.getMessage());
    }
  }

  @Test
  public void removeKeyframeBadTime() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    model.addAnimation("R", new MotionImpl(20, 30, info1, info1));
    try {
      model.deleteKeyframe("R", -1);
      fail("exception not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("the given time cannot be less than 0", e.getMessage());
    }
  }

  @Test
  public void removeKeyframeShapeNotFound() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    model.addAnimation("R", new MotionImpl(20, 30, info1, info1));
    String msg = model.deleteKeyframe("T", 20);
    assertEquals("shape T cannot be found", msg);
  }

  @Test
  public void removeKeyframeStart() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    model.addAnimation("R", new MotionImpl(20, 30, info1, info1));
    assertEquals("canvas 0 0 500 500\n"
        + "shape R rectangle\n"
        + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n"
        + "motion R 20 200 200 50 100 255 0 0 30 200 200 50 100 255 0 0\n",
        model.animationAsText());
    String msg = model.deleteKeyframe("R", 10);
    assertEquals("keyframe successfully deleted for shape R at time 10", msg);
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 20 200 200 50 100 255 0 0 30 200 200 50 100 255 0 0\n",
        model.animationAsText());
  }

  @Test
  public void removeKeyframeEnd() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    model.addAnimation("R", new MotionImpl(20, 30, info1, info1));
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n"
            + "motion R 20 200 200 50 100 255 0 0 30 200 200 50 100 255 0 0\n",
        model.animationAsText());
    String msg = model.deleteKeyframe("R", 30);
    assertEquals("keyframe successfully deleted for shape R at time 30", msg);
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n",
        model.animationAsText());
  }

  @Test
  public void removeKeyframeMid() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    model.addAnimation("R", new MotionImpl(20, 30, info1, info1));
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n"
            + "motion R 20 200 200 50 100 255 0 0 30 200 200 50 100 255 0 0\n",
        model.animationAsText());
    String msg = model.deleteKeyframe("R", 20);
    assertEquals("keyframe successfully deleted for shape R at time 20", msg);
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 30 200 200 50 100 255 0 0\n",
        model.animationAsText());
  }

  @Test
  public void removeKeyframeSoloNoInfo() {
    String msg1 = model.addKeyframe("R", 0);
    assertEquals("keyframe successfully added for shape R at time 0", msg1);
    String msg2 = model.deleteKeyframe("R", 0);
    assertEquals("keyframe successfully deleted for shape R at time 0", msg2);
  }

  @Test
  public void removeKeyframeSoloWithInfo() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    String msg1 = model.addKeyframe("R", 0);
    assertEquals("keyframe successfully added for shape R at time 0", msg1);
    String msg11 = model.editKeyframe("R", 0, info1);
    assertEquals("keyframe successfully edited for shape R at time 0", msg11);
    String msg2 = model.deleteKeyframe("R", 0);
    assertEquals("keyframe successfully deleted for shape R at time 0", msg2);
  }

  @Test
  public void removeKeyframeNoSolo() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    String msg1 = model.addKeyframe("R", 0);
    assertEquals("keyframe successfully added for shape R at time 0", msg1);
    String msg11 = model.editKeyframe("R", 0, info1);
    assertEquals("keyframe successfully edited for shape R at time 0", msg11);
    String msg2 = model.deleteKeyframe("R", 5);
    assertEquals("error: no keyframe exists for shape R at time 5", msg2);
  }

  @Test
  public void removeKeyframeNotExist() {
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    model.addAnimation("R", new MotionImpl(10, 20, info1, info1));
    model.addAnimation("R", new MotionImpl(20, 30, info1, info1));
    String msg = model.deleteKeyframe("R", 21);
    assertEquals("error: no keyframe exists for shape R at time 21", msg);
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 10 200 200 50 100 255 0 0 20 200 200 50 100 255 0 0\n"
            + "motion R 20 200 200 50 100 255 0 0 30 200 200 50 100 255 0 0\n",
        model.animationAsText());
  }

  @Test
  public void addKeyframeMid() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(0, 20, info1, info1));
    ShapeInfo info2 = new ShapeInfoImpl(new Position2D(300, 300), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    ShapeInfo info3 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(100, 100),
        new Color(255, 0, 0));
    m.addAnimation("R", new MotionImpl(20, 25, info1, info2));
    m.addKeyframe("R", 10);
    m.editKeyframe("R", 10, info3);
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n"
        + "motion R 0 200 200 50 100 255 0 0 10 200 200 100 100 255 0 0\n"
        + "motion R 10 200 200 100 100 255 0 0 20 200 200 50 100 255 0 0\n"
        + "motion R 20 200 200 50 100 255 0 0 25 300 300 50 100 255 0 0\n", m.animationAsText());
  }

  @Test
  public void addKeyframeNone() {
    AnimationModel m = new AnimationModelImpl();
    m.addShape(new Rectangle("R"));
    ShapeInfo info1 = new ShapeInfoImpl(new Position2D(200, 200), new ShapeSize(50, 100),
        new Color(255, 0, 0));
    m.addKeyframe("R", 10);
    assertEquals("canvas 0 0 500 500\n" + "shape R rectangle\n", m.animationAsText());
  }

}
