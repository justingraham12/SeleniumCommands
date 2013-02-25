package org.qa.selenium;

/**
 * Created By: Justin Graham
 * Date: 10/31/12
 */
public class MalformedXmlElement extends RuntimeException
{
	public MalformedXmlElement()
	{
	}

	public MalformedXmlElement(String message)
	{
		super(message);
	}

	public MalformedXmlElement(String message, Throwable cause)
	{
		super(message, cause);
	}

	public MalformedXmlElement(Throwable cause)
	{
		super(cause);
	}

	public MalformedXmlElement(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
