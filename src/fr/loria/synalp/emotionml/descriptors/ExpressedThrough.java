package fr.loria.synalp.emotionml.descriptors;

/**
 * ExpressedThrough describes the modality through which an emotion is produced. It is either
 * represented with a Type, that provides a convenient enumeration of the most common modalities or
 * as a Value which is an open value. See <a href="http://www.w3.org/TR/emotionml/#s2.3.2"
 * target="_blank">Expressed-Through in EmotionML</a>.
 * @author Alexandre Denis
 */
public class ExpressedThrough
{
	private final String value;
	private final Type type;


	/**
	 * The Type of modality.
	 * @author Alexandre Denis
	 */
	public enum Type
	{
		GAZE("gaze"),
		FACE("face"),
		HEAD("head"),
		TORSO("torso"),
		GESTURE("gesture"),
		LEG("leg"),
		VOICE("voice"),
		TEXT("text"),
		LOCOMOTION("locomotion"),
		POSTURE("posture"),
		PHYSIOLOGY("physiology");

		private String str;


		private Type(String str)
		{
			this.str = str;
		}


		/**
		 * Returns a xsd:NMTOKEN value corresponding to this Type.
		 */
		@Override
		public String toString()
		{
			return str;
		}
	}


	/**
	 * Parses the given String as an ExpressedThrough. If the given String corresponds to a known
	 * type, use it, else use an open value.
	 * @param string
	 * @return an ExpressedThrough whose token value corresponds to given String
	 */
	public static ExpressedThrough parse(String string)
	{
		for(Type type : Type.values())
			if (type.toString().equals(string))
				return new ExpressedThrough(type);
		return new ExpressedThrough(string);
	}


	/**
	 * Creates a new ExpressedThrough with given type.
	 * @param type
	 */
	public ExpressedThrough(Type type)
	{
		this.type = type;
		this.value = null;
	}


	/**
	 * Creates a new ExpressedThrough with given open value. When possible, it is preferrable to use
	 * the Type enumeration.
	 * @param value
	 */
	public ExpressedThrough(String value)
	{
		this.type = null;
		this.value = value;
	}


	/**
	 * Deep copies the given ExpressedThrough.
	 */
	public ExpressedThrough(ExpressedThrough expressedThrough)
	{
		this.type = expressedThrough.type;
		this.value = expressedThrough.value;
	}


	/**
	 * Returns the Type of this ExpressedThrough if it has been defined.
	 * @return the Type of this ExpressedThrough or null if it has not been defined
	 */
	public Type getType()
	{
		return type;
	}


	/**
	 * Returns the value of this ExpressedThrough if it has been defined.
	 * @return the value of this ExpressedThrough or null if it has not been defined
	 */
	public String getValue()
	{
		return value;
	}


	/**
	 * Returns a String value of this ExpressedThrough.
	 * @return the type value if defined, else the open value if defined or the constant value
	 *         "other"
	 */
	@Override
	public String toString()
	{
		if (type == null)
		{
			if (value == null)
				return "other";
			else return value;
		}
		else return type.toString();
	}

}
