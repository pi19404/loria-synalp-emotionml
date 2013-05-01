package fr.loria.synalp.emotionml.processors.json;

import java.io.*;

import javax.xml.stream.*;

import org.w3c.dom.Element;

import de.odysseus.staxon.json.*;

import fr.loria.synalp.emotionml.exceptions.EmotionMLException;
import fr.loria.synalp.emotionml.processors.*;

/**
 * A JsonEmotionMLExporter converts the DOM element in Json before writing it to the OutputStream.
 * @author Alexandre Denis
 */
public class JsonEmotionMLExporter extends EmotionMLExporter
{

	/**
	 * 
	 */
	public JsonEmotionMLExporter()
	{
		super();
	}


	/**
	 * @param validator
	 */
	public JsonEmotionMLExporter(EmotionMLValidator validator)
	{
		super(validator);
	}


	@Override
	public void write(Element element, OutputStream stream) throws EmotionMLException
	{
		try
		{
			JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(true).prettyPrint(true).build();
			JsonXMLOutputFactory jsonOutputFactory = new JsonXMLOutputFactory(config);
			
			// unfortunately we can't pass the node as a DOMSource so we have to write it as bytes first...
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			super.write(element, out);
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
