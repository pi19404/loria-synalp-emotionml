package fr.loria.synalp.emotionml.descriptors;

import java.util.*;

/**
 * A <a href="http://www.w3.org/TR/emotionml/#s2.5.2" target="_blank">Trace in EmotionML</a>.
 * @author Alexandre Denis
 */
public class Trace
{
	private float frequency;
	private float[] samples;


	/**
	 * @param frequency
	 * @param samples
	 */
	public Trace(float frequency, float[] samples)
	{
		this.frequency = frequency;
		this.samples = samples;
	}


	public Trace(Trace trace)
	{
		this.frequency = trace.getFrequency();
		this.samples = Arrays.copyOf(trace.getSamples(), trace.getSamples().length);
	}


	/**
	 * @return the frequency
	 */
	public float getFrequency()
	{
		return frequency;
	}


	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(float frequency)
	{
		this.frequency = frequency;
	}


	/**
	 * @return the samples
	 */
	public float[] getSamples()
	{
		return samples;
	}


	/**
	 * @param samples the samples to set
	 */
	public void setSamples(float[] samples)
	{
		this.samples = samples;
	}

	
	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder("[");
		ret.append("freq=").append(frequency).append("Hz ");
		ret.append("samples=").append(Arrays.toString(samples));
		ret.append("]");
		return ret.toString();
	}
}
