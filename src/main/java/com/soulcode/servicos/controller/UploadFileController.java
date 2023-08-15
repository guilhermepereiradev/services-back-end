package com.soulcode.servicos.controller;

import com.soulcode.servicos.service.FuncionarioService;
import com.soulcode.servicos.util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/servicos")
public class UploadFileController {

    @Autowired
    FuncionarioService funcionarioService;

    @PostMapping("/funcionarios/envioFoto/{idFuncionario}")
    public ResponseEntity<Void> enviarFoto(@PathVariable Integer idFuncionario, MultipartFile file, @RequestParam("nome") String nome){
        String fileName = nome;
        String uploadDir = "/home/guilherme/Pictures/fotosFunc/";
        String nomeMaisCaminho = uploadDir+nome;

        try{
            UploadFile.saveFile(uploadDir, fileName, file);
            funcionarioService.salvarFoto(idFuncionario, nomeMaisCaminho);
        } catch (IOException e) {
            System.out.println("O arquivo não foi enviado"+e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
