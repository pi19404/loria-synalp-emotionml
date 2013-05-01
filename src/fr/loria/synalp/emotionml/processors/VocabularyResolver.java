package fr.loria.synalp.emotionml.processors;

import java.io.*;
import java.net.*;
import java.util.*;

import org.w3c.dom.*;

import fr.loria.synalp.emotionml.EmotionMLDocument;
import fr.loria.synalp.emotionml.exceptions.*;
import fr.loria.synalp.emotionml.vocabularies.Vocabulary;

/**
 * A VocabularyResolvert helps the retrieval and parsing of documents during a serialization or
 * deserialization operation. It holds a reference to a local document for resolving local URIs and
 * a map to cache externally resolved URIs. The local document is a DOM Document because the
 * vocabulary resolution may happen while importing a document, that is while the document is not
 * fully imported yet.
 * @author adenis
 */
public class VocabularyResolver
{
	private Document localDocument;
	private Map<URI, EmotionMLDocument> documentsCache;


	/**
	 * Creates a new VocabularyResolver.
	 */
	public VocabularyResolver()
	{
		this.localDocument = null;
		this.documentsCache = new HashMap<URI, EmotionMLDocument>();
	}


	/**
	 * Returns the current documents cache. Any modifications on the given cache will be used by the
	 * resolver.
	 * @return the actual cache
	 */
	public Map<URI, EmotionMLDocument> getDocumentsCache()
	{
		return documentsCache;
	}


	/**
	 * Returns the currently defined local document.
	 * @return the currently defined local document
	 */
	public Document getLocalDocument()
	{
		return localDocument;
	}


	/**
	 * Sets the local document which is the document for resolving local URIs.
	 * @param document
	 */
	public void setLocalDocument(Document document)
	{
		this.localDocument = document;
	}


	/**
	 * Returns the Vocabulary in the local document with given id.
	 */
	private Vocabulary retrieveVocabularyFromLocalDocument(String id)
	{
		// first get the element
		Element vocabularyElement = null;
		NodeList children = localDocument.getDocumentElement().getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if (child instanceof Element)
			{
				Element e = (Element) child;
				if (e.getLocalName().equals("vocabulary") && e.getAttribute("id").equals(id))
				{
					vocabularyElement = e;
					break;
				}
			}
		}

		// then import it
		if (vocabularyElement == null)
			return null;
		else return new EmotionMLImporter().importVocabulary(vocabularyElement);
	}


	/**
	 * Retrieves a Vocabulary with given URI. If the URI is local to a document, resolve it against
	 * the local document, else check first in the cache if the URI has been already resolved,
	 * eventually retrieve the document and parse it.
	 * @param uri
	 * @return null if not found
	 */
	public Vocabulary retrieveVocabulary(URI uri) throws EmotionMLResolutionException, EmotionMLFormatException
	{
		String id = uri.getFragment();

		if (id == null)
			throw new EmotionMLResolutionException("Unable to retrieve vocabulary from URI <" + uri +
													"> since it does not contain a fragment identifier that would identify the vocabulary "
													+"(the syntax is \"<URI of document>#<Vocabulary identifier>\", for instance \"http://www.w3.org/TR/emotion-voc/xml#big6\" ; "+
													"the URI of document may be empty when referring to a vocabulary inside the same document, for instance \"#big6\")");

		// assume that the URI is local
		if (uri.getHost() == null && (uri.getPath() == null || uri.getPath().equals("")))
		{
			if (localDocument == null)
				throw new EmotionMLResolutionException("Unable to retrieve local vocabulary from id \"" + id +
														"\" since there is no defined local document where to look for it (call setLocalDocument prior to retrieveVocabulary)");

			// now search in the local document for a Vocabulary with that id

			Vocabulary ret = retrieveVocabularyFromLocalDocument(id);
			if (ret == null)
				throw new EmotionMLResolutionException("Unable to retrieve local vocabulary from id \"" + id +
														"\" since it is not found in the defined local document");
			else return ret;
		}
		else
		{
			if (documentsCache.containsKey(uri))
			{
				EmotionMLDocument document = documentsCache.get(uri);
				Vocabulary ret = document.getVocabulary(id);
				if (ret == null)
					throw new EmotionMLResolutionException("Unable to retrieve vocabulary from id \"" + id +
															"\" since it is not found in the cached document corresponding to <" +
															uri + ">");
				else return ret;
			}
			else
			{
				URL url;
				try
				{
					url = uri.toURL();
				}
				catch (MalformedURLException e)
				{
					throw new EmotionMLResolutionException("Unable to retrieve vocabulary from URI <" + uri + "> since it is malformed: " + e.getMessage());
				}

				BufferedReader in;
				try
				{
					in = new BufferedReader(new InputStreamReader(url.openStream()));
				}
				catch (IOException e)
				{
					throw new EmotionMLResolutionException("Unable to retrieve vocabulary from URI <" + uri +
															">, check the URL since there has been an IO exception while opening the stream: " +
															e.getMessage());
				}

				String line;
				StringBuilder str = new StringBuilder();
				try
				{
					while((line = in.readLine()) != null)
						str.append(line);
				}
				catch (IOException e)
				{
					throw new EmotionMLResolutionException("Unable to retrieve vocabulary from URI <" + uri +
															"> since there has been an IO exception while reading the stream: " +
															e.getMessage());
				}
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					throw new EmotionMLResolutionException("Unable to retrieve vocabulary from URI <" + uri +
															"> since there has been an IO exception while closing the stream: " +
															e.getMessage());
				}

				// now parse the document, and put it in cache
				EmotionMLDocument document;
				try
				{
					document = new EmotionMLImporter().importDocument(str.toString());
				}
				catch (EmotionMLException e)
				{
					throw new EmotionMLFormatException("Unable to retrieve vocabulary from URI \"" + uri +
														"\" since there has been an EmotionMLFormat exception while retrieving it: " + e.getLocalizedMessage());
				}
				catch (IOException e)
				{
					throw new EmotionMLFormatException("Unable to retrieve vocabulary from URI \"" + uri +
														"\" since there has been an IO exception while retrieving it: " + e.getLocalizedMessage());
				}

				documentsCache.put(uri, document);

				Vocabulary ret = document.getVocabulary(id);
				if (ret == null)
					throw new EmotionMLResolutionException("Unable to retrieve vocabulary from id \"" + id +
															"\" since it is not found in the accessed document corresponding to <" +
															uri + ">");
				else return ret;
			}
		}
	}

}
