package fr.loria.synalp.emotionml.processors;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.*;

import javax.xml.XMLConstants;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import fr.loria.synalp.emotionml.EmotionMLDocument;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.descriptors.Reference.Role;
import fr.loria.synalp.emotionml.exceptions.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
 * A EmotionMLValidator is an object which validates the EmotionML elements with regards to the
 * specification (see <a href="http://www.w3.org/TR/emotionml/" target="_blank">EmotionML
 * specification</a>). It both tests if the elements are valid with regards to the EmotionML schema,
 * and with regards to the implementation assertions (see <a
 * href="http://www.w3.org/2002/mmi/2013/emotionml-ir/#test_class" target="_blank"> Assertions</a>).
 * A critical aspect of the validation is the resolution of vocabularies referenced in the emotion
 * or document. Since it may be a blocking factor, especially when validating documents offline, it
 * is possible to deactivate the resolution of vocabularies. Instances of this class are not meant
 * to be used directly but rather embedded in EmotionMLImporter and EmotionMLExporter instances.
 * @author Alexandre Denis
 * @see fr.loria.synalp.emotionml.processors.VocabularyResolver
 * @see fr.loria.synalp.emotionml.processors.EmotionMLImporter
 * @see fr.loria.synalp.emotionml.processors.EmotionMLExporter
 */
public class EmotionMLValidator
{
	private static Set<String> elements;
	private static Set<String> mediaTypes;
	private static final String EMOTIONML_SCHEMA = "fr/loria/synalp/emotionml/schemas/emotionml.xsd";
	private static final String MEDIATYPES_FILE = "/fr/loria/synalp/emotionml/schemas/mediatypes.txt";

	private Schema schema;
	private VocabularyResolver resolver;
	private boolean resolveVocabularies = true;
	private Set<String> identifiers;

	// init the elements for namespace checking
	static
	{
		elements = new HashSet<String>();
		elements.addAll(Arrays.asList(new String[] { "emotionml", "emotion", "vocabulary", "info", "reference", "item", "category", "dimension",
				"appraisal", "action-tendency", "trace" }));
	}


	/**
	 * Creates a new EmotionMLValidator which resolves vocabularies by default.
	 */
	public EmotionMLValidator()
	{
		this(true);
	}


	/**
	 * Creates a new EmotionMLValidator which given vocabulary resolution flag.
	 * @param resolveVocabularies if true, this EmotionMLValidator will retrieve the vocabularies
	 *            referenced in emotions or documents and attempt to validate them as well
	 */
	public EmotionMLValidator(boolean resolveVocabularies)
	{
		readMediaTypes();
		this.resolveVocabularies = resolveVocabularies;
		this.resolver = new VocabularyResolver();
		
		// investigate: IllegalArgumentException when creating a schema factory on android
		// we should find an alternative way of creating the schema, 
		// check https://groups.google.com/forum/?fromgroups#!topic/android-developers/F-L6kUPn5PQ
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		URL schemaURL = getClass().getClassLoader().getResource(EMOTIONML_SCHEMA);
		try
		{
			schema = sf.newSchema(schemaURL);
		}
		catch (SAXException e)
		{
			schema = null;
		}
	}


	/**
	 * Validates the given Element as an &lt;emotionml&gt; element.
	 * @param element
	 * @return the input element if it passes the validation.
	 * @throws EmotionMLValidationException
	 */
	public Element validateDocument(Element element) throws EmotionMLValidationException
	{
		boolean isSchemaValid = true;
		boolean isAssertionValid = true;
		String schemaErrorMessage = "";
		String assertionErrorMessage = "";

		identifiers = new HashSet<String>();
		resolver.setLocalDocument(element.getOwnerDocument());

		// 1- validate schema
		try
		{
			validateSchema(element);
		}
		catch (Exception e)
		{
			isSchemaValid = false;
			schemaErrorMessage = e.getLocalizedMessage();
		}

		// 2- validate assertions
		try
		{
			validateDocumentPrivate(element);
		}
		catch (Exception e)
		{
			isAssertionValid = false;
			assertionErrorMessage = e.getLocalizedMessage();
		}

		if (isSchemaValid && isAssertionValid)
			return element;
		else throw new EmotionMLValidationException(new ValidationResult(element, isSchemaValid, schemaErrorMessage, isAssertionValid, assertionErrorMessage));
	}


	/**
	 * Validates the given Element as a standalone &lt;emotion&gt; element.
	 * @param element
	 * @return the input element if it passes the validation.
	 * @throws EmotionMLValidationException
	 */
	public Element validateEmotion(Element element) throws EmotionMLValidationException
	{
		boolean isSchemaValid = true;
		boolean isAssertionValid = true;
		String schemaErrorMessage = "";
		String assertionErrorMessage = "";

		identifiers = new HashSet<String>();
		resolver.setLocalDocument(null);

		// 1- validate schema
		try
		{
			validateSchema(element);
		}
		catch (Exception e)
		{
			isSchemaValid = false;
			schemaErrorMessage = e.getLocalizedMessage();
		}

		// 2- validate assertions
		try
		{
			validateEmotionPrivate(element);
		}
		catch (Exception e)
		{
			isAssertionValid = false;
			assertionErrorMessage = e.getLocalizedMessage();
		}

		if (isSchemaValid && isAssertionValid)
			return element;
		else throw new EmotionMLValidationException(new ValidationResult(element, isSchemaValid, schemaErrorMessage, isAssertionValid, assertionErrorMessage));
	}


/////// Document Structure (Spec 2.1.1)

