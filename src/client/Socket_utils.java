import java.io.*;
import java.net.*;
import java.util.List;

public class Socket_utils {
    public Socket socket;
    public DataInputStream IS;
    public DataOutputStream OS;
    public boolean set;

    Error err = new Error();


    public Socket_utils(boolean set){
        try {
            if (set == true){
                this.socket = new Socket("127.0.0.1", 2000);
                this.OS = new DataOutputStream(socket.getOutputStream());
                this.IS = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
                System.out.println("[+] Started socket!");
            }

        } catch (Exception e){
            err.ShowError("Error connecting to server");
            System.out.println("Error: " + e);
        }
    }

    public String createChat(List<String> people, String ChatName){
        try {
            String Users = "";
            for (int x = 0; x<people.size(); x++){
                Users += " " + people.get(x);
            }


            this.OS.writeUTF("CREATE:" + ChatName + " " + Users);
            return this.IS.readUTF();


        } catch (Exception e) {
            err.ShowError("Failed to create chat");
            System.out.println("Failed to create chat!");
            return null;
        }
    }

    public String DeleteChat(String path){
        try {
            OS.writeUTF("DELETE " + path);
            System.out.println(IS.readUTF());
            return "Successfuly deleted chat";

        } catch (Exception e) {
            System.out.println("Failed to delete chat");
            Error r = new Error();
            r.ShowError("Failed to delete chat");
            return "ERROR";
        }

    }

    public String[] FetchUsers(){
        try {
            OS.writeUTF("FETCH *");
            String result = IS.readUTF();
            System.out.println(result);
            String[] l = result.split(" ",-1);

            return l;

        } catch (Exception e){
            System.out.println("Error: " + e);
            String[] err = {"Error!"};
            return err ;
        }
    }

    public String pull_chat_messages(String PATH){
        try {
            this.OS.writeUTF("PULL " + PATH);
            String response = "";

            response = this.IS.readUTF();
            response = response.split("//",2)[1];
            
            return response;
            

        } catch (Exception e){
            err.ShowError("Error fetching chat messages");
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }

    public void SendMessage(String PATH, String msg){
        try {
            UserData uD = new UserData();

            OS.writeUTF("POST " + PATH + " " + uD.GetName() + ": " + msg);
            System.out.println(IS.readUTF());
        } catch (Exception e){
            err.ShowError("Failed to send message");
            System.out.println("Error: " + e);
        }
    }


    
}
