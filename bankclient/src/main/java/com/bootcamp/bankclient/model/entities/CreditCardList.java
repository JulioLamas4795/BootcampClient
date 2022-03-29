package com.bootcamp.bankclient.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardList {

    Flux<CreditCard> creditCards;

}