	/**
	 * Validates the document against the EmotionML schema.
	 * @param doc
	 * @throws IOException
	 * @throws SAXException
	 */
	private void validateSchema(Node doc) throws SAXException, IOException
	{
		schema.newValidator().validate(new DOMSource(doc));
	}


	/**
	 * Validates an EmotionML element. It validates its namespace, version, its vocabulary
	 * references and its children.
	 * @param root
	 * @throws EmotionMLException 
	 */
	private void validateDocumentPrivate(Element root) throws EmotionMLException
	{
		validateRootName(root);
		validateNamespace(root);
		validateEmotionMLVersion(root);
		validateEmotionMLDescriptorsSet(root);
		validateEmotionMLChildren(root);
		validateEmotionMLNamespaceConformance(root);
	}


	/**
	 * Validates the root name of the EmotionML document.
	 * @param root
	 * @throws EmotionMLFormatException
	 */
	private void validateRootName(Element root) throws EmotionMLFormatException
	{
		String name = root.getLocalName();
		if (!name.equals("emotionml"))
			throw new EmotionMLFormatException("101: The root element of standalone EmotionML documents MUST be <emotionml>");
	}


	/**
	 * Validates children of emotionml. It validates info, emotions and vocabularies.
	 * @param root
	 * @throws EmotionMLException 
	 */
	private void validateEmotionMLChildren(Element root) throws EmotionMLException
	{
		// we perform validation in two steps, first vocabularies and info, then emotions. The motivation for doing so is that when emotions refer to local vocabularies,
		// it is easier to first validate all vocabularies then validate all emotions, otherwise we could face a reference to a badly formatted vocabulary while validating
		// an emotion, the order "guarantees" that it will not happen
		boolean infoFound = false;
		NodeList children = root.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if (!(child instanceof Element))
				continue;

			String name = child.getLocalName();
			if (name.equals("vocabulary"))
				validateVocabulary((Element) child);
			else if (name.equals("info"))
			{
				if (infoFound)
					throw new EmotionMLFormatException("105: The <emotionml> element MAY contain a single <info> element (it contains at least two)");

				infoFound = true;
				validateInfo((Element) child);
			}
		}

		// and now do emotions
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if (child instanceof Element)
				if (child.getLocalName().equals("emotion"))
					validateEmotionPrivate((Element) child);
		}
	}


	/**
	 * Validates the namespace of the root element.
	 * @param root
	 * @throws EmotionMLFormatException
	 */
	private void validateNamespace(Element root) throws EmotionMLFormatException
	{
		String namespace = root.getNamespaceURI();
		if (namespace == null || !namespace.equals(EmotionMLDocument.NAMESPACE))
			throw new EmotionMLFormatException("102: The <emotionml> element MUST define the EmotionML namespace: \"" + EmotionMLDocument.NAMESPACE + "\"");
	}


	/**
	 * Validates the version of the root element.
	 * @param root
	 * @throws EmotionMLFormatException
	 */
	private void validateEmotionMLVersion(Element root) throws EmotionMLFormatException
	{
		// this has been temporarily disabled to prevent failure for http://www.w3.org/TR/emotion-voc/xml
		/*String version = root.getAttribute("version");
		if (version.equals(""))
			throw new EmotionMLFormatException("110: The root element of a standalone EmotionML document MUST have an attribute \"version\"");
		if (!version.equals(EmotionMLDocument.VERSION))
			throw new EmotionMLFormatException("111: The \"version\" attribute of <emotionml> MUST have the value \"" + EmotionMLDocument.VERSION + "\"");
		*/
	}


	/**
	 * Validates the descriptors set of the root element. It both validates the syntax of URI and
	 * their reference to a valid vocabulary type.
	 * @param root
	 * @throws EmotionMLException 
	 */
	private void validateEmotionMLDescriptorsSet(Element root) throws EmotionMLException
	{
		for(VocabularyType type : VocabularyType.values())
		{
			String descriptorSetURI = root.getAttribute(type.getSet());
			if (!descriptorSetURI.equals(""))
			{
				URI uri = validateEmotionMLDescriptorsSetURI(type, descriptorSetURI);
				validateEmotionMLVocabularyReference(type, uri);
			}
		}
	}


	/**
	 * Validates the given URI as a descriptor set.
	 * @param uri returns the validated URI
	 * @return
	 * @throws EmotionMLFormatException
	 */
	private URI validateEmotionMLDescriptorsSetURI(VocabularyType type, String descriptorSetURI) throws EmotionMLFormatException
	{
		try
		{
			return new URI(descriptorSetURI);
		}
		catch (URISyntaxException e)
		{
			int errorType = -1;
			if (type == VocabularyType.CATEGORY)
				errorType = 113;
			else if (type == VocabularyType.DIMENSION)
				errorType = 116;
			else if (type == VocabularyType.APPRAISAL)
				errorType = 119;
			else if (type == VocabularyType.ACTION_TENDENCY)
				errorType = 122;

			throw new EmotionMLFormatException(errorType + ": The \"" + type.getSet() +
												"\" attribute of <emotionml>, if present, MUST be of type xsd:anyURI (it is \"" + descriptorSetURI + "\")");
		}
	}


	/**
	 * Validates the type of vocabulary referred by the given URI and the given expected type.
	 * @param type
	 * @param uri
	 * @throws EmotionMLException 
	 */
	private void validateEmotionMLVocabularyReference(VocabularyType type, URI uri)
			throws EmotionMLException
	{
		if (!resolveVocabularies)
			return;

		try
		{
			Vocabulary vocabulary = resolver.retrieveVocabulary(uri);
			if (vocabulary.getType() != type)
			{
				int errorType = -1;
				if (type == VocabularyType.CATEGORY)
					errorType = 114;
				else if (type == VocabularyType.DIMENSION)
					errorType = 117;
				else if (type == VocabularyType.APPRAISAL)
					errorType = 120;
				else if (type == VocabularyType.ACTION_TENDENCY)
					errorType = 123;
				throw new EmotionMLFormatException(errorType + ": The \"" + type.getSet() +
													"\" attribute of <emotionml>, if present, MUST refer to the ID of a <vocabulary>" +
													" element with type=\"" + type + "\" (it is \"" + vocabulary.getType() + "\")");
			}
		}
		catch (EmotionMLResolutionException e)
		{
			throw e;
		}
	}


