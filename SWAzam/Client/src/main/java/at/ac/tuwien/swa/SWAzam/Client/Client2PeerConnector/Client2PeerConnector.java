package at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector;


import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public interface Client2PeerConnector {
    public FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, String password) throws UnableToConnectToPeer;
    public UserInformation getUserInformation(String user, String password) throws UnableToConnectToPeer;
}