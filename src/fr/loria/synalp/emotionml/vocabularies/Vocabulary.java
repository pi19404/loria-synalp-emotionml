package fr.loria.synalp.emotionml.vocabularies;

import java.io.Serializable;
import java.util.*;

import fr.loria.synalp.emotionml.info.*;

/**
 * A Vocabulary describes a list of possible values for a descriptor type.
 * @author Alexandre Denis
 */
@SuppressWarnings("serial")
public class Vocabulary extends InfoCarrier implements Serializable
{
	private String id;
	private VocabularyType type;
	private Map<String, VocabularyItem> items; // <name, item>


	@SuppressWarnings("unused")
	private Vocabulary()
	{
		
	}

	/**
	 * Creates a Vocabulary with given id and descriptor type. Warning: for serialization purposes a
	 * Vocabulary needs to have at least one VocabularyItem.
	 * @param id
	 * @param type
	 */
	public Vocabulary(String id, VocabularyType type)
	{
		this.id = id;
		this.type = type;
		this.items = new HashMap<String, VocabularyItem>();
	}


	/**
	 * Returns a Collection view of the item names defined in this Vocabulary.
	 */
	public Collection<String> getItemNames()
	{
		return items.keySet();
	}


	/**
	 * Adds a VocabularyItem with given name to this Vocabulary. If there is already a
	 * VocabularyItem with the same name, it is replaced.
	 */
	public void addItem(String name)
	{
		add(new VocabularyItem(name));
	}


	/**
	 * Adds a VocabularyItem to this Vocabulary. If there is already a VocabularyItem with the same
	 * name, it is replaced.
	 */
	public void add(VocabularyItem item)
	{
		items.put(item.getName(), item);
	}


	/**
	 * Removes a VocabularyItem with given name from this Vocabulary. Warning: for serialization
	 * purposes a Vocabulary needs to have at least one VocabularyItem.
	 * @param name
	 */
	public void removeItem(String name)
	{
		items.remove(name);
	}


	/**
	 * Removes a VocabularyItem from this Vocabulary. Warning: for serialization purposes a
	 * Vocabulary needs to have at least one VocabularyItem.
	 * @param item
	 */
	public void remove(VocabularyItem item)
	{
		items.remove(item.getName());
	}


	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}


	/**
	 * @return the type
	 */
	public VocabularyType getType()
	{
		return type;
	}


	/**
	 * Returns a Collection view of the VocabularyItem contained in this Vocabulary.
	 * @return the items
	 */
	public Collection<VocabularyItem> getItems()
	{
		return items.values();
	}


	/**
	 * Returns the VocabularyItem with given name.
	 * @return null if not found
	 */
	public VocabularyItem getItem(String name)
	{
		return items.get(name);
	}


	/**
	 * Tests if this Vocabulary contains a VocabularyItem with given name.
	 */
	public boolean hasItem(String name)
	{
		return items.containsKey(name);
	}


	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		ret.append("id=").append(id).append(" type=").append(type);
		if (hasInfo())
			ret.append(" ").append(getInfo());
		ret.append(" items: ");
		ret.append(items.values());
		return ret.toString();
	}
}
