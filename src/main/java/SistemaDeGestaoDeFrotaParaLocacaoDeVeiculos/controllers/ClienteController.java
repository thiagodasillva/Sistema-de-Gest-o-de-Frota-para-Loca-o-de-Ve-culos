package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.controllers;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.ClienteRequestDTO;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.ClienteResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.sevices.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@Validated
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes(){
        List<ClienteResponseDTO> clienteDTOList = clienteService.clientes();
        return ResponseEntity.ok(clienteDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> listarClientePorId(@PathVariable Long id){
        ClienteResponseDTO clienteDTO = clienteService.getCliente(id);
        return ResponseEntity.ok(clienteDTO);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorCPF(@PathVariable String cpf){
        ClienteResponseDTO clienteDTO = clienteService.getClienteCPF(cpf);
        return ResponseEntity.ok(clienteDTO);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criarCLiente(@Valid @RequestBody ClienteRequestDTO clienteDTO){
        ClienteResponseDTO clienteDTO1 = clienteService.creatCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteDTO1);


    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable Long id,@Valid @RequestBody ClienteRequestDTO clienteDTO){
        ClienteResponseDTO clienteDTOatualizado = clienteService.updateCliente(id,clienteDTO);
        return ResponseEntity.ok(clienteDTOatualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id){
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }


}
