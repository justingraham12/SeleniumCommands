package org.qa.selenium;

import org.openqa.selenium.WebElement;
import org.qa.selenium.internal.ByCSS;
import org.qa.selenium.internal.ByID;
import org.qa.selenium.internal.ByWebElement;
import org.qa.selenium.internal.ByXPath;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.List;

/**
 * Created By: Justin Graham
 * Date: 1/29/13
 */
public abstract class Using
{
	public static Using XPath(final String xpathExpression)
	{
		return new UsingXPath(xpathExpression);
	}

	public static Using CSS(final String cssExpression)
	{
		return new UsingCss(cssExpression);
	}

	public static Using ID(final String id)
	{
		return new UsingID(id);
	}

	public static Using WebElement(final WebElement element)
	{
		return new UsingWebElement(element);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Using commands = (Using) o;

		return toString().equals(commands.toString());
	}

	@Override
	public int hashCode()
	{
		return toString().hashCode();
	}

	@Override
	public String toString()
	{
		return "[unknown locator]";
	}

	public abstract void Click(SeleniumCommands commands);
	public abstract void ClickRandom(SeleniumCommands commands);
	public abstract void Type(String input, SeleniumCommands commands);
	public abstract void ComboBoxText(String visibleText, SeleniumCommands commands);
	public abstract void ComboBoxIndex(int index, SeleniumCommands commands);
	public abstract void ComboBoxRandom(SeleniumCommands commands);
	public abstract String ComboBoxDisplayText(SeleniumCommands commands);
	public abstract int ComboBoxDisplayIndex(SeleniumCommands commands);
	public abstract List<WebElement> ComboBoxGetOptions(SeleniumCommands commands);
	public abstract void WaitForElement(SeleniumCommands commands);
	public abstract WebElement GetElement(SeleniumCommands commands);
	public abstract List<WebElement> GetElements(SeleniumCommands commands);
	public abstract int GetElementCount(SeleniumCommands commands);
	public abstract String GetElementAttribute(String attribute, SeleniumCommands commands);
	public abstract void EnterWebFrame(SeleniumCommands commands);
	public abstract String GetText(SeleniumCommands commands);
	public abstract void CheckBox(boolean selected, SeleniumCommands commands);

	public static class UsingXPath extends Using
	{
		private String xpath;

		public UsingXPath(String xpath)
		{
			if (xpath == null) throw new NullPointerException("XPath must not be null");
			validateXPath(xpath);
			this.xpath = xpath;
		}

		@Override
		public void Click(SeleniumCommands commands)
		{
			((ByXPath) commands).clickElementByXPath(xpath);
		}

		@Override
		public void ClickRandom(SeleniumCommands commands)
		{
			((ByXPath) commands).clickRandomElementByXPath(xpath);
		}

		@Override
		public void CheckBox(boolean selected, SeleniumCommands commands)
		{
			((ByXPath) commands).checkBoxByXPath(selected, xpath);
		}

		@Override
		public WebElement GetElement(SeleniumCommands commands)
		{
			return ((ByXPath) commands).getElementByXPath(xpath);
		}

		@Override
		public int GetElementCount(SeleniumCommands commands)
		{
			return ((ByXPath) commands).getElementCountByXPath(xpath);
		}

		@Override
		public void Type(String input, SeleniumCommands commands)
		{
			if (input == null) throw new NullPointerException("String input must not be Null");
			((ByXPath) commands).typeByXPath(input, xpath);
		}

		@Override
		public void ComboBoxText(String visibleText, SeleniumCommands commands)
		{
			if (visibleText == null) throw new NullPointerException("String visibleText must not be Null");
			((ByXPath) commands).comboBoxVisibleTextByXPath(visibleText, xpath);
		}

		@Override
		public void ComboBoxIndex(int index, SeleniumCommands commands)
		{
			if (index < 0) throw new IllegalArgumentException("Index must be greater then 0");
			((ByXPath) commands).comboBoxIndexByXPath(index, xpath);
		}

		@Override
		public void ComboBoxRandom(SeleniumCommands commands)
		{
			((ByXPath) commands).comboBoxRandomByXPath(xpath);
		}

