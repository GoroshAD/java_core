public class Main {
    public static void main(String[] args) {
        Calculator calc = Calculator.instance.get();

        try {
            int a = calc.plus.apply(1, 2); // результатом сложения будет 3
            int b = calc.minus.apply(1, 1); // результатом вычитания будет 0
            int c = calc.divide.apply(a, b);  // возникает деление на ноль

            calc.println.accept(c);
        } catch (ArithmeticException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
