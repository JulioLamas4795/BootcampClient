package com.bootcamp.bankclient.model.entities;

import lombok.*;
import reactor.core.publisher.Flux;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Operations {

    List<Deposit> depositsByClient;

    List<Withdrawal> withdrawalByClient;

}
