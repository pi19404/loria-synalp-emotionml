package fr.loria.synalp.emotionml;

import java.net.URI;
import java.util.*;

import fr.loria.synalp.emotionml.vocabularies.*;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.info.Info;

/**
 * An Emotion is the representation of an affective state in EmotionML. An Emotion is primarily
 * described as a list of EmotionDescriptors (Category, Dimension, Appraisal, ActionTendency). These
 * EmotionDescriptors correspond to aspects of the Emotion. An Emotion is considered valid with
 * regards to the descriptors if it defines vocabularies for each of the descriptors that it
 * contains (see VocabularyReferrer). There are two ways to define vocabularies:
 * <ul>
 * <li>you can directly call methods of VocabularyReferrer, for instance setCategorySetURI</li>
 * <li>you can also set the URI of the descriptor before adding it to the Emotion. This mechanism is
 * mainly used to facilitate the vocabulary URI management. For instance, all descriptors defined in
 * the predefined vocabularies have already a correct vocabulary URI. This URI is copied onto the
 * Emotion vocabulary URI when adding the descriptor to the Emotion.</li>
 * </ul>
 * Additionnally, an Emotion may contain timing information, a text value, references to what caused
 * the emotion and modality information.
 * @author Alexandre Denis
 * @see fr.loria.synalp.emotionml.descriptors.EmotionDescriptor
 * @see fr.loria.synalp.emotionml.vocabularies.VocabularyReferrer
 */
public class Emotion extends VocabularyReferrer implements EmotionNode
{
	private String id;
	private String version;
	private EmotionText emotionText;
	private Timestamp timestamp = new Timestamp();
	private List<Reference> references = new ArrayList<Reference>();
	private List<EmotionDescriptor> descriptors = new ArrayList<EmotionDescriptor>();
	private List<ExpressedThrough> expressedThrough = new ArrayList<ExpressedThrough>();


	/**
	 * Creates a new Emotion with given EmotionDescriptor.
	 * @param descriptors
	 */
	public Emotion(EmotionDescriptor... descriptors)
	{
		for(EmotionDescriptor descriptor : descriptors)
			add(descriptor);
	}


	/**
	 * Deep copies the given Emotion.
	 */
	public Emotion(Emotion emotion)
	{
		super(emotion);
		this.id = emotion.getId();
		this.version = emotion.getVersion();
		this.emotionText = emotion.hasText() ? new EmotionText(emotion.getText()) : null;
		this.timestamp = new Timestamp(emotion.getTimestamp());

		for(Reference reference : emotion.getReferences())
			this.references.add(new Reference(reference));

		for(EmotionDescriptor descriptor : emotion.getDescriptors())
			switch (descriptor.getType())
			{
				case ACTION_TENDENCY:
					descriptors.add(new ActionTendency((ActionTendency) descriptor));
					break;
				case APPRAISAL:
					descriptors.add(new Appraisal((Appraisal) descriptor));
					break;
				case CATEGORY:
					descriptors.add(new Category((Category) descriptor));
					break;
				case DIMENSION:
					descriptors.add(new Dimension((Dimension) descriptor));
					break;
			}

		for(ExpressedThrough expressed : emotion.getExpressedThrough())
			expressedThrough.add(new ExpressedThrough(expressed));
	}


////// Emotion descriptors

	/**
	 * Adds the given EmotionDescriptor to this Emotion. If there already exists an
	 * EmotionDescriptor with the same name and type, it is replaced by the given one. If the given
	 * EmotionDescriptor has an URI, this method also sets the vocabulary URI of the whole Emotion
	 * corresponding to the vocabulary type. For instance if calling this method with a Category
	 * whose vocabulary URI is defined, this method sets the category-set URI of the whole Emotion,
	 * replacing it if there is already one. Warning though, setting manually the URI of the
	 * EmotionDescriptor afterwards will not set the Emotion vocabulary set URI.
	 * @param descriptor
	 */
	public void add(EmotionDescriptor descriptor)
	{
		descriptors.add(descriptor);
		if (descriptor.hasURI())
			setDescriptorSetURI(descriptor.getType(), descriptor.getURI());
	}


