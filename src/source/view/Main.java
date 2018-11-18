package source.view;
import source.controller.GameEngine;
import source.controller.Input;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Timer;

public class Main
{
//Son halinde gerek kalmayacak test run lar� i�in
	public static void main(String[] args)
	{
		GuiPanelManager guiManager = new GuiPanelManager();

		KeyListener keyListener = Input.getKeyListener();
		MouseListener mouseListener = Input.getMouseListener();

		guiManager.addKeyListener(keyListener);
		guiManager.addMouseListener(mouseListener);

		System.out.println("1");
		GameEngine gameEngine = new GameEngine();
		Timer timer = new Timer();
		timer.schedule(gameEngine, 0, 1000 / 60);
		System.out.println("2");
	}

}