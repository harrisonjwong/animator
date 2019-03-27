package cs3500.animator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * A ButtonListener is-a ActionListener that responds to buttons using a map. The map
 * contains strings corresponding to the button pressed and a Runnable to execute
 * once that button is pressed.
 */
public class ButtonListener implements ActionListener {

  /**
   * The map containing the button names and actions.
   */
  private Map<String, Runnable> buttonClickedActions;

  /**
   * Sets the button map to the given map.
   * @param map given map containing button names and actions.
   * @throws IllegalArgumentException if the map is null
   */
  public void setButtonClickedActionMap(Map<String, Runnable> map) {
    if (map == null) {
      throw new IllegalArgumentException("the given map is null");
    }
    buttonClickedActions = map;
  }

  /**
   * Runs the action if found in the map.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (buttonClickedActions.containsKey(e.getActionCommand())) {
      buttonClickedActions.get(e.getActionCommand()).run();
    }
  }
}
