package org.qa.selenium;

import com.google.common.base.Function;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.qa.selenium.internal.ByCSS;
import org.qa.selenium.internal.ByID;
import org.qa.selenium.internal.ByWebElement;
import org.qa.selenium.internal.ByXPath;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created By: Justin Graham
 * Date: 1/29/13
 */
public class Commands implements SeleniumCommands, ByXPath, ByCSS, ByID, ByWebElement
{
	/*===================================================================
	 *
	 * Class Variables
	 *
	 *===================================================================*/

	/** Instance of WebDriver to use when calling commands */
	private final WebDriver driver;

	/** Our log4j logger */
	private Logger logger = LogManager.getLogger(getClass().getSimpleName());

	/** Initial values for FluentWait's timeout and polling in Seconds */
	private long waitForElement = 15;
	private long pollingForElement = 1;

	/** Holds the toString of the last command we called allowing us to throw which command we hit an error on */
	private String lastCommand;

	/** Hold the url of the page we last called a command on */
	private String currentUrl;

	/** Maintains the order in which we entered nested WebFrames */
	private LinkedList<Using> webFrames = new LinkedList<Using>();

	private WindowBuilder windowBuilder;

	/** Window handles in the event of a popup */
	private String parentHandle;
	private String popUpHandle;


	/*===================================================================
	 *
	 * Constructor
	 *
	 *===================================================================*/

	public Commands(WebDriver driver)
	{
		this.driver = driver;
	}

	/*===================================================================
	 *
	 * SeleniumCommands functions
	 *
	 *===================================================================*/

	@Override
	public SeleniumCommands click(Using locator)
	{
		setCurrentUrl();
		setLastCommand("click Using " + locator);
		locator.Click(this);
		return this;
	}

	@Override
	public SeleniumCommands click(Using locator, String linkName)
	{
		setCurrentUrl();
		setLastCommand("click '" + linkName + "' Using " + locator);
		locator.Click(this);
		return this;
	}

	@Override
	public SeleniumCommands clickRandom(Using locator, String linkName)
	{
		setCurrentUrl();
		setLastCommand("click Random '" + linkName + "' Using " + locator);
		locator.ClickRandom(this);
		return this;
	}

	@Override
	public SeleniumCommands clickRandom(Using locator)
	{
		setCurrentUrl();
		setLastCommand("click Random Using " + locator);
		locator.ClickRandom(this);
		return this;
	}

	@Override
	public SeleniumCommands checkBox(boolean selected, Using locator)
	{
		setCurrentUrl();
		String option = (selected) ? "check" : "un-check";
		setLastCommand("checkBox '" + option + "' Using " + locator);
		locator.CheckBox(selected, this);
		return this;
	}

	@Override
	public WebElement getElement(Using locator)
	{
		setCurrentUrl();
		setLastCommand("getElement Using " + locator);
		return locator.GetElement(this);
	}

	@Override
	public int getElementCount(Using locator)
	{
		setCurrentUrl();
		setLastCommand("getElementCount Using " + locator);
		return locator.GetElementCount(this);
	}

	@Override
	public SeleniumCommands type(String input, Using locator)
	{
		setCurrentUrl();
		setLastCommand("type '" + input + "' Using " + locator);
		locator.Type(input, this);
		return this;
	}

	@Override
	public SeleniumCommands type(String input, Using locator, String inputName)
	{
		setCurrentUrl();
		setLastCommand("type '" + input + "' into " + inputName + " Using " + locator);
		locator.Type(input, this);
		return this;
	}

	@Override
	public SeleniumCommands comboBoxByText(String visibleText, Using locator)
	{
		setCurrentUrl();
		setLastCommand("Select '" + visibleText + "' Using " + locator);
		locator.ComboBoxText(visibleText, this);
		return this;
	}

	@Override
	public SeleniumCommands comboBoxByIndex(int index, Using locator)
	{
		setCurrentUrl();
		setLastCommand("Select index '" + index + "' Using " + locator);
		locator.ComboBoxIndex(index, this);
		return this;
	}

