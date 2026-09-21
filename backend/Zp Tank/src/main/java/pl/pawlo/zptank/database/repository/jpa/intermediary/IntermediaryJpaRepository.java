package pl.pawlo.zptank.database.repository.jpa.intermediary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.intermediary.IntermediaryEntity;

@Repository
public interface IntermediaryJpaRepository extends JpaRepository<IntermediaryEntity,Long> {


}
