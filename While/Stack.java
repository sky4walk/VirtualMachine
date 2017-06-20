//written by André Betz 
//http://www.andrebetz.de

public class Stack {
	Element First;
	Element Last;
	
	Stack()
	{
		Clear();
	}
	void Add(Object obj)
	{
		if(First==null){
			First = new Element(obj);
			Last = First;
		}else{
			Last.set_Next(new Element(obj));
			Last = Last.get_Next();
		}
	}
	void Clear()
	{
		First = null;
		Last = null;
	}
	boolean IsEmpty()
	{
		if(Last==null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	Object GetOut(){
		Element Out = Last;
		if(Last!=null){
			Last = Last.get_Last();
		}
		if(Out!=null){
			return Out.get_value();
		}else{
			return null;
		}
	}
}
