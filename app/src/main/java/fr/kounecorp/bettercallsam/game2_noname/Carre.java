package fr.kounecorp.bettercallsam.game2_noname;

import android.graphics.Canvas;

public class Carre extends Forme {

    public Carre(float x, float y, float c) {
        super();
        super.setForme(x, y,x+c,y+c);
    }

    @Override
    public void display(Canvas canvas) {
        if (isTouched()) {
            if (isChoosen())
                canvas.drawRect(getForme(), this.fillGreen());
            else
                canvas.drawRect(getForme(), this.fillRed());
        } else {
            canvas.drawRect(getForme(), this.noFill());
        }
    }

}
