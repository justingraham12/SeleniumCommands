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
	 * default times (unless overridden by {@link SeleniumCommands#setFluentWaitTime(Integer,
	 * TimeUnit, Integer, TimeUnit)}. If one element is found we check that it is visible to the user
	 * before calling a new {@link org.openqa.selenium.interactions.Actions} event to move the mouse
	 * over the {@link WebElement] and then send a click command. If more then one element is found we
	 * iterate through them looking for the first visiable which we click.
	 *
	 * @param locator The locator of type {@link Using#XPath(String)}, {@link Using#CSS(String)}, or
	 * {@link Using#ID(String)}
	 * @return The {@link Commands} object with our {@link org.openqa.selenium.WebDriver} instance
	 */
	public SeleniumCommands click(Using locator);

	/**
	 * Follows the same flow as {@link SeleniumCommands#click(Using)} but allows the user to output a custom
	 * logging message in the form of the link text or link name.
	 *
	 * @param locator The locator of type {@link Using#XPath(String)}, {@link Using#CSS(String)}, or
	 * {@link Using#ID(String)}
	 * @param linkName The link text or button text to be sent to the logger
	 * @return The {@link Commands} object with our {@link org.openqa.selenium.WebDriver} instance
	 */
	public SeleniumCommands click(Using locator, String linkName);

	public SeleniumCommands clickRandom(Using locator);
	public SeleniumCommands clickRandom(Using locator, String linkName);
	public SeleniumCommands checkBox(boolean selected, Using locator);
	public SeleniumCommands type(String input, Using locator);
	public SeleniumCommands type(String input, Using locator, String inputName);
	public SeleniumCommands comboBoxByText(String visibleText, Using locator);
	public SeleniumCommands comboBoxByText(String visibleText, Using locator, String comboBoxName);
	public SeleniumCommands comboBoxByIndex(int index, Using locator);
	public SeleniumCommands comboBoxRandom(Using locator);
	public List<WebElement> comboBoxGetOptions(Using locator);
	public String comboBoxGetDisplayText(Using locator);
	public int comboBoxGetDisplayIndex(Using locator);
	public SeleniumCommands waitForElement(Using locator);
	public SeleniumCommands waitForElement(Using locator, String elementName);
	public WebElement getElement(Using locator);
	public List<WebElement> getElements(Using locator);
	public int getElementCount(Using locator);
	public String getElementAttribute(String attribute, Using locator);
	public SeleniumCommands enterWebFrame(Using locator);
	public SeleniumCommands setFluentWaitTime(
            Integer waitTime, TimeUnit waitUnit, Integer pollingTime, TimeUnit pollingUnit
    );
	public SeleniumCommands waitForTime(long time, TimeUnit unit);
	public SeleniumCommands open(String url);
	public SeleniumCommands close();
	public SeleniumCommands popAllWebFrames();
	public SeleniumCommands popCurrentWebFrame();

	/**
	 * Uses Javascript to find the unique XPath of the WebElement passed in. For this function to work
	 * the WebElement passed into the function must be present in the current DOM.
	 *
	 * @param element the {@link WebElement} we want to XPath to
	 * @return A valid unique XPath targeting the element
	 */
	public String getElementXPath(WebElement element);

	public SeleniumCommands openNewWindow(String url);
	public SeleniumCommands switchToWindow();
	public SeleniumCommands switchToParent();
	public SeleniumCommands closeWindow();
	public String getText(Using locator);
}
