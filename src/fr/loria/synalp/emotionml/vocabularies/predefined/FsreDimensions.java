package fr.loria.synalp.emotionml.vocabularies.predefined;
import java.net.URI;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
	Auto-generated class corresponding to vocabulary <a href="http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions" target="_blank"/>http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions</a>
*/
@SuppressWarnings("serial")
public final class FsreDimensions extends Vocabulary
{
	public static final Dimension UNPREDICTABILITY = new Dimension("unpredictability", 1.0f);
	public static final Dimension AROUSAL = new Dimension("arousal", 1.0f);
	public static final Dimension POTENCY = new Dimension("potency", 1.0f);
	public static final Dimension VALENCE = new Dimension("valence", 1.0f);

	static
	{
		try
		{
			UNPREDICTABILITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions"));
			AROUSAL.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions"));
			POTENCY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions"));
			VALENCE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions"));
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public FsreDimensions()
	{
		super("fsre-dimensions", VocabularyType.DIMENSION);
		addItem("unpredictability");
		addItem("arousal");
		addItem("potency");
		addItem("valence");
	}
}