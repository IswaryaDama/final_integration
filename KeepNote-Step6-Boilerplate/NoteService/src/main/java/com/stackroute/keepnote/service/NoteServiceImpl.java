package com.stackroute.keepnote.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.repository.NoteRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class NoteServiceImpl implements NoteService{

	/*
	 * Autowiring should be implemented for the NoteRepository and MongoOperation.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	
	@Autowired
	private NoteRepository noteRepo;
	
	@Autowired
	private MongoOperations mongoOpe;
	
	/*@Autowired
	private NoteUser noteuser;*/
	
	/*
	 * This method should be used to save a new note.
	 */
	public boolean createNote(Note note) {
		try {
			Random r = new Random();
			note.setNoteId(r.nextInt(1000));
			List<Note> notelist = new ArrayList<Note>();
			NoteUser ns =new NoteUser();
			/*notelist.add(note);
			ns.setNotes(notelist);*/
			/*notelist.add(note);
			NoteUser noteuser =new NoteUser();
			noteuser.setUserId(note.getNoteCreatedBy());
			noteuser.setNotes(notelist);*/
			
			ns.setUserId(note.getNoteCreatedBy());
			
			Optional<NoteUser> noteuser=noteRepo.findById(note.getNoteCreatedBy());
			
			if(noteuser.isPresent()) {
				notelist = noteuser.get().getNotes();
				notelist.add(note);
				ns.setNotes(notelist);
				noteRepo.save(ns);
				return true;
			}else {
				notelist.add(note);
				ns.setNotes(notelist);
				noteRepo.insert(ns);
				return true;
			}
					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
		
	}
	
	/* This method should be used to delete an existing note. */

	
	public boolean deleteNote(String userId, int noteId) {
		
		try {
			Note note=getNoteByNoteId(userId,noteId);
			List<Note> notes=getAllNoteByUserId(userId);
			notes.removeIf(p -> p.getNoteId()==noteId);
			NoteUser noteuser = new NoteUser();
			noteuser.setNotes(notes);
			noteuser.setUserId(userId);
			noteRepo.save(noteuser);
			return true;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			throw new NullPointerException();
		}catch(Exception e) {
			return false;
		}
		//NoteUser note= noteRepo.findById(userId).get();
		
	}
	
	/* This method should be used to delete all notes with specific userId. */

	
	public boolean deleteAllNotes(String userId) {
		
		noteRepo.deleteById(userId);
		return true;
	}

	/*
	 * This method should be used to update a existing note.
	 */
	public Note updateNote(Note note, int id, String userId) throws NoteNotFoundExeption {
		
		NoteUser noteUser = null;
        
        try {
               noteUser = noteRepo.findById(userId).get();
        } catch (NoSuchElementException e) {
               e.printStackTrace();
        }
        if (noteUser != null) {
               List<Note> notes = noteUser.getNotes();
               Note fetchedNote = notes.stream().filter(notefilter -> notefilter.getNoteId() == id).findFirst()
                            .orElse(null);
               if (note != null) {
                     noteUser.getNotes().remove(fetchedNote);
                     noteUser.getNotes().add(note);
                     noteUser.setUserId(userId);
                     noteRepo.save(noteUser);
               }
        } else {
               throw new NoteNotFoundExeption("Not found");
        }
        return note;
	
	}

	/*
	 * This method should be used to get a note by noteId created by specific user
	 */
	public Note getNoteByNoteId(String userId, int noteId) throws NoteNotFoundExeption {
		try{

			Note notes=noteRepo.findById(userId).get().getNotes().stream().filter(n->n.getNoteId()==noteId).findFirst().get();
		if(notes!=null) {
			return notes;
		}else {
			throw new NoteNotFoundExeption("Note not found");
		}
		}catch(Exception e) {
			
			throw new NoteNotFoundExeption("note not found");
		}
	}

	/*
	 * This method should be used to get all notes with specific userId.
	 */
	public List<Note> getAllNoteByUserId(String userId) {
		List<Note> allNotes=null;
		try {
			//List<NoteUser> ls = noteRepo.findAll();
			//ls.get
			
			allNotes=noteRepo.findAll().stream().filter(n->n.getUserId().equalsIgnoreCase(userId)).findAny().get().getNotes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		
		return allNotes;
	}

}
