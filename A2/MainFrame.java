import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import javax.swing.Box;

class MainFrame extends JFrame implements IView{

    //Menus
    JMenuBar menubar;

    //Views:
    JDesktopPane desktop; // used to organize dockability
    JInternalFrame toolFrame;
    JInternalFrame color;
    JInternalFrame thickness;
    JPanel image;

    //MousMotionListeners
    MouseMotionListener[] toolLock;
    MouseMotionListener[] colorLock;
    MouseMotionListener[] lineLock;

    //Additional Attribute
    int cWidth;
    int cHeight;

    //SketchBoard Box
    Component north;
    Component west;
    Component east;
    Component south;

    boolean proportionalDisplay; // used to specifies view mode

    public MainFrame(){

        // create windows
        super("JSketch");

        // create menu item: file
        menubar = new Menu(this);

        //Internal Frames(View Models)
        toolFrame = new JInternalFrame("Tool", false, false, false, false);
        toolFrame.setVisible(true);
        toolFrame.setSize(150, 250);

        color = new JInternalFrame("Color", false, false, false, false);
        color.setVisible(true);
        color.setSize(150, 150);

        thickness = new JInternalFrame("Line", false, false, false, false);
        thickness.setVisible(true);
        thickness.setSize(150, 150);

        // draw viwe is a JPanel
        image = new DrawView();

        // Create the desktop
        desktop = new CustomDesktop();

        this.setContentPane(desktop);
        this.setJMenuBar(menubar);

        // Lock toolbars
        toolLock = (MouseMotionListener[]) toolFrame.getListeners(MouseMotionListener.class);
        colorLock = (MouseMotionListener[]) color.getListeners(MouseMotionListener.class);
        lineLock = (MouseMotionListener[]) thickness.getListeners(MouseMotionListener.class);

        lockComponents();

        // Set windows configuration
        proportionalDisplay = false;
        cHeight = 600;
        cWidth = 800;
        this.setSize(800,600);
        this.setMinimumSize(new Dimension(800,600)); // create minimum size
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addComponentListener(new ResizeEventHandler());
    }

    public void notifyView(){}

    // remove all InnerFrame MouseMotionListeners to lock the components
    public void lockComponents(){

        for (MouseMotionListener listener : toolLock){
            toolFrame.removeMouseMotionListener(listener);
        }

        for (MouseMotionListener listener : colorLock){
            color.removeMouseMotionListener(listener);
        }

        // Thickness pallet
        for (MouseMotionListener listener : lineLock)
            thickness.removeMouseMotionListener(listener);

        //reposition the components:
        toolFrame.setLocation(0,0);
        color.setLocation(0, 400);
        thickness.setLocation(0,250);
    }

    // retrieve all InnerFrame MousMotionListener to support draggable content
    public void unlockComponents(){

        // Toolbar
        for (MouseMotionListener listener : toolLock)
            toolFrame.addMouseMotionListener(listener);

        // Color pallet
        for (MouseMotionListener listener : colorLock)
            color.addMouseMotionListener(listener);

        // Thickness pallet
        for (MouseMotionListener listener : lineLock)
            thickness.addMouseMotionListener(listener);
    }

    //Resizes the JInnerFrames
    public void childrenResize(){
       sketchBoard.setSize(desktop.getWidth(), desktop.getHeight());
    }

    // Override Component Listener to perform resizing actions
    class ResizeEventHandler extends ComponentAdapter {

        public void componentResized(ComponentEvent e) {
            super.componentResized(e);

            // enforce only diagonal resize
            int xdelta = getWidth() - cWidth;
            int ydelta = getHeight() - cHeight;

            if (xdelta == 0){
                setSize(cWidth+ydelta, getHeight());
            }else if (ydelta == 0){
                setSize(getWidth(), cHeight+xdelta);
            }

            int delta = xdelta == 0 ? ydelta : xdelta;

            childrenResize(); // all necessary views
            cHeight = cHeight+delta;
            cWidth = cWidth+delta;
        }
    }

    // Inner Classes to Support undockable Tool bars
    // Desktop Inner Class
    class CustomDesktop extends JDesktopPane{

        public CustomDesktop(){
            super();

            this.setLayout(null);
            this.add(toolFrame);
            this.add(color);
            this.add(thickness);

            // sketchView
            image.setSize()
            this.add(image);
            this.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
        }

        // make a grey background
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            //g.setColor(Color.gray);
            //g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}