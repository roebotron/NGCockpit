package frank.cockpit;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface CockpitRepository extends CrudRepository<Cockpit, UUID> {

    List<Cockpit> findByGuid(UUID guid);
    List<Cockpit> findByDecoderAddress(int decoderAddress);
}
