package fr.loria.synalp.emotionml.vocabularies.predefined;
import java.net.URI;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
	Auto-generated class corresponding to vocabulary <a href="http://www.w3.org/TR/emotion-voc/xml#intensity-dimension" target="_blank"/>http://www.w3.org/TR/emotion-voc/xml#intensity-dimension</a>
*/
@SuppressWarnings("serial")
public final class IntensityDimension extends Vocabulary
{
	public static final Dimension INTENSITY = new Dimension("intensity", 1.0f);

	static
	{
		try
		{
			INTENSITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#intensity-dimension"));
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public IntensityDimension()
	{
		super("intensity-dimension", VocabularyType.DIMENSION);
		addItem("intensity");
	}
}