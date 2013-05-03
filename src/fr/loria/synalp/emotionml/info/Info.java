package fr.loria.synalp.emotionml.info;

/**
 * A general purpose class that is to be extended.
 * @author Alexandre Denis
 */
public class Info
{
	private String id;


	/**
	 * Creates an info without id.
	 */
	public Info()
	{
		this.id = null;
	}


	/**
	 * @param id
	 */
	public Info(String id)
	{
		this.id = id;
	}


	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}


	/**
	 * Tests if this Info has an id.
	 * @return true if this Info has an id
	 */
	public boolean hasId()
	{
		return id != null;
	}


	@Override
	public String toString()
	{
		if (hasId())
			return id;
		else return super.toString();
	}
}
