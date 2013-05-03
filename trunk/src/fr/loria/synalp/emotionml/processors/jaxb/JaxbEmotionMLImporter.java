package fr.loria.synalp.emotionml.processors.jaxb;

import java.util.*;

import javax.xml.bind.*;

import org.w3c.dom.*;
import org.w3c.dom.Element;

import fr.loria.synalp.emotionml.exceptions.EmotionMLException;
import fr.loria.synalp.emotionml.info.Info;
import fr.loria.synalp.emotionml.processors.*;

/**
 * A JaxbEmotionMLImporter imports &lt;info&gt; elements as Info object with JAXB. When using
 * subclasses of Info, it is required that each of these classes is bound to this
 * JaxbEmotionMLImporter by calling the addClassToBeBounds method.
 * @author Alexandre Denis
 */
public class JaxbEmotionMLImporter extends EmotionMLImporter
{
	private List<Class<? extends Info>> classesToBeBound = new ArrayList<Class<? extends Info>>();


	/**
	 * Creates a new JaxbEmotionMLImporter.
	 */
	public JaxbEmotionMLImporter()
	{
	}


	/**
	 * Creates a new JaxbEmotionMLImporter based on given EmotionMLValidator.
	 * @param validator
	 */
	public JaxbEmotionMLImporter(EmotionMLValidator validator)
	{
		super(validator);
	}


	/**
	 * Adds the given classes for doing the unmarshalling.
	 * @param classes
	 * @return this JaxbEmotionMLImporter for chaining
	 */
	public JaxbEmotionMLImporter addClassToBeBounds(Class<? extends Info>... classes)
	{
		for(Class<? extends Info> c : classes)
			classesToBeBound.add(c);

		return this;
	}


	/**
	 * Returns a live list of all the classes that are defined to be bound.
	 * @return
	 */
	public List<Class<? extends Info>> getClassesToBeBounds()
	{
		return classesToBeBound;
	}


	/**
	 * Imports the given &lt;info&gt; element as an Info object. This JaxbEmotionMLImporter needs to
	 * define the subclasses of Info that will be used when unmarshalling.
	 */
	@Override
	public Info importInfo(Element element) throws EmotionMLException
	{
		Info info = super.importInfo(element);

		NodeList children = element.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
			if (children.item(i) instanceof Element)
				return createInfo(info.getId(), (Element) children.item(i));

		return info;
	}


	/**
	 * Creates an Info object by unmarshalling the given infoContent element.
	 */
	private Info createInfo(String id, Element infoContent) throws EmotionMLException
	{
		JAXBContext context = null;
		try
		{
			context = JAXBContext.newInstance(classesToBeBound.toArray(new Class<?>[classesToBeBound.size()]));
			Unmarshaller um = context.createUnmarshaller();
			return (Info) um.unmarshal(infoContent);
		}
		catch (UnmarshalException e)
		{
			if (e.getLocalizedMessage().indexOf("unexpected element") != -1)
			{
				throw new EmotionMLException("Unable to import info using jaxb. Did you bind a class for element \"" + infoContent.getLocalName() +
												"\" using the addClassesToBeBound method ? The context is:\n" + context);
			}
			else throw new EmotionMLException("Unable to import info using jaxb: " + e.getLocalizedMessage());

		}
		catch (JAXBException e)
		{
			throw new EmotionMLException("Unable to import info using jaxb: " + e.getLocalizedMessage());
		}
	}
}
