package br.com.roberto.gestaofinanceira.controller;

import br.com.roberto.gestaofinanceira.entity.Despesa;
import br.com.roberto.gestaofinanceira.usecase.BuscarDespesaUseCase;
import br.com.roberto.gestaofinanceira.usecase.CadastroDespesaUseCase;

import jakarta.validation.constraints.Min;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/gestao")
public class GestaoDespesaController {

    private final CadastroDespesaUseCase cadastroDespesaUseCase;
    private final BuscarDespesaUseCase buscarDespesaUseCase;

    public GestaoDespesaController(CadastroDespesaUseCase cadastroDespesaUseCase,
                                   BuscarDespesaUseCase buscarDespesaPorDataUseCase) {
        this.cadastroDespesaUseCase = cadastroDespesaUseCase;
        this.buscarDespesaUseCase = buscarDespesaPorDataUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<Despesa> create(@RequestBody Despesa despesa) {
        try {
            Despesa criada = cadastroDespesaUseCase.execute(despesa);
            return ResponseEntity.status(HttpStatus.CREATED).body(criada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar")
    @Cacheable(value = "despesas", key = "#page + '-' + #size")
    public ResponseEntity<Page<Despesa>> buscar(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {

        try {
            System.out.println("Executando método buscar com page=" + page + ", size=" + size);

            Pageable pageable = PageRequest.of(page, size);
            Page<Despesa> despesas = buscarDespesaUseCase.execute(pageable); // renomeado aqui

            if (despesas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(despesas);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}