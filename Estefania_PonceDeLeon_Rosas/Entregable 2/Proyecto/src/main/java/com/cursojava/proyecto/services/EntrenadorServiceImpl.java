package com.cursojava.proyecto.services;

import com.cursojava.proyecto.model.EntrenadorDTO;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.repository.EntrenadorRepository;
import com.cursojava.proyecto.utils.herencia.Movimiento;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class EntrenadorServiceImpl implements EntrenadorService{
    @Autowired
    EntrenadorRepository entrenadorRepository;

    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public void registrarDatos(EntrenadorDTO entrenador) {
        mongoTemplate.save(entrenador);
    }

    @Override
    public EntrenadorDTO consultarInformacion(EntrenadorDTO entrenador) {
        return entrenadorRepository.findEntrenadorDTOByNombreAndPassword(entrenador.getNombre(),entrenador.getPassword());
    }
    @Override
    public void registrarEquipo(EntrenadorDTO entrenador, PokemonDTO[] equipo) {
        Query query= Query.query(Criteria.where("nombre").is(entrenador.getNombre()).and("claveDeSeguridad").is(entrenador.getClaveDeSeguridad()));
        mongoTemplate.updateFirst(query, Update.update("equipo", equipo),EntrenadorDTO.class);
    }

    @Override
    public void retirarse(String nombre, String claveDeSeguridad) {
        entrenadorRepository.deleteEntrenadorDTOByNombreAndClaveDeSeguridad(nombre,claveDeSeguridad);
    }


    @Override
    public List<EntrenadorDTO> getEncryptedTrainers(){
        List<EntrenadorDTO> entrenadores = entrenadorRepository.findAll();

        // Aquí implementaremos nuestro código de cifrar nuestras cuentas y regresarselas al usuario de manera cifrada
        byte[] keyBytes = new byte[]{
                0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef
        };
        byte[] ivBytes = new byte[]{
                0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x01
        };
        Security.addProvider(new BouncyCastleProvider());
        SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = null;

        try {
            cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            // Cifraremos solamente el nombre y el country (pueden cifrar todos los parámetros que gusten)

            for (int i = 0; i < entrenadores.size(); i++) {
                String nombre = entrenadores.get(i).getNombre();
                byte[] arrTrainerName = nombre.getBytes();
                byte [] trainerNameCipher = new byte[cipher.getOutputSize(arrTrainerName.length)];
                int ctTrainerNameLength = cipher.update(arrTrainerName, 0, arrTrainerName.length, trainerNameCipher, 0);
                ctTrainerNameLength += cipher.doFinal(trainerNameCipher, ctTrainerNameLength);
                entrenadores.get(i).setNombre(trainerNameCipher.toString());

                String claveDeSeguridad = entrenadores.get(i).getClaveDeSeguridad();
                byte[] arrclaveDeSeguridad = claveDeSeguridad.getBytes();
                byte[] claveDeSeguridadCipher = new byte[cipher.getOutputSize(arrclaveDeSeguridad.length)];
                int ctclaveDeSeguridadLength = cipher.update(arrclaveDeSeguridad, 0,
                        arrclaveDeSeguridad.length, claveDeSeguridadCipher, 0);
                ctclaveDeSeguridadLength += cipher.doFinal(claveDeSeguridadCipher, ctclaveDeSeguridadLength);
                entrenadores.get(i).setClaveDeSeguridad(claveDeSeguridadCipher.toString());

                String password = entrenadores.get(i).getPassword();
                byte[] arrPassword = password.getBytes();
                byte[] passwordCipher = new byte[cipher.getOutputSize(arrPassword.length)];
                int ctpasswordLength = cipher.update(arrPassword, 0, arrPassword.length, passwordCipher, 0);
                ctpasswordLength += cipher.doFinal(passwordCipher, ctpasswordLength);
                entrenadores.get(i).setPassword(passwordCipher.toString());

                PokemonDTO[] equipo=entrenadores.get(i).getEquipo();


                for (int j = 0; j < equipo.length; j++) {
                    if(equipo[j]!=null){
                        String pokemon = equipo[j].getNombre();
                        byte[] arrPokemon = pokemon.getBytes();
                        byte[] pokemonCipher = new byte[cipher.getOutputSize(arrPokemon.length)];
                        int ctPokemonLength = cipher.update(arrPokemon, 0, arrPokemon.length, pokemonCipher, 0);
                        ctPokemonLength += cipher.doFinal(pokemonCipher, ctPokemonLength);
                        equipo[j].setNombre(pokemonCipher.toString());

                        String sonido = equipo[j].getSonido();
                        byte[] arrSonido = sonido.getBytes();
                        byte[] sonidoCipher = new byte[cipher.getOutputSize(arrPokemon.length)];
                        int ctSonidoLength = cipher.update(arrSonido, 0, arrSonido.length, sonidoCipher, 0);
                        ctSonidoLength += cipher.doFinal(sonidoCipher, ctSonidoLength);
                        equipo[j].setSonido(sonidoCipher.toString());

                        String tipo1 = equipo[j].getTipo1().getNombre();
                        byte[] arrtipo1 = tipo1.getBytes();
                        byte[] tipo1Cipher = new byte[cipher.getOutputSize(arrPokemon.length)];
                        int ctTipo1Length = cipher.update(arrtipo1, 0, arrtipo1.length, tipo1Cipher, 0);
                        ctTipo1Length += cipher.doFinal(tipo1Cipher, ctTipo1Length);
                        equipo[j].getTipo1().setNombre(tipo1Cipher.toString());

                        Movimiento[] movimientosGen=equipo[j].getMovimientos();
                        for(int k=0 ; k<movimientosGen.length; k++){
                            if (movimientosGen[k]!=null){
                                List<String> movimientos= movimientosGen[k].getMovimientos();
                                for(int l=0; l<movimientos.size(); l++){
                                    String movimiento = movimientos.get(l);
                                    byte[] arrMovimiento = movimiento.getBytes();
                                    byte[] movimientoCipher = new byte[cipher.getOutputSize(arrMovimiento.length)];
                                    int ctMovimientoLength = cipher.update(arrMovimiento, 0, arrMovimiento.length, movimientoCipher, 0);
                                    ctMovimientoLength += cipher.doFinal(movimientoCipher, ctMovimientoLength);
                                    movimientos.set(l,movimientoCipher.toString());
                                }
                            }
                        }
                    }
                }

            }
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
        return entrenadores;
    }


}
