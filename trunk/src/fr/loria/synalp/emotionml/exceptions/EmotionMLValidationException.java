package fr.loria.synalp.emotionml.exceptions;

import fr.loria.synalp.emotionml.processors.ValidationResult;

/**
 * An EmotionMLValidationException is an EmotionMLException thrown when importing or exporting
 * EmotionML elements that cannot pass the validations. It holds a ValidationResult to retrieve the
 * problem more specifically. See <a href="http://www.w3.org/TR/emotionml/"
 * target="_blank">EmotionML</a>.
 * @author Alexandre Denis
 * @see fr.loria.synalp.emotionml.processors.ValidationResult
 */
public class EmotionMLValidationException extends EmotionMLException
{
	private static final long serialVersionUID = 1L;
	private ValidationResult result;


	/**
	 * Creates a new EmotionMLValidationException based on given ValidationResult.
	 * @param result
	 */
	public EmotionMLValidationException(ValidationResult result)
	{
		super(result.toString());
		this.result = result;
	}


	/**
	 * Returns the ValidationResult of this EmotionMLValidationException.
	 * @return a ValidationResult
	 */
	public ValidationResult getValidationResult()
	{
		return result;
	}
}
