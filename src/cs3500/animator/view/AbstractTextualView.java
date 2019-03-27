package cs3500.animator.view;

import java.awt.event.ActionListener;

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
  public boolean isTimeable() {
    return false;
  }

  @Override
  public AnimationPanel getPanel() {
    throw new UnsupportedOperationException("can't get panel from textual views");
  }
}
