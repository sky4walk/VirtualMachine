//written by André Betz 
//http://www.andrebetz.de

public class LexScanner {
	char H[] = {'0','1','2','3','4','5','6','7','8','9',
			 	'a','b','c','d','e','f','A','B','C','D','E','F'};
	char S[] =	{'.','>','<','+','-','*','/','%','&','^','|','=',
				'!',';','{','}',',',':','?','(',')','[',']','~'};
	char D[] =  {' ','\b','\t','\n','\f','\r'};
	char fs[] = {'f','F','l','L'};
	char is[] = {'u','U','l','L'};

	String[] sw =	{"auto","break","case","char","continue",
					"default","do","else","enum","float","for",
					"goto","if","int","return","uint","sizeof",
			 		"struct","switch","typedef","union","void","while",};
	
	String m_Data;
	int m_Pos;
	ScanElement m_ScanErg;
	ScanElement m_Actual;
	
	LexScanner(String Data){
		m_Data = Data;
		m_Pos = 0;
		m_ScanErg = null;
		m_Actual = null;
	}
	
	private void SetNextScanElement(int Type,String value){
		if(m_Actual==null){
			m_Actual = new ScanElement(Type,value);
			m_ScanErg = m_Actual;
		}else{
			m_Actual.setM_Next(new ScanElement(Type,value));
			m_Actual = m_Actual.getM_Next();			
		}
	}
	private char GetChar(){
		if(m_Data.length()<=m_Pos){
			return 0;
		}
		char a = m_Data.charAt(m_Pos);
		m_Pos++;
		return a;
	}
	private char GetNextChar(){
		if(m_Data.length()<=(m_Pos+1)){
			return 0;
		}
		char a = m_Data.charAt(m_Pos+1);
		return a;
	}
	
	private char const_lit()
	{
		String value = "";
		char t;
		int i = 1;
		while(i!=0)
		{
			t = GetChar();
			value += t;
			if	((i==1)&&(t=='\\'))     i = 2;
			else if ((i==1)&&(t=='\'')) i = 0;
			else if (i==2)				i = 1;
		}
		t = GetChar();
		SetNextScanElement(ScanElement.CONSTANT,value);
		return t;
	}
	
	private char string_lit()
	{
		char t;
		String value = "";
		int i = 1;
		while(i!=0)
		{
			t = GetChar();
			value += t;
			if	((i==1)&&(t=='\\'))     i = 2;
			else if ((i==1)&&(t=='\"')) i = 0;
			else if (i==2)              i = 1;
		}
		t = GetChar();
		SetNextScanElement(ScanElement.STRING_LITERAL,value);
		return t;
	}
	
	private char name(char t)
	{
		String value = "";
		int Type = -1;
		int x,i=0;
		while(ParseHelper.IsAlphaNumeric(t))
		{
			value += t;
			t = GetChar();		
		}
		if(ParseHelper.DoesContain(value,sw)!=-1){
			Type = ScanElement.VARIABLE;
		}else{
			Type = ScanElement.IDENTIFIER;
		}
	
		SetNextScanElement(Type,value);
		return t;
	}
	
