import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.pushover.client.*;

class Server {
	static PushoverClient client = new PushoverRestClient();
	static String PUSHOVER_USER, PUSHOVER_KEY, LOGFILE = "log.log";
	
	public static void main(String args[]) throws Exception {
		if (args.length != 2)
			throw new Exception("Call with >java Server.java PUSHOVER_USER PUSHOVER_KEY");
		PUSHOVER_USER = args[0];
		PUSHOVER_KEY = args[1];
		
		@SuppressWarnings("resource")
		DatagramSocket serverSocket = new DatagramSocket(53);
		byte[] receiveData = new byte[1024];
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			char[] arr = sentence.replace("arpa", "").replace("ip", "").replace((char) 5, '.').replace((char) 3, '.')
					.toCharArray();
			String msg = "";
			for (char c : arr)
				if ((c >= 'a' && c <= 'z') || (c > '0' && c <= '9') || c == '.')
					msg += "" + c;

			if (msg.replace("1", "").replace("0", "").replace(".", "").replace("6", "").length() > 0)
				client.pushMessage(PushoverMessage.builderWithApiToken(PUSHOVER_KEY)
						.setUserId(PUSHOVER_USER).setMessage(msg).build());
			appendLog(LOGFILE, sentence);			
		}
	}

	public static void appendLog(String fileName, String str) throws IOException{
		if (fileName.isEmpty())
			return;
		str = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()) + " -> " + str;
		File f = new File(fileName);
		PrintWriter out = (f.exists() && !f.isDirectory()) ? new PrintWriter(new FileOutputStream(f, true)) : new PrintWriter(f);
		out.append(System.lineSeparator() + str);
		out.close();
	}
}