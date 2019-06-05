package Presentation.Controller;

import Domain.Entity.Note;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {

    @GetMapping("/notes")
    public String getNotes() {
        return "hahah lol";
    }

    @GetMapping("/notes/{id}")
    public Note getNote(@PathVariable long id){
        return null;
    }

    @PostMapping("/note")
    public void setNote(@RequestBody Note note){
        // lol
    }

    @PutMapping("/note/{id}")
    public void editNote(@PathVariable long id, @RequestBody Note note){

    }

    @DeleteMapping("note/{id}")
    public void deleteNote(@PathVariable long id){

    }

}
