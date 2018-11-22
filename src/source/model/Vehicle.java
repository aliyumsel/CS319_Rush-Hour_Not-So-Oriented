package source.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import interfaces.Drawable;

public class Vehicle extends GameObject implements Drawable {
	private String type; // we may not need this
	private boolean isChosen; // we may not need this
	private boolean player;
	//private BufferedImage vehicle;
	private BufferedImage[] vehicleImages;

	// AffineTransform at;
	public Vehicle() {
		super();
	}

	public Vehicle(int x, int y, int length, String direction, boolean player) {
		super(x, y, length, direction);
		String theme = "traffic";
		this.player = player;
		vehicleImages = new BufferedImage[length];
		String path = "src/image/theme_" + theme + "/";
		int carType = (int) (Math.random()*2);

		if (!player && length == 2)
			path += "small";
		else if (!player && length == 3)
			path += "large";
		else if (player)
			path += "player";

		if (direction == "Upwards" || direction == "Right"){
			for(int i = 0; i < length; i++){
				if(!player){
					vehicleImages[i] = LoadImage(path + carType + "-" + i + ".png");
				}
				else
					vehicleImages[i] = LoadImage(path + "-" + i + ".png");
			}
		}
		else{
			for(int i = 0; i < length; i++){
				if(!player){
					vehicleImages[i] = LoadImage(path + carType + "-" + (length - i - 1) + ".png");
				}
				else
					vehicleImages[i] = LoadImage(path + "-" + (length - i - 1) + ".png");
			}
		}



		/*
		if (!player && length == 2)
			vehicle = LoadImage("src/image/Car.png");
		else if (!player && length == 3)
			vehicle = LoadImage("src/image/Truck.png");
		else if (player)
			vehicle = LoadImage("src/image/Player.png");
		*/
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
		// Tek parça image geldiğinde kullanılacak
		// at =
		// AffineTransform.getTranslateInstance(transform.position.x*75,transform.position.y*75);
		// at.rotate(Math.toRadians(90), car.getWidth()/2,car.getHeight()/2);
		// graphics2d.drawImage(car,at,null);
		for (int i = 0; i < transform.length; i++) {
			AffineTransform at = AffineTransform.getTranslateInstance(occupiedTransforms[i].position.x*75,occupiedTransforms[i].position.y*75);
			if(transform.direction == "Upwards")
				graphics2d.drawImage(vehicleImages[i],at,null);
			else if(transform.direction == "Downwards"){
				at.rotate(Math.toRadians(180), vehicleImages[i].getWidth()/2.0,vehicleImages[i].getHeight()/2.0);
				graphics2d.drawImage(vehicleImages[i],at,null);
			}
			else if(transform.direction == "Left"){
				at.rotate(Math.toRadians(90), vehicleImages[i].getWidth()/2.0,vehicleImages[i].getHeight()/2.0);
				graphics2d.drawImage(vehicleImages[i],at,null);
			}
			else {
				at.rotate(Math.toRadians(270), vehicleImages[i].getWidth()/2.0,vehicleImages[i].getHeight()/2.0);
				graphics2d.drawImage(vehicleImages[i],at,null);
			}
			//graphics2d.drawImage(vehicleImages[i],occupiedTransforms[i].position.x * 75,occupiedTransforms[i].position.y * 75,
			//		null);

			// Arabanın parçalarını belli etmek için
			//graphics2d.drawString("-" + i + "-", occupiedTransforms[i].position.x * 75 + 27,
			//		occupiedTransforms[i].position.y * 75 + 37);
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
