import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JSketch {

    public static void main (String[] args){

        // create windows
        JFrame frame = new JFrame("JSketch");

        // create menu item: file
        JMenuBar menubar = new JMenuBar();

        // file
        JMenu file = new JMenu("file");
        JMenuItem fileItem[] = new JMenuItem[3];

        fileItem[0] = new JMenuItem("new");
        fileItem[1] = new JMenuItem("save");
        fileItem[2] = new JMenuItem("load");

        for (JMenuItem i:fileItem){
            file.add(i);
        }

        menubar.add(file);

        //view
        JMenu view = new JMenu("view");
        JMenuItem viewItem[] = new JMenuItem[2];

        viewItem[0] = new JMenuItem("Full Size");
        viewItem[1] = new JMenuItem("Fit to Window");

        for (JMenuItem i:viewItem){
            view.add(i);
        }

        menubar.add(view);

        JInternalFrame toolFrame = new JInternalFrame("Tool", true, false, false, false);
        toolFrame.setVisible(true);
        toolFrame.setSize(200, 350);
        toolFrame.setLocation(0,0);

        JInternalFrame color = new JInternalFrame("Color", true, false, false, false);
        color.setVisible(true);
        color.setSize(200, 200);
        color.setLocation(0,350);

        JInternalFrame image = new JInternalFrame("Image", true, false, false, false);
        color.setVisible(true);
        color.setSize(200, 200);
        color.setLocation(0,350);

        // Create the desktop
        JDesktopPane desktop = new JDesktopPane();
        frame.setContentPane(desktop);
        frame.setJMenuBar(menubar);

        desktop.add(toolFrame);
        desktop.add(color);

        desktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE);

        // Dockability testing
        MouseMotionListener[] motionListeners = (MouseMotionListener[]) toolFrame.getListeners(MouseMotionListener.class);

        for (MouseMotionListener listener : motionListeners){
            toolFrame.removeMouseMotionListener(listener);
        }

        for (MouseMotionListener listener : motionListeners){
            toolFrame.addMouseMotionListener(listener);
        }


        // Set windows configuration
        frame.setSize(800,600);
        frame.setMinimumSize(new Dimension(800,600)); // create minimum size
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
