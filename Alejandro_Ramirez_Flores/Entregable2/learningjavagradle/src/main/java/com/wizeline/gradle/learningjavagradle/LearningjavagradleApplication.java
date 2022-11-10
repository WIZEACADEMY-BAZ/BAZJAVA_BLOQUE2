package com.wizeline.gradle.learningjavagradle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.service.BankAccountService;
import com.wizeline.gradle.learningjavagradle.service.BankAccountServiceImpl;
import com.wizeline.gradle.learningjavagradle.service.UserService;
import com.wizeline.gradle.learningjavagradle.service.UserServiceImpl;
import com.wizeline.gradle.learningjavagradle.utils.exceptions.ExcepcionGenerica;
//import org.json.JSONArray;
//import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
//import java.lang.Deprecated.since;

import static com.wizeline.gradle.learningjavagradle.utils.Utils.isDateFormatValid;
import static com.wizeline.gradle.learningjavagradle.utils.Utils.isPasswordValid;

@SpringBootApplication
@EnableFeignClients
public class LearningjavagradleApplication{

	private static final Logger LOGGER = Logger.getLogger(LearningjavagradleApplication.class.getName());
	private static final String SUCCESS_CODE = "OK000";
	private static String responseTextThread = "";
	private static String textThread = "";

	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(LearningjavagradleApplication.class, args);
	}

}