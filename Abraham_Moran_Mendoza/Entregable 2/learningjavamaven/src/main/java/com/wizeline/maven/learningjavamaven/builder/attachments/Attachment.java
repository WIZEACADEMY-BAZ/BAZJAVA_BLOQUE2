package com.wizeline.maven.learningjavamaven.builder.attachments;

public class Attachment {
  private Integer id;
  private String title;
  private String uriContent;
  private String size;
  private String extension;
  private String width;
  private String height;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUriContent() {
    return uriContent;
  }

  public void setUriContent(String uriContent) {
    this.uriContent = uriContent;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getWidth() {
    return width;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }
}