/////// Document Structure (Spec 2.1.2)	

	/**
	 * Validates an emotion element.
	 * @param emotion
	 * @throws EmotionMLException 
	 */
	private void validateEmotionPrivate(Element emotion) throws EmotionMLException
	{
		validateEmotionDescriptorsSet(emotion);
		validateEmotionVersion(emotion);
		validateEmotionId(emotion);
		validateEmotionExpressThrough(emotion);
		validateEmotionTiming(emotion);
		validateEmotionChildren(emotion);
	}


	/**
	 * Validates all children of the given emotion element.
	 * @param emotion
	 * @throws EmotionMLException 
	 */
	private void validateEmotionChildren(Element emotion) throws EmotionMLException
	{
		boolean infoFound = false;
		boolean descriptorFound = false;
		Map<VocabularyType, Set<String>> descriptorNames = new HashMap<VocabularyType, Set<String>>();

		NodeList children = emotion.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node node = children.item(i);

			if (!(node instanceof Element))
				continue;

			String name = node.getLocalName();
			if (name.equals("reference"))
				validateReference((Element) node);
			else if (name.equals("category") || name.equals("dimension") || name.equals("action-tendency") || name.equals("appraisal"))
			{
				descriptorFound = true;
				validateDescriptor((Element) node, descriptorNames);
			}
			else if (name.equals("info"))
			{
				if (infoFound)
					throw new EmotionMLFormatException("155: The <emotion> element MAY contain a single <info> element");

				infoFound = true;
				validateInfo((Element) node);
			}
		}

		if (!descriptorFound)
			throw new EmotionMLFormatException("156: The <emotion> element MUST contain at least one <category> or " +
												"<dimension> or <appraisal> or <action-tendency> element");
	}


	// 160 - 170
	private void validateEmotionDescriptorsSet(Element emotion) throws EmotionMLException
	{
		for(VocabularyType type : VocabularyType.values())
		{
			String descriptorSet = emotion.getAttribute(type.getSet());
			if (!descriptorSet.equals(""))
			{
				URI uri = validateEmotionDescriptorSetURI(descriptorSet, type);
				validateEmotionVocabularyReference(uri, type);
			}
		}
	}


	private URI validateEmotionDescriptorSetURI(String descriptorSetURI, VocabularyType type) throws EmotionMLFormatException
	{
		try
		{
			return new URI(descriptorSetURI);
		}
		catch (URISyntaxException e)
		{
			int errorType = -1;
			if (type == VocabularyType.CATEGORY)
				errorType = 160;
			else if (type == VocabularyType.DIMENSION)
				errorType = 163;
			else if (type == VocabularyType.APPRAISAL)
				errorType = 166;
			else if (type == VocabularyType.ACTION_TENDENCY)
				errorType = 169;
			throw new EmotionMLFormatException(errorType + ": The \"" + type.getSet() + "\" attribute of <emotion>," +
												" if present, MUST be of type xsd:anyURI (it is \"" + descriptorSetURI + "\")");
		}
	}


	private Vocabulary validateEmotionVocabularyReference(URI uri, VocabularyType type) throws EmotionMLException
	{
		if (!resolveVocabularies)
			return null;

		try
		{
			Vocabulary vocabulary = resolver.retrieveVocabulary(uri);
			if (vocabulary.getType() != type)
			{
				int errorType = -1;
				if (type == VocabularyType.CATEGORY)
					errorType = 161;
				else if (type == VocabularyType.DIMENSION)
					errorType = 164;
				else if (type == VocabularyType.APPRAISAL)
					errorType = 167;
				else if (type == VocabularyType.ACTION_TENDENCY)
					errorType = 170;
				throw new EmotionMLFormatException(errorType + ": The \"" + type.getSet() + "\" attribute of <emotion>," +
													" if present, MUST refer to the ID of a <vocabulary> element with type=\"" +
													type + "\" (it is \"" + vocabulary.getType() + "\")");
			}
			return vocabulary;
		}
		catch (EmotionMLResolutionException e)
		{
			throw e;
		}
	}


	// 171 - 172
	private void validateEmotionVersion(Element emotion) throws EmotionMLFormatException
	{
		String version = emotion.getAttribute("version");
		if (!version.equals("") && !version.equals(EmotionMLDocument.VERSION))
			throw new EmotionMLFormatException("172: The \"version\" attribute of <emotion>, if present, MUST " +
												"have the value \"" + EmotionMLDocument.VERSION + "\" (it is \"" + version + "\")");
	}


	// 174
	private void validateEmotionId(Element emotion) throws EmotionMLFormatException
	{
		String id = emotion.getAttribute("id");
		if (!id.equals(""))
		{
			if (id.indexOf(":") != -1) // TODO: a proper definition of xsd:ID
				throw new EmotionMLFormatException("174: The \"id\" attribute of <emotion>, if present, MUST " +
													"be of type xsd:ID (it is \"" + id + "\")");
			if (identifiers.contains(id))
				throw new EmotionMLFormatException("174: The \"id\" attribute of <emotion>, if present, MUST " +
													"be of type xsd:ID (it is \"" + id + "\" and there already exists an element with that identifier)");
			else identifiers.add(id);
		}
	}


