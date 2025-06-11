package br.com.roberto.gestaofinanceira.controller;

import br.com.roberto.gestaofinanceira.entity.Despesa;
import br.com.roberto.gestaofinanceira.usecase.BuscarDespesaPorDataUseCase;
import br.com.roberto.gestaofinanceira.usecase.CadastroDespesaUseCase;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestaoDespesaControllerTest {

    @Mock
    private CadastroDespesaUseCase cadastroDespesaUseCase;

    @Mock
    private BuscarDespesaPorDataUseCase buscarDespesaPorDataUseCase;

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
    void testBuscarPorDataSuccess() {
        LocalDate data = LocalDate.of(2025, 6, 11);
        Despesa despesa = new Despesa();
        despesa.setValor(50.0);

        List<Despesa> despesas = List.of(despesa);

        when(buscarDespesaPorDataUseCase.execute(data)).thenReturn(despesas);

        ResponseEntity<List<Despesa>> response = controller.buscarPorData(data.toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(despesas, response.getBody());
        verify(buscarDespesaPorDataUseCase, times(1)).execute(data);
    }

    @Test
    void testBuscarPorDataNoContent() {
        LocalDate data = LocalDate.of(2025, 6, 11);

        when(buscarDespesaPorDataUseCase.execute(data)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Despesa>> response = controller.buscarPorData(data.toString());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(buscarDespesaPorDataUseCase, times(1)).execute(data);
    }

    @Test
    void testBuscarPorDataBadRequest() {
        String dataInvalida = "2025-13-40";

        ResponseEntity<List<Despesa>> response = controller.buscarPorData(dataInvalida);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verifyNoInteractions(buscarDespesaPorDataUseCase);
    }

    @Test
    void testBuscarPorDataInternalServerError() {
        LocalDate data = LocalDate.of(2025, 6, 11);

        when(buscarDespesaPorDataUseCase.execute(data)).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<List<Despesa>> response = controller.buscarPorData(data.toString());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(buscarDespesaPorDataUseCase, times(1)).execute(data);
    }
}
