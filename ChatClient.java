import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements ClientInterface {
    private static final long serialVersionUID = 1L;
    private String name;
    private Chat chat;

    protected ChatClient(String name, Chat chat) throws RemoteException {
        this.name = name;
        this.chat = chat;
        chat.registerClient(this);
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    public void sendMessage(String message) throws RemoteException {
        chat.sendMessage(name + ": " + message);
    }

    public static void main(String[] args) {
        try {
            if (args.length < 2) {
                System.out.println("Comando: java ChatClient <client_name> <server_ip>");
                System.exit(1);
            }

            String name = args[0];
            String serverIp = args[1];
            Chat chat = (Chat) java.rmi.Naming.lookup("rmi://" + "179.97.182.16" + "/ChatService");
            ChatClient client = new ChatClient(name, chat);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Digite sua mensagem:");
            while (true) {
                String message = scanner.nextLine();
                client.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
