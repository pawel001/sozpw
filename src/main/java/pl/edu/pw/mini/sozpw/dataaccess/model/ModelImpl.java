package pl.edu.pw.mini.sozpw.dataaccess.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import pl.edu.pw.mini.sozpw.dataaccess.persistence.HibernateUtil;
import pl.edu.pw.mini.sozpw.dataaccess.services.ModelToViewModelConverter;
import pl.edu.pw.mini.sozpw.dataaccess.services.RandomStringGenerator;

import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Comment;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Note;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.User;

public class ModelImpl implements Model {

	//private static final String DEFAULT_KEY = "defaultKey";
	//private static int noteId = 1;

	@Override
	public User loginUser(String username, String pass) {
		try {
			System.out.println("Czy tutaj");
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session
					.createQuery("from User where username = :username");
			query.setParameter("username", username);

			@SuppressWarnings("rawtypes")
			List result = query.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result
						.get(0);
			} catch (Exception e) {
				return null;
			}
			if (user != null && user.getPassword().equals(pass)
					&& user.getIsActive()) {
				User ret = new User();
				ret.setUsername(username);
				return ret;
			}
			// TODO zmiana last login date u usera
			// if (pass.equals("123")) {
			// User ret = new User();
			// ret.setUsername(username);
			// return ret;
			// }
		} catch (Exception e) {
			return null;
		}

