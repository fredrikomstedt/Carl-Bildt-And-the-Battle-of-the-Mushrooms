
public class Grunt extends Enemy
{

	public Grunt( int _x, int _y, String filename )
	{
		super( _x, _y, filename );
		setSpeed( 1 );
	}
	
	public void updateMovement()
	{
		if( Robotron.area.player.getX() > getX() )
		{
			setRelativeX( getSpeed() );
		}
		else
		if( Robotron.area.player.getX() < getX() )
		{
			setRelativeX( -getSpeed() );
		}
		if( Robotron.area.player.getY() > getY() )
		{
			setRelativeY( getSpeed() );
		}
		else
		if( Robotron.area.player.getY() < getY() )
		{
			setRelativeY( -getSpeed() );
		}
	}
	
}
