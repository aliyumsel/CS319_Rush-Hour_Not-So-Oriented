package source.view;

import source.controller.GameEngine;
import source.controller.SoundManager;
import source.controller.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

@SuppressWarnings("Duplicates")
public class HelpPanel extends JPanel {
    private GuiPanelManager guiManager;

    private JLabel heading;
    private JLabel help1;
    private JLabel help2;

    private JButton rightArrowButton;
    private JButton leftArrowButton;
    private JButton back;

    private BufferedImage background;
    private BufferedImage title;
    private BufferedImage backButtonImage;
    private BufferedImage backButtonHighlightedImage;
    private BufferedImage help1Image;
    private BufferedImage help2Image;
    private BufferedImage rightArrow;
    private BufferedImage rightArrowHighlighted;
    private BufferedImage leftArrow;
    private BufferedImage leftArrowHighlighted;

    private int panelWidth;
    private int panelHeight;

    HelpPanel(GuiPanelManager _guiManager) {
        super(null);

        guiManager = _guiManager;

        panelWidth = guiManager.panelWidth;
        panelHeight = guiManager.panelHeight;

        setPreferredSize(new Dimension(panelWidth, panelHeight));

        loadImages();
        createComponents();
        addComponents();
        setBoundsOfComponents();
        this.setVisible(false);
    }

    public void loadImages() {
        background = ThemeManager.instance.getBackgroundImage();
        Image scaledImage = background.getScaledInstance(panelWidth, panelHeight, Image.SCALE_DEFAULT);
        background = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = background.createGraphics();
        bGr.drawImage(scaledImage, 0, 0, null);
        bGr.dispose();

        title = guiManager.LoadImage("src/image/icons/howToPlayTitle.png");
        backButtonImage = guiManager.LoadImage("src/image/icons/back.png");
        backButtonHighlightedImage = guiManager.LoadImage("src/image/icons/backH.png");
        help1Image = guiManager.LoadImage("src/image/help1.png");
        help2Image = guiManager.LoadImage("src/image/help2.png");

        rightArrow = guiManager.LoadImage("src/image/icons/rightarrow.png");
        rightArrowHighlighted = guiManager.LoadImage("src/image/icons/rightarrowH.png");
        leftArrow = guiManager.LoadImage("src/image/icons/leftarrow.png");
        leftArrowHighlighted = guiManager.LoadImage("src/image/icons/leftarrowH.png");
    }

    private void createComponents() {
        back = UIFactory.createButton(backButtonImage, backButtonHighlightedImage, "square", actionListener);

        heading = new JLabel();
        heading.setIcon(new ImageIcon(title));
        heading.setPreferredSize(new Dimension(355, 78));

        help1 = new JLabel();
        help1.setIcon(new ImageIcon(help1Image));
        help1.setPreferredSize(new Dimension(244, 192));

        help2 = new JLabel();
        help2.setIcon(new ImageIcon(help2Image));
        help2.setPreferredSize(new Dimension(204, 238));

        rightArrowButton = UIFactory.createButton(rightArrow, rightArrowHighlighted, "arrow", actionListener);
        leftArrowButton = UIFactory.createButton(leftArrow, leftArrowHighlighted, "arrow", actionListener);
    }

    private void addComponents() {
        add(heading);
        add(back);
        add(help1);
        add(help2);

        add(rightArrowButton);
        add(leftArrowButton);
    }

    private void setBoundsOfComponents() {
        heading.setBounds(guiManager.findCenter(panelWidth, heading), 25, heading.getPreferredSize().width, heading.getPreferredSize().height);

        back.setBounds(30, 30, back.getPreferredSize().width, back.getPreferredSize().height);

        help1.setBounds(guiManager.findCenter(panelWidth, help1) - 150, 180, help1.getPreferredSize().width, help1.getPreferredSize().height);

        help2.setBounds(guiManager.findCenter(panelWidth, help1) + 190, 180, help2.getPreferredSize().width, help2.getPreferredSize().height);

        leftArrowButton.setBounds(5, guiManager.findCenter(panelHeight, leftArrowButton),
                leftArrowButton.getPreferredSize().width, leftArrowButton.getPreferredSize().height);
        rightArrowButton.setBounds(panelWidth - 135, guiManager.findCenter(panelHeight, rightArrowButton),
                rightArrowButton.getPreferredSize().width, rightArrowButton.getPreferredSize().height);
    }

    private ActionListener actionListener = e -> {
        GameEngine.instance.soundManager.buttonClick();
        if (e.getSource() == back) {
            guiManager.setPanelVisible("MainMenu");
        }
    };

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBackground(g);
        // setBackground(Color.WHITE);
    }

    private void drawBackground(Graphics graphics) {

        Graphics2D graphics2d = (Graphics2D) graphics;

        graphics2d.drawImage(background, 0, 0, null);

    }
}
