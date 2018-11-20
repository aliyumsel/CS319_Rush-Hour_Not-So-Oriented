package source.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import interfaces.Drawable;

public class Truck extends Vehicle {

	public Truck(int x, int y, String direction, boolean player) {
		super(x, y, 3, direction, player);
		if (player) {
			super.setType("Player");
		} else {
			super.setType("Truck");
		}

	}

}
