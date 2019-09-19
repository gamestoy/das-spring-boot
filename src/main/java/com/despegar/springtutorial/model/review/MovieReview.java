package com.despegar.springtutorial.model.review;

import com.fasterxml.jackson.annotation.JsonCreator;

public class MovieReview {
  private String id;
  private String author;
  private String content;

  @JsonCreator
  public MovieReview(String id, String author, String content) {
    this.id = id;
    this.author = author;
    this.content = content;
  }

  public String getAuthor() {
    return author;
  }

  public String getContent() {
    return content;
  }

  public String getId() {
    return id;
  }
}
