package bot.telegrambot.handlers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.passport.PassportData;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MySuperBot extends TelegramLongPollingBot {
    private final static String WHAT_THE_TIME_REQUEST = "What the time?";
    private final static String WHAT_THE_DATE_REQUEST = "What the date?";
    private final static String WHAT_THE_CAPITAL_REQUEST = "What is the capital of GB?";
    private final static String ORDER_PIZZA_REQUEST = "Order pizza";
    private final static String PIZZA_1 = "Pizza 1";
    private final static String PIZZA_2 = "Pizza 2";

    @Override
    public String getBotUsername() {
        return "my_super_uk_bot";
    }

    @Override
    public String getBotToken() {
        return "6613934353:AAH4HTeJEfEzIuUQVYNd7GexjMYgfiPrN20";
    }

    @Override
    public void onUpdateReceived(Update update) {
        final Message message = update.getMessage();

        try {
            execute(getResponseMessage(message));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private ReplyKeyboardMarkup getMainMenu() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(WHAT_THE_TIME_REQUEST);
        row1.add(WHAT_THE_DATE_REQUEST);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(WHAT_THE_CAPITAL_REQUEST);
        row2.add(ORDER_PIZZA_REQUEST);

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);

        markup.setKeyboard(rows);
        return markup;
    }

    private SendMessage getResponseMessage(Message message) {
        switch (message.getText()) {
            case WHAT_THE_TIME_REQUEST :
                return getCurrentTimeResponse(message);
            case ORDER_PIZZA_REQUEST:
                return getOrderPizzaResponse(message);
            default :
                return greetingMessage(message);
        }
    }

    private ReplyKeyboardMarkup getOrderPizzaMenu() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        row.add(PIZZA_1);
        row.add(PIZZA_2);

        markup.setKeyboard(List.of(row));
        return markup;
    }

    private SendMessage getCurrentTimeResponse(Message message) {
        SendMessage response = new SendMessage();
        response.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        response.setChatId(message.getChatId());
        response.setReplyMarkup(getMainMenu());
        return response;
    }

    private SendMessage greetingMessage(Message message) {
        SendMessage response = new SendMessage();
        response.setChatId(message.getChatId());
        response.setReplyMarkup(getMainMenu());
        response.setText("Hello " + message.getFrom().getFirstName()
                + ". You have send me: " + message.getText());
        return response;
    }

    private SendMessage getOrderPizzaResponse(Message message) {
        SendMessage response = new SendMessage();
        response.setText("Please make you choice :)");
        response.setChatId(message.getChatId());
        response.setReplyMarkup(getOrderPizzaMenu());
        return response;
    }
}
