package fr.loria.synalp.emotionml.processors.io;

import java.io.OutputStream;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

import fr.loria.synalp.emotionml.exceptions.EmotionMLException;

/**
 * An XMLEmotionMLWriter is an EmotionMLWriter that writes the Elements using a Transformer instance.
 * @author Alexandre Denis
 *
 */
public class XMLEmotionMLWriter implements EmotionMLWriter
{

	@Override
	public void write(Element element, OutputStream stream) throws EmotionMLException
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try
		{
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("indent", "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(new DOMSource(element), new StreamResult(stream));
		}
		catch (TransformerConfigurationException e)
		{
			throw new EmotionMLException("Unable to write element: "+e.getLocalizedMessage());
		}
		catch (TransformerException e)
		{
			throw new EmotionMLException("Unable to write element: "+e.getLocalizedMessage());
		}
	}

}
