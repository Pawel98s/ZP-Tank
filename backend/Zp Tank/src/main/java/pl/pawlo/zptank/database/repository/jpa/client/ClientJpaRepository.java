package pl.pawlo.zptank.database.repository.jpa.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.client.ClientEntity;

@Repository
public interface ClientJpaRepository extends JpaRepository<ClientEntity, Long> {



}
