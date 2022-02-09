import java.util.*;
import java.io.File;

public class UserData {

    Error err = new Error();

    public String[] GetAllUsers(Socket_utils sU){
        
        String[] x = sU.FetchUsers();
        

        return x;
    }

    public String getBio(boolean html){
        
        try {
            File f = new File("/home/kali/Talkative/Users/" + GetName() + "/user_bio.txt");
            Scanner fileScan = new Scanner(f);

            if (html){
                String total = "<html>";

                while (fileScan.hasNextLine()){
                    total += fileScan.nextLine() + "<br/>";
                }

                fileScan.close();

                return total + "</html>";
            } else {
                String total = "";
                while (fileScan.hasNextLine()){
                    total += fileScan.nextLine() + "\n";
                }

                fileScan.close();

                return total;
            }
        } catch (Exception e){
            
            err.ShowError("Failed to fetch bio");
            System.out.println("Failed to fetch user bio");
            return "Failed to fetch bio!";
        }
    }

    public String GetName(){

        try {

            File f = new File("/home/kali/java/Talkative/Client/UserData/user.txt");
            Scanner fileScan = new Scanner(f);

            String x = fileScan.nextLine();

            fileScan.close();

            return x;

        } catch (Exception e){
            err.ShowError("Failed to fetch name");
            System.out.println("Error: " + e);
            return "";
        }

    }
}
