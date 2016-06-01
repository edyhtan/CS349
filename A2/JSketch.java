import javax.swing.*;
import java.awt.*;

public class JSketch {

    public static void main (String[] args){

        // create windows
        JFrame frame = new JFrame("JSketch");

        frame.setLayout(new BorderLayout());

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

        //Outter pane
        OuterPane op = new OuterPane();

        frame.add(op, BorderLayout.LINE_START);
        frame.add(menubar, BorderLayout.NORTH);
        // Set windows configuration
        frame.setSize(800,600);
        frame.setMinimumSize(new Dimension(800,600)); // create minimum size
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
