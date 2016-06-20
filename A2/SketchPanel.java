import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import javax.swing.Box;

// The Sketch Panel is only used to container to adjust layout of the app.
//Thus, It doesnot offcially contains the model.
class SketchPanel extends JPanel {

    DrawView drawView;
    JPanel outterFrame;

    int xsquare = 0;
    int ysquare = 0;

    int xleftOver = 0;
    Component LeftPadding;

    private final double DRAWFRAMEX = 600.0 ;
    private final double DRAWFRAMEY = 450.0;

    public SketchPanel(DrawView paintboard){
        super();

        drawView = paintboard;

        this.setLayout(new GridBagLayout());
        this.setLocation(0,0);
        this.setSize(800,600 - 44);

        outterFrame = new JPanel() {

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(Color.gray);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        outterFrame.setLayout(new FlowLayout());
        //outterFrame.add();
        outterFrame.add(drawView);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.6;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;

        this.add(Box.createGlue(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 0.8;
        gbc.weightx = 1.5;

        this.add(outterFrame, gbc);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.gray);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void resetSizeData(){
        xsquare = 0;
        ysquare = 0;
    }

    public void childreanResize(int dx, int dy, boolean isProportional){

        if (!isProportional)
            return;

        xsquare += dx;
        ysquare += dy;

        if (xsquare > 0 && ysquare > 0) {
            int min = xsquare > ysquare ? ysquare : xsquare;

            drawView.setPreferredSize(new Dimension (drawView.getWidth() + min, drawView.getHeight() + min));
            xsquare -= min;
            ysquare -= min;

        }else {

            if (xsquare < 0) {
                drawView.setPreferredSize(new Dimension(drawView.getWidth() + xsquare, drawView.getHeight() + xsquare));
                ysquare -= xsquare;
                xsquare = 0;
            }

            if (ysquare < 0) {
                drawView.setPreferredSize(new Dimension(drawView.getWidth() + ysquare, drawView.getHeight() + ysquare));
                xsquare -= ysquare;
                ysquare = 0;

                // minor adjustment if x-square become lower than 0
                if (xsquare < 0) {
                    drawView.setPreferredSize(new Dimension(drawView.getWidth() + xsquare, drawView.getHeight() + xsquare));
                    ysquare -= xsquare;
                    xsquare = 0;
                }
            }
        }

        drawView.rescale(drawView.getWidth() /DRAWFRAMEX);
    }
}
