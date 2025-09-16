package sevices;

import Repository.ClienteRepository;
import models.Cliente;

import javax.swing.text.html.Option;
import java.util.List;

public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    public List<Cliente> clientes(){

        List<Cliente> clientes = clienteRepository.findAll();
        return clientes;
    }
}
