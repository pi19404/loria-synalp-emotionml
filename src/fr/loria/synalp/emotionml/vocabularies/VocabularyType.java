package fr.loria.synalp.emotionml.vocabularies;

/**
 * A VocabularyType is the type of Vocabulary which is either CATEGORY, DIMENSION, APPRAISAL and
 * ACTION_TENDENCY.
 * @author Alexandre Denis
 */
public enum VocabularyType
{
	CATEGORY("category", "category-set"),
	DIMENSION("dimension", "dimension-set"),
	APPRAISAL("appraisal", "appraisal-set"),
	ACTION_TENDENCY("action-tendency", "action-tendency-set");

	/**
	 * @param typeStr
	 * @return a VocabularyType whose value corresponds to given String
	 */
	public static VocabularyType parse(String typeStr)
	{
		for(VocabularyType type : values())
			if (type.toString().equals(typeStr))
				return type;
		return null;
	}

	private String str;
	private String set;


	private VocabularyType(String str, String set)
	{
		this.str = str;
		this.set = set;
	}


	/**
	 * Returns the name of the feature used to refer to a vocabulary of this VocabularyType.
	 * @return category-set, dimension-set, appraisal-set or action-tendency-set
	 */
	public String getSet()
	{
		return set;
	}


	@Override
	public String toString()
	{
		return str;
	}

}