	@Override
	public SeleniumCommands comboBoxByText(
            String visibleText, Using locator, String comboBoxName
    )
	{
		setCurrentUrl();
		setLastCommand("Select '" + visibleText + "' from '" + comboBoxName + "' Using " + locator);
		locator.ComboBoxText(visibleText, this);
		return this;
	}

	@Override
	public String comboBoxGetDisplayText(Using locator)
	{
		setCurrentUrl();
		setLastCommand("ComboBox get display text Using " + locator);
		return locator.ComboBoxDisplayText(this);
	}

	@Override
	public int comboBoxGetDisplayIndex(Using locator)
	{
		setCurrentUrl();
		setLastCommand("ComboBox get display index Using " + locator);
		return locator.ComboBoxDisplayIndex(this);
	}

	@Override
	public SeleniumCommands comboBoxRandom(Using locator)
	{
		setCurrentUrl();
		setLastCommand("Select Random Using " + locator);
		locator.ComboBoxRandom(this);
		return this;
	}

	@Override
	public List<WebElement> comboBoxGetOptions(Using locator)
	{
		setCurrentUrl();
		setLastCommand("Get ComboBox Options Using " + locator);
		return locator.ComboBoxGetOptions(this);
	}

	@Override
	public SeleniumCommands waitForElement(Using locator)
	{
		setCurrentUrl();
		setLastCommand("Wait for WebElement Using " + locator);
		locator.WaitForElement(this);
		return this;
	}

	@Override
	public SeleniumCommands waitForElement(Using locator, String elementName)
	{
		setCurrentUrl();
		setLastCommand("Wait for WebElement '" + elementName + "' Using " + locator);
		locator.WaitForElement(this);
		return this;
	}

	@Override
	public List<WebElement> getElements(Using locator)
	{
		setCurrentUrl();
		setLastCommand("getElements Using " + locator);
		return locator.GetElements(this);
	}

	@Override
	public String getElementAttribute(String attribute, Using locator)
	{
		setCurrentUrl();
		setLastCommand("getElementAttribute '" + attribute + "' Using " + locator);
		return locator.GetElementAttribute(attribute, this);
	}

	@Override
	public String getText(Using locator)
	{
		setCurrentUrl();
		setLastCommand("getText Using " + locator);
		return locator.GetText(this);
	}

	@Override
	public SeleniumCommands enterWebFrame(Using locator)
	{
		setCurrentUrl();
		setLastCommand("Enter Frame Using " + locator);
		webFrames.addFirst(locator);
		locator.EnterWebFrame(this);
		return this;
	}

	@Override
	public SeleniumCommands setFluentWaitTime(
            Integer waitTime, TimeUnit waitUnit, Integer pollingTime, TimeUnit pollingUnit
    )
	{
		waitTime = (waitTime == null || waitTime < 0) ? 0 : waitTime;
		pollingTime = (pollingTime == null || pollingTime < 0) ? 0 : pollingTime;
		waitUnit = (waitUnit == null) ? TimeUnit.SECONDS : waitUnit;
		pollingUnit = (pollingUnit == null) ? TimeUnit.SECONDS : pollingUnit;

		waitForElement = waitUnit.toSeconds(waitTime);
		pollingForElement = pollingUnit.toSeconds(pollingTime);

		if (waitForElement < pollingForElement)
			throw new IllegalStateException("Wait time must be greater than or equal to polling time");

		return this;
	}

	@Override
	public SeleniumCommands waitForTime(long time, TimeUnit unit)
	{
		if (time <= 0) throw new IllegalArgumentException("Wait time must be positive and greater than 0");
		long waitUntil = System.currentTimeMillis() + unit.toMillis(time);
		while (System.currentTimeMillis() < waitUntil)
		{/*Sleep*/}
		return this;
	}

	@Override
	public SeleniumCommands open(String url)
	{
		setLastCommand("open '" + url + "'");
		validateURL(url);
		driver.get(url);
		return this;
	}

