package com.soulcode.servicos.controller;

import com.soulcode.servicos.model.Cargo;
import com.soulcode.servicos.service.CargoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/servicos/cargos")
public class CargoController {

    @Autowired
    CargoService cargoService;

    @GetMapping
    public ResponseEntity<List<Cargo>> listar(){
        return ResponseEntity.ok(cargoService.listar());
    }

    @GetMapping("/{idCargo}")
    public ResponseEntity<Cargo> buscar(@PathVariable Integer idCargo){
        return ResponseEntity.ok(cargoService.buscarOuFalhar(idCargo));
    }

    @GetMapping("/{nome}/nome")
    public ResponseEntity<Cargo> buscarPeloNome(@PathVariable String nome){
        return ResponseEntity.ok(cargoService.buscarPeloNome(nome));
    }

    @PostMapping
    public ResponseEntity<Cargo> cadastrarCargo(@RequestBody Cargo cargo){
        cargo = cargoService.salvar(cargo);
        URI novaURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cargo.getIdCargo()).toUri();
        return  ResponseEntity.created(novaURI).body(cargo);
    }

    @PutMapping("/{idCargo}")
    public ResponseEntity<Cargo> editarCargo(@PathVariable Integer idCargo, @RequestBody Cargo cargo){
        Cargo novoCargo = cargoService.buscarOuFalhar(idCargo);
        BeanUtils.copyProperties(cargo, novoCargo);

        return ResponseEntity.ok(cargoService.salvar(novoCargo));
    }

    @DeleteMapping("/{idCargo}")
    public ResponseEntity<Void> deletarCargoPeloId(@PathVariable Integer idCargo){
        cargoService.deletarCargoPeloId(idCargo);
        return ResponseEntity.noContent().build();
    }
}
