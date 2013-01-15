package pl.edu.pw.mini.sozpw.dataaccess.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Category;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Comment;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Note;

public class ModelToViewModelConverter {

	// konwersja NOTATKA ViewModel => Model bazodanowy
	public static pl.edu.pw.mini.sozpw.dataaccess.models.Note toDbNote(
			Note note, int addressedUserId, boolean isAddressedToGroup,
			int groupId, int userId, boolean isPrivate) {
		pl.edu.pw.mini.sozpw.dataaccess.models.Note model = new pl.edu.pw.mini.sozpw.dataaccess.models.Note();
		model.setAddressedUser_id(addressedUserId);
		model.setCreateDate(new Timestamp(note.getCreateDate()));
		if (note.getExpiryDate() != 0)
			model.setExpirationDate(new Timestamp(note.getExpiryDate()));
		else
			model.setExpirationDate(null);
		model.setIsPrivate(isPrivate);
		model.setIsAddressedToGroup(isAddressedToGroup);
		model.setGroup_id(groupId);

		pl.edu.pw.mini.sozpw.dataaccess.models.Point notePoint = new pl.edu.pw.mini.sozpw.dataaccess.models.Point();
		notePoint.setLatitude((float) note.getLatitude());
		notePoint.setLongitude((float) note.getLongitude());
		List<pl.edu.pw.mini.sozpw.dataaccess.models.Point> notePoints = new ArrayList<pl.edu.pw.mini.sozpw.dataaccess.models.Point>();
		notePoints.add(notePoint);
		model.setPoints(notePoints);
		model.setText(note.getContent());
		model.setTopic(note.getTopic());
		model.setCathegory_id(note.getCategory().ordinal());
		model.setUser_id(userId);
		return model;
	}

	// Konwersja NOTATKA Model bazodanowy => ViewModel
	public static Note toViewModelNote(
			pl.edu.pw.mini.sozpw.dataaccess.models.Note model, String username,
			ArrayList<String> dedicationList, ArrayList<Comment> comments,
			String filename) {
		Note note = new Note();
		note.setUsername(username);
		note.setLatitude(model.getPoints().get(0).getLatitude());
		note.setLongitude(model.getPoints().get(0).getLongitude());
		note.setTopic(model.getTopic());

		if (model.getExpirationDate() != null)
			note.setExpiryDate(model.getExpirationDate().getTime());
		else
			note.setExpiryDate(0);
		note.setCreateDate(model.getCreateDate().getTime());
		note.setContent(model.getText());
		note.setId(model.getNoteId());
		note.setComments(comments);
		note.setFilename(filename);
		note.setDedicationList(dedicationList);
		note.setCategory(Category.values()[model.getCathegory_id()]);

		return note;
	}

	// Konwersja KOMENTARZ Viewmodel => Model bazodanowy
	public static pl.edu.pw.mini.sozpw.dataaccess.models.Comment toDbComment(
			Comment comment, int userId, int noteId) {
		pl.edu.pw.mini.sozpw.dataaccess.models.Comment commentModel = new pl.edu.pw.mini.sozpw.dataaccess.models.Comment();
		commentModel.setNote_id(noteId);
		commentModel.setUser_id(userId);
		commentModel.setText(comment.getComment());
		commentModel.setCreateDate(new Timestamp(comment.getDate()));
		return commentModel;
	}

	// Konwersja KOMENTARZ Model bazodanowy => Viewmodel
	public static Comment toViewModelComment(
			pl.edu.pw.mini.sozpw.dataaccess.models.Comment comment,
			String username) {
		Comment result = new Comment();
		result.setComment(comment.getText());
		result.setUsername(username);
		result.setDate(comment.getCreateDate().getTime());
		return result;
	}
}
