package operators;

import operands.*;

public class InitOperator extends Operator {

  @Override
  public int priority() {
    return -1;
  }

  @Override
  public Operand execute(Operand op1, Operand op2) {
    return null;
  }
}
