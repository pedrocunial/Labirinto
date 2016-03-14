import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;




public class Screen extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	private final static int CELL_SIZE = 25;
	
	private static final int SIZE = CELL_SIZE;

	private int width;
	private int height;

	private int xBoneco;
	private int yBoneco;

	private int xBot;
	private int yBot;

	
	public int getxBot() {
		return xBot;
	}

	public int getyBot() {
		return yBot;
	}

	
	private Image image;
	private Image imageBot;


	private boolean[][] labyrinth;

	public Screen(boolean[][] labyrinth) {
		this.labyrinth = labyrinth;

		this.width = this.labyrinth[0].length;
		this.height = this.labyrinth.length;

		xBoneco = SIZE / 2;
		yBoneco = SIZE / 2;
		
		xBot = width * 25 - SIZE / 2;
		yBot = SIZE / 2;
		System.out.println(xBot);
		System.out.println(yBot);
		
		image = new ImageIcon(getClass().getResource("/img/fabiomiranda.png")).getImage();
		imageBot = new ImageIcon(getClass().getResource("/img/example.png")).getImage();

		
		Timer timer = new Timer(1000, this);
		timer.start();

		setPreferredSize(new Dimension(this.width * CELL_SIZE, this.height * CELL_SIZE));

//		botAI();
	}

	public void paintComponent(Graphics g) {
		for(int i = 0; i < this.height; i++) {
			int y = i * CELL_SIZE;

			for(int j = 0; j < this.width; j++) {
				int x = j * CELL_SIZE;

				if(labyrinth[i][j]) {
					g.setColor(Color.WHITE);
				}
				else {
					g.setColor(Color.BLACK);
				}

				g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
			}
		}

    	
		// Por fim, desenhamos a imagem.
    	g.drawImage(image, xBoneco - SIZE / 2, yBoneco - SIZE / 2, SIZE, SIZE, null);
    	g.drawImage(imageBot, xBot - SIZE / 2, yBot - SIZE / 2, SIZE, SIZE, null);
    	
    	getToolkit().sync();
    }
    
	public void keyPressed(KeyEvent e) {
    	int key = e.getKeyCode();
    	int xPos = (xBoneco - SIZE / 2) / SIZE;
    	int yPos = (yBoneco - SIZE / 2) / SIZE;
   	
    	// Se a tecla apertada foi a seta para a esquerda...
    	if(key == KeyEvent.VK_LEFT) {
	    	// checamos se ele pode ir para a direção desejada	
    		if(xPos != 0 && labyrinth[yPos][xPos-1]) {
	    		// ...movemos o boneco para a esquerda...
	    		xBoneco -= SIZE;
	    		// ...e redesenhamos a tela.
	    		repaint();
    		}
    	}

    	// Se a tecla apertada foi a seta para a direita...
    	if(key == KeyEvent.VK_RIGHT) {
    		// podemos ir para direita?
    		if(xPos != width && labyrinth[yPos][xPos+1]) {
	    		// ...movemos o boneco para a direita!
	    		xBoneco += SIZE;
	    		// ...e redesenhamos a tela.
	    		repaint();
	    	}
    	}
    	
    	if(key == KeyEvent.VK_DOWN) {
    		// para baixo...
    		if(yPos != height && labyrinth[yPos+1][xPos]) {
	    		// movemos para baixo
	    		yBoneco += SIZE;
	    		repaint();
	    	}
    	}
    	
    	if(key == KeyEvent.VK_UP) {
    		// para cima!
    		if(yPos != 0 && labyrinth[yPos-1][xPos]) {
	    		// movemos para cima
	    		yBoneco -= SIZE;
	    		repaint();
	    	}
    	}    	
	}
    
	public int[] botAI() {
		int xPos = (xBot - SIZE / 2) / SIZE;
    	int yPos = (yBot - SIZE / 2) / SIZE;
		Stack<Crumb> stack = new Stack<Crumb>();
		stack.push(new Crumb(xPos, yPos));

		while(!stack.empty()) {
			int[] local = {this.xBot, this.yBot};
			yPos = stack.peek().getY();
			xPos = stack.peek().getX();
			
			if(stack.peek().getPasses() == 0) {
				if(!labyrinth[yPos][xPos-1]){
					stack.peek().incrementPasses();
				} else {
					if(xPos > 0) {
						// Esquerda
						xPos--;
						xBot -= SIZE;
						stack.push(new Crumb(xPos, yPos));
						local[0] = xBot;
						System.out.println("0");
						stack.peek().incrementPasses();
					}
				}
			}
			
			if(stack.peek().getPasses() == 1) {
				if(!labyrinth[yPos][xPos+1]){
					stack.peek().incrementPasses();
				} else {
					if(xPos < width) {
						// Direita
						xPos++;
						xBot += SIZE;
						stack.push(new Crumb(xPos, yPos));
						local[0] = xBot;
						System.out.println("1");
						stack.peek().incrementPasses();
					}
				}
			}
			
			if(stack.peek().getPasses() == 0) {
				if(!labyrinth[yPos-1][xPos]){
					stack.peek().incrementPasses();
				} else {
					if(yPos > 0) {
						// Cima
						yPos--;
						yBot -= SIZE;
						stack.push(new Crumb(xPos, yPos));
						local[1] = yBot;
						System.out.println("0");
						stack.peek().incrementPasses();
					}
				}
			}
			
			if(stack.peek().getPasses() == 0) {
				if(!labyrinth[yPos+1][xPos]){
					stack.peek().incrementPasses();
				} else {
					if(yPos < height) {
						// Baixo
						yPos++;
						yBot += SIZE;
						stack.push(new Crumb(xPos, yPos));
						local[1] = yBot;
						System.out.println("0");
						stack.peek().incrementPasses();
					}
				}
			}
			
//			if(labyrinth[yPos][xPos-1] && stack.peek().getPasses() == 0 && xPos > 0) {
//				// Esquerda
//				xPos--;
//				xBot -= SIZE;
//				stack.push(new Crumb(xPos, yPos));
//				local[0] = xBot;
//				System.out.println("0");
//				
//			} else if(labyrinth[yPos][xPos+1] && stack.peek().getPasses() == 1 && (xPos < width)) {
//				// Direita
//				xPos++;
//				xBot += SIZE;
//				stack.push(new Crumb(xPos, yPos));				
//				local[0] = xBot;
//				System.out.println("1");
//				
//			} else if(labyrinth[yPos-1][xPos] && stack.peek().getPasses() == 2 && yPos > 0) {
//				// Cima
//				yPos--;
//				yBot -= SIZE;
//				stack.push(new Crumb(xPos, yPos));
//				local[1] = yBot;
//				System.out.println("2");
//				
//			} else if(labyrinth[yPos+1][xPos] && stack.peek().getPasses() == 3 && (yPos < height)) {
//				// Baixo
//				yPos++;
//				yBot += SIZE;
//				stack.push(new Crumb(xPos, yPos));
//				local[1] = yBot;
//				System.out.println("3");
//				
//			} else {
//				stack.pop();
//				
//			}
				
			stack.peek().incrementPasses();
			return local;
		}

		int[] local = {this.xBot, this.yBot};
		return local;
	
	}
	
	public void keyReleased(KeyEvent event) {
	}
	
	public void keyTyped(KeyEvent event) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		xBot = botAI()[0];
		yBot = botAI()[1];
	}
}
