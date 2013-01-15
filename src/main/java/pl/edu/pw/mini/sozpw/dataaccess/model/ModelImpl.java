package pl.edu.pw.mini.sozpw.dataaccess.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import pl.edu.pw.mini.sozpw.dataaccess.models.GroupXUser;
import pl.edu.pw.mini.sozpw.dataaccess.persistence.HibernateUtil;
import pl.edu.pw.mini.sozpw.dataaccess.services.ModelToViewModelConverter;
import pl.edu.pw.mini.sozpw.dataaccess.services.RandomStringGenerator;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Comment;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Note;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.User;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ModelImpl implements Model {

	@Override
	public User loginUser(String username, String pass) {
		System.out.println("loginUser: " + username);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session
					.createQuery("from User where username = :username");
			query.setParameter("username", username);

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
			return null;
			// TODO zmiana last login date u usera
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			if (session != null)
				session.close();
		}

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
					.createQuery("from User where username=:username");
			query.setParameter("username", username);

			List result = query.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result
						.get(0);
				if (user != null) {
					System.out
							.println("Podany username jest zajęty przez innego użytkownika");
					return null;
				}
			} catch (Exception e) {
				System.out
						.println("Username wolny (nie ma innego użytkownika o podanej nazwie)");
			}
			// sprawdzam czy istnieje grupa o tej samej nazwie:
			Query queryGroupTaken = session
					.createQuery("from Group where name=:name");
			queryGroupTaken.setString("name", username);

			List resultGroupTaken = queryGroupTaken.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.Group groupTaken;
			try {
				groupTaken = (pl.edu.pw.mini.sozpw.dataaccess.models.Group) resultGroupTaken
						.get(0);
				if (groupTaken != null) {
					System.out
							.println("Podany username jest zajęty przez grupę");
					return null;
				}
			} catch (Exception e) {
				System.out
						.println("Username wolny (nie ma grupy o podanej nazwie)");
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
		System.out.println("ConfirmRegister: " + username + " key: " + key);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			// wyszukuje usera powiazanego z notatka
			Query query = session
					.createQuery("from User where username = :username");
			query.setParameter("username", username);

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

	@Override
	public List<Note> getNotes(String username) {
		List<Note> noteResults = new ArrayList<Note>();
		System.out.println("Get notes for username" + username);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			// wyszukuje usera powiazanego z komentarzem
			Query query = session
					.createQuery("from User where username = :username");
			query.setParameter("username", username);

			List result = query.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result
						.get(0);
			} catch (Exception e) {
				System.out
						.println("ERROR przy pobieraniu Usera dla ktorego maja byc notatki: "
								+ username);
				System.out.println(e.getMessage());
				return new ArrayList<Note>();
			}

			Date date = new Date();
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Note> results1;
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Note> results2;
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Note> results3;

			Query queryMine = session
					.createQuery("from Note where user_id = "
							+ user.getIdUsers()
							+ " AND (expirationDate = null OR expirationDate>:expirationDate)");
			queryMine.setDate("expirationDate", date);

			Query queryToMe = session
					.createQuery("from Note where addressedUser_id = "
							+ user.getIdUsers()
							+ " AND user_id <>"
							+ user.getIdUsers()
							+ " AND (expirationDate = null OR expirationDate>:expirationDate)");
			queryToMe.setDate("expirationDate", date);

			Query queryPublic = session
					.createQuery("from Note where user_id <> "
							+ user.getIdUsers()
							+ " AND "
							+ "addressedUser_id <> "
							+ user.getIdUsers()
							+ " AND "
							+ "isPrivate = false"
							+ " AND (expirationDate = null OR expirationDate>:expirationDate)");
			queryPublic.setDate("expirationDate", date);
			// moje
			results1 = (List) queryMine.list();
			// skierowane do mnie ale nie moje
			results2 = (List) queryToMe.list();
			// publiczne nie moje nie skierowane do mnie
			results3 = (List) queryPublic.list();

			System.out.println("Lista1 jeden ma " + results1.size()
					+ " elementów");
			System.out.println("Lista2 jeden ma " + results2.size()
					+ " elementów");
			System.out.println("Lista3 jeden ma " + results3.size()
					+ " elementów");

			List<pl.edu.pw.mini.sozpw.dataaccess.models.Note> resultsFinal = results1;
			resultsFinal.addAll(results2);
			resultsFinal.addAll(results3);

			// pobieranie notatek adresowanych do grup w których jest użytkownik
			List<pl.edu.pw.mini.sozpw.dataaccess.models.GroupXUser> resultsForGXU;
			resultsForGXU = (List) session.createQuery(
					"from GroupXUser where userId = " + user.getIdUsers())
					.list();
			for (pl.edu.pw.mini.sozpw.dataaccess.models.GroupXUser gxu : resultsForGXU) {
				Query queryTemp = session
						.createQuery("from Note where group_id = "
								+ gxu.getGroupId()
								+ " AND user_id <> "
								+ user.getIdUsers()
								+ " AND "
								+ "addressedUser_id <> "
								+ user.getIdUsers()
								+ " AND "
								+ "isPrivate = true"
								+ " AND (expirationDate = null OR expirationDate>:expirationDate)");
				
				queryTemp.setDate("expirationDate", date);
				List<pl.edu.pw.mini.sozpw.dataaccess.models.Note> resultsFromGXU = (List) queryTemp
						.list();
				System.out.println("Dla grupy o id: " + gxu.getGroupId()
						+ " dodano: " + resultsFromGXU.size() + " notatek");
				resultsFinal.addAll(resultsFromGXU);
			}

			// LISTA MOICH
			for (pl.edu.pw.mini.sozpw.dataaccess.models.Note n : resultsFinal) {

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
					// WLASCIWEGO TYPA
					try {
						userForUsernameComment = (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
								.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
										c.getUser_id());
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
				// UZUPELNIANIE INFORMACJI ADRESOWYCH

				ArrayList<String> dedicationList = new ArrayList<String>();
				// TODO dodać tu wyciąganie do adresowanej grupy
				// UZUPELNIAM INFORMACJE O ADRESOWANEJ GRUPIE
				if (n.getIsAddressedToGroup()) {
					pl.edu.pw.mini.sozpw.dataaccess.models.Group groupAddressed;
					try {
						groupAddressed = (pl.edu.pw.mini.sozpw.dataaccess.models.Group) session
								.get(pl.edu.pw.mini.sozpw.dataaccess.models.Group.class,
										n.getGroup_id());
						dedicationList.add(groupAddressed.getName());
					}
					// NIC NIE ROBIE BO TO MOŻE NIE BYĆ ADRESOWANE przez jakiś
					// błąd!
					catch (Exception e) {
						System.out.println(e.getMessage());
					}

				}
				// UZUPELNIAM INFORMACJE O ADRESOWANYM USERZE
				else {
					pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsernameAddressed;
					try {
						userForUsernameAddressed = (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
								.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
										n.getAddressedUser_id());
						dedicationList.add(userForUsernameAddressed
								.getUsername());
					}
					// NIC NIE ROBIE BO TO MOŻE NIE BYĆ ADRESOWANE
					catch (Exception e) {
						System.out.println(e.getMessage());
					}

				}

				// UZUPEŁNIAM ZAŁACZNIK:
				List<pl.edu.pw.mini.sozpw.dataaccess.models.Attachment> attachmentsDb = (List) session
						.createQuery(
								"from Attachment where note_id = "
										+ n.getNoteId()).list();
				String filename = "";
				try {
					pl.edu.pw.mini.sozpw.dataaccess.models.Attachment attachment = attachmentsDb
							.get(0);
					filename = attachment.getFilename();
				} catch (Exception e) {
					;
				}
				Note noteViewModel = ModelToViewModelConverter.toViewModelNote(
						n, userForUsername.getUsername(), dedicationList,
						commentsToAdd, filename);
				noteResults.add(noteViewModel);
			}

			// LISTA ADRESOWANYCH DO MNIE
			/*
			 * for (pl.edu.pw.mini.sozpw.dataaccess.models.Note n : results2) {
			 * // UZUPELNIAM STRINGA Z USERNAMEM
			 * pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsername; try
			 * { userForUsername = (pl.edu.pw.mini.sozpw.dataaccess.models.User)
			 * session .get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
			 * n.getUser_id()); } catch (Exception e) {
			 * System.out.println(e.getMessage()); continue; } if
			 * (userForUsername == null) continue;
			 * 
			 * // UZUPELNIAM KOMENTARZE
			 * List<pl.edu.pw.mini.sozpw.dataaccess.models.Comment> commentsDb =
			 * (List) session .createQuery( "from Comment where note_id = " +
			 * n.getNoteId()) .list(); ArrayList<Comment> commentsToAdd = new
			 * ArrayList<Comment>(); for
			 * (pl.edu.pw.mini.sozpw.dataaccess.models.Comment c : commentsDb) {
			 * // UZUPELNIAM USERNAME DLA KOMENTARZA
			 * pl.edu.pw.mini.sozpw.dataaccess.models.User
			 * userForUsernameComment; try { userForUsernameComment =
			 * (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
			 * .get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
			 * n.getUser_id()); } catch (Exception e) {
			 * System.out.println(e.getMessage()); continue; } if
			 * (userForUsernameComment == null) continue; Comment commentToAdd =
			 * ModelToViewModelConverter .toViewModelComment(c,
			 * userForUsernameComment.getUsername());
			 * commentsToAdd.add(commentToAdd); } // UZUPELNIAM INFORMACJE O
			 * ADRESOWANYM USERZE ArrayList<String> dedicationList = new
			 * ArrayList<String>(); pl.edu.pw.mini.sozpw.dataaccess.models.User
			 * userForUsernameAddressed; try { userForUsernameAddressed =
			 * (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
			 * .get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
			 * n.getAddressedUser_id());
			 * dedicationList.add(userForUsernameAddressed.getUsername()); } //
			 * NIC NIE ROBIE BO TO MOŻE NIE BYĆ ADRESOWANE catch (Exception e) {
			 * System.out.println(e.getMessage()); }
			 * 
			 * // UZUPEŁNIAM ZAŁACZNIK:
			 * List<pl.edu.pw.mini.sozpw.dataaccess.models.Attachment>
			 * attachmentsDb = (List) session .createQuery(
			 * "from Attachment where note_id = " + n.getNoteId()).list();
			 * String filename = ""; try {
			 * pl.edu.pw.mini.sozpw.dataaccess.models.Attachment attachment =
			 * attachmentsDb .get(0); filename = attachment.getFilename(); }
			 * catch (Exception e) { ; } Note noteViewModel =
			 * ModelToViewModelConverter.toViewModelNote( n,
			 * userForUsername.getUsername(), dedicationList, commentsToAdd,
			 * filename); noteResults.add(noteViewModel); }
			 * 
			 * // LISTA PUBLICZNYCH for
			 * (pl.edu.pw.mini.sozpw.dataaccess.models.Note n : results3) { //
			 * UZUPELNIAM STRINGA Z USERNAMEM
			 * pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsername; try
			 * { userForUsername = (pl.edu.pw.mini.sozpw.dataaccess.models.User)
			 * session .get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
			 * n.getUser_id()); } catch (Exception e) {
			 * System.out.println(e.getMessage()); continue; } if
			 * (userForUsername == null) continue;
			 * 
			 * // UZUPELNIAM KOMENTARZE
			 * List<pl.edu.pw.mini.sozpw.dataaccess.models.Comment> commentsDb =
			 * (List) session .createQuery( "from Comment where note_id = " +
			 * n.getNoteId()) .list(); ArrayList<Comment> commentsToAdd = new
			 * ArrayList<Comment>(); for
			 * (pl.edu.pw.mini.sozpw.dataaccess.models.Comment c : commentsDb) {
			 * // UZUPELNIAM USERNAME DLA KOMENTARZA
			 * pl.edu.pw.mini.sozpw.dataaccess.models.User
			 * userForUsernameComment; try { userForUsernameComment =
			 * (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
			 * .get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
			 * n.getUser_id()); } catch (Exception e) {
			 * System.out.println(e.getMessage()); continue; } if
			 * (userForUsernameComment == null) continue; Comment commentToAdd =
			 * ModelToViewModelConverter .toViewModelComment(c,
			 * userForUsernameComment.getUsername());
			 * commentsToAdd.add(commentToAdd); } // UZUPELNIAM INFORMACJE O
			 * ADRESOWANYM USERZE ArrayList<String> dedicationList = new
			 * ArrayList<String>(); pl.edu.pw.mini.sozpw.dataaccess.models.User
			 * userForUsernameAddressed; try { userForUsernameAddressed =
			 * (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
			 * .get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
			 * n.getAddressedUser_id());
			 * dedicationList.add(userForUsernameAddressed.getUsername()); } //
			 * NIC NIE ROBIE BO TO MOŻE NIE BYĆ ADRESOWANE catch (Exception e) {
			 * System.out.println(e.getMessage()); }
			 * 
			 * // UZUPEŁNIAM ZAŁACZNIK:
			 * List<pl.edu.pw.mini.sozpw.dataaccess.models.Attachment>
			 * attachmentsDb = (List) session .createQuery(
			 * "from Attachment where note_id = " + n.getNoteId()).list();
			 * String filename = ""; try {
			 * pl.edu.pw.mini.sozpw.dataaccess.models.Attachment attachment =
			 * attachmentsDb .get(0); filename = attachment.getFilename(); }
			 * catch (Exception e) { ; } Note noteViewModel =
			 * ModelToViewModelConverter.toViewModelNote( n,
			 * userForUsername.getUsername(), dedicationList, commentsToAdd,
			 * filename); noteResults.add(noteViewModel); }
			 */
			System.out.println("Lista koncowa ma " + noteResults.size()
					+ " elementów");
			return noteResults;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return noteResults;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public Integer addNote(Note note, byte[] attachment) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			// wyszukuje usera powiazanego z notatka
			Query query = session
					.createQuery("from User where username = :username");
			query.setParameter("username", note.getUsername());

			List result = query.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result
						.get(0);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return -1;
			}
			// TODO POPRAWIĆ kod do wyciągania z dedication list
			// danych
			int addressedUserId = -1;
			int addressGroupId = -1;
			boolean isAddressedToGroup = false;
			boolean isPrivate = false;
			if (note.getDedicationList().size() > 0) {
				isPrivate = true;
				Query queryForAddressedUser = session
						.createQuery("from User where username = :username");
				queryForAddressedUser.setParameter("username", note
						.getDedicationList().get(0));

				List resultAddressedUser = queryForAddressedUser.list();
				pl.edu.pw.mini.sozpw.dataaccess.models.User userAddressed;
				try {
					userAddressed = (pl.edu.pw.mini.sozpw.dataaccess.models.User) resultAddressedUser
							.get(0);
					addressedUserId = userAddressed.getIdUsers();
				} catch (Exception e) {
					;
				}

				Query queryForAddressedGroup = session
						.createQuery("from Group where name = :name");
				queryForAddressedGroup.setParameter("name", note
						.getDedicationList().get(0));

				List resultAddressedGroup = queryForAddressedGroup.list();
				pl.edu.pw.mini.sozpw.dataaccess.models.Group groupAddressed;
				try {
					groupAddressed = (pl.edu.pw.mini.sozpw.dataaccess.models.Group) resultAddressedGroup
							.get(0);
					addressGroupId = groupAddressed.getGroupId();
					isAddressedToGroup = true;
				} catch (Exception e) {
					;
				}
			}

			pl.edu.pw.mini.sozpw.dataaccess.models.Note noteModel = ModelToViewModelConverter
					.toDbNote(note, addressedUserId, isAddressedToGroup,
							addressGroupId, user.getIdUsers(), isPrivate);
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
			return noteModel.getNoteId();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean editNote(Note note, byte[] attachment) {
		System.out.println("BEGIN UPDATE NOTE ID: " + note.getId());
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			// wyszukuje usera powiazanego z notatka
			Query query = session
					.createQuery("from User where username = :username");
			query.setParameter("username", note.getUsername());

			List result = query.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result
						.get(0);
			} catch (Exception e) {
				return false;
			}

			// TODO POPRAWIĆ kod do wyciągania z dedication list
			// danych
			int addressedUserId = -1;
			int addressGroupId = -1;
			boolean isAddressedToGroup = false;
			boolean isPrivate = false;
			if (note.getDedicationList().size() > 0) {
				isPrivate = true;
				Query queryForAddressedUser = session
						.createQuery("from User where username = :username");
				queryForAddressedUser.setParameter("username", note
						.getDedicationList().get(0));

				List resultAddressedUser = queryForAddressedUser.list();
				pl.edu.pw.mini.sozpw.dataaccess.models.User userAddressed;
				try {
					userAddressed = (pl.edu.pw.mini.sozpw.dataaccess.models.User) resultAddressedUser
							.get(0);
					addressedUserId = userAddressed.getIdUsers();
				} catch (Exception e) {
					;
				}

				Query queryForAddressedGroup = session
						.createQuery("from Group where name = :name");
				queryForAddressedGroup.setParameter("name", note
						.getDedicationList().get(0));

				List resultAddressedGroup = queryForAddressedGroup.list();
				pl.edu.pw.mini.sozpw.dataaccess.models.Group groupAddressed;
				try {
					groupAddressed = (pl.edu.pw.mini.sozpw.dataaccess.models.Group) resultAddressedGroup
							.get(0);
					addressGroupId = groupAddressed.getGroupId();
					isAddressedToGroup = true;
				} catch (Exception e) {
					;
				}
			}

			pl.edu.pw.mini.sozpw.dataaccess.models.Note noteModel = ModelToViewModelConverter
					.toDbNote(note, addressedUserId, isAddressedToGroup,
							addressGroupId, user.getIdUsers(), isPrivate);
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Point> backup = noteModel
					.getPoints();
			noteModel.setPoints(null);
			noteModel.setNoteId(note.getId());

			pl.edu.pw.mini.sozpw.dataaccess.models.Note modelDB = (pl.edu.pw.mini.sozpw.dataaccess.models.Note) session
					.get(pl.edu.pw.mini.sozpw.dataaccess.models.Note.class,
							note.getId());

			// przepisywanie z jednego do drugiego.
			modelDB.setAddressedUser_id(noteModel.getAddressedUser_id());
			modelDB.setCathegory_id(noteModel.getCathegory_id());
			// modelDB.setCreateDate(noteModel.getCreateDate());
			modelDB.setExpirationDate(noteModel.getExpirationDate());
			modelDB.setGroup_id(noteModel.getGroup_id());
			modelDB.setIsAddressedToGroup(noteModel.getIsAddressedToGroup());
			modelDB.setIsPrivate(noteModel.getIsPrivate());
			modelDB.setPoints(noteModel.getPoints());
			modelDB.setText(noteModel.getText());
			modelDB.setTopic(noteModel.getTopic());
			// modelDB.setUser_id()
			session.save(modelDB);
			// kasuję stare punkty
			String hqlDeleteP = "delete Point where note_id = :note_id";
			int deletedEntitiesP = session.createQuery(hqlDeleteP)
					.setInteger("note_id", note.getId()).executeUpdate();
			System.out.println("Usunięto: " + deletedEntitiesP + " punktów");

			//
			for (pl.edu.pw.mini.sozpw.dataaccess.models.Point p : backup) {
				p.setNote(noteModel);
				session.save(p);
			}
			System.out.println("Updateuję notatkę o id: " + note.getId());

			// być może dodaję nowe:
			if (attachment != null) {
				try {
					// kasuję stare załączniki:
					String hqlDelete = "delete Attachment where note_id = :note_id";
					int deletedEntities = session.createQuery(hqlDelete)
							.setInteger("note_id", note.getId())
							.executeUpdate();
					System.out.println("Usunięto: " + deletedEntities
							+ " załaczników");
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
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean deleteNote(Integer noteId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			pl.edu.pw.mini.sozpw.dataaccess.models.Note model = (pl.edu.pw.mini.sozpw.dataaccess.models.Note) session
					.get(pl.edu.pw.mini.sozpw.dataaccess.models.Note.class,
							noteId);
			session.delete(model);
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

	@Override
	public boolean addComment(int noteId, Comment comment) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			// wyszukuje usera powiazanego z komentarzem
			Query query = session
					.createQuery("from User where username = :username");
			query.setParameter("username", comment.getUsername());

			List result = query.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result
						.get(0);
			} catch (Exception e) {
				return false;
			}
			pl.edu.pw.mini.sozpw.dataaccess.models.Comment commentModel = ModelToViewModelConverter
					.toDbComment(comment, user.getIdUsers(), noteId);
			session.save(commentModel);
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

	@Override
	public List<String> getUsersHints(String query, int count) {
		System.out.println("Get users hints");
		List<String> res = new ArrayList<String>();
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
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

			return res;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return res;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public List<String> getSubscribedGroups(String username) {
		System.out.println("getSubscribedGroups: " + username);
		List<String> res = new ArrayList<String>();
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query1 = session
					.createQuery("from User where username=:username");
			query1.setParameter("username", username);
			List<pl.edu.pw.mini.sozpw.dataaccess.models.User> results = (List) query1
					.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user = null;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) results
						.get(0);
			} catch (Exception e) {
				System.out.println("ERROR: Podany użytkownik nie istnieje.");
				return res;
			}

			List<pl.edu.pw.mini.sozpw.dataaccess.models.GroupXUser> resultsGroupXUser = (List) session
					.createQuery(
							"from GroupXUser where userId = "
									+ user.getIdUsers()).list();
			for (pl.edu.pw.mini.sozpw.dataaccess.models.GroupXUser gxu : resultsGroupXUser) {
				pl.edu.pw.mini.sozpw.dataaccess.models.Group groupForName;
				try {
					groupForName = (pl.edu.pw.mini.sozpw.dataaccess.models.Group) session
							.get(pl.edu.pw.mini.sozpw.dataaccess.models.Group.class,
									gxu.getGroupId());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}
				res.add(groupForName.getName());
			}
			return res;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return res;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean createGroup(String username, String groupName) {
		// TODO dodać sprawdzanie czy nie ma innego użytkownika lub grupy o tej
		// samej nazwie!
		System.out.println("CreateGroup: " + groupName + " user: " + username);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			// najpierw sprawdzam czy user istnieje, jak nie to go tworzę
			Query query = session
					.createQuery("from User where username=:username");
			query.setParameter("username", groupName);

			List result = query.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User userTaken;
			try {
				userTaken = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result
						.get(0);
				if (userTaken != null) {
					System.out
							.println("Podany groupname jest zajęty przez innego użytkownika");
					return false;
				}
			} catch (Exception e) {
				System.out
						.println("groupname wolny (nie ma innego użytkownika o podanej nazwie)");
			}
			// sprawdzam czy istnieje grupa o tej samej nazwie:
			Query queryGroupTaken = session
					.createQuery("from Group where name=:name");
			queryGroupTaken.setString("name", groupName);

			List resultGroupTaken = queryGroupTaken.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.Group groupTaken;
			try {
				groupTaken = (pl.edu.pw.mini.sozpw.dataaccess.models.Group) resultGroupTaken
						.get(0);
				if (groupTaken != null) {
					System.out
							.println("Podany groupname jest zajęty przez grupę");
					return false;
				}
			} catch (Exception e) {
				System.out
						.println("groupname wolny (nie ma grupy o podanej nazwie)");
			}

			Query query1 = session
					.createQuery("from User where username=:username");
			query1.setParameter("username", username);
			List<pl.edu.pw.mini.sozpw.dataaccess.models.User> results = (List) query1
					.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user = null;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) results
						.get(0);
			} catch (Exception e) {
				System.out.println("ERROR: Podany użytkownik nie istnieje.");
			}

			pl.edu.pw.mini.sozpw.dataaccess.models.Group model = new pl.edu.pw.mini.sozpw.dataaccess.models.Group();
			model.setIsGeneric(false);
			model.setIsPrivate(true);
			model.setName(groupName);
			model.setOwnerId(user.getIdUsers());
			session.save(model);

			pl.edu.pw.mini.sozpw.dataaccess.models.GroupXUser modelGroupXUser = new GroupXUser();
			modelGroupXUser.setGroupId(model.getGroupId());
			modelGroupXUser.setUserId(user.getIdUsers());
			session.save(modelGroupXUser);

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

	@Override
	public void removeGroup(String groupName) {
		System.out.println("RemoveGroup: " + groupName);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query1 = session.createQuery("from Group where name=:name");
			query1.setParameter("name", groupName);
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Group> results = (List) query1
					.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.Group group = null;
			try {
				group = (pl.edu.pw.mini.sozpw.dataaccess.models.Group) results
						.get(0);
			} catch (Exception e) {
				System.out.println("ERROR: Podana grupa nie istnieje.");
				return;
			}
			session.delete(group);

			String hqlDeleteGXU = "delete GroupXUser where group_id = :group_id";
			int deletedEntitiesGXU = session.createQuery(hqlDeleteGXU)
					.setInteger("group_id", group.getGroupId()).executeUpdate();
			System.out.println("Usunięto: " + deletedEntitiesGXU
					+ " wpisów typu groupXUSER");
			session.getTransaction().commit();
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public List<String> getGroupsHints(String query, int count) {
		System.out.println("Get group hints");
		List<String> res = new ArrayList<String>();
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query queryForGroups = session
					.createQuery("from Group where name like :name AND isPrivate=false");
			queryForGroups.setParameter("name", query + '%');

			List<pl.edu.pw.mini.sozpw.dataaccess.models.Group> result = queryForGroups
					.list();
			System.out.println("Get dedication hints liczba trafien: "
					+ result.size());
			for (pl.edu.pw.mini.sozpw.dataaccess.models.Group g : result) {
				res.add(g.getName());
				if (res.size() == count) {
					break;
				}
			}

			return res;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return res;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean changePassword(String username, String oldPass,
			String newPass) {
		System.out.println("changePassword: " + username);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			// wyszukuje usera powiazanego z notatka
			Query query = session
					.createQuery("from User where username = :username");
			query.setParameter("username", username);

			List result = query.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) result
						.get(0);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
			if (!user.getPassword().equals(oldPass))
				return false;

			user.setPassword(newPass);
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

	@Override
	public byte[] getAttachment(int noteId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
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
				return null;
			}
			return attachment.getFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public List<String> getCreatedGroups(String username) {
		System.out.println("Get CreatedGroups dla usera: " + username);
		List<String> res = new ArrayList<String>();
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query1 = session
					.createQuery("from User where username=:username");
			query1.setParameter("username", username);
			List<pl.edu.pw.mini.sozpw.dataaccess.models.User> results = (List) query1
					.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user = null;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) results
						.get(0);
			} catch (Exception e) {
				System.out.println("ERROR: Podany użytkownik nie istnieje.");
			}

			Query queryForGroups = session
					.createQuery("from Group where ownerId = :ownerId");
			queryForGroups.setParameter("ownerId", user.getIdUsers());

			List<pl.edu.pw.mini.sozpw.dataaccess.models.Group> result = queryForGroups
					.list();
			System.out.println("Get CreatedGroups liczba trafien: "
					+ result.size());
			for (pl.edu.pw.mini.sozpw.dataaccess.models.Group g : result) {
				res.add(g.getName());
			}

			return res;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return res;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean addUser(String groupName, String username) {
		System.out.println("addUser: " + groupName + " dla usera: " + username);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query1 = session
					.createQuery("from User where username=:username);");
			query1.setParameter("username", username);

			List<pl.edu.pw.mini.sozpw.dataaccess.models.User> resultsUser = (List) query1
					.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user = null;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) resultsUser
						.get(0);
			} catch (Exception e) {
				System.out.println("ERROR: Podany użytkownik nie istnieje.");
			}

			Query query2 = session.createQuery("from Group where name=:name");
			query2.setParameter("name", groupName);
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Group> resultsGroup = (List) query2
					.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.Group group = null;
			try {
				group = (pl.edu.pw.mini.sozpw.dataaccess.models.Group) resultsGroup
						.get(0);
			} catch (Exception e) {
				System.out.println("ERROR: Podana grupa nie istnieje.");
			}

			pl.edu.pw.mini.sozpw.dataaccess.models.GroupXUser modelGroupXUser = new GroupXUser();
			modelGroupXUser.setGroupId(group.getGroupId());
			modelGroupXUser.setUserId(user.getIdUsers());
			session.save(modelGroupXUser);

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

	@Override
	public boolean removeUser(String groupName, String username) {
		System.out.println("removeUser: " + groupName + " dla usera: "
				+ username);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query1 = session
					.createQuery("from User where username=:username");
			query1.setParameter("username", username);
			List<pl.edu.pw.mini.sozpw.dataaccess.models.User> resultsUser = (List) query1
					.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.User user = null;
			try {
				user = (pl.edu.pw.mini.sozpw.dataaccess.models.User) resultsUser
						.get(0);
			} catch (Exception e) {
				System.out.println("ERROR: Podany użytkownik nie istnieje.");
			}

			Query query2 = session.createQuery("from Group where name=:name ");
			query2.setParameter("name", groupName);
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Group> resultsGroup = (List) query2
					.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.Group group = null;
			try {
				group = (pl.edu.pw.mini.sozpw.dataaccess.models.Group) resultsGroup
						.get(0);
			} catch (Exception e) {
				System.out.println("ERROR: Podana grupa nie istnieje.");
			}

			Query queryForGroupXUsers = session
					.createQuery("from GroupXUser where userId = :userId AND groupId = :groupId");
			queryForGroupXUsers.setParameter("userId", user.getIdUsers());
			queryForGroupXUsers.setParameter("groupId", group.getGroupId());

			List<pl.edu.pw.mini.sozpw.dataaccess.models.GroupXUser> resultsGroupXUser = queryForGroupXUsers
					.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.GroupXUser groupXUser = null;
			try {
				groupXUser = (pl.edu.pw.mini.sozpw.dataaccess.models.GroupXUser) resultsGroupXUser
						.get(0);
			} catch (Exception e) {
				System.out
						.println("ERROR: Podana relacja grupa-użytkownik nie istnieje.");
			}

			session.delete(groupXUser);
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

	@Override
	public List<String> getUsersAndGroupsHints(String query, int count) {
		System.out.println("getUsersAndGroupsHints");
		List<String> res = new ArrayList<String>();
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query queryForGroups = session
					.createQuery("from Group where name like :name");
			queryForGroups.setParameter("name", query + '%');

			List<pl.edu.pw.mini.sozpw.dataaccess.models.Group> result = queryForGroups
					.list();
			System.out.println("Get dedication hints liczba trafien: "
					+ result.size());
			for (pl.edu.pw.mini.sozpw.dataaccess.models.Group g : result) {
				res.add(g.getName());
				if (res.size() == count) {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return res;
		} finally {
			if (session != null)
				session.close();
		}

		if (res.size() < count) {
			res.addAll(getUsersHints(query, count - res.size()));
			return res;
		} else
			return res;
	}

	@Override
	public List<String> getSubscribingUsers(String groupName) {
		System.out.println("getSubscribingUsers: " + groupName);
		List<String> res = new ArrayList<String>();
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query1 = session.createQuery("from Group where name=:name");
			query1.setParameter("name", groupName);
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Group> results = (List) query1
					.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.Group group = null;
			try {
				group = (pl.edu.pw.mini.sozpw.dataaccess.models.Group) results
						.get(0);
			} catch (Exception e) {
				System.out.println("ERROR: Podana grupa nie istnieje.");
				return res;
			}

			List<pl.edu.pw.mini.sozpw.dataaccess.models.GroupXUser> resultsGroupXUser = (List) session
					.createQuery(
							"from GroupXUser where groupId = "
									+ group.getGroupId()).list();
			for (pl.edu.pw.mini.sozpw.dataaccess.models.GroupXUser gxu : resultsGroupXUser) {
				pl.edu.pw.mini.sozpw.dataaccess.models.User userForUsername;
				try {
					userForUsername = (pl.edu.pw.mini.sozpw.dataaccess.models.User) session
							.get(pl.edu.pw.mini.sozpw.dataaccess.models.User.class,
									gxu.getUserId());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}
				res.add(userForUsername.getUsername());
			}
			System.out.println("getSubscribingUsers liczba wyników: "
					+ res.size());
			return res;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return res;
		} finally {
			if (session != null)
				session.close();
		}
	}

	// jako błędy zwracam true!
	@Override
	public boolean getGroupVisibility(String groupName) {
		System.out.println("getGroupVisibility: " + groupName);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query1 = session.createQuery("from Group where name=:name");
			query1.setParameter("name", groupName);
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Group> results = (List) query1
					.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.Group group = null;
			try {
				group = (pl.edu.pw.mini.sozpw.dataaccess.models.Group) results
						.get(0);
			} catch (Exception e) {
				System.out.println("ERROR: Podana grupa nie istnieje.");
				return true;
			}
			System.out.println("getGroupVisibility: zwraca: "
					+ !group.getIsPrivate());
			return !group.getIsPrivate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return true;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public void setGroupVisibility(String groupName, boolean isPrivate) {
		System.out.println("setGroupVisibility: " + groupName + " na: "
				+ !isPrivate);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query1 = session.createQuery("from Group where name=:name");
			query1.setParameter("name", groupName);
			List<pl.edu.pw.mini.sozpw.dataaccess.models.Group> results = (List) query1
					.list();
			pl.edu.pw.mini.sozpw.dataaccess.models.Group group = null;
			try {
				group = (pl.edu.pw.mini.sozpw.dataaccess.models.Group) results
						.get(0);
			} catch (Exception e) {
				System.out.println("ERROR: Podana grupa nie istnieje.");
				return;
			}

			group.setIsPrivate(!isPrivate);

			session.save(group);
			session.getTransaction().commit();
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		} finally {
			if (session != null)
				session.close();
		}
	}
}
