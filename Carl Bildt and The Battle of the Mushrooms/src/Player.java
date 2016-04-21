
public class Player extends GameObject
{
	public Player(int _x, int _y, String filename)
	{
		super(_x, _y, filename);
		speed = 3;
		life = 3;
	}

	public int getSpeed()
	{
		return speed;
	}

	public int getLife()
	{
		return life;
	}

	public void setLife(int _life)
	{
		life = _life;
	}

	private int speed;
	private int life;
}
