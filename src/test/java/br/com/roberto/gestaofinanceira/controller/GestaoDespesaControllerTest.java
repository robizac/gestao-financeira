package br.com.roberto.gestaofinanceira.controller;

import br.com.roberto.gestaofinanceira.entity.Despesa;
import br.com.roberto.gestaofinanceira.usecase.BuscarDespesaUseCase;
import br.com.roberto.gestaofinanceira.usecase.CadastroDespesaUseCase;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestaoDespesaControllerTest {

    @Mock
    private CadastroDespesaUseCase cadastroDespesaUseCase;

    @Mock
    private BuscarDespesaUseCase buscarDespesaUseCase;

    @InjectMocks
    private GestaoDespesaController controller;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testCreateSuccess() {
        Despesa despesa = new Despesa();
        despesa.setValor(100.0);

        when(cadastroDespesaUseCase.execute(despesa)).thenReturn(despesa);

        ResponseEntity<Despesa> response = controller.create(despesa);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(despesa, response.getBody());
        verify(cadastroDespesaUseCase, times(1)).execute(despesa);
    }

    @Test
    void testCreateFailure() {
        Despesa despesa = new Despesa();

        when(cadastroDespesaUseCase.execute(despesa)).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<Despesa> response = controller.create(despesa);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(cadastroDespesaUseCase, times(1)).execute(despesa);
    }

    @Test
    void testBuscarComResultados() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Despesa despesa = new Despesa();
        despesa.setValor(50.0);

        Page<Despesa> pagina = new PageImpl<>(List.of(despesa), pageable, 1);

        when(buscarDespesaUseCase.execute(pageable)).thenReturn(pagina);

        ResponseEntity<Page<Despesa>> response = controller.buscar(page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(despesa, response.getBody().getContent().get(0));

        verify(buscarDespesaUseCase, times(1)).execute(pageable);
    }

    @Test
    void testBuscarSemResultados() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Page<Despesa> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(buscarDespesaUseCase.execute(pageable)).thenReturn(emptyPage);

        ResponseEntity<Page<Despesa>> response = controller.buscar(page, size);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(buscarDespesaUseCase, times(1)).execute(pageable);
    }

    @Test
    void testBuscarErroInterno() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        when(buscarDespesaUseCase.execute(pageable)).thenThrow(new RuntimeException("Erro inesperado"));

        ResponseEntity<Page<Despesa>> response = controller.buscar(page, size);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        verify(buscarDespesaUseCase, times(1)).execute(pageable);
    }
}
