package frank.cockpit;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class CockpitServiceMock implements ICockpitService {

    private final Map<UUID, Cockpit> dataMap = new HashMap<>();
    private final Map<Integer, Cockpit> decoderMap = new HashMap<>();
    
    public CockpitServiceMock()
    {
        // create a default item
        {
            Cockpit ret = new Cockpit(UUID.randomUUID());
            ret.setDecoderAddress(3);
            ret.setName("PIKO default");
            ret.setSpeedSteps(Arrays.asList(0, 10, 20, 30, 40, 50, 60, 70, 80, 90));
            save(ret);
        }
        {
            Cockpit ret = new Cockpit(UUID.randomUUID());
            ret.setDecoderAddress(7);
            ret.setName("SEVEN default");
            ret.setSpeedSteps(Arrays.asList(0, 11, 22, 33, 44, 55, 66, 77, 88, 99));
            save(ret);
        }
    }
    
    @Override
    public Cockpit createCockpit() {
        return new Cockpit(UUID.randomUUID());
    }

    @Override
    public @NonNull Cockpit getCockpit(@NonNull UUID guid) throws CockpitNotFoundException {
        Cockpit found = dataMap.get(guid);
        if (found==null)
            throw new CockpitNotFoundException(guid.toString());
        return new Cockpit(found);
    }

    @Override
    public @NonNull Cockpit getCockpitByDecoderAddress(@NonNull int decoderAddress) throws CockpitNotFoundException {
        Cockpit found = decoderMap.get(decoderAddress);
        if (found==null)
            throw new CockpitNotFoundException("Unknown decoder address: "+decoderAddress);
        return new Cockpit(found);
    }

    @Override
    public Collection<Cockpit> getAllCockpits() {
        java.util.stream.Stream<Cockpit> mappedStream = decoderMap.values().stream().map( (cockpit) -> new Cockpit(cockpit) );
        return mappedStream.collect(Collectors.toList());
    }

    @Override
    public void save(@NonNull Cockpit cockpit) {
        Cockpit old = dataMap.get(cockpit.getGuid());
        if (old != null) {
            decoderMap.remove(old.getDecoderAddress());
        }
        Cockpit privateInstance = new Cockpit(cockpit);
        dataMap.put(cockpit.getGuid(), privateInstance);
        decoderMap.put(cockpit.getDecoderAddress(), privateInstance);
    }

    
}
