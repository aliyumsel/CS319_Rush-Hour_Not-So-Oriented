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

    public TutorialPanel(boolean isTutorialActive,GuiPanelManager _guiManager) { //yeni playersa true olcak ve oyun ilk defa açılıyosa
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

    private void setActiveBackground(){
        for (int i = 0; i < backgrounds.size();i++)
        {
            backgrounds.get(i).setVisible(false);
        }

        if (index == 0)
        {
            activeBackground = backgrounds.get(0);
        }
        else if (index == 1)
        {
            activeBackground = backgrounds.get(1);
        }
        else if (index == 2){
            activeBackground = backgrounds.get(2);
        }
        else if (index == 3){
            activeBackground = backgrounds.get(3);
        }
        activeBackground.setVisible(true);
    }

    private void setActiveLabel(){
        for (int i = 0; i < tutorials.size(); i++) {
            tutorials.get(i).setVisible(false);
        }
        tutorials.get(index).setVisible(true);
        activeLabel = tutorials.get(index);
    }

    private void createComponents() {
        forwardButton = UIFactory.createButton(next,nextH,"square",actionListener);
        add(forwardButton);
        //manipulate tutorialImages, backgroundImages and points(for tutorials) here
        tutorialCoordinates.add(new Point(100,150));
        tutorialCoordinates.add(new Point(400,150));
        tutorialCoordinates.add(new Point(100,150));
        tutorialCoordinates.add(new Point(400,150));

        for(int i = 1; i <= 4;i++) {
            tutorialImages.add(LoadImage("image/help_images/help" + i + ".png"));
            System.out.println(i);
        }

        for(int i = 1; i <= 4;i++) {
            backgroundImages.add(LoadImage("image/tutorial_Backgrounds/background"+i+".png"));
            System.out.println(i);
        }


        //set the images and their coordinates above this part
        for ( int i = 0 ; i < tutorialImages.size();i++){
            Dimension temp = new Dimension(tutorialImages.get(i).getWidth(),tutorialImages.get(i).getHeight());
            tutorials.add(UIFactory.createLabelIcon(tutorialImages.get(i),temp));
            add(tutorials.get(i));
            tutorials.get(i).setVisible(false);
        }

        for ( int i = 0 ; i < backgroundImages.size();i++){
            Dimension temp = new Dimension(backgroundImages.get(i).getWidth(),backgroundImages.get(i).getHeight());
            backgrounds.add(UIFactory.createLabelIcon(backgroundImages.get(i),temp));
            add(backgrounds.get(i));
            backgrounds.get(i).setVisible(false);
        }

        coreBackground = ThemeManager.instance.getBackgroundImage();
        Image scaledImage = coreBackground.getScaledInstance(GuiPanelManager.instance.panelWidth,GuiPanelManager.instance.panelHeight, Image.SCALE_DEFAULT);
        coreBackground = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = coreBackground.createGraphics();
        bGr.drawImage(scaledImage, 0, 0, null);
        bGr.dispose();
    }

    private void setBoundsOfComponents() {
        forwardButton.setBounds(350,400,forwardButton.getPreferredSize().width,forwardButton.getPreferredSize().height);
        for (int i = 0; i < tutorials.size();i++)
        {
            setBounds(tutorials.get(i),tutorialCoordinates.get(i).x,tutorialCoordinates.get(i).y);
        }
        for (int i = 0; i < backgrounds.size();i++)
        {
            setBounds(backgrounds.get(i),0,0);
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
    public BufferedImage LoadImage(String fileName) {

        BufferedImage image = null;
        try {
            image = ImageIO.read(TutorialPanel.class.getClassLoader().getResourceAsStream(fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        BufferedInputStream image = new BufferedInputStream(
                getClass().getResourceAsStream(path));
        */return image;
    }

    private ActionListener actionListener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
          if (e.getSource() == forwardButton)
          {
              index++;
              update();
          }
        }
    };
}
