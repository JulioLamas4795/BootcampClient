package com.bootcamp.bankclient.model.entities;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Products {

    List<Account> accountsByClient;

    List<Credit> creditsByClient;

    List<CreditCard> creditCardByClient;

}
