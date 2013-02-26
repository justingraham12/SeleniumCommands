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
  .Open                 (String url)
  .Close                ()
  .SetFluentWaitTime    (int wait, TimeUnit waitUnit, int poll, TimeUnit pollUnit) 
  .Click                (Using locator)
  .ClickRandom          (Using locator)
  .Type                 (String inputText, Using locator)
  .ComboBoxByText       (String visibleText, Using locator)
  .ComboBoxRandom       (Using locator)
  .WaitForElement       (Using locator)
  .WaitForTime          (long time, TimeUnit unit)
  .GetElement           (Using locator)
  .GetElements          (Using locator)
  .GetElementCount      (Using locator)
  .GetElementAttribute  (String Attribute, Using locator)
  .GetElementXPath      (WebElement element)
```


In use:

```java
/* SeleniumCommands */
SeleniumCommands commands = new Commands(new FirefoxDriver())
  .Open("http://www.github.com")
  .Click(Using.CSS(".search>a"))
  .Type("SeleniumCommands", Using.XPath("//*[@name='q']"))
  .Click(Using.XPath("//*[@type='submit']"))
  .Click(Using.XPath("//a[contains(text(), 'SeleniumCommands')]"))
  .WaitForTime(5, TimeUnit.SECONDS)
  .Close();
```
