package fr.loria.synalp.emotionml.vocabularies.predefined;
import java.net.URI;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
	Auto-generated class corresponding to vocabulary <a href="http://www.w3.org/TR/emotion-voc/xml#pad-dimensions" target="_blank"/>http://www.w3.org/TR/emotion-voc/xml#pad-dimensions</a>
*/
public final class PadDimensions extends Vocabulary
{
	public static final Dimension DOMINANCE = new Dimension("dominance", 1.0f);
	public static final Dimension AROUSAL = new Dimension("arousal", 1.0f);
	public static final Dimension PLEASURE = new Dimension("pleasure", 1.0f);

	static
	{
		try
		{
			DOMINANCE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#pad-dimensions"));
			AROUSAL.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#pad-dimensions"));
			PLEASURE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#pad-dimensions"));
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public PadDimensions()
	{
		super("pad-dimensions", VocabularyType.DIMENSION);
		addItem("dominance");
		addItem("arousal");
		addItem("pleasure");
	}
}