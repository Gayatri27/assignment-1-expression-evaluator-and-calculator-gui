import java.util.*;
import operators.*;
import operands.*;

public class Evaluator {
  private Stack<Operand> operandStack;
  private Stack<Operator> operatorStack;

  private StringTokenizer tokenizer;
  private static final String DELIMITERS = "+-*^/#!() ";

  public Evaluator() {
    operandStack = new Stack<>();
    operatorStack = new Stack<>();
    // initialize operator stack
    initializeOperators();
  }

  public int eval(String expression) {
    String token;

    // The 3rd argument is true to indicate that the delimiters should be used
    // as tokens, too. But, we'll need to remember to filter out spaces.
    this.tokenizer = new StringTokenizer(expression, DELIMITERS, true);

    // Add InitOperator in case the operatorStack is isEmpty.
    // The InitOperator will not be used for any calculations.

    if(operatorStack.isEmpty()) {
      operatorStack.push(new InitOperator());
    }

    while (this.tokenizer.hasMoreTokens()) {
      // filter out spaces
      if (!(token = this.tokenizer.nextToken()).equals(" ")) {
        // check if token is an operand
        if (Operand.check(token)) {
          operandStack.push(new Operand(token));
        } else {
          if (!Operator.check(token)) {
            System.out.println("*****invalid token******");
            System.exit(1);
          }

          Operator newOperator = Operator.operators.get(token);

          if(newOperator.equals(Operator.operators.get("("))) {
            operatorStack.push(newOperator);
          } else if(newOperator.equals(Operator.operators.get(")"))) {
            while(!operatorStack.peek().equals(Operator.operators.get("("))) {
              computeOperands();
            }
            operatorStack.pop();
          } else {
            while (operatorStack.peek().priority() >= newOperator.priority()) {
              computeOperands();
            }
            operatorStack.push(newOperator);
          }
        }
      }
    }

    // Control gets here when we've picked up all of the tokens.

    while(operatorStack.size() > 1) {
      computeOperands();
    }

    int value = operandStack.pop().getValue();
    return value;
  }

  private void computeOperands() {
    Operator oldOperator = operatorStack.pop();
    Operand operand2 = operandStack.pop();
    Operand operand1 = operandStack.pop();
    operandStack.push(oldOperator.execute(operand1, operand2));
  }

  private void initializeOperators() {
    Operator.operators = new HashMap<String, Operator>();
    Operator.operators.put("+", new AdditionOperator());
    Operator.operators.put("-", new SubtractionOperator());
    Operator.operators.put("*", new MultiplicationOperator());
    Operator.operators.put("/", new DivisionOperator());
    Operator.operators.put("^", new PowerOperator());
    Operator.operators.put("(", new RightParenthesesOperator());
    Operator.operators.put(")", new LeftParenthesesOperator());
  }
}
