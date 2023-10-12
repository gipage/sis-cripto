package com.gino.siscripto.controller;

import com.gino.siscripto.model.entity.Billetera;
import com.gino.siscripto.model.entity.Usuario;
import com.gino.siscripto.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

    @Qualifier("usuarioServiceImpl")
    @Autowired
    private IUsuarioService userService;
    //Alta
   @PostMapping("/crear")
    public ResponseEntity<?> create(@RequestBody Usuario user){
       //Verifico si el usuario existe en la BD
       if(userService.localizarUsuario(user.getDni())!=null){
           return ResponseEntity.badRequest().body("El usuario ya existe.");
       }

       Usuario newUser = userService.altaUsuario(user);
       return ResponseEntity.ok(newUser);
    }
    @GetMapping("/usuarios")
    public List<Usuario> read(){
       return  userService.listarUsuarios();
    }
    @GetMapping("/usuarios/{dni}")
    public Usuario read(@PathVariable String dni){
        return  userService.localizarUsuario(dni);
    }

    @PutMapping("/usuarios/update/{dni}")
    public Usuario update(@PathVariable String dni,@RequestBody Usuario usuario){
        //todo
        return  null;

   }

}
