package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.repository;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class AluguelRepositoryTest {

    @Autowired
    private AluguelRepository aluguelRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private TipoVeiculoRepository tipoVeiculoRepository;


    // testes de um crud

    @Test
    void deveSalvarAluguel() {

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");
        tipoVeiculo = tipoVeiculoRepository.save(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999");
        cliente = clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);

        Aluguel aluguel = new Aluguel();
        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);
        aluguel.setDataInicio(LocalDateTime.now());
        aluguel.setDataFim(LocalDateTime.now().plusDays(5));
        aluguel.setStatus(AluguelStatus.ATIVO);

        Aluguel aluguelSalvo = aluguelRepository.save(aluguel);


        assertNotNull(aluguelSalvo);
        assertNotNull(aluguelSalvo.getId());
        assertEquals(veiculo.getId(),aluguelSalvo.getVeiculo().getId());
        assertEquals(cliente.getId(),aluguelSalvo.getCliente().getId());
        assertEquals(AluguelStatus.ATIVO,aluguelSalvo.getStatus());
    }

    @Test
    public void deveBuscarPorIdCOrretamente(){

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");
        tipoVeiculoRepository.save(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999");
        clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculoRepository.save(veiculo);

        Aluguel aluguel = new Aluguel();
        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);
        aluguel.setDataInicio(LocalDateTime.now());
        aluguel.setDataFim(LocalDateTime.now().plusDays(5));
        aluguel.setStatus(AluguelStatus.ATIVO);
        Aluguel aluguelSalvo = aluguelRepository.save(aluguel);

        Optional<Aluguel> busccado = aluguelRepository.findById(aluguelSalvo.getId());
        Aluguel aluguel1 = busccado.get();

        assertNotNull(aluguelSalvo);
        assertNotNull(busccado);
        assertEquals(aluguelSalvo.getId(),aluguel1.getId());
        assertEquals(aluguelSalvo.getCliente().getId(),aluguel1.getCliente().getId());
        assertEquals(aluguelSalvo.getVeiculo().getId(),aluguel1.getVeiculo().getId());


    }

    @Test
    public void deveAtualizarAluguel(){

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");

        tipoVeiculoRepository.save(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999");
        clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculoRepository.save(veiculo);

        Aluguel aluguel = new Aluguel();
        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);
        aluguel.setDataInicio(LocalDateTime.now());
        aluguel.setDataFim(LocalDateTime.now().plusDays(5));
        aluguel.setTaxaFixa(new BigDecimal(100.00));
        aluguel.setStatus(AluguelStatus.ATIVO);

        Aluguel aluguelSalvo = aluguelRepository.save(aluguel);

        BigDecimal novoValor = new BigDecimal(50.0);
        aluguelSalvo.setStatus(AluguelStatus.FINALIZADO);
        aluguelSalvo.setTaxaFixa(novoValor);

        Aluguel update = aluguelRepository.save(aluguelSalvo);

        assertEquals(aluguelSalvo.getId(),update.getId());
        assertEquals(AluguelStatus.FINALIZADO,update.getStatus());
        assertEquals(novoValor,update.getTaxaFixa());


    }


    @Test
    public void deveDeletarAluguel(){
        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");
        tipoVeiculo = tipoVeiculoRepository.save(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999");
        cliente = clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);

        Aluguel aluguel = new Aluguel();
        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);
        aluguel.setDataInicio(LocalDateTime.now());
        aluguel.setDataFim(LocalDateTime.now().plusDays(5));
        aluguel.setStatus(AluguelStatus.ATIVO);

        Aluguel aluguelSalvo = aluguelRepository.save(aluguel);

        aluguelRepository.deleteById(aluguelSalvo.getId());

        Optional<Aluguel> aluguelOptional = aluguelRepository.findById(aluguelSalvo.getId());

        assertFalse(aluguelOptional.isPresent());


    }

    @Test
    public void deveListarTodosOsAlugueis(){

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");
        tipoVeiculo = tipoVeiculoRepository.save(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999");
        cliente = clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);

        Aluguel aluguel = new Aluguel();
        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);
        aluguel.setDataInicio(LocalDateTime.now());
        aluguel.setDataFim(LocalDateTime.now().plusDays(5));
        aluguel.setStatus(AluguelStatus.ATIVO);

        Aluguel aluguel2 = new Aluguel();
        aluguel2.setVeiculo(veiculo);
        aluguel2.setCliente(cliente);
        aluguel2.setDataInicio(LocalDateTime.now().plusDays(6));
        aluguel2.setDataFim(LocalDateTime.now().plusDays(11));
        aluguel2.setStatus(AluguelStatus.ATIVO);

        Aluguel aluguelSalvo = aluguelRepository.save(aluguel);
        Aluguel aluguelSalvo2 = aluguelRepository.save(aluguel2);

        List<Aluguel> alugueis = aluguelRepository.findAll();

        assertNotNull(alugueis);
        assertEquals(2,alugueis.size());

    }









    // testes das buscas personalizadas

    @Test
    void deveBuscarAlugueisPorVeiculo() {

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");
        tipoVeiculo = tipoVeiculoRepository.save(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999");
        cliente = clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);

        Aluguel aluguel = new Aluguel();
        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);
        aluguel.setDataInicio(LocalDateTime.now());
        aluguel.setDataFim(LocalDateTime.now().plusDays(5));
        aluguel = aluguelRepository.save(aluguel);

        Optional<List<Aluguel>> result = aluguelRepository.findByVeiculo(veiculo);
        List<Aluguel> list = result.get();

        assertNotNull(result);
        assertEquals(1, list.size());
        assertEquals(veiculo.getId(), list.get(0).getVeiculo().getId());
    }

    @Test
    void deveBuscarAlugueisPorVeiculoRetornarVazioQuandoNaoExiste() {
        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");
        tipoVeiculo = tipoVeiculoRepository.save(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999");
        cliente = clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);


        Optional<List<Aluguel>> result = aluguelRepository.findByVeiculo(veiculo);
        List<Aluguel> list = result.get();

        assertNotNull(result);
        assertTrue(list.isEmpty());
    }

    @Test
    void deveBuscarAlugueisPorCliente() {

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");
        tipoVeiculo = tipoVeiculoRepository.save(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999");
        cliente = clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);

        Aluguel aluguel = new Aluguel();
        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);
        aluguel.setDataInicio(LocalDateTime.now());
        aluguel.setDataFim(LocalDateTime.now().plusDays(5));
        aluguel = aluguelRepository.save(aluguel);

        Optional<List<Aluguel>> result = aluguelRepository.findByCliente(cliente);
        List<Aluguel> list = result.get();

        assertNotNull(result);
        assertEquals(1, list.size());
        assertEquals(veiculo.getId(), list.get(0).getVeiculo().getId());
    }

    @Test
    void deveBuscarAlugueisPorClienteRetornarVazioQuandoNaoExiste() {
        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");
        tipoVeiculo = tipoVeiculoRepository.save(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999");
        cliente = clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);


        Optional<List<Aluguel>> result = aluguelRepository.findByCliente(cliente);
        List<Aluguel> list = result.get();

        assertNotNull(result);
        assertTrue(list.isEmpty());
    }


    @Test
    void deveBuscarAlugueisEntreDatas() {

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");
        tipoVeiculo = tipoVeiculoRepository.save(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999");
        cliente = clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);

        Aluguel aluguel = new Aluguel();
        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);
        aluguel.setDataInicio(LocalDateTime.now());
        aluguel.setDataFim(LocalDateTime.now().plusDays(5));
        aluguel = aluguelRepository.save(aluguel);

        Optional<List<Aluguel>> result = aluguelRepository.findByDataInicioBetween(aluguel.getDataInicio(),aluguel.getDataFim());
        List<Aluguel> list = result.get();

        assertNotNull(result);
        assertEquals(1, list.size());
        assertEquals(veiculo.getId(), list.get(0).getVeiculo().getId());
    }

    @Test
    void deveBuscarAlugueisEntreDatasRetornarVazioQuandoNaoExiste() {
        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");
        tipoVeiculo = tipoVeiculoRepository.save(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999");
        cliente = clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);


        Optional<List<Aluguel>> result = aluguelRepository.findByDataInicioBetween(LocalDateTime.now(),LocalDateTime.now().plusDays(5));
        List<Aluguel> list = result.get();

        assertNotNull(result);
        assertTrue(list.isEmpty());
    }

    @Test
    void deveBuscarAlugueisPortatus() {

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");
        tipoVeiculo = tipoVeiculoRepository.save(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999");
        cliente = clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);

        Aluguel aluguel = new Aluguel();
        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);
        aluguel.setDataInicio(LocalDateTime.now());
        aluguel.setDataFim(LocalDateTime.now().plusDays(5));
        aluguel.setStatus(AluguelStatus.ATIVO);
        aluguel = aluguelRepository.save(aluguel);

        Optional<List<Aluguel>> result = aluguelRepository.findByStatus(AluguelStatus.ATIVO);
        List<Aluguel> list = result.get();

        assertNotNull(result);
        assertEquals(1, list.size());
        assertEquals(veiculo.getId(), list.get(0).getVeiculo().getId());
    }

    @Test
    void deveBuscarAlugueisPorStatusRetornarVazioQuandoNaoExiste() {
        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");
        tipoVeiculo = tipoVeiculoRepository.save(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999");
        cliente = clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);


        Optional<List<Aluguel>> result = aluguelRepository.findByStatus(AluguelStatus.ATIVO);
        List<Aluguel> list = result.get();

        assertNotNull(result);
        assertTrue(list.isEmpty());
    }

    @Test
    void devePermitirMesmoVeiculoEmPeriodosDiferentes() {

        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setName("Sedan");
        tipoVeiculo = tipoVeiculoRepository.save(tipoVeiculo);

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("11999999999");
        cliente = clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setStatus(VeiculoStatus.DISPONIVEL);
        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo = veiculoRepository.save(veiculo);

        // Arrange
        Aluguel aluguel1 =new Aluguel();
        aluguel1.setVeiculo(veiculo);
        aluguel1.setCliente(cliente);
        aluguel1.setStatus(AluguelStatus.ATIVO);
        aluguel1.setDataInicio(LocalDateTime.of(2024, 1, 1, 10, 0));
        aluguel1.setDataFim(LocalDateTime.of(2024, 1, 5, 10, 0));

        Aluguel aluguel2 = new Aluguel();
        aluguel2.setVeiculo(veiculo);
        aluguel2.setCliente(cliente);
        aluguel2.setDataInicio(LocalDateTime.of(2024, 2, 1, 10, 0));
        aluguel2.setDataFim(LocalDateTime.of(2024, 2, 5, 10, 0));
        aluguel2.setStatus(AluguelStatus.ATIVO);

        Aluguel saved1 = aluguelRepository.save(aluguel1);
        Aluguel saved2 = aluguelRepository.save(aluguel2);

        assertNotNull(saved1.getId());
        assertNotNull(saved2.getId());
        assertNotEquals(saved1.getId(), saved2.getId());
    }




}
