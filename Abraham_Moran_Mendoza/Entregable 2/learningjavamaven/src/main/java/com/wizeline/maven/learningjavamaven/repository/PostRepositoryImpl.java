package com.wizeline.maven.learningjavamaven.repository;

import com.wizeline.maven.learningjavamaven.model.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.wizeline.maven.learningjavamaven.constants.Constants.*;

@Repository
public class PostRepositoryImpl implements PostRepository{

  @Override
  public List<PostDTO> getUserPosts(String userId) {
    RestTemplate restTemplate = new RestTemplate();
    String url = JSON_POSTS_API_URL.replace("?", userId);
    return Arrays.asList(restTemplate.getForObject(url, PostDTO[].class));
  }

  @Override
  public List<PostDTO> getAllPosts(){
    RestTemplate restTemplate = new RestTemplate();
    return Arrays.asList(restTemplate.getForObject(JSON_ALL_POSTS_API_URL, PostDTO[].class));
  }
}
