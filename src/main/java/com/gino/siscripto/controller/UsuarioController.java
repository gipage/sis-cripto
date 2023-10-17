package com.gino.siscripto.controller;

import com.gino.siscripto.dto.CreateUsuarioDTO;
import com.gino.siscripto.model.entity.Billetera;
import com.gino.siscripto.model.entity.Usuario;
import com.gino.siscripto.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

    @Qualifier("usuarioServiceImpl")
    @Autowired
    private IUsuarioService userService;
    //Alta listo con DTO
   @PostMapping("/usuarios/crear")
    public ResponseEntity<?> create(@RequestBody CreateUsuarioDTO createUsuarioDTO){
      return userService.altaUsuario(createUsuarioDTO);
    }
    @GetMapping("/usuarios")
    public ResponseEntity<?> read(){
       return userService.listarUsuarios();
    }
    @PutMapping("/usuarios/update")
    public ResponseEntity<?> update(@RequestBody CreateUsuarioDTO createUsuarioDTO){
        return userService.modificarUsuario(createUsuarioDTO);

   }
   @DeleteMapping("/usuarios/{dni}")
   public ResponseEntity<?> delete(@PathVariable String dni){
       return userService.bajaUsuario(dni);

   }


}
