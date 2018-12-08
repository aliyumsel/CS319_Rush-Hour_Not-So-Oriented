package source.model;

import source.controller.GameManager;
import source.controller.ThemeManager;
import source.view.GuiPanelManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Vehicle extends GameObject// implements Drawable
{
    private String type; // we may not need this
    public boolean isMoving; // we may not need this
    private boolean player;
    private int verticalMoveAxis;
    private int horizontalMoveAxis;
    private int drawingIndexForMoving;
    private boolean special = false;
    private BufferedImage image;

    public Vehicle() {

    }

    public Vehicle(Vehicle templateVehicle, int length) {
        this((int) templateVehicle.transform.position.x, (int) templateVehicle.transform.position.y, 2, templateVehicle.transform.direction, false, false, "traffic");
    }

    Vehicle(int x, int y, int length, String direction, boolean player, boolean special, String theme) //theme i sil burdan
    {
        super(x, y, length, direction);
        this.player = player;
        isMoving = false;
        this.special = special;
        this.drawingIndexForMoving = 60;
        verticalMoveAxis = 0;
        horizontalMoveAxis = 0;
        updateImages();
    }

    public void move(int moveAxis) {
        if (!isMoving) {
            if (transform.axis.equals("Vertical")) {
                transform.position.y -= moveAxis;
                verticalMoveAxis = moveAxis; // if moveaxis == -1 vehicle goes downwards
            } else if (transform.axis.equals("Horizontal")) {
                transform.position.x += moveAxis;
                horizontalMoveAxis = moveAxis; // if moveaxis == -1 vehicle goes left
            }
            findOccupiedCells();
        }
    }

    @Override
    public void updateImages() {
        if (!player && transform.length == 2) {
            image = ThemeManager.instance.getShortVehicleImage();
        } else if (!player && transform.length == 3) {
            image = ThemeManager.instance.getLongVehicleImage();
        } else if (player && !this.special) {
            image = ThemeManager.instance.getPlayerImage();
        } else if (player) {
            image = ThemeManager.instance.getSpecialPlayerImage();
        }
    }

    public void moveToPoint(int x, int y) {
        transform.position.x = x;
        transform.position.y = y;
        findOccupiedCells();
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    public boolean isPlayer() {
        return player;
    }

    @Override
    public void draw(Graphics graphics) {
        int gridPixelSize = 60;
        AffineTransform at;
        Graphics2D graphics2d = (Graphics2D) graphics;

        if (drawingIndexForMoving <= 0) {
            drawingIndexForMoving = gridPixelSize;
            isMoving = false;
            verticalMoveAxis = 0;
            horizontalMoveAxis = 0;
        }

        if (isMoving && transform.axis.equals("Vertical") && verticalMoveAxis == -1) {
            at = AffineTransform.getTranslateInstance(occupiedTransforms[0].position.x * gridPixelSize, occupiedTransforms[0].position.y * gridPixelSize - drawingIndexForMoving);
            drawingIndexForMoving -= 4;
        } else if (isMoving && transform.axis.equals("Vertical") && verticalMoveAxis == 1) {
            at = AffineTransform.getTranslateInstance(occupiedTransforms[0].position.x * gridPixelSize, occupiedTransforms[0].position.y * gridPixelSize + drawingIndexForMoving);
            drawingIndexForMoving -= 4;
        } else if (isMoving && transform.axis.equals("Horizontal") && horizontalMoveAxis == -1) {
            at = AffineTransform.getTranslateInstance(occupiedTransforms[0].position.x * gridPixelSize + drawingIndexForMoving, occupiedTransforms[0].position.y * gridPixelSize);
            drawingIndexForMoving -= 4;
        } else if (isMoving && transform.axis.equals("Horizontal") && horizontalMoveAxis == 1) {
            at = AffineTransform.getTranslateInstance(occupiedTransforms[0].position.x * gridPixelSize - drawingIndexForMoving, occupiedTransforms[0].position.y * gridPixelSize);
            drawingIndexForMoving -= 4;
        } else if (!isMoving && transform.axis.equals("Horizontal")) {

            at = AffineTransform.getTranslateInstance(occupiedTransforms[0].position.x * gridPixelSize, occupiedTransforms[0].position.y * gridPixelSize);
        } else {
            at = AffineTransform.getTranslateInstance(occupiedTransforms[0].position.x * gridPixelSize, occupiedTransforms[0].position.y * gridPixelSize);
        }


        if (transform.direction.equals("Upwards")) {
            graphics2d.drawImage(image, at, null);
        } else if (transform.direction.equals("Downwards")) {
            at.rotate(Math.toRadians(180), image.getWidth() / 2.0, image.getHeight() / 2.0);
            graphics2d.drawImage(image, at, null);
        } else if (transform.direction.equals("Left")) {
            at.rotate(Math.toRadians(90), image.getWidth() / 2.0, image.getHeight() / 2.0 / transform.length);
            at.translate(0, -60);
            graphics2d.drawImage(image, at, null);
        } else {
            at.rotate(Math.toRadians(270), image.getWidth() / 2.0, image.getHeight() / 2.0 / transform.length);
            graphics2d.drawImage(image, at, null);
        }
    }
}
