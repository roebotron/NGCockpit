package frank.cockpit;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Getter @Setter
@EqualsAndHashCode
@ToString(exclude="speedSteps")
public class Cockpit {
    @Id
    private @Nullable UUID guid;
    
    private @NonNull String name;
    private int decoderAddress;
    private int currentSpeedStep;
    private int speedStepCount;
    
    @Transient
    private @NonNull List<Integer> speedSteps = new ArrayList<>();

    public Cockpit() {
        setSpeedStepCount(16);
    }

    public Cockpit(UUID guid) {
        this();
        this.guid = guid;
    }

    public Cockpit(Cockpit source)
    {
        this.guid = source.guid;
        this.name = source.name;
        this.decoderAddress = source.decoderAddress;
        this.currentSpeedStep = source.currentSpeedStep;
        this.speedStepCount = source.speedStepCount;
        this.speedSteps = new ArrayList<>(source.speedSteps);
    }
    
    @Basic(optional=false)
    @Access(AccessType.PROPERTY)
    public String getSpeedStepString()
    {
        return speedSteps.stream().map(i -> i.toString()).collect(Collectors.joining(","));
    }

    public void setSpeedSteps(List<Integer> speedSteps)
    {
        if (speedSteps.isEmpty()) {
            Collections.fill(speedSteps, 0);
        } else {
            this.speedSteps = new ArrayList<>(speedSteps);
            this.speedSteps.set(0, 0);
            this.speedStepCount = speedSteps.size();
        }
    }
    
    public void setSpeedStepString(String speedStepString)
    {
        if (speedStepString.isEmpty()) {
            Collections.fill(speedSteps, 0);
        } else {
            speedSteps = Stream.of(speedStepString.split(",")).map(speed -> Integer.valueOf(speed)).collect(Collectors.toList());
            speedSteps.set(0, 0);
            speedStepCount = speedSteps.size();
        }
    }
    
    public void setSpeedStepCount(int speedStepCount)
    {
        this.speedStepCount = speedStepCount;
        while (speedStepCount < speedSteps.size()) {
            speedSteps.remove(speedSteps.size() - 1);
        }
        while (speedStepCount > speedSteps.size()) {
            speedSteps.add(speedSteps.isEmpty() ? 0 : speedSteps.get(speedSteps.size() - 1));
        }
    }
}
