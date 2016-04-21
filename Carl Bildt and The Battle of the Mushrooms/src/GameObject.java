import javax.swing.ImageIcon;

public class GameObject
{
	public GameObject( int _x, int _y, String filename )
	{
		x = _x;
		y = _y;
		image = new ImageIcon( filename );
		width = image.getIconWidth();
		height = image.getIconHeight();
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public ImageIcon getImage()
	{
		return image;
	}
	
	public void setX( int _x )
	{
		x = _x;
	}
	
	public void setY( int _y )
	{
		y = _y;
	}
	
	public void setRelativeX( int _x )
	{
		x += _x;
	}
	
	public void setRelativeY( int _y )
	{
		y += _y;
	}
	
	public void setImage( String filename )
	{
		image = new ImageIcon( filename );
		width = image.getIconWidth();
		height = image.getIconHeight();
	}
	
	public void setImage( ImageIcon _image )
	{
		image = _image;
		width = image.getIconWidth();
		height = image.getIconHeight();
	}
	
	public void edgesCheck()//Check if object is outside the room and if so, put it next to the edge of the room
	{
		if( x < 0 )
		{
			x = 0;
		}
		if( x > Robotron.area.getWidth() - width )
		{
			x = Robotron.area.getWidth() - width;
		}
		if( y < 35 )
		{
			y = 35;
		}
		if( y > Robotron.area.getHeight() - height )
		{
			y = Robotron.area.getHeight() - height;
		}
	}
	
	private int x;
	private int y;
	
	private ImageIcon image;
	
	private int width;
	private int height;
}
