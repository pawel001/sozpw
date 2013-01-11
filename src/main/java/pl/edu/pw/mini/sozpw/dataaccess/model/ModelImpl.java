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

//TODO bloki finally i try catche przy każdej transkacji
public class ModelImpl implements Model {

	@Override
	public User loginUser(String username, String pass) {
		try {
			System.out.println("loginUser: " + username);
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
				session.close();
				return ret;
			}
			// TODO zmiana last login date u usera
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		return null;
	}

	@Override
	public String registerUser(String username, String pass, String mail) {
		System.out.println("RegisterUser: " + username + " mail: " + mail);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			// najpierw sprawdzam czy user istnieje, jak nie to go tworzę
			Query query = session
					.createQuery("from User where username = :username");
			query.setParameter("username", username);

			@SuppressWarnings("rawtypes")
			List result = query.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result
						.get(0);
				if (user != null) {
					System.out.println("Podany username jest zajęty");
					return null;
				}
			} catch (Exception e) {
				System.out.println("Username wolny");
			}

			pl.edu.pw.mini.sozpw.dataaccess.models.User newUser = new pl.edu.pw.mini.sozpw.dataaccess.models.User();
			java.util.Date date = new java.util.Date();
			newUser.setCreateDate(new Timestamp(date.getTime()));
			newUser.setEmail(mail);
			newUser.setIsActive(false);
			newUser.setPassword(pass);
			newUser.setPhone("");
			newUser.setLastLoginDate(new Timestamp(date.getTime()));

