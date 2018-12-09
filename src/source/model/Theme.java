package source.model;

import source.controller.GameManager;
import source.view.GuiPanelManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Theme {

    private String activeTheme; //aslında theme de bi sürü theme oluyo diye böyle yazdım
    private ArrayList<BufferedImage> shortVehicleImageArray; //Short ve Longu ayırdım hani zaten theme calssındayız ayrı dursunlar bari babında
    private ArrayList<BufferedImage> longVehicleImageArray;
    private BufferedImage playerImage;
    private BufferedImage specialPlayer;
    private BufferedImage obstacle;
    private BufferedImage background;
    private ArrayList<String> vehicleSoundArray;
    private String themeSong;
    private String buttonClick;
    private String path;

    public Theme(String theme) {
        shortVehicleImageArray = new ArrayList<>();
        longVehicleImageArray = new ArrayList<>();
        this.activeTheme = theme;
        path = "src/image/theme_" + this.activeTheme + "/";
        initializeAttributes();
    }

    private void initializeAttributes() { //update yaparsak işimize yarar diye ayırdım ama update gerekmicek %99.9
        setSounds();
        setImages();
    }

    private void setSounds() {
        buttonClick = path + "buttonClick.wav";
        themeSong = path + "theme.wav";
    }

    private void setImages() {
        initializeVehicleImages();
        setPlayerImage();
        setSpecialPlayer();
        setBackground();
        setObstacleImage();
        rescaleImages();
    }

    private void initializeVehicleImages() {
        for (int i = 0; i < 2; i++) //size 2, 2 farklı renk var çünkü her vehicle için
        {
            longVehicleImageArray.add(LoadImage(path + "long" + i + ".png"));
            shortVehicleImageArray.add(LoadImage(path + "short" + i + ".png"));
        }
    }

    private void setObstacleImage() {
        obstacle = LoadImage(path + "obstacle.png");
    }

    private void setPlayerImage() {
        playerImage = LoadImage(path + "player.png");
    }

    private void setBackground() {
        background = LoadImage(path + "background.png");
    }

    private void setSpecialPlayer() {
        specialPlayer = LoadImage(path + "special.png");
    }

    public BufferedImage getLongVehicleImage() {
        return longVehicleImageArray.get((int) (Math.random() * 2)); //test lazım ama çalışır
    }

    public BufferedImage getShortVehicleImage() {
        return shortVehicleImageArray.get((int) (Math.random() * 2));
    }

    public BufferedImage getPlayerImage() {
        return playerImage;
    }

    public BufferedImage getSpecialPlayerImage() {
        return specialPlayer;
    }

    public BufferedImage getObstacleImage() {
        return obstacle;
    }

    public BufferedImage getBackgroundImage() {
        return background;
    }

    public String getButtonClickSound() {
        return buttonClick;
    }

    public String getThemeSong() {
        return themeSong;
    }

    @SuppressWarnings("Duplicates")
    private void rescaleImages() {
        Image scaledImage;
        Graphics2D bGr;
        for (int i = 0; i < shortVehicleImageArray.size(); i++) {
            scaledImage = shortVehicleImageArray.get(i).getScaledInstance(60, 120, Image.SCALE_DEFAULT);
            shortVehicleImageArray.set(i, new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB));
            bGr = shortVehicleImageArray.get(i).createGraphics();
            bGr.drawImage(scaledImage, 0, 0, null);
            bGr.dispose();
        }

        for (int i = 0; i < longVehicleImageArray.size(); i++) {
            scaledImage = longVehicleImageArray.get(i).getScaledInstance(60, 180, Image.SCALE_DEFAULT);
            longVehicleImageArray.set(i, new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB));
            bGr = longVehicleImageArray.get(i).createGraphics();
            bGr.drawImage(scaledImage, 0, 0, null);
            bGr.dispose();
        }

        scaledImage = specialPlayer.getScaledInstance(60, 120, Image.SCALE_DEFAULT);
        specialPlayer = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        bGr = specialPlayer.createGraphics();
        bGr.drawImage(scaledImage, 0, 0, null);
        bGr.dispose();

        scaledImage = playerImage.getScaledInstance(60, 120, Image.SCALE_DEFAULT);
        playerImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        bGr = playerImage.createGraphics();
        bGr.drawImage(scaledImage, 0, 0, null);
        bGr.dispose();
    }

    public BufferedImage LoadImage(String FileName) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(FileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
