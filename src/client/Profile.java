import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;



public class Profile {

    Error err = new Error();
    JPanel ProfilePanel;
    JLabel pPic;
    JLabel bio;

    public void MainScreen(){
        UserData uD = new UserData();

        String uName = uD.GetName();

        JFrame ProfileFrame = new JFrame("My Profile");
        ProfileFrame.setSize(1500,700);

        //cF:Frame layout
        ProfileFrame.setLayout(new GridBagLayout());

        GridBagConstraints cF = new GridBagConstraints();
        cF.fill = GridBagConstraints.VERTICAL;
        cF.weighty = 0.5;
        cF.weightx = 0.5;
        

        //c:Panel Layout
        ProfilePanel = new JPanel(new GridBagLayout());
        ProfilePanel.setBackground(Color.decode("#95A3A4"));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        //Username
        JLabel name = new JLabel(uName);
        name.setFont(new Font(Font.MONOSPACED, Font.BOLD, 60));
        name.setForeground(Color.decode("#45B69C"));

        cF.gridx = 0;
        cF.gridy = 0;
        cF.gridwidth = 3;

        ProfileFrame.add(name,cF);
        cF.fill = GridBagConstraints.HORIZONTAL;


        //Profile panel setup
        JScrollPane sP = new JScrollPane(ProfilePanel);
        sP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        cF.gridy = 1;
        cF.ipady = 400;

        ProfileFrame.add(sP,cF);


        try {

            c.gridy = 0;
            c.gridx = 0;
            c.weighty = 0.5;
            
            BufferedImage Picture = ImageIO.read(new File("/usr/share/Talkative/client/UserData/profilePic.png"));
            Image PictureScaled = Picture.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            pPic = new JLabel(new ImageIcon(PictureScaled));
            ProfilePanel.add(pPic, c);

        } catch (Exception e){
            System.out.println("Failed to create image: " + e);
            err.ShowError("Error creating profile image");
        }

        bio = new JLabel(uD.getBio(true));
        bio.setForeground(Color.BLACK);
        bio.setBackground(Color.decode("#95A3A4"));
        bio.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 25));
        bio.setOpaque(true);

        c.gridy = 1;
        ProfilePanel.add(bio,c);


        //Edit buttons

        JButton editPic = new JButton("Change picture");
        editPic.setBackground(Color.decode("#45B69C"));
        editPic.addActionListener(new UserClickListener());

        cF.gridwidth = 1;
        cF.ipady = 20;
        cF.gridy = 2;

        ProfileFrame.add(editPic,cF);

        JButton editBio = new JButton("Change bio");
        editBio.setBackground(Color.decode("#45B69C"));
        editBio.addActionListener(new UserClickListener());

        cF.gridx = 1;
        ProfileFrame.add(editBio,cF);


        ProfileFrame.getContentPane().setBackground(Color.decode("#2F2F2F"));
        ProfileFrame.setVisible(true);
    }


    public class UserClickListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();
            String PATH = "";

            if (command == "Change picture"){

                System.out.println("Editing pic");
                JFileChooser fileChoiceUi = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Png Images", "png");
                fileChoiceUi.setFileFilter(filter);
                int returnVal = fileChoiceUi.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    PATH = fileChoiceUi.getSelectedFile().getPath();

                    System.out.println("-" + PATH + "-");
                }

                try {

                

                    File x = new File(PATH);
                    File y = new File("/usr/share/Talkative/client/UserData/profilePic.png");

                    if (PATH.length() > 1){
                        y.delete();
                    } else {
                        System.out.println("No path!");
                        return;
                    }

                    InputStream is = null;
                    OutputStream os = null;
                    try {
                        is = new FileInputStream(x);
                        os = new FileOutputStream(y);
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = is.read(buffer)) > 0) {
                            os.write(buffer, 0, length);
                        }
                    } finally {
                        is.close();
                        os.close();
                    }

                    BufferedImage Picture = ImageIO.read(new File("/usr/share/Talkative/client/UserData/profilePic.png"));
                    Image PictureScaled = Picture.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
                    pPic.setIcon(new ImageIcon(PictureScaled));
                    SwingUtilities.updateComponentTreeUI(ProfilePanel);

                } catch (Exception i){
                    System.out.println("Error changing profile picture: " + i);
                    err.ShowError("Error changing picture");
                    
                }
            } else if (command == "Change bio"){
                BioEdit bE = new BioEdit(bio);
                bE.edit();
            }
        }		
    }
}
