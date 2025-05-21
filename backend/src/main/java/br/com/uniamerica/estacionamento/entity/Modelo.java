package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "modelos", schema = "public")
public class Modelo extends AbstractEntity {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false, unique = true)
    private Long id;
    @NotNull(message = "Nome do modelo deve ser informado!")
    @NotBlank(message = "Nome do modelo foi informado vazio!")
    @Length(min = 2, max = 20, message = "Nome do modelo deve conter entre 2 e 20 caracteres!")
    @Getter @Setter
    @Column(name = "nome", nullable = false, unique = true, length = 20)
    private String nome;

    @NotNull(message = "A Marca do modelo deve ser informada!")
    @Getter @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Marca marca;
}