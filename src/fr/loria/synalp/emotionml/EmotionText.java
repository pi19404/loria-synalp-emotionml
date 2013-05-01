package fr.loria.synalp.emotionml;


/**
 * An EmotionText is simply a text.
 * @author Alexandre Denis
 */
public class EmotionText implements EmotionNode
{
	private String content;


	/**
	 * Creates a new EmotionText from given content.
	 * @param content
	 */
	public EmotionText(String content)
	{
		this.content = content;
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
