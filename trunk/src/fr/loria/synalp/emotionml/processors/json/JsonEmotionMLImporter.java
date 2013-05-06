package fr.loria.synalp.emotionml.processors.json;

import fr.loria.synalp.emotionml.processors.*;
import fr.loria.synalp.emotionml.processors.io.JsonEmotionMLReader;

/**
 * A JsonEmotionMLImporter is an EmotionMLImporter whose reader is a JsonEmotionMLReader.
 * @author Alexandre Denis
 */
public class JsonEmotionMLImporter extends EmotionMLImporter
{

	/**
	 * 
	 */
	public JsonEmotionMLImporter()
	{
		setReader(new JsonEmotionMLReader());
	}


	/**
	 * @param validator
	 */
	public JsonEmotionMLImporter(EmotionMLValidator validator)
	{
		super(validator);
		setReader(new JsonEmotionMLReader());
	}
}
