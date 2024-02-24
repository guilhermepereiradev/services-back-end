package com.soulcode.servicos.service;

import com.soulcode.servicos.model.Chamado;
import com.soulcode.servicos.model.Cliente;
import com.soulcode.servicos.model.Funcionario;
import com.soulcode.servicos.model.StatusChamado;
import com.soulcode.servicos.model.exception.ChamadoNaoEncontradoException;
import com.soulcode.servicos.repository.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

//    @Autowired
//    private ClienteRepository clienteRepository;
//
//    @Autowired
//    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Cacheable("chamadosCache")
    public List<Chamado> listar(){
        return chamadoRepository.findAll();
    }

    @Cacheable(value = "chamadosCache", key = "#idChamado")
    public Chamado buscarOuFalhar(Integer idChamado){
        return chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new ChamadoNaoEncontradoException(idChamado));
    }

    @Cacheable(value = "chamadosCache", key = "#idCliente")
    public List<Chamado> bucarPeloCliente(Integer idCliente){
        Cliente cliente = clienteService.buscarOuFalhar(idCliente);
        return chamadoRepository.findByCliente(cliente);
    }

    @Cacheable(value = "chamadosCache", key = "#idFuncionario")
    public List<Chamado> buscarChamadoPeloFuncionario(Integer idFuncionario){
        Funcionario funcionario = funcionarioService.buscarOuFalhar(idFuncionario);
        return chamadoRepository.findByFuncionario(funcionario);
    }

    @Cacheable(value = "chamadosCache", key = "#status")
    public List<Chamado> buscarChamadoPeloStatus(StatusChamado status){
        return chamadoRepository.findByStatus(status);
    }

    @Cacheable(value = "chamadosCache", key = "#date1")
    public List<Chamado> buscarChamadoPorIntervaloData(Date date1, Date date2){
        return chamadoRepository.findByIntervaloData(date1, date2);
    }

    @Transactional
    @CachePut(value = "chamadosCache", key = "#chamado.idChamado")
    public Chamado cadastrarChamado(Chamado chamado, Integer idCliente){
        Cliente cliente = clienteService.buscarOuFalhar(idCliente);
        chamado.setStatus(StatusChamado.RECEBIDO);
        chamado.setCliente(cliente);

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
        Chamado chamadoSemAlteracao = buscarOuFalhar(idChamado);
        Funcionario funcionario = chamadoSemAlteracao.getFuncionario();
        Cliente cliente = chamadoSemAlteracao.getCliente();

        chamado.setCliente(cliente);
        chamado.setFuncionario(funcionario);
        return chamadoRepository.save(chamado);
    }

    // metodo para atribuir um funcionario para determinado chamado
    @CachePut(value = "chamadosCache", key = "#idChamado")
    public Chamado atribuirFuncionario(Integer idChamado, Integer idFuncionario){
//        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        Chamado chamado = buscarOuFalhar(idChamado);
//        chamado.setFuncionario(funcionario.get());
//        chamado.setStatus(StatusChamado.ATRIBUIDO);
        return chamadoRepository.save(chamado);
    }

    //metodo para modificar o status do chamado
    @CachePut(value = "chamadosCache", key = "#idChamado")
    public Chamado modificarStatus(Integer idChamado, String status){
        Chamado chamado = buscarOuFalhar(idChamado);

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
        }
        return chamadoRepository.save(chamado);
    }

}