		@Override
		public String ComboBoxDisplayText(SeleniumCommands commands)
		{
			return ((ByXPath) commands).comboBoxGetDisplayTextByXPath(xpath);
		}

		@Override
		public int ComboBoxDisplayIndex(SeleniumCommands commands)
		{
			return ((ByXPath) commands).comboBoxGetDisplayIndexByXPath(xpath);
		}

		@Override
		public List<WebElement> ComboBoxGetOptions(SeleniumCommands commands)
		{
			return ((ByXPath) commands).comboBoxGetOptionsByXPath(xpath);
		}

		@Override
		public void WaitForElement(SeleniumCommands commands)
		{
			((ByXPath) commands).waitForXPath(xpath);
		}

		@Override
		public List<WebElement> GetElements(SeleniumCommands commands)
		{
			return ((ByXPath) commands).getElementsByXPath(xpath);
		}

		@Override
		public String GetElementAttribute(String attribute, SeleniumCommands commands)
		{
			if (attribute == null) throw new NullPointerException("String param must not be Null");
			return ((ByXPath) commands).getElementAttributeByXPath(attribute, xpath);
		}

		@Override
		public String GetText(SeleniumCommands commands)
		{
			return ((ByXPath) commands).getTextByXPath(xpath);
		}

		@Override
		public void EnterWebFrame(SeleniumCommands commands)
		{
			((ByXPath) commands).enterWebFrameByXPath(xpath);
		}

		@Override
		public String toString()
		{
			return "XPath: " + xpath;
		}

		private static void validateXPath(String x)
		{
			XPathFactory factory = XPathFactory.newInstance();
			javax.xml.xpath.XPath validate = factory.newXPath();

			try
			{
				validate.compile(x);
			}
			catch (XPathExpressionException e)
			{
				throw new MalformedXmlElement("Invalid XPath: " + x);
			}
		}
	}

	public static class UsingCss extends Using
	{
		private String css;

		public UsingCss(String css)
		{
			if (css == null) throw new NullPointerException("CSS must not be Null");
			this.css = css;
		}

		@Override
		public void Click(SeleniumCommands commands)
		{
			((ByCSS) commands).clickElementByCSS(css);
		}

		@Override
		public void ClickRandom(SeleniumCommands commands)
		{
			((ByCSS) commands).clickRandomElementByCSS(css);
		}

		@Override
		public void CheckBox(boolean selected, SeleniumCommands commands)
		{
			((ByCSS) commands).checkBoxByCSS(selected, css);
		}

		@Override
		public WebElement GetElement(SeleniumCommands commands)
		{
			return ((ByCSS) commands).getElementByCSS(css);
		}

		@Override
		public int GetElementCount(SeleniumCommands commands)
		{
			return ((ByCSS) commands).getElementCountByCSS(css);
		}

		@Override
		public void Type(String input, SeleniumCommands commands)
		{
			((ByCSS) commands).typeByCSS(input, css);
		}

		@Override
		public void ComboBoxText(String visibleText, SeleniumCommands commands)
		{
			((ByCSS) commands).comboBoxVisibleTextByCSS(visibleText, css);
		}

		@Override
		public void ComboBoxIndex(int index, SeleniumCommands commands)
		{
			if (index < 0) throw new IllegalArgumentException("Index must be greater then 0");
			((ByCSS) commands).comboBoxIndexByCSS(index, css);
		}

		@Override
		public void ComboBoxRandom(SeleniumCommands commands)
		{
			((ByCSS) commands).comboBoxRandomByCSS(css);
		}

		@Override
		public String ComboBoxDisplayText(SeleniumCommands commands)
		{
			return ((ByCSS) commands).comboBoxGetDisplayTextByCSS(css);
		}

		@Override
		public int ComboBoxDisplayIndex(SeleniumCommands commands)
		{
			return ((ByCSS) commands).comboBoxGetDisplayIndexByCSS(css);
		}

		@Override
		public List<WebElement> ComboBoxGetOptions(SeleniumCommands commands)
		{
			return ((ByCSS) commands).comboBoxGetOptionsByCSS(css);
		}

