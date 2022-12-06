package com.wizeline.maven.learningjavamaven;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.wizeline.maven.learningjavamaven.configuration.EndpointBean;
import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import com.wizeline.maven.learningjavamaven.service.BankAccountServiceImpl;
import com.wizeline.maven.learningjavamaven.service.UserService;
import com.wizeline.maven.learningjavamaven.service.UserServiceImpl;
import com.wizeline.maven.learningjavamaven.utils.Utils;
import com.wizeline.maven.learningjavamaven.utils.exceptions.ExcepcionGenerica;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.wizeline.maven.learningjavamaven.constants.MessageConstants.*;
import static com.wizeline.maven.learningjavamaven.constants.Constants.*;

@SpringBootApplication
public class LearningjavamavenApplication extends Thread {

	private static final Logger LOGGER = Logger.getLogger(LearningjavamavenApplication.class.getName());

	public static void main(String[] args) throws IOException {
		SpringApplication.run(LearningjavamavenApplication.class, args);
	}
}
