import java.io.*;
import java.net.*;


class Handlers {
    final Socket socket;
    final DataInputStream DI;
    final DataOutputStream DO;

    public Handlers(Socket socket, DataInputStream DI, DataOutputStream DO){
        this.socket = socket;
        this.DI = DI;
        this.DO = DO;
    }

    public void PULL_RECIEVE(){
        try {
            String msg = "";
            while (!msg.equals("END")){
                msg = DI.readUTF();
                System.out.println(msg);
            }



        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void POST_RECIEVE(){
        try {
            String msg = "";
            while (!msg.equals("END")){
                msg = DI.readUTF();
                System.out.println(msg);
            }


        } catch (Exception e){
            System.out.println(e);
        }
    }
}