/////// Representations of emotions and related states

	/**
	 * Validates the given emotion descriptor. It validates its name, the presence of a descriptor
	 * set for its type, the actual existence of a vocabulary containing the name, the presence of
	 * trace or value, and the confidence.
	 * @param descriptor
	 * @throws EmotionMLException 
	 */
	private void validateDescriptor(Element descriptor, Map<VocabularyType, Set<String>> descriptorNames) throws EmotionMLException
	{
		VocabularyType type = VocabularyType.parse(descriptor.getLocalName());

		String name = validateEmotionDescriptorName(descriptor, type);
		validateDescriptorNameUnicity(type, name, descriptorNames);

		String descriptorSetURI = validateEmotionDescriptorSetPresence((Element) descriptor.getParentNode(), type);
		validateDescriptorNameReference(name, descriptorSetURI, type);

		validateConfidence(descriptor);
		validateScaleValues(descriptor, type);
	}


	/**
	 * Validates the unicity of a descriptor name inside an emotion.
	 * @param descriptor
	 * @param descriptorNames the map of already validated descriptor names
	 * @throws EmotionMLFormatException
	 */
	private void validateDescriptorNameUnicity(VocabularyType type, String name, Map<VocabularyType, Set<String>> descriptorNames)
			throws EmotionMLFormatException
	{
		if (!descriptorNames.containsKey(type))
			descriptorNames.put(type, new HashSet<String>());

		if (descriptorNames.get(type).contains(name))
		{
			int errorType = -1;
			if (type == VocabularyType.CATEGORY)
				errorType = 213;
			else if (type == VocabularyType.DIMENSION)
				errorType = 223;
			else if (type == VocabularyType.APPRAISAL)
				errorType = 233;
			else if (type == VocabularyType.ACTION_TENDENCY)
				errorType = 243;
			throw new EmotionMLFormatException(errorType + ": For any given " + type + " name in the set, zero or one occurrence" +
												" is allowed within an <" + type + "> element, i.e. a " + type + " with name \"x\" MUST" +
												" NOT appear twice in one <emotion> element (\"" + name + "\" appears at least twice)");
		}
		else descriptorNames.get(type).add(name);
	}


	/**
	 * Validates the name of a descriptor that must refer to an existing vocabulary item.
	 * @param name
	 * @param descriptorSetURI
	 * @param type
	 * @throws EmotionMLException 
	 */
	private void validateDescriptorNameReference(String name, String descriptorSetURI, VocabularyType type) throws EmotionMLException
	{
		// there might be a misleading message here depending on where the descriptor set URI has been found
		// since the descriptor set URI may also be found at the emotionml level
		URI uri = validateEmotionDescriptorSetURI(descriptorSetURI, type);
		Vocabulary vocabulary = validateEmotionVocabularyReference(uri, type);

		if (vocabulary != null && !vocabulary.getItemNames().contains(name))
		{
			int errorType = -1;
			if (type == VocabularyType.CATEGORY)
				errorType = 212;
			else if (type == VocabularyType.DIMENSION)
				errorType = 222;
			else if (type == VocabularyType.APPRAISAL)
				errorType = 232;
			else if (type == VocabularyType.ACTION_TENDENCY)
				errorType = 242;
			throw new EmotionMLFormatException(errorType + ": The value of the \"name\" attribute of the <" + type + "> element MUST" +
												" be contained in the declared category vocabulary " +
												"(the name is \"" + name + "\" but the declared names for \"" + uri + "\" are " + vocabulary.getItemNames() + ")");
		}
	}


	/**
	 * Validates the presence inside the given emotion of a descriptor set for the given type. If it
	 * is not found in the given emotion, this method tries to find it at the emotionml document
	 * level if possible.
	 * @param emotion
	 * @param type
	 * @return the found descriptor set URI
	 * @throws EmotionMLFormatException
	 */
	private String validateEmotionDescriptorSetPresence(Element emotion, VocabularyType type) throws EmotionMLFormatException
	{
		String descriptorSet = emotion.getAttribute(type.getSet());
		if (descriptorSet.equals(""))
		{
			int errorType = -1;
			if (type == VocabularyType.CATEGORY)
				errorType = 210;
			else if (type == VocabularyType.DIMENSION)
				errorType = 220;
			else if (type == VocabularyType.APPRAISAL)
				errorType = 230;
			else if (type == VocabularyType.ACTION_TENDENCY)
				errorType = 240;

			String message = errorType + ": If the <" + type + "> element is used, a " + type.toString().replaceAll("-", " ") +
								" vocabulary MUST be declared using a \"" + type.getSet() + "\" attribute on either " +
								"the enclosing <emotion> element or the root element <emotionml>";

			// retrieve from document level
			Node parent = emotion.getParentNode();
			if (parent == null || parent instanceof Document)
				throw new EmotionMLFormatException(message + " (the <emotion> has none and has no <emotionml> parent)");
			else
			{
				Element root = (Element) parent;
				descriptorSet = root.getAttribute(type.getSet());
				if (descriptorSet.equals(""))
					throw new EmotionMLFormatException(message + " (the <emotion> has none nor its <emotionml> parent)");
			}
		}

		return descriptorSet;
	}


	/**
	 * Validates the existence of a name attribute inside the given descriptor.
	 * @param descriptor
	 * @param type
	 * @return the name
	 * @throws EmotionMLFormatException
	 */
	private String validateEmotionDescriptorName(Element descriptor, VocabularyType type) throws EmotionMLFormatException
	{
		String name = descriptor.getAttribute("name");

		if (name.equals(""))
		{
			int errorType = -1;
			if (type == VocabularyType.CATEGORY)
				errorType = 211;
			else if (type == VocabularyType.DIMENSION)
				errorType = 221;
			else if (type == VocabularyType.APPRAISAL)
				errorType = 231;
			else if (type == VocabularyType.ACTION_TENDENCY)
				errorType = 241;
			throw new EmotionMLFormatException(errorType + ": A <" + type + "> element MUST contain a \"name\" attribute");
		}
		else return name;
	}


	/**
	 * Validates the scale values of given descriptor (value or trace).
	 * @param descriptor
	 * @param type
	 * @throws EmotionMLFormatException
	 */
	private void validateScaleValues(Element descriptor, VocabularyType type) throws EmotionMLFormatException
	{
		boolean hasValue = validateValue(descriptor);
		boolean hasTrace = validateTrace(descriptor);

		if (type == VocabularyType.DIMENSION)
		{
			if (hasValue == hasTrace)
				throw new EmotionMLFormatException("224: A <dimension> MUST contain either a \"value\" attribute or a <trace> element");
		}
		else
		{
			if (hasValue && hasTrace)
			{
				int errorType = -1;
				if (type == VocabularyType.CATEGORY)
					errorType = 216;
				else if (type == VocabularyType.APPRAISAL)
					errorType = 236;
				else if (type == VocabularyType.ACTION_TENDENCY)
					errorType = 246;
				throw new EmotionMLFormatException(errorType + ": A <" + type + "> MUST not contain both a \"value\" attribute " +
													"and a <trace> element");
			}
		}
	}


