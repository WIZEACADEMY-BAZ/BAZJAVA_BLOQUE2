package com.wizeline.maven.learningjavamaven.model;

import com.wizeline.maven.learningjavamaven.builder.attachments.Attachment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

  public class PostDTO {
  private String id;
  private String userId;
  private String title;
  private String body;
  private Date postDate;
  public List<Attachment> attachments = new ArrayList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Date getPostDate() {
    return postDate;
  }

  public void setPostDate(Date postDate) {
    this.postDate = postDate;
  }

  @Override
  public String toString() {
    return "PostDTO{" +
          "id='" + id + '\'' +
          ", userId='" + userId + '\'' +
          ", title='" + title + '\'' +
          ", body='" + body + '\'' +
          ", postDate=" + postDate +
          ", attachments=" + attachments +
          '}';
  }
}