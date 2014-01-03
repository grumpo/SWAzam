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
	private ChartController chartBean;
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
		chartBean = this.<ChartController> findBean("chartBean");
		coinLog = tableService.getCoinLogforUser(user);
		
		chartBean.setValues(coinLog);
		chartBean.createLinearModel();
	}
	
	public void updateRequestLog() {
		recognitionRequests.clear();
		recognitionRequests = tableService.getRequestHistory(user);
	}


	public void updateCurrentCoins() {
		user.setCoins(tableService.getCurrentCoinsForUser(user));
	}
	

	public void setUser(User user) {
		this.user = user;
	}
	
	
	/**
	 * Helper
	 */
	@SuppressWarnings("unchecked")
	public <T> T findBean(String beanName) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
	}

}
