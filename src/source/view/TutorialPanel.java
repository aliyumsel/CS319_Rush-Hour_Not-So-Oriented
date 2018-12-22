package source.view;

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

    private ArrayList<Point> tutorialCoordinates = new ArrayList<>();
    private JLabel activeBackground = null;
    private JLabel activeLabel = null;
    private BufferedImage coreBackground;
    private BufferedImage next = GuiPanelManager.instance.LoadImage("image/icons/next.png");
    private BufferedImage nextH = GuiPanelManager.instance.LoadImage("image/icons/nextH.png");
    private BufferedImage back = GuiPanelManager.instance.LoadImage("image/icons/back.png");
    private BufferedImage backH = GuiPanelManager.instance.LoadImage("image/icons/backH.png");
    private BufferedImage cancel = GuiPanelManager.instance.LoadImage("image/icons/quit.png");
    private BufferedImage cancelH = GuiPanelManager.instance.LoadImage("image/icons/quitH.png");


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
        if (index == tutorials.size())

            guiManager.setPanelVisible("MainMenu");
        else {
            backButton.setVisible(index != 0);
            setActiveLabel();
            setActiveBackground();
        }
    }


    private void setActiveBackground() {
        for (int i = 0; i < backgrounds.size(); i++) {
            backgrounds.get(i).setVisible(false);
        }

        if (index == 0) {
            activeBackground = backgrounds.get(0);
        } else if (index == 1) {
            activeBackground = backgrounds.get(1);
        } else if (index == 2) {
            activeBackground = backgrounds.get(2);
        } else if (index == 3) {
            activeBackground = backgrounds.get(3);
        } else if (index == 4) {
            activeBackground = backgrounds.get(4);
        } else if (index == 5) {
            activeBackground = backgrounds.get(5);
        } else if (index == 6) {
            activeBackground = backgrounds.get(6);
        } else if (index == 7) {
            activeBackground = backgrounds.get(7);
        } else if (index == 8) {
            activeBackground = backgrounds.get(8);
        } else if (index == 9) {
            activeBackground = backgrounds.get(9);
        } else if (index == 10) {
            activeBackground = backgrounds.get(10);
        } else if (index == 11) {
            activeBackground = backgrounds.get(11);
        } else if (index == 12) {
            activeBackground = backgrounds.get(12);
        } else if (index == 13) {
            activeBackground = backgrounds.get(13);
        }
        activeBackground.setVisible(true);
    }

    private void setActiveLabel() {
        for (int i = 0; i < tutorials.size(); i++) {
            tutorials.get(i).setVisible(false);
        }
        //tutorials.get(index).setVisible(true); //labellar için bunu açın
        activeLabel = tutorials.get(index);
    }

    private void createComponents() {
        forwardButton = UIFactory.createButton(next, nextH, "square", actionListener);
        backButton = UIFactory.createButton(back, backH, "square", actionListener);
        cancelButton = UIFactory.createButton(cancel, cancelH, "square", actionListener);
        add(forwardButton);
        add(backButton);
        add(cancelButton);

        //manipulate tutorialImages, backgroundImages and points(for tutorials) here sizes should match for cooardinates and labels
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));


        for (int i = 1; i <= 14; i++) { // change size !!!!
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

        for (int i = 1; i <= 14; i++) {

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

    }

    private JLabel LoadMediaToLabel(String fileName) {
        URL url = this.getClass().getResource(fileName);
        ImageIcon icon = new ImageIcon(url);
        JLabel label = new JLabel(icon);
        return label;
    }

    private void setBoundsOfComponents() {
        forwardButton.setBounds(guiManager.findCenter(guiManager.panelWidth, backButton) + 115, 465, forwardButton.getPreferredSize().width, forwardButton.getPreferredSize().height);
        backButton.setBounds(guiManager.findCenter(guiManager.panelWidth, backButton) - 115, 465, backButton.getPreferredSize().width, backButton.getPreferredSize().height);
        cancelButton.setBounds(guiManager.findCenter(guiManager.panelWidth, cancelButton), 465, backButton.getPreferredSize().width, backButton.getPreferredSize().height);

        for (int i = 0; i < tutorials.size(); i++) {
            setBounds(tutorials.get(i), tutorialCoordinates.get(i).x, tutorialCoordinates.get(i).y);
        }
        for (int i = 0; i < backgrounds.size(); i++) {
            setBounds(backgrounds.get(i), 0, 0);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

    }

    private void setBounds(JLabel label, int x, int y) {
        label.setBounds(x, y, label.getPreferredSize().width, label.getPreferredSize().height);
    }

    public int getIndex() {
        return index;
    }

    public boolean isTutorialActive() {
        return isTutorialActive;
    }

    public void setIndex(int index) {
        this.index = index;
        setActiveLabel();
        setActiveBackground();
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
            update();
        }
    };
}
