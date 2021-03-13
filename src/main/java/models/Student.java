package models;

import java.util.Arrays;

public class Student {

    private int id;
    private String name;
    private String searchLink;
    private int priority;
    private double grade;
    private int direction_id;
    private double averageSchoolMark;
    private ZnoMark[] znoMarks;

    public Student(int id, String name, String searchLink, int priority, double grade, int direction_id, double averageSchoolMark, ZnoMark[] znoMarks) {
        this.id = id;
        this.name = name;
        this.searchLink = searchLink;
        this.priority = priority;
        this.grade = grade;
        this.direction_id = direction_id;
        this.averageSchoolMark = averageSchoolMark;
        this.znoMarks = znoMarks;
    }

    public Student(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSearchLink() {
        return searchLink;
    }

    public int getPriority() {
        return priority;
    }

    public double getGrade() {
        return grade;
    }

    public int getDirection_id() {
        return direction_id;
    }

    public double getAverageSchoolMark() {
        return averageSchoolMark;
    }

    public ZnoMark[] getZnoMarks() {
        return znoMarks;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSearchLink(String searchLink) {
        this.searchLink = searchLink;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void setDirection_id(int direction_id) {
        this.direction_id = direction_id;
    }

    public void setAverageSchoolMark(double averageSchoolMark) {
        this.averageSchoolMark = averageSchoolMark;
    }

    public void setZnoMarks(ZnoMark[] znoMarks) {
        this.znoMarks = znoMarks;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", searchLink='" + searchLink + '\'' +
                ", priority=" + priority +
                ", grade=" + grade +
                ", direction_id=" + direction_id +
                ", averageSchoolMark=" + averageSchoolMark +
                ", znoMarks='" + Arrays.toString(znoMarks) + '\'' +
                '}';
    }
}