	@Override
	public SeleniumCommands close()
	{
		setLastCommand("close WebDriver " + driver.getCurrentUrl());
		try
		{
			driver.quit();
		} catch (Exception e)
		{/*Ignore if driver is already closed*/}
		return this;
	}

	@Override
	public SeleniumCommands popAllWebFrames()
	{
		setCurrentUrl();
		setLastCommand("Pop All Frames");
		webFrames.clear();
		driver.switchTo().defaultContent();
		return this;
	}

	@Override
	public SeleniumCommands popCurrentWebFrame()
	{
		setCurrentUrl();
		setLastCommand("Pop Current Frame " + webFrames.getFirst());
		if (webFrames.size() <= 1) popAllWebFrames();
		else
		{
			//switch to default webFrame
			driver.switchTo().defaultContent();

			//remove the last frame we had entered
			webFrames.removeFirst();

			//We need to enter each frame
			for (int i=webFrames.size()-1; i>=0; i--)
			{
				webFrames.get(i).EnterWebFrame(this);
			}
		}
		return this;
	}

	@Override
	public String getElementXPath(WebElement element)
	{
		setLastCommand("getElementXPath");
		return (String) ((JavascriptExecutor) driver).executeScript(
				"getXPath=function(node)" +
						"{" +
						"if (node.id !== '')" +
						"{" +
						"return '//' + node.tagName.toLowerCase() + '[@id=\"' + node.id + '\"]'" +
						"}" +

						"if (node === document.body)" +
						"{" +
						"return node.tagName.toLowerCase()" +
						"}" +

						"var nodeCount = 0;" +
						"var childNodes = node.parentNode.childNodes;" +

						"for (var i=0; i<childNodes.length; i++)" +
						"{" +
						"var currentNode = childNodes[i];" +

						"if (currentNode === node)" +
						"{" +
						"return getXPath(node.parentNode) + '/' + node.tagName.toLowerCase() + '[' + (nodeCount+1) + ']'" +
						"}" +

						"if (currentNode.nodeType === 1 && " +
						"currentNode.tagName.toLowerCase() === node.tagName.toLowerCase())" +
						"{" +
						"nodeCount++" +
						"}" +
						"}" +
						"};" +

						"return getXPath(arguments[0]);", element);
	}

	@Override
	public SeleniumCommands openNewWindow(String url)
	{
		setLastCommand("open New Window " + url);
		validateURL(url);
		windowBuilder = new WindowBuilder(driver, url);
		return this;
	}

	@Override
	public SeleniumCommands switchToWindow()
	{
		if (windowBuilder != null)
		{
			setLastCommand("Switch to Window");
			windowBuilder.switchToWindow();
			logger.debug("Controlling Window: " + driver.getCurrentUrl());
		}
		else
		{
			setLastCommand("Switch to Popup");
			parentHandle = driver.getWindowHandle();
			Set<String> handles = driver.getWindowHandles();
			handles.remove(parentHandle);
			if (handles.size() == 0)
			{
				logger.warn("Could not find window to switch to. Returning control to the parent window");
				parentHandle = null;
				return this;
			}
			popUpHandle = (String) handles.toArray()[0];
			driver.switchTo().window(popUpHandle);
			logger.debug("Controlling Popup: " + driver.getCurrentUrl());
		}

		return this;
	}

	@Override
	public SeleniumCommands switchToParent()
	{
		setLastCommand("Switch to Parent");
		boolean parent = false;
		if (windowBuilder != null)
		{
			windowBuilder.switchToParent();
		}
		else if (popUpHandle != null)
		{
			driver.switchTo().window(parentHandle);
		}
		else
		{
			parent = true;
			logger.warn("switchToParent Invalid: WebDriver already has control of the parent window");
		}

		if (!parent)
		{
			logger.debug("Controlling Parent: " + driver.getCurrentUrl());
		}

		return this;
	}

