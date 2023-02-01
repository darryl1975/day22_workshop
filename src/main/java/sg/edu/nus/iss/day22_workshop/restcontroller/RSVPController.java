package sg.edu.nus.iss.day22_workshop.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import java.util.*;
import sg.edu.nus.iss.day22_workshop.repo.*;
import sg.edu.nus.iss.day22_workshop.model.*;

@RequestMapping("/api/rsvps")
@RestController
public class RSVPController {
    
    @Autowired
    RsvpRepoImpl rsvpRepo;

    // @RequestMapping(path = "/", method=RequestMethod.GET, produces="application/json")
    @GetMapping(path={"/", "/home"}, produces="application/json")
    public ResponseEntity<List<RSVP>> getAllRSVPS() {
        List<RSVP> listRSVP = new ArrayList<RSVP>();

        listRSVP = rsvpRepo.findAll();

        if (listRSVP.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(listRSVP, HttpStatus.OK);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<RSVP>> getRSVPByName(@RequestParam("name") String name) {
        List<RSVP> listRSVP = new ArrayList<RSVP>();

        listRSVP = rsvpRepo.findByName(name);

        if (listRSVP.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(listRSVP, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> saveRSVP(@RequestBody RSVP rsvp) {
        try {
            RSVP r = rsvp;
            Boolean saved = rsvpRepo.save(r);

            if (saved) {
                return new ResponseEntity<>("RSVP record created successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("RSVP record failed to create.", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("Server fail to process saveRSVP.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRSVP(@PathVariable("id") Integer id, @RequestBody RSVP rsvp) {
   
        RSVP r = rsvpRepo.findById(id);

        Boolean result = false;
        if (r != null) {
            result = rsvpRepo.update(rsvp);
        }

        if (result) {
            return new ResponseEntity<>("RSVP record updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("RSVP record failed to update.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getRSVPCount() {
        Integer rsvpCount = rsvpRepo.countAll();

        return new ResponseEntity<>(rsvpCount, HttpStatus.OK);
    }
}
