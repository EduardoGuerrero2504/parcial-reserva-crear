package reserva.usecase;

import reserva.exception.PersistenceException;
import reserva.exception.ReservationAlreadyExistsException;
import reserva.modelo.Reserva;
import reserva.output.ReservationRepository;

import java.time.LocalDate;
import java.util.UUID;

public class CreateReservationUseCase {

    private ReservationRepository repository;

    public CreateReservationUseCase(ReservationRepository repository) {
        this.repository = repository;
    }

    public Reserva ejecutar(
            UUID id,
            String email,
            LocalDate fecha,
            int cantidadPersonas
    ) {


        if (repository.existeReservaActiva(email, fecha)) {
            throw new ReservationAlreadyExistsException(
                    "Ya existe una reserva activa para ese email y fecha"
            );
        }


        Reserva reserva = Reserva.instance(
                id,
                email,
                fecha,
                cantidadPersonas,
                "Activa"
        );


        if (!repository.guardar(reserva)) {
            throw new PersistenceException(
                    "No se pudo guardar la reserva"
            );
        }


        return reserva;
    }
}
