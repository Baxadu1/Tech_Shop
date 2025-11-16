package abstractFactoty;

import abstractFactoty.interfaces.Headphones;
import abstractFactoty.interfaces.Laptops;
import abstractFactoty.interfaces.Phones;
import abstractFactoty.products.SamsungBuds;
import abstractFactoty.products.SamsungGalaxy;
import abstractFactoty.products.SamsungLaptop;

public class SamsungFac extends abstractFactory {
    public Phones addtocartPhones() {
        return new SamsungGalaxy();
    }

    public Headphones addtocartHeadphones() {
        return new SamsungBuds();
    }

    public Laptops addtocartLaptops() {
        return new SamsungLaptop();
    }
}
