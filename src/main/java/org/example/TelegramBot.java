package org.example;

import com.vdurmont.emoji.EmojiParser;
import org.example.model.Expense;
import org.example.response.HelpCommand;
import org.example.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.ExpenseService;
import org.example.service.impl.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {


    BotConfig botConfig;

    @Autowired
    ExpenseService expenseService;

    @Autowired
    UserRepository userRepository;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "get a welcome message"));
        listofCommands.add(new BotCommand("/help", "help message"));
        listofCommands.add(new BotCommand("/get_all", "get all expenses"));
        listofCommands.add(new BotCommand("/input", "input your data"));
        listofCommands.add(new BotCommand("/get_by_category", "get expenses by category, example /get_by_category FOOD"));
        listofCommands.add(new BotCommand("/group_by_category", "group expenses by category"));
        listofCommands.add(new BotCommand("/group_by_category_and_month", "group expenses by category and month"));

        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            UUID userId;
            Long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            Optional<UUID> optional = Optional.ofNullable(userRepository.findByChatId(chatId)).map(u -> u.getUserid());

            if (!optional.isPresent()) {
                UUID id = UUID.randomUUID();
                userRepository.save(new User(id, chatId));
                userId = id;
            } else {
                userId = optional.get();
            }


            if (messageText.startsWith("/start")) {

                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());


            } else if (messageText.startsWith("/help")) {

                prepareAndSendMessage(chatId, new HelpCommand().getHELP());
            } else if (messageText.startsWith("/get_all")) {

                prepareAndSendMessage(chatId, expenseService.getAll(userId).toString());
            } else if (messageText.startsWith("/input") && messageText.length() > 11) {

                log.info(messageText);

                String[] array = parseMessage(messageText);
                String category = array[1];
                Double price = Double.valueOf(array[2]);

                expenseService.addExpense(userId, category, price);
                sendMessage(chatId, messageText + " - this expense was added.");
            } else if (messageText.equals("/group_by_category_and_month")) {

                prepareAndSendMessage(chatId, expenseService.groupByCategoryAndMonth(userId).toString());

            } else if (messageText.startsWith("/get_by_category")) {

                String[] array = parseMessage(messageText);
                String category = array[1];
                expenseService.findAllByCategory(userId, category);
                prepareAndSendMessage(chatId, expenseService.findAllByCategory(userId, category).toString());

            } else if (messageText.startsWith("/group_by_category")) {

                prepareAndSendMessage(chatId, expenseService.groupByCategory(userId).toString());

            } else {

                prepareAndSendMessage(chatId, "Sorry, command was not recognized");

            }

        } else if (update.hasCallbackQuery()) {

            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals("write category and price")) {

                String text = "write category and price, example: /input FOOD 150.50";
                executeEditMessageText(text, chatId, messageId);

            } else if (callbackData.equals("write a category")) {
                String text = "write category, example: /get_by_category FOOD";
                executeEditMessageText(text, chatId, messageId);
            } else if (callbackData.equals("/get_all")) {
                String text = "/get_all";
                executeEditMessageText(text, chatId, messageId);
            } else if (callbackData.equals("/group_by_category_and_month")) {
                String text = "/group_by_category_and_month";
                executeEditMessageText(text, chatId, messageId);
            } else if (callbackData.equals("/group_by_category")) {
                String text = "/group_by_category";
                executeEditMessageText(text, chatId, messageId);
            } else {
                String text = "could not find this command";
                executeEditMessageText(text, chatId, messageId);
            }
        }

    }

    private void sendMessage(Long chatId, String textToSend) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();


        InlineKeyboardButton input = new InlineKeyboardButton();
        InlineKeyboardButton getAll = new InlineKeyboardButton();
        InlineKeyboardButton getByCategory = new InlineKeyboardButton();
        InlineKeyboardButton getByCategoryAndMonth = new InlineKeyboardButton();
        InlineKeyboardButton groupByCategory = new InlineKeyboardButton();


        input.setText("add expense");
        input.setCallbackData("write category and price");

        getAll.setText("all expenses");
        getAll.setCallbackData("/get_all");

        getByCategory.setText("expenses by category");
        getByCategory.setCallbackData("write a category");

        getByCategoryAndMonth.setText("expenses by category and month");
        getByCategoryAndMonth.setCallbackData("/group_by_category_and_month");

        groupByCategory.setText("group expenses by category");
        groupByCategory.setCallbackData("/group_by_category");


        row1.add(input);


        row2.add(getAll);
        row2.add(getByCategory);

        row3.add(getByCategoryAndMonth);
        row3.add(groupByCategory);

        rowList.add(row1);
        rowList.add(row2);
        rowList.add(row3);

        inlineKeyboardMarkup.setKeyboard(rowList);

        message.setReplyMarkup(inlineKeyboardMarkup);

        executeMessage(message);

    }

    private void executeEditMessageText(String text, long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error" + e.getMessage());
        }
    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("f" + e.getMessage());
        }
    }

    private void prepareAndSendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }


    public void startCommandReceived(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode("Hi, " + name + " :grinning:\n" +
                "\n" + "We have categories:\n" +
                "\n" + "SHOP\n" +
                "\n" + "CLOTHES\n" +
                "\n" + "FOOD\n" +
                "\n" + "CAFE\n" +
                "\n" + "APARTMENT\n" +
                "\n" + "INVESTMENT\n" +
                "\n" + "TECHNICS\n" +
                "\n" + "AUTO\n" +
                "\n" + "TRAVEL\n" +
                "\n" + "TRANSPORT\n" +
                "\n" + "OTHER\n"
        );

        sendMessage(chatId, answer);
    }

    public String[] parseMessage(String textMessage) {

        String[] array = textMessage.split(" ");

        return array;

    }


}
