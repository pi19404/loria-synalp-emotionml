package fr.loria.synalp.emotionml.examples.custom.info.jaxb;

import javax.xml.bind.annotation.*;

import fr.loria.synalp.emotionml.info.Info;

/**
 * A Classifier Info with a name.
 * @author Alexandre Denis
 */
@XmlRootElement(name = "classifier", namespace = "http://www.example.com/meta/classify/")
@XmlAccessorType(XmlAccessType.FIELD)
class ClassifierInfo extends Info
{
	@XmlAttribute
	private String name;

	/**
	 * Empty constructor needed by JAXB.
	 */
	public ClassifierInfo()
	{

	}


	/**
	 * @param name
	 */
	public ClassifierInfo(String name)
	{
		this.name = name;
	}


	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}


	@Override
	public String toString()
	{
		return "classifier " + name;
	}
}
