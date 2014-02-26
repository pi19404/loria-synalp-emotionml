package fr.loria.synalp.emotionml;

/**
 * An EmotionText is simply a text.
 * @author Alexandre Denis
 */
@SuppressWarnings("serial")
public class EmotionText implements EmotionNode
{
	private String content;


	@SuppressWarnings("unused")
	private EmotionText()
	{

	}


	/**
	 * Creates a new EmotionText from given content.
	 * @param content
	 */
	public EmotionText(String content)
	{
		this.content = content;
	}


	/**
	 * Deep copies the given EmotionText.
	 */
	public EmotionText(EmotionText text)
	{
		this.content = text.getContent();
	}


	/**
	 * Returns the text content of this EmotionText.
	 * @return the content of this EmotionText
	 */
	public String getContent()
	{
		return content;
	}


	/**
	 * Returns a String representation of this EmotionText.
	 */
	@Override
	public String toString()
	{
		return content;
	}

}
