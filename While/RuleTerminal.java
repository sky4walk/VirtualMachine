//written by André Betz 
//http://www.andrebetz.de

class RuleTerminal extends RuleElement
{
	RuleTerminal(String Token,RuleElement re){
		super(Token,re);
	}
	public boolean IsTerminal(){
		return true;
	}
}