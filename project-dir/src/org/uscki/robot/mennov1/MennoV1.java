package org.uscki.robot.mennov1;

import org.uscki.robot.lib.Looker;
import org.uscki.robot.lib.Mover;


public class MennoV1 /*extends PApplet*/ {

	static MennoV1 master;
	Looker l;
	Mover m;
	int face_move_interval;
	
	public static final int IWIDTH = 240;
	public static final int IHEIGHT = 320;
	/*	
	public static void main(String args[]) {
		PApplet.main(new String[] { "mennov1.MennoV1" });
	}
*/
	// Het is een singleton pattern
	public static MennoV1 getInstance() {
		if(master == null)
			return null; // throw error?
		return master;
	}

	public void setup() {
		//println("Started MennoV1");
		//size(IHEIGHT, IWIDTH);

		// Initialize libraries
		l = new Looker(this);
		m = new Mover(this);

		// Initialize threads
		
		Thread ircClient = new Thread(new IrcClient());
		ircClient.start();
		//clients[1] = new Thread(new JabberClient());
		//Thread terminalClient = new Thread(new TerminalClient());
		//terminalClient.start();
		

		face_move_interval = 0;
	}
/*
	public void draw() {
		l.look();
		noFill();
		stroke(255, 0, 0);
		if (l.getFaces().length>0) {
			for( int i=0; i<l.getFaces().length; i++ ) {
				rect( l.getFaces()[i].x, l.getFaces()[i].y, l.getFaces()[i].width, l.getFaces()[i].height ); 
			}

			face_move_interval = m.moveToInterval(
					l.getFaces()[0].x+(l.getFaces()[0].width/2),
					l.getFaces()[0].y+(l.getFaces()[0].height/2),
					face_move_interval, 5);
		}
		String url = "https://www.uscki.nl/~kruit/zebra/modules/Webcam/upload.php";
		ImageToWeb img_to_web = new ImageToWeb(this);
		img_to_web.post("uploadedfile",url,"webcam",true);
	}*/
}