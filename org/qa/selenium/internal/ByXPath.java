package org.qa.selenium.internal;

import org.openqa.selenium.WebElement;
import org.qa.selenium.SeleniumCommands;

import java.util.List;

/**
 * Created By: Justin Graham
 * Date: 1/29/13
 */
public interface ByXPath
{
	/**
	 * Finds the first visible element at the supplied XPath. Waits for the element to
	 * become clickable. Then moves the mouse to the element and clicks it.
	 *
	 * @param xpath The location of the element[s]
	 * @return The {@link SeleniumCommands} object with our instance of {@link org.openqa.selenium.WebDriver}
	 */
	SeleniumCommands clickElementByXPath(String xpath);

	SeleniumCommands clickRandomElementByXPath(String xpath);

	/**
	 * Finds the first visible element at the supplied XPath before clearing any existing text from the
	 * element. It will then type the supplied input string into the element.
	 *
	 * @param input The string we want to type into the element
	 * @param xpath The location of the element[s]
	 * @return The {@link SeleniumCommands} object with our instance of {@link org.openqa.selenium.WebDriver}
	 */
	SeleniumCommands typeByXPath(String input, String xpath);

	/**
	 * Finds the first visible element at the supplied XPath before creating a {@link org.openqa.selenium.support.ui.Select}
	 * out of it. It then looks for an option in the select that has the same display text as the visibleText string
	 * we passed in. If found it selects it.
	 *
	 * @param visibleText The visible text of the combo box we want to select
	 * @param xpath The location of the element[s]
	 * @return The {@link SeleniumCommands} object with our instance of {@link org.openqa.selenium.WebDriver}
	 */
	SeleniumCommands comboBoxVisibleTextByXPath(String visibleText, String xpath);

	SeleniumCommands comboBoxRandomByXPath(String xpath);

	SeleniumCommands comboBoxIndexByXPath(int index, String xpath);

	String comboBoxGetDisplayTextByXPath(String xpath);

	int comboBoxGetDisplayIndexByXPath(String xpath);

	List<WebElement> comboBoxGetOptionsByXPath(String xpath);

	/**
	 * Runs a {@link org.openqa.selenium.support.ui.FluentWait} for the element at the supplied XPath. This
	 * causes our {@link org.openqa.selenium.WebDriver} to pause until the element is found.
	 *
	 * @param xpath The location of the element[s]
	 * @return The {@link SeleniumCommands} object with our instance of {@link org.openqa.selenium.WebDriver}
	 */
	SeleniumCommands waitForXPath(String xpath);

	/**
	 * Finds and returns a {@link List} containing all {@link WebElement}s at the supplied XPath.
	 *
	 * @param xpath The location of the element[s]
	 * @return A {@link List} of {@link WebElement}s
	 */
	List<WebElement> getElementsByXPath(String xpath);

	/**
	 * Finds and returns the {@link WebElement} at the supplied XPath. If more than 1 element is found
	 * only the first will be returned.
	 *
	 * @param xpath The location of the element[s]
	 * @return A {@link WebElement}
	 */
	WebElement getElementByXPath(String xpath);

	/**
	 * Finds all {@link WebElement}s at the XPath and returns how many it found
	 *
	 * @param xpath The location of the element[s]
	 * @return The number of elements found
	 */
	int getElementCountByXPath(String xpath);

	String getElementAttributeByXPath(String attribute, String xpath);

	String getTextByXPath(String xpath);

	SeleniumCommands enterWebFrameByXPath(String xpath);

	SeleniumCommands checkBoxByXPath(boolean selected, String xpath);
}
