package com.soulcode.servicos.service;

import com.soulcode.servicos.model.Cargo;
import com.soulcode.servicos.model.exception.CargoNaoEncontradoException;
import com.soulcode.servicos.repository.CargoRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @Cacheable("cargosCache")
    public List<Cargo> listar(){
        return cargoRepository.findAll();
    }

    @Cacheable(value = "cargosCache", key = "#id")
    public Cargo buscarOuFalhar(Integer id){
        return cargoRepository.findById(id)
                .orElseThrow(() -> new CargoNaoEncontradoException(id));
    }

    @Cacheable(value = "cargosCache", key = "#nome")
    public Cargo buscarPeloNome(String nome){
        return cargoRepository.findByNome(nome).orElseThrow(() ->
            new CargoNaoEncontradoException(String.format("Cargo não encontrado com o nome %s", nome)));
    }

    @Transactional
    @CachePut(value = "cargosCache", key = "#cargo.id")
    public Cargo salvar(Cargo cargo){
        return cargoRepository.save(cargo);
    }

    @Transactional
    @CacheEvict(value = "cargosCache", key = "#id", allEntries = true)
    public void deletarCargoPeloId(Integer id){
        try {
            cargoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CargoNaoEncontradoException(id);
        }
    }
}