	private char constant(char t,int z)
	{
		String value = "";
		int Type = -1;
		while(z!=0)
		{
			if(z==1)
			{
				if(t=='0')
				{
					z = 2;
					value += t;
					t = GetChar();
				}
				else
				{
					z = 3;
					value += t;
					t = GetChar();
				}
			}
			else if(z==2)
			{
				if(ParseHelper.IsNumerical(t))
				{
					z = 3;
					value += t;
					t = GetChar();
				}
				else if((t=='x')||(t=='X'))
				{
					z = 4;
					value += t;
					t = GetChar();
				}
				else if(t=='.')
				{
					z = 7;
					value += t;
					t = GetChar();
				}
				else if((t=='e')||(t=='E'))
				{
					z = 5;
					value += t;
					t = GetChar();
				}
				else
				{
					z = 0;
				}
			}
			else if(z==3)
			{
				if(ParseHelper.IsNumerical(t))
				{
					z = 3;
					value += t;
					t = GetChar();
				}
				else if(ParseHelper.DoesContain(t,is)!=-1)
				{
					z = 0;
					Type = ScanElement.CONSTANT_INT;
					value += t;
					t = GetChar();
				}
				else if(t=='.')
				{
					z = 7;
					value += t;
					t = GetChar();
				}
				else if((t=='e')||(t=='E'))
				{
					z = 5;
					value += t;
					t = GetChar();
				}                                                                                                                                                                                                                                                                                                                         
				else
				{
					z = 0;
				}
			}
			else if(z==4)
			{
				if(ParseHelper.DoesContain(t,H)!=-1)
				{
					z = 4;
					value += t;
					t = GetChar();
				}
				else if(ParseHelper.DoesContain(t,is)!=-1)
				{
					z = 0;
					value += t;
					t = GetChar();
				}
				else
				{
					z = 0;
				}
			}
			else if(z==5)
			{
				z = 6;
				if((t=='+')||(t=='-'))
				{
					value += t;
					t = GetChar();	
				}
			}
			else if(z==6)
			{
				if(ParseHelper.IsNumerical(t))
				{
					z = 6;
					value += t;
					t = GetChar();
				}
				else if(ParseHelper.DoesContain(t,fs)!=-1)
				{
					z = 0;
					value += t;
					t = GetChar();
				}
				else
				{
					z = 0;
				}
			}
			else if(z==7)
			{
				if(ParseHelper.IsNumerical(t))
				{
					z = 7;
					value += t;
					t = GetChar();
				}
				else if(ParseHelper.DoesContain(t,fs)!=-1)
				{
					z = 0;
					value += t;
					t = GetChar();
				}
				else if((t=='e')||(t=='E'))
				{
					z = 5;
					value += t;
					t = GetChar();
				}
				else
				{
					z = 0;
				}
			}
			else if(z==8)
			{
				value += t;
				if(ParseHelper.IsNumerical(t))
				{
					z = 9;
					value += t;
					t = GetChar();
				}
				else if((t=='e')||(t=='E'))
				{
					z = 5;
					value += t;
					t = GetChar();
				}
				else if(ParseHelper.DoesContain(t,fs)!=-1)
				{
					z = 0;
					value += t;
					t = GetChar();
				}
				else
				{
					z = 0;
				}
			}
			else if(z==9)
			{
				if(ParseHelper.IsNumerical(t))
				{
					z = 9;
					value += t;
					t = GetChar();
				}
				else if((t=='e')||(t=='E'))
				{
					z = 5;
					value += t;
					t = GetChar();
				}
				else if(ParseHelper.DoesContain(t,fs)!=-1)
				{
					z = 0;
					value += t;
					t = GetChar();
				}
				else
				{
					z = 0;
				}
			}
		}
		SetNextScanElement(Type,value);
		return t;
	}
	
