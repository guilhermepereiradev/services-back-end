package com.soulcode.servicos.service;

import com.soulcode.servicos.model.Cliente;
import com.soulcode.servicos.repository.ClienteRepository;
import com.soulcode.servicos.service.exceptions.EntityNotFoundException;
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
    public List<Cliente> mostrarTodosClientes(){
        return clienteRepository.findAll();
    }


    @Cacheable(value = "clientesCache", key = "#idCliente") // clientesCache::1
    public Cliente mostrarClientePeloId(Integer idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        return cliente.orElseThrow( () ->
            new EntityNotFoundException("Cliente não encontrado: "+ idCliente)
        );
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
        mostrarClientePeloId(idCliente);
        clienteRepository.deleteById(idCliente);
    }

    @CachePut(value = "clientesCache", key = "#cliente.idCliente") //atualiza/substitui a info no cache de acordo com a key
    public Cliente editarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }
}
