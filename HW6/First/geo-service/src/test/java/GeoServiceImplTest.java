import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

public class GeoServiceImplTest {

    private final GeoServiceImpl geoService = new GeoServiceImpl();

    @Test
    public void testByIpForRussianSegment() {
        String russianIp = "172.123.45.67";

        Location result = geoService.byIp(russianIp);

        assertEquals(Country.RUSSIA, result.getCountry());
    }

    @Test
    public void testByIpForAmericanSegment() {
        String americanIp = "96.123.45.67";

        Location result = geoService.byIp(americanIp);

        assertEquals(Country.USA, result.getCountry());
    }

    @Test
    public void testByIpForLocalhost() {
        String localhostIp = "127.0.0.1";

        Location result = geoService.byIp(localhostIp);

        assertNull(result.getCountry());
    }

    @Test
    public void testByIpForUnknownIp() {
        String unknownIp = "192.168.1.1";

        Location result = geoService.byIp(unknownIp);

        assertNull(result);
    }

    @Test
    public void testByIpWithNullIp() {
        assertThrows(NullPointerException.class, () -> geoService.byIp(null));
    }
}