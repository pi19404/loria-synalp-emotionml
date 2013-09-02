package fr.loria.synalp.emotionml.examples;

import java.io.*;

import fr.loria.synalp.emotionml.processors.*;
import fr.loria.synalp.emotionml.processors.io.XMLEmotionMLReader;

/**
 * This example shows how to import documents from Files.
 * @author Alexandre Denis
 *
 */
public class ImportExamples
{
	private static EmotionMLImporter importer;
	
	public static void main(String[] args)
	{
		importer = new EmotionMLImporter();
		importInternalDocuments(new File("tests"));
		validateExternalDocuments(new File("tests/external"));
	}
	
	
	private static void importInternalDocuments(File folder)
	{
		for(File file : folder.listFiles())
		{
			if (file.isDirectory())
				continue;
			
			try
			{
				System.out.println("Importing "+file);
				importer.importDocument(file);
			}
			catch(Exception e)
			{
				System.err.println("Exception while importing: "+file+": "+e.getLocalizedMessage());
				//e.printStackTrace();
			}
		}
	}
	
	
	private static void validateExternalDocuments(File folder)
	{
		XMLEmotionMLReader reader = new XMLEmotionMLReader();
		EmotionMLValidator validator = new EmotionMLValidator();
		for(File file : folder.listFiles())
		{
			if (file.isDirectory())
				continue;
			
			try
			{
				System.out.println("Importing "+file);
				validator.validateExternalDocument(reader.read(new FileInputStream(file)));
				//importer.importExternalDocument(file); // TODO
			}
			catch(Exception e)
			{
				System.err.println("Exception while importing: "+file+": "+e.getLocalizedMessage());
				//e.printStackTrace();
			}
		}
	}
}
