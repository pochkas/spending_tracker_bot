package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Expense {

    public UUID userid;

    public Long id;

    public String category;

    public Double price;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public LocalDateTime date;

    public Expense(UUID userid, Long id, String category, Double price, LocalDateTime date) {
        this.userid = userid;
        this.id = id;
        this.category = category;
        this.price = price;
        this.date = date;
    }

    public Expense(){

    }

    public UUID getUserid() {
        return userid;
    }

    public Long getId() {
        return id;
    }

    public String getCategory(){
        return  category;
    }

    public Double getPrice(){
        return price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setUserid(UUID userid) {
        this.userid = userid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Expense{" +
                ", id=" + id +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(userid, expense.userid) && Objects.equals(id, expense.id) && Objects.equals(category, expense.category) && Objects.equals(price, expense.price) && Objects.equals(date, expense.date);
    }


}
