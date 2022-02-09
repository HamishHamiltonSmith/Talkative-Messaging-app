import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

class gui_main{
    JFrame frame;
    JFrame chatFrame;
    JFrame ChatFrame;
    JTextArea TypeBox;
    JPanel panel;

    List<JButton> chat_names = new ArrayList<>();
    List<JButton> crosses = new ArrayList<>();
    List<JLabel> chat_messages = new ArrayList<>();
    List<String> chatID = new ArrayList<>();

    public String currentPath;
    String[] allChatPaths;
    Socket_utils S;



    public void chat_screen(String ChatName, String msgs, Socket_utils Su){

        S = Su;
        
        //Split messages by 3 newlines
        String[] messages = msgs.split("\n\n\n");

        Font f1 = new Font(Font.MONOSPACED, Font.ITALIC,  20);


        //Main chat frame
        ChatFrame = new JFrame("Talkative - " + ChatName);
        ChatFrame.getContentPane().setBackground(Color.BLACK);

        //Layout
        ChatFrame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        GridLayout ChatLayout = new GridLayout(0,1);
        ChatLayout.setVgap(20);


        //Area for messages to be displayed
        JScrollPane MessageScroll = new JScrollPane();
        MessageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        MessageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        MessageScroll.getVerticalScrollBar().setUnitIncrement(16);   

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 400;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;


        ChatFrame.add(MessageScroll,c);        


        //Area for typing messages
        TypeBox = new JTextArea();
        TypeBox.setFont(f1);

        c.ipady = 100;
        c.gridx = 0;
        c.gridy = 1;

        ChatFrame.add(TypeBox,c);

        
        //Where messages are contained
        JPanel ChatPanel = new JPanel();


        //Send button
        JButton SendBtn = new JButton("Send");
        SendBtn.setBackground(Color.decode("#45B69C"));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 100;
        c.gridx = 0;
        c.gridy = 2;

        ChatFrame.add(SendBtn,c);
        


        //Border for each message
        Border border = BorderFactory.createLineBorder(Color.decode("#45B69C"), 5);


        //Add messages to chat panel
        for (int x=0; x<messages.length; x++){
            chat_messages.add(new JLabel(messages[x]));
            chat_messages.get(x).setForeground(Color.BLACK);
            chat_messages.get(x).setFont(f1);
            chat_messages.get(x).setBackground(Color.GRAY);
            chat_messages.get(x).setOpaque(true);
            chat_messages.get(x).setBorder(border);
            chat_messages.get(x).setPreferredSize(new Dimension(1500,100));

            ChatPanel.add(chat_messages.get(x));
        }


        //Set scrolling view
        MessageScroll.setViewportView(ChatPanel);

        ChatPanel.setLayout(ChatLayout);


        SendBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = TypeBox.getText();
                TypeBox.setText("");
                String chatPath = currentPath;

                Su.SendMessage(chatPath, msg);
                UpdateChat(ChatPanel, currentPath, ChatName);
            }
        });

        //Configure frame
        ChatFrame.setVisible(true);
        ChatFrame.setSize(1300, 700);
    }



    public void menu_screen(String[] chat_data, Socket_utils Su){

        allChatPaths = chat_data;
        S = Su;

        


        frame = new JFrame("Talkative V1.1");
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.decode("#2F2F2F"));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 0.5;
        c.weighty = 0.5;

        panel = new JPanel();
        panel.setSize(1300, 700);
        panel.setLayout(new GridLayout(0,2));
        panel.setBackground(Color.decode("#2F2F2F"));


        Font f1 = new Font(Font.MONOSPACED, Font.ITALIC,  20);
        Font f2 = new Font(Font.MONOSPACED, Font.BOLD,  100);

        JLabel title = new JLabel("Talkative");
        title.setOpaque(true);
        title.setFont(f2);
        title.setBackground(Color.decode("#45B69C"));
        title.setPreferredSize(new Dimension(1300,100));

        c.ipady = 60;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;

        frame.add(title,c);
        c.fill = GridBagConstraints.HORIZONTAL;
        
        JScrollPane Sp = new JScrollPane();
        
        Sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Sp.getVerticalScrollBar().setUnitIncrement(16);        

        if (chat_data[0].equals("")){
            panel.setVisible(false);
        }

        for (int x=0; x<chat_data.length; x++){



            String full = chat_data[x].split("/")[chat_data[x].split("/").length-1];
            String chatName = full.replace(".txt", "");


            chatID.add(chatName);

            chat_names.add(new JButton(chatName));
            chat_names.get(x).setBackground(Color.decode("#525252"));
            chat_names.get(x).setForeground(Color.WHITE);
            chat_names.get(x).setFont(f1);
            chat_names.get(x).addActionListener(new ChatClickListener());
            chat_names.get(x).setPreferredSize(new Dimension(1230,70));
            panel.add(chat_names.get(x));

            
            crosses.add(new JButton(""));
            //crosses.get(x).setPreferredSize(new Dimension(70,70));
            crosses.get(x).setMaximumSize(new Dimension(70,70));
            crosses.get(x).setBackground(Color.decode("#F15156"));
            crosses.get(x).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    System.out.println(e.getSource());
                    String path = allChatPaths[crosses.indexOf(e.getSource())];
                    System.out.println(path);
                    S.DeleteChat(path);

                    Main m = new Main();
                    UserData uD = new UserData();

                    frame.setVisible(false);
                    frame.dispose();
                    
                    String[] chatData = m.pull_chat_data(uD.GetName(), Su);
                    menu_screen(chatData, Su);
                }
            });
            panel.add(crosses.get(x));
        }

        Sp.setViewportView(panel);

        c.gridy = 1;
        c.ipady = 350;
        frame.add(Sp,c);

        JButton CreateBtn = new JButton("Create new");
        CreateBtn.setBackground(Color.decode("#45B69C"));
        CreateBtn.addActionListener(new SettingListener());
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipady = 20;
        frame.add(CreateBtn,c);
        
        JButton ProfileBtn = new JButton ("My profile");
        ProfileBtn.setBackground(Color.decode("#45B69C"));
        ProfileBtn.addActionListener(new SettingListener());
        c.gridx = 1;
        frame.add(ProfileBtn,c);

        JButton SettingBtn = new JButton("My Settings");
        SettingBtn.setBackground(Color.decode("#45B69C"));
        SettingBtn.addActionListener(new SettingListener());
        c.gridx = 2;
        frame.add(SettingBtn,c);


        frame.setSize(1300, 700);
        frame.setVisible(true);
    }

    public void UpdateChat(JPanel ChatPanel, String path, String name){

        String y = S.pull_chat_messages(path);
        String[] messages = y.split("\n\n\n");

        Font f1 = new Font(Font.MONOSPACED, Font.ITALIC,  20);
        Border border = BorderFactory.createLineBorder(Color.decode("#45B69C"), 5);

        chat_messages.clear();

        ChatPanel.removeAll();

        for (int x=0; x<messages.length; x++){
            chat_messages.add(new JLabel(messages[x]));
            chat_messages.get(x).setForeground(Color.BLACK);
            chat_messages.get(x).setFont(f1);
            chat_messages.get(x).setBackground(Color.GRAY);
            chat_messages.get(x).setOpaque(true);
            chat_messages.get(x).setBorder(border);
            chat_messages.get(x).setPreferredSize(new Dimension(1500,100));

            ChatPanel.add(chat_messages.get(x));
        }
        
        
    }

    public class SettingListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command == "Create new"){
                createNew cn = new createNew();
                cn.MainScreen(S,frame,S);
            } else if (command == "My profile"){
                Profile pr = new Profile();
                pr.MainScreen();
            } else if (command == "My Settings"){
                Settings sT = new Settings();
                sT.SettingsScreen();
            }
        }		
    }


    public class ChatClickListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();


            gui_main gm = new gui_main();

            //String y = currentPath;

            if (chatID.contains(command)) {

                String PATH = allChatPaths[chatID.indexOf(command)];
                gm.currentPath = PATH;
                String x = S.pull_chat_messages(PATH);
                gm.chat_screen(command, x, S);

            }
        }		
    }
}