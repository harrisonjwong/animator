package cs3500.animator.view;

import cs3500.animator.model.ReadOnlyAnimationModel;
import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.shapes.Shape;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/**
 * Represents the visual view, which shows a Java Swing version of the animation. It creates a
 * JFrame, which contains a JPanel, which has the shapes being animated.
 */
public class VisualView extends JFrame implements AnimationView {

  private ReadOnlyAnimationModel model;
  private AnimationPanel panel;
  private JScrollPane pane;

  /**
   * Constructs a visual view given an animation model and ticks per second.
   * @param model the given animation model
   * @param tps the ticks per second
   * @throws IllegalArgumentException if model or ticks are invalid
   */
  public VisualView(ReadOnlyAnimationModel model, int tps) {
    super();
    if (model == null) {
      throw new IllegalArgumentException("model is null");
    }
    if (tps <= 0) {
      throw new IllegalArgumentException("ticks per second must be greater than 0");
    }
    this.model = model;

    setupView();

    this.pack();
  }

  private void setupView() {
    this.setTitle("Animation");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocation(model.getWindowX(), model.getWindowY());
//    this.setLayout(new BorderLayout());

    panel = new AnimationPanel();
    panel.setPreferredSize(new Dimension(model.getWindowWidth(), model.getWindowHeight()));
//    this.add(panel);
    pane = new JScrollPane(panel);
    pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    pane.setPreferredSize(new Dimension(model.getWindowWidth(), model.getWindowHeight()));
    this.add(pane, BorderLayout.CENTER);


  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
//    this.timer.start();
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void addActionListener(ActionListener listener) {
//    throw new UnsupportedOperationException("no need to add action listener to visual view");
  }

  @Override
  public boolean isTimeable() {
    return true;
  }

  @Override
  public AnimationPanel getPanel() {
    return this.panel;
  }

}