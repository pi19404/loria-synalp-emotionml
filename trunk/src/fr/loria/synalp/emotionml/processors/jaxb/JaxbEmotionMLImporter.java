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
 * JaxbEmotionMLImporter by calling the addInfoClasses method.
 * @author Alexandre Denis
 */
public class JaxbEmotionMLImporter extends EmotionMLImporter
{
	private JAXBContext context;
	private Set<Class<? extends Info>> knownClasses = new HashSet<Class<? extends Info>>();


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
	 * Adds the given class for doing the unmarshalling. This method adds the given info classe and
	 * recreates the JAXBContext.
	 * @param classes
	 * @return this JaxbEmotionMLImporter for chaining
	 */
	public JaxbEmotionMLImporter addInfoClass(Class<? extends Info> c)
	{
		knownClasses.add(c);
		try
		{
			createJAXBContext();
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
		return this;
	}


	/**
	 * Adds the given classes for doing the unmarshalling. This method adds the given info classes
	 * and recreates the JAXBContext.
	 * @param classes
	 * @return this JaxbEmotionMLImporter for chaining
	 */
	public JaxbEmotionMLImporter addInfoClasses(Class<? extends Info>... classes)
	{
		for(Class<? extends Info> c : classes)
			knownClasses.add(c);

		try
		{
			createJAXBContext();
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

		return this;
	}


	/**
	 * Returns a live set of all the info classes for doing the unmarshalling. Note that if you
	 * modify the set of info classes, you will have to explicitely call createJAXBContext.
	 * @return a list of classes
	 */
	public Set<Class<? extends Info>> getInfoClasses()
	{
		return knownClasses;
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
		try
		{
			if (context == null)
				createJAXBContext();
			Unmarshaller um = context.createUnmarshaller();
			return (Info) um.unmarshal(infoContent);
		}
		catch (UnmarshalException e)
		{
			if (e.getLocalizedMessage().indexOf("unexpected element") != -1)
			{
				throw new EmotionMLException("Unable to import info using jaxb. Did you bind an info class for element \"" + infoContent.getLocalName() +
												"\" using the addInfoClasses method ? The context is:\n" + context);
			}
			else throw new EmotionMLException("Unable to import info using jaxb: " + e.getLocalizedMessage());

		}
		catch (JAXBException e)
		{
			throw new EmotionMLException("Unable to import info using jaxb: " + e.getLocalizedMessage());
		}
	}


	/**
	 * Creates or recreates the JAXBContext using the known classes.
	 */
	public void createJAXBContext() throws JAXBException
	{
		context = JAXBContext.newInstance(knownClasses.toArray(new Class<?>[knownClasses.size()]));
	}
}
