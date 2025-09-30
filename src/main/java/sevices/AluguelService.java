package sevices;

import DTOs.AluguelResponseDTO;
import DTOs.AluguelResquestDTO;
import Repository.AluguelRepository;
import Repository.ClienteRepository;
import Repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import models.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static models.AluguelStatus.*;
import static models.VeiculoStatus.ALUGADO;
import static models.VeiculoStatus.DISPONIVEL;

@Service
public class AluguelService {

  private AluguelRepository aluguelRepository;
  private ClienteRepository clienteRepository;
  private VeiculoRepository veiculoRepository;
  private ModelMapper modelMapper;

  public AluguelService(AluguelRepository aluguelRepository, ClienteRepository clienteRepository, VeiculoRepository veiculoRepository,ModelMapper modelMapper) {
        this.aluguelRepository = aluguelRepository;
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
        this.modelMapper= modelMapper;
  }

    public Aluguel DTOtoEntity(AluguelResquestDTO aluguelDTO){
      return modelMapper.map(aluguelDTO,Aluguel.class);
    }

    public AluguelResponseDTO EntitytoDTO(Aluguel aluguel){
      return modelMapper.map(aluguel, AluguelResponseDTO.class);
    }

  public AluguelResponseDTO getAluguel(Long id){

      Aluguel aluguel = aluguelRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Não existe nenhum aluguel com esse id"));
      AluguelResponseDTO aluguelDTO = EntitytoDTO(aluguel);
      return aluguelDTO;
  }

  public List<AluguelResponseDTO> listarAlugueis(){
      List<Aluguel> aluguelList = aluguelRepository.findAll();
      List<AluguelResponseDTO> aluguelDTOS = aluguelList.stream().map(aluguel -> EntitytoDTO(aluguel)).collect(Collectors.toList());

      return aluguelDTOS;
  }

  public List<AluguelResponseDTO> listarAluguelPorCliente(Long idCliente){
      Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(()-> new EntityNotFoundException("Não existe nenhum cliente com esse id"));
      List<Aluguel> aluguelList = aluguelRepository.findByCliente(cliente).orElseThrow(()-> new RuntimeException("Não existem alugueis relacionados a esse veiculo"));

      List<AluguelResponseDTO> aluguelDTO = aluguelList.stream().map(aluguel -> EntitytoDTO(aluguel)).collect(Collectors.toList());
      return aluguelDTO;
  }

    public List<AluguelResponseDTO> listarALuguelporVeiculo(Long idVeiculo){
        Veiculo veiculo = veiculoRepository.findById(idVeiculo).orElseThrow(()-> new EntityNotFoundException("Não existe nenhum aluguel com esse id"));
        List<Aluguel> aluguelList = aluguelRepository.findByveiculo(veiculo).orElseThrow(()-> new RuntimeException("Não existem alugueis relacionados a esse veiculo"));

        List<AluguelResponseDTO> aluguelDTO = aluguelList.stream().map(aluguel -> EntitytoDTO(aluguel)).collect(Collectors.toList());
        return aluguelDTO;
    }

    public List<AluguelResponseDTO> listarAluguelPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim){
        List<Aluguel> aluguelList = aluguelRepository.findByDataInicioBetween(dataInicio,dataFim).orElseThrow(()-> new EntityNotFoundException("Não existtem"));
        List<AluguelResponseDTO> aluguelDTOList = aluguelList.stream().map(aluguel -> EntitytoDTO(aluguel)).collect(Collectors.toList());
        return aluguelDTOList;
    }



