package com.despegar.dasboot.context;

import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.UUID;

@Component
public class TraceGenerator {
  private String hostname;

  public TraceGenerator() {
    try {
      this.hostname = InetAddress.getLocalHost().getHostName();
    } catch (Exception e) {
      this.hostname = "UNKNOWN";
    }
  }

  String generateUOW(String prefix) {
    return prefix + "-" + UUID.randomUUID().toString();
  }

  String getHostname() {
    return this.hostname;
  }
}
