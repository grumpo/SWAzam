package at.ac.tuwien.swa.SWAzam.Client.Controller;

import at.ac.tuwien.swa.SWAzam.Client.GUIView.LoginFrame;
import at.ac.tuwien.swa.SWAzam.Client.GUIView.MainFrame;

/**
 * Created by markus on 17.12.13.
 */
public class Controller {
    LoginFrame lframe;

    public void run(){
        lframe = new LoginFrame(this);
    }
}
