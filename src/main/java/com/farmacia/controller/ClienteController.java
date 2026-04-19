package com.farmacia.controller;

import com.farmacia.model.Cliente;
import com.farmacia.model.Documento;
import com.farmacia.repository.ClienteRepository;
import com.farmacia.repository.DocumentoRepository;
import com.farmacia.service.S3Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private DocumentoRepository docRepo;

    @Autowired
    private S3Service s3Service;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> upload(
            @RequestParam("cpf") String cpf,
            @RequestParam("file") MultipartFile file) {

        try {
            // ✅ validação
            if (cpf == null || cpf.isEmpty()) {
                return ResponseEntity.badRequest().body("CPF obrigatório");
            }

            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("Arquivo obrigatório");
            }

            // 🔎 busca ou cria cliente
            Cliente cliente = clienteRepo.findByCpf(cpf);

            if (cliente == null) {
                cliente = new Cliente();
                cliente.setCpf(cpf);
                clienteRepo.save(cliente);
            }

            // ☁️ upload para S3
            String url = s3Service.upload(file, cpf);

            // 💾 salvar no banco
            Documento doc = new Documento();
            doc.setNomeArquivo(file.getOriginalFilename());
            doc.setCaminho(url); // agora salva URL do S3
            doc.setCliente(cliente);

            docRepo.save(doc);

            return ResponseEntity.ok(url);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro: " + e.getMessage());
        }
    }
}
