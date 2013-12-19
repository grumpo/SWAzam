package at.ac.tuwien.swa.SWAzam.Client.GUIView;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by markus on 18.12.13.
 */
public class BackgroundPanel extends JPanel {

    int width;
    int height;

    public BackgroundPanel(int width, int height){
        super();

        this.width = width;
        this.height = height;

        Dimension dim = new Dimension(width, height);

        this.setMinimumSize(dim);
        this.setPreferredSize(dim);
        this.setMaximumSize(dim);

        this.setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(Color.lightGray);
        g2d.fill(new Ellipse2D.Float(0, 0, width-1, height-1));
        g2d.setPaint(Color.darkGray);
        g2d.draw(new Ellipse2D.Float(0, 0, width-1, height-1));
    }
}
