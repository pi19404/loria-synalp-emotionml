package fr.loria.synalp.emotionml.processors.json;

import fr.loria.synalp.emotionml.processors.*;
import fr.loria.synalp.emotionml.processors.io.JsonEmotionMLWriter;

/**
 * A JsonEmotionMLExporter is an EmotionMLExporter whose writer is a JsonEmotionMLWriter.
 * @author Alexandre Denis
 */
public class JsonEmotionMLExporter extends EmotionMLExporter
{

	/**
	 * Creates a new JsonEmotionMLExporter with a default EmotionMLValidator.
	 */
	public JsonEmotionMLExporter()
	{
		setWriter(new JsonEmotionMLWriter());
	}


	/**
	 * @param validator
	 */
	public JsonEmotionMLExporter(EmotionMLValidator validator)
	{
		super(validator);
		setWriter(new JsonEmotionMLWriter());
	}
}
