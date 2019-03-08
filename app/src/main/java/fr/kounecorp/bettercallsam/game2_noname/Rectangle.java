package fr.kounecorp.bettercallsam.game2_noname;

import android.graphics.Canvas;

public class Rectangle extends Forme {

    public Rectangle(float x, float y, float longueur, float largeur) {
        super();
        super.setForme(x, y,x+longueur,y+largeur);
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
