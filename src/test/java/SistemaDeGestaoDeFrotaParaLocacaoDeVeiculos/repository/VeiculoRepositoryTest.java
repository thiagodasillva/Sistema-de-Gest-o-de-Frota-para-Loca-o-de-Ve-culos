package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.repository;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.Cliente;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.TipoVeiculo;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.Veiculo;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.VeiculoStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")

public class VeiculoRepositoryTest {

    @Autowired
    private TipoVeiculoRepository tipoVeiculoRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;


    @Test
    public void deveRetornarOsVeiculosPorTipo(){

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");

        tipoVeiculoRepository.save(tipoVeiculo);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);

        Optional<List<Veiculo>> veiculos = veiculoRepository.findByTipoVeiculo(tipoVeiculo);
        List<Veiculo> listVeiculos = veiculos.get();

        assertNotNull(veiculos);
        assertEquals(1,listVeiculos.size());
        assertEquals(veiculo.getId(),listVeiculos.get(0).getId());

    }

    @Test
    public void deveRetornarVazioQuandoNãohouveremVeiculosPorTipo(){

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");

        tipoVeiculoRepository.save(tipoVeiculo);

        Optional<List<Veiculo>> veiculosOptional = veiculoRepository.findByTipoVeiculo(tipoVeiculo);
        List<Veiculo> veiculos = veiculosOptional.get();

        assertTrue(veiculos.isEmpty());

    }

    @Test
    public void deveRetornarTrueQuandoVeiculoExistePorTipo(){

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");

        tipoVeiculoRepository.save(tipoVeiculo);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);

        Boolean existeVeiculos = veiculoRepository.existsByTipoVeiculo(tipoVeiculo);

        assertTrue(existeVeiculos);

    }


    @Test
    public void deveRetornarFalseQuandoVeiculoNaoExistePorTipo(){

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");

        TipoVeiculo tipoVeiculo2 = new TipoVeiculo();
        tipoVeiculo.setName("SUV");

        tipoVeiculoRepository.save(tipoVeiculo);
        tipoVeiculoRepository.save(tipoVeiculo2);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);

        Boolean existeVeiculos = veiculoRepository.existsByTipoVeiculo(tipoVeiculo2);

        assertFalse(existeVeiculos);

    }

    @Test
    public void deveRetornarOsVeiculosPorMarca(){

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");

        tipoVeiculoRepository.save(tipoVeiculo);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo.setMarca("Honda");
        veiculo = veiculoRepository.save(veiculo);

        Optional<List<Veiculo>> veiculos = veiculoRepository.findByMarca(veiculo.getMarca());
        List<Veiculo> listVeiculos = veiculos.get();

        assertNotNull(veiculos);
        assertEquals(1,listVeiculos.size());
        assertEquals(veiculo.getId(),listVeiculos.get(0).getId());

    }

    @Test
    public void deveRetornarVazioQuandoNãohouveremVeiculosPorMarca(){

        Optional<List<Veiculo>> veiculosOptional = veiculoRepository.findByMarca("Honda");
        List<Veiculo> veiculos = veiculosOptional.get();

        assertTrue(veiculos.isEmpty());

    }

    @Test
    public void deveRetornarOsVeiculosPorStatus(){

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");

        tipoVeiculoRepository.save(tipoVeiculo);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo.setMarca("Honda");
        veiculo = veiculoRepository.save(veiculo);

        List<Veiculo> veiculos = veiculoRepository.findByStatus(VeiculoStatus.DISPONIVEL);

        assertNotNull(veiculos);
        assertEquals(1,veiculos.size());
        assertEquals(veiculo.getId(),veiculos.get(0).getId());

    }

    @Test
    public void deveRetornarVazioQuandoNãohouveremVeiculosPorStatus(){

        List<Veiculo> veiculos = veiculoRepository.findByStatus(VeiculoStatus.DISPONIVEL);
        assertTrue(veiculos.isEmpty());

    }
}
