SeleniumCommands
================

An add-on to Selenium 2 to make test automation quicker and easier. 

I've been working with Selenium for some time now, and no matter which project I'm on I find myself 
rewriting the same common functions. I'd say 90% of the webpages I test have some sort of AJAX or Javascript 
loading elements milliseconds after the page loads. If you call the following:

WebElement element = driver.findElement(By.loc(""));

it causes Selenium to throw the classic NoSuchElementException which inturn fails your test cases. 
The solution to this is to add either an explicit or implicit wait before requesting an element. 
In my experience implicit waits are unreliable not to mention not supported by all WebDriver instances 
(NOTE: Last time I tested implicit waits was around 2.14 so this could very well be fixed). To set up an 
implicit wait you'd do something like this:

driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
WebElement element = driver.findElement(By.loc(""));

Once again it's been awhile since I've used this, but the last time I did I had my implicit wait set to 30 seconds
and when my test environment was running slow the page would load and instantly I'd receive a NoSuchElementException.
Obviously the inconsistencies were very frustrating. So I moved onto the next recommended option of explicit waits.
The seleniumhq.org recommends you set up your explicit waits like this:

WebElement element = (new WebDriverWait(driver, 10))
  .until(new ExpectedCondition<WebElement>(){
    @Override
    public WebElement apply(WebDriver d) {
      return d.findElement(By.loc(""));
    }
  });
