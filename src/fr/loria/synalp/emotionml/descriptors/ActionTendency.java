package fr.loria.synalp.emotionml.descriptors;

import fr.loria.synalp.emotionml.vocabularies.VocabularyType;

/**
 * An <a href="http://www.w3.org/TR/emotionml/#s2.2.4" target="_blank">ActionTendency in EmotionML</a>.
 * @author Alexandre Denis
 *
 */
public class ActionTendency extends EmotionDescriptor
{

	/**
	 * Creates a new ActionTendency with given name.
	 * @param name the name of the new ActionTendency
	 */
	public ActionTendency(String name)
	{
		super(VocabularyType.ACTION_TENDENCY, name);
	}


	/**
	 * Creates a new ActionTendency with given name and value.
	 * @param name the name of the new ActionTendency
	 * @param value a float value between 0 and 1 (no check is performed at this point, see {@link fr.loria.synalp.emotionml.descriptors.EmotionDescriptor})
	 */
	public ActionTendency(String name, Float value)
	{
		super(VocabularyType.ACTION_TENDENCY, name, value);
	}


	/**
	 * Creates a new ActionTendency with given name and Trace.
	 * @param name the name of the new ActionTendency
	 * @param trace the Trace of the new ActionTendency
	 */
	public ActionTendency(String name, Trace trace)
	{
		super(VocabularyType.ACTION_TENDENCY, name, trace);
	}


	/**
	 * Creates a new ActionTendency by copying the given ActionTendency.
	 * @param actionTendency
	 */
	public ActionTendency(ActionTendency actionTendency)
	{
		super(actionTendency);
	}


	/**
	 * Creates a new ActionTendency by copying the given ActionTendency but setting the given value.
	 * @param actionTendency
	 * @param value a float value between 0 and 1 (no check is performed at this point, see {@link fr.loria.synalp.emotionml.descriptors.EmotionDescriptor})
	 */
	public ActionTendency(ActionTendency actionTendency, Float value)
	{
		super(actionTendency);
		setValue(value);
	}


	/**
	 * Creates a new ActionTendency by copying the given ActionTendency but setting the given Trace.
	 * @param actionTendency
	 * @param trace the Trace of the new ActionTendency
	 */
	public ActionTendency(ActionTendency actionTendency, Trace trace)
	{
		super(actionTendency);
		setTrace(trace);
	}
}
