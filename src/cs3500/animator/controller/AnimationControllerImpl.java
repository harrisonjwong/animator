package cs3500.animator.controller;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.motions.info.ShapeInfoImpl;
import cs3500.animator.model.shapes.Oval;
import cs3500.animator.model.shapes.Rectangle;
import cs3500.animator.model.shapes.Shape;
import cs3500.animator.model.types.Color;
import cs3500.animator.model.types.Position2D;
import cs3500.animator.model.types.ShapeSize;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.AnimationView.ViewType;
import cs3500.animator.view.ButtonListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * An AnimationControllerImpl contains an AnimationModel and AnimationView. It can control
 * the timing of a visual animation and responds to user inputs of an editing animation. It can
 * also be used to create SVG and textual views.
 */
public class AnimationControllerImpl implements AnimationController {

  private AnimationModel model;
  private AnimationView view;

  //for visual views
  private int tick;
  private Timer timer;
  private int speed;

  //specifically for edit view
  private boolean loopOn;
  private boolean playing;

  /**
   * Constructs an AnimationController given a view, model, and speed of animation.
   * @param view the {@link AnimationView} to display on
   * @param model the {@link AnimationModel} that contains the data
   * @param speed the speed of the animation for visuals
   * @throws IllegalArgumentException if the view or model are null or the speed is
   *         less than or equal 0
   * @throws IllegalStateException if the model contained in the view is different from the model
   *         passed into the controller
   */
  public AnimationControllerImpl(AnimationView view, AnimationModel model, int speed) {
    if (view == null || model == null) {
      throw new IllegalArgumentException("given view or model is null");
    }
    if (speed <= 0) {
      throw new IllegalArgumentException("speed must be greater than 0");
    }
    if (!view.sameModel(model)) {
      throw new IllegalStateException("the model in the view and the model in the controller"
          + "must be the same view");
    }
    this.view = view;
    this.model = model;
    this.speed = speed;

    this.timer = null;
    this.tick = 0;
    this.loopOn = false;
    this.playing = true;

    if (view.getViewType() == ViewType.Edit) {
      this.configureButtonListener();
    }
  }

  /**
   * Makes the view visible. If it's a visual or editing view, then the timer controlling
   * the animation is created and started here.
   */
  @Override
  public void start() {
    view.makeVisible();
    ViewType viewType = view.getViewType();
    if (viewType == ViewType.Edit || viewType == ViewType.Visual) {
      int delay = 1000 / speed;

      this.timer = new Timer(delay, ((ActionEvent e) -> {
        this.view.setTickLabel(this.tick, this.speed);
        List<ShapeInfo> infos = new ArrayList<>();
        List<String> types = new ArrayList<>();

        HashMap<String, Shape> shapes = model.getShapes();
        for (String s : shapes.keySet()) {
          ShapeInfo curinfo = model.shapeInfoAtTime(s, tick);
          String type = shapes.get(s).getType();
          infos.add(curinfo);
          types.add(type);
        }
        view.getPanel().setInfos(infos);
        view.getPanel().setTypes(types);

        view.refresh();
        if (this.tick >= model.getEndingTick()) {
          if (loopOn) {
            timer.stop();
            tick = 0;
            timer.start();
          } else {
            timer.stop();
          }
        } else {
          this.tick++;
        }

      }));
      timer.start();
    }
  }

  /**
   * Creates the (String,Runnable) map with ActionEvents for each button in the editing view.
   */
  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("PlayPause Button", () -> {
      playPause();
      view.resetFocus();
    });
    buttonClickedMap.put("Restart Button", () -> {
      restart();
      view.resetFocus();
    });
    buttonClickedMap.put("Loop Button", () -> {
      this.toggleLooping();
      view.resetFocus();
    });
    buttonClickedMap.put("IncSpeed Button", () -> {
      this.changeSpeed(true);
      view.resetFocus();
    });
    buttonClickedMap.put("DecSpeed Button", () -> {
      this.changeSpeed(false);
      view.resetFocus();
    });
    buttonClickedMap.put("Add Shape Button", () -> {
      addShape();
      view.resetFocus();
    });
    buttonClickedMap.put("Remove Shape Button", () -> {
      removeShape();
      view.resetFocus();
    });
    buttonClickedMap.put("Add Keyframe Button", () -> {
      addKeyframe();
      view.resetFocus();
    });

    buttonClickedMap.put("Edit Keyframe Button", () -> {
      editKeyframe();
      view.resetFocus();
    });

    buttonClickedMap.put("Remove Keyframe Button", () -> {
      removeKeyframe();
      view.resetFocus();
    });

