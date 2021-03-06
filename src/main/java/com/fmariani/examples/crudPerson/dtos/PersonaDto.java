package com.fmariani.examples.crudPerson.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.Period;

/**
 * Created by florencia on 08/06/21.
 */
public class PersonaDto {
    @NotNull
    @JsonProperty("tipo_doc")
    private String tipoDoc;

    @NotNull
    @JsonProperty("nombre")
    private String nombre;

    @NotNull
    @JsonProperty("nro_doc")
    private int nroDoc;

    @NotNull
    @JsonProperty("pais")
    private String pais;

    @NotNull
    @Pattern(regexp = "F|M")
    @JsonProperty("sexo")
    private String sexo;

    @NotNull
    @JsonProperty("contacto")
    private String contacto;

    @JsonProperty("id_padre")
    private Long idPadre;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonProperty("fecha_nacimiento")
    private LocalDate fechaNacimiento;

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public int getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(int nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        Period diff = Period.between( fechaNacimiento, LocalDate.now() );
        return diff.getYears();
    }

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString( this );
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException( jpe );
        }
    }

}

