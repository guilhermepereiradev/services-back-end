package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.CargoRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import com.soulcode.Servicos.Services.Exceptions.DataIntegrityViolationException;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
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

    // primeiro serviço na tabela de funcionarios vai ser a leitura de todos os funcionarios cadastrados
    //findAll -> metodo do spring Data JPA -> busca todos os registros de uma tabela

    public List<Funcionario> mostrarTodosFuncionarios(){
        return funcionarioRepository.findAll();
    }

    //vamos criar mais um serviço relacionado ao funcionário
    //vamos criar um serviço de buscar o funcionario pelo seu id(chave primaria)

    public Funcionario mostrarUmFuncionarioPeloId(Integer idFuncionario){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return funcionario.orElseThrow(
                () -> new EntityNotFoundException("Funcionário não encontrado: "+ idFuncionario)
        );
    }

//    Vamos criar mais um serviço para buscar um funcionario pelo seu email
    public Funcionario mostrarUmFuncioanarioPeloEmail(String email){
        Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);
        return funcionario.orElseThrow();
    }

//  Vamos criar um serviço para cadastrar um novo funcionario
    public Funcionario cadastrarFuncionario(Funcionario funcionario, Integer idCargo){
//  So por preocação nos vamos colocar o id do funcinario como null
        try {
            funcionario.setIdFuncionario(null);
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
        Funcionario funcionario = mostrarUmFuncionarioPeloId(idFuncionario);
        funcionario.setFoto(caminhoFoto);
        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> mostrarFuncionariosPeloCargo(Integer idCargo){
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        List<Funcionario> funcionarios = funcionarioRepository.findByCargo(cargo);
        return funcionarios;
    }
}
