import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocalizationServiceImplTest {

    private final LocalizationServiceImpl localizationService = new LocalizationServiceImpl();


    @Test
    public void testLocaleForRussia() {
        String result = localizationService.locale(Country.RUSSIA);

        assertEquals("Добро пожаловать", result);
    }

    @Test
    public void testLocaleForUSA() {
        String result = localizationService.locale(Country.USA);

        assertEquals("Welcome", result);
    }

    @Test
    public void testLocaleForGermany() {
        String result = localizationService.locale(Country.GERMANY);

        assertEquals("Welcome", result);
    }

    @Test
    public void testLocaleWithNullCountry() {
        assertThrows(NullPointerException.class, () -> localizationService.locale(null));
    }
}
