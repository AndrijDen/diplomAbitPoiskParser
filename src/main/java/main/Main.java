package main;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import database.repositories.StudentRepository;
import database.repositories.StudentStatementRepository;
import database.repositories.UniversityRepository;
import graphLogic.GetStudentTransitionDataFromGraph;
import graphLogic.RelationshipEdge;
import models.StudentStatement;
import models.University;
import org.apache.commons.logging.LogFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedMultigraph;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import parser.Parser;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("hello world");
        //        WORKING EXAMPLE
//        TestSelect ts = new TestSelect();
//        List<University> qwe = ts.all();
//

//        UNIVERSITY SELECT FROM DB TEST
//        UniversityRepository universityRepository = new UniversityRepository();
//        List<University> qwe = universityRepository.getAll();
//        System.out.println("qwe" + qwe);

//        DIRECTION SELECT FROM DB TEST
//        DirectionRepository directionRepository = new DirectionRepository();
//        List<Direction> qwe = directionRepository.getAll();

//        student Test insert into
//        StudentRepository studentRepository = new StudentRepository();
//        Students qwe = new Students( 0, "qwe", "qwe", 1, 1, 675589);
//        System.out.println("qweqwe" + studentsRepository.insert(qwe));
//        System.out.println("get all" + studentRepository.getAll());

//        Student Parser test
//        Parser parser = new Parser();
//        parser.parseStudentsFromDirectionPageByDirectionIds();

//        Student Statement Parser test
//        Parser parser = new Parser();
//        parser.parseStudentStatementsFromStudentInfoPage();

//        Try HTMLUNIT
//        final WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
//        webClient.getOptions().setJavaScriptEnabled(true);
//        webClient.getOptions().setRedirectEnabled(true);
//        webClient.getOptions().setThrowExceptionOnScriptError(false);
//        webClient.getOptions().setCssEnabled(false);
//        webClient.getOptions().setUseInsecureSSL(true);
//        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//        webClient.getCookieManager().setCookiesEnabled(true);
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//        HtmlPage myPage = ((HtmlPage) webClient.getPage("https://abit-poisk.org.ua/#search-%D0%9F%D0%BE%D0%BB%D1%96%D1%89%D1%83%D0%BA+%D0%9E.+%D0%92.+2020+11.1"));
//        Thread.sleep(2000);
//        webClient.waitForBackgroundJavaScript(1000 * 1000);
//        Document doc = Jsoup.parse(myPage.asXml());
//        System.out.println("qweqwe" + doc);
//        String theContent = myPage.asXml();
//        System.out.println(theContent);

//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Desktop\\diplom\\Libraries\\ChromeDriver\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();

//        Try connect PhantomJSDriver
//        DesiredCapabilities DesireCaps = new DesiredCapabilities();
//        DesireCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "C://phantomjs.exe");
//        WebDriver driver = new PhantomJSDriver(DesireCaps);

//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Desktop\\diplom\\Libraries\\ChromeDriver\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();


//        driver.get("https://abit-poisk.org.ua/#search-%D0%9F%D0%BE%D0%BB%D1%96%D1%89%D1%83%D0%BA+%D0%9E.+%D0%92.+2020+11.1");
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        String pageSource = driver.getPageSource();
//        System.out.println("pageSource" + pageSource);
//
//        Document doc = Jsoup.parse(pageSource);
//        System.out.println("qweqwe" + doc);

        //        Student Parser test
//        Parser parser = new Parser();
//        parser.parseStudentStatementsFromStudentInfoPage();

//        System.out.println("qweqwe" + parser.readStudentStatementsTestPage());


//        Parse student and studentStatement data
//        Parser parser = new Parser();
//        parser.parseStudentsFromDirectionPageByDirectionIds();
//        parser.parseStudentStatementsFromStudentInfoPage();

//        TestGraph tg = new TestGraph();
//        tg.testJgraphtLib();

        StudentRepository stud = new StudentRepository();
        System.out.println("+++++++" + stud.getByGrade(176.75));

//        GetStudentTransitionDataFromGraph ggr = new GetStudentTransitionDataFromGraph();
//        ggr.getStudentTransitionData(testDataFindingStrongConnectivitynoLable());
    }

    public static Graph testDataFindingStrongConnectivitynoLable() {
        DirectedMultigraph<String, RelationshipEdge> directedGraph =
                new DirectedMultigraph<String, RelationshipEdge>(RelationshipEdge.class);
        // Test 1

        directedGraph.addVertex("0");
        directedGraph.addVertex("1");
        directedGraph.addVertex("2");
        directedGraph.addVertex("3");
        directedGraph.addVertex("4");

        directedGraph.addEdge("0", "1", new RelationshipEdge("0q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("1q"));
        directedGraph.addEdge("0", "1", new RelationshipEdge("2q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("3q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("4q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("4q1"));
        directedGraph.addEdge("1", "2", new RelationshipEdge("55"));
        directedGraph.addEdge("2", "3", new RelationshipEdge("r1"));
        directedGraph.addEdge("3", "4", new RelationshipEdge("r2"));

        System.out.println("directedGraph" + directedGraph);
        return directedGraph;
    }
}
