package fr.loria.synalp.emotionml.descriptors;

import fr.loria.synalp.emotionml.vocabularies.VocabularyType;

/**
 * An <a href="http://www.w3.org/TR/emotionml/#s2.2.3" target="_blank">Appraisal in EmotionML</a>.
 * @author Alexandre Denis
 *
 */
@SuppressWarnings("serial")
public class Appraisal extends EmotionDescriptor
{

	@SuppressWarnings("unused")
	private Appraisal()
	{
		
	}
	
	/**
	 * Creates a new Appraisal with given name.
	 * @param name the name of the new Appraisal
	 */
	public Appraisal(String name)
	{
		super(VocabularyType.APPRAISAL, name);
	}


	/**
	 * Creates a new Appraisal with given name and value.
	 * @param name the name of the new Appraisal
	 * @param value a float value between 0 and 1 (no check is performed at this point, see {@link fr.loria.synalp.emotionml.descriptors.EmotionDescriptor})
	 */
	public Appraisal(String name, Float value)
	{
		super(VocabularyType.APPRAISAL, name, value);
	}


	/**
	 * Creates a new Appraisal with given name and Trace.
	 * @param name the name of the new Appraisal
	 * @param trace
	 */
	public Appraisal(String name, Trace trace)
	{
		super(VocabularyType.APPRAISAL, name, trace);
	}

	
	/**
	 * Creates a new Appraisal by copying the given Appraisal.
	 * @param appraisal
	 */
	public Appraisal(Appraisal appraisal)
	{
		super(appraisal);
	}


	/**
	 * Creates a new Appraisal by copying the given Appraisal but setting the given value.
	 * @param appraisal
	 * @param value a float value between 0 and 1 (no check is performed at this point, see {@link fr.loria.synalp.emotionml.descriptors.EmotionDescriptor})
	 */
	public Appraisal(Appraisal appraisal, Float value)
	{
		super(appraisal);
		setValue(value);
	}


	/**
	 * Creates a new Appraisal by copying the given Appraisal but setting the given trace.
	 * @param appraisal
	 * @param trace
	 */
	public Appraisal(Appraisal appraisal, Trace trace)
	{
		super(appraisal);
		setTrace(trace);
	}
}
