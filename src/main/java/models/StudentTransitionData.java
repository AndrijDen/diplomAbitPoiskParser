package models;

public class StudentTransitionData {
    private String name;
    private String fromUniversity;
    private String toUniversity;

    public StudentTransitionData(String name, String fromUniversity, String toUniversity) {
        this.name = name;
        this.fromUniversity = fromUniversity;
        this.toUniversity = toUniversity;
    }

    public String getName() {
        return name;
    }

    public String getFromUniversity() {
        return fromUniversity;
    }

    public String getToUniversity() {
        return toUniversity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFromUniversity(String fromUniversity) {
        this.fromUniversity = fromUniversity;
    }

    public void setToUniversity(String toUniversity) {
        this.toUniversity = toUniversity;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", fromUniversity='" + fromUniversity + '\'' +
                ", toUniversity='" + toUniversity + '\'' +
                '}';
    }
}
