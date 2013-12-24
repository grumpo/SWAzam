package at.ac.tuwien.swa.SWAzam.Server.Peer2ServerConnector;

import at.ac.tuwien.swa.SWAzam.Server.DebitManager.DebitManager;
import at.ac.tuwien.swa.SWAzam.Server.PermissionValidator.PermissionValidator;


public interface PeerWebService {
    void run(int port, PermissionValidator pv, DebitManager dm);
}
