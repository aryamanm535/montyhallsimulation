import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class montyhall implements ActionListener
{
    boolean firstClick = true;
	char[] check = new char[3];
	JButton[] cells = new JButton[3];
    JTextField[] counters = new JTextField[6];
    Random r = new Random();
    int correctChoice = r.nextInt(3);
    int hint = 0;
    int picked = 0;
    int stayedRight;
    int stayedIncorrect;
    int changedRight;
    int changedIncorrect;

	montyhall()
	{
        stayedRight = 0;
        stayedIncorrect = 0;
        changedRight = 0;
        changedIncorrect = 0;
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(1, 1));
		
		JPanel box = new JPanel(new GridLayout(1,3));
		box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JPanel count = new JPanel(new GridLayout(2,3));
        count.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		for (int i=0; i<3; i++) 
		{
			cells[i] = new JButton();
			cells[i].setBackground(Color.cyan);
			cells[i].setHorizontalAlignment(JTextField.CENTER);
			cells[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, false));
            cells[i].addActionListener(this);
			box.add(cells[i]);
		}
        
        for (int i=0; i<6; i++) 
        {
            counters[i] = new JTextField();
            counters[i].setHorizontalAlignment(JTextField.CENTER);
            counters[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, false));
            counters[i].setEditable(false);
            counters[i].setFont(new Font("Times New Roman",Font.BOLD,20));
            count.add(counters[i]);
        }
        counters[0].setText("Kept Choice");
        counters[3].setText("Changed Choice");
        counters[1].setBackground(Color.green);
        counters[4].setBackground(Color.green);
        counters[2].setBackground(Color.red);
        counters[5].setBackground(Color.red);
       
        
		
		board.add(box);
        board.add(count);
		
		JFrame frame = new JFrame();
		frame.setSize(800, 800);

		Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(board);
        cp.add(count, BorderLayout.NORTH);

        frame.setTitle("Monty Hall Problem");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
	}
    void hint()
    {
        for(int i=0; i<3; i++)
            if(i!=picked && i!=correctChoice)
            {
                hint = i;
                cells[i].setBackground(Color.red);
                break;
            }
    }
    
    void reset()
    {
        for(int i=0; i<3; i++)
            cells[i].setBackground(Color.cyan);
        firstClick = true;
        Random r = new Random();
        correctChoice = r.nextInt(3);
        hint = 0;
        picked = 0;
    }
    
    public static void main(String[] args) 
    {
        montyhall hall1 = new montyhall();
    }
    
    void counter(boolean stayedCorrect, boolean changedCorrect, boolean stayedWrong, boolean changedWrong)
    {
        
        if(stayedCorrect)
        {
            stayedRight += 1;
            double a = stayedRight+stayedIncorrect;
            double b = stayedRight/a;
            double c = b*100;
            double d = 100-c;
            System.out.println(c+" "+d);
            String s1 = String.valueOf(c) + "%";
            String s2 = String.valueOf(d) + "%";
            counters[1].setText(s1);
            counters[2].setText(s2);
        }
        if(changedCorrect)
        {
            changedRight += 1;
            double a = changedRight+changedIncorrect;
            double b = changedRight/a;
            double c = b*100;
            double d = 100-c;
            System.out.println(c+" "+d);
            String s1 = String.valueOf(c) + "%";
            String s2 = String.valueOf(d) + "%";
            counters[4].setText(s1);
            counters[5].setText(s2);
        }
        if(stayedWrong)
        {
            stayedIncorrect += 1;
            double a = stayedRight+stayedIncorrect;
            double b = stayedIncorrect/a;
            double c = b*100;
            double d = 100-c;
            System.out.println(c+" "+d);
            String s1 = String.valueOf(c) + "%";
            String s2 = String.valueOf(d) + "%";
            counters[2].setText(s1);
            counters[1].setText(s2);
        }
        if(changedWrong)
        {
            changedIncorrect += 1;
            double a = changedRight+changedIncorrect;
            double b = changedIncorrect/a;
            double c = b*100;
            double d = 100-c;
            System.out.println(c+" "+d);
            String s1 = String.valueOf(c) + "%";
            String s2 = String.valueOf(d) + "%";
            counters[5].setText(s1);
            counters[4].setText(s2);
        }    
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(firstClick)
        {
            for(int i=0; i<3; i++)
                if(e.getSource()==cells[i])
                {
                    cells[i].setBackground(Color.yellow);
                    picked = i;
                }
            firstClick = false;
            hint();
        }
        else
        {
            if(e.getSource() != cells[hint])
            {
                if(e.getSource()==cells[correctChoice])
                {
                    cells[correctChoice].setBackground(Color.green);
                    if (e.getSource() == cells[picked])
                    {
                        cells[3-hint-picked].setBackground(Color.red);
                        counter(true, false, false, false);
                    }
                    else
                    {
                        cells[picked].setBackground(Color.red);
                        counter(false, true, false, false);
                    }
                    
                }
                
                else
                {
                    for(int i=0; i<3; i++)
                        if(e.getSource()==cells[i])
                            cells[i].setBackground(Color.red);
                    cells[correctChoice].setBackground(Color.green);
                    if (e.getSource() == cells[picked])
                        counter(false, false, true, false);
                    else
                        counter(false, false, false, true);
                    
                }
                
                try
                    {
                        Thread.sleep(50);   
                    }
                    catch(InterruptedException ex)
                    {
                        ex.printStackTrace();
                    }
                    reset();
            }
            
        }
    }
}