public class CourierGenerator {
    public static Courier getDefault() {
        return new Courier("Test courier 1", "1234", "asdf");
    }

    public static Courier getEmptyCredentials() {
        return new Courier("", "", "");
    }

    public static Courier getWithoutPassword() {
        return new Courier("Test courier 1", "", "asdf");
    }

    public static Courier getWithoutLogin() {
        return new Courier("", "1234", "asdf");
    }

}
