package fr.loria.synalp.emotionml.examples.custom.info;

import java.io.IOException;

import org.xml.sax.SAXException;

import fr.loria.synalp.emotionml.*;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.exceptions.*;
import fr.loria.synalp.emotionml.vocabularies.predefined.*;

/**
 * This example shows how it is possible to subclass Info to define Info for a particular domain. We
 * define here two Infos, a ClassifierInfo and a LocalizationInfo. See this example at <a
 * href="http://www.w3.org/TR/emotionml/#s2.3.3" target="_blank">Info element in EmotionML</a>.
 * @author Alexandre Denis
 */
class CustomInfoExample
{
	public static void main(String[] args) throws SAXException, IOException, EmotionMLException
	{
		EmotionMLDocument doc = new EmotionMLDocument().setInfo(new ClassifierInfo("GMM"));
		doc.add(new Emotion(new Category(Big6.HAPPINESS)).setInfo(new LocalizationInfo("bavarian")));
		doc.add(new Emotion(new Category(Big6.SADNESS)).setInfo(new LocalizationInfo("swabian")));

		System.out.println("Original document:");
		System.out.println(doc + "\n");

		String xml = new CustomExporter().export(doc);
		System.out.println("Serialized:");
		System.out.println(xml);

		EmotionMLDocument imported = new CustomImporter().importDocument(xml);
		System.out.println("Deserialized:");
		System.out.println(imported);

		ClassifierInfo info = (ClassifierInfo) imported.getInfo();
		System.out.println("\nClassifier name: " + info.getName());
	}
}
