import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class MessageSenderImplTest {

    private final GeoService geoService = Mockito.mock(GeoService.class);
    private final LocalizationService localizationService = Mockito.mock(LocalizationService.class);
    private final MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

    @Test
    public void testSendRussianTextForRussianIp() {
        String russianIp = "172.123.45.67";
        Location russianLocation = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, russianIp);

        when(geoService.byIp(eq(russianIp))).thenReturn(russianLocation);
        when(localizationService.locale(eq(Country.RUSSIA))).thenReturn("Добро пожаловать");

        String result = messageSender.send(headers);
        assertEquals("Добро пожаловать", result);
    }

    @Test
    public void testSendEnglishTextForAmericanIp() {
        String americanIp = "96.123.45.67";
        Location americanLocation = new Location("New York", Country.USA, "Broadway", 10);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, americanIp);

        when(geoService.byIp(eq(americanIp))).thenReturn(americanLocation);
        when(localizationService.locale(eq(Country.USA))).thenReturn("Welcome");

        String result = messageSender.send(headers);
        assertEquals("Welcome", result);
    }

    @Test
    public void testSendEnglishTextForOtherIp() {
        String germanIp = "91.123.45.67";
        Location germanLocation = new Location("Berlin", Country.GERMANY, "Revaler Strasse 99", 12);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, germanIp);

        when(geoService.byIp(eq(germanIp))).thenReturn(germanLocation);
        when(localizationService.locale(eq(Country.GERMANY))).thenReturn("Welcome");

        String result = messageSender.send(headers);
        assertEquals("Welcome", result);
    }
}