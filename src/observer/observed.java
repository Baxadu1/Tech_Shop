package observer;

public interface observed {
    void addObserver(observer observer);
    void removeObserver(observer observer);
    void notifyObserver();
}
