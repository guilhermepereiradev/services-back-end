package com.soulcode.servicos.service;

import com.soulcode.servicos.model.Cargo;
import com.soulcode.servicos.repository.CargoRepository;
import com.soulcode.servicos.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    @Autowired
    CargoRepository cargoRepository;

    @Cacheable("cargosCache")
    public List<Cargo> listar(){
        return cargoRepository.findAll();
    }

    @Cacheable(value = "cargosCache", key = "#idCargo")
    public Cargo buscarOuFalhar(Integer idCargo){
        return cargoRepository.findById(idCargo)
                .orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado para o id: "+idCargo));
    }

    @Cacheable(value = "cargosCache", key = "#nome")
    public Cargo buscarPeloNome(String nome){
        return cargoRepository.findByNome(nome).orElseThrow(() ->
            new EntityNotFoundException("Cargo não encontrado para o nome: "+nome));
    }

    @CachePut(value = "cargosCache", key = "#cargo.idCargo")
    public Cargo salvar(Cargo cargo){
        return cargoRepository.save(cargo);
    }

    @CacheEvict(value = "cargosCache", key = "#idCargo", allEntries = true)
    public void deletarCargoPeloId(Integer idCargo){
        Cargo cargo = buscarOuFalhar(idCargo);
        cargoRepository.delete(cargo);
    }
}
