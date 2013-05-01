package fr.loria.synalp.emotionml.descriptors;

import fr.loria.synalp.emotionml.vocabularies.VocabularyType;

/**
 * A <a href="http://www.w3.org/TR/emotionml/#s2.2.1" target="_blank">Category in EmotionML</a>.
 * @author Alexandre Denis
 *
 */
public class Category extends EmotionDescriptor
{

	/**
	 * Creates a new Category with given name.
	 * @param name
	 */
	public Category(String name)
	{
		super(VocabularyType.CATEGORY, name);
	}


	/**
	 * Creates a new Category with given name and value.
	 * @param name
	 * @param value a float value between 0 and 1 (no check is performed at this point, see {@link fr.loria.synalp.emotionml.descriptors.EmotionDescriptor})
	 */
	public Category(String name, Float value)
	{
		super(VocabularyType.CATEGORY, name, value);
	}


	/**
	 * Creates a new Category with given name and Trace.
	 * @param name
	 * @param trace
	 */
	public Category(String name, Trace trace)
	{
		super(VocabularyType.CATEGORY, name, trace);
	}
	
	
	/**
	 * Creates a new Category by copying the given Category.
	 * @param category
	 */
	public Category(Category category)
	{
		super(category);
	}


	/**
	 * Creates a new Category by copying the given Category but setting the given value.
	 * @param category
	 * @param value a float value between 0 and 1 (no check is performed at this point, see {@link fr.loria.synalp.emotionml.descriptors.EmotionDescriptor})
	 */
	public Category(Category category, Float value)
	{
		super(category);
		setValue(value);
	}


	/**
	 * Creates a new Category by copying the given Category but setting the given trace.
	 * @param category
	 * @param trace
	 */
	public Category(Category category, Trace trace)
	{
		super(category);
		setTrace(trace);
	}
}
