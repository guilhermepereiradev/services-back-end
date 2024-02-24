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


    /**
     * TODO
     *  Implementar consulta com filtro por nome
     */
    @GetMapping
    public ResponseEntity<List<CargoResponse>> listar(){
        var cargosResponse = cargoService.listar()
                .stream()
                .map(CargoResponse::new)
                .toList();

        return ResponseEntity.ok(cargosResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoResponse> buscar(@PathVariable Integer id){
        Cargo cargo = cargoService.buscarOuFalhar(id);
        return ResponseEntity.ok(new CargoResponse(cargo));
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
