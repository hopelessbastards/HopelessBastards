package applogic.loginstate;

import java.util.ArrayList;
import java.util.List;
import applogic.CursorInformationProvider;
import applogic.GameState;
import applogic.GameStateEnum;
import applogic.IGarbageCollector;
import applogic.IViewBuilderContainer;
import applogic.components.ActionListener;
import applogic.components.Container;
import applogic.components.IComponent;
import applogic.components.IContainer;
import applogic.components.LoginButton;
import applogic.components.OptionPane;
import applogic.components.PasswordTextField;
import applogic.components.TextField;
import applogic.elements.BasicElement;
import applogic.elements.Leaf;
import controller.events.ActivationNumber;
import controller.events.CursorInformation;
import controller.events.KeyValue;
import controller.events.KeyValueConstant;
import controller.events.MovedWay;
import screenconverter.IConverter;
import soundapi.ISound;
import soundapi.ISoundProvider;
import soundapi.MP3;

public class Login extends GameState{
	/*A bejelentkezõ GameState.*/
	
	private IGarbageCollector garbageCollector;
	private CursorInformationProvider cursorProvider;
	
	private IViewBuilderContainer container;

	private IContainer componentContainer;
	private boolean typeAble;
	private int typeTime;
	
	private double appTime;

	private ISound loginSong;
	/*A levél animációhoz szükséges leaf lista.*/
	private List<BasicElement> leafs;
	
	/*Komponensek, melyek megjelennek majd a képernyõn.*/
	private IComponent username;
	private IComponent password;
	private IComponent loginButton;
	private IComponent optionPane;
	
	public Login(IConverter converter,ISoundProvider soundProvider) {
		super(soundProvider);
		
		loginSong = new MP3("loginSong", soundProvider);
		loginSong.play();/*rögtön betöltõdéskor már el is kezdjük lejátszani a login songot.*/
		
		/*Beállítjuk a GameState információkat.A nextGameStetet azért állítom szintén Loginra
		 mert ha ez marad, akkor nem vált gamestatet a játék alapból.*/
		setNextGameState(GameStateEnum.LOGIN);
		setOwnGameStateType(GameStateEnum.LOGIN);
		/*Azért nullal hívom meg, mert itt nem kell a képernyõn senkit követni.*/
		this.cursorProvider = new CursorInformationProvider(null, converter);
		
		this.componentContainer = new Container(cursorProvider);
		
		this.username = new TextField(400, 300, 500, 30, componentContainer,soundProvider,true);
		this.password = new PasswordTextField(400, 400, 500, 30, componentContainer,soundProvider);
		this.optionPane = new OptionPane(200, 200,752,397,"Not an existing account!",componentContainer,soundProvider);
		this.loginButton = new LoginButton(900,200,335,390,"login",componentContainer);
		
		componentContainer.addComponent(this.username);
		componentContainer.addComponent(this.password);
		componentContainer.addComponent(this.optionPane);
		componentContainer.addComponent(this.loginButton);
		
		/*Az eseménykezelõ teljesen úgy mûködik mint az awt-s környezetben-->király*/
		this.loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionCommand() {
				if(username.toStringTransformation().length() < 2){
					optionPane.setShowNow(true);
					optionPane.playSoundEffect();
				}else{
					/*Ha átlépünk egy másik játékállásba, akkor a bejelentkezõ zene ha megy
					 leállítjuk*/
					loginSong.stop();
					setNextGameState(GameStateEnum.GAME);
				}	
			}
		});
	
		this.container = new LoginViewBuilderContainer(converter,this.cursorProvider,this.componentContainer);
		leafs = new ArrayList<BasicElement>();
		/*Itt kézzel adtam hozzá a leveleket, ezek fognak folymatosan hullani.*/
		leafs.add(new Leaf(0, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(10, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(100, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(0, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(20, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(50, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(200, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(210, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(1100, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(400, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(250, 0,60,60,0,0,0,container));
		leafs.add(new Leaf(350, 0,60,60,0,0,0,container));
	}
	
	@Override
	public void viewPrepare(double renderTime) {
		this.container.viewPrepare(renderTime);
	}
	
	@Override
	public void characterMoved(MovedWay way) {
	}

	@Override
	public void characterStopMoving(MovedWay way) {
	}

	@Override
	public void characterActivation(ActivationNumber number) {
	}

	@Override
	public void cursorClicked(CursorInformation screen) {
		this.cursorProvider.setClick(true);
		this.cursorProvider.setMove(false);
		this.cursorProvider.setLocationOnScreen(screen.getLocationOnScreen());
		this.cursorProvider.setXOnScreen(screen.getxOnScreen());
		this.cursorProvider.setYOnScreen(screen.getyOnScreen());
	}

	@Override
	public void cursorMOved(CursorInformation screen) {
		this.cursorProvider.setClick(false);
		this.cursorProvider.setMove(true);
		this.cursorProvider.setLocationOnScreen(screen.getLocationOnScreen());
		this.cursorProvider.setXOnScreen(screen.getxOnScreen());
		this.cursorProvider.setYOnScreen(screen.getyOnScreen());
	}

	@Override
	public void cursorRelease() {
		this.cursorProvider.setClick(false);
		this.cursorProvider.setMove(false);
	}

	@Override
	public void characterTyped(KeyValue value) {
		if(value.getKeyType() != null && value.getKeyType() == KeyValueConstant.ESCAPE){
			System.exit(0);
		}else if(value.getKeyType() != null && value.getKeyType() == KeyValueConstant.ENTER){
			
		}else{
			/*Ez a rész azért kell, hogy a 60 fps miatt egy billentyûleütés ne okozzon sok
			 karakterbevitelt, hanem másodpercenként csak egy karaktert lehessen beütni,
			 akkor is ha rátenyerelnek a bilentyûzetre.*/
			if(typeTime < appTime){
				typeAble = true;
			}else{
				typeAble = false;
			}
			for(int i=0;i<componentContainer.getComponents().size();i++){
				if(typeAble == true && componentContainer.getComponents().get(i).getFocus().getFocusOwnerComponent() == componentContainer.getComponents().get(i)){
					componentContainer.getComponents().get(i).keyhandle(value);
					typeAble = false;
				}
			}
		}
	}

	@Override
	public GameStateEnum tick(double appTime) {
		this.appTime = appTime;
		
		if(getNextGameState() == GameStateEnum.LOGIN){
			this.cursorProvider.tick(appTime);
			componentContainer.tick(appTime);
			for(int i=0;i<leafs.size();i++){
				leafs.get(i).tick(appTime);
			}
			return null;
		}else{
			return getNextGameState();
		}
	}

	@Override
	public void init() {
	}
}