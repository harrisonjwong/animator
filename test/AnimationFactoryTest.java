import cs3500.animator.util.AnimationFactory;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.AnimationView.ViewType;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.VisualView;
import java.io.FileNotFoundException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class for AnimationFactory.
 */
public class AnimationFactoryTest {

  @Test
  public void empty() {
    try {
      new AnimationFactory(new String[]{});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("no argument found for -in", e.getMessage());
    }
  }

  @Test
  public void noView() {
    try {
      new AnimationFactory(new String[]{"-in", "buildings.txt"});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("no argument found for -view", e.getMessage());
    }
  }

  @Test
  public void noIn() {
    try {
      new AnimationFactory(new String[]{"-view", "visual"});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("no argument found for -in", e.getMessage());
    }
  }

  @Test
  public void nothingFollowingIn() {
    try {
      new AnimationFactory(new String[]{"-in"});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("no valid argument found following -in", e.getMessage());
    }
  }

  @Test
  public void nothingFollowingView() {
    try {
      new AnimationFactory(new String[]{"-in", "buildings.txt", "-view"});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("no valid argument found following -view", e.getMessage());
    }
  }

  @Test
  public void noArguments() {
    try {
      new AnimationFactory(new String[]{"-in", "-view"});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("no valid argument found following -view", e.getMessage());
    }
  }

  @Test
  public void noArgumentsIn() {
    try {
      new AnimationFactory(new String[]{"-view", "-in"});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("no valid argument found following -in", e.getMessage());
    }
  }

  @Test
  public void invalidViewType() {
    try {
      new AnimationFactory(new String[]{"-view", "hi", "-in", "buildings.txt"});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("invalid view type", e.getMessage());
    }
  }

  @Test
  public void textView() {
    try {
      AnimationView view = new AnimationFactory(new String[]{"-view", "text", "-in",
          "buildings.txt"}).getView();
      assertEquals(ViewType.Text, view.getViewType());
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void textViewType2() {
    try {
      AnimationView view = new AnimationFactory(new String[]{"-view", "text", "-in",
          "buildings.txt"}).getView();
      assertEquals(false, view instanceof VisualView);
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void visualViewType() {
    try {
      AnimationView view = new AnimationFactory(new String[]{"-view", "visual", "-in",
          "buildings.txt"}).getView();
      assertEquals(ViewType.Visual, view.getViewType());
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void visualViewType2() {
    try {
      AnimationView view = new AnimationFactory(new String[]{"-view", "visual", "-in",
          "buildings.txt"}).getView();
      assertEquals(false, view instanceof SVGView);
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void editViewType() {
    try {
      AnimationView view = new AnimationFactory(new String[]{"-view", "edit", "-in",
          "buildings.txt"}).getView();
      assertEquals(ViewType.Edit, view.getViewType());
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }


  @Test
  public void editViewType2() {
    try {
      AnimationView view = new AnimationFactory(new String[]{"-view", "edit", "-in",
          "buildings.txt"}).getView();
      assertEquals(false, view instanceof TextView);
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void svgViewType() {
    try {
      AnimationFactory factory = new AnimationFactory(new String[]{"-view", "svg", "-in",
          "buildings.txt"});
      AnimationView view = factory.getView();
      assertEquals(true, view instanceof SVGView);
      assertEquals(false, factory.getModel().getShapes().isEmpty());
      assertEquals(1, factory.getSpeed());
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void svgViewType2() {
    try {
      AnimationView view = new AnimationFactory(new String[]{"-view", "svg", "-in",
          "buildings.txt"}).getView();
      assertEquals(false, view instanceof TextView);
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void outDefault() {
    try {
      new AnimationFactory(new String[]{"-view", "svg", "-in",
          "buildings.txt", "-out"});
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void speedDefault() {
    try {
      new AnimationFactory(new String[]{"-view", "svg", "-in",
          "buildings.txt", "-speed"});
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void speedNeg() {
    try {
      new AnimationFactory(new String[]{"-view", "svg", "-in",
          "buildings.txt", "-speed", "-1"});
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void speedZero() {
    try {
      new AnimationFactory(new String[]{"-view", "svg", "-in",
          "buildings.txt", "-speed", "0"});
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void speedGood() {
    try {
      new AnimationFactory(new String[]{"-view", "svg", "-in",
          "buildings.txt", "-speed", "20"});
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }
}
