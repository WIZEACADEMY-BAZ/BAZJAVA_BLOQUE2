package BO;

import DTO.ResponseDTO;

public interface UserBO {

  ResponseDTO createUser(String user, String password);

  ResponseDTO login(String user, String password);

}
