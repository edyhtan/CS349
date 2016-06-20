import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.awt.geom.Ellipse2D;

class Xcircle extends Xshape {
    int color;
    boolean filled = false;
    Shape shape;
    int thick;

    int fill = -1;

    // internal data
    double x;
    double y;
    double deltaX;
    double deltaY;

    // create Xrectangle, unscaled
    public Xcircle (int c, int t, int x, int y){
        shape = new Ellipse2D.Double(x, y, 0, 0);
        color= c;
        thick = t;

        this.x = x;
        this.y = y;
        deltaX = 0;
        deltaY = 0;
    }

    public Xcircle (int c, int t, int x, int y, int w, int h, int f){
        shape = new Ellipse2D.Double(x, y, w , h);
        color = c;
        thick = t;

        this.x = x;
        this.y = y;
        deltaX = w;
        deltaY = h;

        filled = (f >= 0);
        fill = f;
    }

    public boolean contains(double x, double y, double scale){
        return shape.contains(x/scale,y/scale);
    }

    // change origin
    public void changeXY1(double x, double y){
        this.x += x;
        this.y += y;
    }

    // change delta
    public void changeXY2(double x, double y){
        this.deltaX = (x - this.x);
        this.deltaY = (y- this.y); // get the sign
        this.deltaY = (Math.abs(deltaY)/deltaY) * Math.abs(deltaX);
    }

    // reconstruct shape
    public void confirm(){
        int xTop = (int) (x < (x + deltaX) ? x : x + deltaX);
        int yTop = (int) (y < (y + deltaY) ? y : y + deltaY);
        shape = new Ellipse2D.Double((int)xTop, (int)yTop, (int) Math.abs(deltaX), (int) Math.abs(deltaY));
    }

    public void setFill(int c){
        filled = true;
        fill = c;
    }

    public void draw(Graphics2D g2, double scale){

        int xTop = (int) (x*scale < (x*scale + deltaX*scale) ? x*scale : x*scale + deltaX*scale);
        int yTop = (int) (y*scale < (y*scale + deltaY*scale) ? y*scale : y*scale + deltaY*scale);

        g2.setStroke(new BasicStroke(thick));

        if (filled) {
            g2.setColor(ColorPallet.colors[fill]);
            g2.fillOval((int) xTop, (int) yTop, (int) (Math.abs(deltaX * scale)), (int) (Math.abs(deltaY * scale)));
        }

        g2.setColor(ColorPallet.colors[color]);
        g2.drawOval((int)xTop,(int)yTop,(int)(Math.abs(deltaX*scale)), (int)(Math.abs(deltaY*scale)));

    }

    public void printCoord(){}
}
