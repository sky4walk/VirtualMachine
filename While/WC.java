//written by André Betz 
//http://www.andrebetz.de

// WC is a Compiler for the language WHILE
// which generates Assembler Code for the 
// virtual machine VM
import java.io.*;

class WC
{
	LRTableGen m_LR;
	LLParser m_Parser;
	String m_Data;
	
	WC(){
		String Language = LoadBNF("While.bnf");
		
		BNFReader Rules = new BNFReader();
		Rules.Init(Language);
		
		BNFTree SynthaxTree = new BNFTree(Rules.GetRules());
		SynthaxTree.GenerateTree();
		
		LRTableGen generator = new LRTableGen();
		generator.Init(Rules.GetRules());
		
		m_Parser = new LLParser();
	}
	
	private String LoadBNF(String FileName) {
		StringBuffer readinput = new StringBuffer();
		if(FileName==null){
			return null;
		}
		try {
			File f = new File(FileName);
			FileReader in = new FileReader(f);
			char[] buffer = new char[128];
			int len;
			while((len = in.read(buffer))!=-1) {
				readinput.append(new String(buffer,0,len));
			}
		}
		catch(IOException e) {
			return null;
		}
		return readinput.toString();  	
	}
	public String Compile(String Filename){
		PreProcess preprocessor = new PreProcess(Filename);
		preprocessor.Start();
		m_Data = preprocessor.GetDatei(); 
		LexScanner scan = new LexScanner(m_Data);
		ScanElement se = scan.Scan();
		String Code = m_Parser.Parse(se);
		return Code;
	}
	public void OutPut(){
	}
	
	public static void main(String[] args) {
		WC Compiler = new WC();
		if(args.length!=1){
			System.exit(0);
		}
		String outComp = Compiler.Compile(args[0]);
		System.out.print(outComp);
	}
}