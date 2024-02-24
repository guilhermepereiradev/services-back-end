package com.soulcode.servicos.service;

import com.soulcode.servicos.model.Cliente;
import com.soulcode.servicos.model.exception.ClienteNaoEncontradoException;
import com.soulcode.servicos.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Cacheable("clientesCache") // so chama o return se o cache expirar // clientesCache::[]
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }


    @Cacheable(value = "clientesCache", key = "#idCliente") // clientesCache::1
    public Cliente buscarOuFalhar(Integer idCliente){
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNaoEncontradoException(idCliente));
    }


    public Cliente mostrarClientePeloEmail(String email){
        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
        return cliente.orElseThrow();
    }

    @CachePut(value = "clientesCache", key = "#cliente.idCliente")
    public Cliente cadastrarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @CacheEvict(value = "clientesCache", key = "#idCliente", allEntries = true) //allEntries = true, quando executado o cache tbm é excluido
    public void excluirCliente(Integer idCliente){
        buscarOuFalhar(idCliente);
        clienteRepository.deleteById(idCliente);
    }

    @CachePut(value = "clientesCache", key = "#cliente.idCliente") //atualiza/substitui a info no cache de acordo com a key
    public Cliente editarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }
}
