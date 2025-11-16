package observer;

import java.util.List;

public class Subscriber implements observer {
    String name;

    public Subscriber(String name) {
        this.name = name;
    }

    @Override
    public void update(List<String> newProduct) {
        System.out.println("We want to inform you " + this.name + " that we have new product. It's " + newProduct);
    }
}
