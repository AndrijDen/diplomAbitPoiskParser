package parser;

import com.google.gson.Gson;
import database.repositories.DirectionRepository;
import database.repositories.StudentRepository;
import models.Direction;
import models.Student;
import models.ZnoMark;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
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

//        for (Direction direction: directionList) {
//            String directionUrl = directionBaseUrl + direction.getId();
        for (int i = 0; i < 1; i++) {
            String directionUrl = directionBaseUrl + directionList.get(i).getId();

            Document doc = Jsoup.connect(directionUrl).get();
            Elements studentsTable = doc.select("tr.application-status-9:has([data-header=\"Пріоритет\"]:matches([2-9]))");
            for (Element studentsTableItem : studentsTable) {
                Elements marksBlock = studentsTableItem.select("tr > td:nth-child(6) > div > ul");
                double averageSchoolMark  = Double.parseDouble(marksBlock.select("ul > li:last-child > strong").text());
                String name = studentsTableItem.select("tr > td:nth-child(2) a").text();
                String searchLink = studentsTableItem.select("tr > td:nth-child(2) a").attr("href") + "+" + averageSchoolMark;
                int priority = Integer.parseInt(studentsTableItem.select("tr > td:nth-child(3)").text());
                double grade = Double.parseDouble(studentsTableItem.select("tr > td:nth-child(4)").text());
                int direction_id = directionList.get(i).getId();
//                int direction_id = direction.getId();

                Student student = new Student(0, name, searchLink, priority, grade, direction_id, averageSchoolMark, getAllZnoMarks(marksBlock));

                StudentRepository studentsRepository = new StudentRepository();
                studentsRepository.insert(student);
                System.out.println("student" + student);
            }
        }
    }

    private ZnoMark[] getAllZnoMarks(Elements marksBlock) {
        ArrayList<ZnoMark> znoMarks = new ArrayList<ZnoMark>();
        for (Element markItem : marksBlock.select("li")) {
            if (znoMarks.size() < 3) {
                String markName = markItem.text().substring(0, markItem.text().indexOf(":"));
                double markValue = Double.parseDouble(markItem.select("strong").text());
                znoMarks.add(new ZnoMark(markName, markValue));
            }
        }
        return znoMarks.toArray(new ZnoMark[0]);
    }

    public void parseStudentStatementsFromStudentInfoPage() throws SQLException, IOException {
        StudentRepository studentsRepository = new StudentRepository();
        List<Student> studentsList = studentsRepository.getAll();

        for (int i = 0; i < 1; i++) {
            String studentInfoUrl = baseURL + studentsList.get(i).getSearchLink();
            Document doc = Jsoup.connect(studentInfoUrl).get();
            System.out.println("studentInfoUrl" + studentInfoUrl);

//            first-child > dl > dd:nth-child(2)
//            Elements incorrectObj = doc.select("dl");
//            System.out.println("incorrectObj" + incorrectObj);

            Elements studentsTable = doc.select("tr.application-status-9:has([data-header=\"Пріоритет\"]:matches([2-9]))");
            System.out.println("studentsTable" + studentsTable);
//            System.out.println("doc" + doc);
        }
    }

}
