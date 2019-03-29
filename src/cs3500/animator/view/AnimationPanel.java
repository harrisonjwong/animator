package cs3500.animator.view;

import cs3500.animator.model.motions.info.ShapeInfo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * An animation panel contains the actual animation being played on the screen. It paints the
 * given shapes onto the screen.
 */
public class AnimationPanel extends JPanel {

  private List<ShapeInfo> shapes = new ArrayList<>();
  private List<String> types = new ArrayList<>();

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    for (int i = 0; i < shapes.size(); i++) {
      ShapeInfo info = shapes.get(i);
      String type = types.get(i);
      g2.setColor(new Color(info.getColor().getR(), info.getColor().getG(),
          info.getColor().getB()));
      if (type.equals("rectangle")) { //string checking used because these methods already exist
        g2.fillRect(info.getPosition().getX(), info.getPosition().getY(),
            info.getSize().getW(), info.getSize().getH());
      } else if (type.equals("ellipse")) {
        g2.fillOval(info.getPosition().getX(), info.getPosition().getY(),
            info.getSize().getW(), info.getSize().getH());
      }
    }
  }

  /**
   * Sets the shape information for this animation panel to the given.
   * @param infos the list of shape information
   * @throws IllegalArgumentException if the list is null
   */
  public void setInfos(List<ShapeInfo> infos) {
    if (infos == null) {
      throw new IllegalArgumentException("the given shape information is null");
    }
    this.shapes = infos;
  }

  /**
   * Sets the shape types for this animation panel to to the given.
   * @param types the list of shape types
   * @throws IllegalArgumentException if the list is null
   */
  public void setTypes(List<String> types) {
    if (types == null) {
      throw new IllegalArgumentException("the given shape types are null");
    }
    this.types = types;
  }
}