	@Override
	public SeleniumCommands closeWindow()
	{
		setLastCommand("close Window");
		if (windowBuilder != null)
		{
			windowBuilder.close();
			logger.debug("Closed Window " + windowBuilder.getWindowUrl());
			windowBuilder = null;
		}
		else if (popUpHandle != null)
		{
			if (driver.getWindowHandle().equals(popUpHandle))
			{
				String popUpUrl = driver.getCurrentUrl();
				driver.close();
				popUpHandle = null;
				driver.switchTo().window(parentHandle);
				logger.debug("Closed Popup " + popUpUrl);
			}
			else
			{
				logger.warn("closeWindow Invalid; WebDriver has control of the parent window");
			}
		}

		return this;
	}

	/*===================================================================
	 *
	 * ByCSS functions
	 *
	 *===================================================================*/

	@Override
	public SeleniumCommands clickElementByCSS(String css)
	{
		WebElement element = fluentWaitForElementCss(css);
		fluentWaitForClickableCss(css);
		new Actions(driver).moveToElement(element).perform();
		element.click();
		return this;
	}

	@Override
	public SeleniumCommands clickRandomElementByCSS(String css)
	{
		waitForCSS(css);
		List<WebElement> elements = getAllVisibleElements(fluentWaitForElementsCss(css));
		return clickRandom(elements);
	}

	@Override
	public SeleniumCommands checkBoxByCSS(boolean selected, String css)
	{
		WebElement element = fluentWaitForElementCss(css);
		if (selected != element.isSelected())
		{
			clickElementByWebElement(element);
		}
		return this;
	}

	@Override
	public WebElement getElementByCSS(String css)
	{
		return fluentWaitForElementCss(css);
	}

	@Override
	public int getElementCountByCSS(String css)
	{
		List<WebElement> element = fluentWaitForElementsCss(css);
		return (element == null) ? 0 : element.size();
	}

	@Override
	public List<WebElement> getElementsByCSS(String css)
	{
		return fluentWaitForElementsCss(css);
	}

	@Override
	public SeleniumCommands waitForCSS(String css)
	{
		fluentWaitForElementCss(css);
		return this;
	}

	@Override
	public SeleniumCommands comboBoxVisibleTextByCSS(String visibleText, String css)
	{
		WebElement element = getFirstVisibleElement(fluentWaitForElementsCss(css));
		Select comboBox = new Select(element);
		comboBox.selectByVisibleText(visibleText);
		return this;
	}

	@Override
	public SeleniumCommands comboBoxIndexByCSS(int index, String css)
	{
		WebElement element = fluentWaitForElementCss(css);
		Select comboBox = new Select(element);
		comboBox.selectByIndex(index);
		return this;
	}

	@Override
	public SeleniumCommands comboBoxRandomByCSS(String css)
	{
		WebElement element = fluentWaitForElementCss(css);
		return comboBoxSelectRandom(new Select(element));
	}

	@Override
	public int comboBoxGetDisplayIndexByCSS(String css)
	{
		WebElement element = fluentWaitForElementCss(css);
		String text = comboBoxGetDisplayTextByCSS(css);
		return getComboBoxIndexFromText(text, element);
	}

	@Override
	public String comboBoxGetDisplayTextByCSS(String css)
	{
		WebElement element = fluentWaitForElementCss(css);
		return new Select(element).getFirstSelectedOption().getText();
	}

	@Override
	public List<WebElement> comboBoxGetOptionsByCSS(String css)
	{
		WebElement element = fluentWaitForElementCss(css);
		return new Select(element).getOptions();
	}

	@Override
	public SeleniumCommands typeByCSS(String input, String css)
	{
		WebElement element;
		if (fluentWaitForElementsCss(css).size() > 1)
			element = getFirstVisibleElement(fluentWaitForElementsCss(css));
		else
			element = fluentWaitForElementCss(css);
		fluentWaitForVisibilityOfElement(element);
		element.clear();
		element.sendKeys(input + "\t");
		return this;
	}

	@Override
	public String getElementAttributeByCSS(String attribute, String css)
	{
		WebElement element = fluentWaitForElementCss(css);
		return element.getAttribute(attribute);
	}

