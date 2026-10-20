package usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reserva.exception.PersistenceException;
import reserva.exception.ReservationAlreadyExistsException;
import reserva.modelo.Reserva;
import reserva.output.ReservationRepository;
import reserva.usecase.CreateReservationUseCase;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateReservationUseCaseTest {

    @Mock
    private ReservationRepository repository;

    @Test
    void ejecutar_datosValidos_creaReservaCorrectamente() {


        UUID id = UUID.randomUUID();
        String email = "eduardo@gmail.com";
        LocalDate fecha = LocalDate.of(2026, 10, 20);
        int cantidadPersonas = 2;

        when(repository.existeReservaActiva(email, fecha)).thenReturn(false);
        when(repository.guardar(any(Reserva.class))).thenReturn(true);

        CreateReservationUseCase useCase =
                new CreateReservationUseCase(repository);


        Reserva reserva = useCase.ejecutar(
                id,
                email,
                fecha,
                cantidadPersonas
        );


        assertNotNull(reserva);
        assertEquals(id, reserva.getId());
        assertEquals(email, reserva.getEmailContacto());
        assertEquals(fecha, reserva.getFecha());
        assertEquals(cantidadPersonas, reserva.getCantidadPersonas());
        assertEquals("Activa", reserva.getEstado());

        verify(repository).existeReservaActiva(email, fecha);
        verify(repository).guardar(any(Reserva.class));
    }

    @Test
    void ejecutar_reservaActivaExistente_lanzaExcepcion() {


        UUID id = UUID.randomUUID();
        String email = "eduardo@gmail.com";
        LocalDate fecha = LocalDate.of(2026, 10, 20);

        when(repository.existeReservaActiva(email, fecha)).thenReturn(true);

        CreateReservationUseCase useCase =
                new CreateReservationUseCase(repository);


        assertThrows(
                ReservationAlreadyExistsException.class,
                () -> useCase.ejecutar(id, email, fecha, 2)
        );

        verify(repository).existeReservaActiva(email, fecha);
        verify(repository, never()).guardar(any(Reserva.class));
    }

    @Test
    void ejecutar_errorAlGuardar_lanzaExcepcion() {


        UUID id = UUID.randomUUID();
        String email = "eduardo@gmail.com";
        LocalDate fecha = LocalDate.of(2026, 10, 20);

        when(repository.existeReservaActiva(email, fecha)).thenReturn(false);
        when(repository.guardar(any(Reserva.class))).thenReturn(false);

        CreateReservationUseCase useCase =
                new CreateReservationUseCase(repository);


        assertThrows(
                PersistenceException.class,
                () -> useCase.ejecutar(id, email, fecha, 2)
        );

        verify(repository).existeReservaActiva(email, fecha);
        verify(repository).guardar(any(Reserva.class));
    }
}
