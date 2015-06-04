import javax.swing.JFrame;

public class FractalFrame extends JFrame
{
	FractalPanel p;
	
	public FractalFrame()
	{
		super("Frame");
		p = new FractalPanel(); 
		
		setContentPane(p);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args)
	{
		FractalFrame frame = new FractalFrame();
		frame.pack();
		frame.setVisible(true);
	}
}