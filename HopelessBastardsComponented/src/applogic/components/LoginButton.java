package applogic.components;

import applogic.components.viewbuilders.LoginButtonViewBuilder;

public class LoginButton extends Button{

	/*Ez egy olyan gomb ami más képet tartalmaz mint a standard , de mégis gomb funkciók 
	 vannak, ezért származtatom a Button osztályból.*/
	public LoginButton(int x, int y, int width, int height, String label, IContainer container) {
		super(x, y, width, height, container);
		setButtonImage(new LoginButtonViewBuilder(this));
		container.getImages().add(getButtonImage());
		setLabel("login");
	}
}