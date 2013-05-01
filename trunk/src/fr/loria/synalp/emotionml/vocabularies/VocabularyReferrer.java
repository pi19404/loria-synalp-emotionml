package fr.loria.synalp.emotionml.vocabularies;

import java.net.*;
import java.util.*;

import fr.loria.synalp.emotionml.info.InfoCarrier;

/**
 * A VocabularyReferrer is an InfoCarrier that carries information about vocabulary URIs,
 * that is category set, dimension set, appraisal set and action tendency set. It is used as a super
 * class for both Emotion and EmotionMLDocument.
 * @author Alexandre Denis
 */
public class VocabularyReferrer extends InfoCarrier
{
	// vocabulary URIs: category-set, dimension-set, action-tendency-set, and appraisal-set
	private Map<VocabularyType, URI> descriptorsSetURIs = new HashMap<VocabularyType, URI>();


	/**
	 * Returns the URI corresponding to the action tendency set reference.
	 * @return null if it has not been defined.
	 */
	public URI getActionTendencySetURI()
	{
		return descriptorsSetURIs.get(VocabularyType.ACTION_TENDENCY);
	}


	/**
	 * Returns the URI corresponding to the appraisal set reference.
	 * @return null if it has not been defined.
	 */
	public URI getAppraisalSetURI()
	{
		return descriptorsSetURIs.get(VocabularyType.APPRAISAL);
	}


	/**
	 * Returns the URI corresponding to the category set reference.
	 * @return null if it has not been defined.
	 */
	public URI getCategorySetURI()
	{
		return descriptorsSetURIs.get(VocabularyType.CATEGORY);
	}


	/**
	 * Returns the URI corresponding to given descriptor type set reference.
	 * @param type
	 * @return null if it has not been defined.
	 */
	public URI getDescriptorSetURI(VocabularyType type)
	{
		return descriptorsSetURIs.get(type);
	}


	/**
	 * Returns the URI corresponding to the dimension set reference.
	 * @return null if it has not been defined.
	 */
	public URI getDimensionSetURI()
	{
		return descriptorsSetURIs.get(VocabularyType.DIMENSION);
	}


	/**
	 * Tests if the given descriptor type set is referenced by an URI.
	 */
	public boolean hasVocabularySetURI(VocabularyType type)
	{
		return descriptorsSetURIs.containsKey(type);
	}


	/**
	 * Sets the URI corresponding to the action tendency set reference. If there is already an URI
	 * associated with the action tendency set, replace it by the given one.
	 * @param uri
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer setActionTendencySetURI(URI uri)
	{
		descriptorsSetURIs.put(VocabularyType.ACTION_TENDENCY, uri);
		return this;
	}



	/**
	 * Sets the URI corresponding to the appraisal set reference. If there is already an URI
	 * associated with the appraisal set, replace it by the given one.
	 * @param uri
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer setAppraisalSetURI(URI uri)
	{
		descriptorsSetURIs.put(VocabularyType.APPRAISAL, uri);
		return this;
	}
	

	/**
	 * Sets the URI corresponding to the category set reference. If there is already an URI
	 * associated with the category set, replace it by the given one.
	 * @param uri
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer setCategorySetURI(URI uri)
	{
		descriptorsSetURIs.put(VocabularyType.CATEGORY, uri);
		return this;
	}



	/**
	 * Sets the URI corresponding to given descriptor type set reference. If there is already an URI
	 * associated with given descriptor type, replace it by the given one.
	 * @param uri
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer setDescriptorSetURI(VocabularyType type, URI uri)
	{
		descriptorsSetURIs.put(type, uri);
		return this;
	}



	/**
	 * Sets the URI corresponding to the dimension set reference. If there is already an URI
	 * associated with the dimension set, replace it by the given one.
	 * @param uri
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer setDimensionSetURI(URI uri)
	{
		descriptorsSetURIs.put(VocabularyType.DIMENSION, uri);
		return this;
	}
	
	
	
	/**
	 * Sets the URI corresponding to the action tendency set reference. If there is already an URI
	 * associated with the action tendency set, replace it by the given one.
	 * @param uri
	 * @return null if the URI cannot be parsed
	 */
	public VocabularyReferrer setActionTendencySetURI(String uri)
	{
		try
		{
			return setActionTendencySetURI(new URI(uri));
		}
		catch(URISyntaxException e)
		{
			return null;
		}
	}



	/**
	 * Sets the URI corresponding to the appraisal set reference. If there is already an URI
	 * associated with the appraisal set, replace it by the given one.
	 * @param uri
	 * @return null if the URI cannot be parsed
	 */
	public VocabularyReferrer setAppraisalSetURI(String uri)
	{
		try
		{
			return setAppraisalSetURI(new URI(uri));
		}
		catch(URISyntaxException e)
		{
			return null;
		}
	}
	

	/**
	 * Sets the URI corresponding to the category set reference. If there is already an URI
	 * associated with the category set, replace it by the given one.
	 * @param uri
	 * @return null if the URI cannot be parsed
	 */
	public VocabularyReferrer setCategorySetURI(String uri)
	{
		try
		{
			return setCategorySetURI(new URI(uri));
		}
		catch(URISyntaxException e)
		{
			return null;
		}
	}



	/**
	 * Sets the URI corresponding to given descriptor type set reference. If there is already an URI
	 * associated with given descriptor type, replace it by the given one.
	 * @param uri
	 * @return null if the URI cannot be parsed
	 */
	public VocabularyReferrer setDescriptorSetURI(VocabularyType type, String uri)
	{
		try
		{
			return setDescriptorSetURI(type, new URI(uri));
		}
		catch(URISyntaxException e)
		{
			return null;
		}
	}



	/**
	 * Sets the URI corresponding to the dimension set reference. If there is already an URI
	 * associated with the dimension set, replace it by the given one.
	 * @param uri
	 * @return null if the URI cannot be parsed
	 */
	public VocabularyReferrer setDimensionSetURI(String uri)
	{
		try
		{
			return setDimensionSetURI(new URI(uri));
		}
		catch(URISyntaxException e)
		{
			return null;
		}
	}
}
