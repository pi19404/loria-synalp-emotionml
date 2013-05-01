package fr.loria.synalp.emotionml.vocabularies;

import fr.loria.synalp.emotionml.info.*;

/**
 * A VocabularyItem is an item inside a Vocabulary.
 * @author Alexandre Denis
 */
public final class VocabularyItem extends InfoCarrier
{
	private final String name;


	/**
	 * @param name
	 */
	public VocabularyItem(String name)
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


	@Override
	public String toString()
	{
		if (hasInfo())
			return getInfo() + " " + name;
		else return name;
	}
}
