package fr.loria.synalp.emotionml.exceptions;

/**
 * The super class for all EmotionML exceptions.
 * @author Alexandre Denis
 *
 */
public class EmotionMLException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public EmotionMLException(String message)
	{
		super(message);
	}
}
