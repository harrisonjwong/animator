package cs3500.animator.view;

import cs3500.animator.model.ReadOnlyAnimationModel;
import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.shapes.Shape;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
  private Timer timer;
  private int tick;

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
    this.tick = 0;

    int delay = 1000 / tps;

    setupView();

    timer = new Timer(delay, ((ActionEvent e) -> {
      List<ShapeInfo> infos = new ArrayList<>();
      List<String> types = new ArrayList<>();

      HashMap<String, Shape> shapes = model.getShapes();
      for (String s : shapes.keySet()) {
        ShapeInfo curinfo = model.shapeInfoAtTime(s, tick);
        String type = shapes.get(s).getType();
        infos.add(curinfo);
        types.add(type);
      }
      panel.setInfos(infos);
      panel.setTypes(types);

      refresh();
      if (this.tick >= model.getEndingTick()) {
        timer.stop();
        System.exit(0);
      } else {
        this.tick++;
      }

    }));
    timer.start();

    panel.setPreferredSize(new Dimension(model.getWindowWidth(), model.getWindowHeight()));
    this.pack();
  }

  private void setupView() {
    this.setTitle("Animation");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocation(model.getWindowX(), model.getWindowY());
    this.setLayout(new BorderLayout());

    panel = new AnimationPanel();
    panel.setPreferredSize(new Dimension(model.getWindowWidth(), model.getWindowHeight()));
    JScrollPane pane = new JScrollPane(panel);
    this.add(pane, BorderLayout.CENTER);

  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
  }



}
