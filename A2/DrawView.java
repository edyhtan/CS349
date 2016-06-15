import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class DrawView extends JPanel implements IView{

    public DrawView() {
        super();
    }

    public void notifyView(){}

    public void paintComponents(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setSize(JPanel parent){

    }
}