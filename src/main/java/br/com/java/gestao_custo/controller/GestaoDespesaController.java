package br.com.java.gestao_custo.controller;

import br.com.java.gestao_custo.entity.Despesa;
import br.com.java.gestao_custo.useCase.BuscarDespesaPorDataUseCase;
import br.com.java.gestao_custo.useCase.CadastroDespesaUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/gestao")
public class GestaoDespesaController {

    @Autowired
    private CadastroDespesaUseCase cadastroDespesaUseCase;

    @Autowired
    private BuscarDespesaPorDataUseCase buscarDespesaPorDataUseCase;

    @PostMapping("/create")
    public ResponseEntity<Despesa> create(@RequestBody Despesa despesa) {
        try {
            Despesa criada = cadastroDespesaUseCase.execute(despesa);
            return ResponseEntity.status(HttpStatus.CREATED).body(criada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar-por-data")
    public ResponseEntity<List<Despesa>> buscarPorData(@RequestParam String data) {
        try {
            LocalDate dataFormatada = LocalDate.parse(data); // formato esperado: yyyy-MM-dd
            List<Despesa> despesas = buscarDespesaPorDataUseCase.execute(dataFormatada);

            if (despesas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(despesas);

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
