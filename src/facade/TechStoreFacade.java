package facade;

import builder.PcConfigurator;
import abstractFactoty.abstractFactory;
import abstractFactoty.AppleFac;
import abstractFactoty.SamsungFac;
import abstractFactoty.interfaces.Headphones;
import abstractFactoty.interfaces.Laptops;
import abstractFactoty.interfaces.Phones;
import observer.storenotifyer;
import observer.Subscriber;
import payment.strategy.PaymentStrategy;
import payment.strategy.CreditCardPayment;
import payment.strategy.CryptoPayment;
import payment.strategy.KaspiPayment;
import payment.adapter.PayPalAdapter;

public class TechStoreFacade {
    private storenotifyer notificationSystem;
    private PcConfigurator.PcBuilderConf pcBuilder;
    private abstractFactory currentFactory;

    public TechStoreFacade() {
        this.notificationSystem = new storenotifyer();
        PcConfigurator pcConfigurator = new PcConfigurator();
        this.pcBuilder = pcConfigurator.new PcBuilderConf();
    }

    public String getPCConfiguration() {
        try {
            java.lang.reflect.Field pcField = pcBuilder.getClass().getDeclaredField("pc");
            pcField.setAccessible(true);
            PcConfigurator.PC pc = (PcConfigurator.PC) pcField.get(pcBuilder);

            return "PC Configuration:\n" +
                    "CPU: " + pc.CPU + "\n" +
                    "GPU: " + pc.GPU + "\n" +
                    "RAM: " + pc.Ram + "GB\n" +
                    "Storage: " + pc.Storage + "GB";
        } catch (Exception e) {
            return "PC configuration not complete";
        }
    }

    public void setBrand(String brand) {
        if ("apple".equalsIgnoreCase(brand)) {
            currentFactory = new AppleFac();
        } else if ("samsung".equalsIgnoreCase(brand)) {
            currentFactory = new SamsungFac();
        }
    }

    public String getCurrentBrand() {
        if (currentFactory instanceof AppleFac) {
            return "Apple";
        } else if (currentFactory instanceof SamsungFac) {
            return "Samsung";
        }
        return "No brand selected";
    }

    public void subscribeToNotifications(String subscriberName) {
        Subscriber subscriber = new Subscriber(subscriberName);
        notificationSystem.addObserver(subscriber);
    }

    public void unsubscribeFromNotifications(String subscriberName) {
    }

    public void addNewProduct(String productName) {
        notificationSystem.addProduct(productName);
    }

    public void removeProduct(String productName) {
        notificationSystem.removeProduct(productName);
    }

    public void processPayment(String paymentMethod, double amount) {
        PaymentStrategy strategy = getPaymentStrategy(paymentMethod);
        if (strategy != null) {
            strategy.pay(amount);
        }
    }

    private PaymentStrategy getPaymentStrategy(String method) {
        switch (method.toLowerCase()) {
            case "creditcard":
                return new CreditCardPayment();
            case "crypto":
                return new CryptoPayment();
            case "kaspi":
                return new KaspiPayment();
            case "paypal":
                return new PayPalAdapter();
            default:
                System.out.println("Unknown payment method: " + method);
                return null;
        }
    }

    public void completePurchase(String pcConfig, String products, String paymentMethod, double amount) {
        System.out.println("Processing complete purchase...");

        if (pcConfig != null && !pcConfig.isEmpty()) {
            System.out.println("PC Configuration: " + pcConfig);
        }

        if (products != null && !products.isEmpty()) {
            System.out.println("Products: " + products);
        }

        processPayment(paymentMethod, amount);
        addNewProduct("Recent purchase completed");

        System.out.println("Purchase completed successfully!");
    }
}