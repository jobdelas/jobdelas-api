package com.jobdelas.jobdelas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Postagens {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuarios usuarios;

    private String conteudo;
    private String categoria;

    
    public Postagens(Long id, Usuarios usuarios, String conteudo, String categoria, LocalDateTime data_postagem,
            Integer curtidas_total) {
        this.id = id;
        this.usuarios = usuarios;
        this.conteudo = conteudo;
        this.categoria = categoria;
        this.data_postagem = data_postagem;
        this.curtidas_total = curtidas_total;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    private LocalDateTime data_postagem;
    private Integer curtidas_total;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getData_postagem() {
        return data_postagem;
    }

    public void setData_postagem(LocalDateTime data_postagem) {
        this.data_postagem = data_postagem;
    }

    public Integer getCurtidas_total() {
        return curtidas_total;
    }

    public void setCurtidas_total(Integer curtidas_total) {
        this.curtidas_total = curtidas_total;
    }

    public Postagens() {

}


}
