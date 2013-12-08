package at.ac.tuwien.swa.SWAzam.Peer;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import com.google.gson.Gson;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * Created by grumpo on 12/7/13.
 */
@WebService()
public class ClientWebService {
  @WebMethod
  public String identifyMP3Fingerprint(String requestingClient) {
      System.out.println("Handling fingerprint identification request from" + requestingClient);
      Fingerprint fingerprint = new Gson().fromJson(requestingClient, Fingerprint.class);
      return "Some good piece of music: " + fingerprint.getShiftDuration();
  }

  public static void main(String[] argv) {
    Object implementor = new ClientWebService ();
    String address = "http://localhost:9000/ClientWebService";
    Endpoint.publish(address, implementor);
  }
}
