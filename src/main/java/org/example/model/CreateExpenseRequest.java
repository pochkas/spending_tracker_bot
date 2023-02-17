package org.example.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CreateExpenseRequest {

    String category;
    Double price;

    public CreateExpenseRequest(String category, Double price) {
        this.category = category;
        this.price = price;

    }
}
