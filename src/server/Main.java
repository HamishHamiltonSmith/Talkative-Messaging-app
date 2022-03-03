import java.io.*;
import java.net.*;





class ClientHandler extends Thread{
    final DataInputStream DI;
    final DataOutputStream DO;
    final Socket socket;
    
    public ClientHandler(Socket socket, DataInputStream DI, DataOutputStream DO){
        this.socket = socket;
        this.DI = DI;
        this.DO = DO;
    }

    @Override

    public void run(){
        Handlers Hnd = new Handlers(this.socket, this.DI, this.DO);
        String line = "";
        try {
            while (!line.equals("CLOSE"))
            {
                try
                {
                    line = DI.readUTF();

                    if (line.contains("PULL")){
                        System.out.println("Recievd PULL request from " + this.socket);
                        String i[] = line.split(" ",-1);
                        Hnd.PULL(i[1]);
                    } else if (line.contains("POST")) {
                        System.out.println("Recievd POST request from " + this.socket);
                        String[] y = line.split(" ",2);
                        String[] i = y[1].split(" ",2);

                        Hnd.POST(i[0], i[1]);
                    } else if (line.contains("CREATE")) {
                        System.out.println("Recievd CREATE request from " + this.socket);
                        String[] y = line.split(":",2);

                        String[] i= y[1].split(" ",2);

                        String chatName = i[0];
                        String[] userList = i[1].split(" ");

                        Hnd.CREATE(chatName,userList);
                    } else if (line.split(" ", 2)[0].equals("FETCH")){
                        
                        System.out.println("Recievd FETCH request from " + this.socket);
                        String[] y = line.split(" ",2);
                        String params = y[1];

                        Hnd.FETCH(params);
                    } else if (line.split(" ",2)[0].equals("DELETE")){

                        System.out.println("Recievd DELETE request from " + this.socket);
                        String[] y = line.split(" ",2);
                        String path = y[1];
                        Hnd.DELETE(path);
                    }
                        

                }
                catch(Exception e){
                    System.out.println("Client phase breakown, terminating...");
                    System.out.println("Error: " + e);
                    socket.close();
                    break;
                }
            }

            System.out.println("Closing connection");

            socket.close();

        } catch (Exception e){
            System.out.println("Error: " + e);
        }

    }
}

public class Main {

    public ServerSocket SS;
    public Socket socket;
    public DataInputStream DI;
    public DataOutputStream DO;

    public void startServer(){
        try {
            SS = new ServerSocket(2000);
            System.out.println("Started server!");
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        while (true) {
            try {

                socket = SS.accept();
                
                System.out.println("Recieved connection from " + socket);


                DO = new DataOutputStream(socket.getOutputStream());


                DI = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
                
                Thread t = new ClientHandler(socket, DI, DO);

                t.start();
    
                
            } catch (Exception e){
                System.out.println("MAJOR SYSTEM ERROR: CLIENT THREADING PHASE HAS BROKEN DOWN");
                System.out.println("Error: " + e);
            }
        }
    }

    public static void main(String[] args){
        System.out.println("Starting Talkative server!");
        Main m = new Main();
        m.startServer();
    }

}