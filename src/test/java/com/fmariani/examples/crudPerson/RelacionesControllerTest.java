package com.fmariani.examples.crudPerson;

import com.fmariani.examples.crudPerson.controllers.RelacionesController;
import com.fmariani.examples.crudPerson.models.Persona;
import com.fmariani.examples.crudPerson.service.PersonaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RelacionesController.class)
public class RelacionesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonaRepository personaRepository;

    Persona mock1 = new Persona.PersonBuilder( 1L, "nombre", "DNI", 28334764, "ARG", "M", "3541333333" )
            .setIdPadre( null ).build();
    Persona mock2 = new Persona.PersonBuilder( 2L, "nombre", "DNI", 28334764, "ARG", "F", "3541333333" )
            .setIdPadre( 1L ).build();
    Persona mock3 = new Persona.PersonBuilder( 3L, "nombre", "DNI", 28334764, "ARG", "F", "3541333333" )
            .setIdPadre( 1L ).build();
    Persona mock4 = new Persona.PersonBuilder( 4L, "nombre", "DNI", 28334764, "ARG", "M", "3541333333" )
            .setIdPadre( 2L ).build();
    Persona mock5 = new Persona.PersonBuilder( 5L, "nombre", "DNI", 28334764, "ARG", "M", "3541333333" )
            .setIdPadre( 3L ).build();

    Persona mock6 = new Persona.PersonBuilder( 6L, "nombre", "DNI", 28334764, "ARG", "M", "3541333333" )
            .setIdPadre( null ).build();

    @Test
    public void testThatReturnsNotFoundWhenThereIsNoRelationship() throws Exception {

        Mockito.when(
                personaRepository.findById( 1L ) ).thenReturn( Optional.of( mock1 ) );
        Mockito.when(
                personaRepository.findById( 5L ) ).thenReturn( Optional.of( mock5 ) );
        Mockito.when(
                personaRepository.findById( 3L ) ).thenReturn( Optional.of( mock3 ) );

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/relaciones/1/5" ).accept(
                MediaType.APPLICATION_JSON );

        MvcResult result = mockMvc.perform( requestBuilder ).andReturn();

        System.out.println( result.getResponse() );
        String expected = "{\"status\":\"Not Found\",\"message\":\"No hay relacion\"}";

        JSONAssert.assertEquals( expected, result.getResponse()
                .getContentAsString(), false );
    }

    @Test
    public void testThatReturnsNotFoundWhenBothPeopleDoesNotExist() throws Exception {

        Mockito.when(
                personaRepository.findById( 100L ) ).thenReturn( Optional.empty() );
        Mockito.when(
                personaRepository.findById( 200L ) ).thenReturn( Optional.empty() );

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/relaciones/100/200" ).accept(
                MediaType.APPLICATION_JSON );

        MvcResult result = mockMvc.perform( requestBuilder ).andReturn();

        System.out.println( result.getResponse() );
        String expected = "{\"status\":\"Not Found\",\"message\":\"No hay relacion\"}";

        JSONAssert.assertEquals( expected, result.getResponse()
                .getContentAsString(), false );
    }

    @Test
    public void testThatReturnsNotFoundWhenBothPeopleHaveNoFathert() throws Exception {

        Mockito.when(
                personaRepository.findById( 1L ) ).thenReturn( Optional.of( mock1 ) );
        Mockito.when(
                personaRepository.findById( 6L ) ).thenReturn( Optional.of( mock6 ) );

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/relaciones/100/200" ).accept(
                MediaType.APPLICATION_JSON );

        MvcResult result = mockMvc.perform( requestBuilder ).andReturn();

        System.out.println( result.getResponse() );
        String expected = "{\"status\":\"Not Found\",\"message\":\"No hay relacion\"}";

        JSONAssert.assertEquals( expected, result.getResponse()
                .getContentAsString(), false );
    }

    @Test
    public void testThatRetrieveCorrectBrothersRelationship() throws Exception {

        Mockito.when(
                personaRepository.findById( 2L ) ).thenReturn( Optional.of( mock2 ) );
        Mockito.when(
                personaRepository.findById( 3L ) ).thenReturn( Optional.of( mock3 ) );

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/relaciones/2/3" ).accept(
                MediaType.APPLICATION_JSON );

        MvcResult result = mockMvc.perform( requestBuilder ).andReturn();

        System.out.println( result.getResponse() );
        String expected = "{\"status\":\"OK\",\"message\":\"HERMAN@\"}";

        JSONAssert.assertEquals( expected, result.getResponse()
                .getContentAsString(), false );
    }

    @Test
    public void testThatRetrieveCorrectCousinsRelationship() throws Exception {

        Mockito.when(
                personaRepository.findById( 4L ) ).thenReturn( Optional.of( mock4 ) );
        Mockito.when(
                personaRepository.findById( 5L ) ).thenReturn( Optional.of( mock5 ) );
        Mockito.when(
                personaRepository.findById( 2L ) ).thenReturn( Optional.of( mock2 ) );
        Mockito.when(
                personaRepository.findById( 3L ) ).thenReturn( Optional.of( mock3 ) );

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/relaciones/4/5" ).accept(
                MediaType.APPLICATION_JSON );

        MvcResult result = mockMvc.perform( requestBuilder ).andReturn();

        System.out.println( result.getResponse() );
        String expected = "{\"status\":\"OK\",\"message\":\"PRIM@\"}";

        JSONAssert.assertEquals( expected, result.getResponse()
                .getContentAsString(), false );
    }

    @Test
    public void testThatRetrieveCorrectUncleRelationship() throws Exception {

        Mockito.when(
                personaRepository.findById( 3L ) ).thenReturn( Optional.of( mock3 ) );
        Mockito.when(
                personaRepository.findById( 4L ) ).thenReturn( Optional.of( mock4 ) );
        Mockito.when(
                personaRepository.findById( 1L ) ).thenReturn( Optional.of( mock1 ) );
        Mockito.when(
                personaRepository.findById( 2L ) ).thenReturn( Optional.of( mock2 ) );

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/relaciones/3/4" ).accept(
                MediaType.APPLICATION_JSON );

        MvcResult result = mockMvc.perform( requestBuilder ).andReturn();

        System.out.println( result.getResponse() );
        String expected = "{\"status\":\"OK\",\"message\":\"TI@\"}";

        JSONAssert.assertEquals( expected, result.getResponse()
                .getContentAsString(), false );
    }

}
