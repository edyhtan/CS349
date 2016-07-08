package yh2tan.portablesketch;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by ED on 2016/07/07.
 */
public class Xcircle implements Xshape{
    // basic attributes
    private int color;
    private int fill;
    private int thickness;

    // geometric attributes
    int x1;
    int y1;
    int x2;
    int y2;

    // additional attributes for geometric calculation
    double centerX;
    double centerY;
    double radius;

    public Xcircle(int x, int y, int c, int t) {
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
        this.thickness = thick;
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
        x2 += dx;
        y1 += dy;
        y2 += dy;
        centerX += dx;
        centerY += dy;
    }
    public void changeXY2(int dx, int dy) {
        // to maintain the circle, we only add on dx to keep its radius
        x2 += dx;
        y2 += dx;

        centerX = (x1+x2)/2.0;
        centerY = (y1+y2)/2.0;
        radius += dx/2;
    }

    public boolean contains(double x, double y) {

        double xdist = x - centerX;
        double ydist = y - centerY;

        return xdist*xdist + ydist*ydist < radius*radius;
    }

    // Control function
    public void confirm() {}
    public void drawShape(Canvas c) {}
    public void drawSelectedBorder(Canvas c) {
        Paint paint;
    }

}
