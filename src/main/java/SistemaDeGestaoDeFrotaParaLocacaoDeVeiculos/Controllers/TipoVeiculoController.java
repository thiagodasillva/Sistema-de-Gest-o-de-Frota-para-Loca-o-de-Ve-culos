package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.Controllers;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.TipoVeiculoRequestDTO;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.TipoVeiculoResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.sevices.TipoVeiucloService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/tv")
@Validated
public class TipoVeiculoController {

    public TipoVeiucloService tipoVeiucloService;

    public TipoVeiculoController(TipoVeiucloService tipoVeiucloService) {
        this.tipoVeiucloService = tipoVeiucloService;
    }

    @GetMapping
    public ResponseEntity<List<TipoVeiculoResponseDTO>> listarVeiculos(){
        List<TipoVeiculoResponseDTO> tipoVeiculoDTOS = tipoVeiucloService.getTipos();
        return ResponseEntity.ok(tipoVeiculoDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoVeiculoResponseDTO> listarVeiculosPorId(@PathVariable Long id){
        TipoVeiculoResponseDTO tipoVeiculoDTO = tipoVeiucloService.getTipoId(id);
        return ResponseEntity.ok(tipoVeiculoDTO);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TipoVeiculoResponseDTO> listarPorNome(@PathVariable String name){
        TipoVeiculoResponseDTO tipoVeiculoDTO = tipoVeiucloService.getTipoByname(name);
        return ResponseEntity.ok(tipoVeiculoDTO);
    }

    @PostMapping
    public ResponseEntity<TipoVeiculoResponseDTO> criarTipo(@Valid @RequestBody TipoVeiculoRequestDTO tipoVeiculoDTO){
        TipoVeiculoResponseDTO tipoVeiculoDTO1 = tipoVeiucloService.criarTipo(tipoVeiculoDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(tipoVeiculoDTO1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoVeiculoResponseDTO> atualirVeiculo(@PathVariable Long id,@Valid @RequestBody TipoVeiculoRequestDTO tipoVeiculoDTO){
        TipoVeiculoResponseDTO tipoVeiculoDTO1 = tipoVeiucloService.updateTipo(id,tipoVeiculoDTO);
        return ResponseEntity.ok(tipoVeiculoDTO1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVeiculo(@PathVariable Long id){
        tipoVeiucloService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/preco/{id}")
    public ResponseEntity<TipoVeiculoResponseDTO> alterarPrecoDiario(@PathVariable Long id,@Min(3) @RequestParam BigDecimal precoDiario){
        TipoVeiculoResponseDTO tipoVeiculoDTO = tipoVeiucloService.updatePrecoDiario(id,precoDiario);
        return ResponseEntity.ok(tipoVeiculoDTO);
    }







}
