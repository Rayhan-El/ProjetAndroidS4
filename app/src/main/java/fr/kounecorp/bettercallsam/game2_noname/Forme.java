package fr.kounecorp.bettercallsam.game2_noname;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public abstract class Forme {

    private Paint fillPaint;
    private Paint strokePaint;
    private RectF forme;
    private boolean touched;
    private boolean choosen;

    public Forme() {
        this.touched = false;
        this.choosen = false;
        // fill
        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setAntiAlias(true);

        // stroke
        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setAntiAlias(true);
        strokePaint.setStrokeWidth(4f);
    }

    public void setForme(float left, float top, float right, float bottom) {
        this.forme = new RectF(left, top, right, bottom);
    }

    public RectF getForme() {
        return this.forme;
    }

    public boolean isTouched() {
        return this.touched;
    }

    public boolean isChoosen() {
        return this.choosen;
    }

    public void setChoosen(boolean choosen) {
        this.choosen = choosen;
    }

    public boolean contains(float x, float y) {
        return forme.contains(x,y);
    }

    public boolean intersects(Forme f) {
        return RectF.intersects(this.forme,f.forme);
    }

    public Paint fillGreen() {
        fillPaint.setColor(Color.GREEN);
        return fillPaint;
    }

    public Paint fillRed() {
        fillPaint.setColor(Color.RED);
        return fillPaint;
    }

    public Paint noFill() {
        return strokePaint;
    }

    public void testClick(float fingerX, float fingerY) {
        if (contains(fingerX,fingerY)) {
            touched = true;
        }
    }

    public abstract void display(Canvas canvas);

}
