package sevices;

import DTOs.ClienteDTO;
import Repository.ClienteRepository;
import models.Cliente;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    public List<Cliente> clientes(){

        List<Cliente> clientes = clienteRepository.findAll();
        return clientes;
    }

    public Cliente getCliente(Long id){

        Optional<Cliente> clienteOptional= clienteRepository.findById(id);
        if(clienteOptional!= null){
            Cliente cliente = clienteOptional.get();
            return cliente;

        }
        return null;
    }

    public Cliente saveCliente(ClienteDTO clienteDTO){}
}
