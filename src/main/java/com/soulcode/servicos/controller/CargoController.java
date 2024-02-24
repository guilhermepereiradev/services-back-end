package com.soulcode.servicos.controller;

import com.soulcode.servicos.dtos.CargoRequest;
import com.soulcode.servicos.dtos.CargoResponse;
import com.soulcode.servicos.model.Cargo;
import com.soulcode.servicos.service.CargoService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/servicos/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public ResponseEntity<List<Cargo>> listar(){
        return ResponseEntity.ok(cargoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cargo> buscar(@PathVariable Integer id, @RequestParam(required = false) String nome){

        if (nome.isBlank()) {
            return ResponseEntity.ok(cargoService.buscarPeloNome(nome));
        }

        return ResponseEntity.ok(cargoService.buscarOuFalhar(id));
    }

    @PostMapping
    public ResponseEntity<CargoResponse> cadastrarCargo(@RequestBody CargoRequest cargoRequest){
        var cargo = new Cargo();
        BeanUtils.copyProperties(cargoRequest, cargo);

        cargo = cargoService.salvar(cargo);

        URI novaURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cargo.getId()).toUri();

        return  ResponseEntity.created(novaURI).body(new CargoResponse(cargo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoResponse> editarCargo(@PathVariable Integer id, @RequestBody CargoRequest cargoRequest){
        var cargo = cargoService.buscarOuFalhar(id);
        BeanUtils.copyProperties(cargoRequest, cargo);

        cargo = cargoService.salvar(cargo);

        return ResponseEntity.ok(new CargoResponse(cargo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCargoPeloId(@PathVariable Integer id){
        cargoService.deletarCargoPeloId(id);
        return ResponseEntity.noContent().build();
    }
}
