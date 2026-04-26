package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.services;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.ClienteRequestDTO;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs.ClienteResponseDTO;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.exception.EntityNotFoundException;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.Cliente;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.repository.ClienteRepository;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.sevices.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClienteService clienteService;



    @Test
    public void deveRetornarClienteAoBuscarPorId(){

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setAtivo(true);

        ClienteResponseDTO respostaDTO = new ClienteResponseDTO();
        respostaDTO.setId(1L);

        when(clienteRepository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(cliente));
        when(modelMapper.map(cliente,ClienteResponseDTO.class)).thenReturn(respostaDTO);

        assertEquals(respostaDTO.getId(),clienteService.getCliente(1L).getId());

    }

    @Test
    public void deveRetornarExcecaoAoPesquisarClienteComIdInexistente(){

        when(clienteRepository.findByIdAndAtivoTrue(9L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> {clienteService.getCliente(9L);},"Cliente com id 9 não encontrado.");

    }

    @Test
    public void deveRetornarClienteAoBuscarPorCPF(){

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setAtivo(true);
        cliente.setCpf("111.111.111-11");

        ClienteResponseDTO respostaDTO = new ClienteResponseDTO();
        respostaDTO.setId(1L);
        respostaDTO.setCpf("111.111.111-11");

        when(clienteRepository.findByCpf("111.111.111-11")).thenReturn(Optional.of(cliente));
        when(modelMapper.map(cliente,ClienteResponseDTO.class)).thenReturn(respostaDTO);

        assertEquals(respostaDTO.getId(),clienteService.getClienteCPF("111.111.111-11").getId());

    }

    @Test
    public void deveRetornarExcecaoAoPesquisarClienteComCPFInexistente(){

        when(clienteRepository.findByCpf("111.111.111-11")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> {clienteService.getClienteCPF("111.111.111-11");},"Cliente com cpf 111.111.111-11 não encontrado.");

    }

    @Test
    public void DeveCadastrarClienteCorretamente(){
        ClienteRequestDTO requestDTO = new ClienteRequestDTO();
        requestDTO.setCpf("111.111.111-00");
        requestDTO.setName("João Joao");
        requestDTO.setPassword("senha123");
        requestDTO.setTelefone("99999999");

        Cliente cliente= new Cliente();
        cliente.setCpf("11111111100");

        when(passwordEncoder.encode("senha123")).thenReturn("senhacriptografada");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(modelMapper.map(any(ClienteRequestDTO.class), eq(Cliente.class))).thenReturn(cliente);
        when(modelMapper.map(cliente, ClienteResponseDTO.class)).thenReturn(new ClienteResponseDTO());

        ClienteResponseDTO responseDTO = clienteService.creatCliente(requestDTO);

        assertNotNull(responseDTO);
        verify(clienteRepository).save(any(Cliente.class));
        assertEquals("11111111100",requestDTO.getCpf());

    }

    @Test
    public void deveRemoverFormatacaoDoCPF(){
        ClienteRequestDTO cliente = new ClienteRequestDTO();
        cliente.setCpf("111.111.111-11");
        cliente.setPassword("senha123");

        ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);

        when(passwordEncoder.encode(anyString())).thenReturn("senhaEncoder");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(new Cliente());
        when(modelMapper.map(any(ClienteRequestDTO.class),eq(Cliente.class))).thenReturn(new Cliente());
        when(modelMapper.map(any(Cliente.class),eq(ClienteResponseDTO.class))).thenReturn(new ClienteResponseDTO());

        clienteService.creatCliente(cliente);

        assertEquals("11111111111",cliente.getCpf());
    }

    @Test
    public void deveEncodarASenhaCorretamente(){

        ClienteRequestDTO cliente = new ClienteRequestDTO();
        cliente.setCpf("111.111.111-11");
        cliente.setPassword("senha123");

        ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);

        when(passwordEncoder.encode(anyString())).thenReturn("senhaEncoder");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(new Cliente());
        when(modelMapper.map(any(ClienteRequestDTO.class),eq(Cliente.class))).thenReturn(new Cliente());
        when(modelMapper.map(any(Cliente.class),eq(ClienteResponseDTO.class))).thenReturn(new ClienteResponseDTO());

        clienteService.creatCliente(cliente);

        verify(clienteRepository).save(clienteCaptor.capture());
        Cliente cliente1 = clienteCaptor.getValue();
        assertEquals("senhaEncoder",cliente1.getSenha());

    }

    @Test
    public void deveLancarExcecaoAoInserirParametrosNulos(){
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setCpf(null);
        clienteRequestDTO.setPassword(null);

        assertThrows(NullPointerException.class,()-> {clienteService.creatCliente(clienteRequestDTO);});
    }

    @Test
    public void deveAtualizarOClienteSalvandoONovoCPFComFormatacaoCorreta(){
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setCpf("11111111111");
        cliente.setPassword("senha123");

        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setCpf("222.222.222-22");

        ArgumentCaptor<Cliente> captura = ArgumentCaptor.forClass(Cliente.class);

        when(clienteRepository.findById(1l)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(new Cliente());

        clienteService.updateCliente(1L,clienteRequestDTO);

        verify(clienteRepository).save(captura.capture());
        Cliente cliente1 = captura.getValue();
        assertEquals("22222222222",cliente1.getCpf());

    }


}
