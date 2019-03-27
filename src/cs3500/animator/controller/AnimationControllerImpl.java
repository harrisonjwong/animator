package cs3500.animator.controller;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.shapes.Shape;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.AnimationView.ViewType;
import cs3500.animator.view.ButtonListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Timer;

/**
 * TODO
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
   * TODO
   * @param view
   */
  public AnimationControllerImpl(AnimationView view, AnimationModel model, int speed) {
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

  @Override
  public void start() {
    view.makeVisible();
    ViewType viewType = view.getViewType();
    if (viewType == ViewType.Edit || viewType == ViewType.Visual) {
      int delay = 1000 / speed;

      this.timer = new Timer(delay, ((ActionEvent e) -> {
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

    buttonClickedMap.put("Exit Button", () -> {
      System.exit(0);
    });

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }

  private void restart() {
    timer.stop();
    tick = 0;
    timer.restart();
    this.playing = true;
  }

  private void toggleLooping() {
    loopOn = !loopOn;
  }

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

  private void playPause() {
    if (playing) {
      timer.stop();
    } else {
      timer.start();
    }
    playing = !playing;
  }




}
