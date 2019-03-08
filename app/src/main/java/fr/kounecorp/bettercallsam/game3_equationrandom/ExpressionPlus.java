package fr.kounecorp.bettercallsam.game3_equationrandom;

import android.support.annotation.NonNull;

public class ExpressionPlus extends ExpressionArithmetique {
	
	public ExpressionPlus() {
		super();
	}

	@Override
	public int evaluate() {
		int som = 0;
		ExpressionArithmetique[] expressions = this.getExps();
		for (int i = 0; i < this.getNb(); i++) {
			som += expressions[i].evaluate();
		}
		return som;
	}

	@Override
	@NonNull
	public String toString() {
		return super.toString("+");
	}
	
}
