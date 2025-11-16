import builder.PcConfigurator;
import abstractFactoty.AppleFac;
import abstractFactoty.SamsungFac;
import abstractFactoty.interfaces.Headphones;
import abstractFactoty.interfaces.Laptops;
import abstractFactoty.interfaces.Phones;
import observer.storenotifyer;
import observer.Subscriber;
import payment.strategy.CreditCardPayment;
import payment.strategy.CryptoPayment;
import payment.strategy.KaspiPayment;
import payment.adapter.PayPalAdapter;
import payment.strategy.PaymentStrategy;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static storenotifyer notificationSystem = new storenotifyer();
    private static Map<String, Subscriber> subscribers = new HashMap<>();

    private static final String[] INTEL_CPUS = {
            "Intel Core i3-12100", "Intel Core i5-12400",
            "Intel Core i5-12600K", "Intel Core i7-12700K", "Intel Core i9-12900K"
    };

    private static final String[] AMD_CPUS = {
            "AMD Ryzen 3 4100", "AMD Ryzen 5 5600X",
            "AMD Ryzen 7 5800X", "AMD Ryzen 9 5900X", "AMD Ryzen 9 7950X"
    };

    private static final String[] NVIDIA_GPUS = {
            "NVIDIA GeForce RTX 3060", "NVIDIA GeForce RTX 3070",
            "NVIDIA GeForce RTX 3080", "NVIDIA GeForce RTX 3090", "NVIDIA GeForce RTX 4090"
    };

    private static final String[] AMD_GPUS = {
            "AMD Radeon RX 6600", "AMD Radeon RX 6700 XT",
            "AMD Radeon RX 6800 XT", "AMD Radeon RX 6900 XT", "AMD Radeon RX 7900 XT"
    };

    private static final int[] RAM_OPTIONS = {8, 16, 32, 64, 128};
    private static final int[] STORAGE_OPTIONS = {256, 512, 1024, 2048, 4096};

    public static void main(String[] args) {
        while (true) {
            System.out.println("Tech Store");
            System.out.println("1. PC Configuration");
            System.out.println("2. Product Catalog");
            System.out.println("3. Notification System");
            System.out.println("4. Exit");
            System.out.print("Select option: ");

            int choice = getIntInput(1, 4);

            switch (choice) {
                case 1:
                    pcConfiguration();
                    break;
                case 2:
                    productCatalog();
                    break;
                case 3:
                    notificationSystem();
                    break;
                case 4:
                    System.out.println("Goodbye");
                    return;
            }
            System.out.println();
        }
    }

    private static void pcConfiguration() {
        PcConfigurator pcConfigurator = new PcConfigurator();
        PcConfigurator.PcBuilderConf builder = pcConfigurator.new PcBuilderConf();

        System.out.println("PC Configuration");

        configureCPU(builder);
        configureGPU(builder);
        configureRAM(builder);
        configureStorage(builder);

        PcConfigurator.PC pc = getPCFromBuilder(builder);
        displayPCConfiguration(pc);
        processPayment();
    }

    private static void configureCPU(PcConfigurator.PcBuilderConf builder) {
        System.out.println("Select CPU Manufacturer:");
        System.out.println("1. Intel");
        System.out.println("2. AMD");
        System.out.print("Choice: ");

        int choice = getIntInput(1, 2);
        String[] cpus;

        if (choice == 1) {
            cpus = INTEL_CPUS;
            System.out.println("Intel Processors:");
        } else {
            cpus = AMD_CPUS;
            System.out.println("AMD Processors:");
        }

        displayOptions(cpus);
        System.out.print("Select CPU: ");
        int cpuChoice = getIntInput(1, cpus.length);
        builder.setCPU(cpus[cpuChoice - 1]);
    }

    private static void configureGPU(PcConfigurator.PcBuilderConf builder) {
        System.out.println("Select GPU Manufacturer:");
        System.out.println("1. NVIDIA");
        System.out.println("2. AMD Radeon RX");
        System.out.print("Choice: ");

        int choice = getIntInput(1, 2);
        String[] gpus;

        if (choice == 1) {
            gpus = NVIDIA_GPUS;
            System.out.println("NVIDIA GPUs:");
        } else {
            gpus = AMD_GPUS;
            System.out.println("AMD GPUs:");
        }

        displayOptions(gpus);
        System.out.print("Select GPU: ");
        int gpuChoice = getIntInput(1, gpus.length);
        builder.setGPU(gpus[gpuChoice - 1]);
    }

    private static void configureRAM(PcConfigurator.PcBuilderConf builder) {
        System.out.println("RAM Options:");
        displayOptions(RAM_OPTIONS, "GB");
        System.out.print("Select RAM: ");
        int choice = getIntInput(1, RAM_OPTIONS.length);
        builder.setRam(RAM_OPTIONS[choice - 1]);
    }

    private static void configureStorage(PcConfigurator.PcBuilderConf builder) {
        System.out.println("Storage Options:");
        displayOptions(STORAGE_OPTIONS, "GB");
        System.out.print("Select Storage: ");
        int choice = getIntInput(1, STORAGE_OPTIONS.length);
        builder.setStorage(STORAGE_OPTIONS[choice - 1]);
    }

    private static PcConfigurator.PC getPCFromBuilder(PcConfigurator.PcBuilderConf builder) {
        try {
            java.lang.reflect.Field pcField = builder.getClass().getDeclaredField("pc");
            pcField.setAccessible(true);
            return (PcConfigurator.PC) pcField.get(builder);
        } catch (Exception e) {
            return null;
        }
    }

    private static void displayPCConfiguration(PcConfigurator.PC pc) {
        if (pc == null) return;

        System.out.println("PC Configuration Complete");
        System.out.println("CPU: " + pc.CPU);
        System.out.println("GPU: " + pc.GPU);
        System.out.println("RAM: " + pc.Ram + "GB");
        System.out.println("Storage: " + pc.Storage + "GB");
    }

    private static void productCatalog() {
        System.out.println("Product Catalog");
        System.out.println("Select Brand:");
        System.out.println("1. Apple");
        System.out.println("2. Samsung");
        System.out.print("Choice: ");

        int brandChoice = getIntInput(1, 2);

        if (brandChoice == 1) {
            displayAppleProducts();
        } else {
            displaySamsungProducts();
        }
        processPayment();
    }

    private static void displayAppleProducts() {
        AppleFac factory = new AppleFac();
        System.out.println("Apple Products:");

        Phones phone = factory.addtocartPhones();
        Headphones headphones = factory.addtocartHeadphones();
        Laptops laptop = factory.addtocartLaptops();

        System.out.println("1. Phone: Apple iPhone");
        System.out.println("2. Headphones: Apple Airpods");
        System.out.println("3. Laptop: Apple Macbook");
        System.out.print("Select product to add to cart: ");

        int choice = getIntInput(1, 3);

        switch (choice) {
            case 1:
                phone.addtocart();
                break;
            case 2:
                headphones.addtocart();
                break;
            case 3:
                laptop.addtocart();
                break;
        }
    }

    private static void displaySamsungProducts() {
        SamsungFac factory = new SamsungFac();
        System.out.println("Samsung Products:");

        Phones phone = factory.addtocartPhones();
        Headphones headphones = factory.addtocartHeadphones();
        Laptops laptop = factory.addtocartLaptops();

        System.out.println("1. Phone: Samsung Galaxy");
        System.out.println("2. Headphones: Samsung Buds");
        System.out.println("3. Laptop: Samsung Laptop");
        System.out.print("Select product to add to cart: ");

        int choice = getIntInput(1, 3);

        switch (choice) {
            case 1:
                phone.addtocart();
                break;
            case 2:
                headphones.addtocart();
                break;
            case 3:
                laptop.addtocart();
                break;
        }
    }

    private static void notificationSystem() {
        while (true) {
            System.out.println("Notification System");
            System.out.println("1. Subscribe to notifications");
            System.out.println("2. Unsubscribe from notifications");
            System.out.println("3. Add new product (Admin)");
            System.out.println("4. Remove product (Admin)");
            System.out.println("5. Back to main menu");
            System.out.print("Select option: ");

            int choice = getIntInput(1, 5);

            switch (choice) {
                case 1:
                    subscribeUser();
                    break;
                case 2:
                    unsubscribeUser();
                    break;
                case 3:
                    addProduct();
                    break;
                case 4:
                    removeProduct();
                    break;
                case 5:
                    return;
            }
            System.out.println();
        }
    }

    private static void subscribeUser() {
        System.out.print("Enter your name for subscription: ");
        String name = scanner.nextLine().trim();

        if (subscribers.containsKey(name)) {
            System.out.println("You are already subscribed");
            return;
        }

        Subscriber subscriber = new Subscriber(name);
        notificationSystem.addObserver(subscriber);
        subscribers.put(name, subscriber);
        System.out.println("Successfully subscribed to notifications");
    }

    private static void unsubscribeUser() {
        System.out.print("Enter your name to unsubscribe: ");
        String name = scanner.nextLine().trim();

        Subscriber subscriber = subscribers.remove(name);
        if (subscriber != null) {
            notificationSystem.removeObserver(subscriber);
            System.out.println("Successfully unsubscribed from notifications");
        } else {
            System.out.println("No subscription found for this name");
        }
    }

    private static void addProduct() {
        System.out.print("Enter product name to add: ");
        String product = scanner.nextLine().trim();

        if (!product.isEmpty()) {
            notificationSystem.addProduct(product);
            System.out.println("Product added and subscribers notified");
        }
    }

    private static void removeProduct() {
        System.out.print("Enter product name to remove: ");
        String product = scanner.nextLine().trim();

        if (!product.isEmpty()) {
            notificationSystem.removeProduct(product);
            System.out.println("Product removed and subscribers notified");
        }
    }

    private static void processPayment() {
        System.out.println("Select Payment Method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Cryptocurrency");
        System.out.println("3. Kaspi Pay");
        System.out.println("4. PayPal");
        System.out.print("Choice: ");

        int choice = getIntInput(1, 4);
        System.out.print("Enter amount: ");
        double amount = getDoubleInput();

        PaymentStrategy strategy;
        switch (choice) {
            case 1:
                strategy = new CreditCardPayment();
                break;
            case 2:
                strategy = new CryptoPayment();
                break;
            case 3:
                strategy = new KaspiPayment();
                break;
            case 4:
                strategy = new PayPalAdapter();
                break;
            default:
                System.out.println("Invalid payment method");
                return;
        }

        strategy.pay(amount);
    }

    private static void displayOptions(String[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    private static void displayOptions(int[] options, String unit) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i] + unit);
        }
    }

    private static int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.print("Enter number between " + min + " and " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Enter valid number: ");
            }
        }
    }

    private static double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter valid amount: ");
            }
        }
    }
}