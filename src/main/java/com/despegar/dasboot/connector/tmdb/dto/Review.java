package com.despegar.dasboot.connector.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {
  private String id;
  private String author;
  private String content;

  @JsonCreator
  public Review(
      @JsonProperty("id") String id,
      @JsonProperty("author") String author,
      @JsonProperty("content") String content) {
    this.id = id;
    this.author = author;
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public String getAuthor() {
    return author;
  }

  public String getContent() {
    return content;
  }
}
