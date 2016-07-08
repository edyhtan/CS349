package yh2tan.portablesketch;

import java.util.ArrayDeque;
import java.util.Stack;

/**
 * Created by ED on 2016/07/07.
 */
public class DrawModel {

    // required attributes
    int color = 0;
    int thickness = 0;
    int tool = 0;

    // focus( to keep track of the first tap when user triggers event )
    int focusX = 0;
    int focusY = 0;

    // shapes and actions
    ArrayDeque<Xshape> loShape;
    Xshape selected;

    // undo/redo operation
    ArrayDeque<ArrayDeque<Xshape>> undoStack;
    ArrayDeque<ArrayDeque<Xshape>> redoStack;

    // main activity
    IView android;

    // contructor
    public DrawModel(IView a) {
        loShape = new ArrayDeque<Xshape>();
        undoStack = new ArrayDeque<ArrayDeque<Xshape>>();
        redoStack = new ArrayDeque<ArrayDeque<Xshape>>();
        android = a;
    }

    // set attribute functions
    public void setColor(int c) {
        color = c;
        if (selected != null) selected.setColor(color);
        android.notifyView();
    }

    public void setThickness(int t) {
        thickness = t;
        if (selected != null) selected.setThick(thickness);
        android.notifyView();
    }

    public void setTool(int t) {
        tool = t;
        selected = null;
        android.notifyView();
    }

    public void createShape(int x, int y) {
        Xshape newshape;

        if (tool == 2)
            newshape = new Xline(x, y, color, thickness);
        else if (tool == 3)
            newshape = new Xrect(x, y, color, thickness);
        else if (tool == 4)
            newshape = new Xcircle(x, y, color, thickness);
        else
            return;

        selected = newshape;
        loShape.addFirst(selected);
        android.notifyView();
    }

    // change the current selected shape only on lower coordinates
    public void resizeSelected(int x, int y) {
        selected.changeXY2(x, y);
        android.notifyView();
    }

    // light unleash resets the relative focus
    public void lightUnleash() {
        focusX = 0;
        focusY = 0;

        android.notifyView();
    }

    // heavy unleash confirms the shape
    public void heavyUnleash() {
        selected = null;
        android.notifyView();
    }

    // onSelected determines whether the point xy is within the selected xshape
    public boolean onSelected(int x, int y) {
        return selected != null ? false : selected.contains((double) x, (double) y);
    }

    public void select(int x, int y) {

        if (loShape.isEmpty())
            return;

        for (Xshape s : loShape) {
            if (s.contains(x, y)) {
                selected = s;
                color = s.getColor();
                thickness = s.getThick();
                focusX = x;
                focusY = y;
                break;
            }
        }

        android.notifyView();
    }

    public Xshape getSelected() {
        return selected;
    }

    public ArrayDeque<Xshape> getAllShape() {
        return new ArrayDeque<Xshape>(loShape);
    }


    public void moveShape(int x, int y) {
        selected.changeXY1(x - focusX, y - focusY);
        focusX = x;
        focusY = y;
        android.notifyView();
    }

    public void setFilled(double x, double y, double scale) {
        for (Xshape s : loShape) {
            if (s.contains(x, y)) {
                s.fill(color);
                android.notifyView();
                return;
            }
        }

        android.notifyView();
    }

    public void clear() {
        loShape = new ArrayDeque<Xshape>();
        android.notifyView();
    }

    // undo/redo
    public void makeChanges(ArrayDeque<Xshape> state){
        undoStack.addFirst(new ArrayDeque<Xshape>(state));
        redoStack.clear();
        // for memory purpose we only store up to 5 objects
        if (undoStack.size() > 5)
            undoStack.pollLast();
    }

    public void undo(){
        redoStack.addFirst(new ArrayDeque<Xshape>(loShape));
        loShape = undoStack.pollFirst();
    }

    public void redo(){
        undoStack.addFirst(new ArrayDeque<Xshape>(loShape));
        loShape = redoStack.pollFirst();
    }
}
