package payment.strategy;

public class KaspiPayment implements PaymentStrategy {
    public void pay(double amount){
        System.out.println("Paid $" + amount + " through Kaspi Pay");
    }
}
