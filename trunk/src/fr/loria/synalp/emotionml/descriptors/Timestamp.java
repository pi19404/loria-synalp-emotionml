package fr.loria.synalp.emotionml.descriptors;

import java.math.BigInteger;
import java.net.*;

/**
 * A Timestamp gathers all time related information about an Emotion. See <a
 * href="http://www.w3.org/TR/emotionml/#s2.4.2.4" target="_blank">Timing in EmotionML</a>.
 * @author Alexandre Denis
 */
public class Timestamp
{
	/**
	 * An anchor point whose value is either START or END.
	 * @author Alexandre Denis
	 */
	public enum TimeRefAnchorPoint
	{
		START("start"),
		END("end");

		public static TimeRefAnchorPoint parse(String str)
		{
			for(TimeRefAnchorPoint type : values())
				if (type.toString().equals(str))
					return type;
			return null;
		}

		private String str;


		private TimeRefAnchorPoint(String str)
		{
			this.str = str;
		}


		@Override
		public String toString()
		{
			return str;
		}
	}

	private BigInteger start;
	private BigInteger end;
	private BigInteger duration;

	private URI timeRefUri;
	private BigInteger offsetToStart;
	private TimeRefAnchorPoint timeRefAnchorPoint;


	/**
	 * Creates an empty Timestamp.
	 */
	public Timestamp()
	{

	}


	/**
	 * Deep copies the given Timestamp.
	 * @param timestamp
	 */
	public Timestamp(Timestamp timestamp)
	{
		this.start = timestamp.getStart();
		this.end = timestamp.getEnd();
		this.duration = timestamp.getDuration();
		this.timeRefUri = timestamp.getTimeRefURI(); // since immutable
		this.offsetToStart = timestamp.getOffsetToStart();
		this.timeRefAnchorPoint = timestamp.getTimeRefAnchorPoint();
	}


	/**
	 * Tests if this Timestamp defines start.
	 */
	public boolean hasStart()
	{
		return start != null;
	}


	/**
	 * Tests if this Timestamp defines end.
	 */
	public boolean hasEnd()
	{
		return end != null;
	}


	/**
	 * Tests if this Timestamp defines duration.
	 */
	public boolean hasDuration()
	{
		return duration != null;
	}


	/**
	 * Tests if this Timestamp defines a time ref URI.
	 */
	public boolean hasTimeRefURI()
	{
		return timeRefUri != null;
	}


	/**
	 * Tests if this Timestamp defines an offset to start.
	 */
	public boolean hasOffsetToStart()
	{
		return offsetToStart != null;
	}


	/**
	 * Tests if this Timestamp defines a time ref anchor point.
	 */
	public boolean hasTimeRefAnchorPoint()
	{
		return timeRefAnchorPoint != null;
	}


	/**
	 * @return the start
	 */
	public BigInteger getStart()
	{
		return start;
	}


	/**
	 * Returns the start as a long value. Note that this method may be lossy.
	 * @return the star as a long value or 0 if there is no start defined
	 */
	public long getStartAsLong()
	{
		if (hasEnd())
			return start.longValue();
		else return 0;
	}


	/**
	 * @param start the start to set
	 */
	public void setStart(BigInteger start)
	{
		this.start = start;
	}


	/**
	 * @param start the start to set
	 */
	public void setStart(long start)
	{
		this.start = BigInteger.valueOf(start);
	}


	/**
	 * @return the end
	 */
	public BigInteger getEnd()
	{
		return end;
	}


	/**
	 * Returns the end as a long value. Note that this method may be lossy.
	 * @return the end as a long value or 0 if there is no end defined
	 */
	public long getEndAsLong()
	{
		if (hasEnd())
			return end.longValue();
		else return 0;
	}


	/**
	 * @param end the end to set
	 */
	public void setEnd(BigInteger end)
	{
		this.end = end;
	}


	/**
	 * @param end the end to set
	 */
	public void setEnd(long end)
	{
		this.end = BigInteger.valueOf(end);
	}


	/**
	 * @return the duration
	 */
	public BigInteger getDuration()
	{
		return duration;
	}


	/**
	 * Returns the duration as a long value. Note that this method may be lossy.
	 * @return the duration as a long value or 0 if there is no duration defined
	 */
	public long getDurationAsLong()
	{
		if (hasDuration())
			return duration.longValue();
		else return 0;
	}


	/**
	 * @param duration the duration to set
	 */
	public void setDuration(BigInteger duration)
	{
		this.duration = duration;
	}


	/**
	 * @param duration the duration to set
	 */
	public void setDuration(long duration)
	{
		this.duration = BigInteger.valueOf(duration);
	}


	/**
	 * @return the timeRefUri
	 */
	public URI getTimeRefURI()
	{
		return timeRefUri;
	}


	/**
	 * @param timeRefUri the timeRefUri to set
	 */
	public void setTimeRefURI(URI timeRefUri)
	{
		this.timeRefUri = timeRefUri;
	}


	/**
	 * @return the offsetToStart
	 */
	public BigInteger getOffsetToStart()
	{
		return offsetToStart;
	}


	/**
	 * Returns the offset to start as a long value. Note that this method may be lossy.
	 * @return the offsetToStart as a long value or 0 if there is no offset to start defined
	 */
	public long getOffsetToStartAsLong()
	{
		if (hasOffsetToStart())
			return offsetToStart.longValue();
		else return 0;
	}


	/**
	 * @param offsetToStart the offsetToStart to set
	 */
	public void setOffsetToStart(BigInteger offsetToStart)
	{
		this.offsetToStart = offsetToStart;
	}


	/**
	 * @param offsetToStart the offsetToStart to set
	 */
	public void setOffsetToStart(long offsetToStart)
	{
		this.offsetToStart = BigInteger.valueOf(offsetToStart);
	}


	/**
	 * @return the timeRefAnchorPoint
	 */
	public TimeRefAnchorPoint getTimeRefAnchorPoint()
	{
		return timeRefAnchorPoint;
	}


	/**
	 * @param timeRefAnchorPoint the timeRefAnchorPoint to set
	 */
	public void setTimeRefAnchorPoint(TimeRefAnchorPoint timeRefAnchorPoint)
	{
		this.timeRefAnchorPoint = timeRefAnchorPoint;
	}


	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder("[");
		if (hasStart() || hasEnd() || hasDuration())
		{
			if (hasStart())
				ret.append("from ").append(start).append("ms ");
			if (hasEnd())
				ret.append("to ").append(end).append("ms ");
			if (hasDuration())
				ret.append("(").append(duration).append("ms) ");
			if (hasTimeRefURI() || hasTimeRefAnchorPoint() || hasOffsetToStart())
				ret.append("; ");
		}

		if (hasTimeRefURI() || hasTimeRefAnchorPoint() || hasOffsetToStart())
		{
			if (hasOffsetToStart())
				ret.append(offsetToStart).append("ms").append(" ");
			if (hasTimeRefAnchorPoint())
			{
				ret.append("from ").append(timeRefAnchorPoint).append(" ");
				if (hasTimeRefURI())
					ret.append("of ");
			}

			if (hasTimeRefURI())
				ret.append("ref=").append(timeRefUri);
		}

		return ret.toString().trim() + "]";
	}


	/**
	 * Tests if this Timestamp defines one of its field.
	 * @return true if this Timestamp defines either its start, end, duration, time ref uri, time
	 *         ref anchor point or offset to start.
	 */
	public boolean hasDefinition()
	{
		return hasStart() || hasEnd() || hasDuration() || hasTimeRefURI() || hasTimeRefAnchorPoint() || hasOffsetToStart();
	}
}
