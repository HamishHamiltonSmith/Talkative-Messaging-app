import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;



public class BioEdit {

    JTextArea l;
    JFrame frame;
    Error err = new Error();

    final JLabel bio;
    
    public BioEdit(JLabel bio){
        this.bio = bio;
    }

    public void edit(){

        UserData uD = new UserData();
        frame = new JFrame("Edit Bio");
        frame.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 3;

        frame.setSize(1000,500);
        frame.getContentPane().setBackground(Color.decode("#2F2F2F"));

        l = new JTextArea();
        l.setText(uD.getBio(false));
        l.setBackground(Color.decode("#95A3A4"));
        l.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));

        c.gridy = 0;
        c.ipady = 200;
        frame.add(l,c);

        JButton save = new JButton("Save");
        save.addActionListener(new UserClickListener());
        save.setBackground(Color.decode("#45B69C"));

        c.ipady = 20;
        c.gridwidth = 1;
        c.gridy = 1;
        frame.add(save,c);

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new UserClickListener());
        cancel.setBackground(Color.decode("#45B69C"));

        c.gridx = 1;
        frame.add(cancel,c);

        frame.setVisible(true);
        
    }

    public class UserClickListener implements ActionListener{
    
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            UserData uD = new UserData();

            if (command == "Save"){
                try {
                    File x = new File("/usr/share/Talkative/client/UserData/user_bio.txt");
                    FileWriter fW = new FileWriter(x);
                    
                    fW.write(l.getText());
                    fW.close();

                    System.out.println("Sucessfully wrote to file!");

                    bio.setText(uD.getBio(true));
                    SwingUtilities.updateComponentTreeUI(bio);

                    frame.setVisible(false);
                    frame.dispose();

                } catch (Exception i){
                    System.out.println("Error saving file: " + i);
                    err.ShowError("Error saving file");
                }
            } else {
                frame.setVisible(false);
                frame.dispose();
            }
        }
    }

}
