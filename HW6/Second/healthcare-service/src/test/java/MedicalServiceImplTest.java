import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MedicalServiceImplTest {

    private PatientInfoRepository repository = Mockito.mock(PatientInfoRepository.class);
    private SendAlertService alertService = Mockito.mock(SendAlertService.class);
    private MedicalServiceImpl medicalService = new MedicalServiceImpl(repository, alertService);

    @Test
    public void testCheckBloodPressureWithBadPressureShouldSendAlert() {
        String patientId = "test-patient-id";
        PatientInfo patientInfo = new PatientInfo("Иван", "Петров",
                LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80)));

        BloodPressure currentPressure = new BloodPressure(160, 100);
        when(repository.getById(patientId)).thenReturn(patientInfo);

        medicalService.checkBloodPressure(patientId, currentPressure);

        // судя по коду, любое изменение давления считается ненормальным.
        verify(alertService, times(1)).send(anyString());
    }

    @Test
    public void testCheckBloodPressureWithNormalPressureShouldNotSendAlert() {
        String patientId = "test-patient-id";
        PatientInfo patientInfo = new PatientInfo("Иван", "Петров",
                LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80)));

        BloodPressure currentPressure = new BloodPressure(120, 80);
        when(repository.getById(patientId)).thenReturn(patientInfo);

        medicalService.checkBloodPressure(patientId, currentPressure);

        verify(alertService, never()).send(anyString());
    }

    @Test
    public void testCheckTemperatureWithHighTemperatureShouldSendAlert() {
        String patientId = "test-patient-id";
        PatientInfo patientInfo = new PatientInfo("Иван", "Петров",
                LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80)));

        BigDecimal currentTemperature = new BigDecimal("38.5");
        when(repository.getById(patientId)).thenReturn(patientInfo);

        medicalService.checkTemperature(patientId, currentTemperature);

        // судя по коду, учитывается только понижение температуры, то есть высокая температура не считается плохой.
        verify(alertService, times(0)).send(anyString());
    }

    @Test
    public void testCheckTemperatureWithNormalTemperatureShouldNotSendAlert() {
        String patientId = "test-patient-id";
        PatientInfo patientInfo = new PatientInfo("Иван", "Петров",
                LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80)));

        BigDecimal currentTemperature = new BigDecimal("36.8");
        when(repository.getById(patientId)).thenReturn(patientInfo);

        medicalService.checkTemperature(patientId, currentTemperature);

        verify(alertService, never()).send(anyString());
    }

    @Test
    public void testCheckTemperatureWithLowTemperatureShouldSendAlert() {
        // Arrange
        String patientId = "test-patient-id";
        PatientInfo patientInfo = new PatientInfo("Иван", "Петров",
                LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80)));

        BigDecimal currentTemperature = new BigDecimal("35.0");
        when(repository.getById(patientId)).thenReturn(patientInfo);

        medicalService.checkTemperature(patientId, currentTemperature);

        verify(alertService, times(1)).send(anyString());
    }

    @Test
    public void testCheckBloodPressureMessageContent() {
        String patientId = "test-patient-id";
        PatientInfo patientInfo = new PatientInfo("Иван", "Петров",
                LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80)));

        BloodPressure currentPressure = new BloodPressure(160, 100);

        when(repository.getById(patientId)).thenReturn(patientInfo);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        medicalService.checkBloodPressure(patientId, currentPressure);

        verify(alertService).send(messageCaptor.capture());
        String message = messageCaptor.getValue();

        assertTrue(message.contains(String.format("Warning, patient with id: %s, need help", patientInfo.getId())));
    }

    @Test
    public void testCheckTemperatureMessageContent() {
        String patientId = "test-patient-id";
        PatientInfo patientInfo = new PatientInfo("Иван", "Петров",
                LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80)));

        BigDecimal currentTemperature = new BigDecimal("34.5");

        when(repository.getById(patientId)).thenReturn(patientInfo);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        medicalService.checkTemperature(patientId, currentTemperature);

        verify(alertService).send(messageCaptor.capture());
        String message = messageCaptor.getValue();

        assertTrue(message.contains(String.format("Warning, patient with id: %s, need help", patientInfo.getId())));
    }

    @Test
    public void testCheckBloodPressureWithPatientNotFound() {
        String patientId = "non-existent-id";
        BloodPressure currentPressure = new BloodPressure(160, 100);

        when(repository.getById(patientId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> medicalService.checkBloodPressure(patientId, currentPressure));
    }

    @Test
    public void testCheckTemperatureWithPatientNotFound() {
        String patientId = "non-existent-id";
        BigDecimal currentTemperature = new BigDecimal("34.5");

        when(repository.getById(patientId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> medicalService.checkTemperature(patientId, currentTemperature));
    }
}