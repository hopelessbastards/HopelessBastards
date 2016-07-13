package screenconverter;
/*A Converter komponenscsomag biztosítását végzi.A ProviderFactory komponens ezen keresztül éri el
 a ConverterProvider osztályomat.*/
public interface IConverterProvider {
	public IConverter getConverter();
}
