package at.ac.tuwien.swa.SWAzam.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by markus on 17.12.13.
 */
public class ImageButton extends JButton {
    public ImageButton(Icon active, Icon inactive, int width, int height){
        this.setIcon(active);
        this.setDisabledIcon(inactive);

        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setBorderPainted(false);

        Dimension dim = new Dimension(width, height);
        this.setMinimumSize(dim);
        this.setPreferredSize(dim);
        this.setMaximumSize(dim);
    }

    public void setIconActive(Icon active){
        this.setIcon(active);
    }
}
