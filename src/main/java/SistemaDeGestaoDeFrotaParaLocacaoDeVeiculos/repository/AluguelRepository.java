package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.repository;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AluguelRepository extends JpaRepository<Aluguel,Long> {

    Optional<List<Aluguel>> findByCliente(Cliente cliente);
    Optional<List<Aluguel>> findByVeiculo(Veiculo veiculo);

    Optional<List<Aluguel>> findByDataInicioBetween(LocalDateTime dataInicio,LocalDateTime dataFim);

    Optional<List<Aluguel>> findByStatus(AluguelStatus aluguelStatus);
}
