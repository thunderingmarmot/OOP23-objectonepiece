package Model;

public interface Entity {
    public Section getSection();
    
    public record Position(int x, int y) {};
    public Position getPosition();

    public enum Type {
        BARREL,
        SHIP,
        ISLAND
    }
    public Type getType();
}