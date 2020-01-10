package com.stackroute.keepnote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.exception.CategoryNotCreatedException;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.repository.CategoryRepository;

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
public class CategoryServiceImpl implements CategoryService {

	/*
	 * Autowiring should be implemented for the CategoryRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
     @Autowired
     private CategoryRepository categoryRepo;
	/*
	 * This method should be used to save a new category.Call the corresponding
	 * method of Respository interface.
	 */
	
	public Category createCategory(Category category) throws CategoryNotCreatedException {
 
		Category cat=categoryRepo.insert(category);
		if(null!=cat) {
			return cat;
		}else {
			throw new CategoryNotCreatedException("category not found");
		}
	}

	/*
	 * This method should be used to delete an existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteCategory(String categoryId) throws CategoryDoesNoteExistsException {
       
		categoryRepo.deleteById(categoryId);
		return true;
	}

	/*
	 * This method should be used to update a existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public Category updateCategory(Category category, String categoryId) {
        
		Category updatedCat = null;
		try {
			if(null!=getCategoryById(categoryId)) {
			  
				updatedCat= categoryRepo.save(category);
				
				return category;
			}
		} catch (CategoryNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return updatedCat;
	}

	/*
	 * This method should be used to get a category by categoryId.Call the
	 * corresponding method of Respository interface.
	 */
	public Category getCategoryById(String categoryId) throws CategoryNotFoundException {

		//Category category = categoryRepo.findById(categoryId).get();
		try {
			Category category = categoryRepo.findById(categoryId).get();
			if(null!=category) {
				return category;
			}
			else {
				throw new CategoryNotFoundException("category not found");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new CategoryNotFoundException("category not found");
		}
	}

	/*
	 * This method should be used to get a category by userId.Call the corresponding
	 * method of Respository interface.
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
     
		List<Category> categories = categoryRepo.findAllCategoryByCategoryCreatedBy(userId);
		return categories;
	}

}