	@Override
	public String getTextByCSS(String css)
	{
		WebElement element = fluentWaitForElementCss(css);
		return element.getText();
	}

	@Override
	public SeleniumCommands enterWebFrameByCSS(String css)
	{
		WebElement element = fluentWaitForElementCss(css);
		driver.switchTo().frame(element);
		return this;
	}


	/*===================================================================
	 *
	 * ByID functions
	 *
	 *===================================================================*/

	@Override
	public SeleniumCommands clickElementByID(String id)
	{
		WebElement element = fluentWaitForElementId(id);
		fluentWaitForClickableId(id);
		new Actions(driver).moveToElement(element).perform();
		element.click();
		return this;
	}

	@Override
	public SeleniumCommands clickRandomElementByID(String id)
	{
		waitForID(id);
		List<WebElement> elements = getAllVisibleElements(fluentWaitForElementsId(id));
		return clickRandom(elements);
	}

	@Override
	public SeleniumCommands checkBoxByID(boolean selected, String id)
	{
		WebElement element = fluentWaitForElementId(id);
		if (selected != element.isSelected())
		{
			clickElementByWebElement(element);
		}
		return this;
	}

	@Override
	public WebElement getElementByID(String id)
	{
		return fluentWaitForElementId(id);
	}

	@Override
	public int getElementCountByID(String id)
	{
		List<WebElement> element = fluentWaitForElementsId(id);
		return (element == null) ? 0 : element.size();
	}

	@Override
	public SeleniumCommands typeByID(String input, final String id)
	{
		WebElement element;
		if (fluentWaitForElementsId(id).size() > 1)
			element = getFirstVisibleElement(fluentWaitForElementsId(id));
		else
			element = fluentWaitForElementId(id);
		fluentWaitForVisibilityOfElement(element);
		element.clear();
		element.sendKeys(input + "\t");
		return this;
	}

	@Override
	public SeleniumCommands comboBoxVisibleTextByID(String visibleText, String id)
	{
		WebElement element = getFirstVisibleElement(fluentWaitForElementsId(id));
		Select comboBox = new Select(element);
		comboBox.selectByVisibleText(visibleText);
		return this;
	}

	@Override
	public SeleniumCommands comboBoxIndexByID(int index, String id)
	{
		WebElement element = fluentWaitForElementId(id);
		Select comboBox = new Select(element);
		comboBox.selectByIndex(index);
		return this;
	}

	@Override
	public SeleniumCommands comboBoxRandomByID(String id)
	{
		WebElement element = fluentWaitForElementId(id);
		return comboBoxSelectRandom(new Select(element));
	}

	@Override
	public int comboBoxGetDisplayIndexByID(String id)
	{
		WebElement element = fluentWaitForElementId(id);
		String text = comboBoxGetDisplayTextByID(id);
		return getComboBoxIndexFromText(text, element);
	}

	@Override
	public String comboBoxGetDisplayTextByID(String id)
	{
		WebElement element = fluentWaitForElementId(id);
		return new Select(element).getFirstSelectedOption().getText();
	}

	@Override
	public List<WebElement> comboBoxGetOptionsByID(String id)
	{
		WebElement element = fluentWaitForElementId(id);
		return new Select(element).getOptions();
	}

	@Override
	public SeleniumCommands waitForID(String id)
	{
		fluentWaitForElementId(id);
		return this;
	}

	@Override
	public List<WebElement> getElementsByID(String id)
	{
		return fluentWaitForElementsId(id);
	}

	@Override
	public String getElementAttributeByID(String attribute, String id)
	{
		WebElement element = fluentWaitForElementId(id);
		return element.getAttribute(attribute);
	}

	@Override
	public String getTextByID(String id)
	{
		WebElement element = fluentWaitForElementId(id);
		return element.getText();
	}

	@Override
	public SeleniumCommands enterWebFrameByID(String id)
	{
		WebElement element = fluentWaitForElementId(id);
		driver.switchTo().frame(element);
		return this;
	}


