package br.com.java.gestao_custo.useCase;

import br.com.java.gestao_custo.entity.Despesa;
import br.com.java.gestao_custo.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BuscarDespesaPorDataUseCase {

    @Autowired
    private DespesaRepository despesaRepository;

    public List<Despesa> execute(LocalDate data) {

        return despesaRepository.findByData(data);
    }
}