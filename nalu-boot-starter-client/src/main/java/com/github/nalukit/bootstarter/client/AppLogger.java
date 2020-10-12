package com.github.nalukit.bootstarter.client;

import com.github.nalukit.nalu.client.application.AbstractLogger;

import java.util.List;

public class AppLogger extends AbstractLogger<AppContext> {
  
  @Override
  public void log(List<String> messages,
                  boolean sdmOnly) {
    // TODO implement Logger ...
//    LoggingServiceFactory.INSTANCE.log(messages)
//                                  .onSuccess(response -> {
//                                  })
//                                  .onFailed(failed -> {
//                                  })
//                                  .send();
  }

}
