import javax.swing.*;

public class Error {
    
    public void ShowError(String msg){
        JFrame f = new JFrame("Error!");
        JOptionPane.showMessageDialog(f, msg);
    }
}
