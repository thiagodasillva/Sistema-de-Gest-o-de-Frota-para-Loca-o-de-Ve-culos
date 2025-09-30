package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.Repository;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.TipoVeiculo;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.Veiculo;
import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.VeiculoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {
    Optional<List<Veiculo>> findByTipoVeiculo(TipoVeiculo tipoVeiculo);

    boolean existsByTipoVeiculo(TipoVeiculo tipoVeiculo);

    Optional<List<Veiculo>> findByMarca(String marca);

    //@Query(value = "SELECT * FROM tb_veiculo v WHERE v.status = 'DISPONIVEL'", nativeQuery = true)
    List<Veiculo> findByStatus(VeiculoStatus status);


}
