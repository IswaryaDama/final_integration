package com.stackroute.keepnote.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.service.NoteService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class NoteController {

	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	@Autowired
	private NoteService noteService;

	public NoteController(NoteService noteService) {
		
		this.noteService=noteService;
	}
	
	@RequestMapping("/")
	public String swaggerUI() {
		return "redirect:swagger-ui.html";
	}
	
	@PostMapping("/api/v1/note")
	public ResponseEntity<?> createNote(@RequestBody Note note){
		
				note.setNoteCreationDate(new Date());
				if(noteService.createNote(note)) {
					return new ResponseEntity<Note>(note,HttpStatus.CREATED);
				}
				else {
					return new ResponseEntity<>(note.getNoteId(),HttpStatus.CONFLICT);
				}
			
	}
	
	@DeleteMapping("/api/v1/note/{userId}/{noteId}")
	public ResponseEntity<?> deleteNote(@PathVariable("userId") String userId,@PathVariable("noteId") int noteId){
		
		try {
				if(noteService.deleteNote(userId,noteId)) {
					return new ResponseEntity<Note>(HttpStatus.OK);
				}else {
					return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
				}
			}catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>("Exception occured",HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/api/v1/note/{userId}")
	public ResponseEntity<?> deleteAllNotes(@PathVariable("userId") String userId){
		try {
			if(noteService.deleteAllNotes(userId)) {
				return new ResponseEntity<Note>(HttpStatus.OK);
			}else {
				return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
		// TODO Auto-generated catch block
		return new ResponseEntity<String>("Exception occured",HttpStatus.NOT_FOUND);
	}
	}
	
	@PutMapping("/api/v1/note/{userId}/{noteId}")
	public ResponseEntity<?> updateNote(@RequestBody Note note,@PathVariable("userId") String userId,@PathVariable("noteId") int noteId){
		try {
			Note notes=noteService.updateNote(note,noteId,userId);
				if(notes!=null) {
					return new ResponseEntity<Note>(notes,HttpStatus.OK);
				}else {
					return new ResponseEntity<Note>(notes,HttpStatus.NOT_FOUND);
				}
			}catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>("Exception occured",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/api/v1/note/{userId}")
	public ResponseEntity<?> getAllNoteById(@PathVariable("userId") String userId){
		
		List<Note> notes = noteService.getAllNoteByUserId(userId);
		if(notes!=null) {
				
			return new ResponseEntity<List<Note>>(notes,HttpStatus.OK);
	
			}
			else {
				return new ResponseEntity<List<Note>>(notes,HttpStatus.OK);
			}
		
	}
	
	@GetMapping("/api/v1/note/{userId}/{noteId}")
	public ResponseEntity<?> getNoteById(@PathVariable("userId") String userId,@PathVariable("noteId") int noteId){
		
		Note notes;
		try {
			notes = noteService.getNoteByNoteId(userId, noteId);
			if(notes!=null) {
				
				return new ResponseEntity<Note>(notes,HttpStatus.OK);
		
				}
				else {
					return new ResponseEntity<String>("notes not found",HttpStatus.NOT_FOUND);
				}
		} catch (NoteNotFoundExeption e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>("notes not found",HttpStatus.NOT_FOUND);

		}
		
		
	}


	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 201(CREATED) - If the note created successfully. 
	 * 2. 409(CONFLICT) - If the noteId conflicts with any existing user.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP POST method
	 */

	/*
	 * Define a handler method which will delete a note from a database.
	 * This handler method should return any one of the status messages basis 
	 * on different situations: 
	 * 1. 200(OK) - If the note deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/note/{userId}/{noteId}" using HTTP Delete
	 * method" where "id" should be replaced by a valid noteId without {}
	 */

	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * database. 
	 * This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 200(OK) - If the note updated successfully.
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/note/{userId}/{noteId}" using HTTP PUT method.
	 */
	
	/*
	 * Define a handler method which will get us the all notes by a userId.
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the note found successfully. 
	 * 
	 * This handler method should map to the URL "/api/v1/note/{userId}" using HTTP GET method
	 */
	
	/*
	 * Define a handler method which will show details of a specific note created by specific 
	 * user. This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the note found successfully. 
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * This handler method should map to the URL "/api/v1/note/{userId}/{noteId}" using HTTP GET method
	 * where "id" should be replaced by a valid reminderId without {}
	 * 
	 */


}
