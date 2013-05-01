package fr.loria.synalp.emotionml.vocabularies.predefined;

import java.io.*;
import java.net.*;

import org.xml.sax.SAXException;

import fr.loria.synalp.emotionml.EmotionMLDocument;
import fr.loria.synalp.emotionml.exceptions.*;
import fr.loria.synalp.emotionml.processors.EmotionMLImporter;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
 * This class aims to generate source files corresponding to existing Vocabularies such that it is
 * easier to refer to them.
 * @author Alexandre Denis
 */
class PredefinedVocabulariesGenerator
{
	private static boolean DO_WHOLE_VOC = false; // create a class gathering all vocabularies

	/**
	 * @param args
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws SAXException 
	 * @throws EmotionMLException 
	 */
	public static void main(String[] args) throws MalformedURLException, IOException, EmotionMLException
	{
		generateVocabularies(new EmotionMLImporter().importDocument(EmotionMLDocument.VOCABULARY_URI.toURL().openStream()));
	}
	
	
	public static void generateVocabularies(EmotionMLDocument document) throws IOException
	{
		// generate a class gathering all vocabularies
		if (DO_WHOLE_VOC)
			generateWholeClass(document);
		
		// then generate each vocabulary individually
		for(Vocabulary vocabulary : document.getVocabularies())
			generateVocabularySourceFile(vocabulary);
	}

	
	private static void generateWholeClass(EmotionMLDocument document) throws IOException
	{
		StringBuilder str = new StringBuilder();
		str.append("package fr.loria.synalp.emotionml.vocabularies.predefined;\n");
		str.append("\n");
		str.append("public final class PredefinedVocabularies\n");
		str.append("{\n");
		for(Vocabulary vocabulary : document.getVocabularies())
		{
			String className = formatClassName(vocabulary);
			str.append("\tpublic static final ").append(className).append(" ").append(formatInstance(vocabulary));
			str.append(" = new ").append(className).append("();\n");
		}
		str.append("}");
		
		File file = new File("src/fr/loria/synalp/emotionml/vocabularies/predefined/PredefinedVocabularies.java");
		file.delete();
		RandomAccessFile f = new RandomAccessFile(file, "rw");
		f.writeBytes(str.toString());
		f.close();
	}
	

	public static void generateVocabularySourceFile(Vocabulary vocabulary) throws IOException
	{
		String uri = formatURI(vocabulary);
		String type = formatType(vocabulary);
		String className = formatClassName(vocabulary);

		StringBuilder str = new StringBuilder();
		str.append("package fr.loria.synalp.emotionml.vocabularies.predefined;\n");
		str.append("import java.net.URI;\n");
		str.append("import fr.loria.synalp.emotionml.descriptors.*;\n");
		str.append("import fr.loria.synalp.emotionml.vocabularies.Vocabulary;\n");
		str.append("\n");
		str.append("/**\n");
		str.append("\tAuto-generated class corresponding to vocabulary <a href=\"").append(uri).append("\" target=\"_blank\"/>");
		str.append(uri).append("</a>\n");
		str.append("*/\n");
		str.append("public final class ").append(className).append(" extends Vocabulary\n");
		str.append("{\n");

		// add the items as descriptors
		for(VocabularyItem item : vocabulary.getItems())
		{
			str.append("\t").append("public static final ").append(type).append(" ").append(formatInstance(item));
			if (vocabulary.getType() == VocabularyType.DIMENSION)
				str.append(" = new ").append(type).append("(\"").append(item.getName()).append("\", 1.0f);");
			else str.append(" = new ").append(type).append("(\"").append(item.getName()).append("\");");
			str.append("\n");
		}
		str.append("\n");

		// add the URI to the descriptors
		str.append("\tstatic\n");
		str.append("\t{\n");
		str.append("\t\ttry\n");
		str.append("\t\t{\n");
		for(VocabularyItem item : vocabulary.getItems())
			str.append("\t\t\t").append(formatInstance(item)).append(".setURI(new URI(\"").append(uri).append("\"));\n");
		str.append("\t\t}\n");
		str.append("\t\tcatch (Exception e) { e.printStackTrace(); }\n");
		str.append("\t}");
		str.append("\n\n");

		// add constructor
		str.append("\tpublic ").append(className).append("()\n");
		str.append("\t{\n");
		str.append("\t\tsuper(\"").append(vocabulary.getId()).append("\", ");
		str.append("VocabularyType.").append(vocabulary.getType().name()).append(");\n");
		for(VocabularyItem item : vocabulary.getItems())
			str.append("\t\t").append("addItem(\"").append(item.getName()).append("\");\n");
		str.append("\t}\n");
		str.append("}");

		// write file
		File file = new File("src/fr/loria/synalp/emotionml/vocabularies/predefined/" + className + ".java");
		file.delete();
		RandomAccessFile f = new RandomAccessFile(file, "rw");
		f.writeBytes(str.toString());
		f.close();
	}


	private static String formatURI(Vocabulary vocabulary)
	{
		return EmotionMLDocument.VOCABULARY_URI + "#" + vocabulary.getId();
	}


	/**
	 * @param item
	 * @return
	 */
	private static String formatInstance(VocabularyItem item)
	{
		String name = item.getName().trim();
		name = name.replaceAll("-", "_");
		return name.toUpperCase();
	}
	
	/**
	 * @param item
	 * @return
	 */
	private static String formatInstance(Vocabulary vocabulary)
	{
		String name = vocabulary.getId().trim();
		name = name.replaceAll("-", "_");
		return name.toUpperCase();
	}


	/**
	 * @param vocabulary
	 * @return
	 */
	private static String formatClassName(Vocabulary vocabulary)
	{
		String id = vocabulary.getId().trim();
		String[] parts = id.split("-");
		StringBuilder ret = new StringBuilder();
		for(String part : parts)
			ret.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
		return ret.toString();
	}


	/**
	 * @param vocabulary
	 * @return
	 */
	private static String formatType(Vocabulary vocabulary)
	{
		switch (vocabulary.getType())
		{
			case CATEGORY:
				return "Category";

			case DIMENSION:
				return "Dimension";

			case ACTION_TENDENCY:
				return "ActionTendency";

			case APPRAISAL:
				return "Appraisal";
		}

		return null;
	}

}