/////// Meta-information

	/**
	 * Validates the confidence value of the given descriptor if it exists.
	 * @param descriptor
	 * @throws EmotionMLFormatException
	 */
	private void validateConfidence(Element descriptor) throws EmotionMLFormatException
	{
		String confidenceStr = descriptor.getAttribute("confidence");
		if (!confidenceStr.equals(""))
			try
			{
				float floatValue = Float.parseFloat(confidenceStr);
				if (floatValue < 0 || floatValue > 1)
					throw new NumberFormatException();
			}
			catch (NumberFormatException e)
			{
				throw new EmotionMLFormatException("300: The value of the \"confidence\" attribute MUST be a floating point " +
													"number in the closed interval [0, 1] (it is \"" + confidenceStr + "\")");
			}
	}


	/**
	 * Validates the expressed-through attribute of the given emotion element if it exists.
	 * @param emotion
	 * @throws EmotionMLFormatException
	 */
	private void validateEmotionExpressThrough(Element emotion) throws EmotionMLFormatException
	{
		// there might be a problem here, what happens if expressed-through is present but the empty list ?
		String expressedThroughStr = emotion.getAttribute("expressed-through");
		if (!expressedThroughStr.equals(""))
		{
			String[] parts = expressedThroughStr.split(" ");
			if (parts.length == 0)
				throw new EmotionMLFormatException("301: The attribute \"expressed-through\" of the <emotion> element, if present," +
													" MUST be of type xsd:nmtokens (it is \"" + expressedThroughStr + "\")");
			else for(String part : parts)
				if (part.indexOf(",") != -1)
					new EmotionMLFormatException("301: The attribute \"expressed-through\" of the <emotion> element, if present," +
													" MUST be of type xsd:nmtokens (it contains the value \"" + part + "\")");
		}
	}


	/**
	 * Validates an info element. It validates its id and the namespace of its children.
	 * @param info
	 * @throws EmotionMLFormatException
	 */
	private void validateInfo(Element info) throws EmotionMLFormatException
	{
		validateInfoExternalNamespace(info);
		validateInfoId(info);
	}


	/**
	 * Validates the namespace of children of the given info element.
	 * @param info
	 * @throws EmotionMLFormatException
	 */
	private void validateInfoExternalNamespace(Element info) throws EmotionMLFormatException
	{
		NodeList nodes = info.getElementsByTagName("*");
		for(int i = 0; i < nodes.getLength(); i++)
		{
			String namespace = nodes.item(i).getNamespaceURI();
			if (namespace != null && namespace.equals("http://www.w3.org/2009/10/emotionml"))
				throw new EmotionMLFormatException("304: The <info> element MUST NOT contain any elements in the EmotionML " +
													"namespace, \"http://www.w3.org/2009/10/emotionml\" (it contains \"" + nodes.item(i) + "\")");
		}
	}


	/**
	 * Validates the id of the given info element.
	 * @param info
	 * @throws EmotionMLFormatException
	 */
	private void validateInfoId(Element info) throws EmotionMLFormatException
	{
		String id = info.getAttribute("id");
		if (!id.equals(""))
		{
			if (id.indexOf(":") != -1) // TODO: a proper definition of xsd:ID
				throw new EmotionMLFormatException("306: The \"id\" attribute of the <info> element, if present, MUST be of type " +
													"xsd:ID (it is \"" + id + "\")");

			if (identifiers.contains(id))
				throw new EmotionMLFormatException("306: The \"id\" attribute of the <info> element, if present, MUST be of type " +
													"xsd:ID (it is \"" + id + "\" and there already exists an element with that identifier)");
			else identifiers.add(id);
		}
	}


