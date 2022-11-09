package com.wizeline.maven.learningjavamaven.repository;

import com.wizeline.maven.learningjavamaven.model.PostDTO;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.wizeline.maven.learningjavamaven.constants.Constants.*;

@Repository
public class PostRepositoryImpl implements PostRepository{

  @Override
  public List<PostDTO> getUserPosts(String userId) {
    String url = JSON_POSTS_API_URL.replace("?", userId);
    RestTemplate restTemplate = new RestTemplate();
    return Arrays.asList(restTemplate.getForObject(url, PostDTO[].class));
  }
}
