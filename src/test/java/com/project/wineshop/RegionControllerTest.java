package com.project.wineshop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RegionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RegionRepository repository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void mostrarTodosOk() {
        webTestClient.get()
                .uri("/regions")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void mostrarTodosHalJson() {
        webTestClient.get()
                .uri("/regions")
                .exchange()
                .expectHeader().valueEquals("Content-Type", "application/hal+json");
    }

    @Test
    void getOne() {
        webTestClient.get()
                .uri("/regions/5")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void oneCheckId() {
        webTestClient.get()
                .uri("/regions/5")
                .exchange()
                .expectBody()
                .jsonPath("$.id").isEqualTo(5);
    }

    @Test
    void getOneCheckName() {
        webTestClient.get()
                .uri("/regions/5")
                .exchange()
                .expectBody()
                .jsonPath("$.name").isEqualTo("tierra media");
    }

    @Test
    void getNonExistentStatus() {
        webTestClient.get()
                .uri("/regions/65")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void getNonExistentOne() {
        webTestClient.get()
                .uri("/regions/65")
                .exchange()
                .expectBody(String.class).isEqualTo("Could not find region 65");
    }

    @Test
    void getWrongDatatypetOne() {
        webTestClient.get()
                .uri("/regions/Poteitos")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deleteNonExistent() {
        webTestClient.delete()
                .uri("/regions/404")
                .exchange()
                .expectStatus().isNotFound();
        repository.findAll().forEach(x -> System.out.println(x.toString()));
    }

    @Test
    void deleteOne() {
        webTestClient.delete()
                .uri("/regions/6")
                .exchange()
                .expectStatus().isNoContent();
        repository.findAll().forEach(x -> System.out.println(x.toString()));

    }

    @Test
    void all() {
        webTestClient.get()
                .uri("/regions")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/hal+json")
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2);
    }

    @Test
    void one() {
        int id = 5;
        Optional<Region> tipo = repository.findById(Long.valueOf(id));
        String name = "";
        if (tipo.isPresent()) {
            name = tipo.get().getName();
        }
        webTestClient.get()
                .uri("/regions/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/hal+json")
                .expectBody()
                .jsonPath("@.name").isEqualTo(name);
    }

    @Test
    void add() {
        Region t = new Region("region test", "Espana");
        webTestClient.post()
                .uri("/regions")
                .bodyValue(t)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(7)
                .jsonPath("$.name").isEqualTo("region test");
    }

    @Test
    void addInjection() {
        Region t = new Region("region test'},{id: 8,name:'ijeccion test", "Espana");
        webTestClient.post()
                .uri("/regions")
                .bodyValue(t)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(8); // si se lanza por separado es 7
        //.jsonPath("$.name").isEqualTo("region test");
    }

    @Test
    void addNombreVacio() {
        String name = null;
        Region a = new Region(name, "Espana");
        webTestClient.post()
                .uri("/regions")
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
        int id = 5;
        String name = "asdiaasdiiuahsfagfiu";
        Region a = new Region(name, "Espana");
        webTestClient.put()
                .uri("/regions/" + id)
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
        Region a = new Region(name, "Espana");
        webTestClient.put()
                .uri("/regions/" + id)
                .bodyValue(a)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateIdNoExistente() {
        int id = 65;
        String name = "asdiaasdiiuahsfagfiu";
        Region a = new Region(name, "Espana");
        webTestClient.put()
                .uri("/regions/" + id)
                .bodyValue(a)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("@.name").isEqualTo(name);
    }
}
