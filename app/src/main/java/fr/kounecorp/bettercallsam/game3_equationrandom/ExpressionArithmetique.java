package fr.kounecorp.bettercallsam.game3_equationrandom;

public abstract class ExpressionArithmetique {

	private int nbEntiers;
	private ExpressionArithmetique[] expressions;

	public ExpressionArithmetique() {
		nbEntiers = 0;
		this.expressions = new ExpressionArithmetique[10];
	}

	public ExpressionArithmetique[] getExps() {
		return this.expressions;
	}

	public int getNb() {
		return nbEntiers;
	}

	public void add(ExpressionArithmetique e) {
		this.expressions[nbEntiers] = e;
		nbEntiers++;
	}

	public void add(ExpressionArithmetique[] tabE) {
		for (ExpressionArithmetique e : tabE) {
			this.add(e);
		}
	}

	public abstract int evaluate();

	private String traitementToString(String operand, String space) {
		String ret = "";
		for (int i = 0; i < this.getNb(); i++) {
			ret += this.expressions[i].toString();
			if (i + 1 < this.getNb()) {
				ret += space + operand + space;
			}
		}
		return ret;
	}

	public String toString(String operand) {
		String ret;
		if (operand.equals("*") || operand.equals("/")) {
			ret = traitementToString(operand, "");
		} else {
			ret = traitementToString(operand, " ");
		}
		return ret;
	}

}
