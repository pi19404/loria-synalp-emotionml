package fr.loria.synalp.emotionml.examples.custom.info;

import org.w3c.dom.*;

import fr.loria.synalp.emotionml.info.Info;
import fr.loria.synalp.emotionml.processors.EmotionMLImporter;

/**
 * This example shows how to extends the EmotionMLImporter to import custom Info.
 * @author Alexandre Denis
 */
class CustomImporter extends EmotionMLImporter
{

	@Override
	public Info importInfo(Element element)
	{
		NodeList children = element.getChildNodes();
		for(int i=0; i<children.getLength(); i++)
		{
			Node child = children.item(i);
			if (child instanceof Element)
			{
				Element e = (Element) child;
				String name = e.getLocalName();
				if (name.equals("classifier"))
					return new ClassifierInfo(e.getAttribute("name"));
				else if (name.equals("localization"))
					return new LocalizationInfo(e.getAttribute("value"));
			}
		}
		
		return new Info();
	}

}
