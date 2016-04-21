import java.util.Random;

public class Enemy extends GameObject
{
	public Enemy( int _x, int _y, String filename )
	{
		super( _x, _y, filename );
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public void setSpeed( int _speed )
	{
		speed = _speed;
	}
	
	public void updateMovement()//Not actually used by Enemy class, used for subclasses
	{
		
	}
	
	public int getDirection()//Not actually used by Enemy class, used for subclass Shooter
	{
		return 0;
	}
	
	public void setDirection( int _direction )//Not actually used by Enemy class, used for subclass Shooter
	{
		
	}
	
	private int speed;
}
