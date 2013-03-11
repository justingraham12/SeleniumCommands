package org.qa.selenium.internal;

import org.openqa.selenium.WebElement;
import org.qa.selenium.SeleniumCommands;

import java.util.List;

/**
 * Created By: Justin Graham
 * Date: 1/29/13
 */
public interface ByID
{
	SeleniumCommands clickElementByID(String id);
	SeleniumCommands clickRandomElementByID(String id);
	SeleniumCommands typeByID(String input, String id);
	SeleniumCommands comboBoxVisibleTextByID(String visibleText, String id);
	SeleniumCommands comboBoxRandomByID(String id);
	SeleniumCommands comboBoxIndexByID(int index, String id);
	List<WebElement> comboBoxGetOptionsByID(String id);
	String comboBoxGetDisplayTextByID(String id);
	int comboBoxGetDisplayIndexByID(String id);
	SeleniumCommands waitForID(String id);
	List<WebElement> getElementsByID(String id);
	WebElement getElementByID(String id);
	int getElementCountByID(String id);
	String getElementAttributeByID(String attribute, String id);
	SeleniumCommands enterWebFrameByID(String id);
	String getTextByID(String id);
	SeleniumCommands checkBoxByID(boolean selected, String id);
}
