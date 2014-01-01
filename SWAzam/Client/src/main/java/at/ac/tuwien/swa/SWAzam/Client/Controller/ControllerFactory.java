package at.ac.tuwien.swa.SWAzam.Client.Controller;

public interface ControllerFactory {
    Controller create(String dbPath);
}
