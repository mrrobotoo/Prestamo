package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.model.Cliente;
import com.example.model.EstadoPrestamo;
import com.example.model.Prestamo;
import com.example.model.TipoCliente;
import com.example.repository.ClienteRepository;
import com.example.repository.PrestamoRepository;

public class PrestamoServiceTest {
	@Mock
    private PrestamoRepository prestamoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private PrestamoService prestamoService;

    private Cliente clienteVIP;
    private Cliente clienteRegular;
    private Prestamo prestamo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteVIP = new Cliente("1", TipoCliente.VIP);
        clienteRegular = new Cliente("2", TipoCliente.REGULAR);
        prestamo = new Prestamo("123", 1000.0, "1", LocalDate.now(), EstadoPrestamo.PENDIENTE);
    }

    @Test
    void testCreatePrestamo() {
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamo);
        var result = prestamoService.create(prestamo);
        assertNotNull(result);
        assertEquals("123", result.getId());
        assertEquals(1000.0, result.getMonto(), 0.01);
        verify(prestamoRepository, times(1)).save(prestamo);
    }

    @Test
    void testGetById_Success() {
        
        when(prestamoRepository.findById("123")).thenReturn(Optional.of(prestamo));

        var result = prestamoService.getById("123");

        assertNotNull(result);
        assertEquals("123", result.getId());
        assertEquals(1000.0, result.getMonto(), 0.01);
        verify(prestamoRepository, times(1)).findById("123");
    }

    @Test
    void testGetById_NotFound() {
        when(prestamoRepository.findById("999")).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> prestamoService.getById("999"));
        assertEquals("Entity not found with id 999", exception.getMessage());
        verify(prestamoRepository, times(1)).findById("999");
    }

    
    @Test
    void testGetAll_EmptyList() {
       
        when(prestamoRepository.findAll()).thenReturn(List.of());

        var result = prestamoService.getAll();

        assertTrue(result.isEmpty());
        verify(prestamoRepository, times(1)).findAll();
    }

    @Test
    void testUpdatePrestamo_Success() {
        var updatedPrestamo = new Prestamo("123", 1500.0, "1", LocalDate.now(), EstadoPrestamo.PAGADO);
        when(prestamoRepository.findById("123")).thenReturn(Optional.of(prestamo));

        prestamoService.update(updatedPrestamo, "123");

        verify(prestamoRepository, times(1)).update(updatedPrestamo, "123");
    }

    @Test
    void testUpdatePrestamo_NotFound() {
        when(prestamoRepository.findById("999")).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> prestamoService.getById("999"));
        assertEquals("Entity not found with id 999", exception.getMessage());
        verify(prestamoRepository, times(1)).findById("999");
    }

  

    @Test
    void testCalcularTotal_VIP() {
        when(prestamoRepository.findById("123")).thenReturn(Optional.of(prestamo));
        when(clienteRepository.findById("1")).thenReturn(Optional.of(clienteVIP));

        var total = prestamoService.calcularTotal("123");

        assertEquals(1000.0 * 1.05, total, 0.01); // 5% interest for VIP
        verify(prestamoRepository, times(1)).findById("123");
        verify(clienteRepository, times(1)).findById("1");
    }

    @Test
    void testCalcularTotal_Regular() {
        var prestamoRegular = new Prestamo("124", 1000.0, "2", LocalDate.now(), EstadoPrestamo.PENDIENTE);
        when(prestamoRepository.findById("124")).thenReturn(Optional.of(prestamoRegular));
        when(clienteRepository.findById("2")).thenReturn(Optional.of(clienteRegular));

        var total = prestamoService.calcularTotal("124");

        assertEquals(1000.0 * 1.10, total, 0.01); // 10% interest for REGULAR
        verify(prestamoRepository, times(1)).findById("124");
        verify(clienteRepository, times(1)).findById("2");
    }

    @Test
    void testCalcularTotal_PrestamoNotFound() {
        when(prestamoRepository.findById("999")).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> prestamoService.calcularTotal("999"));
        assertEquals("Entity not found with id 999", exception.getMessage());
        verify(prestamoRepository, times(1)).findById("999");
    }

    @Test
    void testCalcularTotal_ClienteNotFound() {
        when(prestamoRepository.findById("123")).thenReturn(Optional.of(prestamo));
        when(clienteRepository.findById("1")).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> prestamoService.calcularTotal("123"));
        assertEquals("Cliente not found", exception.getMessage());
        verify(prestamoRepository, times(1)).findById("123");
        verify(clienteRepository, times(1)).findById("1");
    }
}
