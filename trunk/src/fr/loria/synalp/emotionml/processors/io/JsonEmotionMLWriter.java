package fr.loria.synalp.emotionml.processors.io;

import java.io.*;

import javax.xml.stream.*;

import org.w3c.dom.Element;

import de.odysseus.staxon.json.*;
import de.odysseus.staxon.json.stream.impl.JsonStreamFactoryImpl;

import fr.loria.synalp.emotionml.exceptions.EmotionMLException;

/**
 * A JsonEmotionMLWriter is an EmotionMLWriter that writes the Elements first using a XMLEmotionWriter then by converting the XML to JSON.
 * @author Alexandre Denis
 *
 */
public class JsonEmotionMLWriter implements EmotionMLWriter
{
	@Override
	public void write(Element element, OutputStream stream) throws EmotionMLException
	{
		try
		{
			JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(true).prettyPrint(true).build();
			JsonXMLOutputFactory jsonOutputFactory = new JsonXMLOutputFactory(config, new JsonStreamFactoryImpl());
			
			// unfortunately we can't pass the node as a DOMSource so we have to write it as bytes first...
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			new XMLEmotionMLWriter().write(element, out);
			XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(new ByteArrayInputStream(out.toByteArray()));
			
			XMLEventWriter writer = jsonOutputFactory.createXMLEventWriter(stream);
			writer.add(reader);
			reader.close();
			writer.close();
		}
		catch(Exception e)
		{
			throw new EmotionMLException("Unable to write the given Element in JSON: "+e.getLocalizedMessage());
		}
	}

}
