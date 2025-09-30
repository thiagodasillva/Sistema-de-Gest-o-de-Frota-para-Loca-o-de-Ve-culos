package Controllers;

import DTOs.TipoVeiculoRequestDTO;
import DTOs.TipoVeiculoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sevices.TipoVeiucloService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/tv")
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
    public ResponseEntity<TipoVeiculoResponseDTO> criarTipo(@RequestBody TipoVeiculoRequestDTO tipoVeiculoDTO){
        TipoVeiculoResponseDTO tipoVeiculoDTO1 = tipoVeiucloService.criarTipo(tipoVeiculoDTO);
        return  ResponseEntity.ok(tipoVeiculoDTO1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoVeiculoResponseDTO> atualirVeiculo(@PathVariable Long id, @RequestBody TipoVeiculoRequestDTO tipoVeiculoDTO){
        TipoVeiculoResponseDTO tipoVeiculoDTO1 = tipoVeiucloService.updateTipo(id,tipoVeiculoDTO);
        return ResponseEntity.ok(tipoVeiculoDTO1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVeiculo(Long id){
        tipoVeiucloService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/preco/{id}")
    public ResponseEntity<TipoVeiculoResponseDTO> alterarPrecoDiario(@PathVariable Long id,@RequestParam BigDecimal precoDiario){
        TipoVeiculoResponseDTO tipoVeiculoDTO = tipoVeiucloService.updatePrecoDiario(id,precoDiario);
        return ResponseEntity.ok(tipoVeiculoDTO);
    }







}
