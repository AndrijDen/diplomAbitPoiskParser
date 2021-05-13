package models;

public class DataForGraphOperations {
    int studStatId;
    double grade;
    int priority;
    String studName;
    int studId;
    String toUniversity;
    String toDirection;
    String toFaculty;
    String fromUniversity;
    String fromDirection;
    String fromFaculty;

    public int getStudStatId() {
        return studStatId;
    }

    public double getGrade() {
        return grade;
    }

    public int getPriority() {
        return priority;
    }

    public String getStudName() {
        return studName;
    }

    public int getStudId() {
        return studId;
    }

    public String getToUniversity() {
        return toUniversity;
    }

    public String getToDirection() {
        return toDirection;
    }

    public String getToFaculty() {
        return toFaculty;
    }

    public String getFromUniversity() {
        return fromUniversity;
    }

    public String getFromDirection() {
        return fromDirection;
    }

    public String getFromFaculty() {
        return fromFaculty;
    }

    public void setStudStatId(int studStatId) {
        this.studStatId = studStatId;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setStudName(String studName) {
        this.studName = studName;
    }

    public void setStudId(int studId) {
        this.studId = studId;
    }

    public void setToUniversity(String toUniversity) {
        this.toUniversity = toUniversity;
    }

    public void setToDirection(String toDirection) {
        this.toDirection = toDirection;
    }

    public void setToFaculty(String toFaculty) {
        this.toFaculty = toFaculty;
    }

    public void setFromUniversity(String fromUniversity) {
        this.fromUniversity = fromUniversity;
    }

    public void setFromDirection(String fromDirection) {
        this.fromDirection = fromDirection;
    }

    public void setFromFaculty(String fromFaculty) {
        this.fromFaculty = fromFaculty;
    }

    @Override
    public String toString() {
        return "{" +
                "studStatId=" + studStatId +
                ", grade=" + grade +
                ", priority=" + priority +
                ", studName='" + studName + '\'' +
                ", studId=" + studId +
                ", toUniversity='" + toUniversity + '\'' +
                ", toDirection='" + toDirection + '\'' +
                ", toFaculty='" + toFaculty + '\'' +
                ", fromUniversity='" + fromUniversity + '\'' +
                ", fromDirection='" + fromDirection + '\'' +
                ", fromFaculty='" + fromFaculty + '\'' +
                '}';
    }
}
