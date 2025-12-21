package com.hidrored.aplicacion.usuarios;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hidrored.dominio.usuarios.IUsuarioRepository;
import com.hidrored.dominio.usuarios.modelo.Usuario;

public class UsuarioApplicationServiceTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioApplicationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarUsuario_DeberiaGuardarUsuarioExitosamente() {
        // 1. Arrange (Preparar) - Según patrón del laboratorio 
        RegistrarUsuarioCommand command = new RegistrarUsuarioCommand("Pepe", "pepe@mail.com", "999888777", "pass123");
        
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. Act (Acción)
        UsuarioDTO resultado = service.registrarUsuario(command);

        // 3. Assert (Verificar)
        assertNotNull(resultado);
        assertEquals("pepe@mail.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).save(any());
    }
}