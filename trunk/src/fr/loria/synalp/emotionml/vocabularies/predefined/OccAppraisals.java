package fr.loria.synalp.emotionml.vocabularies.predefined;
import java.net.URI;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
	Auto-generated class corresponding to vocabulary <a href="http://www.w3.org/TR/emotion-voc/xml#occ-appraisals" target="_blank"/>http://www.w3.org/TR/emotion-voc/xml#occ-appraisals</a>
*/
@SuppressWarnings("serial")
public final class OccAppraisals extends Vocabulary
{
	public static final Appraisal STRENGTH_OF_IDENTIFICATION = new Appraisal("strength-of-identification");
	public static final Appraisal DESIRABILITY_FOR_OTHER = new Appraisal("desirability-for-other");
	public static final Appraisal APPEALINGNESS = new Appraisal("appealingness");
	public static final Appraisal FAMILIARITY = new Appraisal("familiarity");
	public static final Appraisal EXPECTATION_OF_DEVIATION = new Appraisal("expectation-of-deviation");
	public static final Appraisal EFFORT = new Appraisal("effort");
	public static final Appraisal PRAISEWORTHINESS = new Appraisal("praiseworthiness");
	public static final Appraisal LIKING = new Appraisal("liking");
	public static final Appraisal DESERVINGNESS = new Appraisal("deservingness");
	public static final Appraisal LIKELIHOOD = new Appraisal("likelihood");
	public static final Appraisal DESIRABILITY = new Appraisal("desirability");

	static
	{
		try
		{
			STRENGTH_OF_IDENTIFICATION.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-appraisals"));
			DESIRABILITY_FOR_OTHER.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-appraisals"));
			APPEALINGNESS.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-appraisals"));
			FAMILIARITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-appraisals"));
			EXPECTATION_OF_DEVIATION.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-appraisals"));
			EFFORT.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-appraisals"));
			PRAISEWORTHINESS.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-appraisals"));
			LIKING.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-appraisals"));
			DESERVINGNESS.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-appraisals"));
			LIKELIHOOD.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-appraisals"));
			DESIRABILITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#occ-appraisals"));
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public OccAppraisals()
	{
		super("occ-appraisals", VocabularyType.APPRAISAL);
		addItem("strength-of-identification");
		addItem("desirability-for-other");
		addItem("appealingness");
		addItem("familiarity");
		addItem("expectation-of-deviation");
		addItem("effort");
		addItem("praiseworthiness");
		addItem("liking");
		addItem("deservingness");
		addItem("likelihood");
		addItem("desirability");
	}
}