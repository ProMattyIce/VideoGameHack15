import gnu.io.*;

import java.io.*;
import java.util.Enumeration;
import java.util.Enumeration;

public class SerialTest {
	public static void main(String args[]) {
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		//the main port
		CommPort thePort = null;
		CommPortIdentifier port = null;
		while (ports.hasMoreElements()) {
			port = (CommPortIdentifier) ports.nextElement();
			String type;
			switch (port.getPortType()) {
			case CommPortIdentifier.PORT_PARALLEL:
				type = "Parallel";
				break;
			case CommPortIdentifier.PORT_SERIAL:
				type = "Serial";
				break;
			default: /// Shouldn't happen
				type = "Unknown";
				break;
			}
			System.out.println(port.getName() + ": " + type);

			

		}
		try {
			thePort = port.open("COM3", 10);
		} catch (PortInUseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader inStream = null;
		OutputStream outStream = null;
		try {
			inStream = new BufferedReader(new InputStreamReader(thePort.getInputStream()));
			outStream = new PrintStream(thePort.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] toSend = new byte[1];
		toSend[0] = 1;
		try {
			outStream.write(toSend, 0, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

