package cs3500.animator.controller;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.shapes.Shape;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.AnimationView.ViewType;
import cs3500.animator.view.ButtonListener;
import cs3500.animator.view.UserPrompterView;
import cs3500.animator.view.UserPrompterViewImpl;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Timer;

/**
 * An AnimationControllerImpl contains an AnimationModel and AnimationView. It can control
 * the timing of a visual animation and responds to user inputs of an editing animation. It can
 * also be used to create SVG and textual views.
 */
public class AnimationControllerImpl implements AnimationController {

  private AnimationModel model;
  private AnimationView view;
  private UserPrompterView prompter;

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
      throw new IllegalArgumentException("given view and/or model is null");
    }
    if (speed <= 0) {
      throw new IllegalArgumentException("speed must be greater than 0");
    }
    if (!view.sameModel(model)) {
      throw new IllegalStateException("the model in the view and the model in the controller"
          + " must be the same view");
    }
    this.view = view;
    this.model = model;
    this.speed = speed;

    this.timer = null;
    this.tick = 0;
    this.loopOn = false;
    this.playing = true;
    this.prompter = new UserPrompterViewImpl(model, speed);

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
            if (viewType == ViewType.Visual) {
              System.exit(0);
            }
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
    buttonClickedMap.put("Save Button", () -> {
      saveAnimation();
      view.resetFocus();
    });
    buttonClickedMap.put("Load Button", () -> {
      loadAnimation();
      view.resetFocus();
    });

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }

  /**
   * Adds a shape based on user input.
   */
  private void addShape() {
    Runnable run = prompter.addShape();
    if (run != null) {
      run.run();
    }
  }

  /**
   * Removes a shape based on user input.
   */
  private void removeShape() {
    Runnable run = prompter.removeShape();
    if (run != null) {
      run.run();
    }
  }

  /**
   * Adds a keyframe based on user input.
   */
  private void addKeyframe() {
    Runnable run = prompter.addKeyframe();
    if (run != null) {
      run.run();
    }
  }

  /**
   * Edits a keyframe based on user input.
   */
  private void editKeyframe() {
    Runnable run = prompter.editKeyframe();
    if (run != null) {
      run.run();
    }
  }

  /**
   * Removes a keyframe based on user input.
   */
  private void removeKeyframe() {
    Runnable run = prompter.removeKeyframe();
    if (run != null) {
      run.run();
    }
  }

  /**
   * Saves the current animation based on user input.
   */
  private void saveAnimation() {
    prompter.saveAnimation();
  }

  /**
   * Loads an animation based on user input.
   */
  private void loadAnimation() {
    AnimationModel model = prompter.loadAnimation();
    if (model != null) {
      this.model = model;
      this.view.setModel(model);
      this.prompter.setModel(model);
      this.restart();
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
