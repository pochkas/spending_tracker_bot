package org.example.exception;

public class ExpenseBotException extends RuntimeException{
    public ExpenseBotException(String message){
        super(message);
    }
}
