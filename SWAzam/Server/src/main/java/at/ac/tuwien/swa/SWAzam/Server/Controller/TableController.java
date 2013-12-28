package at.ac.tuwien.swa.SWAzam.Server.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import at.ac.tuwien.swa.SWAzam.Server.Entity.CoinLog;
import at.ac.tuwien.swa.SWAzam.Server.Entity.RecognitionRequest;
import at.ac.tuwien.swa.SWAzam.Server.Model.TableService;
import at.ac.tuwien.swa.SWAzam.Server.Model.TableServiceImpl;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;



@ManagedBean(name="tableBean") 
@SessionScoped
public class TableController {

	List<RecognitionRequest> recognitionRequests;
	List<CoinLog> coinLog;
	
	private TableService tableService;
	private User user;
	
	public TableController() {
		recognitionRequests = new ArrayList<RecognitionRequest>();
		tableService = new TableServiceImpl();
	}
	
	
	
	
	public List<RecognitionRequest> getRecognitionRequests() {
		return recognitionRequests;
	}

	public void setRecognitionRequests(List<RecognitionRequest> recognitionRequests) {
		this.recognitionRequests = recognitionRequests;
	}

	public List<CoinLog> getCoinLog() {
		return coinLog;
	}

	public void setCoinLog(List<CoinLog> coinLog) {
		this.coinLog = coinLog;
	}

	public void updateCoinLog() {
		coinLog = tableService.getCoinLogforUser(user);
	}
	
	public void updateRequestLog() {
		recognitionRequests.clear();
		recognitionRequests = tableService.getRequestHistory(user);
		
		//TODO - move to DB
		/*
		recognitionRequests.add(new RecognitionRequest(0, new Date(System.currentTimeMillis()), 
				new Song(), "peer url", false, false));
		
		recognitionRequests.add(new RecognitionRequest(1, new Date(System.currentTimeMillis()), 
					new Song("Volbeat", "Still Counting", "", 2008), "peer url", true, true));
		
		recognitionRequests.add(new RecognitionRequest(2, new Date(System.currentTimeMillis()), 
					new Song("Volbeat", "Cape of our Hero", "", 2012), "peer url", true, true));
		
		recognitionRequests.add(new RecognitionRequest(3, new Date(System.currentTimeMillis()), 
				new Song("", "", "", 0), "peer url", true, false));
		
		recognitionRequests.add(new RecognitionRequest(4, new Date(System.currentTimeMillis()), 
				new Song("Rise Against", "Saviour", "Appeal to Reason", 2008), "peer url", true, true));
		
		recognitionRequests.add(new RecognitionRequest(5, new Date(System.currentTimeMillis()), 
				new Song("", "", "", 0), "peer url", true, false));*/
	}




	public void setUser(User user) {
		this.user = user;
	}
}
