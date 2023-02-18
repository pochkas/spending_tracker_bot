package org.example.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.response.GetByCategory;
import org.example.response.GroupByCategory;
import org.example.response.GroupByCategoryAndMonth;
import org.example.config.BotConfig;
import org.example.exception.ExpenseBotException;
import org.example.model.CreateExpenseRequest;
import org.example.model.Expense;
import org.example.model.ExpenseResponse;
import org.example.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Slf4j
@Component
@Configuration
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    BotConfig botConfig;

    @Autowired
    RestTemplate restTemplate;

    public ExpenseServiceImpl(BotConfig botConfig, RestTemplate restTemplate) {
        this.botConfig = botConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Expense> getAll(UUID userid) {

        String jsonStr = restTemplate.getForObject(botConfig.getServerUrl() + "/expenses" + "/" + userid, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Expense> response = null;
        try {
            response = objectMapper.readValue(jsonStr, new TypeReference<List<Expense>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;


    }

    @Override
    public void addExpense(UUID userid, String category, Double price, LocalDateTime date) {

        CreateExpenseRequest request = new CreateExpenseRequest(category, price, date);

        String jsonStr = restTemplate.postForObject(botConfig.getServerUrl() + "/expenses/" + userid, request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    @Override
    public ExpenseResponse update(UUID userid, Long id) {

        String jsonStr = restTemplate.getForObject(botConfig.getServerUrl() + "/expenses/" + userid + "/" + id, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ExpenseResponse response = null;
        try {
            response = objectMapper.readValue(jsonStr, ExpenseResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;

    }

    @Override
    public ExpenseResponse delete(UUID userid, Long id) {
        String jsonStr = restTemplate.getForObject(botConfig.getServerUrl() + "/expenses/" + userid + "/" + id, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ExpenseResponse response = null;
        try {
            response = objectMapper.readValue(jsonStr, ExpenseResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public ExpenseResponse getExpense(UUID userid, Long id) {

        ExpenseResponse response = null;
        String jsonStr = null;
        try {
            jsonStr = restTemplate.getForObject(botConfig.getServerUrl() + "/expenses/" + userid + "/" + id, String.class);
        } catch (Exception e) {
            log.error("Could not find this id.", e);
            throw new ExpenseBotException("Could not find this id.");

        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            response = objectMapper.readValue(jsonStr, ExpenseResponse.class);
        } catch (Exception e) {
            log.error("Could not read this JSON.");
            throw new RuntimeException(e);
        }

        return response;

    }

    @Override
    public List<GetByCategory> findAllByCategory(UUID userid, String category) {

        String jsonStr = restTemplate.getForObject(botConfig.getServerUrl() + "/expenses/" + userid + "/byCategory/" + category, String.class);
        log.info(jsonStr);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<GetByCategory> response = null;
        try {
            response = objectMapper.readValue(jsonStr, new TypeReference<List<GetByCategory>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public List<GroupByCategory> groupByCategory(UUID userid) {
        String jsonStr = restTemplate.getForObject(botConfig.getServerUrl() + "/expenses/" + userid+"/groupByCategory", String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<GroupByCategory> response = null;
        try {
            response = objectMapper.readValue(jsonStr, new TypeReference<List<GroupByCategory>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public List<GroupByCategoryAndMonth> groupByCategoryAndMonth(UUID userid) {
        String jsonStr = restTemplate.getForObject(botConfig.getServerUrl() + "/expenses/" + userid+"/groupByCategoryAndMonth", String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<GroupByCategoryAndMonth> response = null;
        try {
            response = objectMapper.readValue(jsonStr, new TypeReference<List<GroupByCategoryAndMonth>>() {

            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseServiceImpl that = (ExpenseServiceImpl) o;
        return Objects.equals(botConfig, that.botConfig) && Objects.equals(restTemplate, that.restTemplate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(botConfig, restTemplate);
    }
}
