package fr.loria.synalp.emotionml.processors.io;

import java.io.*;

import org.w3c.dom.Element;

import fr.loria.synalp.emotionml.exceptions.EmotionMLException;

/**
 * An EmotionMLReader reads an InputStream as a DOM Element.
 * @author Alexandre Denis
 *
 */
public interface EmotionMLReader
{
	/**
	 * Returns a DOM Element from the given InputStream.
	 * @param stream
	 * @return
	 * @throws EmotionMLException
	 * @throws IOException
	 */
	public Element read(InputStream stream) throws EmotionMLException, IOException;
}