	/*===================================================================
	 *
	 * ByXPath functions
	 *
	 *===================================================================*/

	@Override
	public SeleniumCommands clickElementByXPath(String xpath)
	{
		WebElement element = fluentWaitForElementXPath(xpath);
		fluentWaitForClickableXPath(xpath);
		new Actions(driver).moveToElement(element).perform();
		element.click();
		return this;
	}

	@Override
	public SeleniumCommands clickRandomElementByXPath(String xpath)
	{
		waitForXPath(xpath);
		List<WebElement> elements = getAllVisibleElements(fluentWaitForElementsXPath(xpath));
		return clickRandom(elements);
	}

	@Override
	public SeleniumCommands checkBoxByXPath(boolean selected, String xpath)
	{
		WebElement element = fluentWaitForElementXPath(xpath);
		if (selected != element.isSelected())
		{
			clickElementByWebElement(element);
		}
		return this;
	}

	@Override
	public WebElement getElementByXPath(String xpath)
	{
		return fluentWaitForElementXPath(xpath);
	}

	@Override
	public int getElementCountByXPath(String xpath)
	{
		List<WebElement> element = fluentWaitForElementsXPath(xpath);
		return (element == null) ? 0 : element.size();
	}

	@Override
	public SeleniumCommands typeByXPath(String input, String xpath)
	{
		WebElement element;
		if (fluentWaitForElementsXPath(xpath).size() > 1)
			element = getFirstVisibleElement(fluentWaitForElementsXPath(xpath));
		else
			element = fluentWaitForElementXPath(xpath);
		fluentWaitForVisibilityOfElement(element);
		element.clear();
		element.sendKeys(input + "\t");
		return this;
	}

	@Override
	public SeleniumCommands comboBoxVisibleTextByXPath(String visibleText, String xpath)
	{
		WebElement element = getFirstVisibleElement(fluentWaitForElementsXPath(xpath));
		Select comboBox = new Select(element);
		comboBox.selectByVisibleText(visibleText);
		return this;
	}

	@Override
	public SeleniumCommands comboBoxIndexByXPath(int index, String xpath)
	{
		WebElement element = fluentWaitForElementXPath(xpath);
		Select comboBox = new Select(element);
		comboBox.selectByIndex(index);
		return this;
	}

	@Override
	public SeleniumCommands comboBoxRandomByXPath(String xpath)
	{
		WebElement element = fluentWaitForElementXPath(xpath);
		return comboBoxSelectRandom(new Select(element));
	}

	@Override
	public int comboBoxGetDisplayIndexByXPath(String xpath)
	{
		WebElement element = fluentWaitForElementXPath(xpath);
		String text = comboBoxGetDisplayTextByXPath(xpath);
		return getComboBoxIndexFromText(text, element);
	}

	@Override
	public String comboBoxGetDisplayTextByXPath(String xpath)
	{
		WebElement element = fluentWaitForElementXPath(xpath);
		return new Select(element).getFirstSelectedOption().getText();
	}

	@Override
	public List<WebElement> comboBoxGetOptionsByXPath(String xpath)
	{
		WebElement element = fluentWaitForElementXPath(xpath);
		return new Select(element).getOptions();
	}

	@Override
	public SeleniumCommands waitForXPath(String xpath)
	{
		fluentWaitForElementXPath(xpath);
		return this;
	}

	@Override
	public List<WebElement> getElementsByXPath(String xpath)
	{
		return fluentWaitForElementsXPath(xpath);
	}

	@Override
	public String getElementAttributeByXPath(String attribute, String xpath)
	{
		WebElement element = fluentWaitForElementXPath(xpath);
		return element.getAttribute(attribute);
	}

	@Override
	public String getTextByXPath(String xpath)
	{
		WebElement element = fluentWaitForElementXPath(xpath);
		return element.getText();
	}

	@Override
	public SeleniumCommands enterWebFrameByXPath(String xpath)
	{
		WebElement element = fluentWaitForElementXPath(xpath);
		driver.switchTo().frame(element);
		return this;
	}


