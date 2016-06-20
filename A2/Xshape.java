import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.io.*;
import java.util.*;

abstract class Xshape {
    int color;
    boolean filled;
    Shape shape;
    int thick;

    abstract boolean contains(double x, double y, double scale);
    abstract void draw(Graphics2D g, double scale);
    abstract void changeXY1(double x, double y);
    abstract void changeXY2(double x, double y);
    abstract void confirm();
    abstract void printCoord();
    abstract void setFill(int c);
    abstract void saveFile(PrintWriter f);

}
