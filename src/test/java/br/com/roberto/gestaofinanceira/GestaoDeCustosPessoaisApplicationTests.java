package br.com.roberto.gestaofinanceira;

import br.com.roberto.gestaofinanceira.entity.Despesa;
import br.com.roberto.gestaofinanceira.repository.DespesaRepository;
import br.com.roberto.gestaofinanceira.usecase.CadastroDespesaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastroDespesaUseCaseTest {

	private DespesaRepository despesaRepository;
	private CadastroDespesaUseCase cadastroDespesaUseCase;

	@BeforeEach
	void setUp() {
		despesaRepository = mock(DespesaRepository.class);
		cadastroDespesaUseCase = new CadastroDespesaUseCase(despesaRepository);
	}

	@Test
	void deveCadastrarDespesaComSucesso() {
		// Arrange
		Despesa despesa = new Despesa();
		despesa.setDescricao("Conta de Luz");
		despesa.setValor(120.50);
		// simula o save retornando a mesma despesa
		when(despesaRepository.save(despesa)).thenReturn(despesa);

		// Act
		Despesa resultado = cadastroDespesaUseCase.execute(despesa);

		// Assert
		assertNotNull(resultado);
		assertEquals("Conta de Luz", resultado.getDescricao());
		assertEquals(120.50, resultado.getValor());
		verify(despesaRepository, times(1)).save(despesa);
	}
}