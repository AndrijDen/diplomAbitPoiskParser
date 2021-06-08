package main;

import graphLogic.*;

public class Main {

    public static void main(String[] args) throws Exception {

//        Parse student and studentStatement data
//        Parser parser = new Parser();
//        parser.parseStudentsFromDirectionPageByDirectionIds();
//        parser.parseStudentStatementsFromStudentInfoPage();

//        Finding all cycles in graph
        FindAllCycles findAllCycles = new FindAllCycles();

//        @param display graph using JFrame
        findAllCycles.findAllCycles(false);
    }
}
