package at.ac.tuwien.swa.SWAzam.Server.Controller;



import java.io.Serializable;  
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.chart.CartesianChartModel;  
import org.primefaces.model.chart.LineChartSeries;  
import at.ac.tuwien.swa.SWAzam.Server.Entity.CoinLog;
  

@ManagedBean(name="chartBean")
@SessionScoped
public class ChartController implements Serializable {  
  
	private static final long serialVersionUID = 1L;

	private CartesianChartModel linearModel;  
	
	private List<CoinLog> coinLog;
  
    public ChartController() {  
    }  
  
    public CartesianChartModel getLinearModel() {  
        return linearModel;  
    }  

    public void createLinearModel() {  
        linearModel = new CartesianChartModel(); 
       
        LineChartSeries lineValues = new LineChartSeries();  
        lineValues.setLabel("Coins");  
        
        List<Integer> numCoins = new ArrayList<Integer>();
        
        if (coinLog.size() < 8) {
        	for (int i = 0; i < 8-coinLog.size(); i++){
        		numCoins.add(0);
        	}
        	for (int i = 0; i < coinLog.size(); i++) {
    			numCoins.add(coinLog.get(coinLog.size()-1-i).getCoins_new());
    		}
        }
        else {
			for (int i = 0; i < 8; i++) {
				numCoins.add(coinLog.get(coinLog.size()-1-i).getCoins_new());
			}
        }
        
		for (int i = 0; i < 8; i++) {
			lineValues.set(i, numCoins.get(i));
		}

        linearModel.addSeries(lineValues);  
    }

	public void setValues(List<CoinLog> coinLog) {
		this.coinLog = coinLog;		
	}  
  
}
