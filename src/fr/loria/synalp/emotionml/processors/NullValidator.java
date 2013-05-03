package fr.loria.synalp.emotionml.processors;

import org.w3c.dom.Element;

import fr.loria.synalp.emotionml.exceptions.EmotionMLValidationException;

/**
 * A NullValidator does not perform validation at all. This class is intended to be used for debugging purposes.
 * @author Alexandre Denis
 */
public class NullValidator extends EmotionMLValidator
{
	/**
	 * Has no effect.
	 */
	@Override
	public Element validateDocument(Element element) throws EmotionMLValidationException
	{
		return element;
	}


	/**
	 * Has no effect.
	 */
	@Override
	public Element validateEmotion(Element element) throws EmotionMLValidationException
	{
		return element;
	}
}
