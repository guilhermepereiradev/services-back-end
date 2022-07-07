package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    CargoRepository cargoRepository;

    public List<Cargo> mostrarTodosCargos(){
        return cargoRepository.findAll();
    }

    public Cargo mostrarCargoPeloId(Integer idCargo){
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return cargo.orElseThrow();
    }

    public Cargo mostrarCargoPeloNome(String nome){
        Optional<Cargo> cargo = cargoRepository.findByNome(nome);
        return cargo.orElseThrow();
    }

    public Cargo cadastrarCargo(Cargo cargo){
        return cargoRepository.save(cargo);
    }

    public Cargo editarCargo(Cargo cargo){
        return cargoRepository.save(cargo);
    }

    public void deletarCargoPeloId(Integer idCargo){
        cargoRepository.deleteById(idCargo);
    }
}
