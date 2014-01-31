package fr.loria.synalp.emotionml.vocabularies;

import java.net.*;
import java.util.*;

import fr.loria.synalp.emotionml.info.InfoCarrier;

import static fr.loria.synalp.emotionml.vocabularies.VocabularyType.*;

/**
 * A VocabularyReferrer is an InfoCarrier that carries information about vocabulary URIs, that is
 * category set, dimension set, appraisal set and action tendency set. It is used as a super class
 * for both Emotion and EmotionMLDocument.
 * @author Alexandre Denis
 */
public class VocabularyReferrer extends InfoCarrier
{
	// vocabulary URIs: category-set, dimension-set, action-tendency-set, and appraisal-set
	private Map<VocabularyType, URI> descriptorsSetURIs = new HashMap<VocabularyType, URI>();


	/**
	 * Creates a new empty VocabularyReferrer.
	 */
	public VocabularyReferrer()
	{

	}


	/**
	 * Deep copies the given VocabularyReferrer.
	 */
	public VocabularyReferrer(VocabularyReferrer referrer)
	{
		super(referrer);
		descriptorsSetURIs.putAll(referrer.descriptorsSetURIs);
	}


///// getters	

	/**
	 * Returns the URI corresponding to the action tendency set reference.
	 * @return null if it has not been defined.
	 */
	public URI getActionTendencySetURI()
	{
		return descriptorsSetURIs.get(ACTION_TENDENCY);
	}


	/**
	 * Returns the URI corresponding to the appraisal set reference.
	 * @return null if it has not been defined.
	 */
	public URI getAppraisalSetURI()
	{
		return descriptorsSetURIs.get(APPRAISAL);
	}


	/**
	 * Returns the URI corresponding to the category set reference.
	 * @return null if it has not been defined.
	 */
	public URI getCategorySetURI()
	{
		return descriptorsSetURIs.get(CATEGORY);
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
		return descriptorsSetURIs.get(DIMENSION);
	}


	/**
	 * Returns a live map of the descriptor set URIs.
	 * @return
	 */
	public Map<VocabularyType, URI> getAllDescriptorSetURIs()
	{
		return descriptorsSetURIs;
	}


//// existence tests

	/**
	 * Tests if the given descriptor type set is referenced by an URI.
	 */
	public boolean hasVocabularySetURI(VocabularyType type)
	{
		return descriptorsSetURIs.containsKey(type);
	}


//// setters

	/**
	 * Sets the URI corresponding to the action tendency set reference. If there is already an URI
	 * associated with the action tendency set, replace it by the given one.
	 * @param uri an URI, if null unsets the URI
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer setActionTendencySetURI(URI uri)
	{
		if (uri == null)
			descriptorsSetURIs.remove(ACTION_TENDENCY);
		else descriptorsSetURIs.put(ACTION_TENDENCY, uri);
		return this;
	}


	/**
	 * Sets the URI corresponding to the appraisal set reference. If there is already an URI
	 * associated with the appraisal set, replace it by the given one.
	 * @param uri an URI, if null unsets the URI
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer setAppraisalSetURI(URI uri)
	{
		if (uri == null)
			descriptorsSetURIs.remove(APPRAISAL);
		else descriptorsSetURIs.put(APPRAISAL, uri);
		return this;
	}


	/**
	 * Sets the URI corresponding to the category set reference. If there is already an URI
	 * associated with the category set, replace it by the given one.
	 * @param uri an URI, if null unsets the URI
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer setCategorySetURI(URI uri)
	{
		if (uri == null)
			descriptorsSetURIs.remove(CATEGORY);
		else descriptorsSetURIs.put(CATEGORY, uri);
		return this;
	}


	/**
	 * Sets the URI corresponding to given descriptor type set reference. If there is already an URI
	 * associated with given descriptor type, replace it by the given one.
	 * @param type the given descriptor type
	 * @param uri an URI, if null unsets the URI
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer setDescriptorSetURI(VocabularyType type, URI uri)
	{
		if (uri == null)
			descriptorsSetURIs.remove(type);
		else descriptorsSetURIs.put(type, uri);
		return this;
	}


	/**
	 * Sets the URI corresponding to the dimension set reference. If there is already an URI
	 * associated with the dimension set, replace it by the given one.
	 * @param uri an URI, if null unsets the URI
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer setDimensionSetURI(URI uri)
	{
		if (uri == null)
			descriptorsSetURIs.remove(DIMENSION);
		else descriptorsSetURIs.put(DIMENSION, uri);
		return this;
	}


	/**
	 * Sets the URI corresponding to the action tendency set reference. If there is already an URI
	 * associated with the action tendency set, replace it by the given one.
	 * @param uri if null unsets the URI
	 * @return null if the URI cannot be parsed
	 */
	public VocabularyReferrer setActionTendencySetURI(String uri)
	{
		if (uri == null)
			return unsetActionTendencySetURI();

		try
		{
			return setActionTendencySetURI(new URI(uri));
		}
		catch (URISyntaxException e)
		{
			return null;
		}
	}


