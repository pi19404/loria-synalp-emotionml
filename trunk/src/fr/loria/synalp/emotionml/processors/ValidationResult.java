package fr.loria.synalp.emotionml.processors;

import org.w3c.dom.*;

/**
 * ValidationResult gathers the results of a validation. It contains both the schema validation
 * result (and its error message if needed) and the assertion validation result (and its error
 * message if needed). It also stores the Element on which the validation has been performed.
 * @author Alexandre Denis
 */
public class ValidationResult
{
	private Element element;
	private boolean isSchemaValid;
	private String schemaErrorMessage;
	private boolean isAssertionValid;
	private String assertionErrorMessage;


	/**
	 * Creates a new ValidationResult.
	 * @param element
	 * @param isSchemaValid
	 * @param schemaErrorMessage
	 * @param isAssertionValid
	 * @param assertionErrorMessage
	 */
	public ValidationResult(Element element, boolean isSchemaValid, String schemaErrorMessage, boolean isAssertionValid, String assertionErrorMessage)
	{
		this.element = element;
		this.isSchemaValid = isSchemaValid;
		this.schemaErrorMessage = schemaErrorMessage;
		this.isAssertionValid = isAssertionValid;
		this.assertionErrorMessage = assertionErrorMessage;
	}


	/**
	 * Returns the Element on which validation has been performed.
	 * @return an Element that corresponds either to &lt;emotionml&gt; or &lt;emotion&gt;
	 */
	public Element getElement()
	{
		return element;
	}


	/**
	 * Tests if this ValidationResult represents a successful validation of both the schema and the
	 * assertions.
	 * @return true if both the schema and the assertions are valid
	 */
	public boolean isValid()
	{
		return isSchemaValid && isAssertionValid;
	}


	/**
	 * Tests if this ValidationResult represents a successful validation of the schema.
	 * @return the isSchemaValid
	 */
	public boolean isSchemaValid()
	{
		return isSchemaValid;
	}


	/**
	 * Returns the error message of the schema validation if it was unsuccessful.
	 * @return the schemaErrorMessage
	 */
	public String getSchemaErrorMessage()
	{
		return schemaErrorMessage;
	}


	/**
	 * Tests if this ValidationResult represents a successful validation of the assertions.
	 * @return the isAssertionValid
	 */
	public boolean isAssertionValid()
	{
		return isAssertionValid;
	}


	/**
	 * Returns the error message of the assertions validation if it was unsuccessful.
	 * @return the assertionErrorMessage
	 */
	public String getAssertionErrorMessage()
	{
		return assertionErrorMessage;
	}


	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		if (isSchemaValid)
			ret.append("schema validated");
		else ret.append("schema invalidated: " + schemaErrorMessage);
		if (isAssertionValid)
			ret.append("; assertion validated");
		else ret.append("; assertion invalidated: " + assertionErrorMessage);
		return ret.toString();
	}
}
