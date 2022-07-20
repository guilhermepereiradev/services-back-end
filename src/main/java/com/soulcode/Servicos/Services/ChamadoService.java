package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Models.StatusChamado;
import com.soulcode.Servicos.Repositories.ChamadoRepository;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    ChamadoRepository chamadoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Cacheable("chamadosCache")
    public List<Chamado> mostrarChamados(){
        return chamadoRepository.findAll();
    }

    @Cacheable(value = "chamadosCache", key = "#idChamado")
    public Chamado mostrarChamadoPeloId(Integer idChamado){
        Optional<Chamado> chamados = chamadoRepository.findById(idChamado);
        return chamados.orElseThrow();
    }

    @Cacheable(value = "chamadosCache", key = "#idCliente")
    public List<Chamado> buscarChamadosPeloCliente(Integer idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        return chamadoRepository.findByCliente(cliente);
    }

    @Cacheable(value = "chamadosCache", key = "#idFuncionario")
    public List<Chamado> buscarChamadoPeloFuncionario(Integer idFuncionario){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return chamadoRepository.findByFuncionario(funcionario);
    }

    @Cacheable(value = "chamadosCache", key = "#status")
    public List<Chamado> buscarChamadoPeloStatus(String status){
        return chamadoRepository.findByStatus(status);
    }

    @Cacheable(value = "chamadosCache", key = "#date1")
    public List<Chamado> buscarChamadoPorIntervaloData(Date date1, Date date2){
        return chamadoRepository.findByIntervaloData(date1, date2);
    }

    //  cadastrar novo um novo chamado
    //  temos tre regras
    //      1º já devemos informar o cliente
    //      2º não devemos atribuir um funcionario
    //      3º o status deve ser RECEBIDO

    //serviço para cadastro de novo chamado

    @CachePut(value = "chamadosCache", key = "#chamado.idChamado")
    public Chamado cadastrarChamado(Chamado chamado, Integer idCliente){
        chamado.setStatus(StatusChamado.RECEBIDO);
        chamado.setFuncionario(null);
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        chamado.setCliente(cliente.get());
        return chamadoRepository.save(chamado);
    }

    // metodo para exclusão de um chamado
    @CacheEvict(value = "chamadosCache", key = "#idChamado", allEntries = true)
    public void excluirChamado(Integer idChamado){
        chamadoRepository.deleteById(idChamado);
    }

    // metodo para editar um chamado
    // devemos preservar o cliente e o funcionario

    @CachePut(value = "chamadosCache", key = "#chamado.idChamado")
    public Chamado editarChamado(Chamado chamado, Integer idChamado){
        Chamado chamadoSemAlteracao = mostrarChamadoPeloId(idChamado);
        Funcionario funcionario = chamadoSemAlteracao.getFuncionario();
        Cliente cliente = chamadoSemAlteracao.getCliente();

        chamado.setCliente(cliente);
        chamado.setFuncionario(funcionario);
        return chamadoRepository.save(chamado);
    }

    // metodo para atribuir um funcionario para determinado chamado
    @CachePut(value = "chamadosCache", key = "#idChamado")
    public Chamado atribuirFuncionario(Integer idChamado, Integer idFuncionario){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        Chamado chamado = mostrarChamadoPeloId(idChamado);
        chamado.setFuncionario(funcionario.get());
        chamado.setStatus(StatusChamado.ATRIBUIDO);
        return chamadoRepository.save(chamado);
    }

    //metodo para modificar o status do chamado
    @CachePut(value = "chamadosCache", key = "#idChamado")
    public Chamado modificarStatus(Integer idChamado, String status){
        Chamado chamado = mostrarChamadoPeloId(idChamado);

        if(chamado.getFuncionario() != null) {
            chamado.setStatus(StatusChamado.ATRIBUIDO);
            switch (status) {
                case "ATRIBUIDO": {
                    break;
                }
                case "CONCLUIDO": {
                    chamado.setStatus(StatusChamado.CONCLUIDO);
                    break;
                }
                case "ARQUIVADO": {
                    chamado.setStatus(StatusChamado.ARQUIVADO);
                    break;
                }
                case "RECEBIDO": {
                    chamado.setStatus(StatusChamado.RECEBIDO);
                    break;
                }
            }
        } else {

        }
        return chamadoRepository.save(chamado);
    }

}
