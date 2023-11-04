package com.gino.siscripto.controller;

import com.gino.siscripto.dto.UserDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.model.entity.Holding;
import com.gino.siscripto.model.entity.User;
import com.gino.siscripto.service.interfaces.IHoldingService;
import com.gino.siscripto.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IHoldingService holdingService;


    @PostMapping("/usuarios")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) throws ApiException {
       return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    //get user por dni
    @GetMapping("/usuarios/{dni}")
    public ResponseEntity<?> get(@PathVariable String dni) throws ApiException {
        return new ResponseEntity<>(userService.getUser(dni),HttpStatus.OK);
    }

    //get users
    @GetMapping("/usuarios")
    public ResponseEntity<?> getAll() throws ApiException {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
    @PutMapping("/usuarios/{dni}") //me pasan los datos que quiero modificar en el dto
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO, @PathVariable String dni) throws ApiException {
        return new ResponseEntity<>(userService.updateUser(dni, userDTO), HttpStatus.OK);

   }
   @DeleteMapping("/usuarios/{dni}")
   public ResponseEntity<?> delete(@PathVariable String dni) throws ApiException {
       return new ResponseEntity<>(userService.deleteUser(dni),HttpStatus.OK);

   }


    @PostMapping("/usuarios/holdings")
    public ResponseEntity<?> createHoldingPrueba(@RequestBody Holding holding) throws ApiException{
        holdingService.createHolding(holding);
        return new ResponseEntity<>(holding,HttpStatus.OK);
    }
   }