		@Override
		public void WaitForElement(SeleniumCommands commands)
		{
			((ByCSS) commands).waitForCSS(css);
		}

		@Override
		public List<WebElement> GetElements(SeleniumCommands commands)
		{
			return ((ByCSS) commands).getElementsByCSS(css);
		}

		@Override
		public String GetElementAttribute(String attribute, SeleniumCommands commands)
		{
			if (attribute == null) throw new NullPointerException("String param must not be Null");
			return ((ByCSS) commands).getElementAttributeByCSS(attribute, css);
		}

		@Override
		public String GetText(SeleniumCommands commands)
		{
			return ((ByCSS) commands).getTextByCSS(css);
		}

		@Override
		public void EnterWebFrame(SeleniumCommands commands)
		{
			((ByCSS) commands).enterWebFrameByCSS(css);
		}

		@Override
		public String toString()
		{
			return "Css: " + css;
		}
	}

	public static class UsingID extends Using
	{
		private String id;

		public UsingID(String id)
		{
			if (id == null) throw new NullPointerException("ID must not be Null");
			this.id = id;
		}

		@Override
		public void Click(SeleniumCommands commands)
		{
			((ByID) commands).clickElementByID(id);
		}

		@Override
		public void ClickRandom(SeleniumCommands commands)
		{
			((ByID) commands).clickRandomElementByID(id);
		}

		@Override
		public void CheckBox(boolean selected, SeleniumCommands commands)
		{
			((ByID) commands).checkBoxByID(selected, id);
		}

		@Override
		public WebElement GetElement(SeleniumCommands commands)
		{
			return ((ByID) commands).getElementByID(id);
		}

		@Override
		public int GetElementCount(SeleniumCommands commands)
		{
			return ((ByID) commands).getElementCountByID(id);
		}

		@Override
		public void Type(String input, SeleniumCommands commands)
		{
			((ByID) commands).typeByID(input, id);
		}

		@Override
		public void ComboBoxText(String visibleText, SeleniumCommands commands)
		{
			((ByID) commands).comboBoxVisibleTextByID(visibleText, id);
		}

		@Override
		public void ComboBoxIndex(int index, SeleniumCommands commands)
		{
			if (index < 0) throw new IllegalArgumentException("Index must be greater then 0");
			((ByID) commands).comboBoxIndexByID(index, id);
		}

		@Override
		public void ComboBoxRandom(SeleniumCommands commands)
		{
			((ByID) commands).comboBoxRandomByID(id);
		}

		@Override
		public String ComboBoxDisplayText(SeleniumCommands commands)
		{
			return ((ByID) commands).comboBoxGetDisplayTextByID(id);
		}

		@Override
		public int ComboBoxDisplayIndex(SeleniumCommands commands)
		{
			return ((ByID) commands).comboBoxGetDisplayIndexByID(id);
		}

		@Override
		public List<WebElement> ComboBoxGetOptions(SeleniumCommands commands)
		{
			return ((ByID) commands).comboBoxGetOptionsByID(id);
		}

		@Override
		public void WaitForElement(SeleniumCommands commands)
		{
			((ByID) commands).waitForID(id);
		}

		@Override
		public List<WebElement> GetElements(SeleniumCommands commands)
		{
			return ((ByID) commands).getElementsByID(id);
		}

		@Override
		public String GetElementAttribute(String attribute, SeleniumCommands commands)
		{
			if (attribute == null) throw new NullPointerException("String param must not be Null");
			return ((ByID) commands).getElementAttributeByID(attribute, id);
		}

		@Override
		public String GetText(SeleniumCommands commands)
		{
			return ((ByID) commands).getTextByID(id);
		}

		@Override
		public void EnterWebFrame(SeleniumCommands commands)
		{
			((ByID) commands).enterWebFrameByID(id);
		}

		@Override
		public String toString()
		{
			return "ID: '" + id + "'";
		}
	}

	public static class UsingWebElement extends Using
	{
		private WebElement element;
		private List<WebElement> elements;

		public UsingWebElement(WebElement element)
		{
			if (element == null) throw new NullPointerException("WebElement must not be Null");
			this.element = element;
		}

