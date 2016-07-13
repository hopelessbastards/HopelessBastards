package xmlhandlers;

import java.util.List;

import graphicsEngine.ImageDescriptor;

/*Ezen az interfacen keresztül lehet a DescriptorLoadert elérni(Nálam a saxparsert ezen keresztül érjük el).
 Ennek az a lényege, hogy külsõ forrásból elõállítsa a képleíró objektum listát és visszaadja a hívónak.*/
public interface DescriptorLoader {
	public List<ImageDescriptor> Parse(String documentPath);	
}