package br.com.java.gestao_custo.repository;

import br.com.java.gestao_custo.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DespesaRepository extends JpaRepository<Despesa, UUID> {

    List<Despesa> findByData(LocalDate data);

}
