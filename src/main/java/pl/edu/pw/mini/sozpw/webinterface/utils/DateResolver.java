package pl.edu.pw.mini.sozpw.webinterface.utils;

import java.util.Date;

@SuppressWarnings("deprecation")
public class DateResolver {

	public static String resolveDate(long date) {
		if (date == 0)
			return "Brak informacji";

		long now = new Date().getTime();
		long diff = now - date;

		if (diff < 0)
			return resolveFutureDate(date, diff);
		else
			return resolvePastDate(date, diff);
	}

	private static String resolvePastDate(long date, long diff) {
		diff = diff / 1000;
		if (diff == 1 || diff == 0)
			return "sekundę temu";
		if (diff < 60) {
			if (diff > 10 && diff < 20)
				return diff + " sekund temu";

			if (diff % 10 > 1 && diff % 10 < 5)
				return diff + " sekundy temu";

			return diff + " sekund temu";
		}

		diff = diff / 60;
		if (diff == 1)
			return "minutę temu";
		if (diff < 60) {
			if (diff > 10 && diff < 20)
				return diff + " minut temu";

			if (diff % 10 > 1 && diff % 10 < 5)
				return diff + " minuty temu";

			return diff + " minut temu";
		}

		Date current = new Date();
		current.setHours(0);
		current.setMinutes(0);
		current.setSeconds(0);

		long todayMidnight = current.getTime();
		long yesterdayMidnight = todayMidnight - 24 * 60 * 60 * 1000;

		diff = diff / 60;
		if (diff == 1)
			return "godzinę temu";
		if (diff < 3)
			return diff + " godziny temu";

		current = new Date(date);
		if (date > todayMidnight)
			return "dzisiaj, " + hours(current) + ":" + minutes(current);

		if (date < todayMidnight && date > yesterdayMidnight)
			return "wczoraj, " + hours(current) + ":" + minutes(current);

		diff = diff / 24;
		if(diff == 1)
			diff++;
		if (diff < 4)
			return diff + " dni temu";

		return date(current) + "-" + month(current) + "-" + (current.getYear() + 1900);

	}

	private static String resolveFutureDate(long date, long diff) {
		diff = diff * (-1);
		diff = diff / 1000;
		if (diff == 1 || diff == 0)
			return "za sekundę";

		if (diff < 60) {
			if (diff > 10 && diff < 20)
				return "za " + diff + " sekund";

			if (diff % 10 > 1 && diff % 10 < 5)
				return "za " + diff + " sekundy";

			return "za " + diff + " sekund";
		}

		diff = diff / 60;
		if (diff == 1)
			return "za minutę";
		if (diff < 60) {
			if (diff > 10 && diff < 20)
				return "za " + diff + " minut";

			if (diff % 10 > 1 && diff % 10 < 5)
				return "za " + diff + " minuty";

			return "za " + diff + " minut";
		}

		Date current = new Date();
		current.setHours(0);
		current.setMinutes(0);
		current.setSeconds(0);

		long todayMidnight = current.getTime() + 24 * 60 * 60 * 1000;
		long tomorrowMidnight = todayMidnight + 24 * 60 * 60 * 1000;

		diff = diff / 60;
		if (diff == 1)
			return "za godzinę";
		if (diff < 3)
			return "za " + diff + " godziny";

		current = new Date(date);
		if (date < todayMidnight)
			return "dzisiaj, " + hours(current) + ":" + minutes(current);

		if (date > todayMidnight && date < tomorrowMidnight)
			return "jutro, " + hours(current) + ":" + minutes(current);

		diff = diff / 24;
		if(diff == 1)
			diff++;
		if (diff < 4)
			return "za " + diff + " dni";

		return date(current) + "-" + month(current) + "-" + (current.getYear() + 1900);
	}

	private static String hours(Date date) {
		String prefix = "";
		if (date.getHours() < 10) {
			prefix = "0";
		}		return prefix + date.getHours();
	}

	private static String minutes(Date date) {
		String prefix = "";
		if (date.getMinutes() < 10) {
			prefix = "0";
		}
		return prefix + date.getMinutes();
	}

	private static String date(Date date) {
		String prefix = "";
		if (date.getDate() < 10) {
			prefix = "0";
		}
		return prefix + date.getDate();
	}

	private static String month(Date date) {
		String prefix = "";
		if (date.getMonth() + 1 < 10) {
			prefix = "0";
		}
		return prefix + (date.getMonth() + 1);
	}

}
