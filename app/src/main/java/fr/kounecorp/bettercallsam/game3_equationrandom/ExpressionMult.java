package fr.kounecorp.bettercallsam.game3_equationrandom;

import android.support.annotation.NonNull;

public class ExpressionMult extends ExpressionArithmetique {

	public ExpressionMult() {
		super();
	}

	@Override
	public int evaluate() {
		int som = 1;
		ExpressionArithmetique[] expressions = this.getExps();
		for (int i = 0; i < this.getNb(); i++) {
			som *= expressions[i].evaluate();
		}
		return som;
	}

	@Override
	@NonNull
	public String toString() {
		return super.toString("*");
	}

}
