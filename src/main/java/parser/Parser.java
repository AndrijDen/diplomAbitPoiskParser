package parser;

import database.repositories.DirectionRepository;
import database.repositories.StudentRepository;
import models.Direction;
import models.Student;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Parser {
    private String baseURL = "http://abit-poisk.org.ua/";
    private int year = 2020;
    private int id = 0;

    public void parseHtml() throws IOException {
        Document doc = Jsoup.connect(this.baseURL).get();
        System.out.print(doc);
    }

    public void parseStudentsFromDirectionPageByDirectionIds() throws SQLException, IOException {
        String directionBaseUrl = baseURL + "rate" + year + "/direction/";

        DirectionRepository directionRepository = new DirectionRepository();
        List<Direction> directionList = directionRepository.getAll();

        for (int i = 0; i < 1; i++) {
            String directionUrl = directionBaseUrl + directionList.get(i).getId();
//            System.out.println(directionUrl);

            Document doc = Jsoup.connect(directionUrl).get();
            Elements studentsTable = doc.select("tr.application-status-9:has([data-header=\"Пріоритет\"]:matches([2-9]))");
//            System.out.println("StudentsHtml" + studentsTable);

            for (Element studentsTableItem : studentsTable) {
                String name = studentsTableItem.select("tr > td:nth-child(2) a").text();
                String searchLink = studentsTableItem.select("tr > td:nth-child(2) a").attr("href");
                int priority = Integer.parseInt(studentsTableItem.select("tr > td:nth-child(3)").text());
                double grade = Double.parseDouble(studentsTableItem.select("tr > td:nth-child(4)").text());
                int direction_id = directionList.get(i).getId();
                double averageSchoolMark  = 11.1;
//                ZnoMarks znoMarks[] = new ZnoMarks[3];
//                znoMarks[0] = new ZnoMarks("qwe", 198.2);
//                znoMarks[1] = new ZnoMarks("asd", 198.3);
//                znoMarks[2] = new ZnoMarks("zxc", 198.4);

//                Student student = new Student(0, name, searchLink, priority, grade, direction_id, averageSchoolMark, znoMarks);
                Student student = new Student(0, name, searchLink, priority, grade, direction_id);

//                StudentRepository studentsRepository = new StudentRepository();
//                studentsRepository.insert(student);

                System.out.println("student" + student);
//                System.out.println("priority" + searchLink);
            }
        }
    }

    public void parseStudentStatementsFromStudentInfoPage() throws SQLException, IOException {
        StudentRepository studentsRepository = new StudentRepository();
        List<Student> studentsList = studentsRepository.getAll();

        for (int i = 0; i < 1; i++) {
            String studentInfoUrl = baseURL + studentsList.get(i).getSearchLink();
            Document doc = Jsoup.connect(studentInfoUrl).get();
            System.out.println("doc" + doc);
            Elements studentsTable = doc.select("tr.application-status-9:has([data-header=\"Пріоритет\"]:matches([2-9]))");
        }

    }
}
