package models;

public class Direction {
    private int id;
    private String name;
    private int directionId;
    private int university_id;

    public Direction(int id, String name, int directionId, int university_id) {
        this.id = id;
        this.name = name;
        this.directionId = directionId;
        this.university_id = university_id;
    }

    public Direction(){};

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

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", directionId=" + directionId +
                ", university_id=" + university_id +
                '}';
    }
}
