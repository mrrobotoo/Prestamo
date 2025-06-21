package com.example.service;

import org.springframework.stereotype.Service;

import com.example.model.Prestamo;
import com.example.repository.ClienteRepository;
import com.example.repository.PrestamoRepository;

@Service
public class PrestamoService extends GenericService<Prestamo, String> {
    private final ClienteRepository clienteRepository;

    public PrestamoService(PrestamoRepository repository, ClienteRepository clienteRepository) {
        super(repository);
        this.clienteRepository = clienteRepository;
    }

    public double calcularTotal(String id) {
        var prestamo = getById(id);
        var cliente = clienteRepository.findById(prestamo.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente not found"));
        var tasa = switch (cliente.tipoCliente()) {
            case VIP -> 0.05; // 5% interest
            case REGULAR -> 0.10; // 10% interest
        };
        return prestamo.getMonto() * (1 + tasa);
    }
}
