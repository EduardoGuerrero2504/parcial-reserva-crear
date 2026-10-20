package model;

import org.junit.jupiter.api.Test;
import reserva.exception.ReservaInvalidaException;
import reserva.modelo.Reserva;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservaTest {
    @Test
    void instance_datosValidos_exito() {

        UUID id = UUID.randomUUID();

        Reserva myReserva = Reserva.instance(
                id,
                "edu921953@gmail.com",
                LocalDate.of(2026, 10, 20),
                2,
                "Activa"
        );

        assertEquals(id, myReserva.getId());
        assertEquals("edu921953@gmail.com", myReserva.getEmailContacto());
        assertEquals(LocalDate.of(2026, 10, 20), myReserva.getFecha());
        assertEquals(2, myReserva.getCantidadPersonas());
        assertEquals("Activa", myReserva.getEstado());
    }

    @Test
    void instance_idNull_lanzaExcepcion() {

        assertThrows(
                ReservaInvalidaException.class,
                () -> Reserva.instance(
                        null,
                        "edu921953@gmail.com",
                        LocalDate.of(2026, 10, 20),
                        2,
                        "Activa"
                )
        );
    }

    @Test
    void instance_fechaNull_lanzaExcepcion() {

        assertThrows(
                ReservaInvalidaException.class,
                () -> Reserva.instance(
                        UUID.randomUUID(),
                        "eduardo@gmail.com",
                        null,
                        2,
                        "Activa"
                )
        );
    }

    @Test
    void instance_emailNulo_lanzaExcepcion() {

        assertThrows(
                ReservaInvalidaException.class,
                () -> Reserva.instance(
                        UUID.randomUUID(),
                        null,
                        LocalDate.of(2026, 10, 20),
                        2,
                        "Activa"
                )
        );
    }

    @Test
    void instance_emailVacio_lanzaExcepcion() {

        assertThrows(
                ReservaInvalidaException.class,
                () -> Reserva.instance(
                        UUID.randomUUID(),
                        "",
                        LocalDate.of(2026, 10, 20),
                        2,
                        "Activa"
                )
        );
    }

    @Test
    void instance_cantidadCero_lanzaExcepcion() {

        assertThrows(
                ReservaInvalidaException.class,
                () -> Reserva.instance(
                        UUID.randomUUID(),
                        "eduardo@gmail.com",
                        LocalDate.of(2026, 10, 20),
                        0,
                        "Activa"
                )
        );
    }

    @Test
    void instance_cantidadNegativa_lanzaExcepcion() {

        assertThrows(
                ReservaInvalidaException.class,
                () -> Reserva.instance(
                        UUID.randomUUID(),
                        "eduardo@gmail.com",
                        LocalDate.of(2026, 10, 20),
                        -1,
                        "Activa"
                )
        );
    }

}
