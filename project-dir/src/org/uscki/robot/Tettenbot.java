package org.uscki.robot;

import org.uscki.robot.bots.Bot;

public class Tettenbot implements Bot {

	@Override
	public String ask(String input, String user) {
		if(input.substring(input.length()-2,input.length()).equals("en"))
			return "Haha, dat rijmt op tetten!";
		return "Interessant...";
	}
}
