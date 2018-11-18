package source.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import interfaces.Drawable;

public class Car extends Vehicle {

	public Car(int x, int y, String direction, boolean player) {
		super(x, y, 2, direction, player);
		if (player) {
			super.setType("Player");
		} else {
			super.setType("Car");
		}

	}

}
