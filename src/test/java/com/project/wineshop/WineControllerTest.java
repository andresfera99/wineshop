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
class WineControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private WineRepository repository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private WineryRepository wineryRepository;
    @Autowired
    private RegionRepository regionRepository;

    private Winery auxWinery;
    private Type auxType;
    private Region auxRegion;

    @BeforeEach
    void setUp() {
        Optional<Winery> a = wineryRepository.findById(Long.valueOf(8));
        if (a.isPresent()) {
            auxWinery = a.get();
        }
        Optional<Type> b = typeRepository.findById(4);
        if (b.isPresent()) {
            auxType = b.get();
        }

        Optional<Region> c = regionRepository.findById(12L);
        if (c.isPresent()) {
            auxRegion = c.get();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void mostrarTodosOk() {
        webTestClient.get()
                .uri("/wines")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void mostrarTodosHalJson() {
        webTestClient.get()
                .uri("/wines")
                .exchange()
                .expectHeader().valueEquals("Content-Type", "application/hal+json");
    }

    @Test
    void getOne() {
        webTestClient.get()
                .uri("/wines/13")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void oneCheckId() {
        webTestClient.get()
                .uri("/wines/13")
                .exchange()
                .expectBody()
                .jsonPath("$.id").isEqualTo(13);
    }

    @Test
    void getOneCheckName() {
        webTestClient.get()
                .uri("/wines/13")
                .exchange()
                .expectBody()
                .jsonPath("$.name").isEqualTo("vino1");
    }

    @Test
    void getNonExistentStatus() {
        webTestClient.get()
                .uri("/wines/65")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void getNonExistentOne() {
        webTestClient.get()
                .uri("/wines/65")
                .exchange()
                .expectBody(String.class).isEqualTo("Could not find wine 65");
    }

    @Test
    void getWrongDatatypetOne() {
        webTestClient.get()
                .uri("/wines/Poteitos")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deleteNonExistent() {
        webTestClient.delete()
                .uri("/wines/404")
                .exchange()
                .expectStatus().isNotFound();
        repository.findAll().forEach(x -> System.out.println(x.toString()));
    }

    @Test
    void deleteOne() {
        webTestClient.delete()
                .uri("/wines/14")
                .exchange()
                .expectStatus().isNoContent();
        repository.findAll().forEach(x -> System.out.println(x.toString()));

    }

    @Test
    void all() {
        webTestClient.get()
                .uri("/wines")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/hal+json")
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2);
    }

    @Test
    void one() {
        int id = 13;
        Optional<Wine> tipo = repository.findById((long) id);
        String name = "";
        if (tipo.isPresent()) {
            name = tipo.get().getName();
        }
        webTestClient.get()
                .uri("/wines/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/hal+json")
                .expectBody()
                .jsonPath("@.name").isEqualTo(name);
    }

    @Test
    void add() {
        Wine t = new Wine("vino test", "2305", 3215.21, 52.1f, 2, "2", "96", auxWinery, auxType, auxRegion);
        webTestClient.post()
                .uri("/wines")
                .bodyValue(t)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(24)
                .jsonPath("$.name").isEqualTo("vino test");
    }

    @Test
    void addInjection() {
        Wine t = new Wine("vino test'},{id: 8,name:'ijeccion test\"", "2305", 3215.21, 52.1f, 2, "2", "96", auxWinery, auxType, auxRegion);
        webTestClient.post()
                .uri("/wines")
                .bodyValue(t)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(25);
        //.jsonPath("$.name").isEqualTo("tipo test");
    }

    @Test
    void addNombreVacio() {
        String name = null;
        Wine t = new Wine(name, "2305", 3215.21, 52.1f, 2, "2", "96", auxWinery, auxType, auxRegion);
        webTestClient.post()
                .uri("/wines")
                .bodyValue(t)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("@.name").isEqualTo(name);

        repository.findAll().forEach(x -> System.out.println(x.toString()));
    }

    @Test
    void update() {
        int id = 13;
        String name = "asdiaasdiiuahsfagfiu";
        Wine t = new Wine(name, "2305", 3215.21, 52.1f, 2, "2", "96", auxWinery, auxType, auxRegion);
        webTestClient.put()
                .uri("/wines/" + id)
                .bodyValue(t)
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
        Wine t = new Wine(name, "2305", 3215.21, 52.1f, 2, "2", "96", auxWinery, auxType, auxRegion);
        webTestClient.put()
                .uri("/wines/" + id)
                .bodyValue(t)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateIdNoExistente() {
        int id = 65;
        String name = "asdiaasdiiuahsfagfiu";
        Wine t = new Wine(name, "2305", 3215.21, 52.1f, 2, "2", "96", auxWinery, auxType, auxRegion);
        webTestClient.put()
                .uri("/wines/" + id)
                .bodyValue(t)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("@.name").isEqualTo(name);
    }
}
