package fr.loria.synalp.emotionml.processors.json;

import java.io.*;

import javax.xml.stream.*;

import org.w3c.dom.Element;

import de.odysseus.staxon.json.*;
import fr.loria.synalp.emotionml.exceptions.EmotionMLException;
import fr.loria.synalp.emotionml.processors.*;

/**
 * A JsonEmotionMLImporter reads the InputStream as a Json input and converts it into a DOM Element.
 * @author Alexandre Denis
 */
public class JsonEmotionMLImporter extends EmotionMLImporter
{

	/**
	 * 
	 */
	public JsonEmotionMLImporter()
	{
		super();
	}


	/**
	 * @param validator
	 */
	public JsonEmotionMLImporter(EmotionMLValidator validator)
	{
		super(validator);
	}


	@Override
	public Element read(InputStream stream) throws EmotionMLException, IOException
	{
		try
		{
			JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(true).prettyPrint(true).build();
			JsonXMLInputFactory jsonFactory = new JsonXMLInputFactory(config);

			// we should find a better way to get the element
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			XMLEventReader reader = jsonFactory.createXMLEventReader(stream);
			XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(out);
			writer.add(reader);
			reader.close();
			writer.close();
			return super.read(new ByteArrayInputStream(out.toByteArray()));
		}
		catch (Exception e)
		{
			throw new EmotionMLException("Unable to read stream: "+e.getLocalizedMessage());
		}
	}

}
