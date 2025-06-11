package br.com.java.gestao_custo.useCase;

import br.com.java.gestao_custo.entity.Despesa;
import br.com.java.gestao_custo.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroDespesaUseCase {

    @Autowired
    private DespesaRepository despesaRepository;

    //SOLID Responsabilidade unica
    public Despesa execute(Despesa despesa){
        despesa = despesaRepository.save(despesa);
        return  despesa;
    }
}
