package Server;

import java.util.Random;



/**
* <h1> RollParser </h1>
* Class for parsing strings with dice rolls and modifiers
* The notation is following:
* Ndk means to roll a k-sided die N times and return the sum.
* d% means d100.
* Adk means to roll a k-sided die with advantage (roll twice, choose highest).
* Ddk means to roll a k-sided die with disadvantage (roll twice, choose lowest).
* Adding and substracting integers also supported.
* 
* <p>
* <i>Example:</i> 
* d% + 2d10 + Ad6 + Dd4 - 4
* <p>
* This will be parsed as following: first, roll a 100-sided die (i.e. result is 57), add two rolls of 10-sided die (let it be 7 and 4, total 11),
* add a roll of a 6-sided die with advantage (1, 5, so we choose 5), a roll of 4-sided die with disadvantage (2, 4, so we choose 2) and finally subtract 4.
* The result will be 57 + 11 + 5 + 2 - 4 = 71
* <p>
* The class will try to parse the string, if it's OK, it sets status flag to true and result - to total roll result; otherwise status is set to false;
* 
* @author viviaxenov
* @since 2019-04-11
* 
*/
public class RollParser
{



	private String roll_str;
	private char[] s;
	private int cur_pos;
	public int result;
	public boolean status;

	public RollParser()
	{
		roll_str = "";
		cur_pos = 0;
		result = 0;
		status = true;
	}

	/**
	* Parses a roll string
	* If parsed, status is set to true, result - to total result of the roll, 
	* else status is set to false
	* @param roll_string String to parse
	*/
	public void parseRoll(String roll_string)
	{
		roll_str = roll_string.replaceAll("\\s", "");
		roll_str = roll_str.replace("-", "+-");
		if(roll_str.charAt(0) == '+')
			roll_str = roll_str.substring(1);

		s = roll_str.toCharArray();
		cur_pos = 0;
		result = 0;
		status = true;

		this.result = this.getExpression();
	}


	private int getExpression()
	{
		int left_oper = this.getTerm();
		if(!status)
			return -1;
		if(cur_pos == roll_str.length() || s[cur_pos] == '\n')
			return left_oper;
		if(!(s[cur_pos] == '+' || s[cur_pos] == '-'))
		{
			status = false;
			return -1;
		}

		if(s[cur_pos] != '+')
		{
			status = false;
			return -1;
		}
		cur_pos++;
		int right_oper = this.getExpression();
		if(!status) return -1;
		
		

		return left_oper + right_oper;
	}

	private int getTerm()
	{
		int res = this.getDie();
		if(status)
			return res;
		res = this.getNum();
		if(status)
			return res;
		return -1;
		
	}

	private int getDie()
	{

		int n_dice, n_sides;
		char tp = 'd';
		// finding d separator	
		int d_pos = roll_str.indexOf('d', cur_pos);
		if(d_pos == -1)
		{
			status = false;
			return -1;
		}

		// parsing part before sep;
		// checking if it's advantage or disadvantage die first
		// or it may be just d6 (starting right with d)
		if(!Character.isDigit(s[cur_pos]))
		{
			if(d_pos - cur_pos > 1 || (!(s[cur_pos] == 'A' || s[cur_pos] == 'd' || s[cur_pos] == 'D')))
			{
				status = false;
				return -1;
			}	
			n_dice = 1;
			tp = s[cur_pos];
		}	
		// else trying to get number of dice
		else
		{
			String left_part = roll_str.substring(cur_pos, d_pos);
			try
			{
				n_dice = Integer.parseInt(left_part);
			}
			catch(NumberFormatException e)
			{
				status = false;
				return -1;
			}
		}
		
		// parcing the part after d
		int end_pos = d_pos + 1;
		if(end_pos >= roll_str.length())
		{
			status = false;
			return -1;
		}
		// d% == d100, parcing this variant first
		if(s[end_pos] == '%')
		{
			n_sides = 100;	
			end_pos++;
		}
		else
		{
			while(end_pos < roll_str.length() && Character.isDigit(s[end_pos]))
				end_pos++;
			try
			{
				n_sides = Integer.parseInt(roll_str.substring(d_pos + 1, end_pos));
			}
			catch(NumberFormatException e)
			{
				status = false;
				return -1;
			}
		}
		
		cur_pos = end_pos;
		status = true;
		return rollDie(tp, n_dice, n_sides);

	}

	private int getNum()
	{
		int end_pos = cur_pos;
		int num = 0;
		while(end_pos < roll_str.length() && (Character.isDigit(s[end_pos]) || s[end_pos] == '-'))
			end_pos++;
		try
		{
			num = Integer.parseInt(roll_str.substring(cur_pos, end_pos));
		}
		catch(NumberFormatException e)
		{
			status = false;
			return -1;
		}
		cur_pos = end_pos;
		status = true;
		return num;
		
	}


	private static int rollDie(char tp, int n_dice, int n_sides)
	{
		Random r = new Random();
		int total = 0;
		switch(tp)
		{
			case 'd':
				for(int i = 0; i < n_dice; i++)
				{
					total += 1 + r.nextInt(n_sides);
				}
				break;
			case 'A':
				int r1 = 1 + r.nextInt(n_sides);
				int r2 = 1 + r.nextInt(n_sides);
				total = (r1 > r2) ? r1 : r2;
				break;
			case 'D':
				r1 = 1 + r.nextInt(n_sides);
				r2 = 1 + r.nextInt(n_sides);
				total = (r1 < r2) ? r1 : r2;
				break;
				
		}
		return total;
	}

}
