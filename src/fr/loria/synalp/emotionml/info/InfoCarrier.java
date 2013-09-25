package fr.loria.synalp.emotionml.info;

/**
 * An InfoCarrier is an Object that can carry an Info.
 * @author Alexandre Denis
 */
public class InfoCarrier
{
	private Info info;


	/**
	 * Creates an empty InfoCarrier.
	 */
	public InfoCarrier()
	{

	}


	/**
	 * Deep copies the given InfoCarrier. It uses the {@link Info#copy() copy} method of the Info
	 * class.
	 */
	public InfoCarrier(InfoCarrier carrier)
	{
		this.info = carrier.hasInfo() ? carrier.getInfo().copy() : null;
	}


	/**
	 * Tests if this InfoCarrier has an Info.
	 * @return true if this InfoCarrier has an Info
	 */
	public boolean hasInfo()
	{
		return info != null;
	}


	/**
	 * Sets the Info of this InfoCarrier.
	 * @param info
	 * @return this InfoCarrier for chaining
	 */
	public InfoCarrier setInfo(Info info)
	{
		this.info = info;
		return this;
	}


	/**
	 * Returns the Info of this InfoCarrier.
	 * @return the Info
	 */
	public Info getInfo()
	{
		return info;
	}

}
