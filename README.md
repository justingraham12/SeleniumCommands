SeleniumCommands
================
#####*An add-on to Selenium 2 to make test automation quicker and easier.* 

##Why did I start this project?

1. Issues around handling elements when AJAX is present
2. Issues with pathing to elements that have a duplicate hidden element
3. Ease of use setting up test cases
4. Hastle of writing the same error handling for every project I start using Selenium


####Issues with Wait when AJAX is present

######No Wait
```java
WebElement element = driver.findElement(By.loc(""));
```
Causes Selenium to throw false NoSuchElementExceptions due to elements loading milliseconds after
the page. Selenium is unable to follow the JS let alone anything server side. Thus we end up with errors
where the page has completely loaded (according to Selenium) but our elements are not yet available.  

######Implicit Wait
```java
driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
WebElement element = driver.findElement(By.loc(""));
```
In theory this should fix any issues of calling WebElements without a wait. But unforchantly this isn't failsafe and not
even cross browser compliant. (NOTE: Last time I tested implicit waits was in 2.14 so this could have been fixed). This 
was by far the biggest issue. I could set up an implicit wait for IE but it wouldn't work in FF. 

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
Explicit waits work very well up until the point we want to start modifying the wait behavior. We can change the 
conditions and how long we wait but how often does our wait poll for the element? Or What if we want to wait 5 seconds
before ever looking for the element? The standard Explicit Wait doesn't seem to handle this functionality. 

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
Fluent waits are the most reliable wait time I've found in Selenium. Thus all calls through a SeleniumCommands object
first run through a fluent wait. Initially the timeout is set to 15 seconds and polls every 1. After 15 seconds of not
finding an element we assume the element is not going to load and throw the NoSuchElementException. You have full 
control of the timeout and polling by calling the following.

```java
SeleniumCommands commands = new Commands(new FirefoxDriver())
  .SetFluentWaitTime(20, SECONDS, 500, MILLISECONDS);
```
Which sets the Timeout value to 20 seconds and the polling time to 500 milliseconds.

##Current functions and usage

Search types: ID, CSS, XPath

Functions: Open, Close, Click, Type, ComboBoxText, WaitForElement, GetElement, GetElements, GetElementCount


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
