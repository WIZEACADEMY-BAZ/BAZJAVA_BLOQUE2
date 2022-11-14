package com.wizeline.gradle.practicajava.service;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.wizeline.gradle.practicajava.model.EstudianteDTO;
import com.wizeline.gradle.practicajava.repository.CursoRepository;
import com.wizeline.gradle.practicajava.utils.exceptions.ExcepcionCurso;

@Service
public class CursoServiceImpl extends Thread implements CursoService {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	CursoRepository cursoRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(CursoServiceImpl.class.getName());
	private static String msgProcPeticion = "LearningJava - Inicia procesamiento de petición...";

	@Override
	public List<EstudianteDTO> obtieneEstudiantes() {
		LOGGER.info(msgProcPeticion);
		return mongoTemplate.findAll(EstudianteDTO.class, "estudiantesCollection");
	}

	@Override
	public void guardaEstudiantes(List<EstudianteDTO> estudiantes) {
		LOGGER.info(msgProcPeticion);

		estudiantes.stream().forEach(estudiante -> estudiante.setNombre(estudiante.getNombre().toUpperCase()));
		Validaciones validaciones = new Validaciones();
		LocalDateTime fechaActual;
		try {
			for (EstudianteDTO estudiante : estudiantes) {
				if (!validaciones.validaMail(estudiante.getCorreo())) {
					estudiante.setCorreo("No disponible");
				}
				estudiante.setApellidoPat(validaciones.cifrar(estudiante.getApellidoPat()));
				estudiante.setSemestreCadena(validaciones.validaSemestre(estudiante.getSemestre()));
				fechaActual = LocalDateTime.now();
				estudiante.setFechaCreacion(fechaActual);
				LOGGER.info("Se almacena estudiante: {}", estudiante.toString());
				mongoTemplate.save(estudiante);
			}
		} catch (ExcepcionCurso ex) {
			LOGGER.info(ex.getMessage());
		}

	}

	@Override
	public void borrarEstudiantes() {
		LOGGER.info(msgProcPeticion);
		cursoRepository.deleteAll();
	}

	@Override
	public void actualizarEstudiante(EstudianteDTO estudiante) {
		LOGGER.info(msgProcPeticion);
		Update update = new Update();
		update.set("calificaciones", estudiante.getCalificaciones());
		mongoTemplate.upsert(Query.query(Criteria.where("_id").is(estudiante.getMatricula())), update,
				EstudianteDTO.class);

	}

	public class Validaciones {

		public boolean validaMail(String mail) {

			final Pattern patronMail = Pattern.compile("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$");
			boolean valido = false;
			if (patronMail.matcher(mail).matches()) {
				valido = true;
			}
			return valido;

		}

		public String validaSemestre(int idSemestre) {
			Map<Integer, String> mapaSemestres = new HashMap<Integer, String>();
			mapaSemestres.put(1, "Primer semestre");
			mapaSemestres.put(2, "Segundo semestre");
			mapaSemestres.put(3, "Tercer semestre");
			mapaSemestres.put(4, "Cuarto semestre");
			mapaSemestres.put(5, "Quinto semestre");
			mapaSemestres.put(6, "Sexto semestre");
			mapaSemestres.put(7, "Septimo semestre");
			mapaSemestres.put(8, "Octavo semestre");
			mapaSemestres.put(9, "Noveno semestre");
			mapaSemestres.put(10, "Decimo semestre");
			mapaSemestres.put(11, "Undecimo semestre");
			mapaSemestres.put(12, "Duodecimo semestre");
			return mapaSemestres.get(idSemestre);
		}

		public String cifrar(String cadena) {
			String cadenaCifrada;

			byte[] keyBytes = new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef };
			byte[] ivBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x01 };
			Security.addProvider(new BouncyCastleProvider());
			SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
			IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			Cipher cipher = null;
			try {
				cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
				cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

				byte[] arrAccountName = cadena.getBytes();
				byte[] accountNameCipher = new byte[cipher.getOutputSize(arrAccountName.length)];
				int ctAccountNameLength = cipher.update(arrAccountName, 0, arrAccountName.length, accountNameCipher, 0);
				ctAccountNameLength += cipher.doFinal(accountNameCipher, ctAccountNameLength);
				cadenaCifrada = accountNameCipher.toString();

			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			} catch (NoSuchProviderException e) {
				throw new RuntimeException(e);
			} catch (NoSuchPaddingException e) {
				throw new RuntimeException(e);
			} catch (InvalidAlgorithmParameterException e) {
				throw new RuntimeException(e);
			} catch (ShortBufferException e) {
				throw new RuntimeException(e);
			} catch (IllegalBlockSizeException e) {
				throw new RuntimeException(e);
			} catch (BadPaddingException e) {
				throw new RuntimeException(e);
			} catch (InvalidKeyException e) {
				throw new RuntimeException(e);
			}
			return cadenaCifrada;
		}
	}

	@Override
	public int[] obtenerCalificaciones(String matricula) {
		actualizaDatos();
		LOGGER.info(msgProcPeticion);
		Optional<EstudianteDTO> estudianteOptional;
		EstudianteDTO estudiante = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(matricula)),
				EstudianteDTO.class);
		estudianteOptional = Optional.ofNullable(estudiante);
		return estudianteOptional.isPresent() ? estudianteOptional.get().getCalificaciones() : new int[0];
	}

	public void actualizaDatos() {
		CursoServiceImpl thread = new CursoServiceImpl();
		thread.start();
		while (thread.isAlive())
			;
	}

	@Override
	public void run() {
		try {
			actualizaApellido();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			throw new ExcepcionCurso(e.getMessage());
		}
	}

	private void actualizaApellido() {
		try {
//			List<EstudianteDTO> estudiantes = obtieneEstudiantes();
//			for (EstudianteDTO estudianteDTO : estudiantes) {
//				estudianteDTO.setApellidoMat(estudianteDTO.getApellidoMat().toUpperCase());
//				Update update = new Update();
//				update.set("apellidoMat", estudianteDTO.getApellidoMat());
//				mongoTemplate.upsert(Query.query(Criteria.where("_id").is(estudianteDTO.getMatricula())), update,
//						EstudianteDTO.class);
//				Thread.sleep(1000);
//			}
			for (int i = 0; i < 5; i++) {
				LOGGER.info("Actualiza usuario " + i);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
