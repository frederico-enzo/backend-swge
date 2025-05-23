package br.com.uniamerica.estacionamento.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
@Table(name = "movimentacoes", schema = "public")
public class Movimentacao extends AbstractEntity {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false, unique = true)
    private Long id;

    @Getter @Setter
    @NotNull(message = "Informe a Data de Entrada")
    @Column(name = "data_entrada", nullable = false)
    private LocalDateTime dataEntrada;

    @Getter @Setter
    @Column(name = "data_saida")
    private LocalDateTime dataSaida;

    @Getter @Setter
    @Column(name = "tempo_estacionado")
    private long tempoEstacionadoSegundos;

    @Getter @Setter
    @Column(name = "tempo_multa")
    private long tempoMultaSegundos;

    @Getter @Setter
    @Column(name = "tempo_desconto")
    private long tempoDescontoSegundos;

    @NotNull(message = "O Veiculo estacionado deve ser informado!")
    @Getter @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Veiculo veiculo;

    @NotNull(message = "O Condutor do Veiculo estacionado deve ser informado!")
    @Getter @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Condutor condutor;

    @Getter @Setter
    @Column(name = "valor_hora")
    private BigDecimal valorHora;

    @Getter @Setter
    @Column(name = "valor_multa")
    private BigDecimal valorMulta;


    @Getter @Setter
    @Column(name = "valor_total")
    private BigDecimal valorTotal;
}