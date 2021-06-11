package com.fmariani.examples.crudPerson.controllers;

import com.fmariani.examples.crudPerson.dtos.ResponseDto;
import com.fmariani.examples.crudPerson.models.Persona;
import com.fmariani.examples.crudPerson.service.PersonaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by florencia on 09/06/21.
 */
@RestController
@RequestMapping("/relaciones")
public class RelacionesController {

    private final Logger log = LoggerFactory.getLogger( this.getClass() );

    @Autowired
    PersonaRepository personaRepository;

    public enum Relacion {
        HERMANOS( "HERMAN@" ), PRIMOS( "PRIM@" ), TIO_TIA( "TI@" ), NORELACIONADOS( "No hay relacion" );

        private final String value;

        private Relacion(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    @GetMapping("/{id1}/{id2}")
    public ResponseEntity<?> getRelaciones(@PathVariable(value = "id1") Long id1, @PathVariable(value = "id2") Long id2) {
        log.debug( "Accediendo a getRelaciones() con id1 = {} y id2 = {}", id1, id2 );

        Relacion relation = getRelacion( id1, id2 );

        if (!relation.equals( Relacion.NORELACIONADOS )) {
            return ResponseEntity.ok(new ResponseDto( HttpStatus.OK.getReasonPhrase(), relation.value() ) );
        }

        return ResponseEntity.badRequest().body( new ResponseDto( HttpStatus.NOT_FOUND.getReasonPhrase(), "No hay relacion" ) );
    }

    private Relacion getRelacion(Long id1, Long id2) {

        Optional<Persona> p1 = personaRepository.findById( id1 );
        Optional<Persona> p2 = personaRepository.findById( id2 );

        if (!p1.isPresent() || !p2.isPresent())
            return Relacion.NORELACIONADOS;

        if (tienenMismoPadre( p1.get(), p2.get() ))
            return Relacion.HERMANOS;

        Optional<Persona> father_p1 = getPadre( p1.get() );
        Optional<Persona> father_p2 = getPadre( p2.get() );

        if (father_p1.isPresent() && father_p2.isPresent()) {
            if (tienenMismoPadre( father_p1.get(), father_p2.get() )) {
                return Relacion.PRIMOS;
            }
            if (tienenMismoPadre( p1.get(), father_p2.get() ) && p1.get().getId() != father_p2.get().getId()) {
                return Relacion.TIO_TIA;
            }
        }

        return Relacion.NORELACIONADOS;
    }

    private boolean tienenMismoPadre(Persona p1, Persona p2) {
        if (p1 != null && p2 != null && p1.getIdPadre() != null && p2.getIdPadre() != null && p1.getIdPadre().equals( p2.getIdPadre() )) {
            return true;
        }
        return false;
    }

    private Optional<Persona> getPadre(Persona p) {
        if (p != null && p.getIdPadre() != null) {
            return personaRepository.findById( p.getIdPadre() );
        }
        return Optional.empty();
    }
}