/////// Reference and time

	/**
	 * Validates the given reference element.
	 * @param reference
	 * @throws EmotionMLFormatException
	 */
	private void validateReference(Element reference) throws EmotionMLFormatException
	{
		validateReferenceURI(reference);
		validateReferenceRole(reference);
		validateReferenceMediaType(reference);
	}


	// 410, 411
	private void validateReferenceURI(Element reference) throws EmotionMLFormatException
	{
		String uri = reference.getAttribute("uri");
		if (uri.equals(""))
			throw new EmotionMLFormatException("410: The <reference> element MUST contain a \"uri\" attribute");
		try
		{
			new URI(uri);
		}
		catch (URISyntaxException e)
		{
			throw new EmotionMLFormatException("411: The \"uri\" attribute of <reference> MUST be of type xsd:anyURI (it is \"" + uri + "\")");
		}
	}


	// 414
	private void validateReferenceRole(Element reference) throws EmotionMLFormatException
	{
		String roleStr = reference.getAttribute("role");
		if (!roleStr.equals(""))
		{
			Role role = Reference.Role.parse(roleStr);
			if (role == null)
				throw new EmotionMLFormatException("414: The value of the \"role\" attribute of the <reference> element, if present," +
													" MUST be one of \"expressedBy\", \"experiencedBy\", \"triggeredBy\", \"targetedAt\"" +
													" (it is \"" + roleStr + "\")");
		}
	}


	// 416, 417
	private void validateReferenceMediaType(Element reference) throws EmotionMLFormatException
	{
		String mediaType = reference.getAttribute("media-type");
		if (!mediaType.equals(""))
		{
			if (!mediaTypes.contains(mediaType))
				throw new EmotionMLFormatException("417: The value of the \"media-type\" attribute of the <reference> element, if " +
													"present, MUST be a valid MIME type (it is \"" + mediaType + "\", check " +
													"http://www.iana.org/assignments/media-types)");
		}
	}


	// 420 - 425
	private void validateEmotionTiming(Element emotion) throws EmotionMLFormatException
	{
		validateNonNegativeInteger(emotion, "start", 420);
		validateNonNegativeInteger(emotion, "end", 421);
		validateNonNegativeInteger(emotion, "duration", 422);
		validateEmotionTimeRefURI(emotion);
		validateEmotionTimeRefAnchorPoint(emotion);
		validateEmotionOffsetToStart(emotion);
	}


	// 423
	private void validateEmotionTimeRefURI(Element emotion) throws EmotionMLFormatException
	{
		String uri = emotion.getAttribute("time-ref-uri");
		if (!uri.equals(""))
		{
			try
			{
				new URI(uri);
			}
			catch (URISyntaxException e)
			{
				throw new EmotionMLFormatException("423: The value of the \"time-ref-uri\" attribute of <emotion>, if present," +
													" MUST be of type xsd:anyURI (it is \"" + uri + "\")");
			}
		}
	}


	// 424
	private void validateEmotionTimeRefAnchorPoint(Element emotion) throws EmotionMLFormatException
	{
		String timeRefAnchorPoint = emotion.getAttribute("time-ref-anchor-point");
		if (!timeRefAnchorPoint.equals(""))
			if (Timestamp.TimeRefAnchorPoint.parse(timeRefAnchorPoint) == null)
				throw new EmotionMLFormatException("424: The value of the \"time-ref-anchor-point\" attribute of <emotion>, if present," +
													" MUST be either \"start\" or \"end\" (it is \"" + timeRefAnchorPoint + "\")");
	}


	// 425
	private void validateEmotionOffsetToStart(Element emotion) throws EmotionMLFormatException
	{
		String offsetToStart = emotion.getAttribute("offset-to-start");
		if (!offsetToStart.equals(""))
		{
			try
			{
				Integer.parseInt(offsetToStart);
			}
			catch (NumberFormatException e)
			{
				throw new EmotionMLFormatException("425: The value of the \"offset-to-start\" attribute of <emotion>, if present," +
													" MUST be of type xsd:integer (it is \"" + offsetToStart + "\")");
			}
		}
	}


	/**
	 * Validates that the attribute of the given Element with given name is a non-negative integer.
	 * @param emotion
	 * @param name
	 * @throws EmotionMLFormatException
	 */
	private void validateNonNegativeInteger(Element emotion, String name, int errorType) throws EmotionMLFormatException
	{
		String valueStr = emotion.getAttribute(name);
		if (!valueStr.equals(""))
		{
			try
			{
				int value = new BigInteger(valueStr).signum();
				if (value < 0)
					throw new NumberFormatException();
			}
			catch (NumberFormatException e)
			{
				throw new EmotionMLFormatException(errorType + ": The value of the \"" + name + "\" attribute of <emotion>, if present," +
													" MUST be of type xsd:nonNegativeInteger (it is \"" + valueStr + "\")");
			}
		}
	}


