package com.alex.projectrapi.controller;

import com.alex.projectrapi.model.Chat;
import com.alex.projectrapi.model.Contacto;
import com.alex.projectrapi.model.Usuario;
import com.alex.projectrapi.repository.ContactoRepository;
import com.alex.projectrapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/chat")
public class ChatControllerAPI {
    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static List<Chat> chats = new ArrayList<>();


    // Obtener ID chat o crearlo a por ID de contacto
    @GetMapping("/websocket/{id}")
    public ResponseEntity<Chat> getOrCreateChat(@PathVariable Integer id) {
        System.out.println("Alguien me pide una ID " + id);
        // Obtener contacto
        Contacto contacto = contactoRepository.findById(id).orElse(null);
        if (contacto == null) {
            return ResponseEntity.notFound().build();
        }

        // Obtener números
        String num1 = contacto.getUsuario().getPhoneNumber();
        String num2 = contacto.getContacto().getPhoneNumber();

        boolean contactHaveUser = contactoRepository.findByUsuarioCitizenId(contacto.getContacto().getCitizenId()).stream().anyMatch(c -> {
            return c.getContacto().getPhoneNumber().equals(num1);
        });

        if (!contactHaveUser) {
            crearNuevoContactoInverso(contacto.getUsuario(), contacto.getContacto());
        }

        // Buscar chat existente
        Chat chatExistente = chats.stream()
                .filter(chat ->
                        (chat.getNumero1().equals(num1) && chat.getNumero2().equals(num2)) ||
                                (chat.getNumero1().equals(num2) && chat.getNumero2().equals(num1)))
                .findFirst()
                .orElse(null);

        if (chatExistente != null) {
            System.out.println("El chat existe en el sistema: " +  chatExistente);
            return ResponseEntity.ok(chatExistente);
        }

        // Crear nuevo chat
        Chat nuevoChat = new Chat(generarIdUnico(),  num1, num2);
        chats.add(nuevoChat);

        System.out.println("Nuevo chat creado en el sistema: " +  nuevoChat);
        return ResponseEntity.ok(nuevoChat);
    }

    // Metodos necesarios

    private String generarIdUnico() {
        final String[] idChat = new String[1];
        do {
            idChat[0] = UUID.randomUUID().toString(); // Modificamos el contenido, no la referencia
        } while (chats.stream().anyMatch(chat -> chat.getId().equals(idChat[0])));
        return idChat[0];
    }

    private void crearNuevoContactoInverso(Usuario usuarioOrigen, Usuario usuarioDestino) {
        boolean contactoInversoExiste = contactoRepository.existsByUsuarioAndContacto(usuarioDestino, usuarioOrigen);

        if (!contactoInversoExiste) {
            Contacto contacto = new Contacto();
            contacto.setUsuario(usuarioDestino);
            contacto.setContacto(usuarioOrigen);
            contacto.setName(usuarioOrigen.getPhoneNumber());
            contacto.setIsChatting(true);
            contactoRepository.save(contacto);
        }
    }

}
