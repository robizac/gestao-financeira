package br.com.roberto.gestaofinanceira.usecase;

import br.com.roberto.gestaofinanceira.entity.Despesa;
import br.com.roberto.gestaofinanceira.repository.DespesaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BuscarDespesaUseCase {

    private final DespesaRepository despesaRepository;

    public BuscarDespesaUseCase(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public Page<Despesa> execute(Pageable pageable) {
        return despesaRepository.findAll(pageable);
    }
}