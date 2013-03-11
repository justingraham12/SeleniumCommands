package org.qa.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.util.Set;

/**
 * Creates and Handles a New window  *
 */
public class WindowBuilder
{

	private WebDriver driver;
	private String handle;
	private String name;
	private String parentHandle;
	private String windowUrl;
	private static int instanceCount = 0;

	/**
	 * Creates a new window for given web driver
	 *
	 * @param parent WebDriver instance
	 * @param url    Initial url to load
	 */
	public WindowBuilder(WebDriver parent, String url)
	{
		this.driver = parent;
		parentHandle = parent.getWindowHandle();
		name = createUniqueName();
		handle = createWindow(url);
		//Switch to that window and load the url to waitForElement
		switchToWindow().get(url);
		windowUrl = parent.getCurrentUrl();
	}

	/**
	 * Gets our current window's handle
	 *
	 * @return A string representation of the handle
	 */
	public String getWindowHandle()
	{
		return handle;
	}

	/**
	 * Gets the parent window's handle
	 *
	 * @return A string representation of the handle
	 */
	public String getParentHandle()
	{
		return parentHandle;
	}

	public String getWindowUrl()
	{
		return windowUrl;
	}

	/**
	 * Closes the current window
	 */
	public void close()
	{
		switchToWindow().close();
		handle = "";
		//Switch back to the parent window
		driver.switchTo().window(parentHandle);
	}

	private static String createUniqueName()
	{
		return "Web_Window_" + instanceCount++;
	}

	public WebDriver switchToWindow()
	{
		checkForClosed();
		driver.switchTo().window(handle);
		windowUrl = driver.getCurrentUrl();
		return driver;
	}

	public WebDriver switchToParent()
	{
		checkForClosed();
		return driver.switchTo().window(parentHandle);
	}

	private String createWindow(String url)
	{

		//Record old handles
		Set<String> oldHandles = driver.getWindowHandles();
		parentHandle = driver.getWindowHandle();

		//Inject an anchor element
		((JavascriptExecutor) driver).
				executeScript(
						injectAnchorTag(name, url)
				);

		//Click on the anchor element
		driver.findElement(By.id(name)).click();

		handle = getNewHandle(oldHandles);

		return handle;
	}

	private String getNewHandle(Set<String> oldHandles)
	{

		Set<String> newHandles = driver.getWindowHandles();
		newHandles.removeAll(oldHandles);

		//Find the new window
		for (String handle : newHandles)
			return handle;


		return null;
	}

	/**
	 * Checks to see if the window is closed.
	 */
	private void checkForClosed()
	{
		if (handle == null || handle.equals(""))
			throw new WebDriverException("Web Window closed or not initialized");
	}

	private String injectAnchorTag(String id, String url)
	{
		return String.format("var anchorTag = document.createElement('a'); " +
				"anchorTag.appendChild(document.createTextNode('nwh'));" +
				"anchorTag.setAttribute('id', '%s');" +
				"anchorTag.setAttribute('href', '%s');" +
				"anchorTag.setAttribute('target', '_blank');" +
				"anchorTag.setAttribute('style', 'display:block;');" +
				"document.getElementsByTagName('body')[0].appendChild(anchorTag);",
				id, url
		);
	}
}