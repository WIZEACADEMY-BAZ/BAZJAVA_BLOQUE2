package com.cursojava.learning2;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.cursojava.learning2.utils.Utils.isDateFormatValid;
import static com.cursojava.learning2.utils.Utils.isPasswordValid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import com.cursojava.learning2.config.EndpointBean;
import com.cursojava.learning2.model.BankAccountDTO;
import com.cursojava.learning2.model.ResponseDTO;
import com.cursojava.learning2.model.UserDTO;
import com.cursojava.learning2.service.BankAccountService;
import com.cursojava.learning2.service.BankAccountServiceImpl;
import com.cursojava.learning2.service.UserService;
import com.cursojava.learning2.service.UserServiceImpl;
import com.cursojava.learning2.utils.exceptions.ExcepcionGenerica;


@SpringBootApplication
public class LearningJavaApplication {
	public static void main(String[] args) { SpringApplication.run(LearningJavaApplication.class, args);}

}