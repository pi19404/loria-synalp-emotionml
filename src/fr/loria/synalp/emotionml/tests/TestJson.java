package fr.loria.synalp.emotionml.tests;

import java.io.*;
import java.net.*;

import javax.xml.stream.*;

import de.odysseus.staxon.json.*;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;

import fr.loria.synalp.emotionml.*;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.exceptions.*;
import fr.loria.synalp.emotionml.processors.EmotionMLExporter;

public class TestJson
{
	public static void main(String[] args) throws URISyntaxException, XMLStreamException, FactoryConfigurationError, IOException, EmotionMLException
	{
		Emotion emotion = new Emotion();
		emotion.add(new Category("happiness"));
		emotion.setCategorySetURI(new URI("http://www.w3.org/TR/emotion-voc/xml#big6"));
		emotion.setText(new EmotionText("I'm happy"));

		Emotion emotion2 = new Emotion();
		emotion2.add(new Category("fear"));
		emotion2.setCategorySetURI(new URI("http://www.w3.org/TR/emotion-voc/xml#big6"));
		emotion2.setText(new EmotionText("I'm scared"));

		EmotionMLDocument doc = new EmotionMLDocument();
		doc.add(emotion);
		doc.add(emotion2);

		String xml = new EmotionMLExporter().export(doc);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		toJson(new ByteArrayInputStream(xml.getBytes()), output);
		System.out.println(output);

		ByteArrayInputStream jsonInput = new ByteArrayInputStream(output.toByteArray());
		fromJson(jsonInput, System.out);

	}


	private static void fromJson(InputStream input, OutputStream output) throws XMLStreamException, FactoryConfigurationError, IOException
	{
		JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).build();
		try
		{
			XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);

			XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
			writer = new PrettyXMLEventWriter(writer); // format output

			writer.add(reader);

			reader.close();
			writer.close();
		}
		finally
		{
			output.close();
			input.close();
		}
	}


	private static void toJson(InputStream input, OutputStream output) throws XMLStreamException, FactoryConfigurationError, IOException
	{
		/*
		 * If we want to insert JSON array boundaries for multiple elements,
		 * we need to set the <code>autoArray</code> property.
		 * If our XML source was decorated with <code>&lt;?xml-multiple?&gt;</code>
		 * processing instructions, we'd set the <code>multiplePI</code>
		 * property instead.
		 * With the <code>autoPrimitive</code> property set, element text gets
		 * automatically converted to JSON primitives (number, boolean, null).
		 */
		JsonXMLConfig config = new JsonXMLConfigBuilder()
															.autoArray(true)
															.autoPrimitive(true)
															.prettyPrint(true)
															.build();
		try
		{
			XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
			XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
			writer.add(reader);
			reader.close();
			writer.close();
		}
		finally
		{
			/*
			 * As per StAX specification, XMLEventReader/Writer.close() doesn't close
			 * the underlying stream.
			 */
			output.close();
			input.close();
		}
	}
}
