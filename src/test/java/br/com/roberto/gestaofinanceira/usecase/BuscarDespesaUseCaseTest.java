package br.com.roberto.gestaofinanceira.usecase;

import br.com.roberto.gestaofinanceira.entity.Despesa;
import br.com.roberto.gestaofinanceira.repository.DespesaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BuscarDespesaUseCaseTest {

    private DespesaRepository despesaRepository;
    private BuscarDespesaUseCase useCase;

    @BeforeEach
    void setUp() {
        despesaRepository = mock(DespesaRepository.class);
        useCase = new BuscarDespesaUseCase(despesaRepository);
    }

    @Test
    void deveRetornarPaginaDeDespesas() {
        // Arrange
        LocalDate dataConsulta = LocalDate.of(2025, 6, 11);
        Pageable pageable = PageRequest.of(0, 10);

        Despesa despesa1 = new Despesa();
        despesa1.setId(UUID.randomUUID());
        despesa1.setDescricao("Aluguel");
        despesa1.setData(dataConsulta);
        despesa1.setValor(1500.0);

        Despesa despesa2 = new Despesa();
        despesa2.setId(UUID.randomUUID());
        despesa2.setDescricao("Energia");
        despesa2.setData(dataConsulta);
        despesa2.setValor(200.0);

        List<Despesa> despesasEsperadas = Arrays.asList(despesa1, despesa2);
        Page<Despesa> pageEsperada = new PageImpl<>(despesasEsperadas, pageable, despesasEsperadas.size());

        when(despesaRepository.findAll(pageable)).thenReturn(pageEsperada);

        // Act
        Page<Despesa> resultado = useCase.execute(pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        assertEquals("Aluguel", resultado.getContent().get(0).getDescricao());
        assertEquals("Energia", resultado.getContent().get(1).getDescricao());

        verify(despesaRepository, times(1)).findAll(pageable);
    }
}
