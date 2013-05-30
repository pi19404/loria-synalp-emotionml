package fr.loria.synalp.emotionml.processors.jaxb;

import java.util.*;

import javax.xml.bind.*;
import javax.xml.transform.dom.DOMResult;

import org.w3c.dom.*;
import org.w3c.dom.Element;

import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationsException;

import fr.loria.synalp.emotionml.exceptions.EmotionMLException;
import fr.loria.synalp.emotionml.info.Info;
import fr.loria.synalp.emotionml.processors.*;

/**
 * A JaxbEmotionMLExporter is an EmotionMLExporter that exports Info objects using JAXB. It
 * maintains a JAXBContext for the export of classes: when exporting a different type of Info class
 * this type is kept in memory and the JAXBContext is regenerated. This avoids forcing to the user
 * to specify which are the Info classes that will be used, like the JaxbEmotionMLImporter.
 * @author Alexandre Denis
 */
public class JaxbEmotionMLExporter extends EmotionMLExporter
{
	private JAXBContext context;
	private Set<Class<? extends Info>> knownClasses = new HashSet<Class<? extends Info>>();


	/**
	 * Creates a new JaxbEmotionMLExporter.
	 */
	public JaxbEmotionMLExporter()
	{
		
	}


	/**
	 * Creates a new JaxbEmotionMLExporter based on given EmotionMLValidator.
	 * @param validator
	 */
	public JaxbEmotionMLExporter(EmotionMLValidator validator)
	{
		super(validator);
	}


	/**
	 * Exports the given Info instance as a DOM element belonging to the given Document. If the
	 * given instance is of class Info, a simple &lt;info&gt; element is produced. If the given
	 * instance belongs to a subclass of Info, an &lt;info&gt; element with a unique child is
	 * produced. This child is produced thanks to JAXB Marshalling. The class needs to define a @XmlRootElement
	 * with a namespace different from the EmotionML namespace and an empty constructor.
	 * @return an &lt;info&gt; element with or without a child
	 */
	@Override
	public Element exportInfo(Info info, Document doc) throws EmotionMLException
	{
		Element ret = super.exportInfo(info, doc);

		if (info.getClass() != Info.class)
		{
			try
			{
				if (!knownClasses.contains(info.getClass()))
				{
					knownClasses.add(info.getClass());
					createJAXBContext();
				}

				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

				DOMResult res = new DOMResult();
				m.marshal(info, res);

				Element element = ((Document) res.getNode()).getDocumentElement();
				doc.adoptNode(element);

				ret.appendChild(element);
			}
			catch (IllegalAnnotationsException e)
			{
				throw new EmotionMLException("Unable to export info " + info + " using jaxb: " + e.getLocalizedMessage() + ": " + e.getErrors() +
												" (check if the classes contain a valid jaxb annotation)");
			}
			catch (JAXBException e)
			{
				e.printStackTrace();
				throw new EmotionMLException("Unable to export info " + info + " using jaxb: " + e.getLocalizedMessage() +
												" (check if the classes contain a valid jaxb annotation)");
			}
		}

		return ret;
	}


	/**
	 * Creates or recreates the JAXBContext using the known classes.
	 */
	private void createJAXBContext() throws JAXBException
	{
		context = JAXBContext.newInstance(knownClasses.toArray(new Class<?>[knownClasses.size()]));
	}
}
