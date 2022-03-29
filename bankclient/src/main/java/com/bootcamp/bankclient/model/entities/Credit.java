package com.bootcamp.bankclient.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document(collection = "credit")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credit {

    @Id
    private String id;
    private double capital;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation = LocalDateTime.now();

    @Indexed(unique = true)
    private String contractNumber;

    @NotNull
    private String clientIdNumber;

    private double amountInitial;

    private double amount;

    private String chargeDay;

    private double commission;

    private double interestRate;

    private boolean debitor;


}
