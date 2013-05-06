package fr.loria.synalp.emotionml.processors.io;

import java.io.*;

import javax.xml.parsers.*;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import fr.loria.synalp.emotionml.exceptions.EmotionMLException;

/**
 * A XMLEmotionMLReader is an EmotionMLReader that reads the InputStream by using a DocumentBuilder.
 * @author Alexandre Denis
 *
 */
public class XMLEmotionMLReader implements EmotionMLReader
{

	@Override
	public Element read(InputStream stream) throws EmotionMLException, IOException
	{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);
		try
		{
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			return builder.parse(stream).getDocumentElement(); // this can hang when parsing documents containing entities
		}
		catch (SAXException e)
		{
			throw new EmotionMLException("Unable to read stream: " + e.getLocalizedMessage());
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
			throw new EmotionMLException("Unable to read stream: " + e.getLocalizedMessage());
		}
	}

}
