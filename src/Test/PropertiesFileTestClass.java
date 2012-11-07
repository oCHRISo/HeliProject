package Test;

import com.mycompany.reservationsystem.peer.data.PropertyFile;

public class PropertiesFileTestClass {
	public static void main(String[] args) {
		System.out.println(PropertyFile.getInstance().getPeerStateDeamonTime());
	}
}
