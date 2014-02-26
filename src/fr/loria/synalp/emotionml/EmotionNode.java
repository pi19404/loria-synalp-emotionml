package fr.loria.synalp.emotionml;

import java.io.Serializable;

/**
 * An EmotionNode serves as a super class for both Emotion and EmotionText since an
 * EmotionMLDocument may both contain plain text and emotions.
 * @author Alexandre Denis
 */
public interface EmotionNode extends Serializable
{

}
