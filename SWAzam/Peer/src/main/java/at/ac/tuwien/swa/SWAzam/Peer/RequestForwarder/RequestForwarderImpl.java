package at.ac.tuwien.swa.SWAzam.Peer.RequestForwarder;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerConnectorFactory;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.UnableToConnectToPeer;
import at.ac.tuwien.swa.SWAzam.Peer.PeerStorage.Peer;
import at.ac.tuwien.swa.SWAzam.Peer.PeerStorage.PeerStorage;
import ch.lambdaj.function.convert.Converter;
import com.google.inject.Inject;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class RequestForwarderImpl implements RequestForwarder {

    private final static Logger log = Logger.getLogger(RequestForwarderImpl.class.getName());
    private static final String PEER_WEB_SERVICE_WSDL_LOCATION = "/PeerWebService?wsdl";
    // TODO: do not hardcode this
    private static final int INITIAL_FAIL_COUNT = 5;
    public static final int MAX_HOP_COUNT = 3;

    @Inject
    private PeerStorage peerStorage;
    @Inject
    private Peer2PeerConnectorFactory peer2PeerConnectorFactory;

    @Override
    public void identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops) throws RequestForwarderException {
        addPeersToPeerStorage(hops);
        Set<Peer> peers = peerStorage.getPeers();

        if (hops.size() > MAX_HOP_COUNT) {
            throw new RequestForwarderException("Tried too many hops, giving up...");
        }

        for(Peer peer : peers)
        {
            if (hops.contains(peer.getUrl())) {
                log.info("Skipping peer, already tried: " + peer.getUrl());
                continue;
            }
            try {
                String peerUrl = peer.getUrl();
                peer2PeerConnectorFactory.create(peerUrl + PEER_WEB_SERVICE_WSDL_LOCATION).identifyMP3Fingerprint(fingerprint, user, hops);
                break; // just try one peer (no backtracking)
            } catch (UnableToConnectToPeer e) {
                log.info("Peer seems down: " + e.getMessage());
                peer.failure();
            }
        }
        throw new RequestForwarderException("Tried all known Peers, none could resolve the fingerprint...");
    }

    private void addPeersToPeerStorage(List<String> hops) {
        List<Peer> peers = ch.lambdaj.Lambda.convert(hops, new Converter<String, Peer>() {
            @Override
            public Peer convert(String url) {
                return new Peer(url, INITIAL_FAIL_COUNT);
            }
        });
        for (Peer peer : peers) {
            if (peerStorage.getPeers().contains(peer)) continue;
            log.info("Adding peer to storage: " + peer);
            peerStorage.addPeer(peer);
        }
    }

}
