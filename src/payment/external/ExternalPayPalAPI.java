package payment.external;

public class ExternalPayPalAPI {
    public void sendPayment(double money){
        System.out.println("PayPal processed payment: $" + money);
    }
}
