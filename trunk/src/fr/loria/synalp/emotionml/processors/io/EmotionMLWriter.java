package fr.loria.synalp.emotionml.processors.io;

import java.io.OutputStream;

import org.w3c.dom.Element;

import fr.loria.synalp.emotionml.exceptions.EmotionMLException;

/**
 * An EmotionMLWriter specifies how an Element has to be written to an Outputstream.
 * @author Alexandre Denis
 *
 */
public interface EmotionMLWriter
{
	/**
	 * Writes the given Element to the given Outputstream.
	 * @param element
	 * @param stream
	 * @throws EmotionMLException
	 */
	public void write(Element element, OutputStream stream) throws EmotionMLException;
}
