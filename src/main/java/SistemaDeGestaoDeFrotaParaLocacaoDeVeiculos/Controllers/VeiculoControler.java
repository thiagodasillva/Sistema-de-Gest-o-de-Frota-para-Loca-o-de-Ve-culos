package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.Controllers;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.VeiculoRequestDTO;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.VeiculoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.sevices.VeiculoService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/veiculo")
@Validated
public class VeiculoControler {


private VeiculoService veiculoService;

    public VeiculoControler(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> listarVeiculos(){
        List<VeiculoResponseDTO> veiculoDTOS = veiculoService.listarVeiculos();
        return ResponseEntity.ok(veiculoDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> getVeiculoPorId(@PathVariable Long id){
        VeiculoResponseDTO veiculoDTO = veiculoService.getVeiculo(id);
        return ResponseEntity.ok(veiculoDTO);

    }

    @GetMapping("/tipo/{id}")
    public ResponseEntity<List<VeiculoResponseDTO>> listarVeiculosPorTipo(@PathVariable Long id){
        List<VeiculoResponseDTO> veiculoDTO = veiculoService.listarPorTipoId(id);
        return ResponseEntity.ok(veiculoDTO);
    }


    @GetMapping("/status")
    public ResponseEntity<List<VeiculoResponseDTO>> listarPorDisponibilidade(){
        List<VeiculoResponseDTO> veiculoDTOS = veiculoService.listarPorDisponibilidade();
        return ResponseEntity.ok(veiculoDTOS);
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<VeiculoResponseDTO>> listarPorMarca(@PathVariable String marca){
        List<VeiculoResponseDTO> veiculoDTOS = veiculoService.listarPorMarca(marca);
        return ResponseEntity.ok(veiculoDTOS);
    }

    @PostMapping
    public ResponseEntity<VeiculoResponseDTO> criarVeiculo(@Valid @RequestBody VeiculoRequestDTO veiculoDTO){
        VeiculoResponseDTO veiculoDTO1 = veiculoService.creatVeiculo(veiculoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoDTO1);
    }


    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> atualizarVeiculo(@PathVariable Long id,@Valid @RequestBody VeiculoRequestDTO veiculoDTO ){
        VeiculoResponseDTO veiculoDTO1 = veiculoService.updateVeiculo(id,veiculoDTO);
        return ResponseEntity.ok(veiculoDTO1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVeiculo(@PathVariable Long id){
        veiculoService.deleteVeiculo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/disponibilidade")
    public ResponseEntity<Boolean> verificarDisponibilidade(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        boolean disponivel = veiculoService.verifcarDisponibilidade(id, inicio, fim);
        return ResponseEntity.ok(disponivel);
    }



}
