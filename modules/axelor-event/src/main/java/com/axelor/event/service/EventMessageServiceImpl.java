package com.axelor.event.service;

import com.axelor.apps.message.db.EmailAccount;
import com.axelor.apps.message.db.EmailAddress;
import com.axelor.apps.message.db.Message;
import com.axelor.apps.message.db.repo.EmailAccountRepository;
import com.axelor.apps.message.db.repo.EmailAddressRepository;
import com.axelor.apps.message.db.repo.MessageRepository;
import com.axelor.apps.message.service.MessageService;
import com.axelor.event.db.Event;
import com.axelor.event.db.EventRegistration;
import com.axelor.exception.AxelorException;
import com.axelor.inject.Beans;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;

public class EventMessageServiceImpl {

  @Inject MessageService messageService;

  public void sendMessageAll(Event event, String model) throws AxelorException, MessagingException {
    Boolean sentByEmail = true;
    Long id = event.getId();
    List<EmailAddress> emailAddressList = new ArrayList<>();
    for (EventRegistration eventRegistration : event.getEventRegistrationList()) {
      if (eventRegistration.getIsEmailSent() != true) {
        EmailAddress emailAddress =
            Beans.get(EmailAddressRepository.class).findByAddress(eventRegistration.getEmail());
        emailAddressList.add(emailAddress);
        eventRegistration.setIsEmailSent(sentByEmail);
      }
    }
    EmailAccount mailAccount =
        Beans.get(EmailAccountRepository.class).all().filter("self.isDefault=true").fetchOne();
    if (mailAccount != null) {
      Message message =
          messageService.createMessage(
              model,
              id.intValue(),
              "Event Registration detail",
              "Registration for Event " + event.getReference() + " is SuccessFull",
              null,
              null,
              emailAddressList,
              null,
              null,
              null,
              null,
              MessageRepository.MEDIA_TYPE_EMAIL,
              mailAccount);
      messageService.sendByEmail(message);
    }
  }
}