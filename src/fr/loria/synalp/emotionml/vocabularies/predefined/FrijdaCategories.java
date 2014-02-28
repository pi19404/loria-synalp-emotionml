package fr.loria.synalp.emotionml.vocabularies.predefined;
import java.net.URI;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
	Auto-generated class corresponding to vocabulary <a href="http://www.w3.org/TR/emotion-voc/xml#frijda-categories" target="_blank"/>http://www.w3.org/TR/emotion-voc/xml#frijda-categories</a>
*/
@SuppressWarnings("serial")
public final class FrijdaCategories extends Vocabulary
{
	public static final Category DISGUST = new Category("disgust");
	public static final Category ENJOYMENT = new Category("enjoyment");
	public static final Category RESIGNATION = new Category("resignation");
	public static final Category DESIRE = new Category("desire");
	public static final Category HUMILITY = new Category("humility");
	public static final Category INTEREST = new Category("interest");
	public static final Category ANGER = new Category("anger");
	public static final Category ARROGANCE = new Category("arrogance");
	public static final Category SURPRISE = new Category("surprise");
	public static final Category INDIFFERENCE = new Category("indifference");
	public static final Category SHOCK = new Category("shock");
	public static final Category FEAR = new Category("fear");

	static
	{
		try
		{
			DISGUST.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-categories"));
			ENJOYMENT.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-categories"));
			RESIGNATION.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-categories"));
			DESIRE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-categories"));
			HUMILITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-categories"));
			INTEREST.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-categories"));
			ANGER.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-categories"));
			ARROGANCE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-categories"));
			SURPRISE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-categories"));
			INDIFFERENCE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-categories"));
			SHOCK.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-categories"));
			FEAR.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-categories"));
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public FrijdaCategories()
	{
		super("frijda-categories", VocabularyType.CATEGORY);
		addItem("disgust");
		addItem("enjoyment");
		addItem("resignation");
		addItem("desire");
		addItem("humility");
		addItem("interest");
		addItem("anger");
		addItem("arrogance");
		addItem("surprise");
		addItem("indifference");
		addItem("shock");
		addItem("fear");
	}
}