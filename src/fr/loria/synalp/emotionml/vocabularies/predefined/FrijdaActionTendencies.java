package fr.loria.synalp.emotionml.vocabularies.predefined;
import java.net.URI;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
	Auto-generated class corresponding to vocabulary <a href="http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies" target="_blank"/>http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies</a>
*/
public final class FrijdaActionTendencies extends Vocabulary
{
	public static final ActionTendency REJECTING = new ActionTendency("rejecting");
	public static final ActionTendency AGONISTIC = new ActionTendency("agonistic");
	public static final ActionTendency SUBMITTING = new ActionTendency("submitting");
	public static final ActionTendency DOMINATING = new ActionTendency("dominating");
	public static final ActionTendency APPROACH = new ActionTendency("approach");
	public static final ActionTendency NONATTENDING = new ActionTendency("nonattending");
	public static final ActionTendency ATTENDING = new ActionTendency("attending");
	public static final ActionTendency INTERRUPTING = new ActionTendency("interrupting");
	public static final ActionTendency BEING_WITH = new ActionTendency("being-with");
	public static final ActionTendency AVOIDANCE = new ActionTendency("avoidance");

	static
	{
		try
		{
			REJECTING.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies"));
			AGONISTIC.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies"));
			SUBMITTING.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies"));
			DOMINATING.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies"));
			APPROACH.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies"));
			NONATTENDING.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies"));
			ATTENDING.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies"));
			INTERRUPTING.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies"));
			BEING_WITH.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies"));
			AVOIDANCE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#frijda-action-tendencies"));
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public FrijdaActionTendencies()
	{
		super("frijda-action-tendencies", VocabularyType.ACTION_TENDENCY);
		addItem("rejecting");
		addItem("agonistic");
		addItem("submitting");
		addItem("dominating");
		addItem("approach");
		addItem("nonattending");
		addItem("attending");
		addItem("interrupting");
		addItem("being-with");
		addItem("avoidance");
	}
}