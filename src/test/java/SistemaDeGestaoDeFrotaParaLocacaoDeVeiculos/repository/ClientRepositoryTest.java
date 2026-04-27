package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.repository;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.Cliente;
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
public class ClientRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    // testes crud

    @Test
    public void deveSalvarCliente(){

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("12345678900");
        cliente.setTelefone("11999999999");

        Cliente clienteSalvo = clienteRepository.save(cliente);

        assertNotNull(clienteSalvo);
        assertNotNull(clienteSalvo.getId());
        assertEquals(cliente.getCpf(),clienteSalvo.getCpf());

    }

    @Test
    public void deveBuscarClientesPorId(){
        Cliente cliente1 = new Cliente();
        cliente1.setName("João Silva");
        cliente1.setPassword("123456789");
        cliente1.setCpf("12345678900");
        cliente1.setTelefone("11999999999");

        Cliente cliente2 = new Cliente();
        cliente2.setName("Maria Silva");
        cliente2.setPassword("111111111");
        cliente2.setCpf("98765432100");
        cliente2.setTelefone("19999999999");

        Cliente clienteSalvo1 = clienteRepository.save(cliente1);
        Cliente clienteSalvo2 = clienteRepository.save(cliente2);

        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteSalvo1.getId());
        Cliente cliente = clienteOptional.get();

        assertNotNull(clienteOptional);
        assertEquals(cliente1.getId(),cliente.getId());
    }

    @Test
    public void deveListarTodosOsClientes(){
        Cliente cliente1 = new Cliente();
        cliente1.setName("João Silva");
        cliente1.setPassword("123456789");
        cliente1.setCpf("12345678900");
        cliente1.setTelefone("11999999999");

        Cliente cliente2 = new Cliente();
        cliente2.setName("Maria Silva");
        cliente2.setPassword("111111111");
        cliente2.setCpf("98765432100");
        cliente2.setTelefone("19999999999");

        Cliente clienteSalvo1 = clienteRepository.save(cliente1);
        Cliente clienteSalvo2 = clienteRepository.save(cliente2);

        List<Cliente> clientes = clienteRepository.findAll();

        assertNotNull(clientes);
        assertEquals(2,clientes.size());
        assertNotEquals(clienteSalvo1.getId(),clienteSalvo2.getId());

    }

    @Test
    public void deveAtualizarCliente(){

        Cliente cliente1 = new Cliente();
        cliente1.setName("João Silva");
        cliente1.setPassword("123456789");
        cliente1.setCpf("12345678900");
        cliente1.setTelefone("11999999999");


        Cliente clienteSalvo1 = clienteRepository.save(cliente1);

        clienteSalvo1.setName("novo nome");
        clienteSalvo1.setSenha("novaSenha");

        Cliente clienteAtualizado = clienteRepository.save(cliente1);

        assertNotNull(clienteAtualizado);
        assertEquals(clienteSalvo1.getId(),clienteAtualizado.getId());


    }

    @Test
    public void deveDeletarCliente(){


        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("12345678900");
        cliente.setTelefone("11999999999");

        Cliente clienteSalvo = clienteRepository.save(cliente);

        clienteRepository.deleteById(cliente.getId());

        Optional <Cliente> cliente1 = clienteRepository.findById(cliente.getId());

        assertTrue(cliente1.isEmpty());

    }


    //Buscar personalizadas

    @Test
    public void deveBuscarClientePorCPF(){

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("12345678900");
        cliente.setTelefone("11999999999");

        Cliente clienteSalvo = clienteRepository.save(cliente);

        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cliente.getCpf());
        Cliente cliente1 = clienteOptional.get();

        assertNotNull(clienteOptional);
        assertEquals(clienteSalvo.getId(),cliente1.getId());

    }

    @Test
    public void deveRetornarVazioQuandoNãoHouverClienteComOCPF(){


        Optional<Cliente> clienteOptional = clienteRepository.findByCpf("12345678900");

        assertTrue(clienteOptional.isEmpty());
    }

    @Test
    public void deveRetornarVazioQuandoNãoHouverClienteAtivo(){

        Cliente cliente = new Cliente();
        cliente.setName("João Silva");
        cliente.setPassword("123456789");
        cliente.setCpf("12345678900");
        cliente.setTelefone("11999999999");
        cliente.setAtivo(false);
        Cliente clienteSalvo = clienteRepository.save(cliente);

        Optional<Cliente> clienteOptional = clienteRepository.findByIdAndAtivoTrue(clienteSalvo.getId());

        assertTrue(clienteOptional.isEmpty());
    }






}
