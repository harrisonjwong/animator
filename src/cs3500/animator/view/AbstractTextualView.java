package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import java.awt.event.ActionListener;

/**
 * Abstract class for the views that output text. Used to throw common
 * UnsupportedOperationExceptions for all of the methods on AnimationView that do not work with
 * textual outputting views.
 */
abstract class AbstractTextualView implements AnimationView {

  @Override
  public void refresh() {
    throw new UnsupportedOperationException("can't refresh textual views");
  }

  @Override
  public void resetFocus() {
    throw new UnsupportedOperationException("can't reset focus on textual views");
  }

  @Override
  public void addActionListener(ActionListener listener) {
    throw new UnsupportedOperationException("can't add action listeners to textual views");
  }

  @Override
  public AnimationPanel getPanel() {
    throw new UnsupportedOperationException("can't get panel from textual views");
  }

  @Override
  public void setTickLabel(int speed, int tps) {
    throw new UnsupportedOperationException("can't set label for textual views");
  }

  @Override
  public void setModel(AnimationModel model) {
    throw new UnsupportedOperationException("can't set model for textual views");
  }
}
