package org.example.oop;

/**
 * ООП-СТИЛЬ.
 *
 * Класс Product инкапсулирует данные одного товара и умеет:
 *  - увеличивать своё количество (приход),
 *  - уменьшать количество, но не уходить в минус (расход).
 *
 * Здесь и живёт логика "не уходить в минус" — внутри товара.
 * В структурной версии эта проверка была в функции issue().
 */
public class Product {

    private final String sku;      // артикул — не меняется
    private final String name;     // название — не меняется
    private int quantity;          // количество — меняется

    public Product(String sku, String name, int quantity) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("Артикул не может быть пустым");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название не может быть пустым");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Начальное количество не может быть отрицательным");
        }
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
    }

    // --- Геттеры (поля закрыты, доступ только на чтение) ---
    public String getSku()      { return sku; }
    public String getName()     { return name; }
    public int getQuantity()    { return quantity; }

    /**
     * Приход: увеличить количество.
     * @return true, если операция выполнена; false, если приход некорректный.
     */
    public boolean increase(int amount) {
        if (amount <= 0) {
            return false;
        }
        this.quantity += amount;
        return true;
    }

    /**
     * Расход: уменьшить количество, но не уходить в минус.
     * @return true, если удалось списать; false, если не хватает или запрос некорректен.
     */
    public boolean decrease(int amount) {
        if (amount <= 0) {
            return false;
        }
        if (amount > this.quantity) {
            return false;
        }
        this.quantity -= amount;
        return true;
    }
}