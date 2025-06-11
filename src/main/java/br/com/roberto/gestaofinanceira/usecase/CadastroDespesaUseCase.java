package br.com.roberto.gestaofinanceira.usecase;

import br.com.roberto.gestaofinanceira.entity.Despesa;
import br.com.roberto.gestaofinanceira.repository.DespesaRepository;
import org.springframework.stereotype.Service;

@Service
public class CadastroDespesaUseCase {
    private final DespesaRepository repository;

    public CadastroDespesaUseCase(DespesaRepository repository) {
        this.repository = repository;
    }

    public Despesa execute(Despesa despesa) {
        return repository.save(despesa);
    }
}