	/**
	 * Sets the URI corresponding to the appraisal set reference. If there is already an URI
	 * associated with the appraisal set, replace it by the given one.
	 * @param uri if null unsets the URI
	 * @return null if the URI cannot be parsed
	 */
	public VocabularyReferrer setAppraisalSetURI(String uri)
	{
		if (uri == null)
			return unsetAppraisalSetURI();

		try
		{
			return setAppraisalSetURI(new URI(uri));
		}
		catch (URISyntaxException e)
		{
			return null;
		}
	}


	/**
	 * Sets the URI corresponding to the category set reference. If there is already an URI
	 * associated with the category set, replace it by the given one.
	 * @param uri if null unsets the URI
	 * @return null if the URI cannot be parsed
	 */
	public VocabularyReferrer setCategorySetURI(String uri)
	{
		if (uri == null)
			return unsetCategorySetURI();

		try
		{
			return setCategorySetURI(new URI(uri));
		}
		catch (URISyntaxException e)
		{
			return null;
		}
	}


	/**
	 * Sets the URI corresponding to given descriptor type set reference. If there is already an URI
	 * associated with given descriptor type, replace it by the given one.
	 * @param uri if null unsets the URI
	 * @return null if the URI cannot be parsed
	 */
	public VocabularyReferrer setDescriptorSetURI(VocabularyType type, String uri)
	{
		if (uri == null)
			return unsetDescriptorSetURI(type);

		try
		{
			return setDescriptorSetURI(type, new URI(uri));
		}
		catch (URISyntaxException e)
		{
			return null;
		}
	}


	/**
	 * Sets the URI corresponding to the dimension set reference. If there is already an URI
	 * associated with the dimension set, replace it by the given one.
	 * @param uri if null unsets the URI
	 * @return null if the URI cannot be parsed
	 */
	public VocabularyReferrer setDimensionSetURI(String uri)
	{
		if (uri == null)
			return unsetDimensionSetURI();

		try
		{
			return setDimensionSetURI(new URI(uri));
		}
		catch (URISyntaxException e)
		{
			return null;
		}
	}


	/**
	 * Sets the descriptor set URIs.
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer setAllDescriptorSetURIs(Map<VocabularyType, URI> descriptorsSetURIs)
	{
		this.descriptorsSetURIs = descriptorsSetURIs;
		return this;
	}


//// unsetters (they avoid having to specify the type when passing null values to setters)	

	/**
	 * Unsets the URI corresponding to the action tendency set reference.
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer unsetActionTendencySetURI()
	{
		descriptorsSetURIs.remove(ACTION_TENDENCY);
		return this;
	}


	/**
	 * Unsets the URI corresponding to the appraisal set reference.
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer unsetAppraisalSetURI()
	{
		descriptorsSetURIs.remove(APPRAISAL);
		return this;
	}


	/**
	 * Unsets the URI corresponding to the category set reference.
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer unsetCategorySetURI()
	{
		descriptorsSetURIs.remove(CATEGORY);
		return this;
	}


	/**
	 * Unsets the URI corresponding to the dimension set reference.
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer unsetDimensionSetURI()
	{
		descriptorsSetURIs.remove(DIMENSION);
		return this;
	}


	/**
	 * Unsets the URI corresponding to the given set reference.
	 * @return this VocabularyReferrer to easily chain methods
	 */
	public VocabularyReferrer unsetDescriptorSetURI(VocabularyType type)
	{
		descriptorsSetURIs.remove(type);
		return this;
	}
}
