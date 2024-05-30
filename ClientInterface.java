import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void receiveMessage(String message) throws RemoteException;
}

