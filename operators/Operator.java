package operators;

import java.util.HashMap;
import operands.*;

public abstract class Operator {

  public static HashMap<String, Operator> operators;

  public abstract int priority();
  public abstract Operand execute(Operand operand1, Operand operand2);

  public static boolean check(String token) {
    return operators.containsKey(token);
  }
}
