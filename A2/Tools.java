import javax.swing.*;
import java.awt.*;

/**
 * Created by ED on 2016/05/30.
 */
public class Tools extends JPanel{

    // Child components
    private JButton[] tools;
    private final String buttons[] = {
            "Cl",
            "E",
            "L",
            "Ci",
            "S",
            "F",
    }; // will change to image view later.

    public Tools(){
        super();
        tools = new JButton[6];

        for (int i = 0 ; i < 6; i ++ ) {
            tools[i] = new JButton(buttons[i]);
            this.add(tools[i]);
        }

    }
}