//  public List<AluguelDTO> listAlugueisAtivos(){
//      List<Aluguel> aluguelList = aluguelRepository.finsBystatus(DISPONIVEL).orElseThrow(()-> new RuntimeException("Não existem alugueis ativos"));
//      List<AluguelDTO> aluguelDTOS = aluguelList.stream().map(aluguel -> EntitytoDTO(aluguel)).collect(Collectors.toList());
//      return aluguelDTOS;
//  }




  public AluguelResponseDTO creat(AluguelResquestDTO aluguelDTO){

      Cliente cliente = clienteRepository.findById(aluguelDTO.getClienteId()).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
      Veiculo veiculo = veiculoRepository.findById(aluguelDTO.getVeiculoId()).orElseThrow(() -> new EntityNotFoundException("Veiculo nãp emcontrado"));

      if(veiculo.getStatus() != DISPONIVEL){
          throw new RuntimeException("Veículo não está disponível para aluguel.");
      }

      Aluguel aluguel = DTOtoEntity(aluguelDTO);


      veiculo.setStatus(ALUGADO);
      aluguelRepository.save(aluguel);


      return EntitytoDTO(aluguel);

  }

  public AluguelResponseDTO update(Long id, AluguelResquestDTO aluguelDTO){

      Aluguel aluguel = aluguelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Não existe nenhum aluguel com esse id"));
      Cliente cliente = clienteRepository.findById(aluguelDTO.getClienteId()).orElseThrow(()->new EntityNotFoundException("Não existe nenhum cliente com esse id"));
      Veiculo veiculo = veiculoRepository.findById(aluguel.getId()).orElseThrow(()-> new EntityNotFoundException("Não existe nenhum cliente com esse id"));

      aluguel.setCliente(cliente);
      aluguel.setVeiculo(veiculo);
      aluguel.setDataInicio(aluguelDTO.getDataInicio());
      aluguel.setTaxaFixa(aluguelDTO.getTaxaFixa());
      aluguelRepository.save(aluguel);


      return EntitytoDTO(aluguel);

  }

    public void delete(Long id){
      Aluguel aluguel = aluguelRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Não é possivel deletar um aluguel inexistente"));
      aluguelRepository.delete(aluguel);
    }


    public Veiculo getVeiculoByIdAluguel(Long id){

      Optional<Aluguel> aluguelOptional = aluguelRepository.findById(id);

      if(aluguelOptional.isPresent()){

          Aluguel aluguel = aluguelOptional.get();
          return aluguel.getVeiculo();

        }

      return  null;

    }

    public Cliente getClienteByIdAluguel(Long id){

        Optional<Aluguel> aluguelOptional = aluguelRepository.findById(id);

        if(aluguelOptional.isPresent()){

            Aluguel aluguel = aluguelOptional.get();
            return aluguel.getCliente();

        }

        return null;


    }


    public AluguelResponseDTO finalizar(Long id){
        Aluguel aluguel = aluguelRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Não existe nenhum cliente com esse id"));
        aluguel.setStatus(FINALIZADO);
        aluguel.setDataFim(LocalDateTime.now());

        aluguel.getVeiculo().setStatus(DISPONIVEL);

        aluguelRepository.save(aluguel);
        AluguelResponseDTO aluguelDTO = EntitytoDTO(aluguel);
        return aluguelDTO;

        }


        public AluguelResponseDTO calcelarAluguel(Long id){
            Aluguel aluguel = aluguelRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Não existe nenhum cliente com esse id"));

            if(aluguel.getStatus() != ATIVO){
                throw  new RuntimeException("Somente aluguéis ativos podem ser cancelados");
            }

            aluguel.setStatus(CANCELADO);
            aluguel.getVeiculo().setStatus(DISPONIVEL);
            aluguelRepository.save(aluguel);
            AluguelResponseDTO aluguelDTO = EntitytoDTO(aluguel);
            return aluguelDTO;
        }

        public BigDecimal calcularAluguel(Long id){
            Aluguel aluguel = aluguelRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Não existe nenhum cliente com esse id"));
            long dias = ChronoUnit.DAYS.between(aluguel.getDataInicio(), aluguel.getDataFim());
            BigDecimal  valorDiaria = aluguel.getVeiculo().getTipoVeiculo().getPrecoDiario().multiply(BigDecimal.valueOf(dias));
            BigDecimal valorTotal = valorDiaria.add(aluguel.getTaxaFixa());
            aluguel.setValorTotal(valorTotal);

            return valorTotal;
        }



}
