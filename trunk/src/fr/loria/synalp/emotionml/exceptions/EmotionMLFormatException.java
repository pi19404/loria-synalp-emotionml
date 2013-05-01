package fr.loria.synalp.emotionml.exceptions;

/**
 * An EmotionMLFormatException is an EmotionMLException thrown when importing or exporting EmotionML
 * elements that do not comply with the EmotionML recommendation. See <a
 * href="http://www.w3.org/TR/emotionml/" target="_blank">EmotionML</a>.
 * @author Alexandre Denis
 */
public class EmotionMLFormatException extends EmotionMLException
{
	private static final long serialVersionUID = 1L;


	/**
	 * Creates a new EmotionMLFormatException based on given message.
	 * @param message
	 */
	public EmotionMLFormatException(String message)
	{
		super(message);
	}

}
