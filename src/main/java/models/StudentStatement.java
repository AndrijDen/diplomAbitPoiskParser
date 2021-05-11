package models;

public class StudentStatement {
    private int id;
    private double grade;
    private int priority;
    private String universityShortName;
    private int directionDataId;
    private int students_id;
    private String facultyShortName;

    public StudentStatement(int id, double grade, int priority, String universityShortName, int directionDataId, int students_id, String facultyShortName) {
        this.id = id;
        this.grade = grade;
        this.priority = priority;
        this.universityShortName = universityShortName;
        this.directionDataId = directionDataId;
        this.students_id = students_id;
        this.facultyShortName = facultyShortName;
    }

    public StudentStatement(){};

    public int getId() {
        return id;
    }

    public double getGrade() {
        return grade;
    }

    public int getPriority() {
        return priority;
    }

    public String getUniversityShortName() {
        return universityShortName;
    }

    public int getDirectionDataId() {
        return directionDataId;
    }

    public int getStudents_id() {
        return students_id;
    }

    public String getFacultyShortName() {
        return facultyShortName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setUniversityShortName(String universityShortName) {
        this.universityShortName = universityShortName;
    }

    public void setDirectionDataId(int directionDataId) {
        this.directionDataId = directionDataId;
    }

    public void setStudents_id(int students_id) {
        this.students_id = students_id;
    }

    public void setFacultyShortName(String facultyShortName) {
        this.facultyShortName = facultyShortName;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", grade=" + grade +
                ", priority=" + priority +
                ", universityShortName='" + universityShortName + '\'' +
                ", directionDataId=" + directionDataId +
                ", students_id=" + students_id +
                ", facultyShortName=" + facultyShortName +
                '}';
    }
}
