import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Menu extends JMenuBar implements IView{

    // Menu Widgets
    JMenu file;
    JMenu view;
    JMenu drawView;
    JMenuItem fileItem[];
    JMenuItem viewItem[];

    ButtonGroup dv;
    MainFrame mainFrame;

    public Menu(MainFrame main){
        super();

        // create a reference to the main display
        mainFrame = main;

        // file
        file = new JMenu("File");
        fileItem = new JMenuItem[3];

        fileItem[0] = new JMenuItem("New");
        fileItem[1] = new JMenuItem("Save");
        fileItem[2] = new JMenuItem("Load");

        for (JMenuItem i:fileItem){
            file.add(i);
        }

        this.add(file);

        //view
        view = new JMenu("View");
        viewItem = new JMenuItem[3];

        //sub-menu for image format
        drawView = new JMenu("Draw View Options");
        dv = new ButtonGroup();

        viewItem[0] = new JRadioButtonMenuItem("Full Size");
        viewItem[1] = new JRadioButtonMenuItem("Fit to Window");
        //add button groups
        drawView.add(viewItem[0]);
        drawView.add(viewItem[1]);
        dv.add(viewItem[0]);
        dv.add(viewItem[1]);
        viewItem[0].setSelected(true);


        viewItem[2] = new JCheckBoxMenuItem("Lock Contents", true);
        view.add(drawView);
        view.add(viewItem[2]);

        this.add(view);

        // add listener to Lock Contents button
        viewItem[2].addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if  (viewItem[2].isSelected()){
                    mainFrame.lockComponents();
                }else {
                    mainFrame.unlockComponents();
                }
            }
        }); // end of addItemListener
    }

    public void notifyView(){}


}
