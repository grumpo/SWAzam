package at.ac.tuwien.swa.SWAzam.Server.Controller;



import java.io.Serializable;  
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.chart.CartesianChartModel;  
import org.primefaces.model.chart.LineChartSeries;  
  

@ManagedBean(name="chartBean")
@SessionScoped
public class ChartController implements Serializable {  
  
	private static final long serialVersionUID = 1L;

	private CartesianChartModel linearModel;  
  
    public ChartController() {  
        createLinearModel();  
    }  
  
    public CartesianChartModel getLinearModel() {  
        return linearModel;  
    }  

    private void createLinearModel() {  
        linearModel = new CartesianChartModel();  

        LineChartSeries series1 = new LineChartSeries();  
        series1.setLabel("Coins");  
  
        series1.set(0, 0); 
        series1.set(1, 0);  
        series1.set(2, 10);  
        series1.set(3, 6);  
        series1.set(4, 6);  
        series1.set(5, 6);  
        series1.set(6, 8); 
  
        linearModel.addSeries(series1);  
    }  
  
}