/////// Scale values

	/**
	 * Validates the value attribute of the given descriptor element if it exists.
	 * @param descriptor
	 * @return true if it exists and is valid, false otherwise
	 * @throws EmotionMLFormatException
	 */
	private boolean validateValue(Element descriptor) throws EmotionMLFormatException
	{
		String valueStr = descriptor.getAttribute("value");
		if (valueStr.equals(""))
			return false;
		else try
		{
			float floatValue = Float.parseFloat(valueStr);
			if (floatValue < 0 || floatValue > 1)
				throw new NumberFormatException();
			else return true;
		}
		catch (NumberFormatException e)
		{
			throw new EmotionMLFormatException("500: The value of a \"value\" attribute, if present, MUST be a floating point" +
												" value from the closed interval [0, 1] (it is \"" + valueStr + "\")");
		}
	}


	/**
	 * Validates the trace of given descriptor if it exists.
	 * @param descriptor an element that might contain a trace child
	 * @return true if there exists a trace and it is valid, false otherwise
	 * @throws EmotionMLFormatException
	 */
	private boolean validateTrace(Element descriptor) throws EmotionMLFormatException
	{
		NodeList children = descriptor.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if (!(child instanceof Element))
				continue;

			if (child.getLocalName().equals("trace"))
			{
				Element trace = (Element) child;
				validateTraceFreq(trace);
				validateTraceSamples(trace);
				return true;
			}
		}
		return false;
	}


	/**
	 * Validates the frequency attribute of the given trace element.
	 * @param trace
	 * @throws EmotionMLFormatException
	 */
	private void validateTraceFreq(Element trace) throws EmotionMLFormatException
	{
		String freqStr = trace.getAttribute("freq");
		if (freqStr.equals(""))
			throw new EmotionMLFormatException("501: The <trace> element MUST have a \"freq\" attribute");

		String message = "502: The value of the \"freq\" attribute of <trace> MUST be a positive floating point number " +
							"followed by optional whitespace followed by \"Hz\" (it is \"" + freqStr + "\")";

		if (!freqStr.endsWith("Hz"))
			throw new EmotionMLFormatException(message);

		String floatStr = freqStr.substring(0, freqStr.indexOf("Hz"));

		try
		{
			float floatValue = Float.parseFloat(floatStr);
			if (floatValue < 0)
				throw new NumberFormatException();
		}
		catch (NumberFormatException e)
		{
			throw new EmotionMLFormatException(message);
		}
	}


	/**
	 * Validates the samples of the given trace element.
	 * @param trace
	 * @throws EmotionMLFormatException
	 */
	private void validateTraceSamples(Element trace) throws EmotionMLFormatException
	{
		String samplesStr = trace.getAttribute("samples");
		if (samplesStr.equals(""))
			throw new EmotionMLFormatException("503: The <trace> element MUST have a \"samples\" attribute");
		String[] parts = samplesStr.split(" ");
		for(String part : parts)
		{
			try
			{
				float floatValue = Float.parseFloat(part);
				if (floatValue < 0 || floatValue > 1)
					throw new NumberFormatException();
			}
			catch (NumberFormatException e)
			{
				throw new EmotionMLFormatException("504: The value of the \"samples\" attribute of <trace> MUST be " +
													"a space-separated list of floating point values from the closed interval" +
													" [0, 1] (it contains value \"" + part + "\")");
			}
		}
	}


