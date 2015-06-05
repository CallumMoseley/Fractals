import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class FractalPanel extends JPanel implements MouseListener,
		MouseMotionListener, KeyListener
{
	private static final int MAX_FRACTAL = 4;

	private int[] iterations;
	private int[] n;
	private int fractalNum;

	public FractalPanel()
	{
		setPreferredSize(new Dimension(1920, 1080));

		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);

		iterations = new int[] { 1, 1, 1, 1, 1 };
		n = new int[] { 6, 0, 0, 45, 0 };
		fractalNum = 4;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.BLACK);
		g.drawString("Iterations: " + iterations[fractalNum], 5, 15);
		if (fractalNum == 0)
		{
			g.drawString("n: " + n[fractalNum], 5, 25);
			drawNFlake(g, iterations[fractalNum], n[fractalNum], 300,
					getWidth() / 2, getHeight() / 2);
		}
		else if (fractalNum == 1)
		{
			sierpinskiCarpet(g, iterations[fractalNum], getWidth() / 2,
					getHeight() / 2,
					1000);
		}
		else if (fractalNum == 2)
		{
			sierpinskiTriangle(g, iterations[fractalNum], getWidth() / 2,
					getHeight() / 2,
					500);
		}
		else if (fractalNum == 3)
		{
			g.drawString("n: " + n[fractalNum], 5, 25);
			pythagoreanTree(g, iterations[fractalNum], getWidth() * 4 / 9,
					getWidth() * 5 / 9, getHeight(), getHeight(), Math.PI / 180 * n[fractalNum]);
		}
		else if (fractalNum == 4)
		{
			binaryTree(g, iterations[fractalNum], getWidth() / 2, getHeight(), Math.PI / 2, 500, Math.PI / 4);
		}
	}

	private void drawNFlake(Graphics g, int iter, int n, double size,
			double x, double y)
	{
		if (iter == 0 || size < 0.25)
		{
			g.fillRect((int) x, (int) y, 1, 1);
			return;
		}

		for (double angle = 0; angle < 2 * Math.PI; angle += Math.PI * 2 / n)
		{
			double xDiff = Math.cos(angle);
			double yDiff = Math.sin(angle);
			g.drawLine((int) Math.round(x), (int) Math.round(y),
					(int) Math.round(x + xDiff * size),
					(int) Math.round(y + yDiff * size));
			double sum = 1;
			for (double k = 1; k <= Math.floor(n / 4); k++)
			{
				sum += Math.cos(2 * Math.PI * k / n);
			}
			double scaleFactor = 1 / (2 * sum);
			drawNFlake(g, iter - 1, n, size * scaleFactor, x + xDiff * size, y
					+ yDiff * size);
		}
	}

	private void sierpinskiCarpet(Graphics g, int iter, double x, double y,
			double size)
	{
		if (iter == 0)
		{
			g.setColor(Color.BLACK);
			g.fillRect((int) (x - size / 2), (int) (y - size / 2), (int) size,
					(int) size);
			return;
		}
		g.setColor(Color.BLACK);
		g.fillRect((int) (x - size / 2), (int) (y - size / 2), (int) size,
				(int) size);
		g.setColor(Color.WHITE);
		g.fillRect((int) (x - size / 6), (int) (y - size / 6),
				(int) (size / 3), (int) (size / 3));

		for (int xd = -1; xd <= 1; xd++)
		{
			for (int yd = -1; yd <= 1; yd++)
			{
				if (xd != 0 || yd != 0)
				{
					sierpinskiCarpet(g, iter - 1, x + (xd * size / 3), y
							+ (yd * size / 3), size / 3);
				}
			}
		}
	}

	private void sierpinskiTriangle(Graphics g, int iter, double x, double y,
			double size)
	{
		if (iter == 0)
		{
			g.setColor(Color.BLACK);
			drawTriangle(g, x, y, size, 0);
			return;
		}

		g.setColor(Color.BLACK);
		drawTriangle(g, x, y, size, 0);
		g.setColor(Color.WHITE);
		drawTriangle(g, x, y, size / 2, Math.PI / 3);

		sierpinskiTriangle(g, iter - 1, x, y - size / 2, size / 2);
		sierpinskiTriangle(g, iter - 1, x + Math.cos(Math.PI * 11 / 6) * size
				/ 2, y - Math.sin(Math.PI * 11 / 6) * size / 2, size / 2);
		sierpinskiTriangle(g, iter - 1, x + Math.cos(Math.PI * 7 / 6) * size
				/ 2, y - Math.sin(Math.PI * 7 / 6) * size / 2, size / 2);
	}

	private void drawTriangle(Graphics g, double x, double y, double size,
			double angle)
	{
		g.fillPolygon(
				new int[] {
						(int) Math.round(x + Math.cos(Math.PI / 2 + angle)
								* size),
						(int) Math.round(x + Math.cos(Math.PI * 11 / 6 + angle)
								* size),
						(int) Math.round(x + Math.cos(Math.PI * 7 / 6 + angle)
								* size) },
				new int[] {
						(int) Math.round(y - Math.sin(Math.PI / 2 + angle)
								* size),
						(int) Math.round(y - Math.sin(Math.PI * 11 / 6 + angle)
								* size),
						(int) Math.round(y - Math.sin(Math.PI * 7 / 6 + angle)
								* size) }, 3);
	}

	private void pythagoreanTree(Graphics g, int iter, double x1, double x2,
			double y1, double y2, double ang)
	{
		if (iter == 0)
		{
			return;
		}

		double size = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		double angle = Math.atan((y1 - y2) / (x2 - x1));
		if (x2 < x1)
		{
			angle += Math.PI;
		}

		g.setColor(Color.BLACK);
		g.fillPolygon(
				new int[] {
						(int) Math.round(x1),
						(int) Math.round(x2),
						(int) Math.round(x2 + Math.cos(angle + Math.PI / 2)
								* size),
						(int) Math.round(x1 + Math.cos(angle + Math.PI / 2)
								* size) },
				new int[] {
						(int) Math.round(y1),
						(int) Math.round(y2),
						(int) Math.round(y2 - Math.sin(angle + Math.PI / 2)
								* size),
						(int) Math.round(y1 - Math.sin(angle + Math.PI / 2)
								* size) }, 4);
		pythagoreanTree(g, iter - 1, x1 + Math.cos(angle + Math.PI / 2) * size, x1 + Math.cos(angle + Math.PI / 2) * size + Math.cos(angle + ang) * Math.sqrt(size * size / 4 + Math.pow(Math.tan(ang) * (size / 2), 2)), y1 - Math.sin(angle + Math.PI / 2) * size, y1 - Math.sin(angle + Math.PI / 2) * size - Math.sin(angle + ang) * Math.sqrt(size * size / 4 + Math.pow(Math.tan(ang) * (size / 2), 2)), ang);
		pythagoreanTree(g, iter - 1, x1 + Math.cos(angle + Math.PI / 2) * size + Math.cos(angle + ang) * Math.sqrt(size * size / 4 + Math.pow(Math.tan(ang) * (size / 2), 2)), x2 + Math.cos(angle + Math.PI / 2) * size, y1 - Math.sin(angle + Math.PI / 2) * size - Math.sin(angle + ang) * Math.sqrt(size * size / 4 + Math.pow(Math.tan(ang) * (size / 2), 2)), y2 - Math.sin(angle + Math.PI / 2) * size, ang);
	}
	
	private void binaryTree(Graphics g, int iter, double x, double y, double angle, double size, double ang)
	{
		if (iter == 0)
		{
			return;
		}
		
		g.drawLine((int) Math.round(x), (int) Math.round(y), (int) Math.round(x + Math.cos(angle) * size), (int) Math.round(y - Math.sin(angle) * size));
		binaryTree(g, iter - 1, (int) Math.round(x + Math.cos(angle) * size), (int) Math.round(y - Math.sin(angle) * size), angle + ang, size / 2, ang);
		binaryTree(g, iter - 1, (int) Math.round(x + Math.cos(angle) * size), (int) Math.round(y - Math.sin(angle) * size), angle - ang, size / 2, ang);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			iterations[fractalNum]++;
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			iterations[fractalNum]--;
			iterations[fractalNum] = Math.max(0, iterations[fractalNum]);
		}
		else if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			n[fractalNum]++;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			n[fractalNum]--;
			n[fractalNum] = Math.max(0, n[fractalNum]);
		}
		else if (e.getKeyCode() == KeyEvent.VK_A)
		{
			fractalNum--;
			fractalNum = Math.max(0, fractalNum);
		}
		else if (e.getKeyCode() == KeyEvent.VK_D)
		{
			fractalNum++;
			fractalNum = Math.min(fractalNum, MAX_FRACTAL);
		}

		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}
}