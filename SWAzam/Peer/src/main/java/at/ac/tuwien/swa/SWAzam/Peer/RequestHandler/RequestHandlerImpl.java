package at.ac.tuwien.swa.SWAzam.Peer.RequestHandler;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.Common.UserInformation;
import at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier.MP3Identifier;
import at.ac.tuwien.swa.SWAzam.Peer.RequestForwarder.RequestForwarder;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RequestHandlerImpl implements RequestHandler {

    private final static Logger log = Logger.getLogger(RequestHandlerImpl.class.getName());

    @Inject
    private MP3Identifier mp3Identifier;
    @Inject
    private RequestForwarder requestForwarder;

    public FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops) {
        if (!mp3Identifier.contains(fingerprint)) {
            log.info("Unable to handle request of user: " + user);
            return requestForwarder.identifyMP3Fingerprint(fingerprint, user, hops);
        }
        log.info("Resolving request of user: " + user);
        FingerprintResult result = new FingerprintResult();
        result.setResult(mp3Identifier.identify(fingerprint));
        result.setHops(new ArrayList<String>());
        return result;
    }

    @Override
    public UserInformation getUserInformation(String user, String password) {
        // TODO: call server webservice
        return new UserInformation(user, 23);
    }
}
