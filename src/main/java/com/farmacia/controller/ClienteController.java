package com.farmacia.controller;

import com.farmacia.model.*;
import com.farmacia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private DocumentoRepository docRepo;

    @PostMapping("/upload")
    public String upload(
            @RequestParam String cpf,
            @RequestParam MultipartFile file) throws IOException {

        Cliente cliente = clienteRepo.findByCpf(cpf);

        if (cliente == null) {
            cliente = new Cliente();
            cliente.setCpf(cpf);
            clienteRepo.save(cliente);
        }

        String pasta = "uploads/" + cpf;
        new File(pasta).mkdirs();

        String caminho = pasta + "/" + file.getOriginalFilename();
        file.transferTo(new File(caminho));

        Documento doc = new Documento();
        doc.setNomeArquivo(file.getOriginalFilename());
        doc.setCaminho(caminho);
        doc.setCliente(cliente);

        docRepo.save(doc);

        return "Upload realizado com sucesso 🚀";
    }
}
