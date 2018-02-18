package operators;

import operands.*;

public class PowerOperator extends Operator {

  @Override
  public int priority() {
    return 4;
  }

  @Override
  public Operand execute(Operand op1, Operand op2) {
    return new Operand((int) Math.pow(op1.getValue(), op2.getValue()));
  }
}