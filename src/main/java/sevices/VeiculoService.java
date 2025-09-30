package sevices;

import DTOs.VeiculoRequestDTO;
import DTOs.VeiculoResponseDTO;
import Repository.TipoVeiculoRepository;
import Repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import models.Aluguel;
import models.TipoVeiculo;
import models.Veiculo;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import static models.VeiculoStatus.DISPONIVEL;

public class VeiculoService {

    private VeiculoRepository veiculoRepository;
    private TipoVeiculoRepository tipoVeiculoRepository;
    private ModelMapper modelMapper;

    public VeiculoService(VeiculoRepository veiculoRepository, TipoVeiculoRepository tipoVeiculoRepository, ModelMapper modelMapper) {
        this.veiculoRepository = veiculoRepository;
        this.tipoVeiculoRepository = tipoVeiculoRepository;
        this.modelMapper = modelMapper;
    }

    public VeiculoResponseDTO EntityToDTO(Veiculo veiculo){
        return modelMapper.map(veiculo, VeiculoResponseDTO.class);
    }

    public Veiculo DTOToEntity(VeiculoRequestDTO veiculoDTO){
        return modelMapper.map(veiculoDTO,Veiculo.class);
    }



    public List<VeiculoResponseDTO> listarVeiculos(){
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        List<VeiculoResponseDTO> veiculoDTOS = veiculoList.stream().map(veiculo -> EntityToDTO(veiculo)).collect(Collectors.toList());
        return veiculoDTOS;

    }

    public VeiculoResponseDTO getVeiculo(Long id){
        Veiculo veiculo = veiculoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Veiculo com id " + id + " não encontrado."));
        VeiculoResponseDTO veiculoDTO = EntityToDTO(veiculo);
        return  veiculoDTO;
    }

    public List<VeiculoResponseDTO> listarPorTipoId(Long idTipoVeiculo){
        TipoVeiculo tipoVeiculo1 = tipoVeiculoRepository.findById(idTipoVeiculo).orElseThrow(() -> new EntityNotFoundException("Tipo de veiculo com id " + idTipoVeiculo + " não encontrado."));
        List<Veiculo> veiculos = veiculoRepository.findBytipoVeiculo(tipoVeiculo1).orElseThrow(()-> new RuntimeException("Carros com esse tipo especifico não foram encontrados"));
        List<VeiculoResponseDTO> veiculosDTO = veiculos.stream().map(veiculo -> EntityToDTO(veiculo)).collect(Collectors.toList());
        return veiculosDTO;

    }

    public List<VeiculoResponseDTO> listarPorDisponibilidade(){
        List<Veiculo>  veiculos = veiculoRepository.findByStatus(DISPONIVEL);
        if(veiculos.isEmpty()){
            throw new RuntimeException("Não há veiculos disponiveis no momento");
        }
        List<VeiculoResponseDTO> veiculoDTOS = veiculos.stream().map(veiculo -> EntityToDTO(veiculo)).collect(Collectors.toList());
        return veiculoDTOS;
    }


    public List<VeiculoResponseDTO> listarPorMarca(String marca){
        List<Veiculo> veiculos = veiculoRepository.findBymarca(marca).orElseThrow(()-> new RuntimeException("não foram encontrados veiculos com essa marca"));
        List<VeiculoResponseDTO> veiculosDTO = veiculos.stream().map(veiculo -> EntityToDTO(veiculo)).collect(Collectors.toList());
        return veiculosDTO;
    }


    public VeiculoResponseDTO creatVeiculo(VeiculoRequestDTO veiculoDTO){
        Veiculo veiculo = DTOToEntity(veiculoDTO);
        veiculoRepository.save(veiculo);
        return EntityToDTO(veiculo);
    }

    public VeiculoResponseDTO updateVeiculo(Long id, VeiculoRequestDTO veiculoDTO){
        Veiculo veiculo = veiculoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("não existe Veiculo com o id informado"));
        TipoVeiculo tipoVeiculo = tipoVeiculoRepository.findById(veiculoDTO.getTipoVeiculoID()).orElseThrow(()-> new EntityNotFoundException("não existe o tipo de veiculo com o id informado"));

        veiculo.setTipoVeiculo(tipoVeiculo);
        veiculo.setStatus(veiculoDTO.getStatus());
        veiculo.setAno(veiculoDTO.getAno());
        veiculo.setCor(veiculoDTO.getCor());
        veiculo.setMarca(veiculoDTO.getMarca());
        veiculo.setModelo(veiculoDTO.getModelo());
        veiculo.setPlaca(veiculoDTO.getPlaca());

        veiculoRepository.save(veiculo);
        return EntityToDTO(veiculo);
    }

    public void deleteVeiculo(Long id){
        Veiculo veiculo = veiculoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("o veiculo informado não existe"));

        if(veiculo.getStatus() != DISPONIVEL){
            throw new RuntimeException("Não é possível deletar veículo com aluguéis associados");
        }
        veiculoRepository.delete(veiculo);


    }

    // metodos de negocio

    public boolean verifcarDisponibilidade(Long veiculoId, LocalDateTime inicio, LocalDateTime fim){

        Veiculo veiculo = veiculoRepository.findById(veiculoId).orElseThrow(()-> new EntityNotFoundException("não existe Veiculo com o id informado"));

        if(veiculo.getStatus() != DISPONIVEL){
          return false;
        }
        return veiculo.getAlugueis().stream().noneMatch(aluguel -> existeConflitoDatas(aluguel,inicio,fim));

    }

    private boolean existeConflitoDatas(Aluguel aluguel, LocalDateTime inicio, LocalDateTime fim){
        return inicio.isBefore(aluguel.getDataFim()) && fim.isAfter(aluguel.getDataInicio());
    }


}

