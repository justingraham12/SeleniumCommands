package org.qa.selenium.internal;

import org.openqa.selenium.WebElement;
import org.qa.selenium.SeleniumCommands;

import java.util.List;

/**
 * Created By: Justin Graham
 * Date: 2/5/13
 */
public interface ByWebElement
{
	SeleniumCommands clickElementByWebElement(WebElement element);
	SeleniumCommands clickRandomElementByWebElement(List<WebElement> elements);
	SeleniumCommands typeByWebElement(String input, WebElement element);
	SeleniumCommands comboBoxVisibleTextByWebElement(String visibleText, WebElement element);
	SeleniumCommands comboBoxRandomByWebElement(WebElement element);
	SeleniumCommands waitForWebElement(WebElement element);
	List<WebElement> getElementsByWebElement(WebElement element);
	WebElement getElementByWebElement(WebElement element);
	int getElementCountByWebElement(WebElement element);
	String getElementAttributeByWebElement(String attribute, WebElement element);
}
