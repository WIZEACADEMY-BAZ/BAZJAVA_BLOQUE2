package baz.practice.wizeline.learningjavamaven.service;

/*import com.wizeline.DAO.UserDAO;
import com.wizeline.DAO.UserDAOImpl;
import com.wizeline.DTO.ErrorDTO;
import com.wizeline.DTO.ResponseDTO;
 */
import baz.practice.wizeline.learningjavamaven.model.ErrorDTO;
import baz.practice.wizeline.learningjavamaven.model.ResponseDTO;
import baz.practice.wizeline.learningjavamaven.repository.UserDAO;
import baz.practice.wizeline.learningjavamaven.repository.UserDAOImpl;
import baz.practice.wizeline.learningjavamaven.utils.Utils;

import java.util.logging.Logger;

public class UserBOImpl implements UserBO{
    private static final Logger LOGGER = Logger.getLogger(UserBOImpl.class.getName());

    @Override
    public ResponseDTO createUser(String user, String password) {
        LOGGER.info("Inicia Procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "fail";
        if(Utils.validateNullValue(user) && Utils.validateNullValue(password)){
            UserDAO userDao = new UserDAOImpl();
            result = userDao.createUser(user, password);
            response.setCode("OK000");
            response.setStatus(result);
        }else {
            response.setCode("OK000");
            response.setStatus(result);
            response.setErrors(new ErrorDTO("ER001","Error al crear usuario"));
        }
        return response;
    }

    @Override
    public ResponseDTO login(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "";
        if(Utils.validateNullValue(user) && Utils.validateNullValue(password)){
            UserDAO userDao = new UserDAOImpl();
            result = userDao.login(user, password);
        }
        if("success".equals(result)){
            response.setCode("OK000");
            response.setStatus(result);
        }else {
            response.setCode("ERR001");
            response.setErrors(new ErrorDTO("ERR001",result));
            response.setStatus("fail");
        }
        return response;
    }
}
