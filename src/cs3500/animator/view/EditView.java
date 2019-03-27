package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.ReadOnlyAnimationModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

/**
 * The EditView is an AnimationView that allows advanced control over an animation.
 * It allows playback controls, including playing, pausing, restarting, changing the speed, and
 * looping. It also allows editing controls, including adding and deleting new shapes as well as
 * adding, editing, and deleting keyframes on those shapes.
 */
public class EditView extends JFrame implements AnimationView {

  private ReadOnlyAnimationModel model;
  private AnimationPanel panel;
  private JScrollPane pane;
  private JButton playPauseButton;
  private JButton restartButton;
  private JToggleButton loopButton;
  private JButton incSpeedButton;
  private JButton decSpeedButton;

  /**
   * Constructs an edit view given an animation model and ticks per second.
   *
   * @param model the given animation model
   * @param tps the ticks per second
   * @throws IllegalArgumentException if model or ticks are invalid
   */
  public EditView(ReadOnlyAnimationModel model, int tps) {
    if (model == null) {
      throw new IllegalArgumentException("model is null");
    }
    if (tps <= 0) {
      throw new IllegalArgumentException("ticks per second must be greater than 0");
    }
    this.model = model;

    this.setTitle("Animation");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocation(model.getWindowX(), model.getWindowY());

    panel = new AnimationPanel();
    panel.setPreferredSize(new Dimension(model.getWindowWidth(), model.getWindowHeight()));
    pane = new JScrollPane(panel);
    pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    pane.setPreferredSize(new Dimension(model.getWindowWidth(), model.getWindowHeight()));
    this.add(pane, BorderLayout.CENTER);

    this.playPauseButton = new JButton("Play/Pause");
    this.playPauseButton.setActionCommand("PlayPause Button");
    this.restartButton = new JButton("Restart");
    this.restartButton.setActionCommand("Restart Button");
    this.loopButton = new JToggleButton("Loop", false);
    this.loopButton.setActionCommand("Loop Button");
    this.incSpeedButton = new JButton("Speed Up");
    this.incSpeedButton.setActionCommand("IncSpeed Button");
    this.decSpeedButton = new JButton("Speed Down");
    this.decSpeedButton.setActionCommand("DecSpeed Button");

    this.setLayout(new FlowLayout());

    this.add(playPauseButton);
    this.add(restartButton);
    this.add(loopButton);
    this.add(incSpeedButton);
    this.add(decSpeedButton);

    pane.setPreferredSize(new Dimension(700, 700));
    this.pack();
  }

  @Override
  public void addActionListener(ActionListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("the given listener is null");
    }
    this.playPauseButton.addActionListener(listener);
    this.restartButton.addActionListener(listener);
    this.loopButton.addActionListener(listener);
    this.incSpeedButton.addActionListener(listener);
    this.decSpeedButton.addActionListener(listener);
  }

  @Override
  public ViewType getViewType() {
    return ViewType.Edit;
  }

  @Override
  public AnimationPanel getPanel() {
    return this.panel;
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
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
  public boolean sameModel(AnimationModel m) {
    if (m == null) {
      throw new IllegalArgumentException("the given model is null");
    }
    return m == this.model;
  }
}
