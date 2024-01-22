package com.jobdelas.jobdelas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Curtidas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuarios usuarios;

    @ManyToOne
    @JoinColumn(name = "postagem_id")
    private Postagens postagens;

    public Curtidas(Long id, Usuarios usuarios, Postagens postagens) {
        this.id = id;
        this.usuarios = usuarios;
        this.postagens = postagens;
    }

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

    public Postagens getPostagens() {
        return postagens;
    }

    public void setPostagens(Postagens postagens) {
        this.postagens = postagens;
    }

    public Curtidas() {

    }
}
