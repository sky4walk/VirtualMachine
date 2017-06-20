//written by André Betz 
//http://www.andrebetz.de

class LRTableGen
{
	MyList FirstSet;
	MyList Rules;
	
	LRTableGen(){
		FirstSet = new MyList();
	}
	
	public void Init(MyList BNF){
		Rules = BNF;
		ConnectAlternatives();
		ConnectTokens();
		GenFIRSTSet();
		GenFOLLOWSet();
	}
	
	private void GenFOLLOWSet(){
	}
	
	private MyList FindFollowSet(RuleStart rs){
		MyList Standings = new MyList();
		Standings = FindTokenInsideRulesList(rs.GetToken());
		Standings.SetFirst();
		for(int i=0;i<Standings.GetLength();i++){
			MyList actLst = (MyList)Standings.GetAct();
			actLst.SetFirst();
			for(int j=0;i<actLst.GetLength();i++){
				RuleElement re = (RuleElement)actLst.GetAct();
				actLst.SetNext();
			}
			Standings.SetNext();
		}
		return null;
	}
	
	private void GenFIRSTSet(){
		int len = Rules.GetLength();
		for(int i=0;i<len;i++){
			BNFRule bnf = (BNFRule)Rules.GetAt(i);
			RuleStart rs = bnf.GetStart();
			if(rs.GetAlternateBack()==null){
				MyList first = FindFirstSet(rs,true);
				bnf.SetFirstSet(first);
			}
		}
	}
	
	private MyList FindFirstSet(RuleStart rs,boolean LastOne){
		MyList FirstLst = new MyList();
		while(rs!=null)
		{
			boolean TerminalFound = false;
			boolean LastToken = false;
			RuleElement first_re = rs.GetNext();
			while(!TerminalFound && first_re!=null)
			{				
				if(first_re.GetNext()==null){
					LastToken = true;
				}else{
					LastToken = false;
				}
				if(first_re.IsTerminal()){
					String Token = first_re.GetToken();
					if(Token.length()>0){
						FirstLst.Add(first_re);
						TerminalFound = true;
					}else{
						if(LastOne&&LastToken){
							FirstLst.Add(first_re);
							TerminalFound = true;
						}else{
						}
					}
				}else{
					RuleStart next_rs = ((RuleToken)first_re).GetConnected();
					if(next_rs!=null){
						MyList resLst = FindFirstSet(next_rs,LastOne&&LastToken);
						FirstLst.AddList(resLst);
					}
				}
				first_re = first_re.GetNext();
			}
			rs = rs.GetAlternate();
		}		
		return FirstLst;
	}
		
	private void ConnectAlternatives()
	{
		int len = Rules.GetLength();
		for(int i=0;i<len;i++){
			RuleStart rs = ((BNFRule)Rules.GetAt(i)).GetStart();
			if(rs.GetAlternate()==null){
				String Token = rs.GetToken();
				MyList sameTokens = FindRules(Token);
				int flen = sameTokens.GetLength();
				sameTokens.SetFirst();
				for(int j=1;j<flen;j++){
					RuleStart rs1 = (RuleStart)sameTokens.GetAt(j-1);
					RuleStart rs2 = (RuleStart)sameTokens.GetAt(j);
					rs1.SetAlternate(rs2);
				}
			}
		}
	}
	
	private void ConnectTokens(){
		int len = Rules.GetLength();
		for(int i=0;i<len;i++){
			RuleStart rs = ((BNFRule)Rules.GetAt(i)).GetStart();
			RuleElement re = rs.GetNext();
			while(re!=null)
			{
				if(!re.IsTerminal()){
					RuleToken rt = (RuleToken)re;
					MyList tl = FindRules(re.GetToken());
					if(tl.GetLength()>0){
						RuleStart con_rs = (RuleStart)tl.GetAt(0);
						rt.SetConnected(con_rs);
					}else{
						// error:
						// jedes NichtTerminal muss eine Regel besitzen
					}
				}
				re = re.GetNext();
			}
		}		
	}
	
	private MyList FindTokenInsideRules(String Token){
		MyList sr = new MyList();
		int len = Rules.GetLength();
		Rules.SetFirst();
		for(int i=0;i<len;i++){
			BNFRule search = (BNFRule)Rules.GetAct();
			if(search!=null){
				MyList tk = new MyList();
				tk = search.FindToken(Token);
				if(tk.GetLength()>0){
					sr.Add(search.GetStart());
				}
			}
			Rules.SetNext();
		}
		return sr;
	}
	
	private MyList FindTokenInsideRulesList(String Token){
		MyList sr = new MyList();
		int len = Rules.GetLength();
		Rules.SetFirst();
		for(int i=0;i<len;i++){
			BNFRule search = (BNFRule)Rules.GetAct();
			if(search!=null){
				MyList tk = new MyList();
				tk = search.FindToken(Token);
				if(tk.GetLength()>0){
					sr.Add(tk);
				}
			}
			Rules.SetNext();
		}
		return sr;
	}
		
	private MyList FindRules(String Token){
		MyList sr = new MyList();
		int len = Rules.GetLength();
		Rules.SetFirst();
		for(int i=0;i<len;i++){
			BNFRule search = (BNFRule)Rules.GetAct();
			if(search!=null){
				if(search.GetStart().GetToken().equals(Token)){
					sr.Add(search.GetStart());
				}
			}
			Rules.SetNext();
		}
		return sr;
	}
}