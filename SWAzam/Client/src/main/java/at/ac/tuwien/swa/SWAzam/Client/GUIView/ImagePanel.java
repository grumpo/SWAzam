package at.ac.tuwien.swa.SWAzam.Client.GUIView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by markus on 17.12.13.
 */
public class ImagePanel extends JPanel {
    Image img;

    public ImagePanel(String img){
        this(new ImageIcon(img).getImage());
    }

    public ImagePanel(Image img){
        this.img = img;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        int iw = img.getWidth(this);
        int ih = img.getHeight(this);

        if(iw > 0 && ih > 0){
            for(int x = 0; x < this.getWidth(); x += iw){
                for(int y = 0; y < this.getHeight(); y += ih){
                    g.drawImage(img, x, y, iw, ih, this);
                }
            }
        }
    }
}
