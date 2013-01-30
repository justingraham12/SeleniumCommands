SeleniumCommands
================
#####*An add-on to Selenium 2 to make test automation quicker and easier.* 

##Why did I start this project?

I've been working with Selenium for some time now, and no matter which project I'm on I find myself 
rewriting the same common functions. I'd say 90% of the webpages I test have some sort of AJAX or Javascript 
loading elements milliseconds after the page loads. If you call the following:

```java
/** No Wait */
WebElement element = driver.findElement(By.loc(""));
```

it causes Selenium to throw the classic NoSuchElementException which inturn fails your test cases. 
The solution to this is to add either an explicit or implicit wait before requesting an element. 
In my experience implicit waits are unreliable not to mention not supported by all WebDriver instances 
(NOTE: Last time I tested implicit waits was around 2.14 so this could very well be fixed). To set up an 
implicit wait you'd do something like this:

```java
/* Implicit Wait */
driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
WebElement element = driver.findElement(By.loc(""));
```

Once again it's been awhile since I've used this, but the last time I did I had my implicit wait set to 30 seconds
and when my test environment was running slow the page would load and instantly I'd receive a NoSuchElementException.
Obviously the inconsistencies were very frustrating. So I moved onto the next recommended option of explicit waits.
The seleniumhq.org recommends you set up your explicit waits like this:

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

Now this example works really well up until we want to start making changes to how long we want our thread to sleep
before searching for our element, or say we want it to only check if the element is available 2 times before throwing
the exception? This is where the FluentWait object comes in very handy:

```java
/* Fluent Wait */
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

This project wraps all calls to driver.findElement() in FluentWaits to stop the random failing of interacting with 
WebElements. I've followed the same structure as Selenium so the setup should be very similiar.

The basic usage looks like this:

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

##Current functions and usage types

Search types: ID, CSS, XPath

Functions: Open, Close, Click, Type, ComboBoxText, WaitForElement, GetElement, GetElements, GetElementCount
