/**
 * Created by ED on 2016/05/29.
 */

import javax.swing.*;
import java.awt.*;

public class OuterPane extends JPanel{

    //Widgets within the Outer Panel
    JPanel Tools;
    JPanel Colours;
    JPanel LineThickness;
    JPanel Paint;


    public OuterPane(){
        super();
        Tools = new Tools();

        this.setLayout(new BorderLayout());
        this.add(Tools, BorderLayout.LINE_START);
    }
}