	private char oper(char t)
	{	
		/* finit automata */
		String value = "";
		int Type = -1;
		int i;
		if(t=='.')
		{
			t = GetChar();
			if(t=='.')
			{
				t = GetChar();
				if(t=='.') /* ... */
				{
					value = "...";
					t = GetChar();
				}
			}
			else if(ParseHelper.IsNumerical(t)) /* .Digit */
			{
				t = constant(t,8);			
			}
			else /* . */
			{
				value = ".";
			}
		}	
		else if(t=='/') 
		{
			t = GetChar();
			if(t=='*') /* Comment */
			{
				i = 0;
				while(i<2)
				{
					t = GetChar();	
					if(i==0)
					{
						if(t=='*') 	i++;
						else		i=0;
					}
					else if(i==1)
					{
						if(t=='/')	i++;                                
						else	 	i=0;
					}
				}
				t = GetChar();
			}
			else if(t=='=') /* /= */
			{
				value = "/=";
				t = GetChar();
			}
			else /* / */
			{
				value = "/";
			}               
		}
		else if(t=='<')
		{
			t = GetChar();
			if(t=='<')
			{
				t = GetChar();
				if(t=='=') /* <<= */
				{
					value = "<<=";
					t = GetChar();	
				}
				else /* << */
				{
					value = "<<";
				}
			}
			else if(t=='=')	/* <= */
			{
				value = "<=";
				t = GetChar();
			}
			else
			{
				value = "<";
			}
		}
		else if(t=='>')
		{
			t = GetChar();
			if(t=='>')
			{
				t = GetChar();
				if(t=='=') /* >>= */
				{
					value = ">>=";
					t = GetChar();
				}
				else /* >> */
				{
					value = ">>";
				}
			}
			else if(t=='=') /* >= */
			{
				value = ">=";
				t = GetChar();
			}
			else
			{
				value = ">";
			}
		}
		else if(t=='-')
		{
			t = GetChar();
			if(t=='-') /* -- */
			{
				value = "--";
				t = GetChar();
			}
			else if(t=='>') /* -> */
			{
				value = "->";
				t = GetChar();
			}
			else if(t=='=') /* -= */
			{
				value = "-=";
				t = GetChar();
			}
			else /* - */
			{
				value = "-";
			}
		}                    
		else if(t=='+')
		{
			t = GetChar();
			if(t=='+') /* ++ */
			{
				value = "++";
				t = GetChar();
			}
			else if(t=='=') /* += */
			{
				value = "+=";
				t = GetChar();
			}
			else
			{
				value = "+";
			}
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
		else if(t=='*')
		{
			t = GetChar();
			if(t=='=') /* *= */
			{
				t = GetChar();
				value = "*=";
			}
			else /* * */
			{
				value = "*";
			}
		}                                      
		else if(t=='%')
		{
			t = GetChar();
			if(t=='=') /* %= */
			{
				value = "%=";
				t = GetChar();
			}
			else /* % */
			{
				value = "%";
			}
		}
		else if(t=='&')
		{
			t = GetChar();
			if(t=='=') /* &= */
			{
				value = "&=";
				t = GetChar();
			}
			else if(t=='&') /* && */
			{
				value = "&&";
				t = GetChar();
			}
			else /* & */
			{
				value = "&";
			}
		}	            
		else if(t=='^')
		{
			t = GetChar();
			if(t=='=') /* ^= */
			{
				value = "^=";
				t = GetChar();
			}
			else /* ^ */
			{
				value = "^";
			}
		}      
		else if(t=='|')
		{
			t = GetChar();
			if(t=='=') /* |= */
			{
				value = "|=";
				t = GetChar();
			}
			else if(t=='|') /* || */
			{
				value = "||";
				t = GetChar();
			}
			else /* | */
			{
				value = "|";
			}
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                             	
		else if(t=='!')
		{
			t = GetChar();
			if(t=='=')
			{
				value = "!=";
				t = GetChar();
			}
			else
			{
				value = "!";
			}
		}
		else if(t=='=')
		{
			t = GetChar();
			if(t=='=')
			{
				value = "==";
				t = GetChar();
			}
			else
			{
				value = "=";
			}
		}
		else if(t==';')
		{
			t = GetChar();
			value = ";";
		}	
		else if(t=='?')
		{
			t = GetChar();
			value = "?";
		}
		else if(t=='~')
		{
			t = GetChar();
			value = "~";
		}
		else if(t=='{')
		{
			t = GetChar();
			value = "{";
		}
		else if(t=='}')
		{
			t = GetChar();
			value = "}";
		}
		else if(t==',')
		{
			t = GetChar();
			value = ",";
		}
		else if(t==':')
		{
			t = GetChar();
			value = ":";
		}                                                        
		else if(t=='(')
		{
			t = GetChar();
			value = "(";
		}
		else if(t==')')
		{
			t = GetChar();
			value = ")";
		}
		else if(t=='[')
		{
			t = GetChar();
			value = "[";
		}
		else if(t==']')
		{
			t = GetChar();
			value = "]";
		}
		else
		{
			t = GetChar();
		}
		SetNextScanElement(Type,value);
		return t;
	}
	
	public ScanElement Scan(){
		char t; 
		m_ScanErg = null;
		t = GetChar();
		while(t!='\0')
		{
			if (ParseHelper.DoesContain(t,D)!=-1)		t = GetChar();
			else if (t=='\'')							t = const_lit();
			else if (t=='\"')							t = string_lit();
			else if	(ParseHelper.IsNumerical(t))		t = constant(t,1);
			else if	(ParseHelper.IsAlpha(t))			t = name(t);
			else if	(ParseHelper.DoesContain(t,S)!=-1)	t = oper(t);
			else										t = '\0';
		}
		return m_ScanErg;
	}
}
