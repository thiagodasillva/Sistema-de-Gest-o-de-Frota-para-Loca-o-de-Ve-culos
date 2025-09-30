package Controllers;


import DTOs.AluguelResponseDTO;
import DTOs.AluguelResquestDTO;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sevices.AluguelService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/aluguel")
@Validated
public class AluguelController {

    private AluguelService aluguelService;

    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    @GetMapping
    public ResponseEntity<List<AluguelResponseDTO>> listarTodosAlugueis() {
        return ResponseEntity.ok(aluguelService.listarAlugueis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AluguelResponseDTO> buscarAluguelPorId(@PathVariable Long id) {
        return ResponseEntity.ok(aluguelService.getAluguel(id));
    }

    @GetMapping("/cliente/id")
    public ResponseEntity<List<AluguelResponseDTO>> buscarPorCliente(@PathVariable Long id){
        List<AluguelResponseDTO> aluguelDTOS = aluguelService.listarAluguelPorCliente(id);
        return ResponseEntity.ok(aluguelDTOS);
    }

    @GetMapping("/veiculo/id")
    public ResponseEntity<List<AluguelResponseDTO>> buscarPorVeiculo(@PathVariable Long id){
        List<AluguelResponseDTO> aluguelDTOS = aluguelService.listarALuguelporVeiculo(id);
        return ResponseEntity.ok(aluguelDTOS);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<AluguelResponseDTO>> listarPorPeriodo(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime fim){
        List<AluguelResponseDTO> aluguelDTOS = aluguelService.listarAluguelPorPeriodo(inicio,fim);
        return ResponseEntity.ok(aluguelDTOS);
    }

    @PostMapping
    public ResponseEntity<AluguelResponseDTO> criarAluguel(@Valid @RequestBody AluguelResquestDTO aluguelDTO) {
        AluguelResponseDTO novoAluguel = aluguelService.creat(aluguelDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAluguel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AluguelResponseDTO> atualizarAluguel(@PathVariable Long id,@Valid @RequestBody AluguelResquestDTO aluguelDTO){
        AluguelResponseDTO aluguelDTO1 = aluguelService.update(id,aluguelDTO);
        return ResponseEntity.ok(aluguelDTO1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluguel(@PathVariable Long id){
        aluguelService.delete(id);
        return ResponseEntity.noContent().build();
    }



    // ROTAS que representam AÇÕES sobre um recurso
    @PostMapping("/{id}/finalizar")
    public ResponseEntity<AluguelResponseDTO> finalizarAluguel(@PathVariable Long id) {
        AluguelResponseDTO aluguelFinalizado = aluguelService.finalizar(id);
        return ResponseEntity.ok(aluguelFinalizado);
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<AluguelResponseDTO> cancelarAluguel(@PathVariable Long id) {
        AluguelResponseDTO aluguelCancelado = aluguelService.calcelarAluguel(id);
        return ResponseEntity.ok(aluguelCancelado);
    }

    // Endpoint para obter um valor calculado
    @GetMapping("/{id}/valor")
    public ResponseEntity<BigDecimal> calcularValorTotal(@PathVariable Long id) {
        BigDecimal valorTotal = aluguelService.calcularAluguel(id);
        return ResponseEntity.ok(valorTotal);
    }





}
