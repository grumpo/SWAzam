package at.ac.tuwien.swa.SWAzam.Server.Controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import at.ac.tuwien.swa.SWAzam.Server.Entity.RecognitionRequest;
import at.ac.tuwien.swa.SWAzam.Server.Entity.Song;


@ManagedBean(name="tableBean") 
@SessionScoped
public class TableController {

	List<RecognitionRequest> recognitionRequests;
	
	public TableController() {
		recognitionRequests = new ArrayList<RecognitionRequest>();
		fillWithTestData();
	}
	
	
	
	public List<RecognitionRequest> getRecognitionRequests() {
		return recognitionRequests;
	}

	public void setRecognitionRequests(List<RecognitionRequest> recognitionRequests) {
		this.recognitionRequests = recognitionRequests;
	}

	//TODO: remove
	private void fillWithTestData() {
		
		recognitionRequests.add(new RecognitionRequest(0, new Date(System.currentTimeMillis()), 
				new Song(), "peer url", false, false));
		
		recognitionRequests.add(new RecognitionRequest(1, new Date(System.currentTimeMillis()), 
					new Song("Volbeat", "Still Counting", "Guitar Gangsters & Cadillac Blood", 2008), "peer url", true, true));
		
		recognitionRequests.add(new RecognitionRequest(2, new Date(System.currentTimeMillis()), 
					new Song("Volbeat", "Cape of our Hero", "Outlaw Gentleman and Shady Ladies", 2012), "peer url", true, true));
		
		recognitionRequests.add(new RecognitionRequest(3, new Date(System.currentTimeMillis()), 
				new Song("", "", "", 0), "peer url", true, false));
		
		recognitionRequests.add(new RecognitionRequest(4, new Date(System.currentTimeMillis()), 
				new Song("Rise Against", "Saviour", "Appeal to Reason", 2008), "peer url", true, true));
		
		recognitionRequests.add(new RecognitionRequest(5, new Date(System.currentTimeMillis()), 
				new Song("", "", "", 0), "peer url", true, false));
		
	}
}
