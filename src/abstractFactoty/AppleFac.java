package abstractFactoty;

import abstractFactoty.interfaces.Headphones;
import abstractFactoty.interfaces.Laptops;
import abstractFactoty.interfaces.Phones;
import abstractFactoty.products.AppleAirpods;
import abstractFactoty.products.AppleIphone;
import abstractFactoty.products.AppleMacbook;

public class AppleFac extends abstractFactory{
    public Phones addtocartPhones() {
        return new AppleIphone();
    }

    public Headphones addtocartHeadphones() {
        return new AppleAirpods();
    }

    public Laptops addtocartLaptops() {
        return new AppleMacbook();
    }
}