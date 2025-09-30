package Repository;

import models.TipoVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoVeiculoRepository extends JpaRepository<TipoVeiculo,Long> {
    Optional<TipoVeiculo> findByName(String name);

}
