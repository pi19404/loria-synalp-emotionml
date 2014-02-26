package fr.loria.synalp.emotionml.descriptors;

import java.io.Serializable;
import java.net.URI;

import fr.loria.synalp.emotionml.vocabularies.VocabularyType;

/**
 * An EmotionDescriptor is an aspect an Emotion (Category, Dimension, Appraisal or ActionTendency).
 * All descriptors have a mandatory name and an optional value or trace. The specific constraints on
 * the presence of value and trace depend on the type of descriptor. For instance a Dimension needs
 * to have either a value or a trace, while a Category may lack both. All descriptors cannot have
 * both a trace and a value. These constraints and other EmotionML constraints (see <a
 * href="http://www.w3.org/TR/emotionml/#s2.2" target="_blank">Representation of Emotions in
 * EmotionML</a>) are not checked when creating or modifying the descriptor, but only checked when
 * exporting the emotion they are describing. A descriptor may be directly associated to a
 * vocabulary URI which is a convenient way to automatically setup the Emotion vocabulary URI when
 * attaching the descriptor to the Emotion. However, altering the URI after the descriptor has been
 * added to the Emotion will have no effect.
 * @author Alexandre Denis
 * @see fr.loria.synalp.emotionml.Emotion
 */
@SuppressWarnings("serial")
public class EmotionDescriptor implements Serializable
{
	private URI uri;
	private Float value;
	private Trace trace;
	private Float confidence;
	private String name;
	private VocabularyType type;


	// for serialization
	protected EmotionDescriptor()
	{

	}


	/**
	 * @param type
	 * @param name
	 */
	protected EmotionDescriptor(VocabularyType type, String name)
	{
		this(type, name, null, null);
	}


	/**
	 * @param type
	 * @param name
	 * @param value
	 */
	protected EmotionDescriptor(VocabularyType type, String name, Float value)
	{
		this(type, name, value, null);
	}


	/**
	 * @param type
	 * @param name
	 * @param trace
	 */
	protected EmotionDescriptor(VocabularyType type, String name, Trace trace)
	{
		this(type, name, null, trace);
	}


	/**
	 * Deep copy constructor.
	 * @param descriptor
	 */
	protected EmotionDescriptor(EmotionDescriptor descriptor)
	{
		this.uri = descriptor.getURI();
		this.type = descriptor.getType();
		this.name = descriptor.getName();
		this.value = descriptor.getValue();
		this.confidence = descriptor.getConfidence();
		this.trace = descriptor.hasTrace() ? new Trace(descriptor.getTrace()) : null;
	}


	/**
	 * @param type
	 * @param name
	 * @param value
	 * @param trace
	 */
	private EmotionDescriptor(VocabularyType type, String name, Float value, Trace trace)
	{
		this(type, name, value, trace, null);
	}


	/**
	 * @param type
	 * @param name
	 * @param value
	 * @param trace
	 */
	private EmotionDescriptor(VocabularyType type, String name, Float value, Trace trace, URI uri)
	{
		this.uri = uri;
		this.type = type;
		this.name = name;
		this.value = value;
		this.trace = trace;
		this.confidence = null;
	}


	/**
	 * Tests if this descriptor has a value.
	 * @return whether this descriptor has a non-null value
	 */
	public boolean hasValue()
	{
		return value != null;
	}


	/**
	 * Tests if this descriptor has a trace.
	 * @return whether this descriptor has a non-null trace
	 */
	public boolean hasTrace()
	{
		return trace != null;
	}


	/**
	 * Returns the name of this descriptor.
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * Returns the value of this descriptor.
	 * @return the value which may be null if it has not been defined
	 */
	public Float getValue()
	{
		return value;
	}


	/**
	 * Sets the value of this descriptor. Warning: setting the value may alter the validity of this
	 * descriptor with regards to EmotionML constraints.
	 * @param value the value to set
	 */
	public void setValue(Float value)
	{
		this.value = value;
	}


	/**
	 * Returns the trace of this descriptor
	 * @return the trace which may be null if it has not been defined
	 */
	public Trace getTrace()
	{
		return trace;
	}


	/**
	 * Sets the Trace of this descriptor. Warning: setting the Trace may alter the validity of this
	 * descriptor with regards to EmotionML constraints.
	 * @param trace the trace to set
	 */
	public void setTrace(Trace trace)
	{
		this.trace = trace;
	}


	/**
	 * Tests if this descriptor has a confidence.
	 * @return whether this descriptor has a non-null confidence
	 */
	public boolean hasConfidence()
	{
		return confidence != null;
	}


	/**
	 * Returns the confidence of this descriptor.
	 * @return the confidence which may be null if it has not been defined
	 */
	public Float getConfidence()
	{
		return confidence;
	}


	/**
	 * Sets the confidence of this descriptor.
	 * @param confidence the confidence to set
	 */
	public void setConfidence(Float confidence)
	{
		this.confidence = confidence;
	}


	/**
	 * Returns the type of this descriptor.
	 * @return the type of this descriptor
	 */
	public VocabularyType getType()
	{
		return type;
	}


	/**
	 * Returns the vocabulary URI in which this descriptor is defined. Note: this method is used
	 * internally when using predefined Vocabularies only. It does not return the URI of the Emotion
	 * when this EmotionDescriptor is attached to an Emotion.
	 * @return null if the URI is not defined
	 */
	public URI getURI()
	{
		return uri;
	}


	/**
	 * Sets the vocbabulary URI in which this descriptor is defined. Note: this method is used
	 * internally when using predefined Vocabularies only. It does not set the vocabulary URI of the
	 * Emotion to which this EmotionDescriptor is attached.
	 * @param uri the vocabulary URI to set
	 */
	public void setURI(URI uri)
	{
		this.uri = uri;
	}


	/**
	 * Tests if this descriptor has defined a vocabulary URI. Note: this method does not check
	 * whether the Emotion to which this EmotionDescriptor is attached has an URI.
	 * @return true if this descriptor has a non-null vocabulary URI
	 */
	public boolean hasURI()
	{
		return uri != null;
	}


	/**
	 * Returns a String representation of this EmotionDescriptor.
	 */
	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		ret.append(type).append("/").append(name).append(" ");
		if (hasValue())
			ret.append("value=").append(value).append(" ");
		if (hasConfidence())
			ret.append("conf=").append(confidence).append(" ");
		if (hasTrace())
			ret.append("trace=").append(trace).append(" ");
		return ret.toString().trim();

	}
}
