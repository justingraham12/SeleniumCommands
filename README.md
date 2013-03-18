SeleniumCommands
================
#####*An add-on to Selenium 2 to make test automation quicker and easier.* 

####Requirements
You must have 'selenium' and 'log4j2' on your classpath for this project to work correctly.  
1. [Selenium] (http://code.google.com/p/selenium/downloads/list)  
2. [log4j2] (http://logging.apache.org/log4j/2.x/download.html)


##Why did I start this project?

1. Issues around handling elements when AJAX is present
2. Issues with pathing to elements that have a duplicate hidden element
3. Ease of use setting up test cases
4. Hastle of writing the same error handling for every Selenium project
5. Ability to write code that accepts any webdriver instance and results in the same behavior


####Issues with Wait when AJAX is present

######No Wait
```java
WebElement element = driver.findElement(By.loc(""));
```
Causes Selenium to throw false positive NoSuchElementExceptions. Selenium is unable to follow JavaScript thus if we have
a JS command create DOM elements after our page loads, Selenium will already have looked for the element and thrown an 
Exception before our JS completed and we end up with errors where the page has completely loaded but the elements we 
request are not yet available.  

######Implicit Wait
```java
driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
WebElement element = driver.findElement(By.loc(""));
```
In theory this should fix any issues of calling WebElements without a wait. But unforchantly this isn't failsafe and not
even cross browser compliant. (NOTE: Last time I tested implicit waits was in 2.14 so this could have been fixed). When
setting up implicit waits the behavior would change between browsers. 

######Explicit Wait
```java
/* Explicit Wait */
WebElement element = (new WebDriverWait(driver, 10))
  .until(new ExpectedCondition<WebElement>(){
    @Override
    public WebElement apply(WebDriver d) {
      return d.findElement(By.loc(""));
    }
  });
```
Explicit waits work really well up until the point we want to start modifying the wait behavior. We can change the 
conditions and how long we wait, but things like polling times, initial wait times, errors to ignore, and custom
error messaging are too limiting to our needs.

######Fluent Wait
```java
Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
  .withTimeout(20, TimeUnit.SECONDS)
  .pollingEvery(2, TimeUnit.SECONDS)
  .ignoring(NoSuchElementException.class);
  
WebElement element = wait.until(new Function<WebDriver, WebElement>(){
  public WebElement apply(WebDriver d) {
    return d.findElement(By.loc(""));
  }
});
```
Fluent waits are the most flexible wait I've found in Selenium. All calls through a SeleniumCommands object
first run through a fluent wait. This reduces the problems you'll run into when testing pages with AJAX content.
Initially the timeout is set to 15 seconds and polls every 1. After 15 seconds of not finding an element we assume 
the element is not going to load and throw the NoSuchElementException. You of course have full control of the 
timeout and polling by calling the following.

```java
SeleniumCommands commands = new Commands(new FirefoxDriver())
  .SetFluentWaitTime(20, SECONDS, 500, MILLISECONDS);
```
This sets the Timeout value to 20 seconds and the polling time to 500 milliseconds.

##Current functions and usage

Locators: ID, CSS, XPath, WebElement

Current Functions:  
```java 
  /** Opens the browser to the supplied url */
  Open(String url)
  
  /** Closes the browser and the controlling WebDriver istance */
  Close()
  
  /** Opens a new window and navigates to the input url */
  OpenNewWindow(String url)
  
  /** Switches control to a popup/new window */
  SwitchToWindow()
  
  /** Switches control back to the original/parent window */ 
  SwitchToParent()
  
  /** Closes the current popup/new window */
  CloseWindow()
  
  /** Sets the time to wait for WebElements and how often to look for them */
  SetFluentWaitTime(int wait, TimeUnit waitUnit, int poll, TimeUnit pollUnit) 
  
  /** Clicks the WebElement at the locator */
  Click(Using locator)
  
  /** If the locator finds more then 1 visible/clickable WebElement we choose a random to click */
  ClickRandom(Using locator)
  
  /** Enters the inputText into the WebElement at the locator */
  Type(String inputText, Using locator)
  
  /** Selects the visibleText from the <select> WebElement at the locator */
  ComboBoxByText(String visibleText, Using locator)
  
  /** Chooses a random option from the <select> WebElement at the locator */
  ComboBoxRandom(Using locator)
  
  /** Changes the drivers control the the WebFrame at the locator */
  EnterWebFrame(Using locator)
  
  /** Changes the drivers control to the WebFrame directly above the current WebFrame */
  PopCurrentWebFrame()
  
  /** Changes the drivers control to the default/top most WebFrame */
  PopAllWebFrames()
  
  /** Wait's using the FluentWaitTime for the WebElement at the locator to exist */
  WaitForElement(Using locator)
  
  /** Pauses all driver functions for the time supplied */
  WaitForTime(long time, TimeUnit unit)
  
  /** Returns the WebElement found at the locator */
  GetElement(Using locator)
  
  /** Returns a list of WebElement's found using the locator */
  GetElements(Using locator)
  
  /** Returns the number of elements the locator found */
  GetElementCount(Using locator)
  
  /** Returns the attribute value of the WebElement at the locator */
  GetElementAttribute(String Attribute, Using locator)
  
  /** Returns the unique XPath of the WebElement */
  GetElementXPath(WebElement element)
```


##SeleniumCommands vs Default Selenium2 (WebDriver):

####Selenium2 (WebDriver) using TestNG

```java

/**
 * This class shows how we could set up a very simple unit test using the default Selenium WebDriver
 */
@Test
public class TestWithoutSeleniumCommands
{
  /* Hold the driver controlling the browser */
  private WebDriver driver;
  
  /* Assign the Firefox browser to our driver */
  @BeforeClass
  public void startWebDriver()
  {
    driver = new FirefoxDriver());
  }

  /* 
   * This is where the bulk of the changes are made. On every line we are required to 
   * make a new call to the driver and to find the WebElement we would like to interact with.
   * Notice here we are not including any wait's as described above. If any of the 
   * WebElements are populated using ajax the test will fail with a NoSuchElementException.
   */
  @Test (description="Simple test using Selenium WebDriver and TestNG")
  public void testSeleniumWebDriver()
  {
    driver.get("http://www.github.com");
    driver.findElement(By.cssSelector(".search>a")).click();
    driver.findElement(By.xpath("//*[@name='q']")).sendKeys("SeleniumCommands");
    driver.findElement(By.xpath("//*[@type='submit']")).click();
    driver.findElement(By.cssSelector("span.mega-icon.mega-icon-public-repo + a")).click();

    //When we hit the final page we would like the Driver to pause for 5 seconds before closing
    long waitFor = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5);
    while (System.currentTimeMillis() < waitFor){}
  }
  
  /*
   * If the test passes or fails we always want to quit the driver.  
   */
  @AfterClass
  public void closeSeleniumCommands()
  {
    driver.quit();
  }
}
```

####SeleniumCommands using TestNG

```java

/**
 * Now lets build the same unit test but this time using the SeleniumCommands project 
 */
public class TestWithSeleniumCommands
{
  /* Our SeleniumCommands object that will run our tests */
  private SeleniumCommands commands;
  
  /* Give SeleniumCommands our Firefox WebDriver */
  @BeforeClass
  public void startSeleniumCommands()
  {
    commands = new Commands(new FirefoxDriver());
  }

  /*
   * The first thing you should notice is the simplictiy in which we call our commands.
   * We have no need of calling the WebDriver because we already gave it to SeleniumCommands.
   * We also do not need to findElements before calling our command. We need to pass the correct
   * locator to the element and use the correct command... thats it! SeleniumCommands will wait for
   * the element to appear on the page and perform the correct command all on it's own.
   */
  @Test (description="Simple test using SeleniumCommands and TestNG")
  public void testSeleniumCommands()
  {
    commands
      .Open("http://www.github.com")
      .Click(Using.CSS(".search>a")) 
      .Type("SeleniumCommands", Using.XPath("//*[@name='q']"))
      .Click(Using.XPath("//*[@type='submit']"))
      .Click(Using.CSS("span.mega-icon.mega-icon-public-repo + a"))
      .WaitForTime(5, TimeUnit.SECONDS);
  }
  
  /* Close selenium once the test completes */
  @AfterClass
  public void closeSeleniumCommands()
  {
    commands.Close();
  }
}
```

####Results of running both unit tests 5x's:

<table>
  <tr>
    <th>Pass</th>
    <th>SeleniumCommands</th>
    <th>Default Selenium</th>
  </tr>
  <tr>
    <td>1</td>
    <td>Pass</td>
    <td>Fail (NoSuchElementException: Unable to locate element: "//*[@name='q']")</td>
  </tr>
  <tr>
    <td>2</td>
    <td>Pass</td>
    <td>Fail (NoSuchElementException: Unable to locate element: "//*[@name='q']")</td>
  </tr>
  <tr>
    <td>3</td>
    <td>Pass</td>
    <td>Pass</td>
  </tr>
  <tr>
    <td>4</td>
    <td>Pass</td>
    <td>Fail (NoSuchElementException: Unable to locate element: "//*[@name='q']")</td>
  </tr>
  <tr>
    <td>5</td>
    <td>Pass</td>
    <td>Pass</td>
  </tr>
  <tr>
    <th>Results</th>
    <td>100% Pass</td>
    <td>40% Pass, 60% Fail</td>
  </tr>
</table>
