package pl.pawlo.zptank.database.repository.jpa.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.address.DeliveryAddressEntity;

import java.util.List;

@Repository
public interface DeliveryAddressJpaRepository extends JpaRepository<DeliveryAddressEntity, Long> {

    List<DeliveryAddressEntity> findByClientId(Long clientId);

    boolean existsByIdAndClientId(Long id, Long clientId);

}
