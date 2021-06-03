package main;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import database.repositories.FullStudentDataRepository;
import database.repositories.StudentRepository;
import database.repositories.StudentStatementRepository;
import database.repositories.UniversityRepository;
import graphLogic.*;
import models.DataForGraphOperations;
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

//        Parse student and studentStatement data
//        Parser parser = new Parser();
//        parser.parseStudentsFromDirectionPageByDirectionIds();
//        parser.parseStudentStatementsFromStudentInfoPage();


//        Finding all cycles in graph
        FindAllCycles findAllCycles = new FindAllCycles();

//        @param display graph in JFrame
        findAllCycles.findAllCycles(true);

    }
}