	/*===================================================================
	 *
	 * ByWebElement functions
	 *
	 *===================================================================*/

	@Override
	public SeleniumCommands clickElementByWebElement(WebElement element)
	{
		fluentWaitForVisibilityOfElement(element);
		new Actions(driver).moveToElement(element).perform();
		element.click();
		return this;
	}

	@Override
	public SeleniumCommands clickRandomElementByWebElement(List<WebElement> elements)
	{
		return clickRandom(elements);
	}

	@Override
	public SeleniumCommands checkBoxByWebElement(boolean selected, WebElement element)
	{
		if (selected != element.isSelected())
		{
			clickElementByWebElement(element);
		}
		return this;
	}

	@Override
	public SeleniumCommands typeByWebElement(String input, WebElement element)
	{
		fluentWaitForVisibilityOfElement(element);
		element.clear();
		element.sendKeys(input + "\t");
		return this;
	}

	@Override
	public SeleniumCommands comboBoxVisibleTextByWebElement(String visibleText, WebElement element)
	{
		Select comboBox = new Select(element);
		comboBox.selectByVisibleText(visibleText);
		return this;
	}

	@Override
	public SeleniumCommands comboBoxRandomByWebElement(WebElement element)
	{
		return comboBoxSelectRandom(new Select(element));
	}

	@Override
	public SeleniumCommands comboBoxIndexByWebElement(int index, WebElement element)
	{
		Select comboBox = new Select(element);
		comboBox.selectByIndex(index);
		return this;
	}

	@Override
	public String comboBoxGetDisplayTextByWebElement(WebElement element)
	{
		return new Select(element).getFirstSelectedOption().getText();
	}

	@Override
	public int comboBoxGetDisplayIndexByWebElement(WebElement element)
	{
		String text = comboBoxGetDisplayTextByWebElement(element);
		return getComboBoxIndexFromText(text, element);
	}

	@Override
	public List<WebElement> comboBoxGetOptionsByWebElement(WebElement element)
	{
		return new Select(element).getOptions();
	}

	@Override
	public SeleniumCommands waitForWebElement(WebElement element)
	{
		fluentWaitForElementXPath(getElementXPath(element));
		return this;
	}

	@Override
	public List<WebElement> getElementsByWebElement(WebElement element)
	{
		ArrayList<WebElement> elementList = new ArrayList<WebElement>();
		elementList.add(element);
		return elementList;
	}

	@Override
	public WebElement getElementByWebElement(WebElement element)
	{
		return element;
	}

	@Override
	public int getElementCountByWebElement(WebElement element)
	{
		return 1;
	}

	@Override
	public String getElementAttributeByWebElement(String attribute, WebElement element)
	{
		return element.getAttribute(attribute);
	}

	@Override
	public String getTextByWebElement(WebElement element)
	{
		return element.getText();
	}

	@Override
	public SeleniumCommands enterWebFrameByWebElement(WebElement element)
	{
		driver.switchTo().frame(element);
		return this;
	}


	/*===================================================================
	 *
	 * Private Class functions
	 *
	 *===================================================================*/

