package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.model.EstadoPrestamo;
import com.example.model.Prestamo;

@Repository
public class PrestamoRepository implements GenericRepository<Prestamo, String> {
    private final List<Prestamo> prestamos = new ArrayList<>();

    
    public Prestamo save(Prestamo prestamo) {
        var id = prestamo.getId() != null ? prestamo.getId() : UUID.randomUUID().toString();
        var saved = new Prestamo(id, prestamo.getMonto(), prestamo.getClienteId(), prestamo.getFecha(), prestamo.getEstado());
        prestamos.add(saved);
        return saved;
    }

    
    public Optional<Prestamo> findById(String id) {
        return prestamos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    
    public List<Prestamo> findAll() {
        return prestamos.stream()
                .filter(p -> p.getEstado() == EstadoPrestamo.PENDIENTE)
                .toList();
    }

    
    public void update(Prestamo prestamo, String id) {
        prestamos.removeIf(p -> p.getId().equals(id));
        var updated = new Prestamo(id, prestamo.getMonto(), prestamo.getClienteId(), prestamo.getFecha(), prestamo.getEstado());
        prestamos.add(updated);
    }

    
    public void deleteById(String id) {
        prestamos.removeIf(p -> p.getId().equals(id));
    }
}