package fr.loria.synalp.emotionml.examples.custom.info;

import org.w3c.dom.*;

import fr.loria.synalp.emotionml.info.Info;
import fr.loria.synalp.emotionml.processors.EmotionMLExporter;

/**
 * This example shows how to extends the EmotionMLExporter to export custom Info.
 * @author Alexandre Denis
 *
 */
class CustomExporter extends EmotionMLExporter
{

	@Override
	public Element exportInfo(Info info, Document doc)
	{
		Element ret = super.exportInfo(info, doc);

		if (info instanceof ClassifierInfo)
			ret.appendChild(export((ClassifierInfo) info, doc));

		if (info instanceof LocalizationInfo)
			ret.appendChild(export((LocalizationInfo) info, doc));

		return ret;
	}


	private Element export(LocalizationInfo info, Document doc)
	{
		Element ret = doc.createElementNS(LocalizationInfo.namespace, "localization");
		ret.setAttribute("value", info.getValue());
		return ret;
	}


	private Element export(ClassifierInfo info, Document doc)
	{
		Element ret = doc.createElementNS(ClassifierInfo.namespace, "classifier");
		ret.setAttribute("name", info.getName());
		return ret;
	}

}
