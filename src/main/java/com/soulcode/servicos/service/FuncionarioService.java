package com.soulcode.servicos.service;

import com.soulcode.servicos.model.Cargo;
import com.soulcode.servicos.model.Funcionario;
import com.soulcode.servicos.model.exception.FuncionarioNaoEncontradoException;
import com.soulcode.servicos.repository.CargoRepository;
import com.soulcode.servicos.repository.FuncionarioRepository;
import com.soulcode.servicos.service.exceptions.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// quando se fala em serviços, estamos falando dos metodos do CRUD da tabela
@Service
public class FuncionarioService {
    //aqui se faz a injeção de dependencia
    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    CargoRepository cargoRepository;


    public List<Funcionario> mostrarTodosFuncionarios(){
        return funcionarioRepository.findAll();
    }


    public Funcionario buscarOuFalhar(Integer idFuncionario) {
        return funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException(idFuncionario));
    }

    public Funcionario mostrarUmFuncioanarioPeloEmail(String email){
        return funcionarioRepository.findByEmail(email)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException(email));
    }


    public Funcionario cadastrarFuncionario(Funcionario funcionario, Integer idCargo){
        try {
            funcionario.setId(null);
            funcionario.setFoto("");
            Optional<Cargo> cargo = cargoRepository.findById(idCargo);
            funcionario.setCargo(cargo.get());
            return funcionarioRepository.save(funcionario);
        } catch(Exception e){
            throw new DataIntegrityViolationException("Atributo não pode ser duplicado");
        }
    }

    public void excluirFuncionario(Integer idFuncionario){
        funcionarioRepository.deleteById(idFuncionario);
    }

    public Funcionario editarFuncionario(Funcionario funcionario){
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario salvarFoto(Integer idFuncionario, String caminhoFoto){
        Funcionario funcionario = buscarOuFalhar(idFuncionario);
        funcionario.setFoto(caminhoFoto);
        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> mostrarFuncionariosPeloCargo(Integer idCargo){
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        List<Funcionario> funcionarios = funcionarioRepository.findByCargo(cargo);
        return funcionarios;
    }
}
