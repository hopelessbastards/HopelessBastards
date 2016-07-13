package startapp;

import java.awt.EventQueue;
import initapplication.IProviderFactory;
import initapplication.ProviderFactory;

public class StartApp {
	/*Az alkalmazás main() függvényét tartalmazza.Ez példányosítja a ProviderFayctory komponenst,
	 ami a konstruktorában szépen felépíti a függõségeket, létrehoz minden szükséges komponenst a 
	 játék gördülékeny futtatásához.Ezt az eseménykezelõ szálon intézem, a konkurencia
	 probléma elkerülése végett.*/

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IProviderFactory factory = new ProviderFactory();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}