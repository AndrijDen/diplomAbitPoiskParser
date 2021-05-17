package parser;

import com.google.gson.Gson;
import database.repositories.DirectionRepository;
import database.repositories.StudentRepository;
import database.repositories.StudentStatementRepository;
import models.Direction;
import models.Student;
import models.StudentStatement;
import models.ZnoMark;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Parser {
    private CheckStudentByZnoMarksService checkStudentByZnoMarksService = new CheckStudentByZnoMarksService();
    private StudentStatementRepository studentStatementRepository = new StudentStatementRepository();
    private StudentRepository studentsRepository = new StudentRepository();
    private String baseURL = "https://abit-poisk.org.ua";
    private int year = 2020;
    private int id = 0;

    public void parseHtml() throws IOException {
        Document doc = Jsoup.connect(this.baseURL).get();
        System.out.print(doc);
    }

    public void parseStudentsFromDirectionPageByDirectionIds() throws SQLException, IOException {
        String directionBaseUrl = baseURL + "/rate" + year + "/direction/";

        DirectionRepository directionRepository = new DirectionRepository();
        List<Direction> directionList = directionRepository.getAll();

        for (Direction direction: directionList) {
            String directionUrl = directionBaseUrl + direction.getId();
/*        for (int i = 0; i < 1; i++) {
            String directionUrl = directionBaseUrl + directionList.get(i).getId();*/
            Document doc = Jsoup.connect(directionUrl).get();
            Elements studentsTable = doc.select("tr.application-status-9:has([data-header=\"Пріоритет\"]:matches([2-9]))");
            for (Element studentsTableItem : studentsTable) {
                getStudentFromHtmlAndInsertToDb(studentsTableItem, direction.getId());
              /*  getStudentFromHtmlAndInsertToDb(studentsTableItem, directionList.get(i).getId());*/
            }
        }
    }

    private void getStudentFromHtmlAndInsertToDb(Element studentHtml, int directionId) throws SQLException {
        Elements marksBlock = studentHtml.select("tr > td:nth-child(6) > div > ul");
        double averageSchoolMark  = Double.parseDouble(marksBlock.select("ul > li:last-child > strong").text());
        String name = studentHtml.select("tr > td:nth-child(2) a").text();
        String searchLink = studentHtml.select("tr > td:nth-child(2) a").attr("href") + "+" + averageSchoolMark;
        int priority = Integer.parseInt(studentHtml.select("tr > td:nth-child(3)").text());
        double grade = Double.parseDouble(studentHtml.select("tr > td:nth-child(4)").text());

        Student student = new Student(0, name, searchLink, priority, grade, directionId, averageSchoolMark, getAllZnoMarks(marksBlock));
        studentsRepository.insert(student);
    }

    private ZnoMark[] getAllZnoMarks(Elements marksBlock) {
        ArrayList<ZnoMark> znoMarks = new ArrayList<ZnoMark>();
        for (Element markItem : marksBlock.select("li")) {
            if (znoMarks.size() < 3) {
                String markName = getMarkName(markItem.text());
                double markValue = Double.parseDouble(markItem.select("strong").text());
                znoMarks.add(new ZnoMark(markName, markValue));
            }
        }
        return znoMarks.toArray(new ZnoMark[0]);
    }

    private String getMarkName(String str) {
        if (str.contains(" (ЗНО)")) {
            return str.substring(0, str.indexOf(" (ЗНО)"));
        }
        return str.substring(0, str.indexOf(":"));
    }

    public void parseStudentStatementsFromStudentInfoPage() throws SQLException {
        StudentRepository studentsRepository = new StudentRepository();
        List<Student> studentsList = studentsRepository.getAll();
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Desktop\\diplom\\Libraries\\ChromeDriver\\chromedriver.exe");

        for (Student student: studentsList) {
          String studentInfoUrl = baseURL + student.getSearchLink();
          WebDriver driver = new ChromeDriver();
          driver.get(studentInfoUrl);
          WebDriverWait wait = new WebDriverWait(driver, 10);
          WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
          driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

          String pageSource = driver.getPageSource();
          Document doc = Jsoup.parse(pageSource);

          String selectHtmlHigherPrioritiesOfStudent = "tr:has([data-header=\"Пріоритет\"]:matches([1-%s]))".formatted(student.getPriority() - 1);
          Elements higherPrioritiesData = doc.select(selectHtmlHigherPrioritiesOfStudent);

          if (higherPrioritiesData.size() > 1) {
              for (Element studentsTableItem : higherPrioritiesData) {
                  Elements studentZnoMarksHtml = studentsTableItem.select("td[data-header=\"Складові заг. балу\"] > dl");
                  if (checkStudentByZnoMarksService.isCurrentStudent(studentZnoMarksHtml, student.getZnoMarks())) {
                      getStudentStatementFromHtmlAndInsertToDb(studentsTableItem, student.getId());
                  }
              }
          } else {
                  getStudentStatementFromHtmlAndInsertToDb(higherPrioritiesData.first(), student.getId());
          }
          driver.close();
        }
    }

    private void getStudentStatementFromHtmlAndInsertToDb(Element studentHtml, int studentId) throws SQLException {
        if (studentHtml != null) {
            double grade = Double.parseDouble(studentHtml.select("tr > td:nth-child(7)").text());
            int priority = Integer.parseInt(studentHtml.select("tr > td:nth-child(5)").text());
            String universityShortName = studentHtml.select("tr > td:nth-child(10) a").text();
            int directionDataId = Integer.parseInt(studentHtml.select("tr > td:nth-child(12) span").text());
            String facultyShortName = studentHtml.select("tr > td:nth-child(11)").text();

            String directionLink = studentHtml.select("tr > td:nth-child(4) a").attr("href");
            String[] directionLinkArr = directionLink.split("/");
            int directionId = Integer.parseInt(directionLinkArr[directionLinkArr.length - 1]);

            StudentStatement studentStatement = new StudentStatement(0, grade, priority, universityShortName, directionDataId, studentId, directionId, facultyShortName);

//            System.out.print("studentStatement" + studentStatement);
            studentStatementRepository.insert(studentStatement);
        }
    }

    public void updateStudentsStatementsDirectionIdByStudentsIdAndUniversityShortName(String universityShortName) throws SQLException {
        List<StudentStatement> studStatList = studentStatementRepository.getByUniversityShortName(universityShortName);
//        StudentStatement studentStat = studentStatementRepository.getById(1);
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Desktop\\diplom\\Libraries\\ChromeDriver\\chromedriver.exe");

        for (StudentStatement studentStat: studStatList) {
            Student student = studentsRepository.getById(studentStat.getStudents_id());

            String studentInfoUrl = baseURL + student.getSearchLink();
            WebDriver driver = new ChromeDriver();
            driver.get(studentInfoUrl);
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

            String pageSource = driver.getPageSource();
            Document doc = Jsoup.parse(pageSource);

            String selectHtmlHigherPrioritiesOfStudent = "tr:has([data-header=\"Пріоритет\"]:matches([%s]))".formatted(studentStat.getPriority());
            Elements higherPrioritiesData = doc.select(selectHtmlHigherPrioritiesOfStudent);

        if (higherPrioritiesData.size() > 1) {
            for (Element studentsTableItem : higherPrioritiesData) {
                Elements studentZnoMarksHtml = studentsTableItem.select("td[data-header=\"Складові заг. балу\"] > dl");
                if (checkStudentByZnoMarksService.isCurrentStudent(studentZnoMarksHtml, student.getZnoMarks())) {
                    updateDirectionIdInStudStatDb(studentsTableItem, studentStat.getId(), studentStat.getPriority());
                }
            }
        } else {
            updateDirectionIdInStudStatDb(higherPrioritiesData.first(), studentStat.getId(), studentStat.getPriority());
        }
            driver.close();
        }
    }

    private void updateDirectionIdInStudStatDb(Element studentHtml, int studentStatementId, int priority) throws SQLException {
        if (studentHtml != null) {
            System.out.print("studentHtml" + studentHtml);
            System.out.print("studentStatementId" + studentStatementId);
            String directionLink = studentHtml.select("tr > td:nth-child(4) a").attr("href");
            String[] directionLinkArr = directionLink.split("/");

            int directionId = Integer.parseInt(directionLinkArr[directionLinkArr.length - 1]);
            System.out.print("directionId" + directionId);

            studentStatementRepository.updateDirectionId(studentStatementId, directionId, priority);
        }
    }

    public void testGettingOfStudStatementDirectionId() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Desktop\\diplom\\Libraries\\ChromeDriver\\chromedriver.exe");
        String studentInfoUrl = "https://abit-poisk.org.ua/#search-%D0%A1%D0%BB%D1%96%D1%81%D0%B0%D1%80%D0%B5%D0%BD%D0%BA%D0%BE+%D0%84.+%D0%93.+2020";
        WebDriver driver = new ChromeDriver();
        driver.get(studentInfoUrl);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        String pageSource = driver.getPageSource();
        Document doc = Jsoup.parse(pageSource);

        String selectHtmlHigherPrioritiesOfStudent = "tr:has([data-header=\"Пріоритет\"]:matches([3]))";
        Elements higherPrioritiesData = doc.select(selectHtmlHigherPrioritiesOfStudent);

        String directionLink = higherPrioritiesData.select("tr > td:nth-child(4) a").attr("href");

        String[] array = directionLink.split("/");

        int directionId = Integer.parseInt(array[array.length - 1]);

        System.out.print("higherPrioritiesData" + directionId);
    }

    public void writeStudentStatementsTestPage(String str)
            throws IOException {
        FileUtils.writeStringToFile(new File("StudentStatementsTestPage.txt"), str);
    }

    public String readStudentStatementsTestPage()
            throws IOException {
        String content = Files.readString(Path.of("StudentStatementsTestPage.txt"), StandardCharsets.UTF_8);
        return content;
    }
}
