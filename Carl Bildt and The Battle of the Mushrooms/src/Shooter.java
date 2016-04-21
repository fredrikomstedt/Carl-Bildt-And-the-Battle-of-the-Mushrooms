
public class Shooter extends Enemy
{

	public Shooter( int _x, int _y, String filename, int _direction )
	{
		super( _x, _y, filename );
		direction = _direction;
		setSpeed( 1 );
	}
	
	public void updateMovement()
	{
		switch( getDirection() )
		{
			case 0:
				setRelativeX( getSpeed() );
				break;
			case 90:
				setRelativeY( -getSpeed() );
				break;
			case 180:
				setRelativeX( -getSpeed() );
				break;
			case 270:
				setRelativeY( getSpeed() );
				break;
		}
	}
	
	public int getDirection()
	{
		return direction;
	}
	
	public void setDirection( int _direction )
	{
		direction = _direction;
	}
	
	private int direction;
}
