package fr.loria.synalp.emotionml.descriptors;

import java.net.URI;

/**
 * Reference provides a link to a media URI. See <a href="http://www.w3.org/TR/emotionml/#s2.4.1"
 * target="_blank">Reference in EmotionML</a>.
 * @author Alexandre Denis
 */
public class Reference
{
	public enum Role
	{
		EXPRESSED_BY("expressedBy"),
		EXPERIENCED_BY("experiencedBy"),
		TRIGGERED_BY("triggeredBy"),
		TARGETED_AT("targetedAt");

		private String str;


		public static Role parse(String roleStr)
		{
			for(Role role : values())
				if (role.toString().equals(roleStr))
					return role;
			return null;
		}


		private Role(String str)
		{
			this.str = str;
		}


		@Override
		public String toString()
		{
			return str;
		}
	}

	private URI uri;
	private Role role;
	private String mediaType;


	public Reference(URI uri)
	{
		this.uri = uri;
		this.role = Role.EXPRESSED_BY;
	}


	public Reference(URI uri, Role role)
	{
		this.uri = uri;
		this.role = role;
	}


	public Reference(URI uri, String mediaType, Role role)
	{
		this.uri = uri;
		this.role = role;
		this.mediaType = mediaType;
	}


	/**
	 * @return the uri
	 */
	public URI getURI()
	{
		return uri;
	}


	/**
	 * @param uri the uri to set
	 */
	public void setURI(URI uri)
	{
		this.uri = uri;
	}


	/**
	 * @return the role
	 */
	public Role getRole()
	{
		return role;
	}


	/**
	 * @param role the role to set
	 */
	public void setRole(Role role)
	{
		this.role = role;
	}


	/**
	 * @return the mediaType
	 */
	public String getMediaType()
	{
		return mediaType;
	}


	/**
	 * @param mediaType the mediaType to set
	 */
	public void setMediaType(String mediaType)
	{
		this.mediaType = mediaType;
	}


	/**
	 * Tests if this Reference defines a media type.
	 * @return true if this Reference has a media type, false otherwise
	 */
	public boolean hasMediaType()
	{
		return mediaType != null;
	}

	
	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		ret.append(role).append(" ");
		ret.append(uri).append(" ");
		if (hasMediaType())
			ret.append("(").append(mediaType).append(") ");
		return ret.toString().trim();
	}
}
