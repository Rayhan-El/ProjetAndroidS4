package fr.kounecorp.bettercallsam.game2_noname;

import android.graphics.Canvas;

public class Cercle extends Forme {

    private float rayon;

    public Cercle(float x, float y, float diametre) {
        super();
        this.rayon = diametre/2;
        super.setForme(x, y,x+diametre,y+diametre);
    }

    @Override
    public boolean contains(float x, float y) {
        double m1 = Math.pow(y - this.getForme().centerY(), 2);
        double m2 = Math.pow(x - this.getForme().centerX(), 2);
        double dist = Math.sqrt(m1 + m2);
        return dist <= this.rayon;
    }

    @Override
    public void display(Canvas canvas) {
        if (isTouched()) {
            if (isChoosen())
                canvas.drawOval(getForme(), this.fillGreen());
            else
                canvas.drawOval(getForme(), this.fillRed());
        } else {
            canvas.drawOval(getForme(), this.noFill());
        }
    }
}
