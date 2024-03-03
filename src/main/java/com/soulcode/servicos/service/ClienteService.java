package com.soulcode.servicos.service;

import com.soulcode.servicos.model.Cliente;
import com.soulcode.servicos.model.exception.ClienteNaoEncontradoException;
import com.soulcode.servicos.repository.ClienteRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Cacheable("clientesCache")
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }


    @Cacheable(value = "clientesCache", key = "#id")
    public Cliente buscarOuFalhar(Integer id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }


    public Cliente buscarClientePeloEmail(String email) {
        return clienteRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ClienteNaoEncontradoException(
                                String.format("Cliente não encontrado com email: '%s'", email)
                        )
                );
    }

    public Cliente cadastrarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @CacheEvict(value = "clientesCache", key = "#idCliente", allEntries = true) //allEntries = true, quando executado o cache tbm é excluido
    public void excluirCliente(Integer idCliente){
        buscarOuFalhar(idCliente);
        clienteRepository.deleteById(idCliente);
    }
}
