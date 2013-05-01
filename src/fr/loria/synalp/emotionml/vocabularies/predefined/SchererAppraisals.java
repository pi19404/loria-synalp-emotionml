package fr.loria.synalp.emotionml.vocabularies.predefined;
import java.net.URI;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
	Auto-generated class corresponding to vocabulary <a href="http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals" target="_blank"/>http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals</a>
*/
public final class SchererAppraisals extends Vocabulary
{
	public static final Appraisal OUTCOME_PROBABILITY = new Appraisal("outcome-probability");
	public static final Appraisal AGENT_OTHER = new Appraisal("agent-other");
	public static final Appraisal CONTROL = new Appraisal("control");
	public static final Appraisal GOAL_CONDUCIVENESS = new Appraisal("goal-conduciveness");
	public static final Appraisal FAMILIARITY = new Appraisal("familiarity");
	public static final Appraisal PREDICTABILITY = new Appraisal("predictability");
	public static final Appraisal SELF_COMPATIBILITY = new Appraisal("self-compatibility");
	public static final Appraisal ADJUSTMENT_POSSIBLE = new Appraisal("adjustment-possible");
	public static final Appraisal RELEVANCE_RELATIONSHIP = new Appraisal("relevance-relationship");
	public static final Appraisal AGENT_SELF = new Appraisal("agent-self");
	public static final Appraisal RELEVANCE_PERSON = new Appraisal("relevance-person");
	public static final Appraisal URGENCY = new Appraisal("urgency");
	public static final Appraisal RELEVANCE_SOCIAL_ORDER = new Appraisal("relevance-social-order");
	public static final Appraisal CONSONANT_WITH_EXPECTATION = new Appraisal("consonant-with-expectation");
	public static final Appraisal POWER = new Appraisal("power");
	public static final Appraisal SUDDENNESS = new Appraisal("suddenness");
	public static final Appraisal AGENT_NATURE = new Appraisal("agent-nature");
	public static final Appraisal INTRINSIC_PLEASANTNESS = new Appraisal("intrinsic-pleasantness");
	public static final Appraisal NORM_COMPATIBILITY = new Appraisal("norm-compatibility");
	public static final Appraisal CAUSE_INTENTIONAL = new Appraisal("cause-intentional");

	static
	{
		try
		{
			OUTCOME_PROBABILITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			AGENT_OTHER.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			CONTROL.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			GOAL_CONDUCIVENESS.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			FAMILIARITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			PREDICTABILITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			SELF_COMPATIBILITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			ADJUSTMENT_POSSIBLE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			RELEVANCE_RELATIONSHIP.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			AGENT_SELF.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			RELEVANCE_PERSON.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			URGENCY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			RELEVANCE_SOCIAL_ORDER.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			CONSONANT_WITH_EXPECTATION.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			POWER.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			SUDDENNESS.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			AGENT_NATURE.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			INTRINSIC_PLEASANTNESS.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			NORM_COMPATIBILITY.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
			CAUSE_INTENTIONAL.setURI(new URI("http://www.w3.org/TR/emotion-voc/xml#scherer-appraisals"));
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public SchererAppraisals()
	{
		super("scherer-appraisals", VocabularyType.APPRAISAL);
		addItem("outcome-probability");
		addItem("agent-other");
		addItem("control");
		addItem("goal-conduciveness");
		addItem("familiarity");
		addItem("predictability");
		addItem("self-compatibility");
		addItem("adjustment-possible");
		addItem("relevance-relationship");
		addItem("agent-self");
		addItem("relevance-person");
		addItem("urgency");
		addItem("relevance-social-order");
		addItem("consonant-with-expectation");
		addItem("power");
		addItem("suddenness");
		addItem("agent-nature");
		addItem("intrinsic-pleasantness");
		addItem("norm-compatibility");
		addItem("cause-intentional");
	}
}