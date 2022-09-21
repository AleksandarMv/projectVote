package model;

public abstract class Persona {
	//@ public invariant DataDiNascita.length == 3 || DataDiNascita == null; 
	//@ public invariant (NazioneDiNascita == "IT" <==> ComuneDiNascita != ""); 
	//@ public invariant (Sesso == 'M' || Sesso == 'F');	
	//@ public invariant (DataDiNascita[2] <= 2021 && DataDiNascita[1] <= 11 && DataDiNascita[0] <= 10) || DataDiNascita == null;

	protected /*@ spec_public @*/ String Nome;
	
	protected /*@ spec_public @*/ String Cognome;

	protected /*@ spec_public @*/ int[] DataDiNascita;

	protected /*@ spec_public @*/ String ComuneDiNascita;
	
	protected /*@ spec_public @*/ String NazioneDiNascita;

	protected /*@ spec_public @*/ char Sesso;
	
	protected /*@ spec_public @*/ char[] CodiceFiscale;

	public Persona(String n, String c, int[] ddn, String ndn, String cdn ,char s, char[] cf){
		
		Nome = n;
		Cognome = c;
		DataDiNascita = new int[]{ddn[0] , ddn[1], ddn[2]};
		NazioneDiNascita = ndn;
		if (NazioneDiNascita.equals("IT")){
			ComuneDiNascita = cdn;
		}else{
			ComuneDiNascita = "";
		}
		Sesso = s;
		if(cfauthenticator(cf)){
			CodiceFiscale = cf;
		
		}
		else{
			CodiceFiscale = new char[] {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
	
		}
	}
	public Persona(){
		Nome = null;
		Cognome = null;
		DataDiNascita = null;
		NazioneDiNascita = null;
		ComuneDiNascita = null;
		CodiceFiscale = null;
	}
	//metodi set && get
	public void setNome(String s){Nome = s;}
	public void setCognome(String s){Cognome = s;}
	public void setDataDiNascita(int[] a){DataDiNascita = a;}
	public void setNazioneDiNascita(String s){NazioneDiNascita = s;}
	public void setComuneDiNascita(String s){if(NazioneDiNascita.equals("IT")){ComuneDiNascita = s;}}
	public void setCodiceFiscale(char[] a){CodiceFiscale = a;}
	public void setSesso(char s){if(s=='M' || s=='F') Sesso = s;}
	
	public String getNome(){return new String(Nome);}
	public String getCognome(){return new String(Cognome);}
	public int[] getDataDiNascita(){return DataDiNascita.clone();}
	public String getNazioneDiNascita(String s){return new String(NazioneDiNascita);}
	public String getComuneDiNascita(String s){return new String(ComuneDiNascita);}
	public char[] getCodiceFiscale(){if (CodiceFiscale != null)return CodiceFiscale.clone(); else return null;}
	public char getSesso(){return Sesso;}
	
	//@ ensures \result == cfauthenticator(CodiceFiscale);
	public boolean cfvalido(){
		if (cfauthenticator(CodiceFiscale)) return true;
		else return false;
	}
	
	protected String cf2string(char[] cf){
		if (cf == null) return "";
		String scf = "";
		for (int i = 0 ; i < cf.length; i++){
			scf += cf[i];
		}
		return scf;
	}
	
	@Override
	public String toString(){
		return "nome:" + Nome + "; cognome:"+ Cognome +"; ddn:" + DataDiNascita[0] + "/"+ DataDiNascita[1]+"/"+DataDiNascita[2]+"; S:"+Sesso + "; cf:" + cf2string(this.CodiceFiscale) +"; cfvalido: "+ cfvalido();
	}
		
	//@ requires a != null;
	//@ ensures \result == ((\forall int i; i >= 0 && i < 3 ; a[i] == get3cognome(Cognome)[i]) && (\forall int i; i >= 3 && i < 6 ; a[i] == get3nome(Nome)[i-3])&& (\forall int i; i >= 6 && i < 11 ; a[i] == get5ddn(DataDiNascita)[i-6]) && (!NazioneDiNascita.equals("IT") ==> a[11] == 'Z') && (\forall int i; i >= 12 && i <= 14; Character.isDigit(a[i])) && Character.isLetter(a[15]));
	private /*@ spec_public @*/boolean cfauthenticator(char[] a){
		if(a.length == 16){
			char[] b = new char[12];
			b[0] = get3cognome(Cognome)[0];
			b[1] = get3cognome(Cognome)[1];
			b[2] = get3cognome(Cognome)[2];
			b[3] = get3nome(Nome)[0];
			b[4] = get3nome(Nome)[1];
			b[5] = get3nome(Nome)[2];
			b[6] = get5ddn(DataDiNascita)[0]; 
			b[7] = get5ddn(DataDiNascita)[1];
			b[8] = get5ddn(DataDiNascita)[2];
			b[9] = get5ddn(DataDiNascita)[3];
			b[10] = get5ddn(DataDiNascita)[4];
			if (!NazioneDiNascita.equals("IT")) b[11] = 'Z';

			for (int i = 0; i < 11; i++){
				if(a[i] != b[i]) return false;
			}	
			if(b[11] == 'Z' && a[11] != 'Z') return false;
			if(!Character.isDigit(a[12])) return false;
			if(!Character.isDigit(a[13])) return false;
			if(!Character.isDigit(a[14])) return false;
			if(!Character.isLetter(a[15]))return false;
			return true;
		}else return false;
	}
	private /*@ spec_public @*/char[] get3cognome(String c){
		int position = 0;
		char[] cf = new char[3];
		String congnomesenzavocali = "";
		congnomesenzavocali = congnomesenzavocali.concat(c);

		congnomesenzavocali = congnomesenzavocali.toUpperCase();
		congnomesenzavocali = congnomesenzavocali.replaceAll("[AEIOU]", "");
	
		String cognomevocali = "";
		cognomevocali = cognomevocali.concat(c);
		cognomevocali = cognomevocali.toUpperCase();

		cognomevocali = cognomevocali.replaceAll("[^AEIOU]", "");
		
		for(int i = 0; i < congnomesenzavocali.length() ; i++ ){
			if (position == 3) break;
			cf[position] = congnomesenzavocali.charAt(i);
			position++;
		}
		for(int i = 0; i < cognomevocali.length() ; i++ ){
			if (position >= 3) break;
			cf[position] = cognomevocali.charAt(i);
			position++;
		}
		while(position < 2){
			cf[position] = 'X';
			position++;
		}	
		return cf;
	}
	private /*@ spec_public @*/char[] get3nome(String nc){
		char[] cf = new char[3];
		String n = "";
		int position = 0;
		for(int i = 0; i<nc.length(); i++){
			char a = nc.charAt(i);
			if(a == ','){
				break;
			}
			n += a;
		}
		String nsenzavocali = "";
		nsenzavocali = nsenzavocali.concat(n);
		nsenzavocali = nsenzavocali.toUpperCase();
		nsenzavocali = nsenzavocali.replaceAll("[AEIOU]", "");
		
		
		String nvocali = "";
		nvocali = nvocali.concat(n);
		nvocali = nvocali.toUpperCase();
		nvocali = nvocali.replaceAll("[^AEIOU]", "");
		
		if (nsenzavocali.length() > 3){
			for(int i = 0; i < nsenzavocali.length() ; i++ ){
				if (i == 1) continue;
				if (position == 3) break;
				cf[position] = nsenzavocali.charAt(i);
				position++;
			}
		}else{
			for(int i = 0; i < nsenzavocali.length() ; i++ ){
				if (position == 3) break;
				cf[position] = nsenzavocali.charAt(i);
				position++;
			}
			for(int i = 0; i < nvocali.length() ; i++ ){
				if (position >= 3) break;
				cf[position] = nvocali.charAt(i);
				position++;
			}
			while(position < 3){
				cf[position] = 'X';
				position++;
			}
		}
		return cf;
	}
	private /*@ spec_public @*/char[] get5ddn(int[] ddn){
		char[] cf = new char[5];
		int position = 0; 
		int annodn = Integer.valueOf(ddn[2]);
		int secondacifra = annodn % 10;
		annodn = annodn /10;
		int primacifra = annodn % 10;
		
		cf[position] = (char) (primacifra + '0');
		position++;
		cf[position] = (char) (secondacifra + '0');
		position++;
		//fine parte data di nascita
		//inizio mese di nascita
		int mesedn = Integer.valueOf(ddn[1]);
		cf[position] = (char) (mesedn + 'A' - 1);
		position++;
		//fine mese di nascita
		//inizio giorno di nascita
		int giornodn = Integer.valueOf(ddn[0]);
		if(Sesso == 'F') giornodn += 40;
		secondacifra = giornodn % 10;
		giornodn = giornodn / 10;
		primacifra = giornodn % 10;
		cf[position] = (char) (primacifra + '0');
		position++;
		cf[position] = (char) (secondacifra + '0');
		position++;
		return cf;
	}

}