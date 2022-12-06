package com.wizeline.maven.learningjavamaven.repository;

import com.wizeline.maven.learningjavamaven.model.PostDTO;

import java.util.List;

// Revisión: Uso de por lo menos una interfaz de creación propia
public interface PostRepository {
  List<PostDTO> getUserPosts(String userId);
}
