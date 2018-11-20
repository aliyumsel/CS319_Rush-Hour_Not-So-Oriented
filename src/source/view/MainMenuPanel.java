package source.view;

import javax.swing.*;

import source.controller.GameEngine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel{

    private GuiPanelManager guiManager;

    private int index;
    private JLabel heading;
    private JLabel player;
    private  JButton cPlayer;
    private  JButton play;
    private  JButton credits;
    private  JButton levels;
    private  JButton settings;
    private  JButton exit;
    private  JButton help;

    public MainMenuPanel(int index, GuiPanelManager _guiManager){
        super(null);

        guiManager = _guiManager;

        setPreferredSize(new Dimension(763,468));
        this.index = index;
        createComponents();
        add(heading);
        add(player);
        add(cPlayer);
        add(play);
        add(credits);
        add(levels);
        add(settings);
        add(help);
        add(exit);
        setBoundsOfComponents();
        this.setVisible(true);
    }

    private void createComponents() {

        ActionListener actionListener = e ->
        {
            if (e.getSource() == play)
            {
                GameEngine.instance.gameManager.loadLastLevel();
                guiManager.setPanelVisible("Game");
            }
        };

        Font stdFont = new Font("Calibri",Font.PLAIN,13);
        Dimension stdDimension = new Dimension(170,36);
        heading = new JLabel("RUSH HOUR", SwingConstants.CENTER);
        heading.setPreferredSize(new Dimension(294, 65));
        heading.setFont(new Font("Calibri", Font.BOLD+Font.ITALIC, 57));
        player = new JLabel("Player0", SwingConstants.CENTER);
        player.setPreferredSize(new Dimension(600, 32));
        player.setFont(new Font("Calibri",Font.ITALIC,24));
        cPlayer = new JButton("Change Player");
        cPlayer.setPreferredSize(stdDimension);
        cPlayer.setFont(stdFont);

        play = new JButton("PLAY");
        play.addActionListener(actionListener);


        play.setPreferredSize(new Dimension(294, 72));
        play.setFont(new Font("Calibri", Font.BOLD,28));
        credits = new JButton("Credits");
        credits.setPreferredSize(stdDimension);
        credits.setFont(stdFont);
        levels = new JButton("Levels");
        levels.setPreferredSize(stdDimension);
        levels.setFont(stdFont);
        settings = new JButton("Settings");
        settings.setPreferredSize(stdDimension);
        settings.setFont(stdFont);
        help = new JButton(new ImageIcon("src/image/info.png"));
        help.setPreferredSize(new Dimension(48, 48));
        exit = new JButton(new ImageIcon("src/image/exit.png"));
        exit.setPreferredSize(new Dimension(48, 48));
    }

    private void setBoundsOfComponents(){
        Insets insets = getInsets();
        Dimension size = help.getPreferredSize();
        help.setBounds(32 + insets.left, 46 + insets.top, size.width, size.height);
        size = exit.getPreferredSize();
        exit.setBounds(680 + insets.left, 46 + insets.top, size.width, size.height);
        size = heading.getPreferredSize();
        heading.setBounds(234 + insets.left, 67 + insets.top, size.width, size.height);
        size = player.getPreferredSize();
        player.setBounds(80 + insets.left, 145 + insets.top, size.width, size.height);
        size = cPlayer.getPreferredSize();
        cPlayer.setBounds(296 + insets.left, 189 + insets.top, size.width, size.height);
        size = play.getPreferredSize();
        play.setBounds(240 + insets.left, 268 + insets.top, size.width, size.height);
        size = credits.getPreferredSize();
        credits.setBounds(32 + insets.left, 388 + insets.top, size.width, size.height);
        size = levels.getPreferredSize();
        levels.setBounds(296 + insets.left, 388 + insets.top, size.width, size.height);
        size = settings.getPreferredSize();
        settings.setBounds(558 + insets.left, 388 + insets.top, size.width, size.height);
    }
}
