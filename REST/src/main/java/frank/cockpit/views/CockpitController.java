package frank.cockpit.views;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import frank.cockpit.Cockpit;
import frank.cockpit.CockpitNotFoundException;
import frank.cockpit.ICockpitService;
import frank.cockpit.views.CockpitView;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://esprimo:4200", "http://esprimo.fritz.box:4200"})
public class CockpitController {

    private ICockpitService service;

    public CockpitController(ICockpitService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cockpit", produces = MediaType.APPLICATION_JSON_VALUE)
    public CockpitView create(@RequestBody CockpitView cockpitView) {
        // ...
        // if(usernameAlreadyExists) {
        // throw new IllegalArgumentException("error.username");
        // }
        // ...
        Cockpit entity = view2target(cockpitView, service.createCockpit()); 
        service.save(entity);
        //Käse, stattdessen muss auf die neue URI umgeschaltet werden
        return target2view(entity, new CockpitView());
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/cockpit/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    //public ResponseEntity<?> ...
    public CockpitView updateCockpit(@PathVariable("guid") String guid, @RequestBody CockpitView cockpitView) throws CockpitNotFoundException {
        Cockpit cockpit = service.getCockpit(UUID.fromString(guid));
        service.save(view2target(cockpitView, cockpit));
        return target2view(cockpit, new CockpitView());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cockpit/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CockpitView getCockpit(@PathVariable("guid") String guid) throws CockpitNotFoundException {
        Cockpit cockpit = service.getCockpit(UUID.fromString(guid)); 
        return target2view(cockpit, new CockpitView());
    }

    @GetMapping(value = "/cockpit/all")
    public List<CockpitView> getAllCockpits() {
        return service.getAllCockpits().stream().map(x -> target2view(x, new CockpitView())).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cockpit/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public CockpitView getCockpitByDecoder(@RequestParam(value="ID", required=false) String guid,
                                           @RequestParam(value="decoderAddress", defaultValue="0", required=false) int decoderAddress) throws CockpitNotFoundException {
        CockpitView view = new CockpitView();
        Cockpit cockpit = guid != null ? service.getCockpit(UUID.fromString(guid)) 
                                       : service.getCockpitByDecoderAddress(decoderAddress); 
        target2view(cockpit, view);
        return view;
    }

    
    private static Cockpit view2target(CockpitView view, Cockpit target)
    {
        target.setName(view.name);
        target.setDecoderAddress(view.decoderAddress);
        target.setCurrentSpeedStep(view.currentSpeedStep);
        target.setSpeedSteps(
                view.speedSteps != null ? Arrays.stream(view.speedSteps).boxed().collect(Collectors.toList())
                        : Collections.emptyList());
        return target;
    }

    private static CockpitView target2view(Cockpit target, CockpitView view) {
        view.guid = target.getGuid().toString();
        view.name = target.getName();
        view.decoderAddress = target.getDecoderAddress();
        view.currentSpeedStep = target.getCurrentSpeedStep();
        view.speedSteps = target.getSpeedSteps().stream().mapToInt(Integer::intValue).toArray();
        return view;
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.BAD_REQUEST.value());

    }

    @ExceptionHandler
    void handleCockpitNotFoundException(CockpitNotFoundException e, HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());

    }
}
