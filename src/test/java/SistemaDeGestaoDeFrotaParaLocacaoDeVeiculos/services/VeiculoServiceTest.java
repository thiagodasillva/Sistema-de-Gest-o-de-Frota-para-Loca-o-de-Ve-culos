package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.services;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.DisponibilidadeResponseDTO;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.VeiculoRequestDTO;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.VeiculoResponseDTO;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.exception.EntityNotFoundException;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.Aluguel;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.TipoVeiculo;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.Veiculo;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.VeiculoStatus;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.repository.TipoVeiculoRepository;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.repository.VeiculoRepository;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.sevices.VeiculoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeiculoServiceTest {

    @Mock
    public VeiculoRepository veiculoRepository;
    @Mock
    public ModelMapper modelMapper;
    @Mock
    public TipoVeiculoRepository tipoVeiculoRepository;
    @InjectMocks
    public VeiculoService veiculoService;

    @Test
    void deveListarVeiculosPorTipoIdComSucesso() {

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setId(1L);
        tipoVeiculo.setName("SUV");

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setMarca("Toyota");
        veiculo.setModelo("Corolla");
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);

        VeiculoRequestDTO requestDTO = new VeiculoRequestDTO();
        requestDTO.setMarca("Toyota");
        requestDTO.setModelo("Corolla");
        requestDTO.setPlaca("ABC-1234");
        requestDTO.settipoVeiculo(1L);
        requestDTO.setStatus(VeiculoStatus.DISPONIVEL);

        VeiculoResponseDTO responseDTO = new VeiculoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setMarca("Toyota");
        responseDTO.setModelo("Corolla");

        List<Veiculo> veiculos = Arrays.asList(veiculo);

        when(tipoVeiculoRepository.findById(tipoVeiculo.getId())).thenReturn(Optional.of(tipoVeiculo));
        when(veiculoRepository.findByTipoVeiculo(tipoVeiculo)).thenReturn(Optional.of(veiculos));
        when(modelMapper.map(any(Veiculo.class), eq(VeiculoResponseDTO.class))).thenReturn(responseDTO);


        List<VeiculoResponseDTO> resultado = veiculoService.listarPorTipoId(veiculo.getId());

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(tipoVeiculoRepository).findById(veiculo.getId());
        verify(veiculoRepository).findByTipoVeiculo(tipoVeiculo);
    }

    @Test
    void deveLancarExcecaoQuandoTipoVeiculoNaoExiste() {

        when(tipoVeiculoRepository.findById(9L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            veiculoService.listarPorTipoId(9L);
        });
        assertTrue(exception.getMessage().contains("não encontrado"));
        verify(veiculoRepository, never()).findByTipoVeiculo(any());
    }

    @Test
    void deveLancarExcecaoQuandoNaoHaVeiculosDoTipo() {


        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setId(1L);
        tipoVeiculo.setName("SUV");


        when(tipoVeiculoRepository.findById(1L)).thenReturn(Optional.of(tipoVeiculo));
        when(veiculoRepository.findByTipoVeiculo(tipoVeiculo)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            veiculoService.listarPorTipoId(1L);
        });

        assertEquals("Carros com esse tipo especifico não foram encontrados", exception.getMessage());
    }

    @Test
    void deveRetornarListaVaziaQuandoTipoExisteMasSemVeiculos() {

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setId(1L);
        tipoVeiculo.setName("SUV");

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setMarca("Toyota");
        veiculo.setModelo("Corolla");
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);

        List<Veiculo> veiculosVazios = Collections.emptyList();

        when(tipoVeiculoRepository.findById(1L)).thenReturn(Optional.of(tipoVeiculo));
        when(veiculoRepository.findByTipoVeiculo(tipoVeiculo)).thenReturn(Optional.of(veiculosVazios));

        // Act
        List<VeiculoResponseDTO> resultado = veiculoService.listarPorTipoId(1L);

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveRetornarDisponivelQuandoVeiculoLivreNoPeriodo() {

        LocalDateTime inicio = LocalDateTime.of(2024, 12, 1, 10, 0);
        LocalDateTime fim = LocalDateTime.of(2024, 12, 5, 18, 0);


        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setAlugueis(new HashSet<>());

        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));

        DisponibilidadeResponseDTO resultado = veiculoService.verifcarDisponibilidade(1L, inicio, fim);

        assertNotNull(resultado);
        assertTrue(resultado.disponibilidade());
    }

    @Test
    void deveRetornarNaoDisponivelQuandoVeiculoIndisponivel() {

        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusDays(3);

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setStatus(VeiculoStatus.ALUGADO);

        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));

        DisponibilidadeResponseDTO resultado = veiculoService.verifcarDisponibilidade(1L, inicio, fim);

        assertNotNull(resultado);
        assertFalse(resultado.disponibilidade());
    }

    @Test
    void deveDetectarConflitoDeDatas() {


        LocalDateTime inicioSolicitado = LocalDateTime.of(2024, 12, 10, 10, 0);
        LocalDateTime fimSolicitado = LocalDateTime.of(2024, 12, 15, 18, 0);

        Aluguel aluguelExistente = new Aluguel();
        aluguelExistente.setDataInicio(LocalDateTime.of(2024, 12, 8, 8, 0));
        aluguelExistente.setDataFim(LocalDateTime.of(2024, 12, 12, 20, 0));

        HashSet<Aluguel> alugueis = new HashSet<>();
        alugueis.add(aluguelExistente);

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setAlugueis(alugueis);

        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));

        DisponibilidadeResponseDTO resultado = veiculoService.verifcarDisponibilidade(1L, inicioSolicitado, fimSolicitado);

        assertFalse(resultado.disponibilidade());
    }

    @Test
    void deveLancarExcecaoQuandoVeiculoNaoExisteNaVerificacao() {
        Long veiculoIdInexistente = 999L;
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusDays(3);

        when(veiculoRepository.findById(veiculoIdInexistente)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            veiculoService.verifcarDisponibilidade(veiculoIdInexistente, inicio, fim);
        });

        assertEquals("não existe Veiculo com o id informado", exception.getMessage());
    }

    @Test
    void deveConsiderarDisponivelQuandoAluguelTerminaAntes() {

        LocalDateTime inicioSolicitado = LocalDateTime.of(2024, 12, 10, 10, 0);
        LocalDateTime fimSolicitado = LocalDateTime.of(2024, 12, 15, 18, 0);

        Aluguel aluguelExistente = new Aluguel();
        aluguelExistente.setDataInicio(LocalDateTime.of(2024, 12, 1, 8, 0));
        aluguelExistente.setDataFim(LocalDateTime.of(2024, 12, 5, 20, 0));

        HashSet<Aluguel> alugueis = new HashSet<>();
        alugueis.add(aluguelExistente);

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setAlugueis(alugueis);

        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));

        DisponibilidadeResponseDTO resultado = veiculoService.verifcarDisponibilidade(1L, inicioSolicitado, fimSolicitado);

        assertTrue(resultado.disponibilidade());
    }

    @Test
    void deveConsiderarDisponivelQuandoAluguelComecaDepois() {
        Long veiculoId = 1L;
        LocalDateTime inicioSolicitado = LocalDateTime.of(2024, 12, 10, 10, 0);
        LocalDateTime fimSolicitado = LocalDateTime.of(2024, 12, 15, 18, 0);

        Aluguel aluguelExistente = new Aluguel();
        aluguelExistente.setDataInicio(LocalDateTime.of(2024, 12, 20, 8, 0));
        aluguelExistente.setDataFim(LocalDateTime.of(2024, 12, 25, 20, 0));

        HashSet<Aluguel> alugueis = new HashSet<>();
        alugueis.add(aluguelExistente);


        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setAlugueis(alugueis);

        when(veiculoRepository.findById(veiculoId)).thenReturn(Optional.of(veiculo));

        DisponibilidadeResponseDTO resultado = veiculoService.verifcarDisponibilidade(veiculoId, inicioSolicitado, fimSolicitado);
        assertTrue(resultado.disponibilidade());
    }



    @Test
    void deveCriarVeiculoComSucesso() {

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setId(1L);
        tipoVeiculo.setName("SUV");

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setMarca("Toyota");
        veiculo.setModelo("Corolla");
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);

        VeiculoRequestDTO requestDTO = new VeiculoRequestDTO();
        requestDTO.setMarca("Toyota");
        requestDTO.setModelo("Corolla");
        requestDTO.setPlaca("ABC-1234");
        requestDTO.settipoVeiculo(1L);
        requestDTO.setStatus(VeiculoStatus.DISPONIVEL);

        VeiculoResponseDTO responseDTO = new VeiculoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setMarca("Toyota");
        responseDTO.setModelo("Corolla");


        when(tipoVeiculoRepository.findById(1L)).thenReturn(Optional.of(tipoVeiculo));
        when(modelMapper.map(requestDTO, Veiculo.class)).thenReturn(veiculo);
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);
        when(modelMapper.map(veiculo, VeiculoResponseDTO.class)).thenReturn(responseDTO);

               VeiculoResponseDTO resultado = veiculoService.creatVeiculo(requestDTO);


        assertNotNull(resultado);
        assertEquals(responseDTO, resultado);
        verify(tipoVeiculoRepository).findById(1L);
        verify(veiculoRepository).save(veiculo);
        verify(modelMapper).map(requestDTO, Veiculo.class);
        verify(modelMapper).map(veiculo, VeiculoResponseDTO.class);
    }

    @Test
    void deveAssociarTipoVeiculoCorretamente() {
        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setId(1L);
        tipoVeiculo.setName("SUV");

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setMarca("Toyota");
        veiculo.setModelo("Corolla");
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);

        VeiculoRequestDTO requestDTO = new VeiculoRequestDTO();
        requestDTO.setMarca("Toyota");
        requestDTO.setModelo("Corolla");
        requestDTO.setPlaca("ABC-1234");
        requestDTO.settipoVeiculo(1L);
        requestDTO.setStatus(VeiculoStatus.DISPONIVEL);

        VeiculoResponseDTO responseDTO = new VeiculoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setMarca("Toyota");
        responseDTO.setModelo("Corolla");

        ArgumentCaptor<Veiculo> veiculoCaptor = ArgumentCaptor.forClass(Veiculo.class);

        when(tipoVeiculoRepository.findById(1L)).thenReturn(Optional.of(tipoVeiculo));
        when(modelMapper.map(requestDTO, Veiculo.class)).thenReturn(veiculo);
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);
        when(modelMapper.map(any(Veiculo.class), eq(VeiculoResponseDTO.class))).thenReturn(responseDTO);

        veiculoService.creatVeiculo(requestDTO);

        verify(veiculoRepository).save(veiculoCaptor.capture());
        Veiculo veiculoSalvo = veiculoCaptor.getValue();
        assertEquals(tipoVeiculo, veiculoSalvo.getTipoVeiculo());
    }


}
