package fr.loria.synalp.emotionml.examples;

import java.io.*;
import java.net.*;

import fr.loria.synalp.emotionml.*;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.descriptors.Reference.Role;
import fr.loria.synalp.emotionml.descriptors.Timestamp.TimeRefAnchorPoint;
import fr.loria.synalp.emotionml.exceptions.*;
import fr.loria.synalp.emotionml.processors.*;
import fr.loria.synalp.emotionml.processors.json.*;
import fr.loria.synalp.emotionml.vocabularies.*;
import fr.loria.synalp.emotionml.vocabularies.predefined.*;

/**
 * This suite of examples shows how to export documents and emotions.
 * @author Alexandre Denis
 */
public class ExportExamples
{
	public static EmotionMLExporter exporter;


	public static void main(String[] args) throws EmotionMLValidationException, URISyntaxException
	{
		exporter = new EmotionMLExporter();

		example1();
		example2();
		example3();
		example4();
		example5();
		example6();
		example7();
		example8();
		example9();
		example10();
		example11();
	}


	/**
	 * Displays a simple emotion based on FEAR category from the Big6 vocabulary.
	 * @throws EmotionMLValidationException
	 */
	public static void example1()
	{
		System.out.println("------- Example1");
		try
		{
			Emotion emotion = new Emotion(Big6.FEAR);
			System.out.println(emotion+"\n");
			
			exporter.export(emotion, System.out);
			// or
			System.out.println(exporter.export(emotion));
		}
		catch (EmotionMLException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * We can combine the FEAR category and AROUSAL dimension.
	 * @throws EmotionMLValidationException
	 */
	public static void example2()
	{
		System.out.println("\n\n------- Example2");
		try
		{
			Emotion emotion = new Emotion(Big6.FEAR, FsreDimensions.AROUSAL); // this is possible
			exporter.export(emotion, System.out);
		}
		catch (EmotionMLException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * However we cannot mix the FEAR category and the AFRAID category since they both are
	 * categories and they belong to different vocabularies. Note that the exception is not thrown
	 * when creating the Emotion but when trying to export it.
	 */
	public static void example3()
	{
		System.out.println("\n\n------- Example3");
		
		// what happens here is that the category-set URI of AFRAID replaces the category-set URI of FEAR
		Emotion emotion = new Emotion(Big6.FEAR, EverydayCategories.AFRAID);
		try
		{
			exporter.export(emotion, System.out); // exception we cannot mix the two kinds
		}
		catch (EmotionMLException e)
		{
			System.out.println("Exception in Example3: " + e);
		}
	}


	/**
	 * We could create a custom Category with name "love" but this would throws an exception when
	 * exporting since there is no defined vocabulary URI (see EmotionML specification). It is then
	 * easier to reuse categories in the predefined vocabularies package because they automatically
	 * setup the vocabulary URI when they are added to an Emotion (like Big6.FEAR for instance).
	 * @throws EmotionMLValidationException
	 */
	public static void example4()
	{
		System.out.println("\n\n------- Example4");
		
		try
		{
			Emotion emotion = new Emotion(new Category("love"));
			exporter.export(emotion, System.out); // exception because there is no category set
		}
		catch (EmotionMLException e)
		{
			System.out.println("Exception in Example4: " + e);
		}
	}


	/**
	 * If we still want to have a custom Category "love" we need to take care of the vocabulary URI
	 * by explicitely calling the setCategorySetURI method. However, because here we provide a fake
	 * URI, it will also throw an exception.
	 */
	public static void example5()
	{
		System.out.println("\n\n------- Example5");
		
		try
		{
			Emotion emotion = new Emotion(new Category("love"));
			emotion.setCategorySetURI("http://myvocabularies#mycategories");
			exporter.export(emotion, System.out); // exception because the URI cannot be resolved
		}
		catch (Exception e)
		{
			System.out.println("Exception in Example5: " + e);
		}
	}


	/**
	 * We could however disable the URI resolution if needed, for instance when working offline. To
	 * do that we have to pass to the EmotionMLExporter an EmotionMLValidator with
	 * setResolveVocabularies false (it is true by default). Nevertheless, this should be
	 * discouraged since it produces invalid EmotionML documents.
	 */
	public static void example6()
	{
		System.out.println("\n\n------- Example6");
		try
		{
			EmotionMLExporter exporter = new EmotionMLExporter(new EmotionMLValidator().setResolveVocabularies(false));
			Emotion emotion = new Emotion(new Category("love"));
			emotion.setCategorySetURI("http://myvocabularies#mycategories");
			exporter.export(emotion, System.out); // no exception
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * To be compliant with EmotionML we would need to create a vocabulary, but we have to put it
	 * inside an EmotionMLDocument and save it somewhere.
	 */
	public static void example7()
	{
		System.out.println("\n\n------- Example7");
		
		try
		{
			// create vocabulary
			String myVocabularyId = "mycategories";
			Vocabulary vocabulary = new Vocabulary(myVocabularyId, VocabularyType.CATEGORY);
			vocabulary.addItem("love");
			vocabulary.addItem("hate");

			// add it to a new doc
			EmotionMLDocument doc = new EmotionMLDocument();
			doc.add(vocabulary);

			// and write the doc
			File file = File.createTempFile("myvocabularies", ".tmp");
			exporter.export(doc, file);

			// we could now safely refer to the love Category
			Emotion emotion = new Emotion(new Category("love"));
			emotion.setCategorySetURI(file.toURI() + "#" + myVocabularyId);

			exporter.export(emotion, System.out); // no exception but it would be better to save the doc somewhere else
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Alternatively, we can embed the Emotion inside an EmotionMLDocument that defines the
	 * vocabulary.
	 */
	public static void example8()
	{
		System.out.println("\n\n------- Example8");
		
		// create vocabulary
		String myVocabularyId = "mycategories";
		Vocabulary vocabulary = new Vocabulary(myVocabularyId, VocabularyType.CATEGORY);
		vocabulary.addItem("love");
		vocabulary.addItem("hate");

		// add it to a new doc
		EmotionMLDocument doc = new EmotionMLDocument();
		doc.add(vocabulary);

		Emotion emotion = new Emotion(new Category("love"));
		emotion.setCategorySetURI("#" + myVocabularyId); // the URI is local to the document
		doc.add(emotion);

		try
		{
			exporter.export(doc, System.out); // no exception since the vocabulary is contained in the same document
		}
		catch (EmotionMLException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * About the expressivity...
	 */
	public static void example9()
	{
		System.out.println("\n\n------- Example9");
		
		try
		{
			Dimension dimension = new Dimension(FsreDimensions.VALENCE, new Trace(100, new float[] { 0.1f, 0.2f, 0.3f }));
			dimension.setConfidence(0.8f);

			Emotion emotion = new Emotion();
			emotion.add(dimension);
			emotion.add(new ExpressedThrough(ExpressedThrough.Type.FACE));
			emotion.add(new ExpressedThrough("eyebrows"));
			emotion.setText(new EmotionText("this is sad but gets better"));
			emotion.add(new Reference(new URI("myvideo.mpeg"), "video/mpeg", Role.EXPRESSED_BY));

			Timestamp timestamp = new Timestamp();
			timestamp.setOffsetToStart(300);
			timestamp.setTimeRefURI(new URI("#mysessionId"));
			timestamp.setTimeRefAnchorPoint(TimeRefAnchorPoint.START);

			emotion.setTimestamp(timestamp);

			System.out.println(emotion+"\n");
			
			exporter.export(emotion, System.out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * We can also import/export from Json.
	 */
	public static void example10()
	{
		System.out.println("\n\n------- Example10");
		
		Dimension dimension = new Dimension(FsreDimensions.VALENCE, 0.2f);
		dimension.setConfidence(0.8f);

		Emotion emotion = new Emotion(dimension);
		System.out.println(emotion+"\n");

		try
		{
			String json = new JsonEmotionMLExporter().export(emotion);
			System.out.println(json);

			new JsonEmotionMLImporter().importEmotion(json);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Warning though: it is not possible yet to export in JSON Emotions containing text values. An
	 * alternative could be to use custom info to store the text (see custom info example).
	 */
	public static void example11()
	{
		System.out.println("\n\n------- Example11");
		
		Emotion emotion = new Emotion(Big6.SADNESS);
		emotion.setText(new EmotionText("Alas, I will throw an exception when exporting in JSON..."));

		try
		{
			new JsonEmotionMLExporter().export(emotion, System.out);
		}
		catch (EmotionMLException e)
		{
			System.out.println("Exception in Example11: " + e);
		}
	}
}
