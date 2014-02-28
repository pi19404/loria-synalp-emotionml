package fr.loria.synalp.emotionml.vocabularies.predefined;
import java.net.URI;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
	Auto-generated class corresponding to vocabulary <a href="http://www.w3.org/TR/emotion-voc/xml#big6" target="_blank"/>http://www.w3.org/TR/emotion-voc/xml#big6</a>
*/
@SuppressWarnings("serial")
public final class Big6 extends Vocabulary
{
	public static final Category DISGUST = new Category("disgust");
	public static final Category SADNESS = new Category("sadness");
	public static final Category ANGER = new Category("anger");
	public static final Category HAPPINESS = new Category("happiness");
	public static final Category SURPRISE = new Category("surprise");
	public static final Category FEAR = new Category("fear");

	static
	{
		try
		{
			DISGUST.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#big6"));
			SADNESS.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#big6"));
			ANGER.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#big6"));
			HAPPINESS.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#big6"));
			SURPRISE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#big6"));
			FEAR.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#big6"));
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public Big6()
	{
		super("big6", VocabularyType.CATEGORY);
		addItem("disgust");
		addItem("sadness");
		addItem("anger");
		addItem("happiness");
		addItem("surprise");
		addItem("fear");
	}
}