package com.bootcamp.bankclient.model.entities;

import lombok.*;
import reactor.core.publisher.Flux;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Products {

    Flux<Account> accountsByClient;

    Flux<Credit> creditsByClient;

    Flux<CreditCard> creditCardByClient;

}
