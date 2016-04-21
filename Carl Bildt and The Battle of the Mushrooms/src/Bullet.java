public class Bullet extends GameObject
{
	public Bullet( int _x, int _y, int _direction, String filename )
	{
		super( _x, _y, filename );
		speed = 15;
		direction = _direction;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public int getDirection()
	{
		return direction;
	}
	
	public boolean checkEdgesOutside()//Check if the bullet is outside the room
	{
		if( getX() + getWidth() < 0 )
		{
			return true;
		}
		else
		if( getX() > Robotron.area.getWidth() )
		{
			return true;
		}
		else
		if( getY() + getHeight() < 35 )
		{
			return true;
		}
		else
		if( getY() > Robotron.area.getHeight() )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private int speed;
	private int direction;
}

