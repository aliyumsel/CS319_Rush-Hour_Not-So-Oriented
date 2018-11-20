package source.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import interfaces.Drawable;

public class Vehicle extends GameObject implements Drawable {
	private String type; // we may not need this
	private boolean isChosen; // we may not need this
	private boolean player;
	private BufferedImage vehicle;

	// AffineTransform at;
	public Vehicle() {
		super();
	}

	public Vehicle(int x, int y, int length, String direction, boolean player) {
		super(x, y, length, direction);
		this.player = player;

		if (!player && length == 2) {
			vehicle = LoadImage("src/image/Car.png");
		} else if (!player && length == 3)
			vehicle = LoadImage("src/image/Truck.png");
		else if (player)
			vehicle = LoadImage("src/image/Player.png");
	}

	public void move(int moveAxis) {

		if (transform.axis.equals("Vertical")) {
			transform.position.y -= moveAxis;
		} else if (transform.axis.equals("Horizontal")) {
			transform.position.x += moveAxis;
		}
		findOccupiedCells();
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public boolean isPlayer()
	{
		return player;
	}

	@Override
	public void draw(Graphics graphics)
	{

		Graphics2D graphics2d = (Graphics2D) graphics;
		// Tek par�a image geldi�inde kullan�lacak
		// at =
		// AffineTransform.getTranslateInstance(transform.position.x*50,transform.position.y*50);
		// at.rotate(Math.toRadians(90), car.getWidth()/2,car.getHeight()/2);
		// graphics2d.drawImage(car,at,null);
		for (int i = 0; i < transform.length; i++) {
			graphics2d.drawImage(vehicle,occupiedTransforms[i].position.x * 50,occupiedTransforms[i].position.y * 50,
					null);

			// Araban�n par�alar�n� belli etmek i�in
			graphics2d.drawString("-" + i + "-", occupiedTransforms[i].position.x * 50 + 15,
					occupiedTransforms[i].position.y * 50 + 25);
		}

		// System.out.println(transform.position.x + " " + transform.position.y);
	}

	private BufferedImage LoadImage(String FileName) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(FileName));
		} catch (IOException e) {
		}
		return image;
	}
}
