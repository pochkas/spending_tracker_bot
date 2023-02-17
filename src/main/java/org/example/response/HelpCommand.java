package org.example.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelpCommand {

    private final String HELP = "How to use bot?\n" +
            "\n" +
            " You can execute commands from the main menu on the left or by typing a command:\n" +
            "\n" +
            "Type /start to see a welcome message.\n" +
            "\n" +
            "Type /help to see this message again.\n" +
            "\n" +
            "Type /input to type your expense.\n" +
            "\n" +
            "Type /get_all to see all expenses.\n" +
            "\n" +
            "Type /get_by_Category to see all expenses in this category.\n" +
            "\n" +
            "Type /get_by_Category_and_Month to see all expenses in this category and this month.";
}
