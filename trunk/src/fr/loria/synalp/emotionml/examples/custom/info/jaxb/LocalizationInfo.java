package fr.loria.synalp.emotionml.examples.custom.info.jaxb;

import javax.xml.bind.annotation.*;

import fr.loria.synalp.emotionml.info.Info;

/**
 * A LocalizationInfo with a value.
 * @author Alexandre Denis
 */
@XmlRootElement(name = "localization", namespace = "http://www.example.com/meta/local/")
@XmlAccessorType(XmlAccessType.FIELD)
class LocalizationInfo extends Info
{
	@XmlAttribute
	private String value;


	/**
	 * Empty constructor needed by JAXB.
	 */
	public LocalizationInfo()
	{

	}


	/**
	 * @param value
	 */
	public LocalizationInfo(String value)
	{
		super();
		this.value = value;
	}


	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}


	@Override
	public String toString()
	{
		return "localization " + value;
	}
}
