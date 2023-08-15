package com.soulcode.servicos.service;

import com.soulcode.servicos.model.Cliente;
import com.soulcode.servicos.model.Endereco;
import com.soulcode.servicos.repository.ClienteRepository;
import com.soulcode.servicos.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Cacheable("enderecoCache")
    public List<Endereco> mostrarEnderecos(){
        return enderecoRepository.findAll();
    }

    @CachePut(value = "enderecoCache", key = "#idCliente")
    public Endereco cadastrarEndereco(Endereco endereco, Integer idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        endereco.setIdEndereco(idCliente);
        enderecoRepository.save(endereco);
        cliente.get().setEndereco(endereco);
        clienteRepository.save(cliente.get());

        return endereco;
    }

    @Cacheable(value = "enderecoCache", key = "#idEndereco")
    public Endereco mostrarEnderecoPeloId(Integer idEndereco){
        Optional<Endereco> endereco = enderecoRepository.findById(idEndereco);
        return  endereco.orElseThrow();
    }

    @Cacheable(value = "enderecoCache", key = "#idCliente")
    public Endereco mostrarEnderecoPeloCliente(Integer idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        Endereco endereco = cliente.get().getEndereco();
        return endereco;
    }

    @CachePut(value = "enderecoCache", key = "#endereco.idEndereco")
    public Endereco atualiazarEndereco(Integer idCliente, Endereco endereco){
       Optional<Cliente> cliente = clienteRepository.findById(idCliente);
       Endereco enderecoAntigo = cliente.get().getEndereco();

       endereco.setIdEndereco(enderecoAntigo.getIdEndereco());

       cliente.get().setEndereco(endereco);
       return enderecoRepository.save(endereco);
    }
    @CacheEvict(value = "enderecoCache", key = "#idCliente", allEntries = true)
    public void excluirEnderecoPeloCliente(Integer idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        int IdEndereco = cliente.get().getEndereco().getIdEndereco();
        cliente.get().setEndereco(null);
        enderecoRepository.deleteById(IdEndereco);
    }

    @CacheEvict(value = "enderecoCache", key = "#idEndereco", allEntries = true)
    public void excluirEndereco(Integer idEndereco){
        Optional<Endereco> endereco = enderecoRepository.findById(idEndereco);
        enderecoRepository.deleteById(idEndereco);
    }
}
