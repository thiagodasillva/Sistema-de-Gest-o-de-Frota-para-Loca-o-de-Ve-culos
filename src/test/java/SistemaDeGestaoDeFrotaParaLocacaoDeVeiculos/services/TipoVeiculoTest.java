package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.services;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.TipoVeiculoRequestDTO;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.TipoVeiculoResponseDTO;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.exception.EntityNotFoundException;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.Cliente;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.TipoVeiculo;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.Veiculo;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.repository.TipoVeiculoRepository;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.repository.VeiculoRepository;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.sevices.TipoVeiucloService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TipoVeiculoTest {

    @Mock
    private TipoVeiculoRepository tipoVeiculoRepository;
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private VeiculoRepository veiculoRepository;
    @InjectMocks
    private TipoVeiucloService tipoVeiucloService;

    @Test
    public void deveBuscarOVeiculoCorretoPorId(){

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setId(1L);

        TipoVeiculoResponseDTO tipoVeiculoResponseDTO = new TipoVeiculoResponseDTO();
        tipoVeiculoResponseDTO.setId(1L);

        when(tipoVeiculoRepository.findById(1L)).thenReturn(Optional.of(tipoVeiculo));
        when(modelMapper.map(any(TipoVeiculo.class),eq(TipoVeiculoResponseDTO.class))).thenReturn(tipoVeiculoResponseDTO);

        TipoVeiculoResponseDTO resultado = tipoVeiucloService.getTipoId(tipoVeiculo.getId());

        assertNotNull(resultado);
        assertEquals(tipoVeiculo.getId(),resultado.getId());
        verify(tipoVeiculoRepository).findById(tipoVeiculo.getId());


    }

    @Test
    public void deveLancarExcecaoQuandoNãoEncontrarOVeiculoBuscadoPorId(){

        when(tipoVeiculoRepository.findById(9L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,() -> {tipoVeiucloService.getTipoId(9L);});
        verify(tipoVeiculoRepository).findById(9L);

    }
    @Test
    public void deveBuscarOVeiculoCorretoPorNome(){

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("tipo 1");

        TipoVeiculoResponseDTO tipoVeiculoResponseDTO = new TipoVeiculoResponseDTO();
        tipoVeiculoResponseDTO.setName("tipo 1");

        when(tipoVeiculoRepository.findByName("tipo 1")).thenReturn(Optional.of(tipoVeiculo));
        when(modelMapper.map(any(TipoVeiculo.class),eq(TipoVeiculoResponseDTO.class))).thenReturn(tipoVeiculoResponseDTO);

        TipoVeiculoResponseDTO resultado = tipoVeiucloService.getTipoByname(tipoVeiculo.getName());

        assertNotNull(resultado);
        assertEquals(tipoVeiculo.getName(),resultado.getName());
        verify(tipoVeiculoRepository).findByName(tipoVeiculo.getName());


    }

    @Test
    public void deveLancarExcecaoQuandoNãoEncontrarOVeiculoBuscadoPorNome(){

        when(tipoVeiculoRepository.findByName("generico")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,() -> {tipoVeiucloService.getTipoByname("generico");});
        verify(tipoVeiculoRepository).findByName("generico");

    }

    @Test
    public void deveCriarUmTipoCorretamente(){
        TipoVeiculoRequestDTO tipoVeiculoRequestDTO = new TipoVeiculoRequestDTO();
        tipoVeiculoRequestDTO.setName("tipo1");

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("tipo1");

        TipoVeiculo tipoVeiculoSalvo = new TipoVeiculo();
        tipoVeiculo.setName("tipo1");
        tipoVeiculo.setId(1L);

        ArgumentCaptor<TipoVeiculo> captor = ArgumentCaptor.forClass(TipoVeiculo.class);

        when(modelMapper.map(any(TipoVeiculoRequestDTO.class),eq(TipoVeiculo.class))).thenReturn(tipoVeiculo);
        when(tipoVeiculoRepository.save(any(TipoVeiculo.class))).thenReturn(tipoVeiculoSalvo);
        when(modelMapper.map(tipoVeiculo, TipoVeiculoResponseDTO.class)).thenReturn(new TipoVeiculoResponseDTO());


        tipoVeiucloService.criarTipo(tipoVeiculoRequestDTO);


        verify(tipoVeiculoRepository).save(captor.capture());
        TipoVeiculo tipoVeiculo1 = captor.getValue();
        assertEquals("tipo1",tipoVeiculo1.getName());

    }


    @Test
    public void deveAtualizarOPrecoDiarioComSucesso(){
        BigDecimal novoPreco = new BigDecimal("150.00");

        TipoVeiculo tipoVeiculoExistente = new TipoVeiculo();
        tipoVeiculoExistente.setId(1L);
        tipoVeiculoExistente.setPrecoDiario(new BigDecimal("100.00"));
        tipoVeiculoExistente.setName("SUV");

        TipoVeiculoResponseDTO responseDTO = new TipoVeiculoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setPrecoDiario(novoPreco);
        responseDTO.setName("SUV");

        when(tipoVeiculoRepository.findById(1L)).thenReturn(Optional.of(tipoVeiculoExistente));
        when(tipoVeiculoRepository.save(tipoVeiculoExistente)).thenReturn(tipoVeiculoExistente);
        when(modelMapper.map(any(TipoVeiculo.class),eq(TipoVeiculoResponseDTO.class))).thenReturn(responseDTO);


        TipoVeiculoResponseDTO responseDTO1 = tipoVeiucloService.updatePrecoDiario(1L,novoPreco);

        assertNotNull(responseDTO1);
        assertEquals(novoPreco,responseDTO1.getPrecoDiario());
        assertEquals(1L,responseDTO1.getId());
        verify(tipoVeiculoRepository).findById(1L);
        verify(tipoVeiculoRepository).save(tipoVeiculoExistente);

    }



    @Test
    void deveAlterarOPrecoDoVeiculoAntesDeSalvar() {

        BigDecimal precoAntigo = new BigDecimal("100.00");
        BigDecimal novoPreco = new BigDecimal("200.00");

        TipoVeiculo tipoVeiculoExistente = new TipoVeiculo();
        tipoVeiculoExistente.setId(1L);
        tipoVeiculoExistente.setPrecoDiario(precoAntigo);

        ArgumentCaptor<TipoVeiculo> captor = ArgumentCaptor.forClass(TipoVeiculo.class);

        when(tipoVeiculoRepository.findById(1L)).thenReturn(Optional.of(tipoVeiculoExistente));
        when(tipoVeiculoRepository.save(any(TipoVeiculo.class))).thenReturn(tipoVeiculoExistente);
        when(modelMapper.map(any(TipoVeiculo.class), eq(TipoVeiculoResponseDTO.class)))
                .thenReturn(new TipoVeiculoResponseDTO());

        tipoVeiucloService.updatePrecoDiario(1L, novoPreco);

        verify(tipoVeiculoRepository).save(captor.capture());
        TipoVeiculo veiculoSalvo = captor.getValue();
        assertEquals(novoPreco, veiculoSalvo.getPrecoDiario());
        assertNotEquals(precoAntigo, veiculoSalvo.getPrecoDiario());
    }

    @Test
    public void deveDeletarOTipoDeVeiculoApenasSemVeiculosAssociados(){

        TipoVeiculo tipoVeiculoExistente = new TipoVeiculo();
        tipoVeiculoExistente.setId(1L);
        tipoVeiculoExistente.setPrecoDiario(new BigDecimal(100.00));

        when(tipoVeiculoRepository.findById(1L)).thenReturn(Optional.of(tipoVeiculoExistente));
        when(veiculoRepository.existsByTipoVeiculo(tipoVeiculoExistente)).thenReturn(false);


        tipoVeiucloService.delete(1L);

        verify(tipoVeiculoRepository).delete(tipoVeiculoExistente);


    }

    @Test
    public void deveLancarExcecaoAoTentarDeletarOTipoDeVeiculoApenasComVeiculosAssociados(){

        TipoVeiculo tipoVeiculoExistente = new TipoVeiculo();
        tipoVeiculoExistente.setId(1L);
        tipoVeiculoExistente.setPrecoDiario(new BigDecimal(100.00));


        when(tipoVeiculoRepository.findById(1L)).thenReturn(Optional.of(tipoVeiculoExistente));
        when(veiculoRepository.existsByTipoVeiculo(tipoVeiculoExistente)).thenReturn(true);

        assertThrows(RuntimeException.class,()->{tipoVeiucloService.delete(1L);},"Não é possível deletar tipo com veículos associados");

    }




 }
