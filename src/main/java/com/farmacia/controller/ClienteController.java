package com.farmacia.controller;

import com.farmacia.model.Cliente;
import com.farmacia.model.Documento;
import com.farmacia.repository.ClienteRepository;
import com.farmacia.repository.DocumentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> upload(
            @RequestParam("cpf") String cpf,
            @RequestParam("file") MultipartFile file) {

        try {
            // 🔍 validações básicas
            if (cpf == null || cpf.isEmpty()) {
                return ResponseEntity.badRequest().body("CPF obrigatório");
            }

            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("Arquivo obrigatório");
            }

            // 🔎 busca cliente
            Cliente cliente = clienteRepo.findByCpf(cpf);

            if (cliente == null) {
                cliente = new Cliente();
                cliente.setCpf(cpf);
                clienteRepo.save(cliente);
            }

            // 📁 cria pasta
            String pasta = "uploads/" + cpf;
            File dir = new File(pasta);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 💾 salva arquivo
            String caminho = pasta + "/" + file.getOriginalFilename();
            File destino = new File(caminho);
            file.transferTo(destino);

            // 🗄️ salva no banco
            Documento doc = new Documento();
            doc.setNomeArquivo(file.getOriginalFilename());
            doc.setCaminho(caminho);
            doc.setCliente(cliente);

            docRepo.save(doc);

            return ResponseEntity.ok("Upload realizado com sucesso 🚀");

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao salvar arquivo: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro inesperado: " + e.getMessage());
        }
    }
}

