package fr.loria.synalp.emotionml.processors.io;

import java.io.*;

import javax.xml.stream.*;

import org.w3c.dom.Element;

import de.odysseus.staxon.json.*;
import de.odysseus.staxon.json.stream.impl.JsonStreamFactoryImpl;

import fr.loria.synalp.emotionml.exceptions.EmotionMLException;

/**
 * A JsonEmotionMLReader reads the InputStream as if it is formatted in JSON, then converts it into a DOM Element.
 * @author Alexandre Denis
 *
 */
public class JsonEmotionMLReader implements EmotionMLReader
{

	@Override
	public Element read(InputStream stream) throws EmotionMLException, IOException
	{
		try
		{
			JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(true).prettyPrint(true).build();
			JsonXMLInputFactory jsonFactory = new JsonXMLInputFactory(config, new JsonStreamFactoryImpl());

			// we should find a better way to get the element
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			XMLEventReader reader = jsonFactory.createXMLEventReader(stream);
			XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(out);
			writer.add(reader);
			reader.close();
			writer.close();
			return new XMLEmotionMLReader().read(new ByteArrayInputStream(out.toByteArray()));
		}
		catch (Exception e)
		{
			throw new EmotionMLException("Unable to read stream: "+e.getLocalizedMessage());
		}
	}

}
