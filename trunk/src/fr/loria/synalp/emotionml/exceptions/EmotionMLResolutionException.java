package fr.loria.synalp.emotionml.exceptions;

/**
 * An EmotionMLResolutionException is an EmotionMLException thrown when importing or exporting
 * EmotionML emotions that refer to vocabularies that cannot be resolved. See <a
 * href="http://www.w3.org/TR/emotionml/" target="_blank">EmotionML</a>.
 * @author Alexandre Denis
 */
public class EmotionMLResolutionException extends EmotionMLException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Creates a new EmotionMLResolutionException based on given message.
	 * @param message
	 */
	public EmotionMLResolutionException(String message)
	{
		super(message);
	}
}
