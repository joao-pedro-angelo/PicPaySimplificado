package com.br.picpaysimplificado.infra.notification;

import com.br.picpaysimplificado.domain.users.User;
import com.br.picpaysimplificado.dtos.notificationDTOs.CreateNotification;
import com.br.picpaysimplificado.infra.exceptions.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Notification {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String msg){
        String email = user.getEmail();
        CreateNotification notificationRequest = new CreateNotification(email, msg);
        ResponseEntity<String> response = this.restTemplate
                .postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", notificationRequest ,String.class);

        if ( !(response.getStatusCode() == HttpStatus.OK) ){
            throw new ValidateException("Erro ao enviar notificação!");
        }
    }
}
