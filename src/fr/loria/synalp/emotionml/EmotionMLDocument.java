package fr.loria.synalp.emotionml;

import java.io.Serializable;
import java.net.*;
import java.util.*;

import fr.loria.synalp.emotionml.info.*;
import fr.loria.synalp.emotionml.vocabularies.*;

/**
 * An EmotionMLDocument is an object containing emotion nodes and vocabularies.
 * @author Alexandre Denis
 */
@SuppressWarnings("serial")
public class EmotionMLDocument extends VocabularyReferrer implements Serializable
{
	public static final String VERSION = "1.0"; // 111
	public static final String NAMESPACE = "http://www.w3.org/2009/10/emotionml"; // 102
	public static final URI VOCABULARY_URI = createVocabularyURI("http://www.w3.org/TR/emotion-voc/xml");

	private List<EmotionNode> emotionNodes = new ArrayList<EmotionNode>();
	private Map<String, Vocabulary> vocabularies = new HashMap<String, Vocabulary>(); // <identifier, vocabulary>


	/**
	 * @return
	 */
	private static URI createVocabularyURI(String uri)
	{
		try
		{
			return new URI(uri);
		}
		catch (Exception e)
		{
			return null;
		}
	}


	/**
	 * Adds the given Emotion to this EmotionMLDocument.
	 */
	public void add(EmotionNode emotionNode)
	{
		emotionNodes.add(emotionNode);
	}


	/**
	 * Adds the given Vocabulary to this EmotionMLDocument. If there is already a vocabulary with
	 * the same id it is replaced by the given one.
	 * @param vocabulary
	 */
	public void add(Vocabulary vocabulary)
	{
		vocabularies.put(vocabulary.getId(), vocabulary);
	}


	/**
	 * Returns a Collection view of vocabularies defined in this EmotionMLDocument.
	 * @return a Collection view of vocabularies
	 */
	public Collection<Vocabulary> getVocabularies()
	{
		return vocabularies.values();
	}


	/**
	 * Returns a Collection view of emotions defined in this EmotionMLDocument.
	 * @return a Collection view of emotions
	 */
	public Collection<Emotion> getEmotions()
	{
		List<Emotion> ret = new ArrayList<Emotion>();
		for(EmotionNode node : emotionNodes)
			if (node instanceof Emotion)
				ret.add((Emotion) node);
		return ret;
	}


	/**
	 * Returns a live List of all the EmotionNodes in the order they are defined.
	 */
	public List<EmotionNode> getEmotionNodes()
	{
		return emotionNodes;
	}


	/**
	 * Returns the vocabulary that has the given identifier.
	 * @param id an identifier
	 * @return null if not found
	 */
	public Vocabulary getVocabulary(String id)
	{
		return vocabularies.get(id);
	}


	/**
	 * Adds all the given emotion nodes to this EmotionMLDocument.
	 * @param emotionNodes
	 */
	public void addAll(List<? extends EmotionNode> emotionNodes)
	{
		this.emotionNodes.addAll(emotionNodes);
	}


	/**
	 * Returns a String representation of this EmotionMLDocument.
	 */
	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		if (hasInfo())
			ret.append("info=").append(getInfo()).append("\n");
		for(Vocabulary vocabulary : vocabularies.values())
			ret.append(vocabulary).append("\n");
		for(EmotionNode node : emotionNodes)
			ret.append(node).append("\n");
		return ret.toString().trim();
	}


	/**
	 * Moves the vocabularies references from the emotion level to this document level if all the
	 * emotions refer to the same vocabularies. If some emotions refer to different vocabularies, or
	 * if they refer to different vocabularies than this document, this method does nothing and
	 * returns false. This method alters vocabularies references of this document and of the
	 * emotions it contains.
	 * @return false if there are discrepancies in vocabularies references that prevent pushing
	 * them to the document level, true if the operation has been performed
	 */
	public boolean pushVocabulariesUpward()
	{
		Map<VocabularyType, URI> uris = new HashMap<VocabularyType, URI>(getAllDescriptorSetURIs());

		// check discrepancies in emotion uris
		for(EmotionNode node : emotionNodes)
			if (node instanceof Emotion)
			{
				Emotion emotion = (Emotion) node;
				for(VocabularyType type : VocabularyType.values())
					if (emotion.hasVocabularySetURI(type))
					{
						URI emoURI = emotion.getDescriptorSetURI(type);
						if (uris.containsKey(type) && !uris.get(type).equals(emoURI))
							return false;
						uris.put(type, emoURI);
					}
			}

		// once we're sure there is no conflict, do the modifications
		for(EmotionNode node : emotionNodes)
			if (node instanceof Emotion)
				for(VocabularyType type : VocabularyType.values())
					((Emotion) node).unsetDescriptorSetURI(type);

		setAllDescriptorSetURIs(uris);
		return true;
	}


///// for chaining

	@Override
	public EmotionMLDocument setInfo(Info info)
	{
		return (EmotionMLDocument) super.setInfo(info);
	}

}
