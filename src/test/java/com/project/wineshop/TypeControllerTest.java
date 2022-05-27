package com.project.wineshop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class TypeControllerTest {

    @Autowired
    private TypeRepository repository;
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void mostrarTodosOk() {
        webTestClient.get()
                .uri("/types")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void mostrarTodosHalJson() {
        webTestClient.get()
                .uri("/types")
                .exchange()
                .expectHeader().valueEquals("Content-Type", "application/hal+json");
    }

    @Test
    void getOne() {
        webTestClient.get()
                .uri("/types/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void oneCheckId() {
        webTestClient.get()
                .uri("/types/1")
                .exchange()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1);
    }

    @Test
    void getOneCheckName() {
        webTestClient.get()
                .uri("/types/1")
                .exchange()
                .expectBody()
                .jsonPath("$.name").isEqualTo("tipo h2 prueba");
    }

    @Test
    void getNonExistentStatus() {
        webTestClient.get()
                .uri("/types/65")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void getNonExistentOne() {
        webTestClient.get()
                .uri("/types/65")
                .exchange()
                .expectBody(String.class).isEqualTo("Could not find type 65");
    }

    @Test
    void getWrongDatatypetOne() {
        webTestClient.get()
                .uri("/types/Poteitos")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deleteNonExistent() {
        webTestClient.delete()
                .uri("/types/404")
                .exchange()
                .expectStatus().isNotFound();
        repository.findAll().forEach(x -> System.out.println(x.toString()));
    }

    @Test
    void deleteOne() {
        webTestClient.delete()
                .uri("/types/2")
                .exchange()
                .expectStatus().isNoContent();
        repository.findAll().forEach(x -> System.out.println(x.toString()));

    }

    @Test
    void all() {
        webTestClient.get()
                .uri("/types")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/hal+json")
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2);
    }

    @Test
    void one() {
        int id = 1;
        Optional<Type> tipo = repository.findById(id);
        String name = "";
        if (tipo.isPresent()) {
            name = tipo.get().getName();
        }
        webTestClient.get()
                .uri("/types/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/hal+json")
                .expectBody()
                .jsonPath("@.name").isEqualTo(name);
    }

    @Test
    void add() {
        Type t = new Type("tipo test");
        webTestClient.post()
                .uri("/types")
                .bodyValue(t)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(20)
                .jsonPath("$.name").isEqualTo("tipo test");
    }

    @Test
    void addInjection() {
        Type t = new Type("tipo test'},{id: 8,name:'ijeccion test");
        webTestClient.post()
                .uri("/types")
                .bodyValue(t)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(21); // si se lanza por separado es 7
        //.jsonPath("$.name").isEqualTo("tipo test");
    }

    @Test
    void addNombreVacio() {
        String name = null;
        Type a = new Type(name);
        webTestClient.post()
                .uri("/types")
                .bodyValue(a)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("@.name").isEqualTo(name);

        repository.findAll().forEach(x -> System.out.println(x.toString()));
    }

    @Test
    void update() {
        int id = 1;
        String name = "asdiaasdiiuahsfagfiu";
        Type a = new Type(name);
        webTestClient.put()
                .uri("/types/" + id)
                .bodyValue(a)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().valueEquals("Content-Type", "application/hal+json")
                .expectBody()
                .jsonPath("@.name").isEqualTo(name)
                .jsonPath("@.id").isEqualTo(id);
    }

    @Test
    void updateIdIncorrecto() {
        String id = "asd";
        String name = "asdiaasdiiuahsfagfiu";
        Type a = new Type(name);
        webTestClient.put()
                .uri("/types/" + id)
                .bodyValue(a)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateIdNoExistente() {
        int id = 65;
        String name = "asdiaasdiiuahsfagfiu";
        Type a = new Type(name);
        webTestClient.put()
                .uri("/types/" + id)
                .bodyValue(a)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("@.name").isEqualTo(name);
    }


}