	private Wait<WebDriver> Wait()
	{
		return new FluentWait<WebDriver>(driver)
				.withMessage(lastCommand + " on " + currentUrl)
				.withTimeout(waitForElement, TimeUnit.SECONDS)
				.pollingEvery(pollingForElement, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
	}

	private WebElement fluentWaitForElementXPath(final String xpath)
	{
		return Wait().until(new Function<WebDriver, WebElement>()
		{
			@Override
			public WebElement apply(WebDriver d)
			{
				return d.findElement(By.xpath(xpath));
			}
		});
	}

	private void fluentWaitForClickableXPath(final String xpath)
	{
		Wait().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}

	private List<WebElement> fluentWaitForElementsXPath(final String xpath)
	{
		return Wait().until(new Function<WebDriver, List<WebElement>>()
		{
			@Override
			public List<WebElement> apply(WebDriver d)
			{
				return d.findElements(By.xpath(xpath));
			}
		});
	}

	private WebElement fluentWaitForElementCss(final String css)
	{
		return Wait().until(new Function<WebDriver, WebElement>()
		{
			@Override
			public WebElement apply(WebDriver d)
			{
				return d.findElement(By.cssSelector(css));
			}
		});
	}

	private List<WebElement> fluentWaitForElementsCss(final String css)
	{
		return Wait().until(new Function<WebDriver, List<WebElement>>()
		{
			@Override
			public List<WebElement> apply(WebDriver d)
			{
				return d.findElements(By.cssSelector(css));
			}
		});
	}

	private void fluentWaitForClickableCss(final String css)
	{
		Wait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)));
	}

	private WebElement fluentWaitForElementId(final String id)
	{
		return Wait().until(new Function<WebDriver, WebElement>()
		{
			@Override
			public WebElement apply(WebDriver d)
			{
				return d.findElement(By.id(id));
			}
		});
	}

	private List<WebElement> fluentWaitForElementsId(final String id)
	{
		return Wait().until(new Function<WebDriver, List<WebElement>>()
		{
			@Override
			public List<WebElement> apply(WebDriver d)
			{
				return d.findElements(By.id(id));
			}
		});
	}

	private void fluentWaitForClickableId(final String id)
	{
		Wait().until(ExpectedConditions.elementToBeClickable(By.id(id)));
	}

	private WebElement getFirstVisibleElement(List<WebElement> elements)
	{
		for (WebElement element : elements)
		{
			if (element.isDisplayed())
			{
				return element;
			}
		}

		throw new ElementNotVisibleException("Element is not currently visible and so may not be interacted with");
	}

	private List<WebElement> getAllVisibleElements(List<WebElement> elements)
	{
		List<WebElement> visibleElements = new ArrayList<WebElement>();

		for (WebElement element : elements)
		{
			if (element.isDisplayed())
			{
				visibleElements.add(element);
			}
		}

		if (visibleElements.isEmpty())
		{
			throw new ElementNotVisibleException("Element is not currently visible and so may not be interacted with");
		}

		return visibleElements;
	}

	private WebElement fluentWaitForVisibilityOfElement(WebElement element)
	{
		return Wait().until(ExpectedConditions.visibilityOf(element));
	}

	private synchronized void setLastCommand(String lastCommand)
	{
		this.lastCommand = lastCommand;
		logger.debug(lastCommand);
	}

	private synchronized void setCurrentUrl()
	{
		currentUrl = driver.getCurrentUrl();
	}

	private SeleniumCommands clickRandom(List<WebElement> elements)
	{
		WebElement clickElement;
		if (elements.size() == 1)
		{
			clickElement = elements.get(0);
		}
		else
		{
			int size = elements.size();
			Random rand = new Random();
			int selection = rand.nextInt(size);
			clickElement = elements.get(selection);
		}
		fluentWaitForVisibilityOfElement(clickElement);
		new Actions(driver).moveToElement(clickElement).perform();
		clickElement.click();
		return this;
	}

	private SeleniumCommands comboBoxSelectRandom(Select comboBox)
	{
		int size = comboBox.getOptions().size();
		Random rand = new Random();
		comboBox.selectByIndex(rand.nextInt(size));
		return this;
	}

	private void validateURL(String url)
	{
		if (url == null) throw new NullPointerException("URL must not be Null");
		UrlValidator validate = new UrlValidator(new String[]{"http", "https"});
		if (!validate.isValid(url)) {
			close();
			throw new IllegalArgumentException("Url '" + url + "' is invalid.");
		}
	}

	private int getComboBoxIndexFromText(String option, WebElement select)
	{
		List<WebElement> options = new Select(select).getOptions();
		for (int i=0; i < options.size(); i++)
		{
			if (options.get(i).getText().equals(option))
			{
				return i;
			}
		}
		//Exception should never be thrown
		throw new WebDriverException(
				"Unable to find index of '" + option + "' in WebElement: " + getElementXPath(select));
	}
}