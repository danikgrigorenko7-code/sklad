package org.example.oop;

public class OopWarehouse {

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();

        System.out.println("=== ДЕМО: ООП-стиль — Склад товаров ===\n");

        System.out.println("1) Приход товаров:");
        warehouse.receive("SKU-001", "Молоко", 10);
        warehouse.receive("SKU-002", "Хлеб", 5);
        warehouse.receive("SKU-003", "Сыр", 3);

        System.out.println("\n2) Повторный приход молока (+5):");
        warehouse.receive("SKU-001", "Молоко", 5);

        System.out.println("\n3) Попытка расхода больше, чем есть (SKU-001, 20 шт):");
        warehouse.issue("SKU-001", 20);

        System.out.println("\n4) Успешный расход (SKU-001, 7 шт):");
        warehouse.issue("SKU-001", 7);

        System.out.println("\n5) Расход несуществующего товара (SKU-999, 1 шт):");
        warehouse.issue("SKU-999", 1);

        System.out.println("\n6) Остаток по артикулу SKU-002: " + warehouse.stockOf("SKU-002"));
        System.out.println("   Остаток по артикулу SKU-999 (нет такого): " + warehouse.stockOf("SKU-999"));

        System.out.println("\n7) Товары с остатком ниже порога 5:");
        for (Product p : warehouse.belowThreshold(5)) {
            System.out.printf("   - %s (%s): %d шт.%n", p.getName(), p.getSku(), p.getQuantity());
        }

        System.out.println("\n8) Общий объём единиц на складе: " + warehouse.totalUnits());

        System.out.println("\n9) Текущее состояние склада:");
        for (Product p : warehouse.allProducts()) {
            System.out.printf("   %s | %s | %d шт.%n", p.getSku(), p.getName(), p.getQuantity());
        }
    }
}