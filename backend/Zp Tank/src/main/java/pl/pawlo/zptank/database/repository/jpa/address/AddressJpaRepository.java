package pl.pawlo.zptank.database.repository.jpa.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.address.AddressEntity;

@Repository
public interface AddressJpaRepository extends JpaRepository<AddressEntity, Long> {



}
