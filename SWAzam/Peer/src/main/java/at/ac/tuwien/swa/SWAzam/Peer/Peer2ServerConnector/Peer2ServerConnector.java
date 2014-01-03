package at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector;

import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.Common.UserInformation;

public interface Peer2ServerConnector {
    UserInformation validateUser(String user, String password) throws UnableToConnectToServerException;
    boolean resolvedIdentification(String user, String password, FingerprintResult result) throws UnableToConnectToServerException;
    boolean requestedIdentification(String user, String password, FingerprintResult result) throws UnableToConnectToServerException;
    void requestIssued (String user, String password, String requestId) throws UnableToConnectToServerException;
}
