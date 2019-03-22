import cs3500.animator.util.AnimationFactory;
import cs3500.animator.view.AnimationView;
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
      AnimationFactory.get(new String[]{});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("no argument found for -in", e.getMessage());
    }
  }

  @Test
  public void noView() {
    try {
      AnimationFactory.get(new String[]{"-in", "buildings.txt"});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("no argument found for -view", e.getMessage());
    }
  }

  @Test
  public void noIn() {
    try {
      AnimationFactory.get(new String[]{"-view", "visual"});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("no argument found for -in", e.getMessage());
    }
  }

  @Test
  public void nothingFollowingIn() {
    try {
      AnimationFactory.get(new String[]{"-in"});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("no valid argument found following -in", e.getMessage());
    }
  }

  @Test
  public void nothingFollowingView() {
    try {
      AnimationFactory.get(new String[]{"-in", "buildings.txt", "-view"});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("no valid argument found following -view", e.getMessage());
    }
  }

  @Test
  public void noArguments() {
    try {
      AnimationFactory.get(new String[]{"-in", "-view"});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("no valid argument found following -view", e.getMessage());
    }
  }

  @Test
  public void noArgumentsIn() {
    try {
      AnimationFactory.get(new String[]{"-view", "-in"});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("no valid argument found following -in", e.getMessage());
    }
  }

  @Test
  public void invalidViewType() {
    try {
      AnimationFactory.get(new String[]{"-view", "hi", "-in", "buildings.txt"});
      fail("exception not thrown");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertEquals("invalid view type", e.getMessage());
    }
  }

  @Test
  public void textView() {
    try {
      AnimationView view = AnimationFactory.get(new String[]{"-view", "text", "-in",
          "buildings.txt"});
      assertEquals(true, view instanceof TextView);
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void textViewType2() {
    try {
      AnimationView view = AnimationFactory.get(new String[]{"-view", "text", "-in",
          "buildings.txt"});
      assertEquals(false, view instanceof VisualView);
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void visualViewType() {
    try {
      AnimationView view = AnimationFactory.get(new String[]{"-view", "visual", "-in",
          "buildings.txt"});
      assertEquals(true, view instanceof VisualView);
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void visualViewType2() {
    try {
      AnimationView view = AnimationFactory.get(new String[]{"-view", "visual", "-in",
          "buildings.txt"});
      assertEquals(false, view instanceof SVGView);
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void svgViewType() {
    try {
      AnimationView view = AnimationFactory.get(new String[]{"-view", "svg", "-in",
          "buildings.txt"});
      assertEquals(true, view instanceof SVGView);
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void svgViewType2() {
    try {
      AnimationView view = AnimationFactory.get(new String[]{"-view", "svg", "-in",
          "buildings.txt"});
      assertEquals(false, view instanceof TextView);
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void outDefault() {
    try {
      AnimationView view = AnimationFactory.get(new String[]{"-view", "svg", "-in",
          "buildings.txt", "-out"});
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void speedDefault() {
    try {
      AnimationView view = AnimationFactory.get(new String[]{"-view", "svg", "-in",
          "buildings.txt", "-speed"});
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void speedNeg() {
    try {
      AnimationView view = AnimationFactory.get(new String[]{"-view", "svg", "-in",
          "buildings.txt", "-speed", "-1"});
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void speedZero() {
    try {
      AnimationView view = AnimationFactory.get(new String[]{"-view", "svg", "-in",
          "buildings.txt", "-speed", "0"});
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

  @Test
  public void speedGood() {
    try {
      AnimationView view = AnimationFactory.get(new String[]{"-view", "svg", "-in",
          "buildings.txt", "-speed", "20"});
    } catch (FileNotFoundException e) {
      fail("exception thrown");
    }
  }

}
