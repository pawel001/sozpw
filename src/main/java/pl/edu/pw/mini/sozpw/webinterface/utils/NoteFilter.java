package pl.edu.pw.mini.sozpw.webinterface.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Category;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Note;

public class NoteFilter {
	
	String text;

	List<Category> categories;

	long createdAfter;
	long createdBefore;

	long expiryAfter;
	long expiryBefore;

	List<String> dedicatedTo;

	public NoteFilter() {
		reset();
	}

	public void reset() {
		text = "";
		
		categories = new ArrayList<Category>(Arrays.asList(Category.values()));
		dedicatedTo = new ArrayList<String>();

		createdAfter = 0;
		createdBefore = Long.MAX_VALUE;

		expiryAfter = 0;
		expiryBefore = Long.MAX_VALUE;
	}

	public boolean pass(Note note) {
		
		if(!note.getTopic().toLowerCase().contains(text.toLowerCase()) && !note.getContent().toLowerCase().contains(text.toLowerCase()))
			return false;
		
		if (!categories.contains(note.getCategory()))
			return false;

		if (note.getCreateDate() < createdAfter
				|| note.getCreateDate() > createdBefore)
			return false;

		if (note.getExpiryDate() < expiryAfter
				|| note.getExpiryDate() > expiryBefore)
			return false;

		boolean result = true;

		if (dedicatedTo.size() > 0) {
			result = false;

			for (String filterItem : dedicatedTo) {
				if (note.getDedicationList().contains(filterItem)) {
					result = true;
					break;
				}
			}
		}

		return result;

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public long getCreatedAfter() {
		return createdAfter;
	}

	public void setCreatedAfter(long createdAfter) {
		this.createdAfter = createdAfter;
	}

	public long getCreatedBefore() {
		return createdBefore;
	}

	public void setCreatedBefore(long createdBefore) {
		this.createdBefore = createdBefore;
	}

	public long getExpiryAfter() {
		return expiryAfter;
	}

	public void setExpiryAfter(long expiryAfter) {
		this.expiryAfter = expiryAfter;
	}

	public long getExpiryBefore() {
		return expiryBefore;
	}

	public void setExpiryBefore(long expiryBefore) {
		this.expiryBefore = expiryBefore;
	}

	public List<String> getDedicatedTo() {
		return dedicatedTo;
	}

	public void setDedicatedTo(List<String> dedicatedTo) {
		this.dedicatedTo = dedicatedTo;
	}
}
