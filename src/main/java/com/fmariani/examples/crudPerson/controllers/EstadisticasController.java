package com.fmariani.examples.crudPerson.controllers;

import com.fmariani.examples.crudPerson.dtos.EstadisticasDto;
import com.fmariani.examples.crudPerson.models.Persona;
import com.fmariani.examples.crudPerson.service.PersonaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by florencia on 10/06/21.
 */
@RestController
@RequestMapping("/estadisticas")
public class EstadisticasController {

    private final Logger log = LoggerFactory.getLogger( this.getClass() );

    @Autowired
    PersonaRepository personaRepository;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<EstadisticasDto> getEstadisticas() {
        log.debug( "Accediendo a getEstadisticas()");
        long countSexoF = personaRepository.countSexoF();
        long countSexoM = personaRepository.countSexoM();
        long countPaisArgentina = personaRepository.countPaisArgentina();
        long count = personaRepository.count();

        return ResponseEntity.ok().body( new EstadisticasDto( countSexoF, countSexoM, countPaisArgentina * 100/count ) );
    }
}
