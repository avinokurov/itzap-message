package com.itzap.api.message.services;

import com.itzap.api.message.model.Message;
import com.itzap.api.message.model.ResponseStatus;
import io.reactivex.Observable;

public interface MessageService {
    Observable<ResponseStatus> send(Message message);
}
