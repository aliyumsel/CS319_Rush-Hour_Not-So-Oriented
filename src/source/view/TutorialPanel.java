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
        add(forwardButton);

        //manipulate tutorialImages, backgroundImages and points(for tutorials) here sizes should match for cooardinates and labels
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));
        tutorialCoordinates.add(new Point(0, 0));

//        File folder = new File("src/image/tutorial_Labels");
//        File[] listOfFiles = folder.listFiles();
        for (int i = 1; i <= 6; i++) { // change size !!!!
            if (LoadMediaToLabel("/image/tutorial_Labels/help" + i + ".png") != null) {
                tutorials.add(LoadMediaToLabel("/image/tutorial_Labels/help" + i + ".png"));
                add(tutorials.get(i - 1));
                tutorials.get(i - 1).setVisible(false);
            } else {
                tutorials.add(LoadMediaToLabel("/image/tutorial_Labels/help" + i + ".gif"));
                add(tutorials.get(i - 1));
                tutorials.get(i - 1).setVisible(false);
            }


        }

        for (int i = 1; i <= 6; i++) {

            if (LoadMediaToLabel("/image/tutorial_Backgrounds/background" + i + ".gif") != null) {
                backgrounds.add(LoadMediaToLabel("/image/tutorial_Backgrounds/background" + i + ".gif"));
                add(backgrounds.get(i - 1));
                backgrounds.get(i - 1).setVisible(false);
                System.out.println(backgrounds.size());
            } else {
                backgrounds.add(LoadMediaToLabel("/image/tutorial_Backgrounds/background" + i + ".png"));
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
        forwardButton.setBounds(350, 400, forwardButton.getPreferredSize().width, forwardButton.getPreferredSize().height);
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
                update();
            }
        }
    };
}
