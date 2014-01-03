package at.ac.tuwien.swa.SWAzam.Server.Controller;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



@ManagedBean(name="tabBean") 
@SessionScoped
public class TabController{

	private Integer activeTabIndex = 0;
	
	private TableController tableBean;

	public TabController() {
		
	}

	public Integer getActiveTabIndex() {
		return activeTabIndex;
	}


	public void setActiveTabIndex(Integer activeTabIndex) {
		this.activeTabIndex = activeTabIndex;
	}
	
	/**
	 * Helper
	 */
	@SuppressWarnings("unchecked")
	public <T> T findBean(String beanName) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
	}


	public String changeTabs() {
		tableBean = this.<TableController> findBean("tableBean");
		
		if (activeTabIndex == 0) {
			tableBean.updateRequestLog();
			tableBean.updateCurrentCoins();
		    return "overview.xhtml?faces-redirect=true";
		}
		else if (activeTabIndex == 1) {
			tableBean.updateCoinLog();
		    return "coinview.xhtml?faces-redirect=true";	
		}
		if (activeTabIndex == 2) {
		    return "accountview.xhtml?faces-redirect=true";
		}
		
		return "";
	}
}