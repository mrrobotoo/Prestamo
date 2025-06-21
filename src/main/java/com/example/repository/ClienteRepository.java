package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.model.Cliente;

@Repository
public class ClienteRepository implements GenericRepository<Cliente, String> {
    private final List<Cliente> clientes = new ArrayList<>();

  
    public Cliente save(Cliente cliente) {
        var id = cliente.id() != null ? cliente.id() : UUID.randomUUID().toString();
        var saved = new Cliente(id, cliente.tipoCliente());
        clientes.add(saved);
        return saved;
    }

    
    public Optional<Cliente> findById(String id) {
        return clientes.stream()
                .filter(c -> c.id().equals(id))
                .findFirst();
    }

    
    public List<Cliente> findAll() {
        return new ArrayList<>(clientes);
    }

    public void update(Cliente cliente, String id) {
        clientes.removeIf(c -> c.id().equals(id));
        var updated = new Cliente(id, cliente.tipoCliente());
        clientes.add(updated);
    }

    
    public void deleteById(String id) {
        clientes.removeIf(c -> c.id().equals(id));
    }
}