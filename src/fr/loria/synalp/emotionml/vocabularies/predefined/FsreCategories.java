package fr.loria.synalp.emotionml.vocabularies.predefined;
import java.net.URI;

import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
	Auto-generated class corresponding to vocabulary <a href="http://www.w3.org/TR/emotion-voc/xml#fsre-categories" target="_blank"/>http://www.w3.org/TR/emotion-voc/xml#fsre-categories</a>
*/
@SuppressWarnings("serial")
public final class FsreCategories extends Vocabulary
{
	public static final Category DESPAIR = new Category("despair");
	public static final Category SHAME = new Category("shame");
	public static final Category HATE = new Category("hate");
	public static final Category CONTENTMENT = new Category("contentment");
	public static final Category SADNESS = new Category("sadness");
	public static final Category JEALOUSY = new Category("jealousy");
	public static final Category JOY = new Category("joy");
	public static final Category ANXIETY = new Category("anxiety");
	public static final Category CONTEMPT = new Category("contempt");
	public static final Category STRESS = new Category("stress");
	public static final Category PLEASURE = new Category("pleasure");
	public static final Category HAPPINESS = new Category("happiness");
	public static final Category COMPASSION = new Category("compassion");
	public static final Category PRIDE = new Category("pride");
	public static final Category LOVE = new Category("love");
	public static final Category DISAPPOINTMENT = new Category("disappointment");
	public static final Category DISGUST = new Category("disgust");
	public static final Category IRRITATION = new Category("irritation");
	public static final Category INTEREST = new Category("interest");
	public static final Category ANGER = new Category("anger");
	public static final Category SURPRISE = new Category("surprise");
	public static final Category BEING_HURT = new Category("being-hurt");
	public static final Category GUILT = new Category("guilt");
	public static final Category FEAR = new Category("fear");

	static
	{
		try
		{
			DESPAIR.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			SHAME.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			HATE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			CONTENTMENT.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			SADNESS.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			JEALOUSY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			JOY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			ANXIETY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			CONTEMPT.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			STRESS.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			PLEASURE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			HAPPINESS.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			COMPASSION.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			PRIDE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			LOVE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			DISAPPOINTMENT.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			DISGUST.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			IRRITATION.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			INTEREST.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			ANGER.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			SURPRISE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			BEING_HURT.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			GUILT.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
			FEAR.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#fsre-categories"));
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public FsreCategories()
	{
		super("fsre-categories", VocabularyType.CATEGORY);
		addItem("despair");
		addItem("shame");
		addItem("hate");
		addItem("contentment");
		addItem("sadness");
		addItem("jealousy");
		addItem("joy");
		addItem("anxiety");
		addItem("contempt");
		addItem("stress");
		addItem("pleasure");
		addItem("happiness");
		addItem("compassion");
		addItem("pride");
		addItem("love");
		addItem("disappointment");
		addItem("disgust");
		addItem("irritation");
		addItem("interest");
		addItem("anger");
		addItem("surprise");
		addItem("being-hurt");
		addItem("guilt");
		addItem("fear");
	}
}