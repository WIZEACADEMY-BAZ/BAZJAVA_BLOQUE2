package com.cursojava.proyecto;

import com.cursojava.proyecto.Enum.Tipo;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.services.PokemonService;
import com.cursojava.proyecto.services.PokemonServiceImpl;
import com.cursojava.proyecto.model.ResponseDTO;
import com.cursojava.proyecto.utils.Utils;
import com.cursojava.proyecto.utils.exceptions.ExcepcionGenerica;
import com.cursojava.proyecto.utils.threads.CreatePokemons;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

@SpringBootApplication
public class ProyectoApplication {

	private static final Logger LOGGER = Logger.getLogger(ProyectoApplication.class.getName());
	private static String SUCCESS_CODE = "OK000";

	private static String responseTextThread = "";
	//private ResponseDTO response;
	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}


	@Autowired
	PokemonService pokemonService;

	@Bean
	public void Running() throws Exception {
		HttpServer server;
		try {
			LOGGER.info("Pokemon Application - Iniciado servicio REST ...");

			server = HttpServer.create(new InetSocketAddress(8081), 0);
			String msgProcPeticion = "Pokemon Application - Inicia procesamiento de peticion ...";

			server.createContext("/api/createPokemon", (exchange -> {
				LOGGER.info(msgProcPeticion);
				ResponseDTO response;
				String responseText = "";

				exchange.getRequestBody();
				if ("POST".equals(exchange.getRequestMethod())) {
					LOGGER.info("Pokemon Application - Procesando peticion HTTP de tipo GET");
					Map<String, String> params = splitQuery(exchange.getRequestURI());
						response = createPokemon(params.get("nombre"),params.get("sonido"),
								Tipo.valueOf(params.get("tipo1")),Tipo.valueOf(params.get("tipo2")), params.get("tranning"));
						JSONObject json = new JSONObject(response);
						responseText = json.toString();
						exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
						exchange.sendResponseHeaders(200, responseText.getBytes().length);
				} else {
					exchange.sendResponseHeaders(405, -1);
				}
				OutputStream output = exchange.getResponseBody();

				LOGGER.info("Pokemon Application - Cerrando recursos ...");
				output.write(responseText.getBytes());
				output.flush();
				output.close();
				exchange.close();
			}));

			server.createContext("/api/getTeam", (exchange -> {
				LOGGER.info(msgProcPeticion);
				Instant inicioDeEjecucion = Instant.now();
				PokemonService service = new PokemonServiceImpl();
				String responseText = "";
				if ("GET".equals(exchange.getRequestMethod())) {
					LOGGER.info("Pokemon Application - Procesando peticion HTTP de tipo GET");
					Map<Integer, PokemonDTO> equipo = service.getTeam();
					ObjectMapper objectMapper = new ObjectMapper();
					try {
						responseText = objectMapper.writeValueAsString(equipo);
						exchange.getResponseHeaders().add("Content-type", "application/json");
						exchange.sendResponseHeaders(200, responseText.getBytes().length);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}

				} else {
					exchange.sendResponseHeaders(405, -1);
				}
				OutputStream output = exchange.getResponseBody();
				Instant finalDeEjecucion = Instant.now();
				LOGGER.info("Pokemon Application - Cerrando recursos ...");
				String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
				LOGGER.info("Tiempo de respuesta: ".concat(total));
				output.write(responseText.getBytes());
				output.flush();
				output.close();
				exchange.close();
			}));

		server.createContext("/api/createMultiplePokemons", (exchange -> {
			LOGGER.info(msgProcPeticion);
			ResponseDTO response = new ResponseDTO();
			exchange.getRequestBody();
			if ("POST".equals(exchange.getRequestMethod())) {
				LOGGER.info("Pokemon Application - Procesando peticion HTTP de tipo POST");
				// Obtenemos el request del body que mandamos
				StringBuilder text = new StringBuilder();
				try (Scanner scanner = new Scanner(exchange.getRequestBody())) {
					while(scanner.hasNext()) {
						text.append(scanner.next());
					}
				} catch (Exception e) {
					LOGGER.severe(e.getMessage());
					throw new ExcepcionGenerica("Fallo al obtener el request del body");
				}
				JSONObject pokemonsObject = new JSONObject(text.toString());
				JSONArray pokemonsArray = pokemonsObject.getJSONArray("pokemons");

				CreatePokemons thread1 =new CreatePokemons(pokemonsArray.getJSONObject(0));
				CreatePokemons thread2 =new CreatePokemons(pokemonsArray.getJSONObject(1));
				CreatePokemons thread3 =new CreatePokemons(pokemonsArray.getJSONObject(2));

				// Iniciamos threads
				thread1.start();
				thread2.start();
				thread3.start();

				// Esperamos a que termine el thread
				try{
					thread1.join();
					thread2.join();
					thread3.join();
				}catch (InterruptedException e){
					throw new RuntimeException(e);
				}

				LOGGER.info(thread1.getPokemon().toString());
				LOGGER.info(thread2.getPokemon().toString());
				LOGGER.info(thread3.getPokemon().toString());
				exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
				exchange.sendResponseHeaders(200, responseTextThread.getBytes().length);
			} else {
				/** 405 Method Not Allowed */
				exchange.sendResponseHeaders(405, -1);
			}
			OutputStream output = exchange.getResponseBody();
			/**
			 * Always remember to close the resources you open.
			 * Avoid memory leaks
			 */
			LOGGER.info("Pokemon Application - Cerrando recursos ...");
			output.write(responseTextThread.getBytes());
			output.flush();
			output.close();
			exchange.close();
		}));

		////
		}catch (IOException e) {
			throw new RuntimeException(e);
		}catch (Exception e){
			throw new Exception(e);
		}
		server.setExecutor(null);
		server.start();
		LOGGER.info("Pokemon Application - Server started on port 8080");
	}

	public static Map<String, String> splitQuery(URI uri) throws UnsupportedEncodingException {

		Map<String, String> query_pairs = new LinkedHashMap<String, String>();

		String query = uri.getQuery();
		String[] pairs = query.split("&");
		for (String pair : pairs) {
			int idx = pair.indexOf("=");
			query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
		}
		LOGGER.info(query_pairs.toString());
		return query_pairs;
	}


	private ResponseDTO createPokemon( String nombre, String sonido, Tipo tipo1, Tipo tipo2, String lastTranning) {
		return pokemonService.createPokemon(nombre,sonido,tipo1,tipo2,null, lastTranning);
	}
}
