package project.filesWalker;

public enum Directories {
    TECH("Техника"),
    QUAL("Квалификация"),
    COMM("Коммерческая");

    public final String name;

    Directories(String name) {
        this.name = name;
    }
}
