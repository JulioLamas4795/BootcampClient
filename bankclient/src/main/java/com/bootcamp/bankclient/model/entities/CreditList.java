package com.bootcamp.bankclient.model.entities;

import lombok.*;
import reactor.core.publisher.Flux;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditList {

    Flux<Credit> credits;

}
