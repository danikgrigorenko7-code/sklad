package org.example.oop;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {

    private final Map<String, Product> products = new LinkedHashMap<>();

    public void receive(String sku, String name, int amount) {
        if (amount <= 0) {
            System.out.println("  [ОШИБКА] Приход должен быть положительным: " + amount);
            return;
        }
        Product existing = products.get(sku);
        if (existing == null) {
            products.put(sku, new Product(sku, name, amount));
            System.out.printf("  [+] Добавлен новый товар: %s (%s) x%d%n", name, sku, amount);
        } else {
            existing.increase(amount);
            System.out.printf("  [+] Приход: %s (%s) +%d, стало %d%n",
                    existing.getName(), sku, amount, existing.getQuantity());
        }
    }

    public boolean issue(String sku, int amount) {
        if (amount <= 0) {
            System.out.println("  [ОШИБКА] Расход должен быть положительным: " + amount);
            return false;
        }
        Product product = products.get(sku);
        if (product == null) {
            System.out.println("  [ОТКАЗ] Товар не найден: " + sku);
            return false;
        }
        if (!product.decrease(amount)) {
            System.out.printf("  [ОТКАЗ] Не хватает %s (%s): на складе %d, запрошено %d%n",
                    product.getName(), sku, product.getQuantity(), amount);
            return false;
        }
        System.out.printf("  [-] Расход: %s (%s) -%d, стало %d%n",
                product.getName(), sku, amount, product.getQuantity());
        return true;
    }

    public int stockOf(String sku) {
        Product p = products.get(sku);
        return p == null ? -1 : p.getQuantity();
    }

    public List<Product> belowThreshold(int threshold) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getQuantity() < threshold) {
                result.add(p);
            }
        }
        return result;
    }

    public int totalUnits() {
        int sum = 0;
        for (Product p : products.values()) {
            sum += p.getQuantity();
        }
        return sum;
    }

    public Iterable<Product> allProducts() {
        return products.values();
    }
}