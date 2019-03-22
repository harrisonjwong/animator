package cs3500.animator.model.types;

import java.util.Objects;

/**
 * Represents a color with red, green, and blue (RGB) values. A color object is a value object
 * and cannot be mutated. The RGB values are all integers between 0 and 255 inclusive.
 */
public final class Color {

  /**
   * The red value.
   */
  private final int red;

  /**
   * The green value.
   */
  private final int green;

  /**
   * The blue value.
   */
  private final int blue;

  /**
   * Constructs a color using the given red, green, and blue values.
   * @param red the value for red
   * @param green the value for green
   * @param blue the value for blue
   * @throws IllegalArgumentException if the given color values are not in the range 0 to 255
   */
  public Color(int red, int green, int blue) {
    if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
      throw new IllegalArgumentException("color values are invalid, must be between 0 and 255");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Returns the textual representation of this color,
   * which contains the red, green, and blue values.
   * @return String containing the red, green, and blue values
   */
  @Override
  public String toString() {
    return this.red + " " + this.green + " " + this.blue;
  }

  //colors are considered to be equal if their rgb values are the same
  @Override
  public boolean equals(Object other) {
    if (other instanceof Color) {
      Color that = (Color)other;
      return that.red == this.red && that.green == this.green && that.blue == this.blue;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }

  /**
   * Gets the red value for this rgb color.
   * @return the integer red value
   */
  public int getR() {
    return red;
  }

  /**
   * Gets the green value for this rgb color.
   * @return the integer green value
   */
  public int getG() {
    return green;
  }

  /**
   * Gets the blue value for this rgb color.
   * @return the integer blue value.
   */
  public int getB() {
    return blue;
  }
}
