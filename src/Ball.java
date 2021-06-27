import java.awt.*;
import java.util.Random;

/**
	Esta classe representa a bola usada no jogo. A classe princial do jogo (Pong)
	instancia um objeto deste tipo quando a execução é iniciada.
*/

public class Ball {

	private double cx;
	private double cy;
	private double width;
	private double height;
	private Color color;
	private double speed;

	// Direções a serem geradas no construtor
	private double xDirection;
	private double yDirection;

	/**
		Construtor da classe Ball. Observe que quem invoca o construtor desta classe define a velocidade da bola 
		(em pixels por millisegundo), mas não define a direção deste movimento. A direção do movimento é determinada 
		aleatóriamente pelo construtor.

		@param cx coordenada x da posição inicial da bola (centro do retangulo que a representa).
		@param cy coordenada y da posição inicial da bola (centro do retangulo que a representa).
		@param width largura do retangulo que representa a bola.
		@param height altura do retangulo que representa a bola.
		@param color cor da bola.
		@param speed velocidade da bola (em pixels por millisegundo).
	*/

	public Ball(double cx, double cy, double width, double height, Color color, double speed){
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
		this.color = color;
		this.speed = speed;

		// Gera direções aleatórias

		Random random = new Random();

		this.xDirection = random.nextInt(1);
		if (this.xDirection == 0) this.xDirection = -1;

		this.yDirection = random.nextInt(1);
		if (this.yDirection == 0) this.yDirection = -1;
	}


	/**
		Método chamado sempre que a bola precisa ser (re)desenhada.
	*/

	public void draw(){
		GameLib.setColor(color);
		GameLib.fillRect(this.getCx(), this.getCy(), width, height);
	}

	/**
		Método chamado quando o estado (posição) da bola precisa ser atualizado.
		
		@param delta quantidade de millisegundos que se passou entre o ciclo anterior de atualização do jogo e o atual.
	*/

	public void update(long delta){
		this.cx += xDirection * delta * this.getSpeed();
		this.cy += yDirection * delta * this.getSpeed();
	}

	/**
		Método chamado quando detecta-se uma colisão da bola com um jogador.
	
		@param playerId uma string cujo conteúdo identifica um dos jogadores.
	*/

	public void onPlayerCollision(String playerId){
		this.xDirection = -this.xDirection;
	}

	/**
		Método chamado quando detecta-se uma colisão da bola com uma parede.

		@param wallId uma string cujo conteúdo identifica uma das paredes da quadra.
	*/

	public void onWallCollision(String wallId){
		switch (wallId) {
			case "Left":
			case "Right":
				this.xDirection = -this.xDirection;
			break;
			case "Top":
			case "Bottom":
				this.yDirection = -this.yDirection;
			break;
		}
	}

	/**
		Método que verifica se houve colisão da bola com uma parede.

		@param wall referência para uma instância de Wall contra a qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/
	
	public boolean checkCollision(Wall wall){
		String wallId = wall.getId();
		switch (wallId) {
			case "Left":
				if (this.getCx() <= wall.getCx()) return true;
			break;
			case "Right":
				if (this.getCx() >= wall.getCx()) return true;
			break;
			case "Top":
				if (this.getCy() <= wall.getCy()) return true;
			break;
			case "Bottom":
				if (this.getCy() >= wall.getCy()) return true;
			break;
		}
		return false;
	}

	/**
		Método que verifica se houve colisão da bola com um jogador.

		@param player referência para uma instância de Player contra o qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/	

	public boolean checkCollision(Player player){

		// A bola precisa colidir entre o limite superior e inferior do player (equivalente a sua altura)
		double topLimit = player.getCy() + player.getHeight()/2;
		double bottomLimit = player.getCy() - player.getHeight()/2;

		String playerId = player.getId();
		switch (playerId) {
			case "Player 1":
				if (this.getCx() <= player.getCx() && this.getCy() <= topLimit && this.getCy() >= bottomLimit) return true;
			break;
			case "Player 2":
				if (this.getCx() >= player.getCx() && this.getCy() <= topLimit && this.getCy() >= bottomLimit) return true;
			break;
		}
		return false;
	}

	/**
		Método que devolve a coordenada x do centro do retângulo que representa a bola.
		@return o valor double da coordenada x.
	*/
	
	public double getCx(){
		return cx;
	}

	/**
		Método que devolve a coordenada y do centro do retângulo que representa a bola.
		@return o valor double da coordenada y.
	*/

	public double getCy(){
		return cy;
	}

	/**
		Método que devolve a velocidade da bola.
		@return o valor double da velocidade.

	*/

	public double getSpeed(){
		return speed;
	}

}
