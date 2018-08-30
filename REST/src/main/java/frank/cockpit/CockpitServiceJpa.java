package frank.cockpit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@Primary //Notlösung, wie geht's besser?
public class CockpitServiceJpa implements ICockpitService {
    
    private CockpitRepository repo;
    
    public CockpitServiceJpa(CockpitRepository repo)
    {
        this.repo = repo;
        if (repo.count() == 0)
        {
            createDefaults();
        }
    }
    
    private void createDefaults()
    {
        {
            Cockpit ret = new Cockpit(UUID.randomUUID());
            ret.setDecoderAddress(3);
            ret.setName("PIKO default");
            ret.setSpeedSteps(Arrays.asList(0, 10, 20, 30, 40, 50, 60, 70, 80, 90));
            ret.setSpeedStepString(ret.getSpeedStepString());
            save(ret);
        }
        {
            Cockpit ret = new Cockpit(UUID.randomUUID());
            ret.setDecoderAddress(7);
            ret.setName("SEVEN default");
            ret.setSpeedSteps(Arrays.asList(0, 11, 22, 33, 44, 55, 66, 77, 88, 99));
            save(ret);
        }
        System.err.println("Defaults created with "+repo.toString());
    }
    
    @Override
    public Cockpit createCockpit() {
        return new Cockpit(UUID.randomUUID());
    }

    @Override
    public @NonNull Cockpit getCockpit(@NonNull UUID guid) throws CockpitNotFoundException {
        List<Cockpit> found = repo.findByGuid(guid);
        if (found.isEmpty())
            throw new CockpitNotFoundException(guid.toString());
        return found.get(0);
    }

    @Override
    public @NonNull Cockpit getCockpitByDecoderAddress(@NonNull int decoderAddress) throws CockpitNotFoundException {
        List<Cockpit> found = repo.findByDecoderAddress(decoderAddress);
        if (found.isEmpty())
            throw new CockpitNotFoundException("Unknown decoder address: "+decoderAddress);
        return found.get(0);
    }

    @Override
    public Collection<Cockpit> getAllCockpits() {
        ArrayList<Cockpit> all = new ArrayList<>(); 
        repo.findAll().forEach(all::add);
        return all;
    }

    @Override
    public void save(@NonNull Cockpit cockpit) {
        //Cockpit myInstance = repo.findById(cockpit.getGuid()).get();
        repo.save(cockpit);
    }

    
}