	/**
	 * Adds all the given EmotionDescriptors to this Emotion. See {@link #add(EmotionDescriptor)
	 * add}.
	 * @param descriptors a collection of descriptors
	 */
	public void addAll(Collection<EmotionDescriptor> descriptors)
	{
		for(EmotionDescriptor descriptor : descriptors)
			add(descriptor);
	}


	/**
	 * Removes the given EmotionDescriptor from this Emotion.
	 */
	public void remove(EmotionDescriptor descriptor)
	{
		descriptors.remove(descriptor);
	}


	/**
	 * Returns the first found descriptor with given name and type.
	 * @param name
	 * @return null if none is found
	 */
	public EmotionDescriptor getDescriptor(String name, VocabularyType type)
	{
		for(EmotionDescriptor descriptor : descriptors)
			if (descriptor.getType() == type && descriptor.getName().equals(name))
				return descriptor;
		return null;
	}


	/**
	 * Returns the first found descriptor with given name.
	 * @param name
	 * @return null if none is found
	 */
	public EmotionDescriptor getDescriptor(String name)
	{
		for(EmotionDescriptor descriptor : descriptors)
			if (descriptor.getName().equals(name))
				return descriptor;
		return null;
	}


	/**
	 * Returns a live list of all the descriptors defined in this Emotion.
	 */
	public List<EmotionDescriptor> getDescriptors()
	{
		return descriptors;
	}


	/**
	 * Returns a List view of all the descriptors of given type defined in this Emotion.
	 */
	public List<? extends EmotionDescriptor> getDescriptors(VocabularyType type)
	{
		switch (type)
		{
			case CATEGORY:
				return getCategories();

			case DIMENSION:
				return getDimensions();

			case ACTION_TENDENCY:
				return getActionTendencies();

			case APPRAISAL:
				return getAppraisals();
		}
		return null;
	}


	/**
	 * Returns a List view of the categories defined in this Emotion.
	 * @return a List view of categories
	 */
	public List<Category> getCategories()
	{
		List<Category> ret = new ArrayList<Category>();
		for(EmotionDescriptor descriptor : descriptors)
			if (descriptor.getType() == VocabularyType.CATEGORY)
				ret.add((Category) descriptor);
		return ret;
	}


	/**
	 * Returns a List view of the appraisals defined in this Emotion.
	 * @return a List view of appraisals
	 */
	public List<Appraisal> getAppraisals()
	{
		List<Appraisal> ret = new ArrayList<Appraisal>();
		for(EmotionDescriptor descriptor : descriptors)
			if (descriptor.getType() == VocabularyType.APPRAISAL)
				ret.add((Appraisal) descriptor);
		return ret;
	}


	/**
	 * Returns a List view of the dimensions defined in this Emotion.
	 * @return a List view of dimensions
	 */
	public List<Dimension> getDimensions()
	{
		List<Dimension> ret = new ArrayList<Dimension>();
		for(EmotionDescriptor descriptor : descriptors)
			if (descriptor.getType() == VocabularyType.DIMENSION)
				ret.add((Dimension) descriptor);
		return ret;
	}


	/**
	 * Returns a List view of the action tendencies defined in this Emotion.
	 * @return a List view of action tendencies
	 */
	public List<ActionTendency> getActionTendencies()
	{
		List<ActionTendency> ret = new ArrayList<ActionTendency>();
		for(EmotionDescriptor descriptor : descriptors)
			if (descriptor.getType() == VocabularyType.ACTION_TENDENCY)
				ret.add((ActionTendency) descriptor);
		return ret;
	}


/////// Id

	/**
	 * Returns the id of this Emotion.
	 * @return the id or null if it has not been defined
	 */
	public String getId()
	{
		return id;
	}


	/**
	 * Tests if this Emotion has a defined id.
	 * @return true if this Emotion as an id
	 */
	public boolean hasId()
	{
		return id != null;
	}


	/**
	 * Sets the id of this Emotion.
	 * @param id
	 */
	public void setId(String id)
	{
		this.id = id;
	}


/////// Version

	/**
	 * Returns the version of this Emotion.
	 * @return the version or null if it has not been defined
	 */
	public String getVersion()
	{
		return version;
	}