		return null;
	}

	@Override
	public String registerUser(String username, String pass, String mail) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		// najpierw sprawdzam czy user istnieje, jak nie to go tworzę
		Query query = session
				.createQuery("from User where username = :username");
		query.setParameter("username", username);

		@SuppressWarnings("rawtypes")
		List result = query.list();
		pl.edu.pw.mini.sozpw.dataaccess.models.User user;
		try {
			user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result.get(0);
		} catch (Exception e) {
			return null;
		}

		if (username.equals("123")) {
			return null;
		}
		if (user != null)
			return null;

		pl.edu.pw.mini.sozpw.dataaccess.models.User newUser = new pl.edu.pw.mini.sozpw.dataaccess.models.User();
		java.util.Date date = new java.util.Date();
		newUser.setCreateDate(new Timestamp(date.getTime()));
		newUser.setEmail(mail);
		newUser.setIsActive(false);
		newUser.setPassword(pass);
		newUser.setPhone("");

		newUser.setUsername(username);
		newUser.setLastLoginDate(new Timestamp(date.getTime()));
		String key = RandomStringGenerator.randomString(30);
		newUser.setSalt(key);
		return key;
	}

	@Override
	public boolean confirmRegistration(String key) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		// wyszukuje usera powiazanego z notatka
		Query query = session.createQuery("from User where salt = :salt");
		query.setParameter("salt", key);

		@SuppressWarnings("rawtypes")
		List result = query.list();
		pl.edu.pw.mini.sozpw.dataaccess.models.User user;
		try {
			user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result.get(0);
		} catch (Exception e) {
			return false;
		}
		user.setIsActive(true);
		user.setSalt("");
		session.save(user);
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Note> getNotes(String username) {
		List<Note> noteResults =  new ArrayList<Note>();
		System.out.println("Get notes");
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		// wyszukuje usera powiazanego z komentarzem
		Query query = session
				.createQuery("from User where username = :username");
		query.setParameter("username", username);

		List result = query.list();
		pl.edu.pw.mini.sozpw.dataaccess.models.User user;
		try {
			user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result.get(0);
		} catch (Exception e) {
			System.out.println("ERROR przy pobieraniu Usera dla ktorego maja byc notatki: " + username);
			return new ArrayList<Note>();
		}
		
		System.out.println("Get notes: Pobrałem usera jego id to: " + user.getIdUsers());
		List<pl.edu.pw.mini.sozpw.dataaccess.models.Note> results1;
		List<pl.edu.pw.mini.sozpw.dataaccess.models.Note> results2;
		List<pl.edu.pw.mini.sozpw.dataaccess.models.Note> results3;
		try {
			results1 = (List) session.createQuery("from Note where user_id = " + user.getIdUsers()).list();
			results2 = (List) session.createQuery("from Note where addressedUser_id = " + user.getIdUsers()).list();
			results3 = (List) session.createQuery("from Note where user_id <> " + user.getIdUsers() 
					+ " AND " + "addressedUser_id <> " + user.getIdUsers()
					+ " AND " + "isPrivate = false" 
					).list();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			return new ArrayList<Note>();
		}
		System.out.println("Lista1 jeden ma " + results1.size() + " elementów");
		System.out.println("Lista2 jeden ma " + results2.size() + " elementów");
		System.out.println("Lista3 jeden ma " + results2.size() + " elementów");
		
		//LISTA MOICH
		for(pl.edu.pw.mini.sozpw.dataaccess.models.Note n : results1) {

			//UZUPELNIAM STRINGA Z USERNAMEM
			pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsername;
			try {
				userForUsername = 
						(pl.edu.pw.mini.sozpw.dataaccess.models.User) 
						session.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class, n.getUser_id());
			}
			catch (Exception e) { continue;}
			if(userForUsername == null) continue;

			//UZUPELNIAM KOMENTARZE
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Comment> commentsDb = (List) session.createQuery("from Comment where note_id = " + n.getNoteId()).list();
			ArrayList<Comment> commentsToAdd = new ArrayList<Comment>();
			for(pl.edu.pw.mini.sozpw.dataaccess.models.Comment c : commentsDb) {
				//UZUPELNIAM USERNAME DLA KOMENTARZA
				pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsernameComment;
				try {
					userForUsernameComment = 
							(pl.edu.pw.mini.sozpw.dataaccess.models.User) 
							session.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class, n.getUser_id());
				}
				catch (Exception e) { continue;}
				if(userForUsernameComment == null) continue;
				Comment commentToAdd = ModelToViewModelConverter.toViewModelComment(c, userForUsernameComment.getUsername());
				commentsToAdd.add(commentToAdd);
			}
			//UZUPELNIAM INFORMACJE O ADRESOWANYM USERZE
			ArrayList<String> dedicationList = new ArrayList<String>();
			pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsernameAddressed;
			try {
				userForUsernameAddressed = 
						(pl.edu.pw.mini.sozpw.dataaccess.models.User) 
						session.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class, n.getAddressedUser_id());
				dedicationList.add(userForUsernameAddressed.getUsername());
			}
			//NIC NIE ROBIE BO TO MOŻE NIE BYĆ ADRESOWANE
			catch (Exception e) { ;}
			
			Note noteViewModel = ModelToViewModelConverter.toViewModelNote(n, userForUsername.getUsername(), dedicationList, commentsToAdd);
			noteResults.add(noteViewModel);
		}
		
		//LISTA ADRESOWANYCH DO MNIE
		for(pl.edu.pw.mini.sozpw.dataaccess.models.Note n : results2) {
			//UZUPELNIAM STRINGA Z USERNAMEM
			pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsername;
			try {
				userForUsername = 
						(pl.edu.pw.mini.sozpw.dataaccess.models.User) 
						session.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class, n.getUser_id());
			}
			catch (Exception e) { continue;}
			if(userForUsername == null) continue;

			//UZUPELNIAM KOMENTARZE
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Comment> commentsDb = (List) session.createQuery("from Comment where note_id = " + n.getNoteId()).list();
			ArrayList<Comment> commentsToAdd = new ArrayList<Comment>();
			for(pl.edu.pw.mini.sozpw.dataaccess.models.Comment c : commentsDb) {
				//UZUPELNIAM USERNAME DLA KOMENTARZA
				pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsernameComment;
				try {
					userForUsernameComment = 
							(pl.edu.pw.mini.sozpw.dataaccess.models.User) 
							session.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class, n.getUser_id());
				}
				catch (Exception e) { continue;}
				if(userForUsernameComment == null) continue;
				Comment commentToAdd = ModelToViewModelConverter.toViewModelComment(c, userForUsernameComment.getUsername());
				commentsToAdd.add(commentToAdd);
			}
			//UZUPELNIAM INFORMACJE O ADRESOWANYM USERZE
			ArrayList<String> dedicationList = new ArrayList<String>();
			pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsernameAddressed;
			try {
				userForUsernameAddressed = 
						(pl.edu.pw.mini.sozpw.dataaccess.models.User) 
						session.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class, n.getAddressedUser_id());
				dedicationList.add(userForUsernameAddressed.getUsername());
			}
			//NIC NIE ROBIE BO TO MOŻE NIE BYĆ ADRESOWANE
			catch (Exception e) { ;}
			
			Note noteViewModel = ModelToViewModelConverter.toViewModelNote(n, userForUsername.getUsername(), dedicationList, commentsToAdd);
			noteResults.add(noteViewModel);
		}
		System.out.println("TEST C1");
		//LISTA PUBLICZNYCH
		for(pl.edu.pw.mini.sozpw.dataaccess.models.Note n : results3) {
			//UZUPELNIAM STRINGA Z USERNAMEM
			System.out.println("TEST D1");
			pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsername;
			try {
				userForUsername = 
						(pl.edu.pw.mini.sozpw.dataaccess.models.User) 
						session.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class, n.getUser_id());
			}
			catch (Exception e) { continue;}
			if(userForUsername == null) continue;
			System.out.println("TEST D2");
			//UZUPELNIAM KOMENTARZE
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Comment> commentsDb = (List) session.createQuery("from Comment where note_id = " + n.getNoteId()).list();
			ArrayList<Comment> commentsToAdd = new ArrayList<Comment>();
			for(pl.edu.pw.mini.sozpw.dataaccess.models.Comment c : commentsDb) {
				//UZUPELNIAM USERNAME DLA KOMENTARZA
				pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsernameComment;
				try {
					userForUsernameComment = 
							(pl.edu.pw.mini.sozpw.dataaccess.models.User) 
							session.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class, n.getUser_id());
				}
				catch (Exception e) { continue;}
				if(userForUsernameComment == null) continue;
				Comment commentToAdd = ModelToViewModelConverter.toViewModelComment(c, userForUsernameComment.getUsername());
				commentsToAdd.add(commentToAdd);
			}
			System.out.println("TEST D3");
			//UZUPELNIAM INFORMACJE O ADRESOWANYM USERZE
			ArrayList<String> dedicationList = new ArrayList<String>();
			pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsernameAddressed;
			try {
				userForUsernameAddressed = 
						(pl.edu.pw.mini.sozpw.dataaccess.models.User) 
						session.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class, n.getAddressedUser_id());
				dedicationList.add(userForUsernameAddressed.getUsername());
			}
			//NIC NIE ROBIE BO TO MOŻE NIE BYĆ ADRESOWANE
			catch (Exception e) { ;}
			System.out.println("TEST D4");
			Note noteViewModel = ModelToViewModelConverter.toViewModelNote(n, userForUsername.getUsername(), dedicationList, commentsToAdd);
			noteResults.add(noteViewModel);
		}
		System.out.println("Lista koncowa ma " + noteResults.size() + " elementów");
		return noteResults;
	}

	@Override
	public Integer addNote(Note note, byte[] attachment) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			// wyszukuje usera powiazanego z notatka
			Query query = session
					.createQuery("from User where username = :username");
			query.setParameter("username", note.getUsername());

			@SuppressWarnings("rawtypes")
			List result = query.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result
						.get(0);
			} catch (Exception e) {
				return -1;
			}
			// TODO dodać kod do wyciągania z dedication list
			// TODO KATEGORIE zmienic na enuma z idkami takimi jak w bazie
			// danych
			int addressedUserId = -1;
			if (note.getDedicationList().size() > 0) {
				Query queryForAddressedUser = session
						.createQuery("from User where username = :username");
				queryForAddressedUser.setParameter("username", note.getDedicationList().get(0));

				@SuppressWarnings("rawtypes")
				List resultAddressedUser = query.list();
				pl.edu.pw.mini.sozpw.dataaccess.models.User userAddressed;
				try {
					userAddressed = (pl.edu.pw.mini.sozpw.dataaccess.models.User) resultAddressedUser.get(0);
					addressedUserId = userAddressed.getIdUsers();
				} catch (Exception e) {
					;
				}
			}
			pl.edu.pw.mini.sozpw.dataaccess.models.Note noteModel = ModelToViewModelConverter
					.toDbNote(note, addressedUserId, false, -1, 1, user.getIdUsers());
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Point> backup = noteModel
					.getPoints();
			noteModel.setPoints(null);
			session.save(noteModel);
			for (pl.edu.pw.mini.sozpw.dataaccess.models.Point p : backup) {
				p.setNote(noteModel);
				session.save(p);
			}
			System.out.println("Dodaję notatkę o id: " + noteModel.getNoteId());
					
			if (attachment != null) {
				try {
					pl.edu.pw.mini.sozpw.dataaccess.models.Attachment attachmentModel = new pl.edu.pw.mini.sozpw.dataaccess.models.Attachment();
					attachmentModel.setFile(attachment);
					attachmentModel.setFilename(note.getFilename());
					attachmentModel.setFileSize(attachment.length);
					attachmentModel.setFileType("");
					attachmentModel.setNote_id(noteModel.getNoteId());
					session.save(attachmentModel);
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
			session.getTransaction().commit();
			return noteModel.getNoteId();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return -1;
	}

	@Override
	public boolean editNote(Note note, byte[] attachment) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		// wyszukuje usera powiazanego z notatka
		Query query = session
				.createQuery("from User where username = :username");
		query.setParameter("username", note.getUsername());

		@SuppressWarnings("rawtypes")
		List result = query.list();
		pl.edu.pw.mini.sozpw.dataaccess.models.User user;
		try {
			user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result.get(0);
		} catch (Exception e) {
			return false;
		}

		// TODO NARAZIE ZROBIONE POPRZEZ USUWANIE I DODAWANIE POTEM ZMIENIĆ
		// Note model = (Note) session.get(Note.class, note.getId());
		deleteNote(note.getId());
		// KONIEC USUWANIA
		
		
		//TODO pożądne dodawanie nowej notatki!!
		pl.edu.pw.mini.sozpw.dataaccess.models.Note noteModel = ModelToViewModelConverter
				.toDbNote(note, -1, false, -1, 1, user.getIdUsers());
		List<pl.edu.pw.mini.sozpw.dataaccess.models.Point> backup = noteModel
				.getPoints();
		noteModel.setPoints(null);
		session.save(noteModel);
		for (pl.edu.pw.mini.sozpw.dataaccess.models.Point p : backup) {
			p.setNote(noteModel);
			session.save(p);
		}
		if (attachment != null) {
			pl.edu.pw.mini.sozpw.dataaccess.models.Attachment attachmentModel = new pl.edu.pw.mini.sozpw.dataaccess.models.Attachment();
			attachmentModel.setFile(attachment);
			attachmentModel.setFilename(note.getFilename());
			attachmentModel.setFileSize(attachment.length);
			// attachmentModel.setFileType(fileInfo.getType());
			attachmentModel.setNote_id(noteModel.getNoteId());
			session.save(attachmentModel);
		}
		session.getTransaction().commit();
		return true;
	}

	@Override
	public boolean deleteNote(Integer noteId) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			pl.edu.pw.mini.sozpw.dataaccess.models.Note model = (pl.edu.pw.mini.sozpw.dataaccess.models.Note) session.get(pl.edu.pw.mini.sozpw.dataaccess.models.Note.class, noteId);
			session.delete(model);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean addComment(int noteId, Comment comment) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		// wyszukuje usera powiazanego z komentarzem
		Query query = session
				.createQuery("from User where username = :username");
		query.setParameter("username", comment.getUsername());

		@SuppressWarnings("rawtypes")
		List result = query.list();
		pl.edu.pw.mini.sozpw.dataaccess.models.User user;
		try {
			user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result.get(0);
		} catch (Exception e) {
			return false;
		}
		pl.edu.pw.mini.sozpw.dataaccess.models.Comment commentModel = ModelToViewModelConverter
				.toDbComment(comment, user.getIdUsers(), noteId);
		session.save(commentModel);
		session.getTransaction().commit();
		return true;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<String> getDedicationHints(String query, int count) {
		System.out.println("Get dedication hints");
		List<String> res = new ArrayList<String>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query queryForUsers = session
				.createQuery("from User where username like :username");
		queryForUsers.setParameter("username", query);
		
		List<pl.edu.pw.mini.sozpw.dataaccess.models.User> result = queryForUsers.list();
		System.out.println("Get dedication hints liczba trafien: " + result.size());
		for(pl.edu.pw.mini.sozpw.dataaccess.models.User u : result) {
			res.add(u.getUsername());
			if (res.size() == count) {
				break;
			}
		}
		return res;
	}

	@Override
	public List<String> getUserGropus(String user) {
		List<String> res = new ArrayList<String>();

		if (user.equals("Pawel")) {
			res.add("GrupaW1");
			res.add("Warszawa");
		}

		return res;
	}

	@Override
	public boolean addGroup(String user, String groupName) {
		return true;
	}

	@Override
	public void removeGroup(String gropuName) {
		return;
	}

	@Override
	public void assignUserToGroups(String username, List<String> groups) {
		return;
	}

	@Override
	public List<String> getGroupsHints(String query, int count) {
		List<String> names = Arrays.asList("GrupaW1", "Warszawa", "Riviera",
				"MiNI", "Politechnika");

		List<String> res = new ArrayList<String>();
		for (String name : names) {
			if (name.toLowerCase().startsWith(query.toLowerCase())) {
				res.add(name);
				if (res.size() == count) {
					break;
				}
			}
		}
		return res;
	}

	@Override
	public boolean changePassword(String oldPass, String newPass) {
		//TODO ta metoda koniecznie musi mieć id użytkownika albo chociaż username
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public byte[] getAttachment(int noteId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<pl.edu.pw.mini.sozpw.dataaccess.models.Attachment> results = (List) session.createQuery("from Attachment where note_id = " + noteId).list();
		pl.edu.pw.mini.sozpw.dataaccess.models.Attachment attachment;
		try {
			attachment = (pl.edu.pw.mini.sozpw.dataaccess.models.Attachment) results.get(0);
		} catch (Exception e) {
			System.out.println("ERROR przy pobieraniu załącznika dla notatki o Id: " + noteId);
			return null;
		}
		return attachment.getFile();
	}

}
