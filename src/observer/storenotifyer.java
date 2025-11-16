package observer;

import java.util.ArrayList;
import java.util.List;

public class storenotifyer implements observed {

    private List<String> newProduct = new ArrayList<>();
    private List<observer> subscribers = new ArrayList<>();

    public void addProduct(String product) {
        newProduct.add(product);
        notifyObserver();
    }

    public void removeProduct(String product) {
        newProduct.remove(product);
        notifyObserver();
    }

    @Override
    public void addObserver(observer observer) {
        subscribers.add(observer);
    }

    @Override
    public void removeObserver(observer observer) {
        subscribers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        List<String> snapshot = new ArrayList<>(newProduct);
        for (observer o : subscribers) {
            o.update(snapshot);
        }
    }
}
