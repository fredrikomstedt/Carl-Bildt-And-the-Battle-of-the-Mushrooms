public class Human extends GameObject
{
	public Human(int _x, int _y, String filename, int _direction )
	{
		super( _x, _y, filename );
		speed = 1;
		direction = _direction;
	}

	public int getSpeed()
	{
		return speed;
	}
	
	public void setDirection( int _direction )
	{
		direction = _direction;
	}
	
	public int getDirection()
	{
		return direction;
	}
	
	private int speed;
	private int direction;
}
