package Repository;

import models.TipoVeiculo;
import models.Veiculo;
import models.VeiculoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {
    Optional<List<Veiculo>> findBytipoVeiculo(TipoVeiculo tipoVeiculo);

    boolean existByTipoVeiculo(TipoVeiculo tipoVeiculo);

    Optional<List<Veiculo>> findBymarca(String marca);

    //@Query(value = "SELECT * FROM tb_veiculo v WHERE v.status = 'DISPONIVEL'", nativeQuery = true)
    List<Veiculo> findByStatus(VeiculoStatus status);


}
