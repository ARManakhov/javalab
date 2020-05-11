package ru.itis.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.MessageDto;
import ru.itis.models.Message;
import ru.itis.models.User;
import ru.itis.repositories.MessageRepository;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.MessageService;

import java.sql.Timestamp;
import java.util.*;

@RestController
public class MessagesController {
    @Autowired
    MessageService messageService;

    private static final Map<Long, List<MessageDto>> messages = new HashMap<>();

    // получили сообщение от какой либо страницы - мы его разошлем во все другие страницы
    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public ResponseEntity<Object> receiveMessage(@RequestBody MessageDto message, Authentication authentication) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        // если сообщений с этой или для этой страницы еще не было
        if (!messages.containsKey(user.getId())) {
            // добавляем эту страницу в Map-у страниц
            messages.put(user.getId(), new ArrayList<>());
        }
        // полученное сообщение добавляем для всех открытых страниц нашего приложения
        for (List<MessageDto> pageMessages : messages.values()) {
            // перед тем как положить сообщение для какой-либо страницы
            // мы список сообщений блокируем
            Date date = new Date();
            messageService.saveMessage(Message.builder().author(user).text(message.getText()).sendTime(new Timestamp(date.getTime())).build());
            synchronized (pageMessages) {
                // добавляем сообщение
                message.setAuthorName(user.getName());
                pageMessages.add(message);
                // говорим, что другие потоки могут воспользоваться этим списком
                pageMessages.notifyAll();
            }
        }

        return ResponseEntity.ok().build();
    }

    // получить все сообщения для текущего запроса
    @SneakyThrows
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public ResponseEntity<List<MessageDto>> getMessagesForPage( Authentication authentication) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        // получили список сообшений для страницы и заблокировали его
        synchronized (messages.get(user.getId())) {
            // если нет сообщений уходим в ожидание
            if (messages.get(user.getId()).isEmpty()) {
                messages.get(user.getId()).wait();
            }
        }

        // если сообщения есть - то кладем их в новых список
        List<MessageDto> response = new ArrayList<>(messages.get(user.getId()));
        // удаляем сообщения из мапы
        messages.get(user.getId()).clear();
        return ResponseEntity.ok(response);
    }
}
