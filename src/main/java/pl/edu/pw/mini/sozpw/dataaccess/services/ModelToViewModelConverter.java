package pl.edu.pw.mini.sozpw.dataaccess.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Category;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Comment;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Note;

public class ModelToViewModelConverter {
	
	//konwersja NOTATKA ViewModel => Model bazodanowy
	public static pl.edu.pw.mini.sozpw.dataaccess.models.Note toDbNote(Note note, int addressedUserId, boolean isAddressedToGroup, int groupId, int cathegoryId, int userId) {
		pl.edu.pw.mini.sozpw.dataaccess.models.Note model = new pl.edu.pw.mini.sozpw.dataaccess.models.Note();
		model.setAddressedUser_id(addressedUserId);
		model.setCreateDate(new Timestamp(note.getCreateDate()));
		model.setExpirationDate(new Timestamp(note.getExpiryDate()));
		//TODO dodać pole zastanowić się jak to ma być
		model.setIsPrivate(true);
		model.setIsAddressedToGroup(isAddressedToGroup);
		model.setGroup_id(groupId);
		
		pl.edu.pw.mini.sozpw.dataaccess.models.Point notePoint = new pl.edu.pw.mini.sozpw.dataaccess.models.Point();
		notePoint.setLatitude((float)note.getLatitude());
		notePoint.setLongitude((float)note.getLongitude());
		List<pl.edu.pw.mini.sozpw.dataaccess.models.Point> notePoints = new ArrayList<pl.edu.pw.mini.sozpw.dataaccess.models.Point>();
		notePoints.add(notePoint);
		model.setPoints(notePoints);
		model.setText(note.getContent());
		model.setTopic(note.getTopic());
		model.setCathegory_id(cathegoryId);
		model.setUser_id(userId);
		return model;
	}
	
	//Konwersja NOTATKA Model bazodanowy => ViewModel	
	public static Note toViewModelNote(pl.edu.pw.mini.sozpw.dataaccess.models.Note model, String username, ArrayList<String> arrayList) {
		Note note = new Note();
		note.setUsername(username);
		note.setLatitude(model.getPoints().get(0).getLatitude());
		note.setLongitude(model.getPoints().get(0).getLongitude());
		note.setTopic( model.getTopic());
		note.setExpiryDate( model.getExpirationDate().getTime());
		note.setCreateDate( model.getCreateDate().getTime());
		note.setContent( model.getText());
		note.setId(model.getNoteId());
		//TODO dodać wyciąganie komentarzy
		note.setComments( new ArrayList<Comment>());
		//TODO to musi być po Idku żeby to później wyciągać z powrotem - nie widzę nigdzie pobierania załączników
		note.setFilename( "");
		note.setDedicationList( arrayList);
		//TODO tutaj jakaś poprawka
		note.setCategory(Category.BRAK_KATEGORII);
		
		return note;
	}
	
	//Konwersja KOMENTARZ Viewmodel => Model bazodanowy
	public static pl.edu.pw.mini.sozpw.dataaccess.models.Comment toDbComment(Comment comment, int userId, int noteId) {
		pl.edu.pw.mini.sozpw.dataaccess.models.Comment commentModel = new pl.edu.pw.mini.sozpw.dataaccess.models.Comment();
		commentModel.setNote_id(noteId);
		commentModel.setUser_id(userId);
		commentModel.setText(comment.getComment());
		return commentModel;
	} 
	
	//TODO Konwersja KOMENTARZ Model bazodanowy => Viewmodel

}
