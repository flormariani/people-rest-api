package com.fmariani.examples.crudPerson.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

/**
 * Created by florencia on 08/06/21.
 */
@Entity
@Table(name = "PERSONA")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    @JsonProperty("tipo_doc")
    private String tipoDoc;

    @NotNull
    @JsonProperty("nro_doc")
    private int nroDoc;

    @NotNull
    private String pais;

    @NotNull
    private String sexo;

    @NotNull
    private String contacto;

    @JsonProperty("id_padre")
    private Long idPadre;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonProperty("fecha_nacimiento")
    private LocalDate fechaNacimiento;

    public Persona() {
    }

    public Persona(Long id, String nombre, String tipoDoc, int nroDoc, String pais, String sexo, String contacto, LocalDate fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
        this.pais = pais;
        this.sexo = sexo;
        this.contacto = contacto;
        this.fechaNacimiento = fechaNacimiento;
    }

    private Persona(PersonBuilder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.tipoDoc = builder.tipoDoc;
        this.nroDoc = builder.nroDoc;
        this.pais = builder.pais;
        this.sexo = builder.sexo;
        this.contacto = builder.contacto;
        this.idPadre = builder.idPadre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(int nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString( this );
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException( jpe );
        }
    }

    public static class PersonBuilder{
        private Long id;
        private String nombre;
        private String tipoDoc;
        private int nroDoc;
        private String pais;
        private String sexo;
        private String contacto;
        private Long idPadre;

        public PersonBuilder(Long id, String nombre, String tipoDoc, int nroDoc, String pais, String sexo, String contacto) {
            this.id = id;
            this.nombre = nombre;
            this.tipoDoc = tipoDoc;
            this.nroDoc = nroDoc;
            this.pais = pais;
            this.sexo = sexo;
            this.contacto = contacto;
        }

        public PersonBuilder setIdPadre(Long idPadre) {
            this.idPadre = idPadre;
            return this;
        }

        public Persona build(){
            return new Persona(this);
        }
    }

}
