package fr.loria.synalp.emotionml.processors.jaxb;

import javax.xml.bind.*;
import javax.xml.transform.dom.DOMResult;

import org.w3c.dom.*;
import org.w3c.dom.Element;

import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationsException;

import fr.loria.synalp.emotionml.exceptions.EmotionMLException;
import fr.loria.synalp.emotionml.info.Info;
import fr.loria.synalp.emotionml.processors.*;

/**
 * A JaxbEmotionMLExporter is an EmotionMLExporter that exports Info objects using JAXB.
 * @author Alexandre Denis
 */
public class JaxbEmotionMLExporter extends EmotionMLExporter
{

	/**
	 * Creates a new JaxbEmotionMLExporter.
	 */
	public JaxbEmotionMLExporter()
	{
		super();
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
				JAXBContext context = JAXBContext.newInstance(info.getClass());
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

}
