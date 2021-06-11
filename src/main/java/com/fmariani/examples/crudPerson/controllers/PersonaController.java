package com.fmariani.examples.crudPerson.controllers;

import com.fmariani.examples.crudPerson.dtos.PersonaDto;
import com.fmariani.examples.crudPerson.dtos.ResponseDto;
import com.fmariani.examples.crudPerson.models.Persona;
import com.fmariani.examples.crudPerson.service.PersonaRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by florencia on 08/06/21.
 */
@RestController
@RequestMapping("/personas")
public class PersonaController {
    private final Logger log = LoggerFactory.getLogger( this.getClass() );

    @Autowired
    PersonaRepository personaRepository;

    @ApiOperation(value = "List all persons", response = Iterable.class)
    @GetMapping(value = {"", "/"})
    public Iterable<Persona> retrieveAllStudents() {
        return personaRepository.findAll();
    }


    @ApiOperation(value = "Search a person with an id", response = Persona.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Persona> get(@PathVariable(value = "id") Long personId) {
        log.debug( "Accediendo a get() con id = {}", personId );
        Optional<Persona> person = personaRepository.findById( personId );
        if (!person.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body( person.get() );
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<?> create(@Valid @RequestBody PersonaDto personaDto) {
        log.debug( "Accediendo a create() con Persona = {}", personaDto.toString() );
        if (loadPerson( personaDto.getTipoDoc(), personaDto.getNroDoc(), personaDto.getPais(), personaDto.getSexo() ).isPresent()) {
            return ResponseEntity.status( HttpStatus.CONFLICT ).build();
        }
        //Valido que el padre existe
        if (personaDto.getIdPadre() != null && !personaRepository.findById( personaDto.getIdPadre() ).isPresent()) {
            return ResponseEntity.badRequest().body( new ResponseDto( HttpStatus.BAD_REQUEST.getReasonPhrase(), "El idPadre no se encuentra en la DB" ) );
        }
        //Valido la edad
        if (personaDto.getEdad() < 18) {
            return ResponseEntity.badRequest().body( new ResponseDto( HttpStatus.BAD_REQUEST.getReasonPhrase(), "Debe ser mayor de 18" ) );
        }

        Persona persona = new Persona();
        persona.setContacto( personaDto.getContacto() );
        persona.setNroDoc( personaDto.getNroDoc() );
        persona.setPais( personaDto.getPais() );
        persona.setSexo( personaDto.getSexo() );
        persona.setTipoDoc( personaDto.getTipoDoc() );
        persona.setFechaNacimiento( personaDto.getFechaNacimiento() );
        persona.setIdPadre( personaDto.getIdPadre() );
        persona.setNombre( personaDto.getNombre() );

        personaRepository.save( persona );
        return ResponseEntity.ok().body( persona );
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long personId,
                                    @Valid @RequestBody PersonaDto personDetails) {

        log.debug( "Accediendo a update() con Persona = {}", personDetails.toString() );
        Optional<Persona> person = personaRepository.findById( personId );

        if (!person.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (personId.equals( personDetails.getIdPadre() )) {
            return ResponseEntity.badRequest().body( new ResponseDto( HttpStatus.BAD_REQUEST.getReasonPhrase(), "El idPadre no puede ser igual al id de la persona" ) );
        }
        //Valido que el padre existe
        if (personDetails.getIdPadre() != null && !personaRepository.findById( personDetails.getIdPadre() ).isPresent()) {
            return ResponseEntity.badRequest().body( new ResponseDto( HttpStatus.BAD_REQUEST.getReasonPhrase(), "El idPadre no se encuentra en la DB" ) );
        }
        //Valido la edad
        if (personDetails.getEdad() < 18) {
            return ResponseEntity.badRequest().body( new ResponseDto( HttpStatus.BAD_REQUEST.getReasonPhrase(), "Debe ser mayor de 18" ) );
        }

        // Verifico que no existan dos personas con el mismo tipoDoc, nroDoc, sexo y pais
        Optional<Persona> pDuplicated = this.loadPerson( personDetails.getTipoDoc(), personDetails.getNroDoc(), personDetails.getPais(), personDetails.getSexo() );
        if (pDuplicated.isPresent() && pDuplicated.get().getId() != personId) {
            return ResponseEntity.status( HttpStatus.CONFLICT ).build();
        }

        person.get().setId( personId );
        person.get().setTipoDoc( personDetails.getTipoDoc() );
        person.get().setNroDoc( personDetails.getNroDoc() );
        person.get().setSexo( personDetails.getSexo() );
        person.get().setContacto( personDetails.getContacto() );
        person.get().setFechaNacimiento( personDetails.getFechaNacimiento() );
        person.get().setIdPadre( personDetails.getIdPadre() );
        person.get().setNombre( personDetails.getNombre() );

        Persona updatedPersona = personaRepository.save( person.get() );
        return ResponseEntity.ok( updatedPersona );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long personId) {
        log.debug( "Accediendo a delete() con person = {}", personId );
        Optional<Persona> person = personaRepository.findById( personId );
        if (!person.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        //No puede eliminarse la persona si es padre de alguna otra
        if (!personaRepository.findByIdPadre( personId ).isEmpty()) {
            return ResponseEntity.badRequest().body( new ResponseDto( HttpStatus.CONFLICT.getReasonPhrase(), "La entidad no puede eliminarse porque está siendo referenciada por otra" ) );
        }
        personaRepository.delete( person.get() );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id1}/padre/{id2}")
    public ResponseEntity<?> getIsFather(@PathVariable(value = "id1") Long id1, @PathVariable(value = "id2") Long id2) {
        log.debug( "Accediendo a getIsFather() con id1 = {} y id2 = {}", id1, id2 );
        Optional<Persona> p2 = personaRepository.findById( id2 );
        if (p2.isPresent() && p2.get().getIdPadre() != null && p2.get().getIdPadre().equals( id1 )) {
            return ResponseEntity.ok( p2.get() );
        }
        return ResponseEntity.badRequest().body( new ResponseDto( HttpStatus.BAD_REQUEST.getReasonPhrase(), id1 + " no es padre de " + id2 ) );
    }

    private Optional<Persona> loadPerson(String tipoDoc, int nroDoc, String pais, String sexo) {
        List<Persona> people = personaRepository.findByTipoDocAndNroDocAndPaisAndSexo( tipoDoc, nroDoc, pais, sexo );
        if (!people.isEmpty()) {
            return Optional.of( people.get( 0 ) );
        }
        return Optional.empty();
    }


}
