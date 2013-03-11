package org.qa.selenium.internal;

import org.openqa.selenium.WebElement;
import org.qa.selenium.SeleniumCommands;

import java.util.List;

/**
 * Created By: Justin Graham
 * Date: 1/29/13
 */
public interface ByCSS
{
	SeleniumCommands clickElementByCSS(String css);
	SeleniumCommands clickRandomElementByCSS(String css);
	SeleniumCommands typeByCSS(String input, String css);
	SeleniumCommands comboBoxVisibleTextByCSS(String visibleText, String css);
	SeleniumCommands comboBoxRandomByCSS(String css);
	SeleniumCommands comboBoxIndexByCSS(int index, String css);
	List<WebElement> comboBoxGetOptionsByCSS(String css);
	String comboBoxGetDisplayTextByCSS(String css);
	int comboBoxGetDisplayIndexByCSS(String css);
	SeleniumCommands waitForCSS(String css);
	List<WebElement> getElementsByCSS(String css);
	WebElement getElementByCSS(String css);
	int getElementCountByCSS(String css);
	String getElementAttributeByCSS(String attribute, String css);
	SeleniumCommands enterWebFrameByCSS(String css);
	String getTextByCSS(String css);
	SeleniumCommands checkBoxByCSS(boolean selected, String css);
}
