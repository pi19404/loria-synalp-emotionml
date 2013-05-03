package fr.loria.synalp.emotionml.examples.custom.info.dom;

import fr.loria.synalp.emotionml.info.Info;

/**
 * A Classifier Info with a name.
 * @author Alexandre Denis
 */
class ClassifierInfo extends Info
{
	private String name;
	public static final String namespace = "http://www.example.com/meta/classify/";


	/**
	 * @param name
	 */
	public ClassifierInfo(String name)
	{
		super();
		this.name = name;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}
	
	
	@Override
	public String toString()
	{
		return "classifier "+name;
	}
}
