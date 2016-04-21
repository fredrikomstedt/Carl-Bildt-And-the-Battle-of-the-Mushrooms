import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class Listener implements KeyListener
{
	public void keyPressed(KeyEvent e)// Check if key is pressed, and if so,
										// store in list
	{
		boolean alreadyPressed = false;
		for(int i = 0; i < pressed.size(); i++)
		{
			if(pressed.get(i) == e.getKeyCode())
			{
				alreadyPressed = true;
				break;
			}
		}
		if(!alreadyPressed)
		{
			pressed.add(e.getKeyCode());
		}
	}

	public void keyReleased(KeyEvent e)// Check if key released, and if so,
										// remove from list
	{
		pressed.removeFirstOccurrence(e.getKeyCode());
	}

	public void keyTyped(KeyEvent e)
	{
		// NO
	}

	public static void updateKeyPress()
	{
		for(int i = 0; i < pressed.size(); i++)// Player movement
		{
			switch(pressed.get(i))
			{
				case KeyEvent.VK_UP:// Move Up
					Robotron.area.player.setRelativeY(-Robotron.area.player.getSpeed());
					break;
				case KeyEvent.VK_DOWN:// Move Down
					Robotron.area.player.setRelativeY(Robotron.area.player.getSpeed());
					break;
				case KeyEvent.VK_LEFT:// Move Left
					Robotron.area.player.setRelativeX(-Robotron.area.player.getSpeed());
					Robotron.area.player.setImage("playerFlipped.png");// To
																		// make
																		// it
																		// look
																		// like
																		// player
																		// rotates
																		// 180
																		// degrees
																		// X-axis
					break;
				case KeyEvent.VK_RIGHT:// Move Right
					Robotron.area.player.setRelativeX(Robotron.area.player.getSpeed());
					Robotron.area.player.setImage("player.png");// To make it
																// look like
																// player
																// rotates 180
																// degrees
																// X-axis
					break;
			}
		}
	}

	public static void updateBulletShooting()// Responds to the player's
												// shooting commands
	{
		for(int i = 0; i < pressed.size(); i++)
		{
			if(pressed.get(i) == KeyEvent.VK_A)// Shoot left
			{
				Robotron.area.bullet.add(new Bullet(Robotron.area.player.getX() - 16,
						Robotron.area.player.getY() + Robotron.area.player.getHeight() / 2 - 5, 180, "bullet.png"));
				break;
			} else if(pressed.get(i) == KeyEvent.VK_D)// Shoot right
			{
				Robotron.area.bullet.add(new Bullet(Robotron.area.player.getX() + Robotron.area.player.getWidth() + 5,
						Robotron.area.player.getY() + Robotron.area.player.getHeight() / 2 - 5, 0, "bullet.png"));
				break;
			} else if(pressed.get(i) == KeyEvent.VK_W)// Shoot up
			{
				Robotron.area.bullet
						.add(new Bullet(Robotron.area.player.getX() + Robotron.area.player.getWidth() / 2 - 5,
								Robotron.area.player.getY() - 16, 90, "bullet.png"));
				break;
			} else if(pressed.get(i) == KeyEvent.VK_S)// Shoot down
			{
				Robotron.area.bullet
						.add(new Bullet(Robotron.area.player.getX() + Robotron.area.player.getWidth() / 2 - 5,
								Robotron.area.player.getY() + Robotron.area.player.getHeight() + 5, 270, "bullet.png"));
				break;
			}
		}
	}

	private static LinkedList<Integer> pressed = new LinkedList<Integer>();
}
