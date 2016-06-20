import java.awt.*;
import javax.swing.*;
import java.lang.*;

class Xrectangle extends Xshape {
    int color;
    boolean filled = false;
    Shape shape;
    int thick;

    int fill = -1;

    // internal data
    double x;
    double y;
    double dx;
    double dy;


    // create Xrectangle, unscaled
    public Xrectangle (int c, int t, int x, int y){
        shape = new Rectangle(new Point(x,y), new Dimension(0, 0));
        color = c;
        thick = t;

        this.x = x;
        this.y = y;
        dx = 0;
        dy = 0;
    }

    public Xrectangle (int c, int t, int x, int y, int w, int h, int f){
        shape = new Rectangle(new Point(x,y), new Dimension(w, h));
        color = c;
        thick = t;

        this.x = x;
        this.y = y;
        dx = w;
        dy = h;

        filled  = (f >= 0);
        fill = f;
    }

    public void setFill(int c){
        filled = true;
        fill = c;
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
        dx = x - this.x;
        dy = y - this.y;
    }

    // reconstruct shape
    public void confirm(){
        x= (x < (x + dx) ? x : x + dx);
        y= (y < (y + dy) ? y : y + dy);
        dx = Math.abs(dx);
        dy = Math.abs(dy);
        shape = new Rectangle(new Point((int)x,(int)y), new Dimension((int)dx, (int)dy));
    }

    public void draw(Graphics2D g2, double scale){

        int xTop = (int) (x*scale < (x*scale + dx*scale) ? x*scale : x*scale + dx*scale);
        int yTop = (int) (y*scale < (y*scale + dy*scale) ? y*scale : y*scale + dy*scale);

        g2.setStroke(new BasicStroke(thick));

        if (filled) {
            g2.setColor(ColorPallet.colors[fill]);
            g2.fillRect((int) xTop, (int) yTop , (int) Math.abs(dx * scale), (int) (Math.abs(dy * scale)));
        }

        g2.setColor(ColorPallet.colors[color]);
        g2.drawRect((int)xTop,(int)yTop,(int)Math.abs(dx*scale),(int)Math.abs(dy*scale));
    }

    public void printCoord(){
        System.out.printf("%f, %f, %f, %f\n", x, y, x+dx, y+dy );
    }
}