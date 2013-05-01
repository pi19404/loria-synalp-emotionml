package fr.loria.synalp.emotionml.vocabularies.predefined;
import java.net.URI;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
	Auto-generated class corresponding to vocabulary <a href="http://www.w3.org/TR/emotion-voc/xml#everyday-categories" target="_blank"/>http://www.w3.org/TR/emotion-voc/xml#everyday-categories</a>
*/
public final class EverydayCategories extends Vocabulary
{
	public static final Category SATISFIED = new Category("satisfied");
	public static final Category SAD = new Category("sad");
	public static final Category BORED = new Category("bored");
	public static final Category ANGRY = new Category("angry");
	public static final Category EXCITED = new Category("excited");
	public static final Category AMUSED = new Category("amused");
	public static final Category LOVING = new Category("loving");
	public static final Category INTERESTED = new Category("interested");
	public static final Category DISAPPOINTED = new Category("disappointed");
	public static final Category CONTENT = new Category("content");
	public static final Category AFRAID = new Category("afraid");
	public static final Category WORRIED = new Category("worried");
	public static final Category RELAXED = new Category("relaxed");
	public static final Category CONFIDENT = new Category("confident");
	public static final Category HAPPY = new Category("happy");
	public static final Category AFFECTIONATE = new Category("affectionate");
	public static final Category PLEASED = new Category("pleased");

	static
	{
		try
		{
			SATISFIED.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			SAD.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			BORED.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			ANGRY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			EXCITED.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			AMUSED.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			LOVING.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			INTERESTED.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			DISAPPOINTED.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			CONTENT.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			AFRAID.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			WORRIED.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			RELAXED.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			CONFIDENT.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			HAPPY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			AFFECTIONATE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
			PLEASED.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#everyday-categories"));
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public EverydayCategories()
	{
		super("everyday-categories", VocabularyType.CATEGORY);
		addItem("satisfied");
		addItem("sad");
		addItem("bored");
		addItem("angry");
		addItem("excited");
		addItem("amused");
		addItem("loving");
		addItem("interested");
		addItem("disappointed");
		addItem("content");
		addItem("afraid");
		addItem("worried");
		addItem("relaxed");
		addItem("confident");
		addItem("happy");
		addItem("affectionate");
		addItem("pleased");
	}
}