		public UsingWebElement(List<WebElement> elements)
		{
			if (elements == null || elements.isEmpty()) throw new NullPointerException("Elements must not be Null");
			this.elements = elements;
			this.element = elements.get(0);
		}

		@Override
		public void Click(SeleniumCommands commands)
		{
			((ByWebElement) commands).clickElementByWebElement(element);
		}

		@Override
		public void ClickRandom(SeleniumCommands commands)
		{
			((ByWebElement) commands).clickRandomElementByWebElement(elements);
		}

		@Override
		public void CheckBox(boolean selected, SeleniumCommands commands)
		{
			((ByWebElement) commands).checkBoxByWebElement(selected, element);
		}

		@Override
		public void Type(String input, SeleniumCommands commands)
		{
			((ByWebElement) commands).typeByWebElement(input, element);
		}

		@Override
		public void ComboBoxText(String visibleText, SeleniumCommands commands)
		{
			((ByWebElement) commands).comboBoxVisibleTextByWebElement(visibleText, element);
		}

		@Override
		public void ComboBoxIndex(int index, SeleniumCommands commands)
		{
			if (index < 0) throw new IllegalArgumentException("Index must be greater then 0");
			((ByWebElement) commands).comboBoxIndexByWebElement(index, element);
		}

		@Override
		public void ComboBoxRandom(SeleniumCommands commands)
		{
			((ByWebElement) commands).comboBoxRandomByWebElement(element);
		}

		@Override
		public String ComboBoxDisplayText(SeleniumCommands commands)
		{
			return ((ByWebElement) commands).comboBoxGetDisplayTextByWebElement(element);
		}

		@Override
		public int ComboBoxDisplayIndex(SeleniumCommands commands)
		{
			return ((ByWebElement) commands).comboBoxGetDisplayIndexByWebElement(element);
		}

		@Override
		public List<WebElement> ComboBoxGetOptions(SeleniumCommands commands)
		{
			return ((ByWebElement) commands).comboBoxGetOptionsByWebElement(element);
		}

		@Override
		public void WaitForElement(SeleniumCommands commands)
		{
			((ByWebElement) commands).waitForWebElement(element);
		}

		@Override
		public WebElement GetElement(SeleniumCommands commands)
		{
			return ((ByWebElement) commands).getElementByWebElement(element);
		}

		@Override
		public List<WebElement> GetElements(SeleniumCommands commands)
		{
			return ((ByWebElement) commands).getElementsByWebElement(element);
		}

		@Override
		public int GetElementCount(SeleniumCommands commands)
		{
			return ((ByWebElement) commands).getElementCountByWebElement(element);
		}

		@Override
		public String GetElementAttribute(String attribute, SeleniumCommands commands)
		{
			if (attribute == null) throw new NullPointerException("String param must not be Null");
			return ((ByWebElement) commands).getElementAttributeByWebElement(attribute, element);
		}

		@Override
		public String GetText(SeleniumCommands commands)
		{
			return ((ByWebElement) commands).getTextByWebElement(element);
		}

		@Override
		public void EnterWebFrame(SeleniumCommands commands)
		{
			((ByWebElement) commands).enterWebFrameByWebElement(element);
		}

		@Override
		public String toString()
		{
			String tagName = 	element.getTagName();
			String id = 		element.getAttribute("id");
			String clazz = 		element.getAttribute("class");
			String title = 		element.getAttribute("title");
			String name = 		element.getAttribute("name");
			String style = 		element.getAttribute("style");
			String type = 		element.getAttribute("type");

			String webElement = "WebElement: <" + tagName;
			webElement += (id != null && !id.equals("")) 		? " id='" + id + "'" 		: "";
			webElement += (clazz != null && !clazz.equals("")) 	? " class='" + clazz + "'" 	: "";
			webElement += (title != null && !title.equals("")) 	? " title='" + title + "'" 	: "";
			webElement += (name != null && !name.equals("")) 	? " name='" + name + "'" 	: "";
			webElement += (style != null && !style.equals("")) 	? " style='" + style + "'" 	: "";
			webElement += (type != null && !type.equals("")) 	? " type='" + type + "'" 	: "";
			webElement += ">...</" + tagName + ">";

			return webElement;
		}
	}
}
