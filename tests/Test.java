

public class Test
{
	public static void main(String[] args)
	{
		while(true)
		{
			RollParser rp = new RollParser();
			rp.parseRoll(System.console().readLine());
			
			if(rp.status)
				System.out.println(rp.result);
			else
				System.out.println("Parsing failed at " + rp.cur_pos);	
		}
	}
}
