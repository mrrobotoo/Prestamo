package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Prestamo;
import com.example.service.PrestamoService;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    
	private final PrestamoService service;

    public PrestamoController(PrestamoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Prestamo> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getById(@PathVariable String id) {
        var prestamo = service.getById(id);
        return ResponseEntity.ok(prestamo);
    }

    @PostMapping
    public ResponseEntity<Prestamo> create(@RequestBody Prestamo prestamo) {
        var saved = service.create(prestamo);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> update(@PathVariable String id, @RequestBody Prestamo prestamo) {
        service.update(prestamo, id);
        return ResponseEntity.ok(prestamo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/total")
    public ResponseEntity<Map<String, Double>> calcularTotal(@PathVariable String id) {
        var total = service.calcularTotal(id);
        return ResponseEntity.ok(Map.of("total", total));
    }
}