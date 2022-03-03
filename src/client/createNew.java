import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;



public class createNew {

    JPanel panel;
    Socket_utils sU;
    List <JButton>JPeople = new ArrayList<> (); 
    List <String> people = new ArrayList<> ();

    


    public void MainScreen(Socket_utils Su, JFrame frame, Socket_utils S){
        sU = S;
        JFrame f = new JFrame("Create new");
        f.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.5;


        panel = new JPanel(new GridLayout(0,1));

        JPanel searchPanel = new JPanel(new GridLayout(0,1));

        JTextField searchBox = new JTextField("Enter users here");
        searchBox.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        searchBox.setBackground(Color.decode("#525252"));
        searchBox.setForeground(Color.WHITE);
        searchPanel.add(searchBox);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(Color.decode("#45B69C"));
        searchPanel.add(searchBtn);
        

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserData uD = new UserData();
                String[] aU = uD.GetAllUsers(sU);
                System.out.println(aU);
                List<String> results = new ArrayList<>();
                String uName = searchBox.getText();

                for (int x = 0; x<aU.length; x++){
                    if (aU[x].contains(uName)){
                        results.add(aU[x]);

                    } else {
                        System.out.println("Failed!");
                    }
                }

                Font f1 = new Font(Font.MONOSPACED, Font.ITALIC,  40);
                Border border = BorderFactory.createLineBorder(Color.BLACK, 5);

                List<JButton> btns = new ArrayList<>();
                JFrame resultFrame = new JFrame("Search Results");

                resultFrame.setSize(1000,700);
                resultFrame.setLayout(new GridLayout(0,1));

                JPanel resultPanel = new JPanel(new GridLayout(0,2));

                JScrollPane sP = new JScrollPane();
                sP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                sP.setViewportView(resultPanel);


                for (int x =0; x<results.size(); x++){
                    btns.add(new JButton(results.get(x)));
                    btns.get(x).setForeground(Color.decode("#45B69C"));
                    btns.get(x).setBorder(border);
                    btns.get(x).setBackground(Color.decode("#2F2F2F"));
                    btns.get(x).addActionListener(new UserClickListener());
                    btns.get(x).setFont(f1);

                    resultPanel.add(btns.get(x));

                }

                resultFrame.add(sP);
                resultFrame.setVisible(true);

            }
        });


        //Chat name
        JTextField nameField = new JTextField("<New Name Here>");
        nameField.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        nameField.setBackground(Color.decode("#525252"));
        nameField.setForeground(Color.WHITE);
        c.gridwidth = 3;
        c.gridy = 0;
        c.ipady = 40;
        c.gridx = 0;
        f.add(nameField,c);

        c.ipady = 100;
        c.gridy = 1;
        f.add(searchPanel,c);

        JScrollPane sP = new JScrollPane();
        sP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sP.getVerticalScrollBar().setUnitIncrement(16);
        sP.setViewportView(panel);
        c.gridy = 2;
        c.ipady = 100;
        f.add(sP,c);





        JButton Create = new JButton("Create");
        Create.setBackground(Color.decode("#45B69C"));

        Create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_main gm = new gui_main();
                UserData uD = new UserData();
                Main m = new Main();

            
                Su.createChat(people, nameField.getText().replace(" ","_") + ".txt");


                f.setVisible(false);
                f.dispose();

                frame.setVisible(false);
                frame.dispose();

                String[] chatData = m.pull_chat_data(uD.GetName(), Su);
                gm.menu_screen(chatData, Su);
            }
        });

        c.gridy = 3;
        c.ipady = 40;
        f.add(Create,c);

        f.setSize(1500,700);
        f.setVisible(true);
        f.getContentPane().setBackground(Color.decode("#2F2F2F"));
    }

    public class RemoveClickListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();
            JPeople.remove(people.indexOf(new String(command)));
            panel.removeAll();
            for (int x = 0; x<JPeople.size(); x++){
                panel.add(JPeople.get(x));
            }

            SwingUtilities.updateComponentTreeUI(panel);
        }
    }


    public class UserClickListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            
            people.add(command);
            JButton x = new JButton(command);
            x.setPreferredSize(new Dimension(1500,50));
            x.setBackground(Color.decode("#2F2F2F"));
            x.setForeground(Color.WHITE);
            x.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 30));
            x.addActionListener(new RemoveClickListener());
            JPeople.add(x);
            panel.add(JPeople.get(JPeople.size()-1));

            //Update panel with added user
            SwingUtilities.updateComponentTreeUI(panel);
        }		
    }
}
