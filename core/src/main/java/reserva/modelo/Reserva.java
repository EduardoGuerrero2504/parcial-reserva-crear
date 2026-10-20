package reserva.modelo;

import reserva.exception.ReservaInvalidaException;

import java.time.LocalDate;
import java.util.UUID;

public class Reserva {


        private UUID id;
        private String emailContacto;
        private LocalDate fecha;
        private int cantidadPersonas;
        private String estado;

        private Reserva(UUID id, String emailContacto, LocalDate fecha,
                        int cantidadPersonas, String estado) {
            this.id = id;
            this.emailContacto = emailContacto;
            this.fecha = fecha;
            this.cantidadPersonas = cantidadPersonas;
            this.estado = estado;
        }

        public static Reserva instance (UUID id, String emailContacto,
                                       LocalDate fecha, int cantidadPersonas,
                                       String estado) {

            if (id == null) {
                throw new ReservaInvalidaException("El id es obligatorio");
            }

            if (emailContacto == null || emailContacto.trim().isEmpty()) {
                throw new ReservaInvalidaException("El email es obligatorio");
            }

            if (fecha == null) {
                throw new ReservaInvalidaException("La fecha es obligatoria");
            }

            if (cantidadPersonas <= 0) {
                throw new ReservaInvalidaException(
                        "La cantidad de personas debe ser mayor que cero"
                );
            }

            return new Reserva(id, emailContacto, fecha,
                    cantidadPersonas, estado);
        }

        public UUID getId() {
            return id;
        }

        public String getEmailContacto() {
            return emailContacto;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public int getCantidadPersonas() {
            return cantidadPersonas;
        }

        public String getEstado() {
            return estado;
        }
}
