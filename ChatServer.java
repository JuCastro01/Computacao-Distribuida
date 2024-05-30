import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends UnicastRemoteObject implements Chat {
    private static final long serialVersionUID = 1L;
    private List<ClientInterface> clients;

    protected ChatServer() throws RemoteException {
        clients = new ArrayList<>();
    }

    @Override
    public synchronized void sendMessage(String message) throws RemoteException {
        for (ClientInterface client : clients) {
            client.receiveMessage(message);
        }
    }

    @Override
    public synchronized void registerClient(ClientInterface client) throws RemoteException {
        clients.add(client);
    }
    
    public static void main(String[] args) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            ChatServer server = new ChatServer();
            java.rmi.Naming.rebind("ChatService", server);
            System.out.println("Chat Server is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

