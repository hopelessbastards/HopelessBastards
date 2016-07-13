package screenconverter;

import graphicsEngine.IRenderer;

public class ConverterProvider implements IConverterProvider{

	private IConverter converter;
	
	public ConverterProvider(IRenderer renderer) {
		converter = new Converter(renderer);
	}
	
	@Override
	public IConverter getConverter() {
		
		return converter;
	}

}
