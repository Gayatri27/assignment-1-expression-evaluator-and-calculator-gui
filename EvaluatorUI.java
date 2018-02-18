import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvaluatorUI extends JFrame implements ActionListener {
  private TextField textField = new TextField();
  private Panel buttonPanel = new Panel();

  private Evaluator evaluator;

  // total of 20 buttons on the calculator,
  // numbered from left to right, top to bottom
  // buttonText[] array contains the text for corresponding buttons
  private static final String[] buttonText = {
    "7", "8", "9", "+", "4", "5", "6", "- ", "1", "2", "3",
    "*", "0", "^", "=", "/", "(", ")", "C", "CE"
  };
  private Button[] buttons = new Button[buttonText.length];

  public static void main(String argv[]) {
    EvaluatorUI calculator = new EvaluatorUI();
  }

  public EvaluatorUI() {
    setLayout(new BorderLayout());

    add(textField, BorderLayout.NORTH);
    textField.setEditable( false );

    add(buttonPanel, BorderLayout.CENTER);
    buttonPanel.setLayout(new GridLayout(5, 4));

    //create 20 buttons with corresponding text in buttonText[] array
    for (int i = 0; i < 20; i++) {
      buttons[i] = new Button(buttonText[i]);
    }

    //add buttons to button panel
    for (int i=0; i<20; i++) {
      buttonPanel.add(buttons[i]);
    }

    //set up buttons to listen for mouse input
    for (int i = 0; i < 20; i++) {
      buttons[i].addActionListener(this);
    }

    setTitle("Calculator");
    setSize(400, 400);
    setLocationByPlatform(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setVisible(true);

    evaluator = new Evaluator();
  }

  public void actionPerformed(ActionEvent event) {
    String currentDisplayText = textField.getText();

    if(event.getSource().equals(buttons[14])) {
      int solution = evaluator.eval(currentDisplayText);
      textField.setText(Integer.toString(solution));
    } else if(event.getSource().equals(buttons[18])) {
      textField.setText("");
      evaluator = new Evaluator();
    } else if(event.getSource().equals(buttons[19])) {
      if(currentDisplayText != null && currentDisplayText.length() > 0) {
        textField.setText("");
      } else {
        evaluator = new Evaluator();
      }
    } else {
      Button button = (Button) event.getSource();
      textField.setText(textField.getText() + button.getLabel());
    }
  }
}
