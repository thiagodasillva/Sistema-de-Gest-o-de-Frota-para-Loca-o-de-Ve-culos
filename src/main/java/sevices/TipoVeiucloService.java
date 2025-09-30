package sevices;

import DTOs.TipoVeiculoRequestDTO;
import DTOs.TipoVeiculoResponseDTO;
import Repository.TipoVeiculoRepository;
import Repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import models.TipoVeiculo;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class TipoVeiucloService {

    private TipoVeiculoRepository tipoVeiculoRepository;
    private VeiculoRepository veiculoRepository;
    private ModelMapper modelMapper;

    public TipoVeiucloService(TipoVeiculoRepository tipoVeiculoRepository, VeiculoRepository veiculoRepository, ModelMapper modelMapper) {
        this.tipoVeiculoRepository = tipoVeiculoRepository;
        this.veiculoRepository = veiculoRepository;
        this.modelMapper = modelMapper;
    }

    public TipoVeiculo DTOTOEntity(TipoVeiculoRequestDTO tipoVeiculoDTO){
        return modelMapper.map(tipoVeiculoDTO,TipoVeiculo.class);
    }

    public TipoVeiculoResponseDTO EntityToDTO(TipoVeiculo tipoVeiculo){
        return modelMapper.map(tipoVeiculo,TipoVeiculoResponseDTO.class);
    }



    public List<TipoVeiculoResponseDTO> getTipos(){
        List<TipoVeiculo> tipoVeiculos = tipoVeiculoRepository.findAll();
        List<TipoVeiculoResponseDTO> tipoVeiculoDTOS = tipoVeiculos.stream().map(tipoVeiculo -> EntityToDTO(tipoVeiculo)).collect(Collectors.toList());
        return tipoVeiculoDTOS;
    }

    public TipoVeiculoResponseDTO getTipoId(Long id){
        TipoVeiculo tipoVeiculo = tipoVeiculoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tipo de veiculo com id " + id + " não encontrado."));
        return EntityToDTO(tipoVeiculo);

    }

    public TipoVeiculoResponseDTO getTipoByname(String name){
        TipoVeiculo tipo = tipoVeiculoRepository.findByName(name).orElseThrow(()-> new EntityNotFoundException("Tipo de veiculo com nome " + name + " não encontrado."));
        return EntityToDTO(tipo);
    }


    public TipoVeiculoResponseDTO criarTipo (TipoVeiculoRequestDTO tipoVeiculoDTO){
        TipoVeiculo tipoVeiculo = DTOTOEntity(tipoVeiculoDTO);
        tipoVeiculoRepository.save(tipoVeiculo);
        return EntityToDTO(tipoVeiculo);
    }

    public TipoVeiculoResponseDTO updateTipo(Long id, TipoVeiculoRequestDTO tipoVeiculoDTO){
        TipoVeiculo tipoVeiculo = tipoVeiculoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Tipo de veiculo com id: "+ id +"não existente"));
        tipoVeiculo.setName(tipoVeiculoDTO.getName());
        tipoVeiculo.setDescricao(tipoVeiculoDTO.getDescricao());
        tipoVeiculo.setPrecoDiario(tipoVeiculoDTO.getPrecoDiario());
        tipoVeiculo.setPrecoDiario(tipoVeiculoDTO.getPrecoDiario());

        tipoVeiculoRepository.save(tipoVeiculo);

        return EntityToDTO(tipoVeiculo);

    }

    public void delete(Long id){
        TipoVeiculo tipoVeiculo = tipoVeiculoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("não é possivel deletar um veiculo inxistente!"));

        if(veiculoRepository.existByTipoVeiculo(tipoVeiculo)){
            throw new RuntimeException("Não é possível deletar tipo com veículos associados");
        };

        tipoVeiculoRepository.delete(tipoVeiculo);
    }


    public TipoVeiculoResponseDTO updatePrecoDiario(Long id, BigDecimal precoDiario){
        TipoVeiculo tipoVeiculo = tipoVeiculoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("não é possivel alterar o valor diario de um Tipo de veiculo inexistente"));
        tipoVeiculo.setPrecoDiario(precoDiario);
        tipoVeiculoRepository.save(tipoVeiculo);
        return EntityToDTO(tipoVeiculo);

    }
}
