public class OrderGenerator {
    public static Order getDefault(String[] color) {
        return new Order("Ivan", "Ivanov", "Moscow", 1, "8-800-555-35-35", 5, "2020-06-06", "Comment1", color);
    }
}