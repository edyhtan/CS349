package yh2tan.portablesketch;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by ED on 2016/07/07.
 */
public class Xline implements Xshape {

    // basic attributes
    private int color;
    private int fill;
    private int thickness;

    // geometric attributes
    int x1;
    int y1;
    int x2;
    int y2;


    public Xline(int x, int y, int c, int t) {
        x1 = x;
        x2 = x;
        y1 = y;
        y2 = y;

        color = c;
        thickness = t;

        fill = -1;
    }

    public void fill(int color) {
        fill = color;
    }

    public void setColor(int color) {
        this.color = color;
    }
    public void setThick(int thick) {
        thickness = thick;
    }
    public int getColor() {
        return color;
    }
    public int getThick() {
        return thickness;
    }

    // geometric attribute of the shape
    public void changeXY1(int dx, int dy) {
        x1 += dx;
        y1 += dy;
        x2 += dx;
        y2 += dy;
    }
    public void changeXY2(int dx, int dy) {
        x2 += dx;
        y2 += dy;
    }
    public boolean contains(double x, double y) {

        int topX = Math.min(x1, x2);
        int topY = Math.min(y1, y2);
        int botX = Math.max(x1, x2);
        int botY = Math.max(y1, y2);

        if ( x > botX|| x < topX || y > botY || y < topY)
            return false;

        // compute the linear equation

        double coeff = (x2 - x1 == 0 ? 0 : (y2 - y1)/ (x2 - x1));
        double constant = y1 - coeff * x1;

        // give few pixel of freedom
        return ((int) Math.abs(y - coeff * x - constant)) < Math.abs(thickness + 2*coeff);
    }

    // Control function
    public void confirm() {}
    public void drawShape(Canvas c) {}
    public void drawSelectedBorder(Canvas c) {
        Paint paint;
    }
}