package parser;

import models.ZnoMark;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class CheckStudentByZnoMarksService {
    private Elements studentMarksHtml;
    private ZnoMark[] znoMarks;

    private void setData(Elements studentMarksHtml, ZnoMark[] znoMarks) {
        this.studentMarksHtml = studentMarksHtml;
        this.znoMarks = znoMarks;
    }

//    checking if student is current student by zno marks
    public boolean isCurrentStudent(Elements studentMarksHtml, ZnoMark[] znoMarks) {
        setData(studentMarksHtml, znoMarks);
        return checkIsCurrentStudent();
    }

    private boolean checkIsCurrentStudent() {
        Elements markNamesDataHtml = studentMarksHtml.select("dt > span");
        Elements markValueDataHtml = studentMarksHtml.select("dd > strong");

        ArrayList<ZnoMark> znoMarksForCheck = new ArrayList<ZnoMark>();

        for (int i = 0; i < 3; i++) {
            znoMarksForCheck.add(new ZnoMark(markNamesDataHtml.get(i).text(), Double.parseDouble(markValueDataHtml.get(i).text())));
        }
        return areArraysEqual(znoMarksForCheck);
    }

    private boolean areArraysEqual( ArrayList<ZnoMark> arrayForCheck) {
        boolean equal = true;
        for (ZnoMark obj : arrayForCheck) {
            if (!hasArrayValue(obj)) {
                equal = false;
                break;
            }
        }
        return equal;
    }

    private boolean hasArrayValue(ZnoMark znoMarkObj) {
        boolean hasValue = false;
        for (ZnoMark o : znoMarks) {
            if (o.equals(znoMarkObj)) {
                hasValue = true;
                break;
            }
        }
        return hasValue;
    }
}
