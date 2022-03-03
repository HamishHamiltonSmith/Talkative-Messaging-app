


public class Main {

    Error err = new Error();

    public String[] pull_chat_data(String user, Socket_utils Su){
        String[] x = {};
        String msg = "";

        System.out.println("[+] Pulling chat data");
              

        try {
            Su.OS.writeUTF("PULL /usr/share/Talkative/server/users/" + user + "/user_chats.txt");
            msg = Su.IS.readUTF();
            return msg.split(" ", -1);

        } catch (Exception e){
            System.out.println("Error: " + e);
            err.ShowError("Error pulling chat data");
            return x;
        }       
    }






    public static void main(String[] args) {
        
        Main m = new Main();
        Socket_utils Su = new Socket_utils(true);


        //Start gui + later networking phase
        gui_main gm = new gui_main();
        UserData uD = new UserData();
        String[] chat_data = m.pull_chat_data(uD.GetName(), Su);

        gm.menu_screen(chat_data,Su);


    }
}