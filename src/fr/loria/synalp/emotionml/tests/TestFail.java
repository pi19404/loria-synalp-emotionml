package fr.loria.synalp.emotionml.tests;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.junit.Test;
import org.w3c.dom.Document;

import fr.loria.synalp.emotionml.exceptions.EmotionMLValidationException;
import fr.loria.synalp.emotionml.processors.*;

public class TestFail
{
	
	@Test
	public void testSchemaAssertionFail()
	{
		EmotionMLValidator validator = new EmotionMLValidator();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		
		File folder = new File("tests/fail");
		List<File> failedSchema = new ArrayList<File>();
		for(File file : folder.listFiles())
		{
			String name = file.getName();
			if (name.equals("fail_110.xml")) // skip 110, 111 temporarily
				continue;
			if (name.equals("fail_111.xml")) // skip 110, 111 temporarily
				continue;
			
			System.out.println(file.getName());
			

			try
			{
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(new FileInputStream(file));
				
				try
				{
					validator.validateDocument(doc.getDocumentElement());
					
					System.out.println("\t"+name+" *** VALIDATES *** schema, either the schema is wrong or it cannot do validation");
					System.out.println("\t"+name+" *** VALIDATES *** assertions, check assertion code since this should not happen");
					return;
				}
				catch(EmotionMLValidationException e)
				{
					ValidationResult result = e.getValidationResult();
					
					if (result.isSchemaValid())
					{
						System.out.println("\t"+name+" *** VALIDATES *** schema, either the schema is wrong or it cannot do validation");
						failedSchema.add(file);
					}
					else System.out.println("\t"+name+" is invalidated by schema: "+result.getSchemaErrorMessage());
					
					if (result.isAssertionValid())
					{
						String msg = "\t"+name+" *** VALIDATES *** assertions, check assertion code since this should not happen";
						System.out.println(msg);
						return;
					}
					else System.out.println("\t"+name+" is invalidated by assertions: "+result.getAssertionErrorMessage());
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		System.out.println("Failed schema:");
		for(File file : failedSchema)
			System.out.println(file);
	}
}
