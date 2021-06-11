package com.fmariani.examples.crudPerson.dtos;

/**
 * Created by florencia on 10/06/21.
 */
public class EstadisticasDto {

    private long cantMujeres;
    private long cantHombres;
    private long porcArgentinos;

    public EstadisticasDto() {
    }

    public EstadisticasDto(long cantMujeres, long cantHombres, long porcArgentinos) {
        this.cantMujeres = cantMujeres;
        this.cantHombres = cantHombres;
        this.porcArgentinos = porcArgentinos;
    }

    public long getCantMujeres() {
        return cantMujeres;
    }

    public void setCantMujeres(long cantMujeres) {
        this.cantMujeres = cantMujeres;
    }

    public long getCantHombres() {
        return cantHombres;
    }

    public void setCantHombres(long cantHombres) {
        this.cantHombres = cantHombres;
    }

    public long getPorcArgentinos() {
        return porcArgentinos;
    }

    public void setPorcArgentinos(long porcArgentinos) {
        this.porcArgentinos = porcArgentinos;
    }

}
