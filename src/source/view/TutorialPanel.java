package source.view;

import source.controller.ThemeManager;
import source.model.Theme;

import javax.imageio.ImageIO;
import javax.net.ssl.ManagerFactoryParameters;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class TutorialPanel extends JPanel {
    private BufferedImage next = LoadImage("image/icons/next.png");
    private BufferedImage nextH = LoadImage("image/icons/nextH.png");
    private JButton forwardButton;
    private GuiPanelManager guiManager;
    private int index; //for activating current tutorial
    private boolean isTutorialActive; //will be accessed from guiManager
    private ArrayList<JLabel> tutorials = new ArrayList<>();
    private ArrayList<JLabel> backgrounds = new ArrayList<>(); //for images and gifs
    private ArrayList<BufferedImage> backgroundImages = new ArrayList<>(); //for images and gifs
    private ArrayList<BufferedImage> tutorialImages = new ArrayList<>(); //for images and gifs
    private ArrayList<Point> tutorialCoordinates = new ArrayList<>();
    JLabel activeBackground = null;
    JLabel activeLabel = null;
    private BufferedImage coreBackground;

    public TutorialPanel(boolean isTutorialActive, GuiPanelManager _guiManager) { //yeni playersa true olcak ve oyun ilk defa açılıyosa
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
        //System.out.println(tutorials.size());
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
            activeBackground = backgrounds.get(3);
        }
        activeBackground.setVisible(true);
    }

    private void setActiveLabel() {
        for (int i = 0; i < tutorials.size(); i++) {
            tutorials.get(i).setVisible(false);
        }
        tutorials.get(index).setVisible(true);
        activeLabel = tutorials.get(index);
    }

    private void createComponents() {
        forwardButton = UIFactory.createButton(next, nextH, "square", actionListener);
        add(forwardButton);
        //manipulate tutorialImages, backgroundImages and points(for tutorials) here
        tutorialCoordinates.add(new Point(100, 150));
        tutorialCoordinates.add(new Point(400, 150));
        tutorialCoordinates.add(new Point(100, 150));
        tutorialCoordinates.add(new Point(400, 150));
        tutorialCoordinates.add(new Point(400, 150));

        for (int i = 1; i <= 5; i++) { // change size !!!!
            try {
                if (LoadImage("image/help_images/help" + i + ".png") != null) {
                    tutorialImages.add(LoadImage("image/help_images/help" + i + ".png"));
                    Dimension temp = new Dimension(tutorialImages.get(i - 1).getWidth(), tutorialImages.get(i - 1).getHeight());
                    tutorials.add(UIFactory.createLabelIcon(tutorialImages.get(i - 1), temp));

                    add(tutorials.get(i - 1));
                    tutorials.get(i - 1).setVisible(false);
                }
            } catch (Exception e) {
            }

            try {
                if (LoadGif(createImageIcon("/image/help_images/help" + i + ".gif")) != null) {

                    JLabel temp = LoadGif(createImageIcon("/image/help_images/help" + i + ".gif"));
                    tutorials.add(temp);
                    add(tutorials.get(i - 1));
                    tutorials.get(i - 1).setVisible(false);

                }
            } catch (Exception e) {
            }


        }
        for (int i = 1; i <= 4; i++) {
            try {
                if (LoadImage("image/tutorial_Backgrounds/background" + i + ".png") != null) {
                    backgroundImages.add(LoadImage("image/tutorial_Backgrounds/background" + i + ".png"));
                    Dimension temp = new Dimension(backgroundImages.get(i - 1).getWidth(), backgroundImages.get(i - 1).getHeight());
                    backgrounds.add(UIFactory.createLabelIcon(backgroundImages.get(i - 1), temp));
                    add(backgrounds.get(i - 1));
                    backgrounds.get(i - 1).setVisible(false);
                }
            }catch (Exception e){}

            try {
                if (LoadGif(createImageIcon("/image/tutorial_Backgrounds/background" + i + ".gif")) != null) {
                    JLabel temp = LoadGif(createImageIcon("/image/tutorial_Backgrounds/background" + i + ".gif"));
                    backgrounds.add(temp);
                    add(backgrounds.get(i - 1));
                    backgrounds.get(i - 1).setVisible(false);
                }
            }catch (Exception e){}


        }


        coreBackground = ThemeManager.instance.getBackgroundImage();
        Image scaledImage = coreBackground.getScaledInstance(GuiPanelManager.instance.panelWidth, GuiPanelManager.instance.panelHeight, Image.SCALE_DEFAULT);
        coreBackground = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = coreBackground.createGraphics();
        bGr.drawImage(scaledImage, 0, 0, null);
        bGr.dispose();
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

        drawBackground(g);
    }

    private void drawBackground(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics;
        graphics2d.drawImage(coreBackground, 0, 0, null);
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
    }

    public void setTutorialActive(boolean tutorialActive) {
        isTutorialActive = tutorialActive;
    }

    private BufferedImage LoadImage(String fileName) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(TutorialPanel.class.getClassLoader().getResourceAsStream(fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private JLabel LoadGif(ImageIcon icon) {
        JLabel label = new JLabel();
        label.setIcon(icon);
        return label;
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    private ImageIcon createImageIcon(String path) {

        ImageIcon icon = new ImageIcon(TutorialPanel.this.getClass().getResource(path));
        return icon;
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
