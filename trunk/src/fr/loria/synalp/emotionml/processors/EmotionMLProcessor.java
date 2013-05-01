package fr.loria.synalp.emotionml.processors;

/**
 * An EmotionMLProcessor is a basic class that holds a reference to an EmotionMLValidator. It is
 * used to subclass EmotionMLExporter and EmotionMLImporter.
 * @author Alexandre Denis
 */
public class EmotionMLProcessor
{
	private EmotionMLValidator validator;


	/**
	 * Creates a new EmotionMLProcessor based on a default EmotionMLValidator.
	 */
	public EmotionMLProcessor()
	{
		validator = new EmotionMLValidator();
	}


	/**
	 * Creates a new EmotionMLProcessor based on the given EmotionMLValidator.
	 */
	public EmotionMLProcessor(EmotionMLValidator validator)
	{
		this.validator = validator;
	}


	/**
	 * Sets the EmotionMLValidator of this EmotionMLProcessor.
	 * @param validator
	 */
	public void setValidator(EmotionMLValidator validator)
	{
		this.validator = validator;
	}

	/**
	 * Returns the EmotionMLValidator of this EmotionMLProcessor.
	 * @return an EmotionMLValidator
	 */
	public EmotionMLValidator getValidator()
	{
		return validator;
	}

}
