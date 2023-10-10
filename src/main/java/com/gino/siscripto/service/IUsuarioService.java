package com.gino.siscripto.service;

import com.gino.siscripto.model.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUsuarioService {
    Usuario altaUsuario(Usuario user);
    Usuario localizarUsuario(String id);
    void bajaUsuario(Usuario user);
    List<Usuario> listarUsuarios();
}