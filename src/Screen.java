import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;




public class Screen extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	private final static int CELL_SIZE = 25;
	
	private static final int SIZE = CELL_SIZE;
	private static final int BORDER = 0;

	private int width;
	private int height;

	private int xLeft;
	private int xRight;

	private int xBoneco;
	private int yBoneco;

	private Image image;


	private boolean[][] labyrinth;

	public Screen(boolean[][] labyrinth) {
		this.labyrinth = labyrinth;

		this.width = this.labyrinth[0].length;
		this.height = this.labyrinth.length;
		
		xLeft = BORDER + SIZE / 2;
		xRight = 2 * BORDER + 3 * SIZE / 2;

		xBoneco = xLeft;
		yBoneco = BORDER + SIZE / 2;
		
		image = new ImageIcon(getClass().getResource("/img/example.png")).getImage();

		Timer timer = new Timer(1000, this);
		timer.start();

		setPreferredSize(new Dimension(this.width * CELL_SIZE, this.height * CELL_SIZE));
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
//		g.setColor(Color.WHITE);
//		g.fillRect(0, 0, width, height);

		// Por fim, desenhamos a imagem.
    	g.drawImage(image, xBoneco - SIZE / 2, yBoneco - SIZE / 2, SIZE, SIZE, null);

    	getToolkit().sync();
    }
    
	public void keyPressed(KeyEvent e) {
    	int key = e.getKeyCode();

    	// Se a tecla apertada foi a seta para a esquerda...
    	if(key == KeyEvent.VK_LEFT) {
    		// ...movemos o boneco para a esquerda...
    		xBoneco -= SIZE;
    		// ...e redesenhamos a tela.
    		repaint();
    	}

    	// Se a tecla apertada foi a seta para a direita...
    	if(key == KeyEvent.VK_RIGHT) {
    		// ...movemos o boneco para a direita!
    		xBoneco += SIZE;
    		// ...e redesenhamos a tela.
    		repaint();
    	}
    	
    	if(key == KeyEvent.VK_DOWN) {
    		// ...movemos o boneco para a direita!
    		yBoneco += SIZE;
    		// ...e redesenhamos a tela.
    		repaint();
    	}
    	
    	if(key == KeyEvent.VK_UP) {
    		// ...movemos o boneco para a direita!
    		yBoneco -= SIZE;
    		// ...e redesenhamos a tela.
    		repaint();
    	}
    	
    	
	}
    
    public void keyReleased(KeyEvent event) {
	}
	
	// N�o � necess�rio entender essa parte por enquanto.
	public void keyTyped(KeyEvent event) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
