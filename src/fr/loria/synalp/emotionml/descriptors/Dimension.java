package fr.loria.synalp.emotionml.descriptors;

import fr.loria.synalp.emotionml.vocabularies.VocabularyType;

/**
 * A <a href="http://www.w3.org/TR/emotionml/#s2.2.2" target="_blank">Dimension in EmotionML</a>.
 * @author Alexandre Denis
 */
public class Dimension extends EmotionDescriptor
{

	/**
	 * Creates a new Dimension with given name and value.
	 * @param name
	 * @param value a float value between 0 and 1 (no check is performed at this point, see {@link fr.loria.synalp.emotionml.descriptors.EmotionDescriptor})
	 */
	public Dimension(String name, Float value)
	{
		super(VocabularyType.DIMENSION, name, value);
	}


	/**
	 * Creates a new Dimension with given name and Trace.
	 * @param name
	 * @param trace
	 */
	public Dimension(String name, Trace trace)
	{
		super(VocabularyType.DIMENSION, name, trace);
	}


	/**
	 * Creates a new Dimension by copying the given Dimension.
	 * @param dimension
	 */
	public Dimension(Dimension dimension)
	{
		super(dimension);
	}


	/**
	 * Creates a new Dimension by copying the given Dimension but setting the given value.
	 * @param dimension
	 * @param value a float value between 0 and 1 (no check is performed at this point, see {@link fr.loria.synalp.emotionml.descriptors.EmotionDescriptor})
	 */
	public Dimension(Dimension dimension, Float value)
	{
		super(dimension);
		setValue(value);
	}


	/**
	 * Creates a new Dimension by copying the given Dimension but setting the given trace.
	 * @param dimension
	 * @param trace
	 */
	public Dimension(Dimension dimension, Trace trace)
	{
		super(dimension);
		setTrace(trace);
	}
}