/////// Defining vocabularies for representing emotions

	/**
	 * Validates given Vocabulary.
	 * @param vocabulary
	 * @throws EmotionMLFormatException
	 */
	private void validateVocabulary(Element vocabulary) throws EmotionMLFormatException
	{
		validateVocabularyChildren(vocabulary);
		validateVocabularyType(vocabulary);
		validateVocabularyId(vocabulary);
	}


	/**
	 * @param vocabulary
	 * @throws EmotionMLFormatException
	 */
	private void validateVocabularyType(Element vocabulary) throws EmotionMLFormatException
	{
		String typeStr = vocabulary.getAttribute("type");
		if (typeStr.equals(""))
			throw new EmotionMLFormatException("602: A <vocabulary> element MUST contain a \"type\" attribute");

		if (VocabularyType.parse(typeStr) == null)
			throw new EmotionMLFormatException("603: The value of the \"type\" attribute of the <vocabulary>" +
												" element MUST be one of \"category\", \"dimension\", \"action-tendency\"" +
												" or \"appraisal\" (it is \"" + typeStr + "\")");
	}


	/**
	 * @param vocabulary
	 * @throws EmotionMLFormatException
	 */
	private void validateVocabularyId(Element vocabulary) throws EmotionMLFormatException
	{
		String id = vocabulary.getAttribute("id");
		if (id.equals(""))
			throw new EmotionMLFormatException("604: A <vocabulary> element MUST contain an \"id\" attribute");

		if (id.indexOf(":") != -1) // should fine a proper definition of xsd:ID, unicity ?
			throw new EmotionMLFormatException("605: The value of the \"id\" attribute of the <vocabulary> " +
												"element MUST be of type xsd:ID (it is \"" + id + "\")");

		if (identifiers.contains(id))
			throw new EmotionMLFormatException("605: The value of the \"id\" attribute of the <vocabulary> " +
												"element MUST be of type xsd:ID (it is \"" + id + "\" and there already exists an element with that identifier)");
		else identifiers.add(id);
	}


	/**
	 * @param vocabulary
	 * @throws EmotionMLFormatException
	 */
	private void validateVocabularyChildren(Element vocabulary) throws EmotionMLFormatException
	{
		boolean infoFound = false;
		Set<String> itemNames = new HashSet<String>();
		NodeList children = vocabulary.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);

			if (!(child instanceof Element))
				continue;

			String name = child.getLocalName();
			if (name.equals("item"))
				validateVocabularyItem((Element) child, itemNames);
			else if (name.equals("info"))
			{
				if (infoFound)
					throw new EmotionMLFormatException("601: A <vocabulary> element MAY contain a single <info> element (it contains at least two)");

				infoFound = true;
				validateInfo((Element) child);
			}
		}

		if (itemNames.isEmpty())
			throw new EmotionMLFormatException("600: A <vocabulary> element MUST contain one or more <item> elements");
	}


	/**
	 * @param child
	 */
	private void validateVocabularyItem(Element item, Set<String> itemNames) throws EmotionMLFormatException
	{
		String name = item.getAttribute("name");
		if (name.equals(""))
			throw new EmotionMLFormatException("607: An <item> element MUST contain a \"name\" attribute");

		if (itemNames.contains(name))
			throw new EmotionMLFormatException("608: An <item> MUST NOT have the same name as any other " +
												"<item> within the same <vocabulary> (it already contains \"" + name + "\")");

		// add restriction xsd:NMTOKEN ?
		
		itemNames.add(name);

		NodeList children = item.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);

			if (!(child instanceof Element))
				continue;

			if (child.getLocalName().equals("info"))
				validateInfo((Element) child); // 606
		}
	}


/////// Conformance

	/**
	 * Recursively test the namespace of the given element.
	 * @param element
	 * @throws EmotionMLFormatException
	 */
	private void validateEmotionMLNamespaceConformance(Element element) throws EmotionMLFormatException
	{
		NodeList children = element.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if (child instanceof Element)
			{
				Element e = (Element) child;
				String name = e.getLocalName();
				if (elements.contains(name))
				{
					String namespace = e.getNamespaceURI();
					if (namespace == null)
						throw new EmotionMLFormatException("700: All EmotionML elements MUST use the EmotionML namespace, " +
															"\"http://www.w3.org/2009/10/emotionml\" (an element with name \"" +
															name + "\" was found in an unspecified namespace)");
					else if (!namespace.equals(EmotionMLDocument.NAMESPACE))
						throw new EmotionMLFormatException("700: All EmotionML elements MUST use the EmotionML namespace, " +
															"\"http://www.w3.org/2009/10/emotionml\" (an element with name \"" +
															name + "\" was found in namespace \"" + namespace + "\")");
					else validateEmotionMLNamespaceConformance(e);
				}
			}
		}
	}


/////// Other

	/**
	 * Sets whether this EmotionMLValidator must try to resolve vocabularies.
	 * @param resolveVocabularies if true, this EmotionMLValidator will retrieve the vocabularies
	 *            referenced in emotions or documents and attempt to validate them as well
	 * @return this EmotionMLValidator to easily chain methods
	 */
	public EmotionMLValidator setResolveVocabularies(boolean resolveVocabularies)
	{
		this.resolveVocabularies = resolveVocabularies;
		return this;
	}


	/**
	 * Tests whether this EmotionMLValidator must try to resolve vocabularies.
	 * @return true if this EmotionMLValidator must try to resolve vocabularies.
	 */
	public boolean doesResolveVocabularies()
	{
		return resolveVocabularies;
	}


	/**
	 * Reads the media types file.
	 */
	private void readMediaTypes()
	{
		if (mediaTypes == null)
		{
			mediaTypes = new HashSet<String>();
			try
			{
				InputStreamReader is = new InputStreamReader(getClass().getResourceAsStream(MEDIATYPES_FILE));
				BufferedReader br = new BufferedReader(is);

				String line;
				while((line = br.readLine()) != null)
					mediaTypes.add(line.trim());
				br.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
