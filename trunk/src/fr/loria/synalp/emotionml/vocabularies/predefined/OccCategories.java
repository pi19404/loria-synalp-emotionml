package fr.loria.synalp.emotionml.vocabularies.predefined;
import java.net.URI;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
	Auto-generated class corresponding to vocabulary <a href="http://www.w3.org/TR/emotion-voc/xml#occ-categories" target="_blank"/>http://www.w3.org/TR/emotion-voc/xml#occ-categories</a>
*/
public final class OccCategories extends Vocabulary
{
	public static final Category PITY = new Category("pity");
	public static final Category SHAME = new Category("shame");
	public static final Category HATE = new Category("hate");
	public static final Category JOY = new Category("joy");
	public static final Category REMORSE = new Category("remorse");
	public static final Category GLOATING = new Category("gloating");
	public static final Category RELIEF = new Category("relief");
	public static final Category RESENTMENT = new Category("resentment");
	public static final Category REPROACH = new Category("reproach");
	public static final Category PRIDE = new Category("pride");
	public static final Category LOVE = new Category("love");
	public static final Category ADMIRATION = new Category("admiration");
	public static final Category DISAPPOINTMENT = new Category("disappointment");
	public static final Category GRATIFICATION = new Category("gratification");
	public static final Category SATISFACTION = new Category("satisfaction");
	public static final Category ANGER = new Category("anger");
	public static final Category FEARS_CONFIRMED = new Category("fears-confirmed");
	public static final Category DISTRESS = new Category("distress");
	public static final Category GRATITUDE = new Category("gratitude");
	public static final Category HAPPY_FOR = new Category("happy-for");
	public static final Category FEAR = new Category("fear");
	public static final Category HOPE = new Category("hope");

	static
	{
		try
		{
			PITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			SHAME.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			HATE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			JOY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			REMORSE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			GLOATING.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			RELIEF.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			RESENTMENT.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			REPROACH.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			PRIDE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			LOVE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			ADMIRATION.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			DISAPPOINTMENT.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			GRATIFICATION.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			SATISFACTION.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			ANGER.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			FEARS_CONFIRMED.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			DISTRESS.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			GRATITUDE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			HAPPY_FOR.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			FEAR.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
			HOPE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-categories"));
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public OccCategories()
	{
		super("occ-categories", VocabularyType.CATEGORY);
		addItem("pity");
		addItem("shame");
		addItem("hate");
		addItem("joy");
		addItem("remorse");
		addItem("gloating");
		addItem("relief");
		addItem("resentment");
		addItem("reproach");
		addItem("pride");
		addItem("love");
		addItem("admiration");
		addItem("disappointment");
		addItem("gratification");
		addItem("satisfaction");
		addItem("anger");
		addItem("fears-confirmed");
		addItem("distress");
		addItem("gratitude");
		addItem("happy-for");
		addItem("fear");
		addItem("hope");
	}
}