package br.com.roberto.gestaofinanceira.repository;

import br.com.roberto.gestaofinanceira.entity.Despesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    Page<Despesa> findAll( Pageable pageable);
}
