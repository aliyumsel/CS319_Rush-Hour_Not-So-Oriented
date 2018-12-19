package source.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//Test
class LevelButton extends JButton {
    private GuiPanelManager guiManager;

    private BufferedImage levelBackground;
    private BufferedImage levelBackgroundHighlighted;

    private BufferedImage starActive;
    private BufferedImage starInactive;
    private BufferedImage bonusLevelIcon;
    private BufferedImage lockedBackground;
    private JLabel timerIconLabel;
    private static Dimension levelButtonDimension = new Dimension(105, 120);

    private JLabel[] stars;

    private int levelNo;

    private boolean isLocked;

    LevelButton(GuiPanelManager _guiManager) {
        super();
        guiManager = _guiManager;

        levelNo = 0;
        isLocked = false;

        setLayout(null);
        loadImages();
        setupButton();
        createComponents();
        addComponents();
        setBoundsOfComponents();
    }

    private void setupButton() {
        setPreferredSize(levelButtonDimension);

        setIcon(new ImageIcon(levelBackground));
        setRolloverIcon(new ImageIcon(levelBackgroundHighlighted));
        setPressedIcon(new ImageIcon(levelBackgroundHighlighted));
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusable(false);

        setVerticalTextPosition(SwingConstants.CENTER);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setFont(new Font("Odin Rounded", Font.PLAIN, 25));
        revalidate();
    }

    private void loadImages() {
        Graphics2D bGr;
        levelBackground = guiManager.LoadImage("src/image/icons/levelbackground.png");
        levelBackgroundHighlighted = guiManager.LoadImage("src/image/icons/levelbackgroundH.png");
        lockedBackground = guiManager.LoadImage("src/image/icons/levelBackgroundLocked.png");
        starActive = guiManager.LoadImage("src/image/icons/miniStar.png");
        starInactive = guiManager.LoadImage("src/image/icons/miniStarLocked.png");
        bonusLevelIcon = guiManager.LoadImage("src/image/icons/timerIcon.png");
//        Image scaledImage = bonusLevelIcon.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
//        bonusLevelIcon = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
//        bGr = bonusLevelIcon.createGraphics();
//        bGr.drawImage(scaledImage, 0, 0, null);
//        bGr.dispose();

    }

    private void createComponents() {
        stars = new JLabel[3];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = UIFactory.createLabelIcon(starInactive, "miniStar");
        }
        timerIconLabel = UIFactory.createLabelIcon(bonusLevelIcon,"timer");
        if(!isLocked)
            timerIconLabel.setVisible(false);
    }

    private void addComponents() {
        for (int i = 0; i < stars.length; i++) {
            add(stars[i]);
        }
        add(timerIconLabel);
    }

    private void setBoundsOfComponents() {
        for (int i = 0; i < stars.length; i++) {
            stars[i].setBounds(guiManager.findCenter(levelButtonDimension.width, stars[i]) + (30 * (i - 1)), 15, stars[i].getPreferredSize().width, stars[i].getPreferredSize().height);
        }
        timerIconLabel.setBounds(52, 55, timerIconLabel.getPreferredSize().width, timerIconLabel.getPreferredSize().height);
    }

    void setLevelNo(int _levelNo) {
        levelNo = _levelNo;
        setText("" + (levelNo + 1));
    }

    void showStars(int starAmount) {
        if (starAmount == -1) {
            for (int i = 0; i < stars.length; i++) {
                stars[i].setVisible(false);
            }
            return;
        }
        for (int i = 0; i < stars.length; i++) {
            if (i < starAmount) {
                stars[i].setIcon(new ImageIcon(starActive));
            } else {
                stars[i].setIcon(new ImageIcon(starInactive));
            }
            stars[i].setVisible(true);
        }

    }

    void showTimerIcon(boolean isBonusLevel)
    {
        timerIconLabel.setVisible(isBonusLevel);
    }
    void toggleLock(boolean state) {
        isLocked = state;
        //System.out.println("isLocked: " + isLocked);

        BufferedImage temp;
        BufferedImage tempH;
        if (isLocked) {
            temp = lockedBackground;
            tempH = lockedBackground;
            setEnabled(false);
            showStars(-1);
        } else {
            temp = levelBackground;
            tempH = levelBackgroundHighlighted;
            setEnabled(true);
            showStars(0);
        }
        setIcon(new ImageIcon(temp));
        setRolloverIcon(new ImageIcon(tempH));
        setDisabledIcon(new ImageIcon(lockedBackground));
    }

//   boolean getIsLocked()
//   {
//      return isLocked;
//   }
}

