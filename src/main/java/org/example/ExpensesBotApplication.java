package org.example;

import org.example.config.BotConfig;
import org.example.service.ExpenseService;
import org.example.service.impl.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


import java.util.UUID;

@SpringBootApplication
public class ExpensesBotApplication implements CommandLineRunner {


       public static void main(String[] args){

        SpringApplication.run(ExpensesBotApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    @Override
    public void run(String... args) throws Exception {

//        ExpenseService expenseService=new ExpenseServiceImpl(botConfig, new RestTemplate());
//
//        System.out.println(expenseService.getAll(UUID.randomUUID()));
//        System.out.println(expenseService.getExpense(UUID.randomUUID(), 1L));

    }


}
