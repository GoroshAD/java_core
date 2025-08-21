import java.util.function.*;

public class Calculator {
    public static Supplier<Calculator> instance = Calculator::new;

    public BinaryOperator<Integer> plus = (x, y) -> x + y;
    public BinaryOperator<Integer> minus = (x, y) -> x - y;
    public BinaryOperator<Integer> multiply = (x, y) -> x * y;
    public BinaryOperator<Integer> divide = (x, y) -> {   // при делении на ноль будем выбрасывать исключение.
        if (y == 0) throw new ArithmeticException("Деление на ноль!");
        return x / y;
    } ;

    public UnaryOperator<Integer> pow = x -> x * x;
    public UnaryOperator<Integer> abs = x -> x > 0 ? x : x * -1;

    public Predicate<Integer> isPositive = x -> x > 0;

    public Consumer<Integer> println = System.out::println;


}