package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.motions.info.ShapeInfo;
import cs3500.animator.model.motions.info.ShapeInfoImpl;
import cs3500.animator.model.types.Color;
import cs3500.animator.model.types.Position2D;
import cs3500.animator.model.types.ShapeSize;
import cs3500.animator.util.AnimationReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A UserPrompterViewImpl prompts the user for information and returns information that allows
 * the controller to make appropriate edits to the model.
 */
public class UserPrompterViewImpl implements UserPrompterView {

  private AnimationModel model;
  private int speed;

  /**
   * Constructs a UserPrompterViewImpl with a model and a speed.
   * @param model the animation model to be changed by the prompts
   * @param speed the speed of the animation
   * @throws IllegalArgumentException if the model or speed are invalid
   */
  public UserPrompterViewImpl(AnimationModel model, int speed) {
    if (model == null) {
      throw new IllegalArgumentException("model is null");
    }
    if (speed < 0) {
      throw new IllegalArgumentException("the speed is less than 0");
    }
    this.model = model;
    this.speed = speed;
  }

  @Override
  public Runnable addShape() {
    String shapeName = JOptionPane.showInputDialog("Enter shape name");
    if (shapeName == null) {
      JOptionPane.showMessageDialog(null, "error: must input shape name");
      return null;
    }
    String[] options = {"Rectangle", "Ellipse"};
    String shapeType = (String)JOptionPane.showInputDialog(null, "What shape type?",
        "Shape type selector", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    if (shapeType == null) {
      return null;
    }
    return () -> {
      String msg = this.model.addShape(shapeName, shapeType);
      JOptionPane.showMessageDialog(null, msg);
    };
  }

  @Override
  public Runnable removeShape() {
    String shapeName = JOptionPane.showInputDialog("Enter shape name");
    if (shapeName == null) {
      JOptionPane.showMessageDialog(null, "error: must input shape name");
    }
    return () -> {
      String message = model.deleteShape(shapeName);
      JOptionPane.showMessageDialog(null, message);
    };
  }

  @Override
  public Runnable addKeyframe() {
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
        return null;
      }
      if (num < 0) {
        JOptionPane.showMessageDialog(null, "the number must be greater than 0");
        return null;
      }
      return () -> {
        String msg = model.addKeyframe(shapeName.getText(), num);
        JOptionPane.showMessageDialog(null, msg);
      };
    }
    return null;
  }

  @Override
  public Runnable editKeyframe() {
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
        return null;
      }
      if (num < 0) {
        JOptionPane.showMessageDialog(null, "the number must be greater than 0");
        return null;
      }

      if (model.isKeyframe(shapeName.getText(), num)) {
        ShapeInfo infoAtTime = model.shapeInfoAtTime(shapeName.getText(), num);

        JTextField inputX = new JTextField(Integer.toString(infoAtTime.getPosition().getX()));
        JTextField inputY = new JTextField(Integer.toString(infoAtTime.getPosition().getY()));
        JTextField inputW = new JTextField(Integer.toString(infoAtTime.getSize().getW()));
        JTextField inputH = new JTextField(Integer.toString(infoAtTime.getSize().getH()));
        JColorChooser colorChooser = new JColorChooser();
        colorChooser.setColor(infoAtTime.getColor().getR(),
            infoAtTime.getColor().getG(), infoAtTime.getColor().getB());

        Object[] secondPrompt = {
            "x-location:", inputX, "y-location:", inputY,
            "Width:", inputW, "Height:", inputH,
            "Color:", colorChooser
        };
        int b = JOptionPane.showConfirmDialog(null, secondPrompt,
            "Edit keyframe", JOptionPane.OK_CANCEL_OPTION);

        if (b == JOptionPane.OK_OPTION) {
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
            return null;
          }
          if (outW < 0 || outH < 0) {
            JOptionPane.showMessageDialog(null, "shape size invalid");
            return null;
          }
          int outR = colorChooser.getColor().getRed();
          int outG = colorChooser.getColor().getGreen();
          int outB = colorChooser.getColor().getBlue();

          Position2D posn = new Position2D(outX, outY);
          ShapeSize size = new ShapeSize(outW, outH);
          Color color = new Color(outR, outG, outB);
          ShapeInfo newShapeInfo = new ShapeInfoImpl(posn, size, color);

          return () -> {
            String msg = model.editKeyframe(shapeName.getText(), num, newShapeInfo);
            JOptionPane.showMessageDialog(null, msg);
          };
        } else {
          JOptionPane.showMessageDialog(null,
              "there is no keyframe for shape " + shapeName.getText() + " at time " + num);
          return null;
        }
      }
    }
    return null;

  }

  @Override
  public Runnable removeKeyframe() {
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
        return null;
      }
      if (num < 0) {
        JOptionPane.showMessageDialog(null, "the number must be greater than 0");
        return null;
      }
      return () -> {
        String msg = model.deleteKeyframe(shapeName.getText(), num);
        JOptionPane.showMessageDialog(null, msg);
      };
    }
    return null;
  }

  @Override
  public void saveAnimation() {
    String fileName = JOptionPane.showInputDialog("Output file name (without extension)");
    if (fileName == null || fileName.equals("")) {
      JOptionPane.showMessageDialog(null, "error: must input valid file name");
      return;
    }
    String[] options = {"Text", "SVG"};
    String outputType = (String)JOptionPane.showInputDialog(null, "What output type?",
        "Shape type selector", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    if (outputType == null) {
      return;
    }
    try {
      switch (outputType) {
        case "Text":
          new TextView(this.model, new FileWriter(fileName + ".txt")).makeVisible();
          break;
        case "SVG":
          new SVGView(this.model, this.speed, new FileWriter(fileName + ".svg")).makeVisible();
          break;
        default:
          JOptionPane.showMessageDialog(null, "error: unexpected view type");
          break;
      }
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null,
          "error with file saving; please try again");
    }
    JOptionPane.showMessageDialog(null, "view successfully saved");
  }

  @Override
  public AnimationModel loadAnimation() {
    JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        ".txt describing animation", "txt");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(null);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      AnimationModel mdl;
      try {
        mdl = AnimationReader.parseFile(new FileReader(f),
            new AnimationModelImpl.Builder());
      } catch (FileNotFoundException | IllegalArgumentException | IllegalStateException e) {
        JOptionPane.showMessageDialog(null,
            "error with file loading; please try again");
        return null;
      }
      return mdl;
    }
    return null;
  }

  @Override
  public void setModel(AnimationModel model) {
    if (model == null) {
      throw new IllegalArgumentException("given model is null");
    }
    this.model = model;
  }


}
