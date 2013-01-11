package pl.edu.pw.mini.sozpw.dataaccess.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Category;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Comment;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Note;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.User;

public class ModelImpl implements Model {

	private static final String DEFAULT_KEY = "defaultKey";
	private static int noteId = 1;

	@Override
	public User loginUser(String username, String pass) {

		if (pass.equals("123")) {
			User ret = new User();
			ret.setUsername(username);
			return ret;
		}

		return null;
	}

	@Override
	public String registerUser(String username, String pass, String mail) {
		if (username.equals("123")) {
			return null;
		}
		return DEFAULT_KEY;
	}

	@Override
	public boolean confirmRegistration(String user, String key) {
		return key.equals(DEFAULT_KEY);
	}

	@Override
	public List<Note> getNotes(String user) {
		if (user.equals("Pawel")) {
			ArrayList<Note> notes = new ArrayList<Note>();
			Note sampleNote1 = new Note();
			sampleNote1.setId(noteId++);
			sampleNote1.setTopic("Pizza wieczorem");
			sampleNote1
					.setContent("Cześć, szukam osoby, która jest chętna pójść ze mną na pizzę wieczorem, najlepiej po 20. Lokalizacja dowolna. Mój tel: 600-123-123.");
			sampleNote1.setUsername("Pawel");
			sampleNote1.setLatitude(52.247280);
			sampleNote1.setLongitude(21.013540);
			sampleNote1.setCategory(Category.SPOTKANIE);
			sampleNote1.setCreateDate(new Date().getTime() - 14 * 60 * 1000);
			sampleNote1.setExpiryDate(new Date().getTime() + 20 * 60 * 60
					* 1000);
			sampleNote1.setFilename("");

			Comment comment1 = new Comment();
			comment1.setComment("Ja chętnie się wybiorę!");
			comment1.setDate(new Date().getTime() - 7 * 60 * 1000);
			comment1.setUsername("Małgosia");

			sampleNote1.getComments().add(comment1);
			notes.add(sampleNote1);

			Note sampleNote2 = new Note();
			sampleNote2.setId(noteId++);
			sampleNote2.setTopic("Konkurs! Super nagrody do wygrania.");
			sampleNote2
					.setContent("Kawiarnia \"Pyszna Kawa\" ogłasza konkurs na najlepszy slogan reklamowy. Szczegóły w lokalu oraz na załączonym plakacie.");
			sampleNote2.setUsername("PysznaKawa");
			sampleNote2.setLatitude(52.19823);
			sampleNote2.setLongitude(21.010868);
			sampleNote2.setCategory(Category.OGLOSZENIE);
			sampleNote2.setCreateDate(new Date().getTime() - 7 * 24 * 60 * 60
					* 1000);
			sampleNote2.setExpiryDate(new Date().getTime() + 7 * 24 * 60 * 60
					* 1000);
			sampleNote2.setFilename("plakat.jpg");
			notes.add(sampleNote2);

			Note sampleNote3 = new Note();
			sampleNote3.setId(noteId++);
			sampleNote3.setTopic("Zobacz wystawę.");
			sampleNote3
					.setContent("Jak będziesz przechodził to zwróć uwagę na na wystawę w sklepie meblowym. Spodobała mi się ta kanapa.");
			sampleNote3.setUsername("Dominika");
			sampleNote3.setLatitude(52.23738);
			sampleNote3.setLongitude(20.957492);
			sampleNote3.setCategory(Category.BRAK_KATEGORII);
			sampleNote3.setCreateDate(new Date().getTime() - 3 * 24 * 60 * 60
					* 1000);
			sampleNote3.getDedicationList().add("Pawel");
			notes.add(sampleNote3);

			return notes;
		}
		return new ArrayList<Note>();
	}

	@Override
	public Integer addNote(Note note, byte[] attachment) {
		if (attachment != null) {
			System.out.println(attachment.length);
		}
		return noteId++;
	}

	@Override
	public boolean editNote(Note note, byte[] attachment) {
		if (attachment != null) {
			System.out.println(attachment.length);
		}
		return true;
	}

	@Override
	public boolean deleteNote(Integer noteId) {
		return true;
	}

	@Override
	public boolean addComment(int noteId, Comment comment) {
		return true;
	}

	@Override
	public List<String> getUsersHints(String query, int count) {
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
	public List<String> getSubscribedGroups(String user) {
		if (user.equals("Pawel")) {
			return Arrays.asList("GrupaW1");
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
	public boolean changePassword(String username, String oldPass, String newPass) {
		return true;
	}

	@Override
	public byte[] getAttachment(int noteId) {
		return null;
	}

	@Override
	public List<String> getCreatedGroups(String username) {
		return Arrays.asList("GrupaW1", "Warszawa", "Riviera",
				"MiNI", "Politechnika");
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
		List<String> result = getUsersHints(query, count);
		result.addAll(getGroupsHints(query, count));
		return result.subList(0, 5);
	}

	@Override
	public List<String> getSubscribingUsers(String groupName) {
		return Arrays.asList("Brooklyn", "Jesse", "Marissa", "Pawel");
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
