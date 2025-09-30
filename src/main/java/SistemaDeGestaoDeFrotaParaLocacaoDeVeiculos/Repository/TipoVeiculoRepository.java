package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.Repository;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.TipoVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoVeiculoRepository extends JpaRepository<TipoVeiculo,Long> {
    Optional<TipoVeiculo> findByName(String name);

}
