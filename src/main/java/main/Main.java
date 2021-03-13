package main;

import database.repositories.StudentRepository;
import database.repositories.UniversityRepository;
import models.University;
import parser.Parser;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
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
        Parser parser = new Parser();
        parser.parseStudentStatementsFromStudentInfoPage();

    }
}
