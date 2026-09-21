package pl.pawlo.zptank.database.repository.jpa.huzarsent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.huzarsent.HuzarsentEntity;

@Repository
public interface HuzarsentJpaRepository extends JpaRepository<HuzarsentEntity,Long> {


}
