package org.qa.selenium;

import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Main interface to implement safe usage of the Selenium WebDriver when
 * interacting with DOM elements {@link WebElement}
 *
 * Created By: Justin Graham
 * Date: 1/29/13
 */
public interface SeleniumCommands
{
	/**
	 * Looks for any {@link WebElement}s using the given locator. Initially we start a FluentWait using
	 * default times (unless overridden by {@link SeleniumCommands#SetFluentWaitTime(Integer,
	 * TimeUnit, Integer, TimeUnit)}. If one element is found we check that it is visible to the user
	 * before calling a new {@link org.openqa.selenium.interactions.Actions} event to move the mouse
	 * over the {@link WebElement] and then send a click command. If more then one element is found we
	 * iterate through them looking for the first visiable which we click.
	 *
	 * @param locator The locator of type {@link Using#XPath(String)}, {@link Using#CSS(String)}, or
	 * {@link Using#ID(String)}
	 * @return The {@link Commands} object with our {@link org.openqa.selenium.WebDriver} instance
	 */
	public SeleniumCommands Click(Using locator);

	/**
	 * Follows the same flow as {@link SeleniumCommands#Click(Using)} but allows the user to output a custom
	 * logging message in the form of the link text or link name.
	 *
	 * @param locator The locator of type {@link Using#XPath(String)}, {@link Using#CSS(String)}, or
	 * {@link Using#ID(String)}
	 * @param linkName The link text or button text to be sent to the logger
	 * @return The {@link Commands} object with our {@link org.openqa.selenium.WebDriver} instance
	 */
	public SeleniumCommands Click(Using locator, String linkName);

	public SeleniumCommands ClickRandom(Using locator);
	public SeleniumCommands ClickRandom(Using locator, String linkName);
	public SeleniumCommands CheckBox(boolean selected, Using locator);
	public SeleniumCommands Type(String input, Using locator);
	public SeleniumCommands Type(String input, Using locator, String inputName);
	public SeleniumCommands ComboBoxByText(String visibleText, Using locator);
	public SeleniumCommands ComboBoxByText(String visibleText, Using locator, String comboBoxName);
	public SeleniumCommands ComboBoxByIndex(int index, Using locator);
	public SeleniumCommands ComboBoxRandom(Using locator);
	public List<WebElement> ComboBoxGetOptions(Using locator);
	public String ComboBoxGetDisplayText(Using locator);
	public int ComboBoxGetDisplayIndex(Using locator);
	public SeleniumCommands WaitForElement(Using locator);
	public SeleniumCommands WaitForElement(Using locator, String elementName);
	public WebElement GetElement(Using locator);
	public List<WebElement> GetElements(Using locator);
	public int GetElementCount(Using locator);
	public String GetElementAttribute(String attribute, Using locator);
	public SeleniumCommands EnterWebFrame(Using locator);
	public SeleniumCommands SetFluentWaitTime(
			Integer waitTime, TimeUnit waitUnit, Integer pollingTime, TimeUnit pollingUnit
	);
	public SeleniumCommands WaitForTime(long time, TimeUnit unit);
	public SeleniumCommands Open(String url);
	public SeleniumCommands Close();
	public SeleniumCommands PopAllWebFrames();
	public SeleniumCommands PopCurrentWebFrame();

	/**
	 * Uses Javascript to find the unique XPath of the WebElement passed in. For this function to work
	 * the WebElement passed into the function must be present in the current DOM.
	 *
	 * @param element the {@link WebElement} we want to XPath to
	 * @return A valid unique XPath targeting the element
	 */
	public String GetElementXPath(WebElement element);

	public SeleniumCommands OpenNewWindow(String url);
	public SeleniumCommands SwitchToWindow();
	public SeleniumCommands SwitchToParent();
	public SeleniumCommands CloseWindow();
	public String GetText(Using locator);
}