	/**
	 * Tests whether this Emotion has defined a version.
	 * @return true
	 */
	public boolean hasVersion()
	{
		return version != null;
	}


	/**
	 * Sets the version of this Emotion.
	 * @param version
	 */
	public void setVersion(String version)
	{
		this.version = version;
	}


/////// Expressed through

	/**
	 * Adds the given ExpressedThrough information to this Emotion.
	 * @param expressedThrough
	 */
	public void add(ExpressedThrough expressedThrough)
	{
		this.expressedThrough.add(expressedThrough);
	}


	/**
	 * Removes the given ExpressedThrough information from this Emotion.
	 * @param expressedThrough
	 */
	public void remove(ExpressedThrough expressedThrough)
	{
		this.expressedThrough.remove(expressedThrough);
	}


	/**
	 * Returns the live List of expressed through information carried by this Emotion.
	 * @return a live List of expressed through
	 */
	public List<ExpressedThrough> getExpressedThrough()
	{
		return expressedThrough;
	}


	/**
	 * Sets all the given ExpressedThrough information of this Emotion.
	 * @param expressedThrough
	 */
	public void setExpressedThrough(List<ExpressedThrough> expressedThrough)
	{
		this.expressedThrough = expressedThrough;
	}


/////// Reference

	/**
	 * Adds the given Reference to this Emotion.
	 * @param reference
	 */
	public void add(Reference reference)
	{
		references.add(reference);
	}


	/**
	 * Removes the given Reference from this Emotion.
	 * @param reference
	 */
	public void remove(Reference reference)
	{
		references.remove(reference);
	}


	/**
	 * Returns the live List of references of this Emotion.
	 * @return a live List of references
	 */
	public List<Reference> getReferences()
	{
		return references;
	}


/////// Text node

	/**
	 * Returns the emotion text of this Emotion.
	 * @return an EmotionText or null if not defined
	 */
	public EmotionText getText()
	{
		return emotionText;
	}


	/**
	 * Sets the text of this Emotion.
	 * @param emotionText
	 */
	public void setText(EmotionText emotionText)
	{
		this.emotionText = emotionText;
	}


	/**
	 * Tests if this Emotion has an emotion text.
	 * @return true if this Emotion has an emotion text
	 */
	public boolean hasText()
	{
		return emotionText != null;
	}


/////// Timestamp

	/**
	 * Returns the Timestamp of this Emotion.
	 * @return a Timestamp
	 */
	public Timestamp getTimestamp()
	{
		return timestamp;
	}


	/**
	 * Sets the Timestamp of this Emotion.
	 */
	public void setTimestamp(Timestamp timestamp)
	{
		this.timestamp = timestamp;
	}


////// to ease chaining...

	@Override
	public Emotion setDescriptorSetURI(VocabularyType type, URI uri)
	{
		return (Emotion) super.setDescriptorSetURI(type, uri);
	}


	@Override
	public Emotion setDimensionSetURI(URI uri)
	{
		return (Emotion) super.setDimensionSetURI(uri);
	}


	@Override
	public Emotion setAppraisalSetURI(URI uri)
	{
		return (Emotion) super.setAppraisalSetURI(uri);
	}


	@Override
	public Emotion setCategorySetURI(URI uri)
	{
		return (Emotion) super.setCategorySetURI(uri);
	}


	@Override
	public Emotion setActionTendencySetURI(URI uri)
	{
		return (Emotion) super.setActionTendencySetURI(uri);
	}


	@Override
	public Emotion setInfo(Info info)
	{
		return (Emotion) super.setInfo(info);
	}


	/**
	 * Returns a String representation of this Emotion.
	 */
	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		if (hasId())
			ret.append("id=").append(id).append(" ");
		ret.append(descriptors).append(" ");
		if (hasText())
			ret.append("text=\"").append(emotionText).append("\" ");
		if (!expressedThrough.isEmpty())
			ret.append("expressed-through=").append(expressedThrough).append(" ");
		if (!references.isEmpty())
			ret.append("references=").append(references).append(" ");
		if (timestamp.hasDefinition())
			ret.append("time=").append(timestamp).append(" ");
		if (hasInfo())
			ret.append("info=").append(getInfo()).append(" ");
		return ret.toString().trim();
	}

}
