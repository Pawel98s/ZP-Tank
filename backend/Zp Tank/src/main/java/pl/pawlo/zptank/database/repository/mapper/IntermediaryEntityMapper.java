package pl.pawlo.zptank.database.repository.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.zptank.database.entity.intermediary.IntermediaryEntity;
import pl.pawlo.zptank.domain.intermediary.Intermediary;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IntermediaryEntityMapper {

    Intermediary mapToDomain(final IntermediaryEntity intermediaryEntity);

    IntermediaryEntity mapToEntity(Intermediary intermediary);
}
