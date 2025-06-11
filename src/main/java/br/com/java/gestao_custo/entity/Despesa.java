package br.com.java.gestao_custo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "despesa")
@Data
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String descricao;
    private LocalDate data;
    private Double valor;
    @CreationTimestamp
    private LocalDateTime dataCriacao;
}