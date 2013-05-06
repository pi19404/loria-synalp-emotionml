package fr.loria.synalp.emotionml.processors;

import static fr.loria.synalp.emotionml.EmotionMLDocument.*;

import java.io.*;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import fr.loria.synalp.emotionml.*;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.exceptions.*;
import fr.loria.synalp.emotionml.info.*;
import fr.loria.synalp.emotionml.processors.io.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
 * A EmotionMLExporter is an EmotionMLProcessor that exports EmotionML objects in an OutputStream by
 * first building DOM elements corresponding to them then validating them. In order to write the DOM
 * elements it uses an instance of an EmotionMLWriter which by default is a XMLEmotionMLWriter that
 * writes the elements in XML format. It is possible to set a different writer.
 * @author Alexandre Denis
 */
public class EmotionMLExporter extends EmotionMLProcessor
{
	private EmotionMLWriter writer = new XMLEmotionMLWriter();


	/**
	 * Creates a new EmotionMLExporter based on a default EmotionMLValidator.
	 */
	public EmotionMLExporter()
	{

	}


	/**
	 * Creates a new EmotionMLExporter with the given EmotionMLValidator.
	 * @param validator
	 */
	public EmotionMLExporter(EmotionMLValidator validator)
	{
		super(validator);
	}


	/**
	 * Sets the EmotionMLWriter that is used to write the DOM elements.
	 * @param writer
	 * @return this EmotionMLExporter for chaining
	 */
	public EmotionMLExporter setWriter(EmotionMLWriter writer)
	{
		this.writer = writer;
		return this;
	}
	
	
	/**
	 * Returns the currently used EmotionMLWriter.
	 */
	public EmotionMLWriter getWriter()
	{
		return writer;
	}


	/**
	 * Writes the given Element to the given OutputStream using the defined writer.
	 * @param element
	 * @param stream
	 */
	protected void write(Element element, OutputStream stream) throws EmotionMLException
	{
		writer.write(element, stream);
	}


	/**
	 * Exports the given EmotionMLDocument as a DOM Node.
	 * @param document
	 * @throws EmotionMLException
	 * @throws EmotionMLValidationException
	 */
	public void export(EmotionMLDocument document, OutputStream stream) throws EmotionMLValidationException, EmotionMLException
	{
		write(getValidator().validateDocument(export(document, createDOMDocument())), stream);
	}


	/**
	 * Exports the given Emotion as a DOM Node.
	 * @param emotion
	 * @throws EmotionMLValidationException
	 */
	public void export(Emotion emotion, OutputStream stream) throws EmotionMLValidationException, EmotionMLException
	{
		write(getValidator().validateEmotion(export(emotion, createDOMDocument())), stream);
	}


	/**
	 * Exports the given EmotionMLDocument to the given File. If the File exists, it is deleted
	 * before export.
	 * @param document
	 * @param file
	 * @throws EmotionMLValidationException
	 * @throws IOException
	 */
	public void export(EmotionMLDocument document, File file) throws EmotionMLValidationException, EmotionMLException, IOException
	{
		file.delete();
		FileOutputStream stream = new FileOutputStream(file);
		export(document, stream);
		stream.close();
	}


	/**
	 * Exports the given Emotion to the given File. If the File exists, it is deleted before export.
	 * @param emotion
	 * @param file
	 * @throws EmotionMLValidationException
	 * @throws IOException
	 */
	public void export(Emotion emotion, File file) throws EmotionMLValidationException, EmotionMLException, IOException
	{
		file.delete();
		FileOutputStream stream = new FileOutputStream(file);
		export(emotion, stream);
		stream.close();
	}


	/**
	 * Exports the given EmotionMLDocument as a String.
	 * @param document
	 * @return a XML String representing the given EmotionMLDocument
	 * @throws EmotionMLValidationException
	 */
	public String export(EmotionMLDocument document) throws EmotionMLValidationException, EmotionMLException
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		export(document, stream);
		try
		{
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return stream.toString();
	}


	/**
	 * Exports the given Emotion as a String.
	 * @param emotion
	 * @return a XML String representing the given Emotion
	 * @throws EmotionMLValidationException
	 */
	public String export(Emotion emotion) throws EmotionMLValidationException, EmotionMLException
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		export(emotion, stream);
		try
		{
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return stream.toString().trim();
	}


