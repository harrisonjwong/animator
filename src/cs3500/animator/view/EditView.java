package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.ReadOnlyAnimationModel;
import cs3500.animator.model.shapes.Shape;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;

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

  private JButton addShapeButton;
  private JButton removeShapeButton;
  private JButton addKeyframeButton;
  private JButton editKeyframeButton;
  private JButton removeKeyframeButton;
  private JButton saveButton;
  private JButton loadButton;

  private JLabel tickLabel;

  //private JPanel topButtonPanel;
  //private JPanel bottomButtonPanel;
  private JPanel selectionListPanel;

  private JList<String> listOfStrings;

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

    this.setLayout(new BorderLayout());

    panel = new AnimationPanel();
    panel.setPreferredSize(new Dimension(model.getWindowWidth(), model.getWindowHeight()));
    pane = new JScrollPane(panel);
    pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    pane.setPreferredSize(new Dimension(model.getWindowWidth(), model.getWindowHeight()));
    this.add(pane, BorderLayout.CENTER);

    //Playback controls
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
    this.tickLabel = new JLabel();

    //Top button bar
    JPanel topButtonPanel = new JPanel();
    topButtonPanel.add(playPauseButton);
    topButtonPanel.add(restartButton);
    topButtonPanel.add(loopButton);
    topButtonPanel.add(incSpeedButton);
    topButtonPanel.add(decSpeedButton);
    topButtonPanel.add(this.tickLabel);
    this.add(topButtonPanel, BorderLayout.NORTH);


    //Editing controls
    this.addShapeButton = new JButton("Add Shape");
    this.addShapeButton.setActionCommand("Add Shape Button");
    this.removeShapeButton = new JButton("Remove Shape");
    this.removeShapeButton.setActionCommand("Remove Shape Button");
    this.addKeyframeButton = new JButton("Add Keyframe");
    this.addKeyframeButton.setActionCommand("Add Keyframe Button");
    this.editKeyframeButton = new JButton("Edit Keyframe");
    this.editKeyframeButton.setActionCommand("Edit Keyframe Button");
    this.removeKeyframeButton = new JButton("Remove Keyframe");
    this.removeKeyframeButton.setActionCommand("Remove Keyframe Button");
    this.saveButton = new JButton("Save");
    this.saveButton.setActionCommand("Save Button");
    this.loadButton = new JButton("Load");
    this.loadButton.setActionCommand("Load Button");


    JPanel bottomButtonPanel = new JPanel();
    bottomButtonPanel.add(addShapeButton);
    bottomButtonPanel.add(removeShapeButton);
    bottomButtonPanel.add(addKeyframeButton);
    bottomButtonPanel.add(editKeyframeButton);
    bottomButtonPanel.add(removeKeyframeButton);
    bottomButtonPanel.add(saveButton);
    bottomButtonPanel.add(loadButton);
    this.add(bottomButtonPanel, BorderLayout.SOUTH);

    selectionListPanel = new JPanel();
    selectionListPanel.setBorder(BorderFactory.createTitledBorder("Shapes"));

    JScrollPane shapesScrollPane = new JScrollPane(selectionListPanel);
    this.add(shapesScrollPane, BorderLayout.EAST);

    DefaultListModel<String> shapeNames = new DefaultListModel<>();
    model.getShapes().keySet().forEach( (s) -> shapeNames.addElement(s));
    listOfStrings = new JList<>(shapeNames);
    listOfStrings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    selectionListPanel.add(listOfStrings);
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
    this.addShapeButton.addActionListener(listener);
    this.removeShapeButton.addActionListener(listener);
    this.addKeyframeButton.addActionListener(listener);
    this.editKeyframeButton.addActionListener(listener);
    this.removeKeyframeButton.addActionListener(listener);
    this.saveButton.addActionListener(listener);
    this.loadButton.addActionListener(listener);
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
    updateShapesBar();
    this.repaint();
  }

  private void updateShapesBar() {
    selectionListPanel.remove(listOfStrings);
    DefaultListModel<String> shapeNames = new DefaultListModel<>();
    model.getShapes().keySet().forEach( (s) -> shapeNames.addElement(s));
    listOfStrings = new JList<>(shapeNames);
    selectionListPanel.add(listOfStrings);
    listOfStrings.addListSelectionListener( (s) -> {
      String name = listOfStrings.getSelectedValue();
      Shape shape = model.getShapes().get(name);
      if (shape != null) {
        JOptionPane.showMessageDialog(null,
            "shape " + name + " has keyframes at times "
                + Arrays.toString(shape.getKeyframeTimes()));
      }
    });
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void setTickLabel(int tick, int tps) {
    this.tickLabel.setText("Tick: " + tick + " // Speed(TPS): " + tps);
  }

  @Override
  public boolean sameModel(AnimationModel m) {
    if (m == null) {
      throw new IllegalArgumentException("the given model is null");
    }
    return m == this.model;
  }

  @Override
  public void setModel(AnimationModel model) {
    if (model == null) {
      throw new IllegalArgumentException("the given model is null");
    }
    this.model = model;
    panel.setPreferredSize(new Dimension(model.getWindowWidth(), model.getWindowHeight()));
    pane.setPreferredSize(new Dimension(model.getWindowWidth(), model.getWindowHeight()));

  }
}
