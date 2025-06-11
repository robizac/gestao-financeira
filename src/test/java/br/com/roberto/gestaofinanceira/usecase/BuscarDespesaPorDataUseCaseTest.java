package br.com.roberto.gestaofinanceira.usecase;

import br.com.roberto.gestaofinanceira.entity.Despesa;
import br.com.roberto.gestaofinanceira.repository.DespesaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BuscarDespesaPorDataUseCaseTest {

    private DespesaRepository despesaRepository;
    private BuscarDespesaPorDataUseCase useCase;

    @BeforeEach
    void setUp() {
        despesaRepository = mock(DespesaRepository.class);
        useCase = new BuscarDespesaPorDataUseCase(despesaRepository);
    }

    @Test
    void deveRetornarListaDeDespesasParaDataInformada() {
        // Arrange
        LocalDate dataConsulta = LocalDate.of(2025, 6, 11);

        Despesa despesa1 = new Despesa();
        despesa1.setId(UUID.randomUUID()); // gerando UUID manualmente para o teste
        despesa1.setDescricao("Aluguel");
        despesa1.setData(dataConsulta);
        despesa1.setValor(1500.0);

        Despesa despesa2 = new Despesa();
        despesa2.setId(UUID.randomUUID());
        despesa2.setDescricao("Energia");
        despesa2.setData(dataConsulta);
        despesa2.setValor(200.0);

        List<Despesa> despesasEsperadas = Arrays.asList(despesa1, despesa2);

        when(despesaRepository.findByData(dataConsulta)).thenReturn(despesasEsperadas);

        // Act
        List<Despesa> resultado = useCase.execute(dataConsulta);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Aluguel", resultado.get(0).getDescricao());
        assertEquals("Energia", resultado.get(1).getDescricao());

        verify(despesaRepository, times(1)).findByData(dataConsulta);
    }
}
