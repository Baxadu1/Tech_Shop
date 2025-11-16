package abstractFactoty;

import abstractFactoty.interfaces.Headphones;
import abstractFactoty.interfaces.Laptops;
import abstractFactoty.interfaces.Phones;

public abstract class abstractFactory {
    public abstract Headphones addtocartHeadphones();
    public abstract Phones addtocartPhones();
    public abstract Laptops addtocartLaptops();


}
