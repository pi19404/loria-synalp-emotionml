package fr.loria.synalp.emotionml.examples;

import java.io.File;

import fr.loria.synalp.emotionml.processors.EmotionMLImporter;

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
		importInternal(new File("tests"));
		importExternal(new File("tests/external"));
	}
	
	
	private static void importInternal(File folder)
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
	
	
	private static void importExternal(File folder)
	{
		for(File file : folder.listFiles())
		{
			if (file.isDirectory())
				continue;
			
			try
			{
				System.out.println("Importing "+file);
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