			// TODO SALT UŻYWANY JAKO REGISTER KEY
			newUser.setUsername(username);
			newUser.setLastLoginDate(new Timestamp(date.getTime()));
			String key = RandomStringGenerator.randomString(30);
			newUser.setSalt(key);
			session.save(newUser);
			session.getTransaction().commit();
			return key;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "a";
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean confirmRegistration(String username, String key) {
		// TODO @PAWEL: tu nie zmieniam bo ja potrzebuje powiazania tego klucza
		// z
		// userem
		System.out.println("ConfirmRegister: " + username + " key: " + key);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			// wyszukuje usera powiazanego z notatka
			Query query = session.createQuery("from User where salt = :salt");
			query.setParameter("salt", key);

			@SuppressWarnings("rawtypes")
			List result = query.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result
						.get(0);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
			user.setIsActive(true);
			user.setSalt("");
			session.save(user);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Note> getNotes(String username) {
		List<Note> noteResults = new ArrayList<Note>();
		System.out.println("Get notes for username" + username);
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
			System.out
					.println("ERROR przy pobieraniu Usera dla ktorego maja byc notatki: "
							+ username);
			System.out.println(e.getMessage());
			session.close();
			return new ArrayList<Note>();
		}

		List<pl.edu.pw.mini.sozpw.dataaccess.models.Note> results1;
		List<pl.edu.pw.mini.sozpw.dataaccess.models.Note> results2;
		List<pl.edu.pw.mini.sozpw.dataaccess.models.Note> results3;
		try {
			// moje
			results1 = (List) session.createQuery(
					"from Note where user_id = " + user.getIdUsers()).list();
			// skierowane do mnie ale nie moje
			results2 = (List) session.createQuery(
					"from Note where addressedUser_id = " + user.getIdUsers()
							+ " AND user_id <>" + user.getIdUsers()).list();
			// publiczne nie moje nie skierowane do mnie
			results3 = (List) session.createQuery(
					"from Note where user_id <> " + user.getIdUsers() + " AND "
							+ "addressedUser_id <> " + user.getIdUsers()
							+ " AND " + "isPrivate = false").list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.close();
			return new ArrayList<Note>();
		}
		System.out.println("Lista1 jeden ma " + results1.size() + " elementów");
		System.out.println("Lista2 jeden ma " + results2.size() + " elementów");
		System.out.println("Lista3 jeden ma " + results3.size() + " elementów");

		// LISTA MOICH
		for (pl.edu.pw.mini.sozpw.dataaccess.models.Note n : results1) {

			// UZUPELNIAM STRINGA Z USERNAMEM
			pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsername;
			try {
				userForUsername = (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
						.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
								n.getUser_id());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
			if (userForUsername == null)
				continue;

			// UZUPELNIAM KOMENTARZE
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Comment> commentsDb = (List) session
					.createQuery(
							"from Comment where note_id = " + n.getNoteId())
					.list();
			ArrayList<Comment> commentsToAdd = new ArrayList<Comment>();
			for (pl.edu.pw.mini.sozpw.dataaccess.models.Comment c : commentsDb) {
				// UZUPELNIAM USERNAME DLA KOMENTARZA
				pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsernameComment;
				try {
					userForUsernameComment = (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
							.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
									n.getUser_id());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}
				if (userForUsernameComment == null)
					continue;
				Comment commentToAdd = ModelToViewModelConverter
						.toViewModelComment(c,
								userForUsernameComment.getUsername());
				commentsToAdd.add(commentToAdd);
			}
			// UZUPELNIAM INFORMACJE O ADRESOWANYM USERZE
			ArrayList<String> dedicationList = new ArrayList<String>();
			pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsernameAddressed;
			try {
				userForUsernameAddressed = (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
						.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
								n.getAddressedUser_id());
				dedicationList.add(userForUsernameAddressed.getUsername());
			}
			// NIC NIE ROBIE BO TO MOŻE NIE BYĆ ADRESOWANE
			catch (Exception e) {
				System.out.println(e.getMessage());
			}

			// UZUPEŁNIAM ZAŁACZNIK:
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Attachment> attachmentsDb = (List) session
					.createQuery(
							"from Attachment where note_id = " + n.getNoteId())
					.list();
			String filename = "";
			try {
				pl.edu.pw.mini.sozpw.dataaccess.models.Attachment attachment = attachmentsDb
						.get(0);
				filename = attachment.getFilename();
			} catch (Exception e) {
				;
			}
			Note noteViewModel = ModelToViewModelConverter.toViewModelNote(n,
					userForUsername.getUsername(), dedicationList,
					commentsToAdd, filename);
			noteResults.add(noteViewModel);
		}

		// LISTA ADRESOWANYCH DO MNIE
		for (pl.edu.pw.mini.sozpw.dataaccess.models.Note n : results2) {
			// UZUPELNIAM STRINGA Z USERNAMEM
			pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsername;
			try {
				userForUsername = (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
						.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
								n.getUser_id());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
			if (userForUsername == null)
				continue;

			// UZUPELNIAM KOMENTARZE
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Comment> commentsDb = (List) session
					.createQuery(
							"from Comment where note_id = " + n.getNoteId())
					.list();
			ArrayList<Comment> commentsToAdd = new ArrayList<Comment>();
			for (pl.edu.pw.mini.sozpw.dataaccess.models.Comment c : commentsDb) {
				// UZUPELNIAM USERNAME DLA KOMENTARZA
				pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsernameComment;
				try {
					userForUsernameComment = (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
							.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
									n.getUser_id());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}
				if (userForUsernameComment == null)
					continue;
				Comment commentToAdd = ModelToViewModelConverter
						.toViewModelComment(c,
								userForUsernameComment.getUsername());
				commentsToAdd.add(commentToAdd);
			}
			// UZUPELNIAM INFORMACJE O ADRESOWANYM USERZE
			ArrayList<String> dedicationList = new ArrayList<String>();
			pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsernameAddressed;
			try {
				userForUsernameAddressed = (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
						.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
								n.getAddressedUser_id());
				dedicationList.add(userForUsernameAddressed.getUsername());
			}
			// NIC NIE ROBIE BO TO MOŻE NIE BYĆ ADRESOWANE
			catch (Exception e) {
				System.out.println(e.getMessage());
			}

			// UZUPEŁNIAM ZAŁACZNIK:
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Attachment> attachmentsDb = (List) session
					.createQuery(
							"from Attachment where note_id = " + n.getNoteId())
					.list();
			String filename = "";
			try {
				pl.edu.pw.mini.sozpw.dataaccess.models.Attachment attachment = attachmentsDb
						.get(0);
				filename = attachment.getFilename();
			} catch (Exception e) {
				;
			}
			Note noteViewModel = ModelToViewModelConverter.toViewModelNote(n,
					userForUsername.getUsername(), dedicationList,
					commentsToAdd, filename);
			noteResults.add(noteViewModel);
		}

		// LISTA PUBLICZNYCH
		for (pl.edu.pw.mini.sozpw.dataaccess.models.Note n : results3) {
			// UZUPELNIAM STRINGA Z USERNAMEM
			pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsername;
			try {
				userForUsername = (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
						.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
								n.getUser_id());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
			if (userForUsername == null)
				continue;

			// UZUPELNIAM KOMENTARZE
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Comment> commentsDb = (List) session
					.createQuery(
							"from Comment where note_id = " + n.getNoteId())
					.list();
			ArrayList<Comment> commentsToAdd = new ArrayList<Comment>();
			for (pl.edu.pw.mini.sozpw.dataaccess.models.Comment c : commentsDb) {
				// UZUPELNIAM USERNAME DLA KOMENTARZA
				pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsernameComment;
				try {
					userForUsernameComment = (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
							.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
									n.getUser_id());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}
				if (userForUsernameComment == null)
					continue;
				Comment commentToAdd = ModelToViewModelConverter
						.toViewModelComment(c,
								userForUsernameComment.getUsername());
				commentsToAdd.add(commentToAdd);
			}
			// UZUPELNIAM INFORMACJE O ADRESOWANYM USERZE
			ArrayList<String> dedicationList = new ArrayList<String>();
			pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsernameAddressed;
			try {
				userForUsernameAddressed = (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
						.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
								n.getAddressedUser_id());
				dedicationList.add(userForUsernameAddressed.getUsername());
			}
			// NIC NIE ROBIE BO TO MOŻE NIE BYĆ ADRESOWANE
			catch (Exception e) {
				System.out.println(e.getMessage());
			}

			// UZUPEŁNIAM ZAŁACZNIK:
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Attachment> attachmentsDb = (List) session
					.createQuery(
							"from Attachment where note_id = " + n.getNoteId())
					.list();
			String filename = "";
			try {
				pl.edu.pw.mini.sozpw.dataaccess.models.Attachment attachment = attachmentsDb
						.get(0);
				filename = attachment.getFilename();
			} catch (Exception e) {
				;
			}
			Note noteViewModel = ModelToViewModelConverter.toViewModelNote(n,
					userForUsername.getUsername(), dedicationList,
					commentsToAdd, filename);
			noteResults.add(noteViewModel);
		}
		System.out.println("Lista koncowa ma " + noteResults.size()
				+ " elementów");
		session.close();
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
				session.close();
				System.out.println(e.getMessage());
				return -1;
			}
			// TODO POPRAWIĆ kod do wyciągania z dedication list
			// danych
			int addressedUserId = -1;
			if (note.getDedicationList().size() > 0) {
				Query queryForAddressedUser = session
						.createQuery("from User where username = :username");
				queryForAddressedUser.setParameter("username", note
						.getDedicationList().get(0));

				@SuppressWarnings("rawtypes")
				List resultAddressedUser = queryForAddressedUser.list();
				pl.edu.pw.mini.sozpw.dataaccess.models.User userAddressed;
				try {
					userAddressed = (pl.edu.pw.mini.sozpw.dataaccess.models.User) resultAddressedUser
							.get(0);
					addressedUserId = userAddressed.getIdUsers();
				} catch (Exception e) {
					;
				}
			}
			pl.edu.pw.mini.sozpw.dataaccess.models.Note noteModel = ModelToViewModelConverter
					.toDbNote(note, addressedUserId, false, -1, 1,
							user.getIdUsers());
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
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			session.getTransaction().commit();
			session.close();
			return noteModel.getNoteId();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return -1;
	}

	@Override
	public boolean editNote(Note note, byte[] attachment) {
		System.out.println("BEGIN UPDATE NOTE ID: " + note.getId());
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
			session.close();
			return false;
		}

		// TODO NARAZIE ZROBIONE POPRZEZ USUWANIE I DODAWANIE POTEM ZMIENIĆ
		// Note model = (Note) session.get(Note.class, note.getId());
		/*
		 * deleteNote(note.getId()); // KONIEC USUWANIA
		 * 
		 * // TODO pożądne dodawanie nowej notatki!!
		 * pl.edu.pw.mini.sozpw.dataaccess.models.Note noteModel =
		 * ModelToViewModelConverter .toDbNote(note, -1, false, -1, 1,
		 * user.getIdUsers());
		 * List<pl.edu.pw.mini.sozpw.dataaccess.models.Point> backup = noteModel
		 * .getPoints(); noteModel.setPoints(null); session.save(noteModel); for
		 * (pl.edu.pw.mini.sozpw.dataaccess.models.Point p : backup) {
		 * p.setNote(noteModel); session.save(p); } if (attachment != null) {
		 * pl.edu.pw.mini.sozpw.dataaccess.models.Attachment attachmentModel =
		 * new pl.edu.pw.mini.sozpw.dataaccess.models.Attachment();
		 * attachmentModel.setFile(attachment);
		 * attachmentModel.setFilename(note.getFilename());
		 * attachmentModel.setFileSize(attachment.length);
		 * attachmentModel.setFileType("");
		 * attachmentModel.setNote_id(noteModel.getNoteId());
		 * session.save(attachmentModel); } session.getTransaction().commit();
		 * session.close();
		 */
		
		// TODO POPRAWIĆ kod do wyciągania z dedication list
		// danych
		
		int addressedUserId = -1;
		if (note.getDedicationList().size() > 0) {
			Query queryForAddressedUser = session
					.createQuery("from User where username = :username");
			queryForAddressedUser.setParameter("username", note
					.getDedicationList().get(0));

			@SuppressWarnings("rawtypes")
			List resultAddressedUser = queryForAddressedUser.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User userAddressed;
			try {
				userAddressed = (pl.edu.pw.mini.sozpw.dataaccess.models.User) resultAddressedUser
						.get(0);
				addressedUserId = userAddressed.getIdUsers();
			} catch (Exception e) {
				;
			}
		}
		pl.edu.pw.mini.sozpw.dataaccess.models.Note noteModel = ModelToViewModelConverter
				.toDbNote(note, addressedUserId, false, -1, 1,
						user.getIdUsers());
		List<pl.edu.pw.mini.sozpw.dataaccess.models.Point> backup = noteModel
				.getPoints();
		noteModel.setPoints(null);
		noteModel.setNoteId(note.getId());
		
		pl.edu.pw.mini.sozpw.dataaccess.models.Note modelDB = (pl.edu.pw.mini.sozpw.dataaccess.models.Note) session
				.get(pl.edu.pw.mini.sozpw.dataaccess.models.Note.class,
						note.getId());
		
		//przepisywanie z jednego do drugiego.
		modelDB.setAddressedUser_id(noteModel.getAddressedUser_id());
		modelDB.setCathegory_id(noteModel.getCathegory_id());
		//modelDB.setCreateDate(noteModel.getCreateDate());
		modelDB.setExpirationDate(noteModel.getExpirationDate());
		modelDB.setGroup_id(noteModel.getGroup_id());
		modelDB.setIsAddressedToGroup(noteModel.getIsAddressedToGroup());
		modelDB.setIsPrivate(noteModel.getIsPrivate());
		modelDB.setPoints(noteModel.getPoints());
		modelDB.setText(noteModel.getText());
		modelDB.setTopic(noteModel.getTopic());
		//modelDB.setUser_id()
		session.save(modelDB);
		//kasuję stare punkty
		String hqlDeleteP = "delete Point where note_id = :note_id";
		int deletedEntitiesP = session.createQuery( hqlDeleteP )
		        .setInteger( "note_id", note.getId() )
		        .executeUpdate();
		System.out.println("Usunięto: " + deletedEntitiesP + " punktów");
		
		//
		for (pl.edu.pw.mini.sozpw.dataaccess.models.Point p : backup) {
			p.setNote(noteModel);
			session.save(p);
		}
		System.out.println("Updateuję notatkę o id: " + note.getId());
		
		//być może dodaję nowe:
		if (attachment != null) {
			try {
				//kasuję stare załączniki:
				String hqlDelete = "delete Attachment where note_id = :note_id";
				int deletedEntities = session.createQuery( hqlDelete )
				        .setInteger( "note_id", note.getId() )
				        .executeUpdate();
				System.out.println("Usunięto: " + deletedEntities + " załaczników");
				pl.edu.pw.mini.sozpw.dataaccess.models.Attachment attachmentModel = new pl.edu.pw.mini.sozpw.dataaccess.models.Attachment();
				attachmentModel.setFile(attachment);
				attachmentModel.setFilename(note.getFilename());
				attachmentModel.setFileSize(attachment.length);
				attachmentModel.setFileType("");
				attachmentModel.setNote_id(modelDB.getNoteId());
				session.save(attachmentModel);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		session.getTransaction().commit();
		return true;
	}

	@Override
	public boolean deleteNote(Integer noteId) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			pl.edu.pw.mini.sozpw.dataaccess.models.Note model = (pl.edu.pw.mini.sozpw.dataaccess.models.Note) session
					.get(pl.edu.pw.mini.sozpw.dataaccess.models.Note.class,
							noteId);
			session.delete(model);
			session.getTransaction().commit();
			session.close();
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
			session.close();
			return false;
		}
		pl.edu.pw.mini.sozpw.dataaccess.models.Comment commentModel = ModelToViewModelConverter
				.toDbComment(comment, user.getIdUsers(), noteId);
		session.save(commentModel);
		session.getTransaction().commit();
		session.close();
		return true;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<String> getUsersHints(String query, int count) {
		System.out.println("Get users hints");
		List<String> res = new ArrayList<String>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query queryForUsers = session
				.createQuery("from User where username like :username");
		queryForUsers.setParameter("username", query + '%');

		List<pl.edu.pw.mini.sozpw.dataaccess.models.User> result = queryForUsers
				.list();
		System.out.println("Get dedication hints liczba trafien: "
				+ result.size());
		for (pl.edu.pw.mini.sozpw.dataaccess.models.User u : result) {
			res.add(u.getUsername());
			if (res.size() == count) {
				break;
			}
		}
		session.close();
		return res;
	}

	@Override
	public List<String> getSubscribedGroups(String user) {
		if (user.equals("Pawel")) {
			return new ArrayList<String>(Arrays.asList("GrupaW1"));
		}
		return null;
	}

	@Override
	public boolean createGroup(String groupName, String username) {
		return true;
	}

	@Override
	public void removeGroup(String gropuName) {
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
	public boolean changePassword(String username, String oldPass,
			String newPass) {
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public byte[] getAttachment(int noteId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<pl.edu.pw.mini.sozpw.dataaccess.models.Attachment> results = (List) session
				.createQuery("from Attachment where note_id = " + noteId)
				.list();
		pl.edu.pw.mini.sozpw.dataaccess.models.Attachment attachment;
		try {
			attachment = (pl.edu.pw.mini.sozpw.dataaccess.models.Attachment) results
					.get(0);
		} catch (Exception e) {
			System.out
					.println("ERROR przy pobieraniu załącznika dla notatki o Id: "
							+ noteId);
			System.out.println(e.getMessage());
			session.close();
			return null;
		}
		session.close();
		return attachment.getFile();
	}

	@Override
	public List<String> getCreatedGroups(String username) {
		return new ArrayList<String>( Arrays.asList("GrupaW1", "Warszawa", "Riviera", "MiNI",
				"Politechnika"));
	}

	@Override
	public boolean addUser(String groupName, String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUser(String groupName, String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getUsersAndGroupsHints(String query, int count) {
		//List<String> result = getUsersHints(query, count);
		//result.addAll(getGroupsHints(query, count));
		//return result.subList(0, 5);
		return getUsersHints(query, count);
	}

	@Override
	public List<String> getSubscribingUsers(String groupName) {
		return new ArrayList<String>( Arrays.asList("Brooklyn", "Jesse", "Marissa", "Pawel"));
	}

	@Override
	public boolean getGroupVisibility(String groupName) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setGroupVisibility(String groupName, boolean isPrivate) {
		// TODO Auto-generated method stub

	}
}
