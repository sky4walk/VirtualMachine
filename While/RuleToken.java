//written by André Betz 
//http://www.andrebetz.de

class RuleToken extends RuleElement
{
	RuleStart Connected;
	
	RuleToken(String Token,RuleElement re){
		super(Token,re);
		Connected = null;
	}
	
	public void SetConnected(RuleStart rs){
		Connected = rs;
	}
	public RuleStart GetConnected(){
		return Connected;
	}
}