package fr.loria.synalp.emotionml.processors;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.*;

import org.w3c.dom.*;

import fr.loria.synalp.emotionml.*;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.descriptors.Reference.Role;
import fr.loria.synalp.emotionml.exceptions.*;
import fr.loria.synalp.emotionml.info.*;
import fr.loria.synalp.emotionml.processors.io.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
 * A EmotionMLImporter imports EmotionML elements from the InputStream by first reading the stream
 * as a DOM Element, then by validating the DOM Element with respect to the EmotionML standard and
 * eventually by building EmotionML objects. It also offers different methods to import documents
 * from other sources than stream. In order to read DOM Elements it uses an instance of an
 * EmotionMLReader, which by default is a XMLEmotionMLReader. It is possible to setup a different
 * reader.
 * @author Alexandre Denis
 */
public class EmotionMLImporter extends EmotionMLProcessor
{
	private EmotionMLReader reader = new XMLEmotionMLReader();


	/**
	 * Creates a new EmotionMLImporter with a default EmotionMLValidator.
	 */
	public EmotionMLImporter()
	{
		super();
	}


	/**
	 * Creates a new EmotionMLImporter with the given EmotionMLValidator.
	 */
	public EmotionMLImporter(EmotionMLValidator validator)
	{
		super(validator);
	}


	/**
	 * Returns the currently defined EmotionMLReader.
	 * @return the reader
	 */
	public EmotionMLReader getReader()
	{
		return reader;
	}


	/**
	 * Sets the EmotionMLReader that is used to read the input stream.
	 * @param reader the reader to set
	 * @return this EmotionLMImporter for chaining
	 */
	public EmotionMLImporter setReader(EmotionMLReader reader)
	{
		this.reader = reader;
		return this;
	}


	/**
	 * Reads the given InputStream as a DOM Element.
	 * @param stream
	 * @return a DOM Element
	 * @throws EmotionMLException
	 * @throws IOException
	 */
	protected Element read(InputStream stream) throws EmotionMLException, IOException
	{
		return reader.read(stream);
	}


	/**
	 * Imports an EmotionMLDocument from given InputStream.
	 * @param stream
	 * @return an EmotionMLDocument
	 * @throws IOException
	 * @throws EmotionMLValidationException
	 * @throws EmotionMLException
	 */
	public EmotionMLDocument importDocument(InputStream stream) throws IOException, EmotionMLValidationException, EmotionMLException
	{
		return importDocument(getValidator().validateDocument(read(stream)));
	}


	/**
	 * Imports an Emotion from given InputStream.
	 * @param stream
	 * @return an Emotion
	 * @throws IOException
	 * @throws EmotionMLValidationException
	 * @throws EmotionMLException
	 */
	public Emotion importEmotion(InputStream stream) throws IOException, EmotionMLValidationException, EmotionMLException
	{
		return importEmotion(getValidator().validateEmotion(read(stream)));
	}


	/**
	 * Imports the given File as an EmotionMLDocument.
	 * @param file
	 * @return an EmotionMLDocument
	 * @throws EmotionMLValidationException
	 * @throws IOException
	 * @throws EmotionMLException
	 */
	public EmotionMLDocument importDocument(File file) throws IOException, EmotionMLValidationException, EmotionMLException
	{
		FileInputStream stream = new FileInputStream(file);
		EmotionMLDocument ret = importDocument(stream);
		stream.close();
		return ret;
	}


	/**
	 * Imports the given File as an Emotion.
	 * @param file
	 * @return an Emotion
	 * @throws EmotionMLValidationException
	 * @throws EmotionMLException
	 * @throws IOException
	 */
	public Emotion importEmotion(File file) throws IOException, EmotionMLValidationException, EmotionMLException
	{
		FileInputStream stream = new FileInputStream(file);
		Emotion ret = importEmotion(stream);
		stream.close();
		return ret;
	}


