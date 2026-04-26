package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.services;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.AluguelResponseDTO;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.AluguelResquestDTO;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.exception.EntityNotFoundException;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.*;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.repository.AluguelRepository;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.repository.ClienteRepository;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.repository.VeiculoRepository;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.sevices.AluguelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AluguelServiceTest {

    @Mock
    private AluguelRepository aluguelRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private AluguelService aluguelService;



    // codigo exemplo
    @Test
    public void naoDevePermitirCancelarAluguelQueJaEstaCancelado() {
        Aluguel aluguel = new Aluguel();
        aluguel.setId(1L);
        aluguel.setStatus(AluguelStatus.CANCELADO); // Já está cancelado!

        when(aluguelRepository.findById(1L)).thenReturn(Optional.of(aluguel));

        assertThrows(RuntimeException.class, () -> { aluguelService.calcelarAluguel(1L);
        }, "Somente aluguéis ativos podem ser cancelados");
    }

    @Test
    public void calculoDeveSerCorreto(){

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setId(1L);
        tipoVeiculo.setPrecoDiario(BigDecimal.valueOf(150.00));

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setTipoVeiculo(tipoVeiculo);

        Aluguel aluguel = new Aluguel();
        aluguel.setId(1L);
        aluguel.setVeiculo(veiculo);
        aluguel.setTaxaFixa(BigDecimal.valueOf(50.00));

        //converter o valor para o formato correto
        String inicio = "2025-04-08 12:30";
        String fim = "2025-04-12 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        aluguel.setDataInicio(LocalDateTime.parse(inicio,formatter));
        aluguel.setDataFim(LocalDateTime.parse(fim,formatter));

        when(aluguelRepository.findById(1L)).thenReturn(Optional.of(aluguel));

        BigDecimal valor =  aluguelService.calcularAluguel(aluguel.getId());

        assertEquals(new BigDecimal("650.0"),valor);
        assertEquals(new BigDecimal("650.0"),aluguel.getValorTotal());

        Mockito.verify(aluguelRepository).findById(1L);
    }

    @Test
    public void naoDeveCalcularAluguelParaAluguelFinalizado(){

        Aluguel aluguel = new Aluguel();
        aluguel.setId(1L);
        aluguel.setStatus(AluguelStatus.FINALIZADO);

        when(aluguelRepository.findById(1L)).thenReturn(Optional.of(aluguel));

        assertThrows(RuntimeException.class,() -> aluguelService.calcularAluguel(1L));
    }

    @Test
    public void deveFInalisarAluguelCorretamente(){

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setId(1L);
        tipoVeiculo.setPrecoDiario(BigDecimal.valueOf(150.00));

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setTipoVeiculo(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Aluguel aluguel = new Aluguel();
        aluguel.setId(1L);
        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);
        aluguel.setTaxaFixa(BigDecimal.valueOf(50.00));

        String inicio = "2025-04-08 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        aluguel.setDataInicio(LocalDateTime.parse(inicio,formatter));

        when(aluguelRepository.findById(1L)).thenReturn(Optional.of(aluguel));

        AluguelResponseDTO aluguelResponseDTO = aluguelService.finalizar(1L);

        assertEquals(AluguelStatus.FINALIZADO,aluguel.getStatus());
        assertEquals(VeiculoStatus.DISPONIVEL,aluguel.getVeiculo().getStatus());
        assertNotNull(aluguel.getDataFim());
        assertNotNull(aluguelResponseDTO.getValorTotal());

    }


    @Test
    public void deveSerLancadoExcecaoCasoOAluguelNaoForEncontrado(){

        when(aluguelRepository.findById(9L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> aluguelService.getAluguel(9l));
    }

    @Test
    public void deveLancarExcecaoQuandoNaoHouverAluguelAtivo(){
        when(aluguelRepository.findByStatus(AluguelStatus.ATIVO)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()-> {aluguelService.listAlugueisAtivos();});
    }

    @Test
    public void nãoDeveCriarAluguelComVeiculosIndisponiveis(){

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setId(1L);

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo.setStatus(VeiculoStatus.ALUGADO);

        AluguelResquestDTO aluguel = new AluguelResquestDTO();
        aluguel.setVeiculoId(1L);
        aluguel.setClienteId(1L);

        assertThrows(RuntimeException.class,()->{aluguelService.creat(aluguel);});


    }

    @Test
    public void deveListarAluguelPorCliente(){

        Cliente cliente= new Cliente();
        cliente.setId(1L);

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setId(1L);

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo.setStatus(VeiculoStatus.ALUGADO);

        Aluguel aluguel = new Aluguel();
        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(aluguelRepository.findByCliente(cliente)).thenReturn(Optional.of(Arrays.asList(aluguel)));

        List<AluguelResponseDTO> resultado = aluguelService.listarAluguelPorCliente(cliente.getId());

        assertEquals(1,resultado.size());
        verify(clienteRepository).findById(1L);
        verify(aluguelRepository).findByCliente(cliente);




    }



}
