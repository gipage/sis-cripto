package com.gino.siscripto.controller;

import com.gino.siscripto.dto.CreateTransactionDTO;
import com.gino.siscripto.dto.CreateUserDTO;
import com.gino.siscripto.dto.TransactionSuccesfullyDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.model.entity.Holding;
import com.gino.siscripto.model.entity.User;
import com.gino.siscripto.service.interfaces.IHoldingService;
import com.gino.siscripto.service.interfaces.ITransactionService;
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
    private ITransactionService transactionService;
    @Autowired
    private IHoldingService holdingService;


    @PostMapping("/usuarios")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO) throws ApiException {
      User user = userService.createUser(createUserDTO);
       return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //get user por dni
    @GetMapping("/usuarios/{dni}")
    public ResponseEntity<?> get(@PathVariable String dni) throws ApiException {
        User user = userService.getUser(dni);
        return  new ResponseEntity<>(user,HttpStatus.OK);
    }

    //get users
    @GetMapping("/usuarios")
    public ResponseEntity<?> getAll() throws ApiException {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
    @PutMapping("/usuarios/{dni}") //me pasan los datos que quiero modificar en el dto
    public ResponseEntity<?> update(@RequestBody CreateUserDTO createUserDTO, @PathVariable String dni) throws ApiException {
        User user = userService.updateUser(dni,createUserDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);

   }
   @DeleteMapping("/usuarios/{dni}")
   public ResponseEntity<?> delete(@PathVariable String dni) throws ApiException {
       User user = userService.deleteUser(dni);
       return new ResponseEntity<>(user,HttpStatus.OK);

   }

   @PostMapping("/usuarios/transactions")
        public ResponseEntity<?> createTransaction(@RequestBody CreateTransactionDTO transactiondto) throws ApiException {
       TransactionSuccesfullyDTO succesfullyDTO = transactionService.createTransaction(transactiondto);
       return new ResponseEntity<>(succesfullyDTO,HttpStatus.OK);
    }

    @PostMapping("/usuarios/holdings")
    public ResponseEntity<?> createHoldingPrueba(@RequestBody Holding holding) throws ApiException{
        holdingService.createHolding(holding);
        return new ResponseEntity<>(holding,HttpStatus.OK);
    }
   }



