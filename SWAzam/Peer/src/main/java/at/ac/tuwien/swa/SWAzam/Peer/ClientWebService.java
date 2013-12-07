package at.ac.tuwien.swa.SWAzam.Peer;
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
      String result = "Handling fingerprint identification request from" + requestingClient;
      System.out.println(result);
      return "Some good piece of music";
  }

  public static void main(String[] argv) {
    Object implementor = new ClientWebService ();
    String address = "http://localhost:9000/ClientWebService";
    Endpoint.publish(address, implementor);
  }
}
