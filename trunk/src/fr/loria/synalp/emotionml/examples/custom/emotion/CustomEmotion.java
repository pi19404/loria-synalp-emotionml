package fr.loria.synalp.emotionml.examples.custom.emotion;

import fr.loria.synalp.emotionml.Emotion;
import fr.loria.synalp.emotionml.descriptors.*;
import fr.loria.synalp.emotionml.vocabularies.predefined.FsreDimensions;

/**
 * A CustomEmotion is an example of an Emotion that provides an easy access to a valence dimension.
 * @author Alexandre Denis
 */
class CustomEmotion extends Emotion
{
	private Dimension valenceDimension;

	
	public CustomEmotion(float valence)
	{
		valenceDimension = new Dimension(FsreDimensions.VALENCE, valence);
		add(valenceDimension);
	}


	public float getValence()
	{
		return valenceDimension.getValue();
	}


	public void setValence(float valence)
	{
		valenceDimension.setValue(valence);
	}
}
