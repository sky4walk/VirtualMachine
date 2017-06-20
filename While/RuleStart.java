//written by André Betz 
//http://www.andrebetz.de

class RuleStart extends RuleToken
{
	RuleStart AlternateRule;
	RuleStart AlternateRuleBack;
	BNFRule RuleConnect;
	
	RuleStart(String Token,RuleElement re,BNFRule back){
		super(Token,re);
		AlternateRule = null;
		AlternateRuleBack = null;
		RuleConnect = back;
	}
	public RuleStart GetAlternate(){
		return AlternateRule;
	}
	public void SetAlternate(RuleStart alternate){
		AlternateRule = alternate;
		if(alternate!=null){
			alternate.SetAlternateBack(this);
		}
	}
	public RuleStart GetAlternateBack(){
			return AlternateRuleBack;
	}
	public void SetAlternateBack(RuleStart alternate){
		AlternateRuleBack = alternate;
	}
	public BNFRule GetRuleConnect(){
		return RuleConnect;
	}
}