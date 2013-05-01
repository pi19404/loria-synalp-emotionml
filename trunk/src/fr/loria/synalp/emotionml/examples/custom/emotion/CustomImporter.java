package fr.loria.synalp.emotionml.examples.custom.emotion;

import java.io.IOException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import fr.loria.synalp.emotionml.Emotion;
import fr.loria.synalp.emotionml.exceptions.*;
import fr.loria.synalp.emotionml.processors.*;
import fr.loria.synalp.emotionml.vocabularies.predefined.FsreDimensions;

/**
 * This example shows how it is possible to subclass Emotion and create objects that are easier to
 * manipulate (see CustomEmotion). There is no need to subclass EmotionMLExporter, only the
 * EmotionMLImporter.
 * @author Alexandre Denis
 */
class CustomImporter extends EmotionMLImporter
{
	public static void main(String[] args) throws SAXException, IOException, EmotionMLException
	{
		CustomEmotion emotion = new CustomEmotion(0.7f);
		System.out.println(emotion + "\n");

		String xml = new EmotionMLExporter().export(emotion);
		System.out.println(xml + "\n");

		CustomEmotion imported = (CustomEmotion) new CustomImporter().importEmotion(xml);
		System.out.println(imported + "\n");
	}


	@Override
	public CustomEmotion importEmotion(Element element)
	{
		Emotion emotion = super.importEmotion(element);
		return new CustomEmotion(emotion.getDescriptor(FsreDimensions.VALENCE.getName()).getValue());
	}

}
