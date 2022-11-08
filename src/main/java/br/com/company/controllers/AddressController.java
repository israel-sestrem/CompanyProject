package br.com.company.controllers;

import br.com.company.controllers.exceptions.AddressNotFoundException;
import br.com.company.entities.AddressEntity;
import br.com.company.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/addresses")
    ResponseEntity<List<AddressEntity>> findAll(){
        return new ResponseEntity<>(addressService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/addresses/{id}")
    ResponseEntity<AddressEntity> findById(@PathVariable Integer id){
        Optional<AddressEntity> address = addressService.findById(id);
        if(address.isPresent()){
            return new ResponseEntity<>(address.get(), HttpStatus.OK);
        }
        throw new AddressNotFoundException(id);
    }

    @GetMapping("/addresses/client/{id}")
    ResponseEntity<List<AddressEntity>> findAllByClientId(@PathVariable Integer id){
        List<AddressEntity> addresses = addressService.findAllByClientId(id);
        if(!addresses.isEmpty()){
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        }
        throw new AddressNotFoundException(id);
    }

    @PostMapping("/addresses")
    ResponseEntity<AddressEntity> save(@RequestBody AddressEntity address){
        return new ResponseEntity<>(addressService.save(address), HttpStatus.CREATED);
    }

    @PutMapping("/addresses/{id}")
    ResponseEntity<AddressEntity> update(@RequestBody AddressEntity newAddress, @PathVariable Integer id){
        Optional<AddressEntity> addressEntity = addressService.findById(id);
        if(addressEntity.isPresent()){
            return new ResponseEntity<>(addressService.save(addressService.checkNullFields(addressEntity.get(), newAddress)), HttpStatus.OK);
        }
        throw new AddressNotFoundException(id);
    }

    @DeleteMapping("/addresses/{id}")
    ResponseEntity<HttpStatus> delete(@PathVariable Integer id){
        if(addressService.existsById(id)){
            addressService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw new AddressNotFoundException(id);
    }

}