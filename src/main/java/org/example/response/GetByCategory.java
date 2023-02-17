package org.example.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetByCategory {

    String category;
    Double price;

    @Override
    public String toString() {
        return "category=" + category + " price=" + price + "\n";
    }
}
