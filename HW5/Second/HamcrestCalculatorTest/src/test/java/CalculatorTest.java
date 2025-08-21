import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CalculatorTest {
    private static long suiteStartTime;

    @BeforeAll
    public static void initSuite() {
        System.out.println("Running StringTest");
        suiteStartTime = System.nanoTime();
    }

    @AfterAll
    public static void completeSuite() {
        System.out.println("StringTest complete: " + (System.nanoTime() - suiteStartTime));
    }

    @Test
    public void testSumSuccess() {
        // given:
        int x = 10;
        int y = -2;
        int expectedRes = 8;
        // when:
        Calculator calc = Calculator.instance.get();
        int actualRes = calc.plus.apply(x, y);

        // then:
        Assertions.assertEquals(expectedRes, actualRes);
    }

    @Test
    public void testSumError() {
        // given:
        int x = 10;
        int y = 2;
        int expectedRes = 8;
        // when:
        Calculator calc = Calculator.instance.get();
        int actualRes = calc.plus.apply(x, y);

        // then:
        Assertions.assertNotEquals(expectedRes, actualRes);
    }

    //---

    @Test
    public void testSubSuccess() {
        // given:
        int x = 10;
        int y = -2;
        int expectedRes = 12;
        // when:
        Calculator calc = Calculator.instance.get();
        int actualRes = calc.minus.apply(x, y);

        // then:
        Assertions.assertEquals(expectedRes, actualRes);
    }

    @Test
    public void testSubError() {
        // given:
        int x = 10;
        int y = 2;
        int expectedRes = 12;
        // when:
        Calculator calc = Calculator.instance.get();
        int actualRes = calc.minus.apply(x, y);

        // then:
        Assertions.assertNotEquals(expectedRes, actualRes);
    }

    //---

    @Test
    public void testMulSuccess() {
        // given:
        int x = 10;
        int y = -2;
        int expectedRes = -20;
        // when:
        Calculator calc = Calculator.instance.get();
        int actualRes = calc.multiply.apply(x, y);

        // then:
        Assertions.assertEquals(expectedRes, actualRes);
    }

    @Test
    public void testMulError() {
        // given:
        int x = 10;
        int y = 2;
        int expectedRes = -20;
        // when:
        Calculator calc = Calculator.instance.get();
        int actualRes = calc.multiply.apply(x, y);

        // then:
        Assertions.assertNotEquals(expectedRes, actualRes);
    }

    //---

    @Test
    public void testDivSuccess() {
        // given:
        int x = 10;
        int y = -2;
        int expectedRes = -5;
        // when:
        Calculator calc = Calculator.instance.get();
        int actualRes = calc.divide.apply(x, y);

        // then:
        Assertions.assertEquals(expectedRes, actualRes);
    }

    @Test
    public void testDivError() {
        // given:
        int x = 10;
        int y = 2;
        int expectedRes = -5;
        // when:
        Calculator calc = Calculator.instance.get();
        int actualRes = calc.divide.apply(x, y);

        // then:
        Assertions.assertNotEquals(expectedRes, actualRes);
    }

    @Test
    public void testDivException() {
        // given:
        int x = 10;
        int y = 0;
        int expectedRes = -5;
        // when:
        Calculator calc = Calculator.instance.get();

        // then:
        Assertions.assertThrows(ArithmeticException.class, () -> {
            calc.divide.apply(x, y);
        });
    }

    //---

    @Test
    public void testPowSuccess() {
        // given:
        int x = 10;
        int expectedRes = 100;
        // when:
        Calculator calc = Calculator.instance.get();
        int actualRes = calc.pow.apply(x);

        // then:
        Assertions.assertEquals(expectedRes, actualRes);
    }

    @Test
    public void testPowError() {
        // given:
        int x = 10;
        int expectedRes = 25;
        // when:
        Calculator calc = Calculator.instance.get();
        int actualRes = calc.pow.apply(x);

        // then:
        Assertions.assertNotEquals(expectedRes, actualRes);
    }

    //---

    @Test
    public void testAbsSuccess() {
        // given:
        int x = -10;
        int expectedRes = 10;
        // when:
        Calculator calc = Calculator.instance.get();
        int actualRes = calc.abs.apply(x);

        // then:
        Assertions.assertEquals(expectedRes, actualRes);
    }

    @Test
    public void testAbsError() {
        // given:
        int x = 10;
        int expectedRes = -10;
        // when:
        Calculator calc = Calculator.instance.get();
        int actualRes = calc.abs.apply(x);

        // then:
        Assertions.assertNotEquals(expectedRes, actualRes);
    }

    //---

    @Test
    public void testIsPosSuccess() {
        // given:
        int x = 10;
        // when:
        Calculator calc = Calculator.instance.get();
        boolean actualRes = calc.isPositive.test(x);

        // then:
        Assertions.assertTrue(actualRes);
    }

    @Test
    public void testIsPosError() {
        // given:
        int x = -10;
        // when:
        Calculator calc = Calculator.instance.get();
        boolean actualRes = calc.isPositive.test(x);

        // then:
        Assertions.assertFalse(actualRes);
    }

    //------

    @Test
    public void testAnswerGreater() {
        Calculator calc = Calculator.instance.get();
        Random rand = new Random();
        List<Integer> list = List.of(
                calc.abs.apply(rand.nextInt(5) - 5),
                calc.abs.apply(rand.nextInt(5) - 5),
                calc.abs.apply(rand.nextInt(5) - 5)
        );

        assertThat(list, everyItem(greaterThan(0)));
    }

    @Test
    public void testAnswerIn() {
        Calculator calc = Calculator.instance.get();
        int result = calc.plus.apply(10, 5);

        assertThat(result, both(greaterThan(10)).and(lessThan(20)));
    }
}
