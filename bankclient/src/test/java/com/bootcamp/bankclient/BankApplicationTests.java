package com.bootcamp.bankclient;

import com.bootcamp.bankclient.controller.ClientController;
import com.bootcamp.bankclient.model.dto.ClientDto;
import com.bootcamp.bankclient.model.entities.ClientType;
import com.bootcamp.bankclient.service.ClientService;
import com.bootcamp.bankclient.service.ClientTypeService;
import com.bootcamp.bankclient.service.Impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebFluxTest(ClientController.class)
class BankApplicationTests {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ClientService service;
    @MockBean
    private ClientTypeService typeService;

    @Test
    public void addSaveClientTest(){

        Mono<ClientDto> clientDtoMono=Mono.just(new ClientDto("4354534534543","pepe","7","7","ga@gmail.com","9433534543","las flores sjl",new ClientType("7","7","pepe")));
        when(service.saveClients(clientDtoMono)).thenReturn(clientDtoMono);

        webTestClient.post().uri("/api/clients/create")
                .body(Mono.just(clientDtoMono),ClientDto.class)
                    .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getAllClientsTest(){
        Flux<ClientDto> clientDtoFlux=Flux.just(new ClientDto("435453453","pepe","55","5","ga@gmail.com","9433534543","las flores sjl", new ClientType("55","5","pepe")),
        new ClientDto("234324233","july","66","6","ga7@gmail.com","945674543","las flores 22 sjl", new ClientType("66","6","july")));
        when(service.getClients()).thenReturn(clientDtoFlux);

        Flux<ClientDto> responseBody=webTestClient.get().uri("/api/clients")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ClientDto.class)
                .getResponseBody();

       StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new ClientDto("435453453", "pepe", "55", "5", "ga@gmail.com", "9433534543", "las flores sjl",  new ClientType("55","5","pepe")))
                .expectNext(new ClientDto("234324233", "july", "66", "6", "ga7@gmail.com", "945674543", "las flores 22 sjl",  new ClientType("66","6","july")));
    }


    @Test
    public void getObtenerClientIdTest(){
        Mono<ClientDto> clientDtoMono=Mono.just(new ClientDto("435453453","pepe","5","5","ga@gmail.com","9433534543","las flores sjl", new ClientType("5","5","pepe")));
        when(service.getClientById(any())).thenReturn(clientDtoMono);

        Flux<ClientDto> responseBody = webTestClient.get().uri("/api/clients/435453453")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ClientDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p->p.getId().equals("435453453"))
                .verifyComplete();
    }

    @Test
    public void getObtenerClientNameTest(){
        Mono<ClientDto> clientDtoMono=Mono.just(new ClientDto("435453453","pepe","5","5","ga@gmail.com","9433534543","las flores sjl", new ClientType("5","5","pepe")));
        when(service.getClientById(any())).thenReturn(clientDtoMono);

        Flux<ClientDto> responseBody = webTestClient.get().uri("/api/clients/pepe")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ClientDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p->p.getName().equals("pepe"))
                .verifyComplete();
    }

    @Test
    public void updateClientTest(){
        Mono<ClientDto> clientDtoMono=Mono.just(new ClientDto("435453453","pepe","5","5","ga@gmail.com","9433534543","las flores sjl", new ClientType("5","5","pepe")));
        when(service.updateClients(clientDtoMono,"102")).thenReturn(clientDtoMono);

        webTestClient.put().uri("/api/clients/435453453")
                .body(Mono.just(clientDtoMono),ClientDto.class)
                .exchange()
                .expectStatus().isOk();//200
    }
    @Test
    public void deleteClientIdTest(){
        given(service.deleteClient(any())).willReturn(Mono.empty());
        webTestClient.delete().uri("/api/clients/435453453")
                .exchange()
                .expectStatus().isOk();//200
    }

}
