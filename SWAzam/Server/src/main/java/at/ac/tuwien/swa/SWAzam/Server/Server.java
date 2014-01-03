package at.ac.tuwien.swa.SWAzam.Server;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.Tomcat;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import at.ac.tuwien.swa.SWAzam.Server.DebitManager.DebitManagerFactory;
import at.ac.tuwien.swa.SWAzam.Server.Peer2ServerConnector.PeerWebService;
import at.ac.tuwien.swa.SWAzam.Server.PermissionValidator.PermissionValidatorFactory;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorage;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorageImpl;


public class Server {
	
	private final static Logger log = Logger.getLogger(Server.class.getName());

	@Inject
	private PeerWebService peerWebService;
	
	@Inject
	private DebitManagerFactory debitManagerFactory;
	
	@Inject 
	private PermissionValidatorFactory permissionValidatorFactor;
	
    
	// only one instance - no guicer needed ?
    public static void main(String[] argv) throws IOException {
    	Injector injector = Guice.createInjector(new ServerModule());
        String msg = "Please pass the port for the web services as argument.";

    	
    	 if (argv.length < 1) {
             log.log(Level.SEVERE, msg);
             return;
         }
    	 
         Integer port;
         
         try {
             port = Integer.valueOf(argv[0]);
         } catch (NumberFormatException e) {
             log.log(Level.SEVERE, "Port is missing or malformed! " + msg);
             return;
         }
         
         //TODO: just for testing - remove!
         UserDataStorage uds = new UserDataStorageImpl();
         uds.addRequestEntry(new User("Jane", "", 0), "test request ID");
          
         injector.getInstance(Server.class).run(port);
         startTomcatEmbedded();	  	
    }

    private static void startTomcatEmbedded() {
    	String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();        
        tomcat.setPort(8080);
        
        Context context;

        try {
			context = tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
			WebappLoader solrLoader = new WebappLoader(Server.class.getClassLoader());
			context.setLoader(solrLoader); 
			tomcat.start();
	        tomcat.getServer().await();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
  	}

	public void run(Integer port) {
        log.info("Server has been started and is running now...");
        startService(port);
    }
    
    
	private void startService(Integer port) {
		peerWebService.run(port, permissionValidatorFactor.create(), debitManagerFactory.create());	
	}

}
