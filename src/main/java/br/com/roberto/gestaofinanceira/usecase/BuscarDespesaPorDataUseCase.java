package br.com.roberto.gestaofinanceira.usecase;

import br.com.roberto.gestaofinanceira.entity.Despesa;
import br.com.roberto.gestaofinanceira.repository.DespesaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BuscarDespesaPorDataUseCase {

    private final DespesaRepository despesaRepository;

    public BuscarDespesaPorDataUseCase(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public List<Despesa> execute(LocalDate data) {
        return despesaRepository.findByData(data);
    }
}