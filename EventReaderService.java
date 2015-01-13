import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class EventReaderService {
	public static final String URL = "http://dining.columbia.edu/events";
	private Document doc;
	
	public EventReaderService() {
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements events = doc.select("div.views-row");
		CopyOnWriteArraySet<Event> uniqueEvents = new CopyOnWriteArraySet<Event>();
		
		for(Element e : events) {
			String title       = e.select("div.views-field-title").first().text(),
				   date        = e.select("div.views-field-field-event-date-value").first().text(),
				   location    = e.select("div.views-field-field-event-location-value").first().text(),
				   description = e.select("div.views-field-field-event-description-value").first().text(),
				   imgURL      = e.select("div.views-field-field-event-image-fid span a").first().attr("abs:href");
			uniqueEvents.add(new Event(title, date, location, description, imgURL));
		}
		for(Event e : uniqueEvents)
			System.out.println(e + "\n");
		
	}
	
	public static void main(String[] args) {
		new EventReaderService();
	}
}

class Event {
	
	public String title, date, location, description, imgURL;
	
	public Event(String title, String date, String location, String description, String imgURL) {
		this.title = title;
		this.date = date;
		this.location = location;
		this.description = description;
		this.imgURL = imgURL;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Event)) 
			return false;
		
		Event other = (Event) o;
		return other.title.equals(this.title) &&
			   other.date.equals(this.date) &&
			   other.location.equals(this.location) &&
			   other.description.equals(this.description);
	}
	
	public String toString() {
		return title +"\n" +
			   date + "\n" +
			   location + "\n" + 
			   description + "\n" + 
			   imgURL;
	}
}
