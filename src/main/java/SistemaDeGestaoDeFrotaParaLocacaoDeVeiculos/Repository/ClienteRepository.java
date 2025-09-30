package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.Repository;

import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    public Optional<Cliente> findByCpf(String cpf);


    Optional<Cliente> findByIdAndAtivoTrue(Long id);
    List<Cliente> findByAtivoTrue();

}
