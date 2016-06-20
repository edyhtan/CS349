import java.awt.*;
import java.lang.*;
import java.util.*;
import java.io.*;

class DrawModel {

    IView views[]; // list of other views, currently none
    ArrayDeque<Xshape> loShape;

    //other important attributes:
    int drawTool = 0;
    int color = 0;
    int lineThick = 1;

    // create Focus
    int focusX = 0;
    int focusY = 0;

    //IViews
    IView Draw;

    //selcted shape
    Xshape selected;

    public DrawModel(){
        loShape = new ArrayDeque<Xshape>();
    }

    public void setDrawCanvas(IView draw){
        Draw = draw;
    }

    public void setDrawTool(int tool){
        drawTool = tool;
    }

    public void setColor(int c){
        color = c;
    }

    public void setStroke(int t){
        lineThick = t;
    }

    public int getTool(){
        return drawTool;
    }

    public void createShape(int x, int y, double scale){
        Xshape newshape;

        if (drawTool == 2)
            newshape = new Xline(color, lineThick, (int)(x/scale), (int)(y/scale));
        else if (drawTool == 3)
            newshape = new Xrectangle(color, lineThick, (int)(x/scale), (int)(y/scale));
        else if (drawTool == 4)
            newshape = new Xcircle(color, lineThick, (int)(x/scale),(int)(y/scale));
        else
            return ;

        selected = newshape;
        loShape.addFirst(selected);
        Draw.notifyView();
    }

    public void setCurrentSize(double x, double y, double scale){
        selected.changeXY2(x/scale, y/scale);
        Draw.notifyView();
    }

    public void lightUnleash(){

        if (selected != null)
            selected.confirm();

        focusX = 0;
        focusY = 0;

        Draw.notifyView();
    }

    public void unleash(){
        selected.confirm();
        selected = null;
        Draw.notifyView();
    }

    public void selectShape(double x, double y, double scale){

        if (loShape.isEmpty())
            return;

        boolean changed = false;

        for (Xshape s : loShape){
            s.printCoord();
            if (s.contains(x,y,scale)){
                selected = s;
                focusX = (int) x;
                focusY = (int) y;
                changed = true;
                break;
            }
        }

        if (!changed) {
            System.out.println("Failed");
            selected = null;
        }

        Draw.notifyView();
    }

    public boolean onSelected(double x, double y, double scale){

        return selected == null ? false : selected.contains(x, y, scale);
    }

    public Xshape getSelected(){
        return selected;
    }

    public ArrayDeque<Xshape> getAllShape(){
        return new ArrayDeque<Xshape>(loShape);
    }

    public void removeShape(double x, double y, double scale){
        for (Xshape s : loShape){
            if (s.contains(x,y, scale)){
                loShape.remove(s);
                Draw.notifyView();
                return;
            }
        }

        Draw.notifyView();
    }

    public void moveShape(int x, int y, double scale){
        selected.changeXY1(x - focusX, y - focusY);
        selected.confirm();
        focusX = x;
        focusY = y;
        Draw.notifyView();
    }

    public void setFilled(double x, double y, double scale){
        for (Xshape s : loShape){
            if (s.contains(x,y, scale)){
                s.setFill(color);
                Draw.notifyView();
                return;
            }
        }

        Draw.notifyView();
    }

    public void clear(){
        loShape = new ArrayDeque<Xshape>();
        Draw.notifyView();
    }

    public void saveFile(File file) {

    }

    public void loadFile(File file) {
        if (file != null) {

            try {
                ArrayDeque<Xshape> loNew = new ArrayDeque<Xshape>();
                Scanner scan = new Scanner(file);

                while (scan.hasNextInt()) {
                    int type = scan.nextInt();
                    int x1 = scan.nextInt();
                    int y1 = scan.nextInt();
                    int x2 = scan.nextInt();
                    int y2 = scan.nextInt();
                    int color = scan.nextInt();
                    int fill = scan.nextInt();
                    int thick = scan.nextInt();

                    if (type == 0){
                        loNew.addFirst( new Xline(color, thick, x1, y1, x2, y2) );
                    }else if (type == 1){
                        loNew.addFirst( new Xrectangle(color, thick, x1, y1, x2, y2, fill));
                    }else if (type == 3){
                        loNew.addFirst( new Xcircle(color, thick, x1, y1, x2, y2, fill));
                    }
                }
                clear();
                loShape = loNew;
            }catch (FileNotFoundException e) {}
        }
    }
}