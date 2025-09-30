package Repository;

import models.Aluguel;
import models.Cliente;
import models.Veiculo;
import models.VeiculoStatus;
import org.springframework.boot.info.SslInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AluguelRepository extends JpaRepository<Aluguel,Long> {

    public Optional<List<Aluguel>> findByCliente(Cliente cliente);
    public Optional<List<Aluguel>> findByveiculo(Veiculo veiculo);

    public Optional<List<Aluguel>> findByDataInicioBetween(LocalDateTime dataInicio,LocalDateTime dataFim);

    public Optional<List<Aluguel>> findBystatus(VeiculoStatus veiculoStatus);
}
