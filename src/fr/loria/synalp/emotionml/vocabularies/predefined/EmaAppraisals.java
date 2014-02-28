package fr.loria.synalp.emotionml.vocabularies.predefined;
import java.net.URI;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
	Auto-generated class corresponding to vocabulary <a href="http://www.w3.org/TR/emotion-voc/xml#ema-appraisals" target="_blank"/>http://www.w3.org/TR/emotion-voc/xml#ema-appraisals</a>
*/
@SuppressWarnings("serial")
public final class EmaAppraisals extends Vocabulary
{
	public static final Appraisal EGO_INVOLVEMENT = new Appraisal("ego-involvement");
	public static final Appraisal UNEXPECTEDNESS = new Appraisal("unexpectedness");
	public static final Appraisal RELEVANCE = new Appraisal("relevance");
	public static final Appraisal POWER = new Appraisal("power");
	public static final Appraisal AGENCY = new Appraisal("agency");
	public static final Appraisal CHANGEABILITY = new Appraisal("changeability");
	public static final Appraisal CONTROLLABILITY = new Appraisal("controllability");
	public static final Appraisal LIKELIHOOD = new Appraisal("likelihood");
	public static final Appraisal DESIRABILITY = new Appraisal("desirability");
	public static final Appraisal ADAPTABILITY = new Appraisal("adaptability");
	public static final Appraisal URGENCY = new Appraisal("urgency");
	public static final Appraisal BLAME = new Appraisal("blame");

	static
	{
		try
		{
			EGO_INVOLVEMENT.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#ema-appraisals"));
			UNEXPECTEDNESS.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#ema-appraisals"));
			RELEVANCE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#ema-appraisals"));
			POWER.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#ema-appraisals"));
			AGENCY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#ema-appraisals"));
			CHANGEABILITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#ema-appraisals"));
			CONTROLLABILITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#ema-appraisals"));
			LIKELIHOOD.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#ema-appraisals"));
			DESIRABILITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#ema-appraisals"));
			ADAPTABILITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#ema-appraisals"));
			URGENCY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#ema-appraisals"));
			BLAME.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#ema-appraisals"));
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public EmaAppraisals()
	{
		super("ema-appraisals", VocabularyType.APPRAISAL);
		addItem("ego-involvement");
		addItem("unexpectedness");
		addItem("relevance");
		addItem("power");
		addItem("agency");
		addItem("changeability");
		addItem("controllability");
		addItem("likelihood");
		addItem("desirability");
		addItem("adaptability");
		addItem("urgency");
		addItem("blame");
	}
}