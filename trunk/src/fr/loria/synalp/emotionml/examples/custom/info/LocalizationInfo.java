package fr.loria.synalp.emotionml.examples.custom.info;

import fr.loria.synalp.emotionml.info.Info;

/**
 * A LocalizationInfo with a value.
 * @author Alexandre Denis
 */
class LocalizationInfo extends Info
{
	private String value;
	public static final String namespace = "http://www.example.com/meta/local/";


	/**
	 * @param value
	 */
	public LocalizationInfo(String value)
	{
		super();
		this.value = value;
	}


	public String getValue()
	{
		return value;
	}


	public void setValue(String value)
	{
		this.value = value;
	}


	@Override
	public String toString()
	{
		return "localization "+value;
	}
}