	/**
	 * Creates a new DOM Document.
	 * @return
	 */
	private Document createDOMDocument()
	{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);
		try
		{
			return builderFactory.newDocumentBuilder().newDocument();
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Exports the given Info as a DOM Element belonging to given DOM Document.
	 * @param info
	 * @return a DOM Element representing the given Info
	 */
	public Element exportInfo(Info info, Document doc) throws EmotionMLException
	{
		Element ret = doc.createElementNS(NAMESPACE, "info");
		if (info.hasId())
			ret.setAttribute("id", info.getId());
		return ret;
	}


	/**
	 * Exports the given EmotionMLDocument as a DOM Element belonging to given DOM Document.
	 * @param emotionDocument
	 * @return a Document whose root is &lt;emotionml&gt;
	 * @throws EmotionMLValidationException
	 */
	public Element export(EmotionMLDocument emotionDocument, Document doc) throws EmotionMLValidationException, EmotionMLException
	{
		Element root = doc.createElementNS(NAMESPACE, "emotionml");
		doc.appendChild(root);
		root.setAttribute("xmlns", NAMESPACE);
		root.setAttribute("version", VERSION);

		// descriptors sets (category-set, dimension-set, ...)
		for(VocabularyType type : VocabularyType.values())
			if (emotionDocument.hasVocabularySetURI(type))
				root.setAttribute(type.getSet(), emotionDocument.getDescriptorSetURI(type).toString());

		// children
		if (emotionDocument.hasInfo())
			root.appendChild(exportInfo(emotionDocument.getInfo(), doc));

		for(Vocabulary vocabulary : emotionDocument.getVocabularies())
			root.appendChild(export(vocabulary, doc));

		for(EmotionNode emotionNode : emotionDocument.getEmotionNodes())
			root.appendChild(export(emotionNode, doc));

		return root;
	}


	/**
	 * Exports the given EmotionNode.
	 * @param emotionNode
	 * @param doc
	 * @return
	 */
	private Node export(EmotionNode emotionNode, Document doc) throws EmotionMLException
	{
		if (emotionNode instanceof EmotionText)
			return export((EmotionText) emotionNode, doc);
		else if (emotionNode instanceof Emotion)
			return export((Emotion) emotionNode, doc);
		else
		{
			System.err.println("Error: cannot export " + emotionNode + " because objects of class " + emotionNode.getClass() + " cannot be exported");
			return null;
		}
	}


	/**
	 * Exports the given EmotionText as a text node.
	 * @param text
	 * @param doc
	 * @return
	 */
	private Node export(EmotionText text, Document doc)
	{
		return doc.createTextNode(text.getContent());
	}


	/**
	 * Exports the given Emotion as a DOM element.
	 * @param emotion
	 * @param doc an owner document
	 * @return a DOM element representing given Emotion
	 */
	public Element export(Emotion emotion, Document doc) throws EmotionMLException
	{
		Element ret = doc.createElementNS(NAMESPACE, "emotion");

		// descriptors sets (category-set, dimension-set, ...)
		for(VocabularyType type : VocabularyType.values())
			if (emotion.hasVocabularySetURI(type))
				ret.setAttribute(type.getSet(), emotion.getDescriptorSetURI(type).toString());

		// version
		if (emotion.hasVersion())
			ret.setAttribute("version", emotion.getVersion());

		// id
		if (emotion.hasId())
			ret.setAttribute("id", emotion.getId());

		// expressed through
		List<ExpressedThrough> expressedThrough = emotion.getExpressedThrough();
		if (!expressedThrough.isEmpty())
			ret.setAttribute("expressed-through", export(expressedThrough));

		// timestamp
		export(emotion.getTimestamp(), ret);

		// info
		if (emotion.hasInfo())
			ret.appendChild(exportInfo(emotion.getInfo(), doc));

		// descriptors
		for(EmotionDescriptor descriptor : emotion.getDescriptors())
			ret.appendChild(export(descriptor, doc));

		// reference
		for(Reference reference : emotion.getReferences())
			ret.appendChild(export(reference, doc));

		// text
		if (emotion.hasText())
			ret.appendChild(export(emotion.getText(), doc));

		return ret;
	}


	/**
	 * Exports the given Timestamp in the given emotion Element.
	 * @param timestamp
	 * @param element
	 */
	private void export(Timestamp timestamp, Element element)
	{
		if (timestamp.hasStart())
			element.setAttribute("start", timestamp.getStart().toString());
		if (timestamp.hasEnd())
			element.setAttribute("end", timestamp.getEnd().toString());
		if (timestamp.hasDuration())
			element.setAttribute("duration", timestamp.getDuration().toString());
		if (timestamp.hasOffsetToStart())
			element.setAttribute("offset-to-start", timestamp.getOffsetToStart().toString());
		if (timestamp.hasTimeRefURI())
			element.setAttribute("time-ref-uri", timestamp.getTimeRefURI().toString());
		if (timestamp.hasTimeRefAnchorPoint())
			element.setAttribute("time-ref-anchor-point", timestamp.getTimeRefAnchorPoint().toString());
	}


	/**
	 * Exports the list of given ExpressedThrough items as a String.
	 * @param expressedThrough
	 * @return a String where each part is separated by a space character
	 */
	private String export(List<ExpressedThrough> expressedThrough)
	{
		if (expressedThrough.isEmpty())
			return "";
		else
		{
			StringBuilder ret = new StringBuilder();
			ret.append(expressedThrough.get(0));
			for(int i = 1; i < expressedThrough.size(); i++)
				ret.append(" ").append(expressedThrough.get(i));
			return ret.toString();
		}
	}


	/**
	 * Exports the given Reference as an DOM element.
	 * @param reference
	 * @param doc
	 * @return
	 */
	private Element export(Reference reference, Document doc)
	{
		Element ret = doc.createElementNS(NAMESPACE, "reference");
		ret.setAttribute("uri", reference.getURI().toString());
		ret.setAttribute("role", reference.getRole().toString());
		if (reference.hasMediaType())
			ret.setAttribute("media-type", reference.getMediaType());
		return ret;
	}


	/**
	 * Exports the given EmotionDescriptor as a DOM element.
	 * @param descriptor
	 * @param doc
	 * @return
	 */
	private Element export(EmotionDescriptor descriptor, Document doc)
	{
		Element ret = doc.createElementNS(NAMESPACE, descriptor.getType().toString());
		ret.setAttribute("name", descriptor.getName());
		if (descriptor.hasValue())
			ret.setAttribute("value", String.valueOf(descriptor.getValue()));
		if (descriptor.hasConfidence())
			ret.setAttribute("confidence", String.valueOf(descriptor.getConfidence()));
		if (descriptor.hasTrace())
			ret.appendChild(export(descriptor.getTrace(), doc));
		return ret;
	}


	/**
	 * Exports the given Trace as a DOM element.
	 * @param trace
	 * @param doc
	 * @return
	 */
	private Element export(Trace trace, Document doc)
	{
		Element ret = doc.createElementNS(NAMESPACE, "trace");
		ret.setAttribute("freq", trace.getFrequency() + "Hz");
		ret.setAttribute("samples", format(trace.getSamples()));
		return ret;
	}


	/**
	 * Exports the given Vocabulary as a DOM element.
	 * @param vocabulary
	 * @param doc
	 * @return
	 */
	private Element export(Vocabulary vocabulary, Document doc) throws EmotionMLException
	{
		Element ret = doc.createElementNS(NAMESPACE, "vocabulary");
		if (vocabulary.hasInfo())
			ret.appendChild(exportInfo(vocabulary.getInfo(), doc));
		ret.setAttribute("id", vocabulary.getId());
		ret.setAttribute("type", vocabulary.getType().toString());
		for(VocabularyItem item : vocabulary.getItems())
			ret.appendChild(export(item, doc));
		return ret;
	}


	/**
	 * Exports the given VocabularyItem as a DOM element.
	 * @param item
	 * @param doc
	 * @return
	 */
	private Element export(VocabularyItem item, Document doc) throws EmotionMLException
	{
		Element ret = doc.createElementNS(NAMESPACE, "item");
		if (item.hasInfo())
			ret.appendChild(exportInfo(item.getInfo(), doc));
		ret.setAttribute("name", item.getName());
		return ret;
	}


	/**
	 * Formats the given float as a String space-separated values.
	 * @param samples
	 * @return
	 */
	private String format(float[] samples)
	{
		if (samples.length == 0)
			return "";
		else
		{
			StringBuilder ret = new StringBuilder(String.valueOf(samples[0]));
			for(int i = 1; i < samples.length; i++)
				ret.append(" ").append(samples[i]);
			return ret.toString();
		}
	}
}
