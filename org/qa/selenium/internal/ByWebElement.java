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
	SeleniumCommands comboBoxIndexByWebElement(int index, WebElement element);
	List<WebElement> comboBoxGetOptionsByWebElement(WebElement element);
	String comboBoxGetDisplayTextByWebElement(WebElement element);
	int comboBoxGetDisplayIndexByWebElement(WebElement element);
	SeleniumCommands waitForWebElement(WebElement element);
	List<WebElement> getElementsByWebElement(WebElement element);
	WebElement getElementByWebElement(WebElement element);
	int getElementCountByWebElement(WebElement element);
	String getElementAttributeByWebElement(String attribute, WebElement element);
	SeleniumCommands enterWebFrameByWebElement(WebElement element);
	String getTextByWebElement(WebElement element);
	SeleniumCommands checkBoxByWebElement(boolean selected, WebElement element);
}
