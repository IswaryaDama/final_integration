package com.stackroute.keepnote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.ReminderNotCreatedException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.repository.ReminderRepository;

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
public class ReminderServiceImpl implements ReminderService {

	/*
	 * Autowiring should be implemented for the ReminderRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
     @Autowired
     private ReminderRepository reminderRepo;
	/*
	 * This method should be used to save a new reminder.Call the corresponding
	 * method of Respository interface.
	 */
	public Reminder createReminder(Reminder reminder) throws ReminderNotCreatedException {

		Reminder reminder1 = reminderRepo.insert(reminder);
		if(reminder1!=null) {
			return reminder1;
		}else {
			throw new ReminderNotCreatedException("Reminder not found");
		}
	}

	/*
	 * This method should be used to delete an existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteReminder(String reminderId) throws ReminderNotFoundException {

		Reminder rem = getReminderById(reminderId);
		if(null!=rem) {
		reminderRepo.delete(rem);
		return true;
	}else {
		throw new ReminderNotFoundException("reminder not found");
	}
	}

	/*
	 * This method should be used to update a existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder updateReminder(Reminder reminder, String reminderId) throws ReminderNotFoundException {
        Reminder reminderObj=getReminderById(reminderId);
        if(null!=reminderObj) {
        	reminderRepo.save(reminderObj);
        	return reminderObj;
        }else {
        	throw new ReminderNotFoundException("Reminder not found");
        }
	}

	/*
	 * This method should be used to get a reminder by reminderId.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder getReminderById(String reminderId) throws ReminderNotFoundException {
        try
        {
        	Reminder reminder = reminderRepo.findById(reminderId).get();
        	if(null!=reminder) {
        		return reminder;
        	}else {
        		throw new ReminderNotFoundException("Reminder not found");
        	}
     }catch(Exception e) {
    	 throw new ReminderNotFoundException("Reminder not found");
 
     }
	}

	/*
	 * This method should be used to get all reminders. Call the corresponding
	 * method of Respository interface.
	 */

	public List<Reminder> getAllReminders() {

		return reminderRepo.findAll();
	}

}
