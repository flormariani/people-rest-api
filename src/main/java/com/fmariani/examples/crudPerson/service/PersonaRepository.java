package com.fmariani.examples.crudPerson.service;

import java.util.List;

import com.fmariani.examples.crudPerson.models.Persona;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by florencia on 08/06/21.
 */
public interface PersonaRepository extends PagingAndSortingRepository<Persona, Long> {
    List<Persona> findByTipoDocAndNroDocAndPaisAndSexo(String tipoDoc, int nroDoc, String pais, String sexo);

    List<Persona> findByIdPadre(Long idPadre);

    @Query("select count(p) from Persona p where p.sexo ='F'")
    long countSexoF();

    @Query("select count(p) from Persona p where p.sexo ='M'")
    long countSexoM();

    @Query("select count(p) from Persona p where p.pais ='Argentina'")
    long countPaisArgentina();

}
