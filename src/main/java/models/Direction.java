package models;

public class Direction {
    private int id;
    private String name;
    private int directionId;
    private int university_id;
    private String facultyName;
    private String facultyShortName;

    public Direction(){};

    public Direction(int id, String name, int directionId, int university_id, String facultyName, String facultyShortName) {
        this.id = id;
        this.name = name;
        this.directionId = directionId;
        this.university_id = university_id;
        this.facultyName = facultyName;
        this.facultyShortName = facultyShortName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDirectionId() {
        return directionId;
    }

    public int getUniversity_id() {
        return university_id;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public String getFacultyShortName() {
        return facultyShortName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDirectionId(int directionId) {
        this.directionId = directionId;
    }

    public void setUniversity_id(int university_id) {
        this.university_id = university_id;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public void setFacultyShortName(String facultyShortName) {
        this.facultyShortName = facultyShortName;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", directionId=" + directionId +
                ", university_id=" + university_id +
                ", facultyName='" + facultyName + '\'' +
                ", facultyShortName='" + facultyShortName + '\'' +
                '}';
    }
}