	/**
	 * Imports the given String as an EmotionMLDocument.
	 * @param string
	 * @return an EmotionMLDocument
	 * @throws EmotionMLValidationException
	 * @throws EmotionMLException
	 * @throws IOException
	 */
	public EmotionMLDocument importDocument(String string) throws IOException, EmotionMLValidationException, EmotionMLException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes());
		EmotionMLDocument ret = importDocument(stream);
		stream.close();
		return ret;
	}


	/**
	 * Imports the given String as an Emotion.
	 * @param string
	 * @return an Emotion
	 * @throws EmotionMLValidationException
	 * @throws EmotionMLException
	 * @throws IOException
	 */
	public Emotion importEmotion(String string) throws IOException, EmotionMLValidationException, EmotionMLException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes());
		Emotion ret = importEmotion(stream);
		stream.close();
		return ret;
	}


	/**
	 * Imports the given DOM Element as an EmotionMLDocument. This method assumes that the given DOM
	 * Element is well defined with respect to EmotionML standard.
	 * @param element
	 * @return an EmotionMLDocument corresponding to given DOM Element
	 * @throws EmotionMLException
	 */
	public EmotionMLDocument importDocument(Element element) throws EmotionMLException
	{
		EmotionMLDocument ret = new EmotionMLDocument();

		// vocabulary sets
		for(VocabularyType type : VocabularyType.values())
		{
			String descriptorSet = element.getAttribute(type.getSet());
			if (!descriptorSet.equals(""))
				ret.setDescriptorSetURI(type, importURI(descriptorSet));
		}

		// children
		NodeList children = element.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if (child instanceof Text)
			{
				String value = child.getNodeValue();
				if (!value.trim().equals(""))
					ret.add(new EmotionText(value));
			}
			else if (child instanceof Element)
			{
				String name = child.getLocalName();
				if (name.equals("info"))
					ret.setInfo(importInfo((Element) child));
				else if (name.equals("emotion"))
					ret.add(importEmotion((Element) child));
				else if (name.equals("vocabulary"))
					ret.add(importVocabulary((Element) child));
			}
		}
		return ret;
	}


	/**
	 * Imports the given DOM Element as an Emotion. This method assumes that the given Element is
	 * well defined with respect to EmotionML standard.
	 * @param element
	 * @return an Emotion corresponding to given DOM Element
	 * @throws EmotionMLException
	 */
	public Emotion importEmotion(Element element) throws EmotionMLException
	{
		Emotion ret = new Emotion();
		
		// id
		String id = element.getAttribute("id");
		if (!id.equals(""))
			ret.setId(id);

		// descriptor sets
		for(VocabularyType type : VocabularyType.values())
		{
			String descriptorSet = element.getAttribute(type.getSet());
			if (!descriptorSet.equals(""))
				ret.setDescriptorSetURI(type, importURI(descriptorSet));
		}

		// expressed through
		String expressedThrough = element.getAttribute("expressed-through");
		if (!expressedThrough.equals(""))
			ret.setExpressedThrough(importExpressedThrough(expressedThrough));

		// timestamp
		ret.setTimestamp(importTimestamp(element));

		// children
		NodeList children = element.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if (child instanceof Text)
			{
				String value = child.getNodeValue();
				if (!value.trim().equals(""))
					ret.setText(new EmotionText(value));
			}
			else if (child instanceof Element)
			{
				String name = child.getLocalName();
				if (name.equals("info"))
					ret.setInfo(importInfo((Element) child));
				else if (name.equals("reference"))
					ret.add(importReference((Element) child));
				else if (name.equals("category") || name.equals("dimension") || name.equals("action-tendency") || name.equals("appraisal"))
					ret.add(importDescriptor((Element) child));
			}
		}

		return ret;
	}


	/**
	 * Imports the given DOM Element as an Info.
	 * @param element
	 * @return an Info corresponding to given DOM Element
	 * @throws EmotionMLException
	 */
	public Info importInfo(Element element) throws EmotionMLException
	{
		String id = element.getAttribute("id");
		if (id.equals(""))
			return new Info();
		else return new Info(id);
	}


	/**
	 * Imports the given DOM Element as a Vocabulary.
	 * @param element
	 * @return a Vocabulary
	 * @throws EmotionMLException
	 */
	public Vocabulary importVocabulary(Element element) throws EmotionMLException
	{
		String id = element.getAttribute("id");
		VocabularyType type = VocabularyType.parse(element.getAttribute("type"));

		Vocabulary ret = new Vocabulary(id, type);
		NodeList children = element.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if (child instanceof Element)
			{
				String name = child.getLocalName();
				if (name.equals("info"))
					ret.setInfo(importInfo((Element) child));
				else if (name.equals("item"))
					ret.add(importVocabularyItem((Element) child));
			}
		}
		return ret;
	}


	/**
	 * Imports the given DOM Element as a VocabularyItem
	 * @param element
	 * @return a VocabularyItem
	 * @throws EmotionMLException
	 */
	private VocabularyItem importVocabularyItem(Element element) throws EmotionMLException
	{
		VocabularyItem ret = new VocabularyItem(element.getAttribute("name"));

		NodeList children = element.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if (child instanceof Element)
				if (child.getLocalName().equals("info"))
					ret.setInfo(importInfo((Element) child));
		}

		return ret;
	}


	/**
	 * Imports a Timestamp from given DOM Element.
	 * @param element
	 * @return
	 */
	private Timestamp importTimestamp(Element element)
	{
		Timestamp ret = new Timestamp();

		// start
		String startStr = element.getAttribute("start");
		if (!startStr.equals(""))
			ret.setStart(new BigInteger(startStr));

		// end
		String endStr = element.getAttribute("end");
		if (!endStr.equals(""))
			ret.setEnd(new BigInteger(endStr));

		// duration
		String durationStr = element.getAttribute("duration");
		if (!durationStr.equals(""))
			ret.setDuration(new BigInteger(durationStr));

		// offset-to-start
		String offsetStr = element.getAttribute("offset-to-start");
		if (!offsetStr.equals(""))
			ret.setOffsetToStart(new BigInteger(offsetStr));

		// time-ref-uri
		String timeRefURI = element.getAttribute("time-ref-uri");
		if (!timeRefURI.equals(""))
			ret.setTimeRefURI(importURI(timeRefURI));

		// time-ref-anchor-point
		String timeRefAnchorPoint = element.getAttribute("time-ref-anchor-point");
		if (!timeRefAnchorPoint.equals(""))
			ret.setTimeRefAnchorPoint(Timestamp.TimeRefAnchorPoint.parse(timeRefAnchorPoint));

		return ret;

	}


	/**
	 * @param expressedThrough
	 * @return
	 */
	private List<ExpressedThrough> importExpressedThrough(String expressedThrough)
	{
		List<ExpressedThrough> ret = new ArrayList<ExpressedThrough>();
		for(String part : expressedThrough.split(" "))
			ret.add(ExpressedThrough.parse(part));
		return ret;
	}


	/**
	 * @param child
	 * @return
	 */
	private Reference importReference(Element element)
	{
		Reference ret = new Reference(importURI(element.getAttribute("uri")));
		String mediaType = element.getAttribute("media-type");
		if (!mediaType.equals(""))
			ret.setMediaType(mediaType);
		String role = element.getAttribute("role");
		if (role != null)
			ret.setRole(Role.parse(role));
		return ret;
	}


	/**
	 * Imports the given Element as an EmotionDescriptor.
	 * @param child
	 * @return
	 */
	private EmotionDescriptor importDescriptor(Element element)
	{
		EmotionDescriptor ret = null;
		String name = element.getAttribute("name");
		switch (VocabularyType.parse(element.getLocalName()))
		{
			case CATEGORY:
				ret = new Category(name);
				break;

			case DIMENSION:
				ret = new Dimension(name, (Float) null);
				break;

			case APPRAISAL:
				ret = new Appraisal(name);
				break;

			case ACTION_TENDENCY:
				ret = new ActionTendency(name);
				break;
		}

		String value = element.getAttribute("value");
		if (!value.equals(""))
			ret.setValue(Float.parseFloat(value));

		String confidence = element.getAttribute("confidence");
		if (!confidence.equals(""))
			ret.setConfidence(Float.parseFloat(confidence));

		NodeList children = element.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if (child instanceof Element && child.getLocalName().equals("trace"))
				ret.setTrace(importTrace((Element) child));
		}

		return ret;
	}


	/**
	 * Imports the given Element as a Trace.
	 * @param child
	 * @return
	 */
	private Trace importTrace(Element element)
	{
		String freqStr = element.getAttribute("freq");
		float freq = Float.parseFloat(freqStr.substring(0, freqStr.indexOf("Hz")));
		String samples = element.getAttribute("samples");
		String[] parts = samples.split(" ");
		float[] samplesValues = new float[parts.length];
		for(int i = 0; i < parts.length; i++)
			samplesValues[i] = Float.parseFloat(parts[i]);

		return new Trace(freq, samplesValues);
	}


	/**
	 * Imports the given String as an URI. This method catches the uri syntax exception.
	 * @param uri
	 * @return
	 */
	private URI importURI(String uri)
	{
		try
		{
			return new URI(uri);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
			return null; // this should not happen
		}
	}
}
