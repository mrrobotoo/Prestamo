package com.example.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Prestamo {
	private String id;
    private double monto;
    private String clienteId;
    private LocalDate fecha;
    private EstadoPrestamo estado;
}