    buttonClickedMap.put("Exit Button", () -> {
      System.exit(0);
    });

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }

  private void addShape() {
    String shapeName = JOptionPane.showInputDialog("Enter shape name");
    String[] options = {"Rectangle", "Ellipse"};
    String shapeType = (String)JOptionPane.showInputDialog(null, "What shape type?",
        "Shape type selector", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    switch (shapeType) {
      case "Rectangle":
        this.model.addShape(new Rectangle(shapeName));
        JOptionPane.showMessageDialog(null, "Rectangle " + shapeName + " added");
        break;
      case "Ellipse":
        this.model.addShape(new Oval(shapeName));
        JOptionPane.showMessageDialog(null, "Ellipse " + shapeName + " added");
        break;
      default:
        break;
    }
  }

  private void removeShape() {
    String shapeName = JOptionPane.showInputDialog("Enter shape name");
    String message = model.deleteShape(shapeName);
    JOptionPane.showMessageDialog(null, message);
  }

  private void addKeyframe() {
    JTextField shapeName = new JTextField();
    JTextField time = new JTextField();
    Object[] message = {
        "Shape Name:", shapeName,
        "Time:", time,
    };
    int option = JOptionPane.showConfirmDialog(null, message,
        "Add keyframe", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      int num;
      try {
        num = Integer.parseInt(time.getText());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "the number is invalid");
        return;
      }
      if (num < 0) {
        JOptionPane.showMessageDialog(null, "the number must be greater than 0");
      }
      String msg = model.addKeyframe(shapeName.getText(), num);
      JOptionPane.showMessageDialog(null, msg);
    }
  }

  private void editKeyframe() {
    JTextField shapeName = new JTextField();
    JTextField time = new JTextField();
    Object[] message = {
        "Shape Name:", shapeName,
        "Time:", time,
    };
    int option = JOptionPane.showConfirmDialog(null, message,
        "Edit keyframe", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      int num;
      try {
        num = Integer.parseInt(time.getText());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "the number is invalid");
        return;
      }
      if (num < 0) {
        JOptionPane.showMessageDialog(null, "the number must be greater than 0");
      }

      if (model.isKeyframe(shapeName.getText(), num)) {
        ShapeInfo infoAtTime = model.shapeInfoAtTime(shapeName.getText(), num);

        JTextField inputX = new JTextField(Integer.toString(infoAtTime.getPosition().getX()));
        JTextField inputY = new JTextField(Integer.toString(infoAtTime.getPosition().getY()));
        JTextField inputW = new JTextField(Integer.toString(infoAtTime.getSize().getW()));
        JTextField inputH = new JTextField(Integer.toString(infoAtTime.getSize().getH()));
        JColorChooser colorChooser = new JColorChooser();

        Object[] secondPrompt = {
            "x-location:", inputX, "y-location:", inputY,
            "Width:", inputW, "Height:", inputH,
            "Color:", colorChooser
        };
        int b = JOptionPane.showConfirmDialog(null, secondPrompt,
            "Edit keyframe", JOptionPane.OK_CANCEL_OPTION);

        int outX;
        int outY;
        int outW;
        int outH;
        try {
          outX = Integer.parseInt(inputX.getText());
          outY = Integer.parseInt(inputY.getText());
          outW = Integer.parseInt(inputW.getText());
          outH = Integer.parseInt(inputH.getText());
        } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "a number is invalid");
          return;
        }
        if (outW < 0 || outH < 0) {
          JOptionPane.showMessageDialog(null, "shape size invalid");
          return;
        }
        int outR = colorChooser.getColor().getRed();
        int outG = colorChooser.getColor().getGreen();
        int outB = colorChooser.getColor().getBlue();

        Position2D posn = new Position2D(outX, outY);
        ShapeSize size = new ShapeSize(outW, outH);
        Color color = new Color(outR, outG, outB);
        ShapeInfo newShapeInfo = new ShapeInfoImpl(posn, size, color);

        String msg = model.editKeyframe(shapeName.getText(), num, newShapeInfo);
        JOptionPane.showMessageDialog(null, msg);
      } else {
        JOptionPane.showMessageDialog(null, "there is no keyframe at this time");
      }
    }

  }

  private void removeKeyframe() {
    JTextField shapeName = new JTextField();
    JTextField time = new JTextField();
    Object[] message = {
        "Shape Name:", shapeName,
        "Time:", time,
    };
    int option = JOptionPane.showConfirmDialog(null, message,
        "Remove keyframe", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      int num;
      try {
        num = Integer.parseInt(time.getText());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "the number is invalid");
        return;
      }
      if (num < 0) {
        JOptionPane.showMessageDialog(null, "the number must be greater than 0");
      }
      String msg = model.deleteKeyframe(shapeName.getText(), num);
      JOptionPane.showMessageDialog(null, msg);
    }
  }

  /**
   * Restarts the animation in the editing view.
   */
  private void restart() {
    timer.stop();
    tick = 0;
    timer.restart();
    this.playing = true;
  }

  /**
   * Turns looping in the editing view on or off.
   */
  private void toggleLooping() {
    loopOn = !loopOn;
  }

  /**
   * Changes the speed in the editing view based on the given boolean.
   * @param increase boolean representing whether to increase (true) or decrease (false) speed
   */
  private void changeSpeed(boolean increase) {
    if (increase) {
      this.speed += 2;
    } else {
      this.speed -= 2;
    }
    if (this.speed <= 0) {
      this.speed = 1;
    }
    int delay = 1000 / this.speed;
    this.timer.setDelay(delay);
  }

  /**
   * Plays or pauses the animation in the editing view.
   */
  private void playPause() {
    if (playing) {
      timer.stop();
    } else {
      timer.start();
    }
    playing = !playing;
  }




}
