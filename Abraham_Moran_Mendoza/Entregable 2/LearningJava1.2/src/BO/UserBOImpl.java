package BO;

import DAO.UserDAO;
import DAO.UserDaoImpl;
import DTO.ErrorDTO;
import DTO.ResponseDTO;
import utils.Utils;

import java.util.logging.Logger;

public class UserBOImpl implements UserBO{
  private static final Logger LOGGER = Logger.getLogger(UserBOImpl.class.getName());

  @Override
  public ResponseDTO createUser(String user, String password) {
    LOGGER.info("Inicia procesamiento en capa de negocio");
    ResponseDTO response = new ResponseDTO();
    String result = "fail";
    System.out.println("Prueba 1");
    if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
      UserDAO userDao = new UserDaoImpl();
      result = userDao.createUser(user, password);
      response.setCode("OK000");
      response.setStatus(result);
      System.out.println("Prueba 2");
    }else {
      System.out.println("Prueba 3");
      response.setCode("OK000");
      response.setStatus(result);
      response.setErrors(new ErrorDTO("ER001","Error al crear usuario"));
    }
    return response;
  }

  @Override
  public ResponseDTO login(String user, String password) {
    LOGGER.info("Inicia procesamiento en caá de negocio");
    ResponseDTO response = new ResponseDTO();
    String result = "";
    if(Utils.validateNullValue(user) && Utils.validateNullValue(password)){
      UserDAO userDAO = new UserDaoImpl();
      result = userDAO.login(user,password);
    }
    if("success".equals(result)) {
      response.setCode("OK000");
      response.setStatus(result);
    } else {
      response.setCode("ER001");
      response.setErrors(new ErrorDTO("ER001",result));
      response.setStatus("fail");
    }
    return response;
  }
}
