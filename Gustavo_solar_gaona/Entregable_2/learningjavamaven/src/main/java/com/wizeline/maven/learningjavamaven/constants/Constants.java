package com.wizeline.maven.learningjavamaven.constants;

public class Constants {

  private Constants() {
    throw new IllegalStateException("Constants class");
  }

  public static final String GET_METHOD = "GET";
  public static final String POST_METHOD = "POST";

  public static final long SLEEP_MILLIS = 1000;

  public static final Integer SOCKET_PORT = 8081;
  public static final Integer DEFAULT_BACKLOG = 0;

  public static final Integer RESPONSE_LENGTH = -1;

  public static final Integer METHOD_OK_CODE = 200;

  public static final Integer BAD_REQUEST_CODE = 400;
  public static final Integer UNAUTHORIZED_CODE = 401;
  public static final Integer METHOD_NOT_ALLOWED_CODE = 405;

  public static final String CONTENT_TYPE = "Content-type";
  public static final String CONTENT_TYPE_JSON = "application/json";
  public static final String CONTENT_TYPE_JSON_CHARSET = "application/json; charset=UTF-8";

  public static final String SUCCESS_STATUS = "success";

  public static final String SUCCESS_CODE = "OK000";
  public static final String ERROR_CODE = "ER001";

  public static final String JSON_TODOS_API_URL = "https://jsonplaceholder.typicode.com/users/?/todos";
  public static final String JSON_POSTS_API_URL = "https://jsonplaceholder.typicode.com/users/?/posts";
}
