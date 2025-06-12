package br.com.roberto.gestaofinanceira.repository;

import br.com.roberto.gestaofinanceira.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
}
