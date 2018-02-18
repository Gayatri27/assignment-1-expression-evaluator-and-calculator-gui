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
    initializeOperators();
  }

  public int eval(String expression) {
    String token;

    // The 3rd argument is true to indicate that the delimiters should be used
    // as tokens, too. But, we'll need to remember to filter out spaces.
    this.tokenizer = new StringTokenizer(expression, DELIMITERS, true);

    // initialize operator stack - necessary with operator priority schema
    // the priority of any operator in the operator stack other than
    // the usual mathematical operators - "+-*/" - should be less than the priority
    // of the usual operators

    if(operatorStack.isEmpty()) {
      operatorStack.push(new InitOperator());
    }

    // When is it a good time to add the "!" operator?

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
              // note that when we eval the expression 1 - 2 we will
              // push the 1 then the 2 and then do the subtraction operation
              // This means that the first number to be popped is the
              // second operand, not the first operand - see the following code
              computeOperands();
            }
            operatorStack.push(newOperator);
          }
        }
      }
    }

    // Control gets here when we've picked up all of the tokens; you must add
    // code to complete the evaluation - consider how the code given here
    // will evaluate the expression 1+2*3
    // When we have no more tokens to scan, the operand stack will contain 1 2
    // and the operator stack will have + * with 2 and * on the top;
    // In order to complete the evaluation we must empty the stacks (except
    // the init operator on the operator stack); that is, we should keep
    // evaluating the operator stack until it only contains the init operator;
    // Suggestion: create a method that takes an operator as argument and
    // then executes the while loop; also, move the stacks out of the main
    // method

    while(operatorStack.size() > 1) {
      computeOperands();
    }

    int value = operandStack.pop().getValue();
    return value;
  }

  private void computeOperands() {
    Operator oldOpr = operatorStack.pop();
    Operand op2 = operandStack.pop();
    Operand op1 = operandStack.pop();
    operandStack.push(oldOpr.execute(op1, op2));
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
