package at.ac.tuwien.swa.SWAzam.Server.Controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import at.ac.tuwien.swa.SWAzam.Server.Entity.CoinLog;
import at.ac.tuwien.swa.SWAzam.Server.Entity.RecognitionRequest;
import at.ac.tuwien.swa.SWAzam.Server.Entity.Song;
import at.ac.tuwien.swa.SWAzam.Server.Model.TableService;
import at.ac.tuwien.swa.SWAzam.Server.Model.TableServiceImpl;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;


@ManagedBean(name="tableBean") 
@SessionScoped
public class TableController {

	List<RecognitionRequest> recognitionRequests;
	List<CoinLog> coinLog;
	
	private TableService tableService;
	
	private UserController userBean;
	
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

	public UserController getUserBean() {
		return userBean;
	}

	public void setUserBean(UserController userBean) {
		this.userBean = userBean;
	}
	
	/**
	 * Helper
	 */
	@SuppressWarnings("unchecked")
	public <T> T findBean(String beanName) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
	}

	public void updateCoinLog() {
		userBean = this.<UserController> findBean("userBean");
		coinLog = tableService.getCoinLogforUser(new User(userBean.getUser().getUsername(), "", 0));
	}
	
	public void updateRequestLog() {
		recognitionRequests.clear();
		
		//TODO - move to DB
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
