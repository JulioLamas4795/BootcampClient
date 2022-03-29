package com.bootcamp.bankclient.service.Impl;

import com.bootcamp.bankclient.model.entities.*;
import com.bootcamp.bankclient.model.dto.ClientDto;
import com.bootcamp.bankclient.repository.ClientRepository;
import com.bootcamp.bankclient.service.ClientService;
import com.bootcamp.bankclient.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {



    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ClientRepository clientRepository;

    public Flux<ClientDto> getClients(){
        return clientRepository.findAll().map(AppUtils::entityToDto);
    }

    @Override
    public Mono<ClientDto> getClientById(String id) {
        return clientRepository.findById(id).map(AppUtils::entityToDto);
    }

    @Override
    public Mono<ClientDto> getClientByName(String name) {
        return clientRepository.findByName(name);
    }

    @Override
    public Mono<ClientDto> getClientByClientIdNumber(String clientIdNumber) {
        return clientRepository.findByClientIdNumber(clientIdNumber)
                .switchIfEmpty(Mono.just(ClientDto.builder()
                        .clientIdNumber(null).build()));
    }



    public Mono<ClientDto> saveClients(Mono<ClientDto> clientDtoMono){
        return clientDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(clientRepository::insert)
                .map(AppUtils::entityToDto);
    }



    @Override
    public Mono<Client> save(Client client) {
        return clientRepository.save(client);
    }

    public Mono<ClientDto> updateClients(Mono<ClientDto> clientDtoMono,String id){
        return clientRepository.findById(id)
                .flatMap(p->clientDtoMono.map(AppUtils::dtoToEntity)
                .doOnNext(e->e.setId(id)))
                .flatMap(clientRepository::save)
                .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteClient(String id){
        return clientRepository.deleteById(id);
    }


    public Mono<Products> obtainProductsByClient(String id){
        //Tarjetas de crédito del cliente
        CreditCardList creditCard = restTemplate.getForObject("http://localhost:8083/api/creditcard/client/{id}", CreditCardList.class, id);
        System.out.println(creditCard);
        //Cuentas
        AccountList account = restTemplate.getForObject("http://localhost:8083/api/accounts/client/{id}", AccountList.class, id);
        //Créditos
        CreditList credit = restTemplate.getForObject("http://localhost:8083/api/credit/client/{id}", CreditList.class, id);

        Products productsByClient = new Products(account.getAccounts(), credit.getCredits(), creditCard.getCreditCards());

        Mono<Products> productsMono = Mono.just(productsByClient);



        return productsMono;
    }

    public Mono<Operations> obtainOperationsByClient(String id){

        //Depositos
        DepositList depositList = restTemplate.getForObject("http://localhost:8083/api/deposit/history/{id}", DepositList.class, id);

        //Retiros
        WithdrawalList withdrawalList = restTemplate.getForObject("http://localhost:8083/api/withdrawal/client/{id}", WithdrawalList.class, id);

        Operations operations = new Operations(depositList.getDepositFlux(), withdrawalList.getWithdrawalFlux());
        Mono<Operations> operationsMono = Mono.just(operations);

        return operationsMono;
    }



}
