package sevices;

import DTOs.ClienteRequestDTO;
import DTOs.ClienteResponseDTO;
import Repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import models.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;
    private ModelMapper modelMapper;

    public ClienteService(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper=modelMapper;
    }

    public Cliente DTOtoEntity(ClienteRequestDTO clienteDTO){
        return modelMapper.map(clienteDTO,Cliente.class);
    }

    public ClienteResponseDTO EntityToDTO(Cliente cliente){ return modelMapper.map(cliente,ClienteResponseDTO.class);
    }


    public List<ClienteResponseDTO> clientes(){

        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteResponseDTO> clienteDTOS = clientes.stream().map(cliente ->  EntityToDTO(cliente)).collect(Collectors.toList());
        return clienteDTOS;
    }

    public ClienteResponseDTO getCliente(Long id){

        Cliente cliente= clienteRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Cliente com id " + id + " não encontrado."));
        return EntityToDTO(cliente);

    }

    public ClienteResponseDTO getClienteCPF(String cpf){
        Cliente cliente = clienteRepository.findByCpf(cpf).orElseThrow(()-> new EntityNotFoundException("Cliente com cpf " + cpf + " não encontrado."));
        return EntityToDTO(cliente);

    }

    public ClienteResponseDTO creatCliente(ClienteRequestDTO clienteDTO){
        Cliente cliente = DTOtoEntity(clienteDTO);
        clienteRepository.save(cliente);
        return EntityToDTO(cliente);
    }



    public ClienteResponseDTO updateCliente(Long id, ClienteRequestDTO clienteDTO){
        Cliente cliente= clienteRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Cliente com id " + id + " não encontrado."));

        cliente.setName(clienteDTO.getName());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setTelefone(clienteDTO.getTelefone());

        clienteRepository.save(cliente);

           return EntityToDTO(cliente);

    }

    public ClienteResponseDTO deletarCliente(Long id){
        Cliente cliente= clienteRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Cliente com id " + id + " não encontrado."));
        clienteRepository.delete(cliente);
        return EntityToDTO(cliente);
    }

}
