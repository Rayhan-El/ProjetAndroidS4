package fr.kounecorp.bettercallsam.game3_equationrandom;

import android.support.annotation.NonNull;

public class ExpressionDiv extends ExpressionArithmetique {

	public ExpressionDiv() {
		super();
	}

	@Override
	public int evaluate() {
		int som = 0;
		ExpressionArithmetique[] expressions = this.getExps();
		if (this.getNb() > 1) {
			som = expressions[0].evaluate();
			for (int i = 1; i < this.getNb(); i++) {
				som /= expressions[i].evaluate();
			}
		} else if (this.getNb() == 1){
			som += expressions[0].evaluate();
		}
		return som;
	}

	@Override
	@NonNull
	public String toString() {
		return super.toString("/");
	}

}
