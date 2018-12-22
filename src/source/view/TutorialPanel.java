package source.view;

import source.controller.SoundManager;
import source.controller.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class TutorialPanel extends JPanel {

    private JButton forwardButton;
    private JButton backButton;
    private JButton cancelButton;
    private GuiPanelManager guiManager;
    public int index; //for activating current tutorial
    private boolean isTutorialActive; //will be accessed from guiManager
    private ArrayList<JLabel> tutorials = new ArrayList<>();
    private ArrayList<JLabel> backgrounds = new ArrayList<>(); //for images and gifs
    private JLabel labelBackground;
    private JLabel left;
    private JLabel right;
    private JLabel up;
    private JLabel down;
    private BufferedImage background;
    private ArrayList<Point> arrowCooardinates = new ArrayList<>();
    private JLabel activeBackground = null;
    private JLabel activeLabel = null;
    private BufferedImage coreBackground;
    private BufferedImage next = GuiPanelManager.instance.LoadImage("image/icons/nextO.png");
    private BufferedImage nextH = GuiPanelManager.instance.LoadImage("image/icons/nextOH.png");
    private BufferedImage back = GuiPanelManager.instance.LoadImage("image/icons/backO.png");
    private BufferedImage backH = GuiPanelManager.instance.LoadImage("image/icons/backOH.png");
    private BufferedImage cancel = GuiPanelManager.instance.LoadImage("image/icons/quitO.png");
    private BufferedImage cancelH = GuiPanelManager.instance.LoadImage("image/icons/quitOH.png");


    TutorialPanel(boolean isTutorialActive, GuiPanelManager _guiManager) { //yeni playersa true olcak ve oyun ilk defa açılıyosa
        setLayout(null);
        index = 0;
        this.isTutorialActive = isTutorialActive;
        guiManager = _guiManager;
        setPreferredSize(new Dimension(guiManager.panelWidth, guiManager.panelHeight));
        createComponents();
        setBoundsOfComponents();
        update();
        this.setVisible(isTutorialActive);
    }

    void update() {
        if (index == tutorials.size()) {
            backButton.setEnabled(false);
            guiManager.setPanelVisible("MainMenu");
        } else {
            backButton.setEnabled(index != 0);
            setLabels();

        }
    }


    private void setLabels() {
        for (int i = 0; i < backgrounds.size(); i++) {
            backgrounds.get(i).setVisible(false);
        }

        if (index >= 0 && index < 5) {
            activeBackground = backgrounds.get(0);
            updateLabelBackground(50, 30);
        } else if (index >= 5 && index < 10) {
            activeBackground = backgrounds.get(1);
            updateLabelBackground(-190, 140);
        } else if (index == 10) {
            activeBackground = backgrounds.get(0);
            updateLabelBackground(50, 30);
        } else if (index >= 11 && index < 13) {
            activeBackground = backgrounds.get(2);
            updateLabelBackground(-47, 130);
        } else if (index == 13) {
            activeBackground = backgrounds.get(0);
            updateLabelBackground(50, 30);
        } else if (index >= 14 && index < 18) {
            activeBackground = backgrounds.get(3);
            updateLabelBackground(50, 30);
        } else if (index == 18) {
            activeBackground = backgrounds.get(0);
            updateLabelBackground(50, 30);
        } else if (index == 19) {
            activeBackground = backgrounds.get(4);
            updateLabelBackground(-51, -190);
            //updateLabelBackground(50,50);
        } else if (index == 20) {
            activeBackground = backgrounds.get(5);
        } else if (index == 21) {
            activeBackground = backgrounds.get(6);
        } else if (index == 22) {
            activeBackground = backgrounds.get(7);
        } else if (index == 23) {
            activeBackground = backgrounds.get(8);
        } else if (index == 24) {
            activeBackground = backgrounds.get(9);
        }
        activeBackground.setVisible(true);

        for (int i = 0; i < tutorials.size(); i++) {
            tutorials.get(i).setVisible(false);
        }
        tutorials.get(index).setVisible(true); //labellar için bunu açın
        activeLabel = tutorials.get(index);
    }


    private void createComponents() {
        forwardButton = UIFactory.createButton(next, nextH, "square", actionListener);
        backButton = UIFactory.createButton(back, backH, "square", actionListener);
        cancelButton = UIFactory.createButton(cancel, cancelH, "square", actionListener);

        labelBackground = LoadMediaToLabel("/image/icons/tutorialBG.png");

        add(forwardButton);
        add(backButton);
        add(cancelButton);

        //manipulate tutorialImages, backgroundImages and points(for tutorials) here sizes should match for cooardinates and labels
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(0, 0));


        for (int i = 1; i <= 25; i++) { // change size !!!!
            if (LoadMediaToLabel("/image/tutorial_Labels/" + i + ".png") != null) {
                tutorials.add(LoadMediaToLabel("/image/tutorial_Labels/" + i + ".png"));
                add(tutorials.get(i - 1));
                tutorials.get(i - 1).setVisible(false);
            } else {
                tutorials.add(LoadMediaToLabel("/image/tutorial_Labels/" + i + ".gif"));
                add(tutorials.get(i - 1));
                tutorials.get(i - 1).setVisible(false);
            }


        }
        add(labelBackground);
        for (int i = 1; i <= 10; i++) {

            if (LoadMediaToLabel("/image/tutorial_Backgrounds/" + i + ".gif") != null) {
                backgrounds.add(LoadMediaToLabel("/image/tutorial_Backgrounds/" + i + ".gif"));
                add(backgrounds.get(i - 1));
                backgrounds.get(i - 1).setVisible(false);
                System.out.println(backgrounds.size());
            } else {
                backgrounds.add(LoadMediaToLabel("/image/tutorial_Backgrounds/" + i + ".png"));
                add(backgrounds.get(i - 1));
                backgrounds.get(i - 1).setVisible(false);
                System.out.println(backgrounds.size());
            }
        }


        left = LoadMediaToLabel("/image/icons/arrowLeft.png");
        right = LoadMediaToLabel("/image/icons/arrowRight.png");
        up = LoadMediaToLabel("/image/icons/arrowUp.png");
        down = LoadMediaToLabel("/image/icons/arrowDown.png");


        background = ThemeManager.instance.getGamePanelBackgroundImage();
        Image scaledImage = background.getScaledInstance(guiManager.panelWidth, guiManager.panelHeight, Image.SCALE_DEFAULT);
        background = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = background.createGraphics();
        bGr.drawImage(scaledImage, 0, 0, null);
        bGr.dispose();
    }

    private JLabel LoadMediaToLabel(String fileName) {
        URL url = this.getClass().getResource(fileName);
        ImageIcon icon = new ImageIcon(url);
        JLabel label = new JLabel(icon);
        return label;
    }

    private void updateLabelBackground(int x, int y) {
        labelBackground.setBounds(guiManager.findCenter(guiManager.panelWidth, labelBackground) + 200 + x, 200 + y, labelBackground.getPreferredSize().width, labelBackground.getPreferredSize().height);
        for (int i = 0; i < tutorials.size(); i++) {
            setBounds(tutorials.get(i), labelBackground.getX() + 10, labelBackground.getY() + 20); //label background coordinates
        }
        forwardButton.setBounds(guiManager.findCenter(guiManager.panelWidth, backButton) + 75 + 200 + x, 275 + y, forwardButton.getPreferredSize().width, forwardButton.getPreferredSize().height);
        backButton.setBounds(guiManager.findCenter(guiManager.panelWidth, backButton) - 75 + 200 + x, 275 + y, backButton.getPreferredSize().width, backButton.getPreferredSize().height);
        cancelButton.setBounds(guiManager.findCenter(guiManager.panelWidth, cancelButton) + 200 + x, 275 + y, backButton.getPreferredSize().width, backButton.getPreferredSize().height);

    }

    private void setBoundsOfComponents() {
        forwardButton.setBounds(guiManager.findCenter(guiManager.panelWidth, backButton) + 75 + 200, 275, forwardButton.getPreferredSize().width, forwardButton.getPreferredSize().height);
        backButton.setBounds(guiManager.findCenter(guiManager.panelWidth, backButton) - 75 + 200, 275, backButton.getPreferredSize().width, backButton.getPreferredSize().height);
        cancelButton.setBounds(guiManager.findCenter(guiManager.panelWidth, cancelButton) + 200, 275, backButton.getPreferredSize().width, backButton.getPreferredSize().height);
        labelBackground.setBounds(guiManager.findCenter(guiManager.panelWidth, labelBackground) + 200, 200, labelBackground.getPreferredSize().width, labelBackground.getPreferredSize().height);

        for (int i = 0; i < tutorials.size(); i++) {
            setBounds(tutorials.get(i), labelBackground.getX() + 10, labelBackground.getY() + 20); //label background coordinates
        }
        for (int i = 0; i < backgrounds.size(); i++) {
            setBounds(backgrounds.get(i), 0, 0);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }

    private void setBounds(JLabel label, int x, int y) {
        label.setBounds(x, y, label.getPreferredSize().width + 20, label.getPreferredSize().height);
    }

    public int getIndex() {
        return index;
    }

    public boolean isTutorialActive() {
        return isTutorialActive;
    }

    public void setIndex(int index) {
        this.index = index;
        setLabels();
    }

    public void setTutorialActive(boolean tutorialActive) {
        isTutorialActive = tutorialActive;
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == forwardButton) {
                index++;

            } else if (e.getSource() == backButton) {
                index--;

            } else if (e.getSource() == cancelButton) {
                index = tutorials.size();

            }
            SoundManager.instance.buttonClick();
            update();
        }
    };
}
