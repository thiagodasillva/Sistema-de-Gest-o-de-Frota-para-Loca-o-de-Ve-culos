package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.repository;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.TipoVeiculo;
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
public class TipoVeiculoRepositoryTest {

    @Autowired
    private TipoVeiculoRepository tipoVeiculoRepository;

    @Test
    public void deveSalvarVeiculo() {

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");

        TipoVeiculo tipoVeiculoSalvo = tipoVeiculoRepository.save(tipoVeiculo);

        assertNotNull(tipoVeiculoSalvo);
        assertNotNull(tipoVeiculoSalvo.getId());
    }

    @Test
    public void deveListarOsTiposDeVeiculos() {

        TipoVeiculo tipoVeiculo1 = new TipoVeiculo();
        tipoVeiculo1.setName("Sedan");
        TipoVeiculo tipoVeiculo2 = new TipoVeiculo();
        tipoVeiculo2.setName("Sedan");


        TipoVeiculo tipoVeiculoSalvo1 = tipoVeiculoRepository.save(tipoVeiculo1);
        TipoVeiculo tipoVeiculoSalvo2 = tipoVeiculoRepository.save(tipoVeiculo2);

        List<TipoVeiculo> tipos = tipoVeiculoRepository.findAll();


        assertNotNull(tipos);
        assertEquals(2, tipos.size());
        assertEquals(tipoVeiculoSalvo1.getId(), tipos.get(0).getId());

    }

    @Test
    public void deveRetornrListaVaziaQuandoNãoHouveremTiposDeVeiculo() {

        List<TipoVeiculo> tipos = tipoVeiculoRepository.findAll();

        assertTrue(tipos.isEmpty());

    }

    @Test
    public void deveListarOTipoDeVeiculoPorId() {

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");

        TipoVeiculo tipoVeiculoSalvo = tipoVeiculoRepository.save(tipoVeiculo);

        Optional<TipoVeiculo> tipoVeiculoOptional = tipoVeiculoRepository.findById(tipoVeiculoSalvo.getId());
        TipoVeiculo tipoVeiculo1 = tipoVeiculoOptional.get();

        assertNotNull(tipoVeiculoOptional);
        assertEquals(tipoVeiculoSalvo.getId(), tipoVeiculo1.getId());


    }

    @Test
    public void deveRetornrVazioQuandoNãoHouveremTiposDeVeiculoId() {

        Optional<TipoVeiculo> tipoVeiculoOptional = tipoVeiculoRepository.findById(1L);

        assertTrue(tipoVeiculoOptional.isEmpty());

    }

    @Test
    public void deveAtualizarTipoVeiculo() {

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");

        TipoVeiculo tipoVeiculoSalvo = tipoVeiculoRepository.save(tipoVeiculo);

        String novoNome = "SUV";
        tipoVeiculoSalvo.setName(novoNome);

        TipoVeiculo tipoVeiculoOptional = tipoVeiculoRepository.save(tipoVeiculoSalvo);

        assertNotNull(tipoVeiculoOptional);
        assertEquals(tipoVeiculoSalvo.getId(), tipoVeiculoOptional.getId());
        assertEquals(novoNome, tipoVeiculoOptional.getName());

    }

    @Test
    public void deveDeletarTipoVeiculo() {

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");

        TipoVeiculo tipoVeiculoSalvo = tipoVeiculoRepository.save(tipoVeiculo);

        tipoVeiculoRepository.deleteById(tipoVeiculoSalvo.getId());

        Optional<TipoVeiculo> buscado = tipoVeiculoRepository.findById(tipoVeiculoSalvo.getId());

        assertTrue(buscado.isEmpty());


    }

    @Test
    public void deveListarOTipoDeVeiculoPorName() {

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");

        TipoVeiculo tipoVeiculoSalvo = tipoVeiculoRepository.save(tipoVeiculo);

        Optional<TipoVeiculo> tipoVeiculoOptional = tipoVeiculoRepository.findByName(tipoVeiculoSalvo.getName());
        TipoVeiculo tipoVeiculo1 = tipoVeiculoOptional.get();

        assertNotNull(tipoVeiculoOptional);
        assertEquals(tipoVeiculoSalvo.getId(), tipoVeiculo1.getId());


    }

    @Test
    public void deveRetornrVazioQuandoNãoHouveremTiposDeVeiculoName() {

        Optional<TipoVeiculo> tipoVeiculoOptional = tipoVeiculoRepository.findByName("SUV");

        assertTrue(tipoVeiculoOptional.isEmpty());


    }
}
