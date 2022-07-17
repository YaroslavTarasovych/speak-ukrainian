
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class YaroslavTest {
    private  WebDriver driver;
    public  String Url = "https://speak-ukrainian.org.ua/dev/";

    @BeforeClass
    public void setBeforeClass(){
        System.setProperty("webdriver.chrome.driver","src\\lib\\chromedriver.exe");

    }

    @AfterClass
    public void setAfterClass(){
        driver.quit();
    }
    @BeforeMethod
    public void setBeforeMethod(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(Url);
    }

    @AfterMethod
    public void setAfterMethod(){
        driver.close();
    }


    @Test
    public void loginNegativeTest() throws InterruptedException {
        driver.findElement(By.cssSelector("div.ant-dropdown-trigger.user-profile")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("ul.ant-dropdown-menu.ant-dropdown-menu-root.ant-dropdown-menu-vertical.ant-dropdown-menu-light > li:nth-child(2) ")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("basic_email")).click();
        driver.findElement(By.id("basic_email")).clear();
        driver.findElement(By.id("basic_email")).sendKeys("staryk@gmail.com");
        Thread.sleep(2000);
        driver.findElement(By.id("basic_password")).click();
        driver.findElement(By.id("basic_password")).clear();
        driver.findElement(By.id("basic_password")).sendKeys("password");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("button.ant-btn.ant-btn-default.login-button")).click();
        Thread.sleep(1000);
        WebElement element = driver.findElement(By.cssSelector("div.ant-message-custom-content.ant-message-error > span:nth-child(2)"));
        System.out.println("Message " + element.getText());
        Assert.assertEquals(element.getText(),"Введено невірний пароль або email");
    }

    @Test
    public void loginPositiveTest() throws InterruptedException {
        driver.findElement(By.cssSelector("div.ant-dropdown-trigger.user-profile")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("ul.ant-dropdown-menu.ant-dropdown-menu-root.ant-dropdown-menu-vertical.ant-dropdown-menu-light > li:nth-child(2) ")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("basic_email")).click();
        driver.findElement(By.id("basic_email")).clear();
        driver.findElement(By.id("basic_email")).sendKeys("admin@gmail.com");
        Thread.sleep(2000);
        driver.findElement(By.id("basic_password")).click();
        driver.findElement(By.id("basic_password")).clear();
        driver.findElement(By.id("basic_password")).sendKeys("admin");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("button.ant-btn.ant-btn-default.login-button")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("div.ant-dropdown-trigger.user-profile")).click();
        Thread.sleep(2000);
        List<WebElement> elements = driver.findElements(By.cssSelector("ul.ant-dropdown-menu.ant-dropdown-menu-root.ant-dropdown-menu-vertical.ant-dropdown-menu-light > li"));
        int actualSize = elements.size();
        Assert.assertEquals(actualSize,5);
        if (actualSize == 5) {
            driver.findElement(By.cssSelector("li.ant-dropdown-menu-item.ant-dropdown-menu-item-danger.ant-dropdown-menu-item-only-child")).click();
            System.out.println("Has done");
        }

    }

    @Test
    public void loginGroupsTest() throws InterruptedException {
        driver.findElement(By.cssSelector("ul.ant-menu-overflow.ant-menu.ant-menu-root.ant-menu-horizontal.ant-menu-light.nav-menu > li:nth-child(1) ")).click();
        Thread.sleep(2000);
        List<WebElement> element = driver.findElements(By.cssSelector("div.content-clubs-list.content-clubs-block > div"));
        int count = element.size();
        System.out.println("There are "+count+" elements");
        Assert.assertEquals(element.size(),8);

        int countAllGroup = count;

        List<WebElement> allElementsGroup = driver.findElements(By.cssSelector("ul.ant-pagination.pagination > li"));
        if (allElementsGroup.size() != 0) {
            String name = allElementsGroup.get(allElementsGroup.size() - 2).findElement(By.cssSelector("a")).getText();
            int allPages = Integer.parseInt(name);
            allElementsGroup.get(allElementsGroup.size() - 2).click();
            countAllGroup = (allPages - 1) * count;
            Thread.sleep(2000);
            element = driver.findElements(By.cssSelector("div.content-clubs-list.content-clubs-block > div"));
            countAllGroup += element.size();
        }
        System.out.println("All groups "+countAllGroup);
        Assert.assertEquals(countAllGroup,366);
    }

    @Test
    public void loginSearchTest() throws InterruptedException {
        driver.findElement(By.cssSelector("ul.ant-menu-overflow.ant-menu.ant-menu-root.ant-menu-horizontal.ant-menu-light.nav-menu > li:nth-child(1) ")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("span.anticon.anticon-control.advanced-icon")).click();
        List<WebElement> element = driver.findElements(By.cssSelector("aside.ant-layout-sider.ant-layout-sider-dark.club-list-sider"));
        boolean isPresent = element.size() > 0;
        Assert.assertTrue(isPresent);
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("span.anticon.anticon-control.advanced-icon")).click();
        element = driver.findElements(By.cssSelector("aside.ant-layout-sider.ant-layout-sider-dark.club-list-sider"));
        isPresent = element.size() > 0;
        Assert.assertFalse(isPresent);
    }

}
