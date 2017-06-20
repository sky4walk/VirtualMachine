//written by André Betz 
//http://www.andrebetz.de


class BNFRule
{
	RuleStart Start;
	RuleElement act;
	MyList FirstSet;
	
	BNFRule(){
		Start = null;
		act = null;
	}
	public RuleStart GetStart(){
		return Start;
	}
	public MyList FindToken(String Token){
		MyList sr = new MyList();
		if(Start!=null)
		{
			RuleStart rs = Start;
			while(rs!=null){
				RuleElement pos = rs.GetNext();
				while(pos!=null){
					if(pos.GetToken().equals(Token)){
						sr.Add(pos);
					}
					pos = pos.GetNext();
				}
				rs = rs.GetConnected();
			}
		}
		return sr;
	}
	public MyList GetFirstTerminals(){
		MyList ts = new MyList();
		if(Start!=null)
		{
			RuleElement pos = Start.GetNext();
			while(pos!=null){
				if(pos.IsTerminal()){
					ts.Add(pos);
				}
				pos = pos.GetNext();
			}
		}
		return ts;
	}
	
	public void SetStart(String Token){
		Start = new RuleStart(Token,null,this);
		act = Start;
	}
	public void SetNextToken(String Token,boolean Terminal){
		if(Terminal){
			act.SetNext(new RuleTerminal(Token,Start));
		}else{
			act.SetNext(new RuleToken(Token,Start));
		}
		act = act.GetNext();
	}
	public void SetFirstSet(MyList set){
		FirstSet = set;
	}
	public MyList GetFirstSet(){
		return FirstSet;
	}
}