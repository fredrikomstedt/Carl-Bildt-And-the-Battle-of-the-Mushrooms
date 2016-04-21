import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameArea extends JPanel
{
	public GameArea()
	{
		setFocusable(true);
		addKeyListener(new Listener());
	}

	public void paintComponent(Graphics g)// Draw everything onto the screen
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());// Background
		if(player.getLife() != 0 && paintPlayer)
		{
			g.drawImage(player.getImage().getImage(), player.getX(), player.getY(), this);
		}
		for(int i = 0; i < bullet.size(); i++)
		{
			g.drawImage(bullet.get(i).getImage().getImage(), bullet.get(i).getX(), bullet.get(i).getY(), this);
		}
		for(int i = 0; i < enemyBullet.size(); i++)
		{
			g.drawImage(enemyBullet.get(i).getImage().getImage(), enemyBullet.get(i).getX(), enemyBullet.get(i).getY(),
					this);
		}
		for(int i = 0; i < enemy.size(); i++)
		{
			g.drawImage(enemy.get(i).getImage().getImage(), enemy.get(i).getX(), enemy.get(i).getY(), this);
		}
		for(int i = 0; i < human.size(); i++)
		{
			g.drawImage(human.get(i).getImage().getImage(), human.get(i).getX(), human.get(i).getY(), this);
		}
		g.setColor(Color.DARK_GRAY);// Draw HUD, containing score and health
									// left
		g.fillRect(0, 0, getWidth(), 35);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		g.drawString("Life: ", 10, 25);
		g.setColor(Color.BLACK);
		for(int i = 0; i < player.getLife(); i++)// Draw how many lives the
													// player still has
		{
			g.drawImage(playerLife.getImage(), 80 + 48 * i, 10, this);
		}
		g.setColor(Color.WHITE);
		g.drawString("Score: " + Integer.toString(score), getWidth() - 200, 25);// Draw
																				// score
		g.drawString("Wave: " + Integer.toString(Robotron.wave), getWidth() / 2 - 60, 25);// Draw
																							// wave
		g.setColor(Color.BLACK);

		if(gameOver)// Draw Game Over screen if game is over
		{
			g.drawImage(gameOverPic.getImage(), getWidth() / 2 - gameOverPic.getIconWidth() / 2,
					getHeight() / 2 - gameOverPic.getIconHeight() / 2, this);
		}
	}

	public void enemyCreate(int number, int type)// Create enemies
	{
		Random random = new Random();
		switch(type)
		{
			case 0:
				int rand = random.nextInt(number * 2);
				for(int i = 0; i < number + rand; i++)
				{
					enemy.add(new Grunt(random.nextInt(getWidth()), random.nextInt(getHeight()), "enemy.png"));
				}
				break;
			case 1:
				rand = random.nextInt(number - 1);
				for(int i = 0; i < (number + rand) / 2; i++)
				{
					enemy.add(new Lazy(random.nextInt(getWidth()), random.nextInt(getHeight()), "enemy1.png"));
				}
				break;
			case 2:
				rand = random.nextInt(number / 3);
				for(int i = 0; i < (number + rand - 3) / 3; i++)
				{
					int direction = 0;
					int temp = random.nextInt(4);
					switch(temp)
					{
						case 0:
							direction = 0;
							break;
						case 1:
							direction = 90;
							break;
						case 2:
							direction = 180;
							break;
						case 3:
							direction = 270;
							break;
					}
					enemy.add(new Shooter(random.nextInt(getWidth()), random.nextInt(getHeight()), "enemy2.png",
							direction));
				}
				break;
		}
	}

	public void humanCreate(int number)// Create humans
	{
		Random random = new Random();
		int rand = random.nextInt(number);
		int direction = 0;
		for(int i = 0; i < rand; i++)
		{
			int temp = random.nextInt(4);// Give random starting direction to
											// every human
			switch(temp)
			{
				case 0:
					direction = 0;
					break;
				case 1:
					direction = 90;
					break;
				case 2:
					direction = 180;
					break;
				case 3:
					direction = 270;
					break;
			}
			human.add(new Human(random.nextInt(getWidth()), random.nextInt(getHeight()), "human.png", direction));
			human.get(i).edgesCheck();
		}
	}

	public void enemyBulletCreate()// Creation of enemy bullets
	{
		for(int i = 0; i < enemy.size(); i++)
		{
			if(enemy.get(i) instanceof Shooter)
			{
				int direction = 0;
				Random random = new Random();
				int temp = random.nextInt(75);// 1/75 chance every frame of
												// every type 2 enemy to spawn a
												// bullet in a random direction
				if(temp == 1)
				{
					int temp2 = random.nextInt(4);
					switch(temp2)
					{
						case 0:
							direction = 0;
							enemyBullet.add(new Bullet(enemy.get(i).getX() + 16,
									enemy.get(i).getY() + enemy.get(i).getHeight() / 2, direction, "enemybullet.png"));
							break;
						case 1:
							direction = 90;
							enemyBullet.add(new Bullet(enemy.get(i).getX() + enemy.get(i).getWidth() / 2,
									enemy.get(i).getY() - 16, direction, "enemybullet.png"));
							break;
						case 2:
							direction = 180;
							enemyBullet.add(new Bullet(enemy.get(i).getX() - 16,
									enemy.get(i).getY() + enemy.get(i).getHeight() / 2, direction, "enemybullet.png"));
							break;
						case 3:
							direction = 270;
							enemyBullet.add(new Bullet(enemy.get(i).getX() + enemy.get(i).getWidth() / 2,
									enemy.get(i).getY() + 16, direction, "enemybullet.png"));
							break;
					}
				}
			}
		}
	}

	public void bulletDestroyOutside(LinkedList<Bullet> _bullet)// Destroy
																// bullets
																// outside of
																// the screen
	{
		for(int i = _bullet.size() - 1; i >= 0; i--)
		{
			if(_bullet.get(i).checkEdgesOutside())
			{
				_bullet.remove(i);
			}
		}
	}

	public void bulletDestroyAll(LinkedList<Bullet> _bullet)// Destroy all
															// bullets currently
															// in game
	{
		for(int i = _bullet.size() - 1; i >= 0; i--)
		{
			_bullet.remove(i);
		}
	}

	public void enemyDestroyAll()// Destroy all enemies currently in game
	{
		for(int i = enemy.size() - 1; i >= 0; i--)
		{
			enemy.remove(i);
		}
	}

	public void humanDestroyAll()// Destroy all humans currently in game
	{
		for(int i = human.size() - 1; i >= 0; i--)
		{
			human.remove(i);
		}
	}

	public void bulletHitEnemyCheck()// Destroy colliding bullets and enemies
										// and give points to the player
	{
		for(int i = bullet.size() - 1; i >= 0; i--)
		{
			for(int ii = enemy.size() - 1; ii >= 0; ii--)
			{
				if(bullet.get(i).getX() + (bullet.get(i).getWidth() / 2) >= enemy.get(ii).getX()
						&& bullet.get(i).getX() + (bullet.get(i).getWidth() / 2) <= enemy.get(ii).getX()
								+ enemy.get(ii).getWidth()
						&& bullet.get(i).getY() + (bullet.get(i).getHeight() / 2) >= enemy.get(ii).getY()
						&& bullet.get(i).getY() + (bullet.get(i).getHeight() / 2) <= enemy.get(ii).getY()
								+ enemy.get(ii).getHeight()
						&& !(enemy.get(ii) instanceof Lazy))
				{
					if(score < bonusLifeChecker * 25000 && score + 100 >= bonusLifeChecker * 25000
							&& player.getLife() < 5)// Bonus lives are awarded
													// every 25000 points
					{
						bonusLifeChecker++;
						player.setLife(player.getLife() + 1);
					}
					score += 100;
					enemy.remove(ii);
					bullet.remove(i);
					break;
				}
			}
		}
	}

	public void humanHitPlayerCheck()// Check if player saves humans and give
										// points to the player if so
	{
		for(int i = human.size() - 1; i >= 0; i--)
		{
			if(human.get(i).getX() + (human.get(i).getWidth() / 2) >= player.getX()
					&& human.get(i).getX() + (human.get(i).getWidth() / 2) <= player.getX() + player.getWidth()
					&& human.get(i).getY() + (human.get(i).getHeight() / 2) >= player.getY()
					&& human.get(i).getY() + (human.get(i).getHeight() / 2) <= player.getY() + player.getHeight())
			{
				if(score < bonusLifeChecker * 25000 && score + 2000 >= bonusLifeChecker * 25000 && player.getLife() < 5)// Bonus
																														// lives
																														// are
																														// awarded
																														// every
																														// 25000
																														// points
				{
					bonusLifeChecker++;
					player.setLife(player.getLife() + 1);
				}
				score += 2000;
				human.remove(i);
			}
		}
	}

	public boolean enemyHitPlayerCheck()// Check if player has been hit by enemy
	{
		for(int i = enemy.size() - 1; i >= 0; i--)
		{
			if(enemy.get(i).getX() >= player.getX() - 5 && enemy.get(i).getX() <= player.getX() + 5
					&& enemy.get(i).getY() >= player.getY() - 5 && enemy.get(i).getY() <= player.getY() + 5)
			{
				return true;
			}
		}
		return false;
	}

	public boolean enemyBulletHitPlayerCheck()// Check if player has been hit by
												// enemy bullet
	{
		for(int i = enemyBullet.size() - 1; i >= 0; i--)
		{
			if(enemyBullet.get(i).getX() + enemyBullet.get(i).getWidth() / 2 >= player.getX()
					&& enemyBullet.get(i).getX() + enemyBullet.get(i).getWidth() / 2 <= player.getX()
							+ player.getWidth()
					&& enemyBullet.get(i).getY() + enemyBullet.get(i).getHeight() / 2 >= player.getY()
					&& enemyBullet.get(i).getY() + enemyBullet.get(i).getHeight() / 2 <= player.getY()
							+ player.getHeight())
			{
				return true;
			}
		}
		return false;
	}

	public void enemyHitPlayerAvoid()// Make sure player doesn't spawn on an
										// enemy
	{
		Random rand = new Random();
		player.setX(rand.nextInt(getWidth()));
		player.setY(rand.nextInt(getHeight()));
		for(int i = 0; i < enemy.size(); i++)
		{
			if(enemy.get(i).getX() >= player.getX() - 31 && enemy.get(i).getX() <= player.getX() + 31
					&& enemy.get(i).getY() >= player.getY() - 31 && enemy.get(i).getY() <= player.getY() + 31)
			{
				player.setX(rand.nextInt(getWidth()));
				player.setY(rand.nextInt(getHeight()));
				i = 0;
			}
		}
	}

	public void bulletMovement(LinkedList<Bullet> _bullet)// Movement of the
															// bullets in four
															// different
															// directions
	{
		for(int i = 0; i < _bullet.size(); i++)
		{
			switch(_bullet.get(i).getDirection())
			{
				case 0:
					_bullet.get(i).setRelativeX(_bullet.get(i).getSpeed());
					break;
				case 90:
					_bullet.get(i).setRelativeY(-_bullet.get(i).getSpeed());
					break;
				case 180:
					_bullet.get(i).setRelativeX(-_bullet.get(i).getSpeed());
					break;
				case 270:
					_bullet.get(i).setRelativeY(_bullet.get(i).getSpeed());
					break;
			}
		}
	}

	public void humanMovement()// Movement of the humans in four different
								// directions
	{
		for(int i = 0; i < human.size(); i++)
		{
			switch(human.get(i).getDirection())
			{
				case 0:
					human.get(i).setRelativeX(human.get(i).getSpeed());
					break;
				case 90:
					human.get(i).setRelativeY(-human.get(i).getSpeed());
					break;
				case 180:
					human.get(i).setRelativeX(-human.get(i).getSpeed());
					break;
				case 270:
					human.get(i).setRelativeY(human.get(i).getSpeed());
					break;
			}
		}
	}

	public void humanDirectionChange()// Change direction of human movement
	{
		for(int i = 0; i < human.size(); i++)
		{
			int direction = human.get(i).getDirection();
			Random random = new Random();
			int temp = random.nextInt(40);// 1/40 chance every frame of humans
											// to change direction
			if(temp == 1)
			{
				int temp2 = random.nextInt(4);
				switch(temp2)
				{
					case 0:
						direction = 0;
						break;
					case 1:
						direction = 90;
						break;
					case 2:
						direction = 180;
						break;
					case 3:
						direction = 270;
						break;
				}
			}
			human.get(i).setDirection(direction);
		}
	}

	public void enemyDirectionChange()// Change of direction for type 2 enemies
	{
		for(int i = 0; i < enemy.size(); i++)
		{
			if(enemy.get(i) instanceof Shooter)
			{
				int direction = enemy.get(i).getDirection();
				Random random = new Random();
				int temp = random.nextInt(60);// 1/60 chance every frame of
												// enemies to change direction
				if(temp == 1)
				{
					int temp2 = random.nextInt(4);
					switch(temp2)
					{
						case 0:
							direction = 0;
							break;
						case 1:
							direction = 90;
							break;
						case 2:
							direction = 180;
							break;
						case 3:
							direction = 270;
							break;
					}
				}
				enemy.get(i).setDirection(direction);
			}
		}
	}

	public void enemyMovement()// Enemy movement, depending on the enemy type
	{
		for(int i = 0; i < enemy.size(); i++)
		{
			enemy.get(i).updateMovement();
		}
	}

	ImageIcon playerLife = new ImageIcon("playerTiny.png");

	Player player = new Player(0, 0, "player.png");

	LinkedList<Bullet> bullet = new LinkedList<Bullet>();

	LinkedList<Bullet> enemyBullet = new LinkedList<Bullet>();

	LinkedList<Enemy> enemy = new LinkedList<Enemy>();

	LinkedList<Human> human = new LinkedList<Human>();

	int score = 0;

	boolean paintPlayer = false;

	boolean gameOver = false;
	ImageIcon gameOverPic = new ImageIcon("gameover.png");

	private int bonusLifeChecker = 1;
}
