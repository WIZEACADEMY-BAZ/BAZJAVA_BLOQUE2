package com.Wizeline.maven.learningjavamaven.repository;

import com.Wizeline.maven.learningjavamaven.model.Usuario;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class UsersDAO {
    /*--MongoTemplate--*/
    @Autowired
    private MongoTemplate mongoTemplate;

    public Usuario crearUsuario(Usuario usuario) {
        /* Escritura en MongoDB*/
        return mongoTemplate.save(usuario);
    }

    public List<Usuario> obtenerUsuarios() {
        /* Lectura de MongoDB*/
        List<Usuario> usuarios = mongoTemplate.findAll(Usuario.class);
        return usuarios;
    }

    public long cambiarContrasenia(String idUsuario, String contrasenia) {
        int identificador = Integer.parseInt(idUsuario);
        Query query = new Query().addCriteria(Criteria.where("_id").is(identificador));
        Update update = new Update().set("contrasenia", contrasenia);

        /* Actualización en MongoDB*/
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Usuario.class);

        return updateResult.getModifiedCount();
    }

    public Usuario obtenerUsuario(String idUsuario) {
        int identificador = Integer.parseInt(idUsuario);
        /* Lectura de MongoDB*/
        Query query = new Query().addCriteria(Criteria.where("id").in(identificador));
        Usuario usuario = mongoTemplate.findOne(query, Usuario.class);

        return usuario;
    }

    public long borrarUsuario(String idUsuario) {

        int identificador = Integer.parseInt(idUsuario);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(identificador));

        /* Borrado en MongoDB*/
        DeleteResult result = mongoTemplate.remove(query, Usuario.class);

        return result.getDeletedCount();
    }

    public Usuario login(String usuario, String contrasenia) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("nombre").is(usuario),
                Criteria.where("contrasenia").is(contrasenia)
        );

        Query query = new Query(criteria);

        Usuario res = mongoTemplate.findOne(query, Usuario.class);

        return res;
    }
}
