import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Handlers {
    final Socket socket;
    final DataInputStream DI;
    final DataOutputStream DO;
    public File f;
    public Scanner fileScan;

    public Handlers(Socket socket, DataInputStream DI, DataOutputStream DO){
        this.socket = socket;
        this.DI = DI;
        this.DO = DO;
    }

    public void DELETE(String chatName){
        try {

            File f = new File(chatName.replace("\n", ""));

            Scanner fScan = new Scanner(f);
            String p = fScan.nextLine().replace("//","");
            String[] users = p.split(",",-1);
            fScan.close();

            for (int x = 0; x<users.length; x++){
                String content = "";

                File person = new File("/usr/share/Talkative/server/users/" + users[x] + "/user_chats.txt");
                Scanner fScan2 = new Scanner(person);

                while (fScan2.hasNextLine()){
                    content += fScan2.nextLine();
                }

                FileOutputStream oS = new FileOutputStream(person.getPath(),false);


                if (content.contains(" ")){
                    content = content.replace(" " + chatName.replace("\n",""),"");
                } else {
                    content = content.replace(chatName.replace("\n",""),"");
                }
                System.out.println("New content: " + content);
                oS.write(content.getBytes());
                
                
                oS.close();
                fScan2.close();
            }

            if (f.delete()){
                System.out.println("Deleted chat: " + chatName);
                DO.writeUTF("Success!!!!");
            } else {
                System.out.println("Failed to delete chat: " + chatName);
                DO.writeUTF("Failiure");
            }
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    public void FETCH(String params){
        try {
            if (params.replace("\n", "").replace(" ","").equals("*")){

                File f = new File("/usr/share/Talkative/server/users");
                String[] x = f.list();
                String result = "";

                for (int i = 0; i<x.length; i++){
                    if (i != 0){
                        result += " " + x[i];
                    } else {
                        result += x[i];
                    }
                }
                
                DO.writeUTF(result);

            } else {
                System.out.println(params);
            }
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    public void CREATE(String chatName,String[] Users){

        String PATH = "/usr/share/Talkative/server/chats/" + chatName;
        System.out.println("[+] New chat path: " + PATH);
        try {
            File f = new File(PATH);


            if (f.createNewFile()){
                System.out.println("[+] Created new chat!");
                DO.writeUTF("Created chat!");
            } else{
                DO.writeUTF("Failed to create chat");
            }

            String people = "";
            for (int x=0; x<Users.length; x++){
                System.out.println(Users[x]);
                if (x != 0){
                    people += "," + Users[x];
                } else{
                    people += Users[x];
                }
            }

            FileOutputStream oS = new FileOutputStream(f,false);
            oS.write(people.getBytes());
            oS.write("//".getBytes());
            oS.close();

            String userPath = "";

            for (int x = 0; x<Users.length; x++){
                try {

                    userPath = "/usr/share/Talkative/server/users/" + Users[x].replace("\n","") + "/user_chats.txt";
                            
                    FileOutputStream out = new FileOutputStream(userPath,true);
                    Scanner readPath = new Scanner(new File(userPath));
                
                    if (readPath.hasNext()){
                        out.write(" ".getBytes());
                        out.write(PATH.getBytes());
                        out.close();
                    } else {
                        out.write(PATH.getBytes());
                        out.close();
                    }

                } catch (Exception e){
                    System.out.println("Failed to reference user: " + e);
                }
            }
        } catch (Exception e){
            System.out.println("Failed chat creation process: " + e);
        }

    }

    public void PULL(String PATH){
        System.out.println("[+] Requested path:" + PATH);
        try {
            f = new File(PATH.replace("\n",""));
            fileScan = new Scanner(f);

            String x = "";
            while (fileScan.hasNextLine()){
                x += fileScan.nextLine() + "\n";
            }

            DO.writeUTF(x);
            System.out.println("[+] PULL completed\n");

        } catch (Exception e){
            try {
                DO.writeUTF("Error fetching file: " + e);

            } catch (Exception i) {
                System.out.println("Client connetion faliure: " + i);
            }
            System.out.println("Error:" + e);
        }
    }

    public void POST(String PATH, String msg){
        System.out.println("File for changes: " + PATH);
        System.out.println("Message: " + msg);
        PATH = PATH.replace("\n","");
        try{

            FileOutputStream out = new FileOutputStream(PATH,true);
            out.write("\n\n\n".getBytes());
            out.write(msg.getBytes());
            out.close();
            
            DO.writeUTF("[+] Wrote " + msg + " to " + PATH);
        } catch (Exception e){

            System.out.println("Error appending to file: " + e);
            try {
                DO.writeUTF("Error appending to file: " + PATH);
            } catch (Exception i){
                System.out.println("Client connection faliure: " + i);
            }
        }
    }
}
