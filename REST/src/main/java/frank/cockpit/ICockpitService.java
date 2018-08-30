package frank.cockpit;

import java.util.Collection;
import java.util.UUID;

import org.springframework.lang.NonNull;

public interface ICockpitService {

    public @NonNull Cockpit createCockpit();

    public @NonNull Cockpit getCockpit(@NonNull UUID guid) throws CockpitNotFoundException;
    public @NonNull Cockpit getCockpitByDecoderAddress(@NonNull int decoderAddress) throws CockpitNotFoundException;
    public @NonNull Collection<Cockpit> getAllCockpits();
    
    public void save(@NonNull Cockpit cockpit);
}
