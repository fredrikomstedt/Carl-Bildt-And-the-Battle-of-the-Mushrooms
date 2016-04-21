import javax.swing.JFrame;


/*********************************************/
/*CONTROLS:                                  */
/*MOVE WITH ARROW KEYS                       */
/*SHOOT WITH WASD                            */
/*********************************************/
public class Robotron extends JFrame
{
	public Robotron()//Creates an area for the game
	{
		setSize( 800, 600 );
		area = new GameArea();
		add( area );
		setTitle( "Carl Bildt and The Battle of the Mushrooms" );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocationRelativeTo( null );
		setVisible( true );
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		new Robotron();
		
		int shootingTimer = 0;
		boolean levelStart = false;
		
		while( true )
		{
			boolean playerHit = false;
			while( area.player.getLife() > 0 )//Play if player isn't dead
			{
				Thread.sleep( 1000/60 );//To maintain 60 frames per second
				
				if( !levelStart )//If a new level is about to begin
				{
					startLevel();
					levelStart = true;
				}
				
				//Check whether the level is complete and it's time for the next level, AKA when all enemies that can be killed are dead
				if( nextLevel() )
				{
					break;
				}
				
				//Destroy bullets outside the screen to make game smoother
				area.bulletDestroyOutside( area.bullet );
				area.bulletDestroyOutside( area.enemyBullet );
				
				//Collision checking for humans, enemies, bullets and the player
				area.humanHitPlayerCheck();
				area.bulletHitEnemyCheck();
				//"restart" level when player is hit
				if( area.enemyHitPlayerCheck() || area.enemyBulletHitPlayerCheck() )
				{
					restartLevel();
					playerHit = true;
					break;
				}
				
				shootingTimer = bulletCreation( shootingTimer );//Creation of both enemy and friendly bullets, using a timer to make sure player doesn't shoot too fast
				
				movement();//Movement for bullets, humans, enemies and the player, as well as checking if they've moved outside the screen
				
				area.repaint();//Put everything done this frame on the screen before going to the next frame
			}
			
			if( area.player.getLife() == 0 )//Initiate Game Over
			{
				break;
			}
			else
			if( !playerHit )//Time for new level
			{
				levelStart = false;
				area.paintPlayer = false;
				wave++;
			}
		}
		
		gameOver();
	}
	
	public static void startLevel() throws InterruptedException
	{
		//Destroy everything from the previous level
		area.enemyDestroyAll();
		area.bulletDestroyAll( area.enemyBullet );
		area.bulletDestroyAll( area.bullet );
		area.humanDestroyAll();
		
		//Creation of all different kinds of enemies, depending on which wave it is
		if( wave > 3 )
		{
			area.enemyCreate( wave, 1 );
		}
		if( wave > 7 )
		{
			area.enemyCreate( wave, 2 );
		}
		area.enemyCreate( wave, 0 );
		//Make sure the enemies aren't outside the screen.
		for( int i = 0; i < area.enemy.size(); i++ )
		{
			area.enemy.get(i).edgesCheck();
		}
		
		area.humanCreate( wave );//Creation of humans.
		
		//Show it to the player and give two seconds to see how the wave is distributed
		area.repaint();
		Thread.sleep( 2000 );
		
		//spawn the player and give him/her the opportunity to look for 1 second on how to play, before the wave starts
		area.enemyHitPlayerAvoid();
		area.player.edgesCheck();
		area.paintPlayer = true;
		area.repaint();
		Thread.sleep( 1000 );
	}
	
	public static boolean nextLevel()
	{
		for( int i = 0; i < area.enemy.size(); i++ )
		{
			if( area.enemy.get(i) instanceof Grunt || area.enemy.get(i) instanceof Shooter )
			{
				return false;
			}
		}
		
		return true;
	}
	
	public static void restartLevel() throws InterruptedException
	{
		area.player.setLife( area.player.getLife() - 1 );//Lower the health of the player by 1
		
		area.bulletDestroyAll( area.bullet );//Destroy bullets so they don't cause any harm right when the wave restarts 
		area.bulletDestroyAll( area.enemyBullet );//(Unfortunate to be hit by a bullet right when you've respawned)
		area.enemyHitPlayerAvoid();//Respawn the player and give another second to look on how to play
		area.paintPlayer = true;
		area.player.edgesCheck();
		area.repaint();
		Thread.sleep( 1000 );
	}
	
	public static int bulletCreation( int shootingTimer )
	{
		area.enemyBulletCreate();
		if( shootingTimer == 0 )//Timer to make sure bullets are shot from player in a constant flow, yet not all the time. Enemy bullets are shot randomly
		{
			Listener.updateBulletShooting();//Shooting controlled by key presses
			shootingTimer++;
			return shootingTimer;
		}
		else
		if( shootingTimer == 8 )
		{
			shootingTimer = 0;
			return shootingTimer;
		}
		else
		{
			shootingTimer++;
			return shootingTimer;
		}
	}
	
	public static void movement()
	{
		area.bulletMovement( area.bullet );
		area.bulletMovement( area.enemyBullet );
		area.humanDirectionChange();
		area.humanMovement();
		for( int i = 0; i < area.human.size(); i++ )
		{
			area.human.get(i).edgesCheck();
		}
		area.enemyDirectionChange();
		area.enemyMovement();
		for( int i = 0; i < area.enemy.size(); i++ )
		{
			area.enemy.get(i).edgesCheck();
		}
		Listener.updateKeyPress();//Player movement controlled by key presses
		area.player.edgesCheck();
	}
	
	public static void gameOver()
	{
		area.paintPlayer = false;//Destroy everything and show Game Over picture
		area.enemyDestroyAll();
		area.humanDestroyAll();
		area.bulletDestroyAll( area.bullet );
		area.bulletDestroyAll( area.enemyBullet );
		area.gameOver = true;
		area.repaint();
	}
	
	static int wave = 1;
	static GameArea area;
}
