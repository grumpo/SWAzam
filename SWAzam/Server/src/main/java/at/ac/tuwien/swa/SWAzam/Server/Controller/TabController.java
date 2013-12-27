package at.ac.tuwien.swa.SWAzam.Server.Controller;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



@ManagedBean(name="tabBean") 
@SessionScoped
public class TabController{

	private Integer activeTabIndex = 0;

	public TabController() {
		
	}

	public Integer getActiveTabIndex() {
		return activeTabIndex;
	}


	public void setActiveTabIndex(Integer activeTabIndex) {
		this.activeTabIndex = activeTabIndex;
	}


	public String changeTabs() {
		if (activeTabIndex == 0) 
		    return "overview.xhtml?faces-redirect=true";
		else if (activeTabIndex == 1) 
		    return "accountview.xhtml?faces-redirect=true";		
		return "";
	}
}