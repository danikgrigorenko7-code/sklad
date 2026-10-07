package org.example.structural;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StructuralWarehouse {

    public record Product(String sku, String name, int quantity) {}

    public static void receive(Map<String, Product> warehouse, String sku, String name, int amount) {
        if (amount <= 0) {
            System.out.println("  [ОШИБКА] Приход должен быть положительным: " + amount);
            return;
        }
        Product existing = warehouse.get(sku);
        if (existing == null) {
            warehouse.put(sku, new Product(sku, name, amount));
            System.out.printf("  [+] Добавлен новый товар: %s (%s) x%d%n", name, sku, amount);
        } else {
            int newQty = existing.quantity() + amount;
            warehouse.put(sku, new Product(sku, existing.name(), newQty));
            System.out.printf("  [+] Приход: %s (%s) +%d, стало %d%n", existing.name(), sku, amount, newQty);
        }
    }

    public static boolean issue(Map<String, Product> warehouse, String sku, int amount) {
        if (amount <= 0) {
            System.out.println("  [ОШИБКА] Расход должен быть положительным: " + amount);
            return false;
        }
        Product existing = warehouse.get(sku);
        if (existing == null) {
            System.out.println("  [ОТКАЗ] Товар не найден: " + sku);
            return false;
        }
        if (existing.quantity() < amount) {
            System.out.printf("  [ОТКАЗ] Не хватает %s (%s): на складе %d, запрошено %d%n",
                    existing.name(), sku, existing.quantity(), amount);
            return false;
        }
        int newQty = existing.quantity() - amount;
        warehouse.put(sku, new Product(sku, existing.name(), newQty));
        System.out.printf("  [-] Расход: %s (%s) -%d, стало %d%n", existing.name(), sku, amount, newQty);
        return true;
    }

    public static int stockOf(Map<String, Product> warehouse, String sku) {
        Product p = warehouse.get(sku);
        return p == null ? -1 : p.quantity();
    }

    public static List<Product> belowThreshold(Map<String, Product> warehouse, int threshold) {
        List<Product> result = new ArrayList<>();
        for (Product p : warehouse.values()) {
            if (p.quantity() < threshold) {
                result.add(p);
            }
        }
        return result;
    }

    public static int totalUnits(Map<String, Product> warehouse) {
        int sum = 0;
        for (Product p : warehouse.values()) {
            sum += p.quantity();
        }
        return sum;
    }

    public static void main(String[] args) {
        Map<String, Product> warehouse = new LinkedHashMap<>();

        System.out.println("=== ДЕМО: Структурный стиль — Склад товаров ===\n");

        System.out.println("1) Приход товаров:");
        receive(warehouse, "SKU-001", "Молоко", 10);
        receive(warehouse, "SKU-002", "Хлеб", 5);
        receive(warehouse, "SKU-003", "Сыр", 3);

        System.out.println("\n2) Повторный приход молока (+5):");
        receive(warehouse, "SKU-001", "Молоко", 5);

        System.out.println("\n3) Попытка расхода больше, чем есть (SKU-001, 20 шт):");
        issue(warehouse, "SKU-001", 20);

        System.out.println("\n4) Успешный расход (SKU-001, 7 шт):");
        issue(warehouse, "SKU-001", 7);

        System.out.println("\n5) Расход несуществующего товара (SKU-999, 1 шт):");
        issue(warehouse, "SKU-999", 1);

        System.out.println("\n6) Остаток по артикулу SKU-002: " + stockOf(warehouse, "SKU-002"));
        System.out.println("   Остаток по артикулу SKU-999 (нет такого): " + stockOf(warehouse, "SKU-999"));

        System.out.println("\n7) Товары с остатком ниже порога 5:");
        for (Product p : belowThreshold(warehouse, 5)) {
            System.out.printf("   - %s (%s): %d шт.%n", p.name(), p.sku(), p.quantity());
        }

        System.out.println("\n8) Общий объём единиц на складе: " + totalUnits(warehouse));

        System.out.println("\n9) Текущее состояние склада:");
        for (Product p : warehouse.values()) {
            System.out.printf("   %s | %s | %d шт.%n", p.sku(), p.name(), p.quantity());
        }
    }
}