package kamilceglinski.wit.greathealth.service;

import java.time.LocalDateTime;
import java.time.Month;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PatientServiceTest {

    @Test
    public void isInCollision() {
        assertTrue(PatientService.isInCollision(
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 30, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 20, 0)
        ));
        assertTrue(PatientService.isInCollision(
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 30, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 10, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 20, 0)
        ));
        assertTrue(PatientService.isInCollision(
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 30, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 10, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 12, 0, 0)
        ));
        assertTrue(PatientService.isInCollision(
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 30, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 10, 30, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 10, 0)
        ));
        assertTrue(PatientService.isInCollision(
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 30, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 20, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 40, 0)
        ));
        assertFalse(PatientService.isInCollision(
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 10, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 12, 0, 0)
        ));
        assertFalse(PatientService.isInCollision(
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 12, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 10, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 0, 0)
        ));
        assertFalse(PatientService.isInCollision(
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 10, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 12, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 13, 0, 0)
        ));
        assertFalse(PatientService.isInCollision(
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 12, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 13, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 10, 0, 0),
            LocalDateTime.of(2025, Month.FEBRUARY, 23, 11, 0, 0)
        ));
    }
}