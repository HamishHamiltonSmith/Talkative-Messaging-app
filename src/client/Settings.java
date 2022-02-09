import javax.swing.*;
import java.awt.*;



public class Settings {
    public void SettingsScreen(){

        JFrame setFrame = new JFrame("My settings");
        setFrame.getContentPane().setBackground(Color.BLACK);
        setFrame.setLayout(new GridBagLayout());
        setFrame.setSize(new Dimension(1500,500));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;

        JLabel sT = new JLabel("SETTINGS (so many I know)");
        sT.setFont(new Font(Font.MONOSPACED, Font.BOLD, 70));
        sT.setForeground(Color.decode("#45B69C"));

        setFrame.add(sT,c);

        c.gridy = 1;

        setFrame.setVisible(true);
    }
}
