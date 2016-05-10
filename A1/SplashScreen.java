import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by ED on 2016/05/10.
 */
public class SplashScreen extends JPanel {

    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Display.Deep);
        g2.fillRect(0, 0, 800, 580);
    }

}
