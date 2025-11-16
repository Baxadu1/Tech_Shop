package payment.adapter;

import external.ExternalPayPalAPI;
import payment.external.ExternalPayPalAPI;
import payment.strategy.PaymentStrategy;

public class PayPalAdapter implements PaymentStrategy {

    private ExternalPayPalAPI api = new ExternalPayPalAPI();

    public void pay(double amount){
        api.sendPayment(amount);
    }
}
