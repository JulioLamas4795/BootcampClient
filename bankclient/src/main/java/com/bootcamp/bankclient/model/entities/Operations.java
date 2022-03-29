package com.bootcamp.bankclient.model.entities;

import lombok.*;
import reactor.core.publisher.Flux;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Operations {

    Flux<Deposit> depositsByClient;

    Flux<Withdrawal> withdrawalByClient;

}
