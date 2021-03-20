package models;

import java.util.Objects;

public class ZnoMark {
    private String name;
    private double value;

    public ZnoMark(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZnoMark znoMark = (ZnoMark) o;
        return Double.compare(znoMark.value, value) == 0 && Objects.equals(name, znoMark.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
