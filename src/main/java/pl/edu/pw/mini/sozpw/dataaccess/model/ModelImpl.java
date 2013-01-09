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
		try {
			results1 = (List) session.createQuery("from Note where user_id = " + user.getIdUsers()).list();
			results2 = (List) session.createQuery("from Note where addressedUser_id = " + user.getIdUsers()).list();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			return new ArrayList<Note>();
		}
		System.out.println("Lista1 jeden ma " + results1.size() + " elementów");
		System.out.println("Lista2 jeden ma " + results2.size() + " elementów");
		for(pl.edu.pw.mini.sozpw.dataaccess.models.Note n : results1) {
			//Query queryForUsername = session
			//		.createQuery("from User where username = :username");
			//queryForUsername.setParameter("idUsers", n.getUser_id());
			pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsername;
			try {
				userForUsername = 
						(pl.edu.pw.mini.sozpw.dataaccess.models.User) 
						session.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class, n.getUser_id());
			}
			catch (Exception e) { continue;}
			if(userForUsername == null) continue;
			//List resultForUsername = queryForUsername.list();
			//pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsername;
			//try {
			//	userForUsername = (pl.edu.pw.mini.sozpw.dataaccess.models.User) resultForUsername.get(0);
			//} catch (Exception e) {
			//	continue;
			//}
			
			Note noteViewModel = ModelToViewModelConverter.toViewModelNote(n, userForUsername.getUsername(), new ArrayList<String>());
			noteResults.add(noteViewModel);
		}
		
		for(pl.edu.pw.mini.sozpw.dataaccess.models.Note n : results2) {
			//Query queryForUsername = session
			//		.createQuery("from User where username = :username");
			//queryForUsername.setParameter("idUsers", n.getUser_id());
			pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsername;
			try {
				userForUsername = 
						(pl.edu.pw.mini.sozpw.dataaccess.models.User) 
						session.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class, n.getUser_id());
			}
			catch (Exception e) { continue;}
			if(userForUsername == null) continue;
			//List resultForUsername = queryForUsername.list();
			//pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsername;
			//try {
			//	userForUsername = (pl.edu.pw.mini.sozpw.dataaccess.models.User) resultForUsername.get(0);
			//} catch (Exception e) {
			//	continue;
			//}
			
			Note noteViewModel = ModelToViewModelConverter.toViewModelNote(n, userForUsername.getUsername(), new ArrayList<String>());
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
		// TODO dodać kod do wyciągania z dedication list
		// TODO KATEGORIE zmienic na enuma z idkami takimi jak w bazie danych

		// TODO NARAZIE ZROBIONE POPRZEZ USUWANIE I DODAWANIE POTEM ZMIENIĆ
		// Note model = (Note) session.get(Note.class, note.getId());
		deleteNote(note.getId());
		// KONIEC USUWANIA

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
			// TODO usunąć punkty??? kaskada?
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

	@Override
	public List<String> getDedicationHints(String query, int count) {
		List<String> names = Arrays.asList("Jacob", "Emily", "Michael",
				"Madison", "Joshua", "Emma", "Matthew", "Hannah",
				"Christopher", "Olivia", "Andrew", "Abigail", "Daniel",
				"Isabella", "Ethan", "Ashley", "Joseph", "Samantha", "William",
				"Elizabeth", "Anthony", "Alexis", "Nicholas", "Sarah", "David",
				"Alyssa", "Alexander", "Grace", "Ryan", "Sophia", "Tyler",
				"Taylor", "James", "Brianna", "John", "Lauren", "Jonathan",
				"Ava", "Brandon", "Kayla", "Christian", "Jessica", "Dylan",
				"Natalie", "Zachary", "Chloe", "Noah", "Anna", "Samuel",
				"Victoria", "Benjamin", "Hailey", "Nathan", "Mia", "Logan",
				"Sydney", "Justin", "Jasmine", "Jose", "Morgan", "Gabriel",
				"Julia", "Austin", "Destiny", "Kevin", "Rachel", "Caleb",
				"Megan", "Robert", "Kaitlyn", "Elijah", "Katherine", "Thomas",
				"Jennifer", "Jordan", "Savannah", "Cameron", "Ella", "Hunter",
				"Alexandra", "Jack", "Haley", "Angel", "Allison", "Isaiah",
				"Maria", "Jackson", "Nicole", "Evan", "Mackenzie", "Luke",
				"Brooke", "Jason", "Makayla", "Isaac", "Kaylee", "Mason",
				"Lily", "Aaron", "Stephanie", "Connor", "Andrea", "Gavin",
				"Faith", "Kyle", "Amanda", "Jayden", "Katelyn", "Aidan",
				"Kimberly", "Juan", "Madeline", "Luis", "Gabrielle", "Charles",
				"Zoe", "Aiden", "Trinity", "Adam", "Alexa", "Brian", "Mary",
				"Eric", "Jenna", "Lucas", "Lillian", "Sean", "Paige",
				"Nathaniel", "Kylie", "Alex", "Gabriella", "Adrian", "Rebecca",
				"Carlos", "Jordan", "Bryan", "Sara", "Ian", "Addison", "Jesus",
				"Michelle", "Owen", "Riley", "Julian", "Vanessa", "Cole",
				"Angelina", "Landon", "Leah", "Diego", "Caroline", "Steven",
				"Sofia", "Chase", "Audrey", "Timothy", "Maya", "Jeremiah",
				"Avery", "Sebastian", "Evelyn", "Xavier", "Autumn", "Devin",
				"Amber", "Cody", "Ariana", "Seth", "Jocelyn", "Hayden",
				"Claire", "Blake", "Jada", "Richard", "Danielle", "Carter",
				"Bailey", "Wyatt", "Isabel", "Dominic", "Arianna", "Antonio",
				"Sierra", "Jaden", "Mariah", "Miguel", "Aaliyah", "Brayden",
				"Melanie", "Patrick", "Erin", "Alejandro", "Nevaeh", "Carson",
				"Brooklyn", "Jesse", "Marissa", "Pawel");

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
		return true;
	}

	@Override
	public byte[] getAttachment(int noteId) {
		return null;
	}

}
