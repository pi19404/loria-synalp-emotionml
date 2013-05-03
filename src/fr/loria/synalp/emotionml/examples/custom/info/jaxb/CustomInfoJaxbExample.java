package fr.loria.synalp.emotionml.examples.custom.info.jaxb;

import java.io.IOException;

import fr.loria.synalp.emotionml.*;
import fr.loria.synalp.emotionml.descriptors.Category;
import fr.loria.synalp.emotionml.exceptions.*;

import fr.loria.synalp.emotionml.processors.jaxb.*;
import fr.loria.synalp.emotionml.vocabularies.predefined.Big6;

public class CustomInfoJaxbExample
{

	/**
	 * @param args
	 * @throws EmotionMLException 
	 * @throws EmotionMLValidationException 
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws EmotionMLValidationException, EmotionMLException, IOException
	{
		EmotionMLDocument doc = new EmotionMLDocument().setInfo(new ClassifierInfo("GMM"));
		doc.add(new Emotion(new Category(Big6.HAPPINESS)).setInfo(new LocalizationInfo("bavarian")));
		doc.add(new Emotion(new Category(Big6.SADNESS)).setInfo(new LocalizationInfo("swabian")));

		System.out.println("Original document:");
		System.out.println(doc + "\n");

		String xml = new JaxbEmotionMLExporter().export(doc);
		System.out.println("Serialized:");
		System.out.println(xml);

		JaxbEmotionMLImporter importer = new JaxbEmotionMLImporter().addInfoClasses(ClassifierInfo.class, LocalizationInfo.class);
		
		EmotionMLDocument imported = importer.importDocument(xml);
		System.out.println("Deserialized:");
		System.out.println(imported);

		ClassifierInfo info = (ClassifierInfo) imported.getInfo();
		System.out.println("\nClassifier name: " + info.getName());
	}

}
