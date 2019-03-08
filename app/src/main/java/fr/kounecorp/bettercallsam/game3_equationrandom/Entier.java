package fr.kounecorp.bettercallsam.game3_equationrandom;

import android.support.annotation.NonNull;

public class Entier extends ExpressionArithmetique {

	private int entier;
	
	public Entier(int e) {
		this.entier = e;
	}
	
	public int evaluate() {
		return this.entier;
	}

	@Override
	@NonNull
	public String toString() {
		if (this.entier < 0)
			return "(" + this.entier + ")";
		else
			return Integer.toString(this.entier);
	}
	
}
