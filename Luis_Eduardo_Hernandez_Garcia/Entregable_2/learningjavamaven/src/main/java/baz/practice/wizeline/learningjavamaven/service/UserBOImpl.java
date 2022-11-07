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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserBOImpl implements UserBO{

    @Autowired
    UserDAO userDAO;

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
            response.setErrors(new ErrorDTO.ErrorDTOBuilder("ER001", "Error al crear usuario")
                    .errorCode("ER001")
                    .message("Error al crear usuario").build());
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
            response.setErrors(new ErrorDTO.ErrorDTOBuilder("ER001", "Error al crear usuario")
                    .errorCode("ER001")
                    .message("Error al crear usuario").build());
            response.setStatus("fail");
        }
        return response;
    }
}
